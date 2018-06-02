package com.rkarp.appcore.debug;

import com.rkarp.appcore.AppContext;
import com.rkarp.appcore.settings.Configuration;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * This class contains helper functions used for debugging purposes.
 */
public class DebugHelper {

    public static final boolean DEBUGVERSION = false;
    private static long startTime = 0;

    public static boolean isDebugVersion() {
        if(DEBUGVERSION) {
            return true;
        } else {
            if (Configuration.debugState) { return true; }
        }
        return false;
    }

    public static void logProperties() {
        LogFileHandler.logger.info("JAVA_VERSION=" + System.getProperty("java.version"));
        LogFileHandler.logger.config("PROGRAM_VERSION=" + AppContext.getContext().getInternalVersion());
        LogFileHandler.logger.config("DEBUGVERSION=" + DebugHelper.isDebugVersion());
        LogFileHandler.logger.config("UPDATE_CHANNEL=" + Configuration.updateChannel);
        LogFileHandler.logger.config("LOGS=" + Configuration.logState);
        LogFileHandler.logger.config("PROGRAM_LANGUAGE={" + Configuration.language + "," + Configuration.CURRENT_LOCALE.toString() + "}");
        LogFileHandler.logger.config("SYSTEM_TRAY=" + Configuration.showTrayIcon);
        LogFileHandler.logger.config("HOST=" + Configuration.host);
        //LogFileHandler.logger.config("CORE_AMOUNT={" + Runtime.getRuntime().availableProcessors() + "}");
    }

    public static void profile(String state, String name) {
        switch (state) {
            case "start":
                if (startTime == 0) {
                    startTime = System.currentTimeMillis();
                    LogFileHandler.logger.info("Start monitoring " + name);
                    break;
                } else { LogFileHandler.logger.warning("Profiler already started!"); break; }
            case "stop":
                if (startTime != 0) {
                    long endTime = System.currentTimeMillis();
                    LogFileHandler.logger.info("Stop monitoring " + name + "! Total execution time: " + (endTime - startTime) + "ms");
                    startTime = 0;
                    break;
                } else { LogFileHandler.logger.warning("Profiler not started!"); break; }
        }
    }

    /**
     * Restarts the application. Only works when running the jar file.
     * @throws URISyntaxException URISyntaxException
     * @throws IOException IOException
     */
    public static void restartApplication() throws URISyntaxException, IOException {

        final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        final File currentJar = new File(DebugHelper.class.getProtectionDomain().getCodeSource().getLocation().toURI());

        /* is it a jar file? */
        if(!currentJar.getName().endsWith(".jar")) { return; }

        /* Build command: java -jar application.jar */
        final ArrayList<String> command = new ArrayList<>();
        command.add(javaBin);
        command.add("-jar");
        command.add(currentJar.getPath());

        final ProcessBuilder builder = new ProcessBuilder(command);
        builder.start();
        System.exit(0);
    }
}
