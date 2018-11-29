package net.rickiekarp.core;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import net.rickiekarp.api.HelloService;
import net.rickiekarp.core.controller.LanguageController;
import net.rickiekarp.core.debug.DebugHelper;
import net.rickiekarp.core.debug.ExceptionHandler;
import net.rickiekarp.core.settings.Configuration;
import net.rickiekarp.core.settings.LoadSave;
import net.rickiekarp.core.ui.tray.ToolTrayIcon;
import net.rickiekarp.core.util.ImageLoader;
import net.rickiekarp.core.view.MainScene;
import net.rickiekarp.core.view.MessageDialog;
import net.rickiekarp.core.view.layout.AppLayout;

public class AppStarter extends Application {
    private static Class mainClazz;
    private static Class configClazz;

    private static AppLayout node;

    protected static void setMainClazz(Class mainClazz) {
        AppStarter.mainClazz = mainClazz;
    }
    protected static void setConfigClazz(Class configClazz) {
        AppStarter.configClazz = configClazz;
    }
    protected static void setLayout(AppLayout node) {
        AppStarter.node = node;
    }

    @Override
    public void start(Stage stage) {
        AppContext.create("sha1pass");

        final HelloService service = HelloFactory.createService();
        System.out.println(service.hi("hello"));

        //load config file
        Configuration.config = new Configuration("config.xml", mainClazz);
        boolean isConfigLoaded = Configuration.config.load();
        if (isConfigLoaded) {
            //load additional application related configuration
            Configuration.config.loadProperties(configClazz);

            //log properties of current program state0
            DebugHelper.logProperties();
        } else {
            //if the config file can not be created, set settings anyway
            Configuration.language = LoadSave.language;
            LanguageController.setCurrentLocale();
        }

        //load language properties file
        LanguageController.loadLangFile(service.getStream("language_packs/language_" + Configuration.CURRENT_LOCALE + ".properties"));

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
        MainScene.mainScene.getBorderPane().setCenter(node.getLayout());
        node.postInit();

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
