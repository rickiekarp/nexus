package com.rkarp.appcore.ui.windowmanager;

import com.rkarp.appcore.debug.DebugHelper;
import com.rkarp.appcore.settings.Configuration;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

public class WindowScene extends Scene {

    private Window window;
    public Window getWin() {
        return window;
    }

    /**
     * Basic constructor with built-in behavior
     * @param stage The main stage
     * @param root your UI to be displayed in the Stage
     */
    public WindowScene(WindowStage stage, Region root, int winType) {
        this(stage, StageStyle.TRANSPARENT, root, winType);
    }

    /**
     * WindowScene constructor
     * @param stage The main stage
     * @param stageStyle could be StageStyle.UTILITY or StageStyle.TRANSPARENT
     * @param root your UI to be displayed in the Stage
     * @param winType The type of the window decoration (0: with Sidebar / 1: without Sidebar)
     */
    private WindowScene(WindowStage stage, StageStyle stageStyle, Region root, int winType) {

        super(root);

        //add custom button style for settings/about scene button
        root.getStylesheets().add("ui/components/button/ButtonStyle.css");

        // behaviour when using system borders instead of custom implementation
        if(Configuration.useSystemBorders) {
            root.setPrefHeight(stage.getStage().getHeight());
            root.setPrefWidth(stage.getStage().getWidth());

            //add custom button style for settings/about scene button
//            root.getStylesheets().add("com/rkarp/resources/ui/components/button/ButtonStyle.css");
        } else {
            window = new Window(stage, root, stageStyle, winType);
            super.setRoot(window);

            // Transparent scene and stage
            stage.getStage().initStyle(stageStyle);

            if (DebugHelper.DEBUGVERSION) {
                super.setFill(Color.SLATEGRAY);
            } else {
                super.setFill(Color.TRANSPARENT);
            }

            // Default Accelerators
            window.installAccelerators(this, winType);
        }

        ThemeSelector.setTheme(this);
    }
}
