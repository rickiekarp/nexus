package com.rkarp.appcore.view;

import com.rkarp.appcore.settings.AppCommands;
import com.rkarp.appcore.ui.windowmanager.WindowScene;
import com.rkarp.appcore.ui.windowmanager.WindowStack;
import com.rkarp.appcore.ui.windowmanager.WindowStage;
import com.rkarp.appcore.ui.windowmanager.WindowStageStack;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class MainScene {
    public static WindowStageStack stageStack;
    public static MainScene mainScene;
    private WindowScene windowScene;
    public WindowScene getWindowScene() {
        return windowScene;
    }
    private Region layoutRegion;
    public Region getLayoutRegion() {
        return layoutRegion;
    }
    public BorderPane getBorderPane() {
        return (BorderPane) layoutRegion;
    }

    public MainScene(Stage stage) {
        createScene(stage, 0);
    }

    public MainScene(Stage stage, int winType) {
        createScene(stage, winType);
    }

    private void createScene(Stage stage, int winType) {
        WindowStage mainStage = new WindowStage("main", stage);
        mainScene = this;
        layoutRegion = new BorderPane();
        windowScene = new WindowScene(mainStage, layoutRegion, winType);
        stage.setScene(windowScene);
        stage.show();

        switch (winType) {
            case 0:
                windowScene.getWin().calcSidebarButtonSize(stage.getHeight());

                //add available commands to a list
                try {
                    new AppCommands().fillCommandsList();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                break;
        }

        stageStack = new WindowStageStack();
        stageStack.push(mainStage);
    }

    public WindowStack getSceneViewStack() {
        return stageStack.getSceneViewStack();
    }
}
