package net.rickiekarp.core.settings;

import net.rickiekarp.core.controller.LanguageController;
import net.rickiekarp.core.debug.DebugHelper;
import net.rickiekarp.core.debug.ExceptionHandler;
import net.rickiekarp.core.debug.LogFileHandler;
import net.rickiekarp.core.model.ConsoleCommands;
import net.rickiekarp.core.view.CommandsScene;
import net.rickiekarp.core.view.MessageDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

public class AppCommands {
    public static ObservableList<ConsoleCommands> commandsList = FXCollections.observableArrayList();

    /**
     * Console commands are added to a list.
     */
    public void fillCommandsList() throws NoSuchMethodException {
        AppCommands.commandsList.add(
                new ConsoleCommands("/help", "", LanguageController.getString("commandsList_desc"), AppCommands.class));
        AppCommands.commandsList.add(
                new ConsoleCommands("/exceptionTest", "", LanguageController.getString("throwsException"), AppCommands.class));
        AppCommands.commandsList.add(
                new ConsoleCommands("/errorTest", "", LanguageController.getString("showTest_desc"), AppCommands.class));
        AppCommands.commandsList.add(
                new ConsoleCommands("/log", "(create/size/clear)", LanguageController.getString("logFile_desc"), AppCommands.class));
        AppCommands.commandsList.add(
                new ConsoleCommands("/restart", "", LanguageController.getString("restart_desc"), AppCommands.class));
    }

    /**
     * Checks and executed the entered command.
     * @param command Entered command
     */
    public static void execCommand(String command) {
        if (AppCommands.commandsList.isEmpty()) {
            System.out.println("No commands found!");
            return;
        }

        //trim spaces of command string
        command = command.replaceAll("\\s+"," ");

        for (int i = 0; i < commandsList.size(); i++) {
            if (command.startsWith(AppCommands.commandsList.get(i).getCommandName())) {
                String methoName = AppCommands.commandsList.get(i).getCommandName().substring(1, AppCommands.commandsList.get(i).getCommandName().length()); // methodname to be invoked

                if (command.equals(AppCommands.commandsList.get(i).getCommandName()) || command.equals(AppCommands.commandsList.get(i).getCommandName() + " ")) {
                    Method myMethod;
                    try {
                        myMethod = AppCommands.commandsList.get(i).getMethod().getDeclaredMethod(methoName);
                        myMethod.invoke(AppCommands.commandsList.get(i).getMethod().getDeclaredMethod(methoName));
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e1) {
                        System.out.println("Method not found!");
                    }
                } else {
                    String newCommand = command.substring(
                            AppCommands.commandsList.get(i).getCommandName().length() + 1, command.length()
                    );

                    // make an object array and store the parameters that you wish to pass it.
                    // Object[] obj={"hello"}; for method1(String str)
                    // Object[] obj={"hello",1}; for method1(String str, int number)
                    Object[] obj;
                    if (command.isEmpty()) {
                        obj = new Object[] {};
                    } else {
                        //splits the command string
                        obj = newCommand.split(" ");
//                        System.out.println("Parameters: "+ obj.length + " " + Arrays.toString(obj));
                    }

                    // create a class array which will hold the signature of the method being called.
                    Class<?> params[] = new Class[obj.length];
                    for (int e = 0; e < obj.length; e++) {
                        if (obj[e] instanceof String) {
                            params[e] = String.class;
                        }
                        else if (obj[e] instanceof Integer) {
                            params[e] = Integer.TYPE;
                        }
                    }

                    String className = AppCommands.commandsList.get(i).getMethod().getName(); // Class name
                    Class<?> cls;
                    try {
                        cls = Class.forName(className);
                        Object _instance = cls.newInstance();
                        Method myMethod = cls.getDeclaredMethod(methoName, params);
                        myMethod.invoke(_instance, obj);
                    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e1) {
                        System.out.println("Method not found!");
                    }
                }
            }
        }
    }

    public static void help() {
        CommandsScene commands = CommandsScene.Companion.getCommandsScene();
        if (commands == null) {
            new CommandsScene();
        } else {
            if (commands.getCommandsWindow().getWin().getWindowStage().getStage().isShowing()) {
                commands.getCommandsWindow().getWin().getWindowStage().getStage().requestFocus();
            } else {
                commands.getCommandsWindow().getWin().getWindowStage().getStage().show();
            }
        }
    }

    public static void exceptionTest() {
        ExceptionHandler.throwTestException();
    }

    public static void errorTest() {
        new MessageDialog(0, "TEST", 450, 220);
    }

    public static void log(String command) {
        switch (command) {
            case "create": LogFileHandler.createLogFile(); break;
            case "size": LogFileHandler.getLogSize(); break;
            case "clear": LogFileHandler.clearLogData(); break;
        }
    }

    public static void restart() {
        try {
            DebugHelper.restartApplication();
        } catch (URISyntaxException | IOException e1) {
            if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
        }
    }
}
