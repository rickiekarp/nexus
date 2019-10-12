package net.rickiekarp.core.debug;

import net.rickiekarp.core.controller.LanguageController;
import net.rickiekarp.core.settings.Configuration;
import net.rickiekarp.core.util.CommonUtil;
import net.rickiekarp.core.view.MessageDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.logging.*;

/**
 * This class handles the Logfile behaviour (set up logger, create logfile etc.)
 */
public class LogFileHandler {
    public static Logger logger = Logger.getLogger("AppLog");
    private static Formatter formatter;
    private static FileHandler fh;
    private static ObservableList<String> logData = FXCollections.observableArrayList();
    private final static Level logLevel = Level.CONFIG;

    /**
     * Sets up the logger
     * Called on program start
     */
    public static void setupLogger() {

        logger.setUseParentHandlers(false);
        logger.setLevel(logLevel);

        // defines the Log File formatting
        formatter = new Formatter() {
            @Override
            public String format(LogRecord record) {
                StringBuilder sb = new StringBuilder();

                sb.append(new Date(record.getMillis()))
                        .append(" - ")
                        .append(record.getLevel())
                        .append(" in ").append(record.getSourceMethodName())
                        .append(": ")
                        .append(formatMessage(record))
                        .append(System.getProperty("line.separator"));

                logData.add(sb.toString());
                return sb.toString();
            }
        };
    }

    public static void startLogging() {
        //starts the fileHandler if logState = true
        if (Configuration.Companion.getLogState()) { onLogStateChange(); }

        //shows logging in the console if DEBUGVERSION = true
        if (DebugHelper.INSTANCE.getDEBUGVERSION()) {
            ConsoleHandler ch = new ConsoleHandler();
            ch.setLevel(logLevel);
            ch.setFormatter(formatter);

            //add the console handler to the logger
            logger.addHandler(ch);
        }
    }

    /**
     * Starts / Stops the FileHandler
     * Called when: program starts / settings change / log file was created
     */
    public static void onLogStateChange() {

        if (fh == null) {
            if (!Configuration.Companion.getConfig().getLogsDirFile().exists()) { Configuration.Companion.getConfig().getLogsDirFile().mkdirs(); }
            try {
                fh = new FileHandler(Configuration.Companion.getConfig().getLogsDirFile().getPath() + File.separator + getLogFileName());
            } catch (IOException e1) {
                if (DebugHelper.INSTANCE.getDEBUGVERSION()) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
            }
            fh.setLevel(logLevel);
            fh.setFormatter(formatter);

            //add the file handler to the logger
            logger.addHandler(fh);
            logger.log(Level.INFO, "Logging started");
        } else {
            logger.removeHandler(fh);
            fh.close(); fh = null;
        }
    }

    /**
     * Writes all logged data to a file
     * Only called when using the /log create command
     */
    public static void createLogFile() {

        //checks if the fileHandler is active
        if (fh != null) {
            //fileHandler is closed if active
            logger.removeHandler(fh);
            fh.close(); fh = null;
            logData.clear();

            //open a new fileHandler for a new logfile
            if (Configuration.Companion.getLogState()) {
                onLogStateChange();
            }
        }
        else //executed if fileHandler is not active
        {
            //checks if there is logData that can be written to a file
            if (logData.size() > 0) {

                //checks if 'logs' directory exists
                if (Configuration.Companion.getConfig().getLogsDirFile().exists()) {
                    writeLog();
                }
                else
                {
                    //create logs directory
                    Configuration.Companion.getConfig().getLogsDirFile().mkdirs();
                    logger.log(Level.INFO, "'logs' directory created in " + Configuration.Companion.getConfig().getJarFile().getParent());
                    writeLog();
                }
            }
            else
            {
                new MessageDialog(0, LanguageController.getString("logFile_created_fail") + " " + LanguageController.getString("no_logData_desc"), 450, 220);
            }
        }
    }

    private static void writeLog() {
        try {
            PrintStream ps = new PrintStream(new File(Configuration.Companion.getConfig().getLogsDirFile(), getLogFileName()));
            logData.forEach(ps::print);
            ps.close();
        } catch (FileNotFoundException e1) {
            if (DebugHelper.INSTANCE.getDEBUGVERSION()) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
        }
        logData.clear();
    }

    /**
     * Deletes all logged data
     */
    public static void clearLogData() {
        if (logData.size() != 0) {
            logData.clear();
            logger.log(Level.INFO, "Logfile cleared!");
        }
    }

    /**
     * Deletes all logged data
     */
    public static void getLogSize() {
        new MessageDialog(1, LanguageController.getString("log_size_current") + " " + logData.size(), 450, 220);
    }

    /**
     * Returns log file name
     */
    private static String getLogFileName() {
        final String logTime = CommonUtil.getTime("HH-mm-ss"); //time String
        return "log_" + logTime + ".log";
    }
}
