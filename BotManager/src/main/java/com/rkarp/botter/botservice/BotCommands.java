package com.rkarp.botter.botservice;

import com.rkarp.appcore.controller.LanguageController;
import com.rkarp.appcore.model.ConsoleCommands;
import com.rkarp.appcore.settings.AppCommands;
import com.rkarp.botter.settings.AppConfiguration;
import com.rkarp.botter.view.MainLayout;

/**
 * This class is used to define additional application related console commands.
 * Simply add the new command to the commands list and define the method to execute.
 */
public class BotCommands {
    public void addBotCommands() throws NoSuchMethodException {
        AppCommands.commandsList.add(
                new ConsoleCommands("/setInterval", "<minutes>", LanguageController.getString("timer_desc"), this.getClass())
        );
    }

    public static void setInterval(String test) {
        AppConfiguration.runInterval = Integer.parseInt(test);
        MainLayout.mainLayout.setStatus("neutral", "Timer intervall: " + AppConfiguration.runInterval + " min");
    }

}
