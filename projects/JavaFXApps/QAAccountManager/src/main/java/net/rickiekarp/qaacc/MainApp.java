package net.rickiekarp.qaacc;

import javafx.stage.Stage;
import net.rickiekarp.core.AppStarter;
import net.rickiekarp.core.controller.AppLaunch;
import net.rickiekarp.qaacc.settings.AppConfiguration;
import net.rickiekarp.qaacc.view.MainLayout;

public class MainApp extends AppStarter implements AppLaunch {

    /**
     * Main Method
     * @param args Program arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        setMainClazz(MainApp.class);
        setConfigClazz(AppConfiguration.class);

        setWinType((byte) 1);
        setMinWidth(500);
        setMinHeight(300);
        setWidth(750);
        setHeight(335);
        setLayout(new MainLayout());

        super.start(stage);
        postLaunch();
    }

    @Override
    public void postLaunch() {

    }

//    public void start(Stage stage) throws Exception {
//        AppContext.create("qaaccountmanager");
//
//        //load config file
//        Configuration.config = new Configuration("config.xml", MainApp.class);
//        boolean isConfigLoaded = Configuration.config.load();
//        if (isConfigLoaded) {
//            //load additional application related configuration
//            Configuration.config.loadProperties(AppConfiguration.class);
//
//            ProjectXmlFactory.getSettings();
//
//            //log properties of current program state0
//            DebugHelper.logProperties();
//        } else {
//            //if the config file can not be created, set settings anyway
//            Configuration.language = LoadSave.language;
//            LanguageController.setCurrentLocale();
//        }
//
//        //load language properties file
//        LanguageController.loadLangFile();
//
//        //set the default exception handler
//        if (!DebugHelper.DEBUGVERSION) {
//            Thread.setDefaultUncaughtExceptionHandler((t, e) -> Platform.runLater(() -> new ExceptionHandler(t, e)));
//            Thread.currentThread().setUncaughtExceptionHandler(ExceptionHandler::new);
//        }
//
//        //application related configuration
//        stage.setTitle(AppContext.getContext().getApplicationName());
//        stage.setMinWidth(500); stage.setMinHeight(300);
//        stage.setWidth(750); stage.setHeight(335);
//        stage.setResizable(false);
//        stage.getIcons().add(ImageLoader.getAppIconSmall());
//
//        //create and show the main scene
//        new MainScene(stage);
//
//        //set up the Client Area to display
//        MainScene.mainScene.getBorderPane().setCenter(new MainLayout().getMainLayout());
//
//
//        //post launch settings
//        MainLayout.mainLayout.fillComboBox();
//
//        //add additional buttons in the sidebar
//        SidebarButton button1 = new SidebarButton("bugreport");
//        button1.setOnAction(event -> {
//            new BugReportSettings(AppConfiguration.pjState);
//            MainScene.mainScene.getWindowScene().getWin().toggleSideBar();
//        });
//
//        SidebarButton button2 = new SidebarButton("template_copy");
//        button2.setOnAction(event -> {
//            BugReportSettings.copyTemplate();
//            MainScene.mainScene.getWindowScene().getWin().toggleSideBar();
//        });
//
//        MainScene.mainScene.getWindowScene().getWin().getSidebarButtonBox().getChildren().add(1, button1);
//        MainScene.mainScene.getWindowScene().getWin().getSidebarButtonBox().getChildren().add(2, button2);
//        MainScene.mainScene.getWindowScene().getWin().calcSidebarButtonSize(stage.getHeight());
//
//        //add system tray icon
//        if (Configuration.showTrayIcon) {
//            new ToolTrayIcon();
//        }
//
//        //disable settings view if no config file is present
//        if (!isConfigLoaded) {
//            new MessageDialog(0, LanguageController.getString("config_not_found"), 500, 250);
//            MainScene.mainScene.getWindowScene().getWin().getSidebarButtonBox().getChildren().get(0).setDisable(true);
//            MainScene.mainScene.getWindowScene().getWin().getSidebarButtonBox().getChildren().get(1).setDisable(true);
//            MainScene.mainScene.getWindowScene().getWin().getSidebarButtonBox().getChildren().get(2).setDisable(true);
//        }
//
//        new MyCommands().addCommands();
//    }
}