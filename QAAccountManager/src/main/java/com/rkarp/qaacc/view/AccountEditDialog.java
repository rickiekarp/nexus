package com.rkarp.qaacc.view;

import com.rkarp.appcore.components.textfield.CustomTextField;
import com.rkarp.appcore.controller.LanguageController;
import com.rkarp.appcore.debug.DebugHelper;
import com.rkarp.appcore.debug.ExceptionHandler;
import com.rkarp.appcore.debug.LogFileHandler;
import com.rkarp.appcore.ui.windowmanager.WindowScene;
import com.rkarp.appcore.ui.windowmanager.WindowStage;
import com.rkarp.appcore.util.ImageLoader;
import com.rkarp.appcore.view.MessageDialog;
import com.rkarp.qaacc.factory.AccountXmlFactory;
import com.rkarp.qaacc.model.Account;
import com.rkarp.qaacc.settings.AppConfiguration;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;

public class AccountEditDialog {

    private static AccountEditDialog editDialog;
    private WindowScene accEditScene;

    public static TextField accNameTF;
    public static TextField accMailTF;
    public static CustomTextField accLevelTF;
    public static TextField accAllianceTF;


    AccountEditDialog(int GAME_ID, String sceneType, int selectedIdx, Account selectedItem) {
        AccountEditDialog accEdit = AccountEditDialog.editDialog;
        if (accEdit == null) {
            editDialog = this;
            create(GAME_ID, sceneType, selectedIdx, selectedItem);
        } else {
            if (accEdit.getAccEditScene().getWin().getWindowStage().getStage().isShowing()) {
                accEdit.getAccEditScene().getWin().getWindowStage().getStage().requestFocus();
            } else {
                editDialog = this;
                create(GAME_ID, sceneType, selectedIdx, selectedItem);
            }
        }
    }

    private void create(int GAME_ID, String SceneType, int selectedIdx, Account selectedItem) {
        Stage editStage = new Stage();
        editStage.setWidth(360); editStage.setHeight(380);
        switch (SceneType)
        {
            case "new":
                editStage.setTitle(LanguageController.getString("addAcc"));
                editStage.getIcons().add(ImageLoader.getAppIconSmall());
                break;

            case "edit":
                editStage.setTitle(LanguageController.getString("editAcc"));
                editStage.getIcons().add(ImageLoader.getAppIconSmall());
                break;
        }
        editStage.setResizable(false);

        BorderPane borderpane = new BorderPane();
        Node contentNode = getLayout(GAME_ID, SceneType, selectedIdx, selectedItem);

        // The UI (Client Area) to display
        borderpane.setCenter(contentNode);

        accEditScene = new WindowScene(new WindowStage("edit", editStage), borderpane, 1);

        editStage.setScene(accEditScene);
        editStage.show();

        //if (DebugHelper.isDebugVersion()) { DebugHelper.debugAccEdit(); }
        LogFileHandler.logger.log(Level.INFO, "open.AccountEditDialog{" + SceneType + "," + selectedIdx + "}");
    }


    private Node getLayout(int GAME_ID, String SceneType, int selectedIdx, Account selectedItem) {

        BorderPane mainContent = new BorderPane();
        mainContent.getStyleClass().add("background");

        AnchorPane anchor = new AnchorPane();
        GridPane maingrid = new GridPane();

        ColumnConstraints column1 = new ColumnConstraints(); column1.setPercentWidth(40);
        ColumnConstraints column2 = new ColumnConstraints(); column2.setPercentWidth(60);
        maingrid.getColumnConstraints().addAll(column1, column2);
        maingrid.setVgap(15);

        AnchorPane.setTopAnchor(maingrid, 15.0);
        AnchorPane.setRightAnchor(maingrid, 15.0);
        AnchorPane.setLeftAnchor(maingrid, 15.0);
        AnchorPane.setBottomAnchor(maingrid, 15.0);

        HBox controls = new HBox();

        //add components
        Label accName = new Label(LanguageController.getString("name"));
        GridPane.setConstraints(accName, 0, 0);
        maingrid.getChildren().add(accName);

        accNameTF = new TextField();
        GridPane.setConstraints(accNameTF, 1, 0);
        maingrid.getChildren().add(accNameTF);

        Label accMail = new Label(LanguageController.getString("mail"));
        GridPane.setConstraints(accMail, 0, 1);
        maingrid.getChildren().add(accMail);

        accMailTF = new TextField();
        GridPane.setConstraints(accMailTF, 1, 1);
        maingrid.getChildren().add(accMailTF);

        Label accLevel = new Label(LanguageController.getString("level"));
        GridPane.setConstraints(accLevel, 0, 2);
        maingrid.getChildren().add(accLevel);

        accLevelTF = new CustomTextField();
        accLevelTF.setRestrict("[0-9]");
        GridPane.setConstraints(accLevelTF, 1, 2);
        maingrid.getChildren().add(accLevelTF);

        Label accAlliance = new Label();
        GridPane.setConstraints(accAlliance, 0, 3);
        maingrid.getChildren().add(accAlliance);

        accAllianceTF = new TextField();
        GridPane.setConstraints(accAllianceTF, 1, 3);
        maingrid.getChildren().add(accAllianceTF);


        //controls
        Button saveCfg = new Button();
        controls.getChildren().add(saveCfg);

        controls.setPadding(new Insets(15, 12, 15, 12));  //padding top, left, bottom, right
        controls.setSpacing(10);
        controls.setAlignment(Pos.CENTER_RIGHT);

        switch (SceneType)
        {
            case "new":
                saveCfg.setText(LanguageController.getString("addAcc"));
                saveCfg.setOnAction(event -> {

                    if (accNameTF.getText().isEmpty()) { new MessageDialog(0, LanguageController.getString("enterName"), 350, 220); }
                    else
                    {
                        try {
                            AccountXmlFactory.addAccount(GAME_ID, accNameTF.getText(), accMailTF.getText(), accLevelTF.getText(), accAllianceTF.getText());
                        } catch (TransformerException | ParserConfigurationException | IOException | SAXException e1) {
                            if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
                        }
                        AppConfiguration.accountData.add(new Account(
                                accNameTF.getText(),
                                accMailTF.getText(),
                                accLevelTF.getText(),
                                accAllianceTF.getText()));
                        getAccEditScene().getWin().getWindowStage().getStage().close();
                        AccountOverview.status.setStyle("-fx-text-fill: #55c4fe;"); AccountOverview.status.setText(LanguageController.getString("accAdded"));
                        LogFileHandler.logger.log(Level.INFO, "new account added: " + accNameTF.getText());

                        if (AppConfiguration.accountData.size() == 1) {
                            AccountOverview.accCount.setText("1 " + LanguageController.getString("acc_loaded"));
                            AccountOverview.editAcc.setDisable(false); AccountOverview.delAcc.setDisable(false);
                            AccountOverview.tableview.getSelectionModel().select(0);
                        } else {
                            AccountOverview.accCount.setText(String.valueOf(AppConfiguration.accountData.size()) + " " + LanguageController.getString("accs_loaded"));
                        }
                    }


                });
                break;

            case "edit":
                saveCfg.setText(LanguageController.getString("saveAcc"));
                saveCfg.setOnAction(event -> {
                    try {
                        AccountXmlFactory.saveAccXml(GAME_ID, selectedIdx);
                        getAccEditScene().getWin().getWindowStage().getStage().close();
                    } catch (MalformedURLException e1) {
                        if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
                    }
                    Account.setAccount(selectedItem);
                    AccountOverview.refreshPersonTable(selectedIdx);
                });
                break;
        }

        //set project specific names
        switch (GAME_ID)
        {
            case 2:
                accAlliance.setText(LanguageController.getString("cooperative"));
                break;
            default:
                accAlliance.setText(LanguageController.getString("alliance"));
                break;
        }

        anchor.getChildren().add(maingrid);

        //set borderpane layout
        mainContent.setCenter(anchor);
        mainContent.setBottom(controls);


        accNameTF.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if(newValue.length() > 16)   //maxLength of text field
                    accNameTF.setText(oldValue);
            } catch (Exception e) { accNameTF.setText(oldValue); }
        });

        return mainContent;
    }

    private WindowScene getAccEditScene() {
        return accEditScene;
    }
}
