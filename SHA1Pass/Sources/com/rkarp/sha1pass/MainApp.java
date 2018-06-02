package com.rkarp.sha1pass;

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
import com.rkarp.sha1pass.settings.AppConfiguration;
import com.rkarp.sha1pass.view.MainLayout;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class MainApp extends Application {

    /**
     * Main Method
     * @param args Program arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        AppContext.create("sha1pass");

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
        stage.getIcons().add(ImageLoader.getAppIconSmall());
        stage.setResizable(false);
        stage.setMinWidth(440); stage.setMinHeight(145);
        stage.setWidth(475); stage.setHeight(205);

        new MainScene(stage, 1);

        //set up the Client Area to display
        MainLayout mainLayout = new MainLayout();
        MainScene.mainScene.getBorderPane().setCenter(mainLayout.getMainLayout());
        mainLayout.getSentenceMaskTextField().requestFocus();

        //post launch settings
        if (Configuration.showTrayIcon) {
            new ToolTrayIcon();
        }

        //disable settings view if no config file is present
        if (!isConfigLoaded) {
            new MessageDialog(0, LanguageController.getString("config_not_found"), 500, 250);
            MainScene.mainScene.getWindowScene().getWin().getSidebarButtonBox().getChildren().get(0).setDisable(true);
        }
    }
}
