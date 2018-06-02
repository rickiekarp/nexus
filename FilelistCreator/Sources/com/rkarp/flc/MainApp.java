package com.rkarp.flc;

import com.rkarp.appcore.AppContext;
import com.rkarp.appcore.controller.LanguageController;
import com.rkarp.appcore.debug.DebugHelper;
import com.rkarp.appcore.debug.ExceptionHandler;
import com.rkarp.appcore.settings.Configuration;
import com.rkarp.appcore.settings.LoadSave;
import com.rkarp.appcore.ui.tray.ToolTrayIcon;
import com.rkarp.appcore.util.ImageLoader;
import com.rkarp.appcore.view.MainScene;
import com.rkarp.appcore.view.MessageDialog;
import com.rkarp.flc.controller.FileCommands;
import com.rkarp.flc.settings.AppConfiguration;
import com.rkarp.flc.view.layout.MainLayout;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        AppContext.create("filelistcreator");

        //load config file
        Configuration.config = new Configuration("config.xml", MainApp.class);
        boolean isConfigLoaded = Configuration.config.load();
        if (isConfigLoaded) {
            //load additional application related configuration
            Configuration.config.loadProperties(AppConfiguration.class);

            //log properties of current program state0
            DebugHelper.logProperties();
        } else {
            //if the config file can not be created, set settings anyway
            Configuration.language = LoadSave.language;
            LanguageController.setCurrentLocale();
        }

        //load language properties file
        LanguageController.loadLangFile();

        //set the default exception handler
        if (!DebugHelper.DEBUGVERSION) {
            Thread.setDefaultUncaughtExceptionHandler((t, e) -> Platform.runLater(() -> new ExceptionHandler(t, e)));
            Thread.currentThread().setUncaughtExceptionHandler(ExceptionHandler::new);
        }

        //application related configuration
        stage.setTitle(AppContext.getContext().getApplicationName());
        stage.setMinWidth(800); stage.setMinHeight(550);
        stage.setWidth(900); stage.setHeight(600);
        stage.getIcons().add(ImageLoader.getAppIconSmall());

        //create and show the main scene
        new MainScene(stage);

        //set up the Client Area to display
        MainScene.mainScene.getBorderPane().setCenter(new MainLayout().getMainLayout());

        //post launch settings
        if (Configuration.showTrayIcon) {
            new ToolTrayIcon();
        }

        //disable settings view if no config file is present
        if (!isConfigLoaded) {
            new MessageDialog(0, LanguageController.getString("config_not_found"), 500, 250);
            MainScene.mainScene.getWindowScene().getWin().getSidebarButtonBox().getChildren().get(0).setDisable(true);
        }

        new FileCommands().addFileCommands();
    }
}