package net.rickiekarp.core.ui.windowmanager;

import net.rickiekarp.core.settings.Configuration;
import net.rickiekarp.core.view.CommandsScene;
import net.rickiekarp.core.view.MainScene;
import net.rickiekarp.core.view.SettingsScene;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.net.URL;

public class ThemeSelector {

    public final static String DARK_THEME_CSS = "themes/DarkTheme.css";
    private final static String LIGHT_THEME_CSS = "themes/LightTheme.css";

    /**
     * Sets the theme according to the current themeState.
     * @param scene The scene
     */
    public static void setTheme(Scene scene, ClassLoader loader) {
        switch (Configuration.themeState) {
            case 0:
                URL darkStyle = loader.getResource(DARK_THEME_CSS);
                if(!scene.getStylesheets().contains(DARK_THEME_CSS)) { scene.getStylesheets().clear(); }
                scene.getStylesheets().add(darkStyle.toString());
                break;
            case 1:
                URL lightStyle = loader.getResource(LIGHT_THEME_CSS);
                if(!scene.getStylesheets().contains(LIGHT_THEME_CSS)) { scene.getStylesheets().clear(); }
                scene.getStylesheets().add(lightStyle.toString());
                break;
        }
    }

    /**
     * If the theme is changed, all active stages are updated.
     */
    public static void onThemeChange()
    {
        System.out.println("fixme: onThemeChange()");
//        if (MainScene.mainScene.getWindowScene() != null) { setTheme(MainScene.mainScene.getWindowScene().getWin().getWindowStage().getStage().getScene()); }
//        if (SettingsScene.settingsScene != null) { setTheme(SettingsScene.settingsScene.getSettingsWindow().getWin().getWindowStage().getStage().getScene()); }
////        if (AboutScene.aboutScene != null) { setTheme(AboutScene.aboutScene.getAboutWindow().getWin().getWindowStage().getScene()); }
//        if (CommandsScene.commandsScene != null) { setTheme(CommandsScene.commandsScene.getCommandsWindow().getWin().getScene()); }
    }


    /**
     * Changes the color scheme of the application
     * @param schemeIdx The scheme index
     */
    public static void changeColorScheme(int schemeIdx) {
        switch (schemeIdx) {
            case 0:
                Window.colorTheme = "darkgray";
                MainScene.Companion.getMainScene().getWindowScene().getWin().getClientArea().setStyle("-fx-accent: " + Window.colorTheme + ";" + "-fx-focus-color: " + Window.colorTheme + ";");
                break;
            case 1:
                Window.colorTheme = "gray";
                MainScene.Companion.getMainScene().getWindowScene().getWin().getClientArea().setStyle("-fx-accent: " + Window.colorTheme + ";" + "-fx-focus-color: " + Window.colorTheme + ";");
                break;
            case 2:
                Window.colorTheme = "black";
                MainScene.Companion.getMainScene().getWindowScene().getWin().getClientArea().setStyle("-fx-accent: " + Window.colorTheme + ";" + "-fx-focus-color: " + Window.colorTheme + ";");
                break;
            case 3:
                Window.colorTheme = "red";
                MainScene.Companion.getMainScene().getWindowScene().getWin().getClientArea().setStyle("-fx-accent: " + Window.colorTheme + ";" + "-fx-focus-color: " + Window.colorTheme + ";");
                break;
            case 4:
                Window.colorTheme = "orange";
                MainScene.Companion.getMainScene().getWindowScene().getWin().getClientArea().setStyle("-fx-accent: " + Window.colorTheme + ";" + "-fx-focus-color: " + Window.colorTheme + ";");
                break;
            case 5:
                Window.colorTheme = "yellow";
                MainScene.Companion.getMainScene().getWindowScene().getWin().getClientArea().setStyle("-fx-accent: " + Window.colorTheme + ";" + "-fx-focus-color: " + Window.colorTheme + ";");
                break;
            case 6:
                Window.colorTheme = "blue";
                MainScene.Companion.getMainScene().getWindowScene().getWin().getClientArea().setStyle("-fx-accent: " + Window.colorTheme + ";" + "-fx-focus-color: " + Window.colorTheme + ";");
                break;
            case 7:
                Window.colorTheme = "magenta";
                MainScene.Companion.getMainScene().getWindowScene().getWin().getClientArea().setStyle("-fx-accent: " + Window.colorTheme + ";" + "-fx-focus-color: " + Window.colorTheme + ";");
                break;
            case 8:
                Window.colorTheme = "purple";
                MainScene.Companion.getMainScene().getWindowScene().getWin().getClientArea().setStyle("-fx-accent: " + Window.colorTheme + ";" + "-fx-focus-color: " + Window.colorTheme + ";");
                break;
            case 9:
                Window.colorTheme = "green";
                MainScene.Companion.getMainScene().getWindowScene().getWin().getClientArea().setStyle("-fx-accent: " + Window.colorTheme + ";" + "-fx-focus-color: " + Window.colorTheme + ";");
                break;
            default:
                Window.colorTheme = "darkgray";
                MainScene.Companion.getMainScene().getWindowScene().getWin().setStyle("-fx-accent: " + Window.colorTheme + ";" + "-fx-focus-color: " + Window.colorTheme + ";");
                break;
        }

        for (int i = 0; i < MainScene.Companion.getMainScene().getSceneViewStack().size(); i++) {
            MainScene.Companion.getMainScene().getSceneViewStack().get(i).setStyle("-fx-accent: " + Window.colorTheme + ";" + "-fx-focus-color: " + Window.colorTheme + ";");
        }

        MainScene.Companion.getMainScene().getWindowScene().getWin().setStyle("-fx-accent: " + Window.colorTheme + ";" + "-fx-focus-color: " + Window.colorTheme + ";");
    }

    /**
     * If the theme is changed, all active stages are updated.
     */
    public static void changeWindowShadowColor(boolean focus, String value)
    {
        if (focus) {
            Configuration.shadowColorFocused = Color.valueOf(value);
            Window.dsFocused.setColor(Configuration.shadowColorFocused);
        } else {
            Configuration.shadowColorNotFocused = Color.valueOf(value);
            Window.dsNotFocused.setColor(Configuration.shadowColorNotFocused);
        }
    }

    /**
     * If the theme is changed, all active stages are updated.
     */
    public static void changeDecorationColor(String newValue)
    {
        System.out.println("fixme: onThemeChange()");
        Configuration.decorationColor = Color.valueOf(newValue);
        if (MainScene.Companion.getMainScene().getWindowScene() != null) { MainScene.Companion.getMainScene().getWindowScene().getWin().setDecorationColor(); }
//        if (SettingsScene.Companion.getSettingsScene() != null) { SettingsScene.Companion.getSettingsScene().getSettingsWindow().getWin().setDecorationColor(); }
//        if (AboutScene.aboutScene != null) { AboutScene.aboutScene.getAboutWindow().getWin().setDecorationColor(); }
//        if (CommandsScene.Companion.getCommandsScene() != null) { CommandsScene.Companion.getCommandsScene().getCommandsWindow().getWin().setDecorationColor(); }
    }

    /**
     * Converts a color code to hex format.
     * Example: 0x1d1d1dff -> #1d1d1d
     */
    public static String getColorHexString(Color color)
    {
        int green = (int) (color.getGreen()*255);
        String greenString = Integer.toHexString(green);
        if (greenString.length() == 1) { greenString += "0"; } //append a '0' if string length is 1

        int red = (int) (color.getRed()*255);
        String redString = Integer.toHexString(red);
        if (redString.length() == 1) { redString += "0"; } //append a '0' if string length is 1

        int blue = (int) (color.getBlue()*255);
        String blueString = Integer.toHexString(blue);
        if (blueString.length() == 1) { blueString += "0"; } //append a '0' if string length is 1

        return "#" + redString + greenString + blueString;
    }
}
