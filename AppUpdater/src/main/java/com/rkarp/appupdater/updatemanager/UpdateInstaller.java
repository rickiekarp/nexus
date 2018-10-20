package com.rkarp.appupdater.updatemanager;

import com.rkarp.appupdater.UpdateMain;
import com.rkarp.appupdater.ui.UpdateCheckerGUI;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class UpdateInstaller {

    private File jarFile;

    public UpdateInstaller() {
        try {
            jarFile = new File(UpdateMain.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            UpdateCheckerGUI.updateChecker.appendMessage(UpdateMain.getExceptionString(e));
            e.printStackTrace();
        }
    }

    public void installFiles()
    {
        //update location
        File file = new File(jarFile.getParent() + File.separator + "data/update/");

        if (file.exists()) {
            //list all files in directory
            File[] listOfFiles = file.listFiles();

            if (listOfFiles.length > 0) {
                Path copyTo = new File(jarFile.getParent()).toPath();

                //iterate through directory
                for (File afile : listOfFiles) {
                    Path copyFrom = afile.toPath();

                    try {
                        Files.copy(copyFrom, copyTo.resolve(copyFrom.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                        UpdateCheckerGUI.updateChecker.appendMessage(UpdateMain.getExceptionString(e));
                    }
                }
                cleanup(listOfFiles);
            }
            else
            {
                UpdateCheckerGUI updateChecker = new UpdateCheckerGUI();
                updateChecker.setMessage("Can not find any files to update!");
                try {
                    launchProgram();
                } catch (URISyntaxException | IOException e) {
                    e.printStackTrace();
                    UpdateCheckerGUI.updateChecker.appendMessage(UpdateMain.getExceptionString(e));
                }
            }
        }
        else
        {
            UpdateCheckerGUI updateChecker = new UpdateCheckerGUI();
            updateChecker.setMessage("Can not find update directory " + file.getPath());
        }
    }

    private void cleanup(File[] files)
    {
        System.out.println("Preforming clean up...");

        //iterate through start directory
        for (File afile : files) {
            System.out.println("deleting: " + afile);

            afile.delete();
        }

        System.out.println("Cleaned up...");
        System.out.println("Update done!");

        try {
            launchProgram();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            UpdateCheckerGUI.updateChecker.appendMessage(UpdateMain.getExceptionString(e));
        }
    }

    private void launchProgram() throws URISyntaxException, IOException {
        final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        final File currentJar = new File(UpdateMain.class.getProtectionDomain().getCodeSource().getLocation().toURI());

        /* Build command: java -jar application.jar */
        final ArrayList<String> command = new ArrayList<String>();
        command.add(javaBin);
        command.add("-jar");
        command.add(currentJar.getParent() + File.separator + UpdateMain.getArgs()[1]);

        final ProcessBuilder builder = new ProcessBuilder(command);
        builder.start();
        System.exit(0);
    }
}
