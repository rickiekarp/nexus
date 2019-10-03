package net.rickiekarp.qaacc.view;

import net.rickiekarp.core.controller.LanguageController;
import net.rickiekarp.core.debug.DebugHelper;
import net.rickiekarp.core.debug.ExceptionHandler;
import net.rickiekarp.core.ui.windowmanager.WindowScene;
import net.rickiekarp.core.ui.windowmanager.WindowStage;
import net.rickiekarp.core.util.CommonUtil;
import net.rickiekarp.core.ui.windowmanager.ImageLoader;
import net.rickiekarp.core.view.MessageDialog;
import net.rickiekarp.qaacc.factory.AccountXmlFactory;
import net.rickiekarp.qaacc.settings.AppConfiguration;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.MalformedURLException;

public class BugReportSettings {
    public static BugReportSettings bugReport;
    private WindowScene bugCfgScene;
    private Label accountTP;
    public static TextField browserTF;
    private TextField versionTF;
    private TextArea tmplTA;
    public static ComboBox<String> accountCB, serverCB, locaCB;


    public BugReportSettings(int projectID) {
        BugReportSettings bugRep = BugReportSettings.bugReport;
        if (bugRep == null) {
            bugReport = this;
            create(projectID);
        } else {
            if (bugRep.getBugReportSettings().getWin().getWindowStage().getStage().isShowing()) {
                bugRep.getBugReportSettings().getWin().getWindowStage().getStage().requestFocus();
            } else {
                bugReport = this;
                create(projectID);
            }
        }
    }

    private void create(int projectID) {
        Stage bugCfgStage = new Stage();
        bugCfgStage.setTitle("Bug Report");
        bugCfgStage.getIcons().add(ImageLoader.getAppIconSmall());
        bugCfgStage.setResizable(false);
        bugCfgStage.setWidth(720); bugCfgStage.setHeight(540);

        BorderPane borderpane = new BorderPane();
        Node contentNode = getLayout(projectID);

        // The UI (Client Area) to display
        borderpane.setCenter(contentNode);

        bugCfgScene = new WindowScene(new WindowStage("bugconfig", bugCfgStage), borderpane, 1);

        bugCfgStage.setScene(bugCfgScene);
        bugCfgStage.show();

        //if (DebugHelper.isDebugVersion()) { DebugHelper.debugBugTemplate(); }

        switch (projectID)
        {
            case -1:
                accountTP.setVisible(false); accountCB.setVisible(false);
                break;

            default:
                AppConfiguration.accountList.clear();
                try {
                    AccountXmlFactory.readAccountNameFromXML(projectID);
                } catch (MalformedURLException e1) {
                    if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
                }
                break;
        }

        versionTF.setText(AppConfiguration.found_in_version);
        serverCB.setValue(AppConfiguration.server.get(AppConfiguration.srvSel));
        locaCB.setValue(AppConfiguration.loca.get(AppConfiguration.locaSel));

        //parseBugTemplate();
        populateBugTemplate(projectID);
    }



    private Node getLayout(int pjState) {

        BorderPane mainContent = new BorderPane();
        mainContent.getStyleClass().add("background");

        GridPane mainGrid = new GridPane();
        GridPane cfgGrid = new GridPane();
        GridPane prevGrid = new GridPane();
        GridPane cfgInnerGrid = new GridPane();
        HBox controls = new HBox();

        cfgGrid.setHgap(30);
        cfgGrid.setPadding(new Insets(10, 10, 0, 10));  //padding top, left, bottom, right
        cfgGrid.setAlignment(Pos.BASELINE_CENTER);

        prevGrid.setPadding(new Insets(10, 10, 0, 10));  //padding top, left, bottom, right
        cfgInnerGrid.setHgap(10);
        cfgInnerGrid.setVgap(15);

        //add components
        TitledPane tmplCfg = new TitledPane(LanguageController.getString("settings"), cfgInnerGrid); tmplCfg.setCollapsible(false);
        GridPane.setConstraints(tmplCfg, 0, 0);
        cfgGrid.getChildren().add(tmplCfg);

        Label browserTP = new Label("Browser");
        GridPane.setConstraints(browserTP, 0, 0);
        cfgInnerGrid.getChildren().add(browserTP);

        browserTF = new TextField(AppConfiguration.browser);
        GridPane.setConstraints(browserTF, 1, 0);
        cfgInnerGrid.getChildren().add(browserTF);

        Label versionTP = new Label("Version");
        GridPane.setConstraints(versionTP, 0, 1);
        cfgInnerGrid.getChildren().add(versionTP);

        versionTF = new TextField();
        GridPane.setConstraints(versionTF, 1, 1);
        cfgInnerGrid.getChildren().add(versionTF);

        Label serverTP = new Label("Server");
        GridPane.setConstraints(serverTP, 0, 2);
        cfgInnerGrid.getChildren().add(serverTP);

        serverCB = new ComboBox<>(AppConfiguration.server);
        GridPane.setConstraints(serverCB, 1, 2);
        cfgInnerGrid.getChildren().add(serverCB);

        Label langTP = new Label(LanguageController.getString("language"));
        GridPane.setConstraints(langTP, 0, 3);
        langTP.setPrefWidth(95);
        cfgInnerGrid.getChildren().add(langTP);

        locaCB = new ComboBox<>(AppConfiguration.loca);
        GridPane.setConstraints(locaCB, 1, 3);
        cfgInnerGrid.getChildren().add(locaCB);

        accountTP = new Label("Account");
        GridPane.setConstraints(accountTP, 0, 4);
        cfgInnerGrid.getChildren().add(accountTP);

        accountCB = new ComboBox<>();
        GridPane.setConstraints(accountCB, 1, 4);
        cfgInnerGrid.getChildren().add(accountCB);

        tmplTA = new TextArea(); tmplTA.setEditable(false);
        tmplTA.setMinHeight(320);
        tmplTA.setStyle("-fx-font-size: 12pt;");

        TitledPane tmplPrev = new TitledPane("Template " + LanguageController.getString("preview"), tmplTA); tmplPrev.setCollapsible(false);
        GridPane.setConstraints(tmplPrev, 0, 0);
        prevGrid.getChildren().add(tmplPrev);

        Label status = new Label();
        Button saveCfg = new Button(LanguageController.getString("saveCfg"));
        Button copyTemplate = new Button(LanguageController.getString("template_copy"));
        controls.getChildren().addAll(status, saveCfg, copyTemplate);

        controls.setPadding(new Insets(15, 12, 15, 12));  //padding top, left, bottom, right
        controls.setSpacing(10);
        controls.setAlignment(Pos.CENTER_RIGHT);

        ColumnConstraints cfg = new ColumnConstraints(); cfg.setPercentWidth(45);
        ColumnConstraints prev = new ColumnConstraints(); prev.setPercentWidth(55);
        mainGrid.getColumnConstraints().addAll(cfg, prev);

        GridPane.setConstraints(cfgGrid, 0, 0);
        GridPane.setConstraints(prevGrid, 1, 0);
        mainGrid.getChildren().add(cfgGrid);
        mainGrid.getChildren().add(prevGrid);

        //add to borderpane layout
        mainContent.setCenter(mainGrid);
        mainContent.setBottom(controls);

        saveCfg.setOnAction(event -> {
            AppConfiguration.browser = browserTF.getText();
            AppConfiguration.found_in_version = versionTF.getText();
            AppConfiguration.srvSel = serverCB.getSelectionModel().getSelectedIndex();
            AppConfiguration.locaSel = locaCB.getSelectionModel().getSelectedIndex();
            if (pjState >= 0) {
                AppConfiguration.projectData.get(pjState).setProjectAccBookmarkIdx(accountCB.getSelectionModel().getSelectedIndex());
                if (accountCB.getValue() != null) { AppConfiguration.projectData.get(pjState).setProjectAccBookmarkName(accountCB.getSelectionModel().getSelectedItem()); }
            }

//            try {
//                SettingsXmlFactory.saveBugTemplateSettings(pjState);
//                populateBugTemplate(pjState);
//                status.setStyle("-fx-text-fill: #55c4fe;"); status.setText(LanguageController.getString("cfgSaved"));
//            } catch (MalformedURLException | FileNotFoundException e1) {
//                if (AppConfiguration.debugVersion) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
//            }
        });

        copyTemplate.setOnAction(event -> {
            AppConfiguration.setStringToClipboard(tmplTA.getText());
            status.setStyle("-fx-text-fill: #55c4fe;");
            status.setText(LanguageController.getString("template_copied"));
        });

        browserTF.setOnKeyReleased(ke -> populateBugTemplate(pjState));

        versionTF.setOnKeyReleased(ke -> populateBugTemplate(pjState));

        accountCB.valueProperty().addListener((ov, t, t1) -> {
            if (t1 != null) {
                AppConfiguration.projectData.get(pjState).setProjectAccBookmarkName(accountCB.getSelectionModel().getSelectedItem());
                populateBugTemplate(pjState);
            }
        });

        serverCB.valueProperty().addListener((ov, t, t1) -> {
            AppConfiguration.srvSel = serverCB.getSelectionModel().getSelectedIndex();
            if (t1 != null) { populateBugTemplate(pjState); }
        });

        locaCB.valueProperty().addListener((ov, t, t1) -> {
            AppConfiguration.locaSel = locaCB.getSelectionModel().getSelectedIndex();
            if (t1 != null) { populateBugTemplate(pjState); }
        });

        return mainContent;
    }

    private void populateBugTemplate(int projectInt) {
        tmplTA.setText(getBugtemplate(projectInt));
    }

    /**
     *  returns bug template
     *  only called in BugReportSettings scene
     **/
    private String getBugtemplate(int projectInt) {
        String accName;
        String orAny;
        try {
            if (AppConfiguration.projectData.get(projectInt).getProjectAccBookmarkName().equals("")) { accName = ""; orAny = ""; }
            else if (AppConfiguration.projectData.get(projectInt).getProjectAccBookmarkName().equals(LanguageController.getString("xml_not_found"))) { accName = ""; orAny = ""; }
            else if (AppConfiguration.projectData.get(projectInt).getProjectAccBookmarkName().equals(LanguageController.getString("no_account_found"))) { accName = ""; orAny = ""; }
            else { accName = AppConfiguration.projectData.get(projectInt).getProjectAccBookmarkName(); orAny = AppConfiguration.bugtemplate[11]; }
        } catch (ArrayIndexOutOfBoundsException e1) { accName = ""; orAny = ""; }

        return
                AppConfiguration.bugtemplate[0] + browserTF.getText() + "\n" +
                        AppConfiguration.bugtemplate[1] + versionTF.getText() + "\n" +
                        AppConfiguration.bugtemplate[2] + serverCB.getValue() + "\n" +
                        AppConfiguration.bugtemplate[3] + CommonUtil.getTime("HH:mm") + "\n" +
                        AppConfiguration.bugtemplate[4] + accName + orAny + "\n" +
                        AppConfiguration.bugtemplate[5] + locaCB.getValue() + AppConfiguration.bugtemplate[11] + "\n" +
                        AppConfiguration.bugtemplate[6] + "\n\n" +
                        AppConfiguration.bugtemplate[7] + "\n" +
                        AppConfiguration.bugtemplate[8] + "\n\n" +
                        AppConfiguration.bugtemplate[9] + "\n" +
                        AppConfiguration.bugtemplate[8] + "\n\n" +
                        AppConfiguration.bugtemplate[10] + "\n" +
                        AppConfiguration.bugtemplate[8];
    }

    public static void copyTemplate() {
        int projectID = AppConfiguration.pjState;

        if (projectID == -1) {
            new MessageDialog(0, LanguageController.getString("project_not_selected"), 350, 220);
        }
        else
        {
            try {
                AccountXmlFactory.getFavAccName(projectID);
                AppConfiguration.setStringToClipboard(AppConfiguration.onCopyBugtemplate(projectID));
                MainLayout.status.setText(LanguageController.getString("template_copied"));
            }
            catch (Exception e1) {
                if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
            }
        }
    }


    public WindowScene getBugReportSettings() {
        return bugCfgScene;
    }
}