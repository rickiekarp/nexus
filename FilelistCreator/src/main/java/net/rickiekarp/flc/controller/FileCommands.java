package net.rickiekarp.flc.controller;

import net.rickiekarp.core.controller.LanguageController;
import net.rickiekarp.core.model.ConsoleCommands;
import net.rickiekarp.core.settings.AppCommands;
import net.rickiekarp.core.util.CommonUtil;
import net.rickiekarp.flc.view.layout.MainLayout;

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
