package com.rkarp.appcore.view;

import com.rkarp.appcore.controller.LanguageController;
import com.rkarp.appcore.debug.DebugHelper;
import com.rkarp.appcore.debug.ExceptionHandler;
import com.rkarp.appcore.debug.LogFileHandler;
import com.rkarp.appcore.net.update.UpdateChecker;
import com.rkarp.appcore.settings.Configuration;
import com.rkarp.appcore.ui.windowmanager.WindowScene;
import com.rkarp.appcore.ui.windowmanager.WindowStage;
import com.rkarp.appcore.util.ImageLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * This class is used for creating different message dialogs.
 * Example: Error Message
 */
public class MessageDialog {

    public MessageDialog(int type, String msg, int width, int height) {
        switch (type) {
            case 0: createDialog("error", msg, width, height); break;
            case 1: createDialog("info", msg, width, height); break;
        }
    }

    private static void createDialog(String title, String msg, int width, int height) {
        Stage modalDialog = new Stage();
        modalDialog.getIcons().add(ImageLoader.getAppIconSmall());
        modalDialog.initModality(Modality.APPLICATION_MODAL);
        modalDialog.setResizable(false);
        modalDialog.setWidth(width); modalDialog.setHeight(height);
        modalDialog.setTitle(LanguageController.getString(title));

        //Layout
        BorderPane contentPane = new BorderPane();

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20,0,0,20));

        HBox controls = new HBox();
        controls.setPadding(new Insets(10, 0, 10, 0));  //padding top, left, bottom, right
        controls.setAlignment(Pos.CENTER);

        //Components
        Label label = new Label(msg);
        vbox.getChildren().addAll(label);
        label.setWrapText(true);

        Button okButton = new Button("OK");
        okButton.setOnAction(arg0 -> modalDialog.close());
        controls.getChildren().add(okButton);

        // The UI (Client Area) to display
        contentPane.setCenter(vbox);
        contentPane.setBottom(controls);

        WindowScene modalDialogScene = new WindowScene(new WindowStage("message", modalDialog), contentPane, 1);

        modalDialog.setScene(modalDialogScene);
        modalDialog.show();

        LogFileHandler.logger.info("open.MessageDialog(" + title + ")");
    }

    static boolean confirmDialog(String msg, int width, int height) {
        Stage modalDialog = new Stage();
        modalDialog.getIcons().add(ImageLoader.getAppIconSmall());
        modalDialog.initModality(Modality.APPLICATION_MODAL);
        modalDialog.setResizable(false);
        modalDialog.setWidth(width); modalDialog.setHeight(height);
        modalDialog.setTitle(LanguageController.getString("confirm"));

        final boolean[] bool = new boolean[1];

        BorderPane borderpane = new BorderPane();

        VBox contentVbox = new VBox();
        contentVbox.setSpacing(20);

        HBox optionHBox = new HBox();
        optionHBox.setSpacing(20);
        optionHBox.setAlignment(Pos.CENTER);
        optionHBox.setPadding(new Insets(5, 0, 15, 0));

        //components
        Label label = new Label(LanguageController.getString(msg));
        label.setWrapText(true);
        label.setPadding(new Insets(20, 10, 10, 20));

        Button yesButton = new Button(LanguageController.getString("yes"));
        yesButton.setOnAction(event -> {
            bool[0] = true;
            modalDialog.close();
        });

        Button noButton = new Button(LanguageController.getString("no"));
        noButton.setOnAction(event -> {
            bool[0] = false;
            modalDialog.close();
        });

        if (DebugHelper.isDebugVersion()) {
            contentVbox.setStyle("-fx-background-color: gray");
            optionHBox.setStyle("-fx-background-color: #444444;");
        }

        optionHBox.getChildren().addAll(yesButton, noButton);

        // The UI (Client Area) to display
        contentVbox.getChildren().addAll(label, optionHBox);
        VBox.setVgrow(contentVbox, Priority.ALWAYS);

        borderpane.setCenter(contentVbox);
        borderpane.setBottom(optionHBox);

        // The Window as a Scene
        WindowScene modalDialogScene = new WindowScene(new WindowStage("confirm", modalDialog), borderpane, 1);

        modalDialog.setScene(modalDialogScene);

        LogFileHandler.logger.info("open.confirmDialog");

        modalDialog.showAndWait();

        return bool[0];
    }

    static boolean restartDialog(String msg, int width, int height) {
        Stage modalDialog = new Stage();
        modalDialog.getIcons().add(ImageLoader.getAppIconSmall());
        modalDialog.initModality(Modality.APPLICATION_MODAL);
        modalDialog.setResizable(false);
        modalDialog.setWidth(width); modalDialog.setHeight(height);
        modalDialog.setTitle(LanguageController.getString("restartApp"));

        final boolean[] bool = new boolean[1];

        BorderPane borderpane = new BorderPane();

        VBox contentVbox = new VBox();
        contentVbox.setSpacing(20);

        HBox optionHBox = new HBox();
        optionHBox.setSpacing(20);
        optionHBox.setAlignment(Pos.CENTER);
        optionHBox.setPadding(new Insets(5, 0, 15, 0));

        //components
        Label label = new Label(LanguageController.getString(msg));
        label.setWrapText(true);
        label.setPadding(new Insets(20, 10, 10, 20));

        Button yesButton = new Button(LanguageController.getString("yes"));
        yesButton.setOnAction(event -> {
            try {
                //save settings
                try {
                    Configuration.config.save();
                } catch (Exception e1) {
                    if (DebugHelper.DEBUGVERSION) {
                        e1.printStackTrace();
                    } else {
                        new ExceptionHandler(Thread.currentThread(), e1);
                    }
                }

                //restart
                DebugHelper.restartApplication();
            } catch (URISyntaxException | IOException e1) {
                if (DebugHelper.DEBUGVERSION) {
                    e1.printStackTrace();
                } else {
                    new ExceptionHandler(Thread.currentThread(), e1);
                }
            }
        });

        Button noButton = new Button(LanguageController.getString("restartLater"));
        noButton.setOnAction(event -> {
            bool[0] = false;
            modalDialog.close();
        });

        if (DebugHelper.isDebugVersion()) {
            contentVbox.setStyle("-fx-background-color: gray");
            optionHBox.setStyle("-fx-background-color: #444444;");
        }

        optionHBox.getChildren().addAll(yesButton, noButton);

        // The UI (Client Area) to display
        contentVbox.getChildren().addAll(label, optionHBox);
        VBox.setVgrow(contentVbox, Priority.ALWAYS);

        borderpane.setCenter(contentVbox);
        borderpane.setBottom(optionHBox);

        // The Window as a Scene
        WindowScene modalDialogScene = new WindowScene(new WindowStage("restart", modalDialog), borderpane, 1);

        modalDialog.setScene(modalDialogScene);

        LogFileHandler.logger.info("open.errorMessageDialog");

        modalDialog.showAndWait();

        return bool[0];
    }

    static Stage installUpdateDialog(String msg, int width, int height) {
        Stage modalDialog = new Stage();
        modalDialog.getIcons().add(ImageLoader.getAppIconSmall());
        modalDialog.initModality(Modality.APPLICATION_MODAL);
        modalDialog.setResizable(false);
        modalDialog.setWidth(width); modalDialog.setHeight(height);
        modalDialog.setTitle(LanguageController.getString("installUpdate"));

        BorderPane borderpane = new BorderPane();

        VBox contentVbox = new VBox();
        contentVbox.setSpacing(20);

        AnchorPane options = new AnchorPane();
        options.setMinHeight(50);

        HBox optionHBox = new HBox();
        optionHBox.setSpacing(10);

        //components
        Label label = new Label(LanguageController.getString("update_desc"));
        label.setWrapText(true);
        label.setPadding(new Insets(20, 10, 10, 20));

        CheckBox remember = new CheckBox(LanguageController.getString("hideThis"));
        remember.setDisable(true);
        remember.setOnAction(event1 -> {
            System.out.println(remember.isSelected());
        });

        Button yesButton = new Button(LanguageController.getString("yes"));
        yesButton.setOnAction(event -> {
            try {
                //remove tray icon before installing update
//                if (ToolTrayIcon.icon != null ) {
//                    if (ToolTrayIcon.icon.getSystemTray().getTrayIcons().length > 0) {
//                        ToolTrayIcon.icon.removeTrayIcon();
//                    }
//                }
                UpdateChecker.installUpdate();
            } catch (URISyntaxException | IOException e1) {
                if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
            }
        });

        Button noButton = new Button(LanguageController.getString("no"));
        noButton.setOnAction(event -> {
            modalDialog.close();
        });

//        if (DebugHelper.isDebugVersion()) {
//            contentVbox.setStyle("-fx-background-color: gray");
//            optionHBox.setStyle("-fx-background-color: #444444;");
//        }

        optionHBox.getChildren().addAll(yesButton, noButton);

        // The UI (Client Area) to display
        contentVbox.getChildren().addAll(label);
        options.getChildren().addAll(remember, optionHBox);

        AnchorPane.setRightAnchor(optionHBox, 5.0);
        AnchorPane.setBottomAnchor(optionHBox, 5.0);
        AnchorPane.setLeftAnchor(remember, 10.0);
        AnchorPane.setBottomAnchor(remember, 10.0);



        borderpane.setCenter(contentVbox);
        borderpane.setBottom(options);

        // The Window as a Scene
        WindowScene modalDialogScene = new WindowScene(new WindowStage("installUpdate", modalDialog), borderpane, 1);


        modalDialog.setScene(modalDialogScene);

        LogFileHandler.logger.info("open.installUpdateDialog");

        return modalDialog;
    }
}
