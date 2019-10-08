package net.rickiekarp.core.settings;

import net.rickiekarp.core.controller.LanguageController;
import javafx.geometry.Side;
import javafx.scene.paint.Color;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/** Annotation for indicating load/save a field. */
@Retention(RetentionPolicy.RUNTIME)
public @interface LoadSave {

    /** settings defaults **/
    String host = "https://app.rickiekarp.net/";
    int updateChannel = 0;
    int language = LanguageController.INSTANCE.getLocale();
    int themeState = 0;
    int colorScheme = 0;
    boolean animations = true;
    boolean useSystemBorders = false;
    boolean logState = false;
    boolean showTrayIcon = false;

    /** advanced settings defaults **/
    boolean debugState = false;
    Color decorationColor = Color.valueOf("#1d1d1d");
    Color shadowColorFocused = Color.valueOf("#000000");
    Color shadowColorNotFocused = Color.valueOf("#a9a9a9");
    Side tabPosition = Side.LEFT;
}
