package com.rkarp.appupdater.ui;

import com.rkarp.appupdater.UpdateMain;
import com.rkarp.appupdater.settings.UpdateConstants;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class UpdateCheckerGUI extends Stage {

    public static UpdateCheckerGUI updateChecker;
    private TextArea textArea;
    private Button btnInstall;

    public UpdateCheckerGUI() {

        initComponents();

        //readManifestVersion();

        //checkForUpdate();
    }
    private void initComponents() {

        Stage modalDialog = new Stage();
        modalDialog.setWidth(400); modalDialog.setHeight(300);
        modalDialog.setTitle("Updater (" + UpdateMain.readManifestProperty("Version") + ")");

        BorderPane borderpane = new BorderPane();
        borderpane.setPadding(new Insets(10, 10, 10, 10));

        AnchorPane options = new AnchorPane();
        options.setMinHeight(50);

        HBox optionHBox = new HBox();

        //components
        textArea = new TextArea();
        textArea.setEditable(false);

        btnInstall = new Button("Install!");
        btnInstall.setDisable(true);
        btnInstall.setMinSize(100, 30);

        optionHBox.getChildren().addAll(btnInstall);

        //ActionListener
        btnInstall.setOnAction(event -> {
            textArea.appendText("Installing update! Please wait...\n");
        });

        // The UI (Client Area) to display
        options.getChildren().addAll(optionHBox);

        AnchorPane.setRightAnchor(optionHBox, 5.0);
        AnchorPane.setBottomAnchor(optionHBox, 5.0);

        borderpane.setCenter(textArea);
        borderpane.setBottom(options);

        Scene modalDialogScene = new Scene(borderpane);

        modalDialog.setScene(modalDialogScene);
        modalDialog.show();
    }

    public void setMessage(String msg) {
        textArea.setText(msg);
    }

    public void appendMessage(String msg) {
        textArea.appendText("\n" + msg);
    }

    public String getMessage() {
        return textArea.getText();
    }

    private void readManifestVersion() {

        String version;

        //read version from main jar manifest
        try {
            Manifest manifest = new JarFile(UpdateMain.getArgs()[1]).getManifest();
            Attributes attributes = manifest.getMainAttributes();
            version = attributes.getValue("Version");
        } catch (IOException e) {
            System.out.println("Error while reading version: " + e.getMessage());
            version = "DEV";
        }

        textArea.appendText("Current program version: " + version + "\n");

    }

    private void checkForUpdate() {


        textArea.appendText("Contacting download server...\n");







        textArea.appendText("Success\n");


        btnInstall.setDisable(false);

    }
}