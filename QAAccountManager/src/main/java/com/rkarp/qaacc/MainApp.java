package com.rkarp.qaacc;

import com.rkarp.appcore.AppContext;
import com.rkarp.appcore.components.button.SidebarButton;
import com.rkarp.appcore.controller.LanguageController;
import com.rkarp.appcore.debug.DebugHelper;
import com.rkarp.appcore.debug.ExceptionHandler;
import com.rkarp.appcore.settings.Configuration;
import com.rkarp.appcore.settings.LoadSave;
import com.rkarp.appcore.ui.tray.ToolTrayIcon;
import com.rkarp.appcore.util.ImageLoader;
import com.rkarp.appcore.view.MainScene;
import com.rkarp.appcore.view.MessageDialog;
import com.rkarp.qaacc.factory.ProjectXmlFactory;
import com.rkarp.qaacc.settings.AppConfiguration;
import com.rkarp.qaacc.settings.MyCommands;
import com.rkarp.qaacc.view.BugReportSettings;
import com.rkarp.qaacc.view.MainLayout;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        AppContext.create("qaaccountmanager");

        //load config file
        Configuration.config = new Configuration("config.xml", MainApp.class);
        boolean isConfigLoaded = Configuration.config.load();
        if (isConfigLoaded) {
            //load additional application related configuration
            Configuration.config.loadProperties(AppConfiguration.class);

            ProjectXmlFactory.getSettings();

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
        stage.setMinWidth(500); stage.setMinHeight(300);
        stage.setWidth(750); stage.setHeight(335);
        stage.setResizable(false);
        stage.getIcons().add(ImageLoader.getAppIconSmall());

        //create and show the main scene
        new MainScene(stage);

        //set up the Client Area to display
        MainScene.mainScene.getBorderPane().setCenter(new MainLayout().getMainLayout());


        //post launch settings
        MainLayout.mainLayout.fillComboBox();

        //add additional buttons in the sidebar
        SidebarButton button1 = new SidebarButton("bugreport");
        button1.setOnAction(event -> {
            new BugReportSettings(AppConfiguration.pjState);
            MainScene.mainScene.getWindowScene().getWin().toggleSideBar();
        });

        SidebarButton button2 = new SidebarButton("template_copy");
        button2.setOnAction(event -> {
            BugReportSettings.copyTemplate();
            MainScene.mainScene.getWindowScene().getWin().toggleSideBar();
        });

        MainScene.mainScene.getWindowScene().getWin().getSidebarButtonBox().getChildren().add(1, button1);
        MainScene.mainScene.getWindowScene().getWin().getSidebarButtonBox().getChildren().add(2, button2);
        MainScene.mainScene.getWindowScene().getWin().calcSidebarButtonSize(stage.getHeight());

        //add system tray icon
        if (Configuration.showTrayIcon) {
            new ToolTrayIcon();
        }

        //disable settings view if no config file is present
        if (!isConfigLoaded) {
            new MessageDialog(0, LanguageController.getString("config_not_found"), 500, 250);
            MainScene.mainScene.getWindowScene().getWin().getSidebarButtonBox().getChildren().get(0).setDisable(true);
            MainScene.mainScene.getWindowScene().getWin().getSidebarButtonBox().getChildren().get(1).setDisable(true);
            MainScene.mainScene.getWindowScene().getWin().getSidebarButtonBox().getChildren().get(2).setDisable(true);
        }

        new MyCommands().addCommands();
    }
}