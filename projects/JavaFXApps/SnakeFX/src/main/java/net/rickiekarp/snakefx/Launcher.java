package net.rickiekarp.snakefx;

import net.rickiekarp.snakefx.inject.DependencyInjector;
import net.rickiekarp.snakefx.util.FxmlFactory;
import net.rickiekarp.snakefx.util.KeyboardHandler;
import net.rickiekarp.snakefx.util.PopupDialogHelper;
import net.rickiekarp.snakefx.view.FXMLFile;
import net.rickiekarp.snakefx.viewmodel.ViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static void main(final String... args) {
        Application.launch(Launcher.class, args);
    }


    @Override
    public void start(final Stage primaryStage) {
        DependencyInjector dependencyInjector = new DependencyInjector();

        FxmlFactory fxmlFactory = new FxmlFactory(dependencyInjector);
        Scene scene = new Scene(fxmlFactory.getFxmlRoot(FXMLFile.MAIN));
        scene.setOnKeyPressed(dependencyInjector.get(KeyboardHandler.class));


        PopupDialogHelper popupDialogHelper = new PopupDialogHelper(fxmlFactory);


        ViewModel viewModel = dependencyInjector.get(ViewModel.class);

        Stage highScoreStage = popupDialogHelper.createModalDialog(viewModel.highscoreWindowOpen, primaryStage, FXMLFile.HIGHSCORE);

        popupDialogHelper.createModalDialog(viewModel.newHighscoreWindowOpen, highScoreStage, FXMLFile.NEW_HIGHSCORE);


        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
