package com.rkarp.botter;

import com.rkarp.appcore.AppContext;
import com.rkarp.appcore.account.ILoginHandler;
import com.rkarp.appcore.components.button.SidebarButton;
import com.rkarp.appcore.controller.LanguageController;
import com.rkarp.appcore.debug.DebugHelper;
import com.rkarp.appcore.debug.ExceptionHandler;
import com.rkarp.appcore.debug.LogFileHandler;
import com.rkarp.appcore.settings.Configuration;
import com.rkarp.appcore.settings.LoadSave;
import com.rkarp.appcore.ui.tray.ToolTrayIcon;
import com.rkarp.appcore.util.FileUtil;
import com.rkarp.appcore.util.ImageLoader;
import com.rkarp.appcore.view.MainScene;
import com.rkarp.appcore.view.MessageDialog;
import com.rkarp.appcore.view.layout.LoginMaskLayout;
import com.rkarp.botlib.BotConfig;
import com.rkarp.botlib.PluginConfig;
import com.rkarp.botlib.enums.BotPlatforms;
import com.rkarp.botlib.enums.BotType;
import com.rkarp.botlib.model.PluginData;
import com.rkarp.botlib.net.BotNetworkApi;
import com.rkarp.botter.botservice.BotCommands;
import com.rkarp.botter.view.BotSetupLayout;
import com.rkarp.botter.view.MainLayout;
import com.rkarp.botter.view.PluginManagerLayout;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainApp extends Application implements ILoginHandler {

    /**
     * Main Method
     * @param args Program arguments
     */
    public static void main(String[] args) {

        //check if the program was started with a parameter
        if (args.length > 0) {

//            BotLauncher bot;
//            switch (args[0]) {
//                case "0":
//                    bot = new BotLauncher();
//                    // set up bot GiveawayController
//                    new GiveawayController();
//                    GiveawayController.manager.setGiftSite(0);
//                    if (args[1] != null) { GiveawayController.manager.setGiftPointLimit(Integer.parseInt(args[1])); }
//                    if (bot.isValid()) {
//                        bot.launch();
//                    }
//                    break;
//                case "1":
//                    bot = new BotLauncher();
//                    // set up bot GiveawayController
//                    new GiveawayController();
//                    GiveawayController.manager.setGiftSite(1);
//                    if (args[1] != null) { GiveawayController.manager.setGiftPointLimit(Integer.parseInt(args[1])); }
//                    if (bot.isValid()) {
//                        bot.launch();
//                    }
//                    break;
//                case "2":
//                    bot = new BotLauncher();
//                    // set up bot GiveawayController
//                    new GiveawayController();
//                    GiveawayController.manager.setGiftSite(2);
//                    if (args[1] != null) { GiveawayController.manager.setGiftPointLimit(Integer.parseInt(args[1])); }
//                    if (bot.isValid()) {
//                        bot.launch();
//                    }
//                    break;
//                case "help": printHelp(); System.exit(0); break;
//                default: System.out.println("Invalid command!"); printHelp(); System.exit(0);
//            }
        } else {
            launch(args);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        AppContext.create("botmanager", new BotNetworkApi());

        //load config file
        Configuration.config = new Configuration("config.xml", MainApp.class);
        boolean isConfigLoaded = Configuration.config.load();
        if (isConfigLoaded) {
            //load additional application related configuration
            Configuration.config.loadProperties(PluginConfig.class);
            Configuration.config.loadProperties(BotConfig.class);

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
        stage.setMinWidth(730); stage.setMinHeight(350);
        stage.setWidth(900); stage.setHeight(600);
        stage.getIcons().add(ImageLoader.getAppIconSmall());

        //create and show the main scene
        new MainScene(stage);

        AppContext.getContext().initAccountManager();

        LoginMaskLayout loginMaskLayout = new LoginMaskLayout();
        setAppContextLoginBehaviour(loginMaskLayout);
        MainScene.mainScene.getSceneViewStack().push(loginMaskLayout.getMaskNode());

        //auto login
        if (AppContext.getContext().getAccountManager().isAutoLogin()) {
            MainScene.mainScene.getBorderPane().setCenter(loginMaskLayout.getMaskNode());
            new Thread(loginMaskLayout.getLoginTask()).start();
        }

        new BotCommands().addBotCommands();

        //post launch settings
        if (Configuration.showTrayIcon) {
            new ToolTrayIcon();
        }

        //disable settings and bot setup views if no config file is present
        if (!isConfigLoaded) {
            new MessageDialog(0, LanguageController.getString("config_not_found"), 500, 250);
            MainScene.mainScene.getWindowScene().getWin().getSidebarButtonBox().getChildren().get(0).setDisable(true);
            MainScene.mainScene.getWindowScene().getWin().getSidebarButtonBox().getChildren().get(1).setDisable(true);
        }
    }

    private void loadLocalPlugins() throws IOException {
        if (Configuration.config.getPluginDirFile().exists()) {
            File[] pluginFileList = FileUtil.getListOfFiles(Configuration.config.getPluginDirFile());
            List<String> valueList; //TODO: Use HashMap instead
            for (File file : pluginFileList) {
                if (file.getName().endsWith(".jar")) {
                    try {
                        valueList = FileUtil.readManifestPropertiesFromJar(file.getPath(), "Main-Class", "Version", "Type");
                        PluginData data = new PluginData(
                                valueList.get(0),
                                file.getName().substring(0, file.getName().length() - 4),
                                valueList.get(1),
                                null,
                                BotPlatforms.valueOf(valueList.get(2))
                        );

                        if (data.getPluginType() == BotPlatforms.ANDROID) {
                            loadAndroidAttributes(file, data);
                        }

                        PluginData.pluginData.add(data);
                    } catch (IOException e) {
                        LogFileHandler.logger.warning("Plugin: " + file.getName() + " could not be loaded!");
                    }
                }
            }
        }
    }

    /**
     * Defines additional behaviour for the current AppContext that gets executed
     * when logging in successfully
     * @param loginMaskLayout Login Mask layout
     */
    @Override
    public void setAppContextLoginBehaviour(LoginMaskLayout loginMaskLayout) {
        loginMaskLayout.getLoginTask().setOnSucceeded(t -> {
            // This handler will be called if Task succesfully executed login code
            // disregarding result of login operation

            // and here we act according to result of login code
            if (loginMaskLayout.getLoginTask().getValue()) {
                loginMaskLayout.addAccountMenu(this);

                //set up the Client Area to display
                if (isValidConfig()) {
                    MainScene.mainScene.getBorderPane().setTop(MainLayout.getInstance().getLaunchNode());
                    MainScene.mainScene.getBorderPane().setCenter(null);
                } else {
                    MainScene.mainScene.getBorderPane().setCenter(new BotSetupLayout().getLayout());
                }

                //load plugins
                try {
                    loadLocalPlugins();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //add additional buttons in the sidebar
                addSidebarButtons(MainScene.mainScene.getLayoutRegion().getHeight());

            } else {
                final Task<Boolean> retryTask = loginMaskLayout.doLogin();
                loginMaskLayout.setLoginTask(retryTask);
                setAppContextLoginBehaviour(loginMaskLayout);
            }
        });
    }

    @Override
    public void setOnLogout() {
        MainLayout.getInstance().clearModules();
    }

    private void loadAndroidAttributes(File file, PluginData data) throws IOException {
        List<String> valueList = FileUtil.readManifestPropertiesFromJar(file.getPath(), "Package", "Activity");
        data.setPluginPackage(valueList.get(0));
        data.setPluginActvity(valueList.get(1));
    }

    /**
     * Evaluates the current WebDriver configuration
     * @return Status of the configuration where true = good, false = needs setup
     */
    private boolean isValidConfig() {
        return BotConfig.nodeBinary != null && PluginConfig.browserProfileName != null && !(PluginConfig.botType == BotType.Bot.CHROME && PluginConfig.chromeConfigDirectory == null);
    }

    /**
     * Adds additional buttons to the sidebar
     * @param stageHeight Height of the stage
     */
    private void addSidebarButtons(double stageHeight) {
        SidebarButton pluginButton = new SidebarButton("pluginmanager");
        pluginButton.setOnAction(event -> {
            new PluginManagerLayout();
            MainScene.mainScene.getWindowScene().getWin().toggleSideBar();
        });

        MainScene.mainScene.getWindowScene().getWin().getContentController().addSidebarItem(1, pluginButton);
        MainScene.mainScene.getWindowScene().getWin().calcSidebarButtonSize(stageHeight);
    }
}
