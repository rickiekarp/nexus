package net.rickiekarp.core.controller;

import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.rickiekarp.core.debug.LogFileHandler;
import net.rickiekarp.core.settings.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

public final class LanguageController {
    private static Properties prop;

    public static final int getCurrentLocale() {
        String var0 = Configuration.CURRENT_LOCALE.toString();
        switch(var0.hashCode()) {
            case 3201:
                if (var0.equals("de")) {
                    return 1;
                }
                break;
            case 3241:
                if (var0.equals("en")) {
                    return 0;
                }
        }

        return 0;
    }

    public static final int getLocale() {
        String var10000 = System.getProperty("user.language");
        if (var10000 != null) {
            String var0 = var10000;
            switch(var0.hashCode()) {
                case 3201:
                    if (var0.equals("de")) {
                        return 1;
                    }
                    break;
                case 3241:
                    if (var0.equals("en")) {
                        return 0;
                    }
            }
        }

        return 0;
    }

    @JvmStatic
    public static final void loadLangFile(InputStream utf8in) {
        try {
            System.out.println(utf8in);
            System.out.println("Locale: " + Configuration.CURRENT_LOCALE);
            try {
                prop.load(utf8in);
            } catch (IOException var2) {
                var2.printStackTrace();
            }

            utf8in.close();
        } catch (NullPointerException | IOException var3) {
            LogFileHandler.logger.warning("Error");
        }

    }

    @JvmStatic
    public static final String getString(String textID) {
        Intrinsics.checkParameterIsNotNull(textID, "textID");

        try {
            String value = prop.getProperty(textID);
            CharSequence var2 = (CharSequence)value;
            return var2 == null || StringsKt.isBlank(var2) ? textID : value;
        } catch (NullPointerException var3) {
            LogFileHandler.logger.warning("Error when loading text ID: " + textID);
            return textID;
        }
    }

    @JvmStatic
    public static final void setCurrentLocale() {
        switch(Configuration.language) {
            case 0:
                Configuration.CURRENT_LOCALE = Locale.ENGLISH;
                break;
            case 1:
                Configuration.CURRENT_LOCALE = Locale.GERMAN;
                break;
            default:
                Configuration.CURRENT_LOCALE = Locale.ENGLISH;
        }

    }

    static {
        prop = new Properties();
    }
}