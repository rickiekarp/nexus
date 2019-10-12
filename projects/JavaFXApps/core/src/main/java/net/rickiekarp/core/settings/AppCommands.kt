package net.rickiekarp.core.settings

import net.rickiekarp.core.controller.LanguageController
import net.rickiekarp.core.debug.DebugHelper
import net.rickiekarp.core.debug.ExceptionHandler
import net.rickiekarp.core.debug.LogFileHandler
import net.rickiekarp.core.model.ConsoleCommands
import net.rickiekarp.core.view.CommandsScene
import net.rickiekarp.core.view.MessageDialog
import javafx.collections.FXCollections

import java.io.IOException
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.net.URISyntaxException

class AppCommands {

    /**
     * Console commands are added to a list.
     */
    @Throws(NoSuchMethodException::class)
    fun fillCommandsList() {
        AppCommands.commandsList.add(
                ConsoleCommands("/help", "", LanguageController.getString("commandsList_desc"), AppCommands::class.java))
        AppCommands.commandsList.add(
                ConsoleCommands("/exceptionTest", "", LanguageController.getString("throwsException"), AppCommands::class.java))
        AppCommands.commandsList.add(
                ConsoleCommands("/errorTest", "", LanguageController.getString("showTest_desc"), AppCommands::class.java))
        AppCommands.commandsList.add(
                ConsoleCommands("/log", "(create/size/clear)", LanguageController.getString("logFile_desc"), AppCommands::class.java))
        AppCommands.commandsList.add(
                ConsoleCommands("/restart", "", LanguageController.getString("restart_desc"), AppCommands::class.java))
    }

    companion object {
        var commandsList = FXCollections.observableArrayList<ConsoleCommands>()

        /**
         * Checks and executed the entered command.
         * @param command Entered command
         */
        fun execCommand(command: String) {
            var command = command
            if (AppCommands.commandsList.isEmpty()) {
                println("No commands found!")
                return
            }

            //trim spaces of command string
            command = command.replace("\\s+".toRegex(), " ")

            for (i in commandsList.indices) {
                if (command.startsWith(AppCommands.commandsList[i].commandName)) {
                    val methoName = AppCommands.commandsList[i].commandName.substring(1, AppCommands.commandsList[i].commandName.length) // methodname to be invoked

                    if (command == AppCommands.commandsList[i].commandName || command == AppCommands.commandsList[i].commandName + " ") {
                        val myMethod: Method
                        try {
                            myMethod = AppCommands.commandsList[i].method.getDeclaredMethod(methoName)
                            myMethod.invoke(AppCommands.commandsList[i].method.getDeclaredMethod(methoName))
                        } catch (e: InvocationTargetException) {
                            e.printStackTrace()
                        } catch (e: IllegalAccessException) {
                            e.printStackTrace()
                        } catch (e1: NoSuchMethodException) {
                            println("Method not found!")
                        }

                    } else {
                        val newCommand = command.substring(
                                AppCommands.commandsList[i].commandName.length + 1, command.length
                        )

                        // make an object array and store the parameters that you wish to pass it.
                        // Object[] obj={"hello"}; for method1(String str)
                        // Object[] obj={"hello",1}; for method1(String str, int number)
                        val obj: Array<Any>
                        if (command.isEmpty()) {
                            obj = arrayOf()
                        } else {
                            //splits the command string
                            obj = newCommand.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                            //                        System.out.println("Parameters: "+ obj.length + " " + Arrays.toString(obj));
                        }

                        // create a class array which will hold the signature of the method being called.
                        val params = arrayOfNulls<Class<*>>(obj.size)
                        for (e in obj.indices) {
                            if (obj[e] is String) {
                                params[e] = String::class.java
                            } else if (obj[e] is Int) {
                                params[e] = Integer.TYPE
                            }
                        }

                        val className = AppCommands.commandsList[i].method.name // Class name
                        val cls: Class<*>
                        try {
                            cls = Class.forName(className)
                            val _instance = cls.newInstance()
                            val myMethod = cls.getDeclaredMethod(methoName, *params)
                            myMethod.invoke(_instance, *obj)
                        } catch (e: ClassNotFoundException) {
                            e.printStackTrace()
                        } catch (e: IllegalAccessException) {
                            e.printStackTrace()
                        } catch (e: InstantiationException) {
                            e.printStackTrace()
                        } catch (e: InvocationTargetException) {
                            e.printStackTrace()
                        } catch (e1: NoSuchMethodException) {
                            println("Method not found!")
                        }
                    }
                }
            }
        }

        fun help() {
            val commands = CommandsScene.commandsScene
            if (commands == null) {
                CommandsScene()
            } else {
                if (commands.commandsWindow!!.win.windowStage.stage.isShowing) {
                    commands.commandsWindow!!.win.windowStage.stage.requestFocus()
                } else {
                    commands.commandsWindow!!.win.windowStage.stage.show()
                }
            }
        }

        fun exceptionTest() {
            ExceptionHandler.throwTestException()
        }

        fun errorTest() {
            MessageDialog(0, "TEST", 450, 220)
        }

        fun log(command: String) {
            when (command) {
                "create" -> LogFileHandler.createLogFile()
                "size" -> LogFileHandler.getLogSize()
                "clear" -> LogFileHandler.clearLogData()
            }
        }

        fun restart() {
            try {
                DebugHelper.restartApplication()
            } catch (e1: URISyntaxException) {
                if (DebugHelper.DEBUGVERSION) {
                    e1.printStackTrace()
                } else {
                    ExceptionHandler(Thread.currentThread(), e1)
                }
            } catch (e1: IOException) {
                if (DebugHelper.DEBUGVERSION) {
                    e1.printStackTrace()
                } else {
                    ExceptionHandler(Thread.currentThread(), e1)
                }
            }
        }
    }
}
