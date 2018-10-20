package com.rkarp.botlib;

import net.rickiekarp.core.settings.Configuration;
import net.rickiekarp.core.settings.LoadSave;

import java.io.File;

public class BotConfig {
    @LoadSave
    public static String nodeBinary;
    public static String appiumBinary = File.separator+"appium"+File.separator+"build"+File.separator+"lib"+File.separator+"main.js";

    public static File getModulesDirFile() {
        return new File(Configuration.config.getConfigDirFile() + File.separator + "modules");
    }

    public static int APPIUM_LOG_LEVEL = 1;

    public static String DEVICE_NAME;
    public static String VERSION;
    public static String UDID;
}
