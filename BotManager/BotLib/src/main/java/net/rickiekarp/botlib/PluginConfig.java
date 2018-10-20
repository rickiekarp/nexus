package net.rickiekarp.botlib;

import net.rickiekarp.core.settings.LoadSave;
import net.rickiekarp.botlib.enums.BotPlatforms;
import net.rickiekarp.botlib.enums.BotType;
import net.rickiekarp.botlib.plugin.BotSetting;
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
