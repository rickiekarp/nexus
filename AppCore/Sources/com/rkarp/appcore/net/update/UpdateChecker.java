package com.rkarp.appcore.net.update;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkarp.appcore.AppContext;
import com.rkarp.appcore.debug.DebugHelper;
import com.rkarp.appcore.debug.ExceptionHandler;
import com.rkarp.appcore.debug.LogFileHandler;
import com.rkarp.appcore.model.dto.ApplicationDTO;
import com.rkarp.appcore.net.NetworkApi;
import com.rkarp.appcore.settings.Configuration;
import com.rkarp.appcore.util.FileUtil;
import javafx.application.Platform;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UpdateChecker {
    public static ArrayList<String> filesToDownload = new ArrayList<>();
    public static boolean isUpdAvailable = false;

    /**
     * Compares local and remote program versions and returns the update status ID
     * @return  Returns update status ID as an integer
     *          Status ID's are: 0 (No update), 1 (Update), 2 (No connection), 3 (Error)
     */
    public int checkProgramUpdate() {
        InputStream inputStream = AppContext.getContext().getNetworkApi().runNetworkAction(NetworkApi.requestVersionInfo(Configuration.updateChannel));
        if (inputStream == null) {
            return 2;
        }

        List<ApplicationDTO> applicationList;
        try {
            applicationList = new ObjectMapper().readValue(inputStream, new TypeReference<List<ApplicationDTO>>(){});
        } catch (IOException e) {
            e.printStackTrace();
            return 3;
        }


        boolean isUpdateEnabled; // Is the update channel open?
        for (ApplicationDTO applicationEntry : applicationList) {
            isUpdateEnabled = applicationEntry.isUpdateEnable();
            if (isUpdateEnabled) {
                LogFileHandler.logger.info("Checking module [" + applicationEntry.getIdentifier() + "] version: " + applicationEntry.getVersion());

                //convert local/remote versions to int
                int remoteVer = applicationEntry.getVersion();
                int localVer = -1;
                try {
                    localVer = Integer.parseInt(FileUtil.readManifestPropertyFromJar(Configuration.config.getJarFile().getParent() + File.separator + applicationEntry.getIdentifier(), "Build-Time"));
                } catch (IOException e) {
                    LogFileHandler.logger.warning("Error while reading version: " + e.getMessage());
                }

                //compare versions
                if (remoteVer > localVer) {
                    filesToDownload.add(applicationEntry.getIdentifier());
                }
            }
        }

        //return update status for files to update
        if (filesToDownload.size() > 0) {
            isUpdAvailable = true;
            LogFileHandler.logger.info("New updates found: " + filesToDownload.subList(0, filesToDownload.size()));
            return 1;
        }
        return 0;
    }

    /**
     * Checks the listed server for a new java version
     * @return Returns remote version string
     * @deprecated Feature was removed because the JreCurrentVersion2.txt was removed
     */
    @Deprecated
    private String getRemoteJavaVersion() {

        String version;
        URL javaurl = null;

        LogFileHandler.logger.info("Checking for new java version...");

        try {
            javaurl = new URL("http://java.com/applet/JreCurrentVersion2.txt");

            LogFileHandler.logger.info("Connecting to: " + javaurl);
            Scanner scanner = new Scanner(javaurl.openStream());
            version = scanner.next();
            scanner.close();
            LogFileHandler.logger.info("Success! Current remote java version: " + version);

            return version;
        }
        catch(IOException e) {
            // there was some connection problem, or the file did not exist on the server,
            // or your URL was not in the right format.
            LogFileHandler.logger.warning("Can not connect to: " + javaurl);
            if (DebugHelper.DEBUGVERSION) { e.printStackTrace(); } else {
                Platform.runLater(() -> new ExceptionHandler(Thread.currentThread(), e));
            }
            return "no_connection";
        }
    }

    /**
     * Compares local and remote java versions
     * @return Returns update status ID as an integer
     * @deprecated Feature was removed because the JreCurrentVersion2.txt was removed
     */
    @Deprecated
    public int checkJavaUpdate() {
        String remoteJavaVersion = getRemoteJavaVersion();
        String localJavaVersion = System.getProperty("java.version");

        if (localJavaVersion.equals(remoteJavaVersion)) {
            return 0;
        } else {

            if (remoteJavaVersion.equals("no_connection")) {
                return 2;
            } else {
                remoteJavaVersion = remoteJavaVersion.replace(".", "");
                localJavaVersion = localJavaVersion.replace(".", "");

                //splits the version string
                String localVersion[] = localJavaVersion.split("_");
                int localversion = Integer.parseInt(localVersion[0]);
                int localrevision = Integer.parseInt(localVersion[1]);

                String remoteVersion[] = remoteJavaVersion.split("_");
                int remoteversion = Integer.parseInt(remoteVersion[0]);
                int remoterevision = Integer.parseInt(remoteVersion[1]);

                //if remote version is higher than local
                if (remoteversion > localversion) {
                    return 1;
                }

                //if remote revision equals local
                else if (remoteversion == localversion) {
                    if (remoterevision > localrevision) {
                        return 1;
                    }
                    if (remoterevision <= localrevision) {
                        return 0;
                    }
                }

                //if remote version is lower than local
                else if (remoteversion < localversion) {
                    return 0;
                }

                //return an error if no check returned a status
                return 3;
            }
        }
    }

    /**
     * Starts the updater and installs updates.
     * @throws URISyntaxException Exception
     * @throws IOException Exception
     */
    public static void installUpdate() throws URISyntaxException, IOException {
        /* is it a jar file? */
        if(!Configuration.config.getJarFile().getName().endsWith(".jar")) { return; }

        final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";

        File updater = new File(Configuration.config.getJarFile().getParentFile() + "/data/update/updater.jar");

        //install new updater.jar if it has been downloaded earlier
        if (updater.exists()) {
            final Path moveFrom = updater.toPath();
            final Path moveTo = Configuration.config.getJarFile().getParentFile().toPath();

            //move file
            Files.move(moveFrom, moveTo.resolve(moveFrom.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        }

        /* Build command: java -jar application.jar */
        final ArrayList<String> command = new ArrayList<>();
        command.add(javaBin);
        command.add("-jar");
        command.add(Configuration.config.getJarFile().getParent() + File.separator + "updater.jar");
        command.add("update");
        command.add(Configuration.config.getJarFile().getName());

        if (!new File(Configuration.config.getJarFile().getParentFile() + "/updater.jar").exists()) {
            return;
        }

        final ProcessBuilder builder = new ProcessBuilder(command);
        builder.start();
        System.exit(0);
    }
}
