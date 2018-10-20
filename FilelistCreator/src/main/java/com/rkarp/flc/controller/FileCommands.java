package com.rkarp.flc.controller;

import com.rkarp.appcore.controller.LanguageController;
import com.rkarp.appcore.model.ConsoleCommands;
import com.rkarp.appcore.settings.AppCommands;
import com.rkarp.appcore.util.CommonUtil;
import com.rkarp.flc.view.layout.MainLayout;

/**
 * This class is used to define additional application related console commands.
 * Simply add the new command to the commands list and define the method to execute.
 */
public class FileCommands {
    public void addFileCommands() throws NoSuchMethodException {
        AppCommands.commandsList.add(
                new ConsoleCommands("/selectRandomFile", "", LanguageController.getString("desc_file_select_random"), this.getClass())
        );
    }

    public static void selectRandomFile() {
        if (MainLayout.fileTable.getItems() != null && MainLayout.fileTable.getItems().size() > 0) {
            final int randomNumber = CommonUtil.randInt(0, MainLayout.fileTable.getItems().size() - 1);
            MainLayout.fileTable.getSelectionModel().select(randomNumber);
            MainLayout.mainLayout.setStatus("neutral", MainLayout.fileTable.getItems().get(randomNumber).getFilename() + " selected!");
        } else {
            MainLayout.mainLayout.setStatus("neutral", "No file found!");
        }
    }
}
