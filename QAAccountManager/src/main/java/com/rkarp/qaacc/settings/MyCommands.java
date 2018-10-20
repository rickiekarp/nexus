package com.rkarp.qaacc.settings;

import net.rickiekarp.core.controller.LanguageController;
import net.rickiekarp.core.model.ConsoleCommands;
import net.rickiekarp.core.settings.AppCommands;

/**
 * This class is used to define additional application related console commands.
 * Simply add the new command to the commands list and define the method to execute.
 */
public class MyCommands {
    public void addCommands() throws NoSuchMethodException {
        AppCommands.commandsList.addAll(
                new ConsoleCommands("/addProject", "NAME", LanguageController.getString("addProject_desc"), this.getClass()),
                new ConsoleCommands("/botAccount", "PROJECT NAME AMOUNT", LanguageController.getString("addAccounts_desc"), this.getClass())
        );
    }

    public static void addProject(String str) {
        System.out.println(str);
//            else if (command.equals(commandsList.get(6).getCommandName() + " " + str[2])) {
//            try {
//                SettingsXmlFactory.addProject(str[2]);
//                MainScene.gameComboBox.getItems().add(str[2]);
//                MainScene.consoleStatus.setText(LanguageController.getString("addProject_success"));
//            } catch (MalformedURLException | FileNotFoundException e1) {
//                if (AppConfiguration.debugVersion) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
//            }
//        }
    }

    public static void addAccounts(String str) {
        System.out.println(str);

//            else if (command.equals(commandsList.get(7).getCommandName() + " " + str[2] + " " + str[3] + " " + str[4])) {
//            try {
//                int amount = Integer.parseInt(str[4]);
//
//                if (amount >= 1) {
//                    for (int i = 0; i < amount; i++) {
//                        AccountXmlFactory.addAccount(Integer.parseInt(str[2]), str[3] + i, AppConfiguration.mail_pref + str[3] + i + AppConfiguration.mail_end, "1", "");
//                    }
//                    if (amount == 1) {
//                        MainScene.consoleStatus.setText(LanguageController.getString("accAdded"));
//                    } else {
//                        MainScene.consoleStatus.setText(LanguageController.getString("accsAdded"));
//                    }
//                }
//            } catch (ParserConfigurationException | IOException | TransformerException | SAXException e1) {
//                if (AppConfiguration.debugVersion) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
//            } catch (NumberFormatException | IndexOutOfBoundsException e1) {
//                //ignore
//            }
        }
    }


