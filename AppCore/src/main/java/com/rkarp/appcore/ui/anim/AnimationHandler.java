package com.rkarp.appcore.ui.anim;

import com.rkarp.appcore.settings.Configuration;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *
 */
public class AnimationHandler {

    public static DoubleProperty xPosMenu;
    private static final Duration SLIDE_DURATION = Duration.seconds(0.2);

    public static Animation menuBtnAnim;
    public static Timeline slideIn;
    public static Timeline slideOut;
    public static FadeTransition stackFadeIn;
    public static FadeTransition stackFadeOut;

    public static final int OFFSET_X =  0;
    public static final int OFFSET_Y =  0;
    public static final int WIDTH    = 39;
    public static double menuWidth;

    /**
     *  Creates slideIn / slideOut timeline to slide the node content in and out
     *  @param stageWidth Width of the stage
     **/
    public static void addSlideHandlers(double stageWidth) {
        menuWidth = stageWidth * 0.35;
        xPosMenu = new SimpleDoubleProperty(-menuWidth);
        slideIn = new Timeline(new KeyFrame(SLIDE_DURATION, new KeyValue(xPosMenu, 0)));
        slideOut = new Timeline(new KeyFrame(SLIDE_DURATION, new KeyValue(xPosMenu,  -menuWidth)));
    }

    /**
     *  Starts the menu button animation
     *  @param imageView Animated ImageView
     **/
    public static void createMenuBtnAnim(ImageView imageView, int HEIGHT) {
        menuBtnAnim = new SpriteAnimation(
                imageView, Duration.millis(1000),
                42, 7,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        menuBtnAnim.setCycleCount(1);
    }

    /**
     * Translation effect in the settings stage
     * @param pane Pane which is translated
     * @param duration The duration of the translate effect
     * @param startVal The X position before the TranslateTransition
     * @param endVal The X position after the TranslateTransition
     */
    public static void translate(Object pane, int duration, double startVal, double endVal) {
        if (pane instanceof Node) {
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(duration), (Node) pane);
            translateTransition.setFromX(startVal);
            translateTransition.setToX(endVal);
            translateTransition.setCycleCount(1);
            translateTransition.setAutoReverse(true);
            translateTransition.play();
        }
    }

    /**
     * Fading effect.
     * @param pane Pane which is faded in/out
     * @param duration The duration of the fade effect
     * @param startVal The opacity value before the FadeTransition
     * @param endVal The final opacity value after the FadeTransition
     */
    public static FadeTransition fade(Object pane, int duration, double startVal, double endVal) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(duration), (Node) pane);
        fadeTransition.setFromValue(startVal);
        fadeTransition.setToValue(endVal);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(true);
        return fadeTransition;
    }

    /**
     * Fading effect of the status label
     * @param label The affected label
     * @param type Type of the status (success|fail|neutral)
     * @param text Status text
     */
    public static void statusFade(Label label, String type, String text) {
        if (Configuration.animations) {
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(700), label);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            switch (type) {
                case "success":
                    label.setStyle("-fx-text-fill: #55c4fe; -fx-opacity: 0;");
                    fadeTransition.setCycleCount(1);
                    break;
                case "fail":
                    label.setStyle("-fx-text-fill: red; -fx-opacity: 0;" );
                    fadeTransition.setCycleCount(1);
                    break;
                case "neutral":
                    label.setStyle("-fx-text-fill: white; -fx-opacity: 0;");
                    fadeTransition.setCycleCount(1);
                    break;
            }
            fadeTransition.setAutoReverse(true);
            fadeTransition.stop();
            fadeTransition.play();
        } else {
            switch (type) {
                case "success":
                    label.setStyle("-fx-text-fill: #55c4fe; -fx-opacity: 1;");
                    break;
                case "fail":
                    label.setStyle("-fx-text-fill: red; -fx-opacity: 1;" );
                    break;
                case "neutral":
                    label.setStyle("-fx-text-fill: white; -fx-opacity: 1;" );
                    break;
            }
        }
        label.setText(text);
    }
}
