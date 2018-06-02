package com.rkarp.appupdater;

import com.rkarp.appupdater.settings.UpdateConstants;
import com.rkarp.appupdater.ui.UpdateCheckerGUI;
import com.rkarp.appupdater.updatemanager.UpdateInstaller;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class UpdateMain extends Application {

    private static String[] savedArgs;
    public static String[] getArgs() {
        return savedArgs;
    }

    public static void main(String[] args) {
        //BAD HACK: delay start to make sure that the main jar closes completely
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //read manifest version
        UpdateConstants.internalVersion = readManifestProperty("Build-Time");

        savedArgs = args;
        launch(args);
    }

    public void start(final Stage stage) {
        try {
            switch (savedArgs[0]) {
                case "update":
                    if (savedArgs.length > 1 && savedArgs[1].endsWith(".jar")) {
                        UpdateInstaller installer = new UpdateInstaller();
                        installer.installFiles();
                    } else {
                        UpdateCheckerGUI.updateChecker = new UpdateCheckerGUI();
                        UpdateCheckerGUI.updateChecker.setMessage("Jar to update has not been defined! Check your program arguments!");
                    }
                    break;
                case "check":
                    UpdateCheckerGUI.updateChecker = new UpdateCheckerGUI();
                    UpdateCheckerGUI.updateChecker.setMessage("Checking for updates...");
                    break;
                default:
                    UpdateCheckerGUI.updateChecker = new UpdateCheckerGUI();
                    UpdateCheckerGUI.updateChecker.setMessage("Parameter invalid! Please try again with a valid argument!");
                    break;
            }
        } catch (IndexOutOfBoundsException e) {
            UpdateCheckerGUI.updateChecker = new UpdateCheckerGUI();
            UpdateCheckerGUI.updateChecker.setMessage("No parameter found. Please try again with a valid parameter.");
        }
    }

    /**
     * Returns exception string
     */
    public static String getExceptionString(Throwable t) {

        StringBuilder sb = new StringBuilder();

        sb.append(String.valueOf(t)).append(System.getProperty("line.separator"));

        StackTraceElement[] trace = t.getStackTrace();
        for (StackTraceElement aTrace : trace) {
            sb.append("       at ").append(String.valueOf(aTrace)).append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }

    public static String readManifestProperty(String key) {
        InputStream manifestStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/MANIFEST.MF");
        try {
            Manifest manifest = new Manifest(manifestStream);
            Attributes attributes = manifest.getMainAttributes();
            String value = attributes.getValue(key);
            manifestStream.close();
            return value;
        } catch(IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
