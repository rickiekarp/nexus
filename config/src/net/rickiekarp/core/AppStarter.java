package net.rickiekarp.core;

//import daggerok.api.HelloService;
//import net.rickiekarp.core.HelloFactory;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

public class AppStarter extends Application {

    private static Class mainClazz;
    private static Class configClazz;

    private static Node node;

    protected static void setMainClazz(Class mainClazz) {
        AppStarter.mainClazz = mainClazz;
    }

    protected static void setConfigClazz(Class configClazz) {
        AppStarter.configClazz = configClazz;
    }

    public static void setLayout(Node node) {
        AppStarter.node = node;
    }

    @Override
    public void start(Stage stage) {
        Sphere sphere = new Sphere(50);
        Box box = new Box(40, 120, 60);
        PhongMaterial material = new PhongMaterial(Color.BLUE);
        material.setSpecularColor(Color.LIGHTBLUE);
        material.setSpecularPower(10.0d);
        box.setMaterial(material);
        box.setTranslateX(20);
        Group root = new Group(node);
//        root.setTranslateX(320);
//        root.setTranslateY(240);
        Scene scene = new Scene(root, 640, 480);
        stage.setTitle("JavaFX Test " + mainClazz.getSimpleName());
        stage.setScene(scene);
        stage.show();

//        root.getChildren().add(node);



//        AppContext.create("sha1pass");
//
//        //load config file
//        Configuration.config = new Configuration("config.xml", mainClazz);
//        boolean isConfigLoaded = Configuration.config.load();
//        if (isConfigLoaded) {
//            //load additional application related configuration
//            Configuration.config.loadProperties(configClazz);
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
////        stage.getIcons().add(ImageLoader.getAppIconSmall());
//        stage.setResizable(false);
//        stage.setMinWidth(440); stage.setMinHeight(145);
//        stage.setWidth(475); stage.setHeight(205);
//
//        new MainScene(stage, 1);
//
//        //set up the Client Area to display
////        MainScene.mainScene.getBorderPane().setCenter(node);
////        mainLayout.getSentenceMaskTextField().requestFocus();
//
//        //post launch settings
//        if (Configuration.showTrayIcon) {
//            new ToolTrayIcon();
//        }
//
//        //disable settings view if no config file is present
//        if (!isConfigLoaded) {
//            new MessageDialog(0, LanguageController.getString("config_not_found"), 500, 250);
//            MainScene.mainScene.getWindowScene().getWin().getSidebarButtonBox().getChildren().get(0).setDisable(true);
//        }
    }

}
