package com.rkarp.botlib;

import com.rkarp.appcore.settings.LoadSave;
import com.rkarp.botlib.enums.BotPlatforms;
import com.rkarp.botlib.enums.BotType;
import com.rkarp.botlib.plugin.BotSetting;
import javafx.collections.ObservableList;

public class PluginConfig {

    public static ObservableList<BotSetting> settingsList;

    public static BotPlatforms botPlatform;
    public static BotType.Bot botType;

    /** settings **/
    @LoadSave
    public static String browserProfileName;
    @LoadSave
    public static String chromeConfigDirectory;

    public static boolean isBrowserBotPlugin = true;
}
