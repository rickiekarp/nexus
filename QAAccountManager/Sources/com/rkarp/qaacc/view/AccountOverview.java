package com.rkarp.qaacc.view;

import com.rkarp.appcore.controller.LanguageController;
import com.rkarp.appcore.debug.DebugHelper;
import com.rkarp.appcore.debug.ExceptionHandler;
import com.rkarp.appcore.debug.LogFileHandler;
import com.rkarp.appcore.ui.windowmanager.WindowScene;
import com.rkarp.appcore.ui.windowmanager.WindowStage;
import com.rkarp.appcore.util.ImageLoader;
import com.rkarp.qaacc.factory.AccountXmlFactory;
import com.rkarp.qaacc.model.Account;
import com.rkarp.qaacc.settings.AppConfiguration;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.util.logging.Level;

public class AccountOverview {

    public static AccountOverview overview;
    private WindowScene accOverviewScene;
    static TableView<Account> tableview;

    public static Button editAcc, delAcc;
    public static Label nameTF,  mailTF, alliTF, lvTF, accCount, status;

    public AccountOverview(int projectID) {
        AccountOverview accOverview = AccountOverview.overview;
        if (accOverview == null) {
            overview = this;
            create(projectID);
        } else {
            if (accOverview.getAccOverviewScene().getWin().getWindowStage().getStage().isShowing()) {
                accOverview.getAccOverviewScene().getWin().getWindowStage().getStage().requestFocus();
            } else {
                overview = this;
                create(projectID);
            }
        }
    }

    private void create(int projectID) {
        Stage overviewStage = new Stage();
        overviewStage.setTitle(AppConfiguration.projectData.get(projectID).getProjectName() + " - " + LanguageController.getString("account_manager"));
        overviewStage.getIcons().add(ImageLoader.getAppIcon());
        overviewStage.setResizable(true);
        overviewStage.setWidth(770); overviewStage.setHeight(450);
        overviewStage.setMinWidth(750); overviewStage.setMinHeight(430);

        BorderPane borderpane = new BorderPane();
        Node contentNode = getLayout(projectID);

        // The UI (Client Area) to display
        borderpane.setCenter(contentNode);

        accOverviewScene = new WindowScene(new WindowStage("overview", overviewStage), borderpane, 1);

        overviewStage.setScene(accOverviewScene);
        overviewStage.show();
        //if (DebugHelper.isDebugVersion()) { DebugHelper.debugAccOverview(); }
        LogFileHandler.logger.log(Level.INFO, "open.AccountOverview{" + projectID + "}");
    }


    private Node getLayout(int projectID) {
        BorderPane mainContent = new BorderPane();
        mainContent.getStyleClass().add("background");

        SplitPane splitPane = new SplitPane();
        AnchorPane anchor1 = new AnchorPane();
        AnchorPane anchor2 = new AnchorPane();

        splitPane.getItems().add(0, anchor1);
        splitPane.getItems().add(1, anchor2);
        splitPane.setDividerPositions(0.35);

        GridPane gridpane = new GridPane();
        gridpane.setAlignment(Pos.BASELINE_LEFT);
        gridpane.setVgap(15);
        gridpane.setHgap(25);

        HBox controls = new HBox();
        controls.setPadding(new Insets(15, 12, 15, 12));  //padding top, left, bottom, right
        controls.setSpacing(10);
        controls.setAlignment(Pos.CENTER_RIGHT);

        tableview = new TableView<>();
        tableview.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableview.setPlaceholder(new Label(LanguageController.getString("no_account_found")));


        TableColumn column1 = new TableColumn<>(LanguageController.getString("name"));
        column1.setCellValueFactory(new PropertyValueFactory<>("accName"));
        TableColumn column2 = new TableColumn<>(LanguageController.getString("mail")); column2.setVisible(false);
        column2.setCellValueFactory(new PropertyValueFactory<>("accMail"));
        TableColumn column3 = new TableColumn<>(LanguageController.getString("level")); column3.setVisible(false);
        column3.setCellValueFactory(new PropertyValueFactory<>("accLevel"));
        TableColumn column4 = new TableColumn<>(); column4.setVisible(false);
        column4.setCellValueFactory(new PropertyValueFactory<>("accAlliance"));

        AnchorPane.setTopAnchor(tableview, 0.0);
        AnchorPane.setRightAnchor(tableview, 0.0);
        AnchorPane.setBottomAnchor(tableview, 0.0);
        AnchorPane.setLeftAnchor(tableview, 0.0);

        AnchorPane.setTopAnchor(gridpane, 75.0);
        AnchorPane.setLeftAnchor(gridpane, 15.0);
        AnchorPane.setRightAnchor(gridpane, 15.0);

        AnchorPane.setRightAnchor(controls, 10.0);
        AnchorPane.setBottomAnchor(controls, 10.0);

        tableview.setItems(AppConfiguration.accountData);
        tableview.tableMenuButtonVisibleProperty().set(true);
        tableview.getColumns().setAll(column1, column2, column3, column4);


        //add components
        Label header = new Label("Account Details:");
        header.getStyleClass().add("label-header");
        anchor2.getChildren().add(0, header);
        AnchorPane.setTopAnchor(header, 5.0);
        AnchorPane.setLeftAnchor(header, 10.0);

        Label nameL = new Label(LanguageController.getString("name"));
        GridPane.setConstraints(nameL, 0, 0);
        gridpane.getChildren().add(nameL);

        nameTF = new Label();
        GridPane.setConstraints(nameTF, 1, 0);
        gridpane.getChildren().add(nameTF);

        Label mailL = new Label(LanguageController.getString("mail"));
        GridPane.setConstraints(mailL, 0, 1);
        gridpane.getChildren().add(mailL);

        mailTF = new Label();
        mailTF.setMinWidth(300);
        GridPane.setConstraints(mailTF, 1, 1);
        gridpane.getChildren().add(mailTF);

        Label lvL = new Label(LanguageController.getString("level"));
        GridPane.setConstraints(lvL, 0, 2);
        gridpane.getChildren().add(lvL);

        lvTF = new Label();
        GridPane.setConstraints(lvTF, 1, 2);
        GridPane.setFillWidth(lvTF, false);
        gridpane.getChildren().add(lvTF);

        Label alliL = new Label();
        GridPane.setConstraints(alliL, 0, 3);
        gridpane.getChildren().add(alliL);

        alliTF = new Label();
        GridPane.setConstraints(alliTF, 1, 3);
        gridpane.getChildren().add(alliTF);

        accCount = new Label();
        anchor2.getChildren().add(1, accCount);
        AnchorPane.setBottomAnchor(accCount, 75.0);
        AnchorPane.setRightAnchor(accCount, 250.0);

        status = new Label(LanguageController.getString("ready"));
        anchor2.getChildren().add(2, status);
        AnchorPane.setBottomAnchor(status, 75.0);
        AnchorPane.setRightAnchor(status, 60.0);

        Button newAcc = new Button(LanguageController.getString("create"));
        controls.getChildren().add(newAcc);

        editAcc = new Button(LanguageController.getString("edit"));
        controls.getChildren().add(editAcc);

        delAcc = new Button(LanguageController.getString("delete"));
        controls.getChildren().add(delAcc);

        //set project specific names
        switch (projectID)
        {
            case 2:
                alliL.setText(LanguageController.getString("cooperative"));
                column4.setText(LanguageController.getString("cooperative"));
                break;
            default:
                alliL.setText(LanguageController.getString("alliance"));
                column4.setText(LanguageController.getString("alliance"));
                break;
        }


        //load all accounts for selected project
        AppConfiguration.accountData.clear();
        AccountXmlFactory.loadAccData(projectID);

        if (AppConfiguration.accountData.size() == 0) {
            editAcc.setDisable(true);
            delAcc.setDisable(true);
            accCount.setText("0 " + LanguageController.getString("accs_loaded"));
        }
        else if (AppConfiguration.accountData.size() == 1) {
            accCount.setText("1 " + LanguageController.getString("acc_loaded"));
            tableview.getSelectionModel().select(0);
        }
        else {
            accCount.setText(String.valueOf(AppConfiguration.accountData.size()) + " " + LanguageController.getString("accs_loaded"));
            tableview.getSelectionModel().select(0);
        }

        //add to AnchorPane Layout
        anchor1.getChildren().add(0, tableview);
        anchor2.getChildren().add(0, gridpane);
        anchor2.getChildren().add(1, controls);

        //add SplitPane to borderpane layout
        mainContent.setCenter(splitPane);

        newAcc.setOnAction(event -> new AccountEditDialog(projectID, "new", -1, null));

        editAcc.setOnAction(event -> {
            int selectedIndex = tableview.getSelectionModel().getFocusedIndex();
            Account selectedAccount = tableview.getSelectionModel().getSelectedItem();

            new AccountEditDialog(projectID, "edit", selectedIndex, selectedAccount);
            Account.showAccountDetails(selectedAccount);
        });

        delAcc.setOnAction(event -> {
            int selectedIndex = tableview.getSelectionModel().getFocusedIndex();
            try {
                AccountXmlFactory.removeAccountFromXml(projectID, selectedIndex);
                if (selectedIndex >= 0) {
                    AppConfiguration.accountData.remove(selectedIndex); tableview.getSelectionModel().select(selectedIndex);
                }
            } catch (MalformedURLException e1) {
                if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
            }
        });

        //Add change listener
        tableview.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected
            if (tableview.getSelectionModel().getSelectedItem() != null) {
                editAcc.setDisable(false);
                delAcc.setDisable(false);
                Account selectedAccount = tableview.getSelectionModel().getSelectedItem();
                nameTF.setText(selectedAccount.getAccName());
                mailTF.setText(selectedAccount.getAccMail());
                lvTF.setText(selectedAccount.getAccLevel());
                alliTF.setText(selectedAccount.getAccAlliance());
            }
            else
            {
                editAcc.setDisable(true);
                delAcc.setDisable(true);
            }
        });

        return mainContent;
    }

    //refreshes the table
    static void refreshPersonTable(int selectedIdx) {
        tableview.setItems(null);
        tableview.layout();
        tableview.setItems(AppConfiguration.accountData);
        tableview.getSelectionModel().select(selectedIdx);
    }

    public WindowScene getAccOverviewScene() {
        return accOverviewScene;
    }
}