package net.rickiekarp.core;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import net.rickiekarp.core.controller.LanguageController;
import net.rickiekarp.core.debug.DebugHelper;
import net.rickiekarp.core.debug.ExceptionHandler;
import net.rickiekarp.core.settings.Configuration;
import net.rickiekarp.core.settings.LoadSave;
import net.rickiekarp.core.ui.tray.ToolTrayIcon;
import net.rickiekarp.core.ui.windowmanager.ImageLoader;
import net.rickiekarp.core.view.MainScene;
import net.rickiekarp.core.view.MessageDialog;
import net.rickiekarp.core.view.layout.AppLayout;

public class AppStarter extends Application {
    private Class mainClazz;
    private Class configClazz;
    protected boolean isConfigLoaded;
    private byte winType;
    private int minWidth;
    private int minHeight;
    private int width;
    private int height;
    private boolean resizable;

    private static AppLayout node;

    @Override
    public void start(Stage stage) {
        AppContext.create(mainClazz.getPackage().getName());

        //load config file
        Configuration.Companion.setConfig(new Configuration("config.xml", mainClazz));
        isConfigLoaded = Configuration.Companion.getConfig().load();
        if (isConfigLoaded) {
            //load additional application related configuration
            Configuration.Companion.getConfig().loadProperties(configClazz);

            //log properties of current program state0
            DebugHelper.INSTANCE.logProperties();
        } else {
            //if the config file can not be created, set settings anyway
            Configuration.Companion.setLanguage(LoadSave.language);
            LanguageController.setCurrentLocale();
        }

        //load language properties file
        LanguageController.loadLangFile(mainClazz.getClassLoader().getResourceAsStream("language_packs/language_" + Configuration.Companion.getCURRENT_LOCALE() + ".properties"));

        //set the default exception handler
        if (!DebugHelper.INSTANCE.getDEBUGVERSION()) {
            Thread.setDefaultUncaughtExceptionHandler((t, e) -> Platform.runLater(() -> new ExceptionHandler(t, e)));
            Thread.currentThread().setUncaughtExceptionHandler(ExceptionHandler::new);
        }

        //application related configuration
        stage.setTitle(AppContext.getContext().getApplicationName());
        stage.getIcons().add(ImageLoader.INSTANCE.getAppIconSmall());
        stage.setResizable(resizable);
        stage.setMinWidth(minWidth); stage.setMinHeight(minHeight);
        stage.setWidth(width); stage.setHeight(height);

        new MainScene(stage, winType);

        //set up the Client Area to display
        MainScene.Companion.getMainScene().getBorderPane().setCenter(node.getLayout());
        node.postInit();

        //post launch settings
        if (Configuration.Companion.getShowTrayIcon()) {
            new ToolTrayIcon();
        }

        //disable settings view if no config file is present
        if (!isConfigLoaded) {
            new MessageDialog(0, LanguageController.getString("config_not_found"), 500, 250);
            MainScene.Companion.getMainScene().getWindowScene().getWin().getSidebarButtonBox().getChildren().get(0).setDisable(true);
        }
    }

    protected void setMainClazz(Class clazz) {
        mainClazz = clazz;
    }
    protected void setConfigClazz(Class clazz) {
        configClazz = clazz;
    }
    protected void setLayout(AppLayout node) {
        AppStarter.node = node;
    }
    protected void setWinType(byte type) {
        winType = type;
    }
    protected void setMinWidth(final int width) {
        minWidth = width;
    }
    protected void setMinHeight(final int height) {
        minHeight = height;
    }
    protected void setWidth(final int defWidth) {
        width = defWidth;
    }
    protected void setHeight(final int defHeight) {
        height = defHeight;
    }
    protected void setResizable(final boolean isResizable) {
        resizable = isResizable;
    }

}
