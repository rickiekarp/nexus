package net.rickiekarp.core.view;

import net.rickiekarp.core.AppContext;
import net.rickiekarp.core.controller.LanguageController;
import net.rickiekarp.core.debug.DebugHelper;
import net.rickiekarp.core.debug.ExceptionHandler;
import net.rickiekarp.core.debug.LogFileHandler;
import net.rickiekarp.core.model.SettingsList;
import net.rickiekarp.core.net.update.FileDownloader;
import net.rickiekarp.core.net.update.UpdateChecker;
import net.rickiekarp.core.settings.Configuration;
import net.rickiekarp.core.settings.LoadSave;
import net.rickiekarp.core.ui.anim.AnimationHandler;
import net.rickiekarp.core.ui.windowmanager.ThemeSelector;
import net.rickiekarp.core.ui.tray.ToolTrayIcon;
import net.rickiekarp.core.ui.windowmanager.WindowScene;
import net.rickiekarp.core.ui.windowmanager.WindowStage;
import net.rickiekarp.core.ui.windowmanager.ImageLoader;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * The Settings Stage GUI.
 */
public class SettingsScene {

    public static SettingsScene settingsScene;
    private WindowScene settingsWindow;
    private AnchorPane controls;
    private TabPane tabPane;
    private String tabName[] = {"general", "appearance", "advanced"};
    private VBox tabVBox[] = new VBox[tabName.length];
    private GridPane tab1ContentGrid[];
    private GridPane tab2ContentGrid[];
    private GridPane tab3ContentGrid[];
    private ObservableList<String> localeData = FXCollections.observableArrayList("English", "Deutsch");
    private TableView<SettingsList> otherTable;
    private ObservableList<SettingsList> listData;
    private CheckBox logCBox, animateCBox, sysBorderCBox;
    private ComboBox<String> langCB, themeCB, colBox;
    private ObservableList<String> updateChannel = FXCollections.observableArrayList("stable", "dev");

    public SettingsScene() {
        SettingsScene about = SettingsScene.settingsScene;
        if (about == null) {
            settingsScene = this;
            create();
        } else {
            if (about.getSettingsWindow().getWin().getWindowStage().getStage().isShowing()) {
                about.getSettingsWindow().getWin().getWindowStage().getStage().requestFocus();
            } else {
                settingsScene = this;
                create();
            }
        }
    }

    public WindowScene getSettingsWindow() {
        return settingsWindow;
    }

    private void create() {
        Stage cfgStage = new Stage();
        cfgStage.setTitle(LanguageController.getString("settings"));
        cfgStage.getIcons().add(ImageLoader.getAppIconSmall());
        cfgStage.setResizable(true);
        cfgStage.setMinWidth(640); cfgStage.setMinHeight(480);
        cfgStage.setWidth(720); cfgStage.setHeight(570);

        BorderPane contentVbox = new BorderPane();

        Node cfgNode = getCfgLayout();

        // The UI (Client Area) to display
        contentVbox.setCenter(cfgNode);
        VBox.setVgrow(cfgNode, Priority.ALWAYS);

        // The Window as a Scene
        settingsWindow = new WindowScene(new WindowStage("settings", cfgStage), contentVbox, 1);

        cfgStage.setScene(settingsWindow);
        cfgStage.show();

        debugCfg();

        LogFileHandler.logger.info("open.settings");
    }


    private BorderPane getCfgLayout() {

        BorderPane cfgContent = new BorderPane();

        controls = new AnchorPane();

        tabPane = new TabPane();
        tabPane.setSide(Configuration.tabPosition);

        Tab tabs[] = new Tab[tabName.length];

        //create tabs and set the content
        for (int i = 0; i < tabName.length; i++) {
            tabs[i] = new Tab();
            tabs[i].setText(LanguageController.getString(tabName[i]));
            tabs[i].setClosable(false);

            tabVBox[i] = new VBox();
            tabVBox[i].setPadding(new Insets(0, 0, 0, 0));  //padding top, left, bottom, right
            tabs[i].setContent(tabVBox[i]);
        }
        tabPane.getTabs().add(tabs[0]);
        tabPane.getTabs().add(tabs[1]);

        //add components
        //Tab 1
        tab1ContentGrid = new GridPane[3];
        for (int i = 0; i < tab1ContentGrid.length; i++) {
            tab1ContentGrid[i] = new GridPane();
            tab1ContentGrid[i].setPadding(new Insets(15, 5, 5, 15));
            tab1ContentGrid[i].setVgap(5);
            tab1ContentGrid[i].setHgap(10);
        }
        GridPane.setConstraints(tab1ContentGrid[0], 0, 0);
        GridPane.setConstraints(tab1ContentGrid[1], 0, 1);
        GridPane.setConstraints(tab1ContentGrid[2], 0, 2);


        Label appLanguage = new Label();
        appLanguage.setText(LanguageController.getString("languageSelection"));
        appLanguage.setStyle("-fx-font-size: 12pt;");
        GridPane.setConstraints(appLanguage, 0, 0);
        tab1ContentGrid[0].getChildren().add(appLanguage);

        langCB = new ComboBox<>(localeData);
        langCB.setValue(localeData.get(LanguageController.getCurrentLocale()));
        GridPane.setConstraints(langCB, 0, 1);
        tab1ContentGrid[0].getChildren().add(langCB);

        Label setChange = new Label(LanguageController.getString("restartOnCfgChange"));
        setChange.setVisible(false);
        setChange.setStyle("-fx-text-fill: red;");
        GridPane.setConstraints(setChange, 0, 2);
        tab1ContentGrid[0].getChildren().add(setChange);

        Label pgUpdate = new Label();
        pgUpdate.setText(LanguageController.getString("progUpdate"));
        pgUpdate.setStyle("-fx-font-size: 12pt;");
        GridPane.setConstraints(pgUpdate, 0, 0);
        tab1ContentGrid[1].getChildren().add(pgUpdate);

        ComboBox<String> updateChannelBox = new ComboBox<>();
        updateChannelBox.setStyle("-fx-font-size: 12pt;");
        GridPane.setConstraints(updateChannelBox, 1, 0);
        tab1ContentGrid[1].getChildren().add(updateChannelBox);

        updateChannelBox.getItems().addAll("Stable", "Dev");
        updateChannelBox.getSelectionModel().select(Configuration.updateChannel);
        updateChannelBox.valueProperty().addListener((ov, t, t1) -> Configuration.updateChannel = updateChannelBox.getSelectionModel().getSelectedIndex());

        Label chkAppUpdate = new Label();
        chkAppUpdate.setText(LanguageController.getString("chkAppUpdate"));
        GridPane.setConstraints(chkAppUpdate, 0, 1);
        tab1ContentGrid[1].getChildren().add(chkAppUpdate);

        HBox appbox = new HBox();
        appbox.setMinHeight(40);
        appbox.setSpacing(20);
        appbox.setAlignment(Pos.CENTER_LEFT);
        GridPane.setConstraints(appbox, 1, 1);
        tab1ContentGrid[1].getChildren().add(appbox);

        Button btn_chkAppUpdate = new Button();
        btn_chkAppUpdate.setText(LanguageController.getString("chkUpdate"));

        ProgressBar updateBarApp = new ProgressBar(0);
        updateBarApp.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);

        Label updStatusApp = new Label();

        Button btn_downloadAppUpdate = new Button(LanguageController.getString("download"));

        Button btn_installAppUpdate = new Button(LanguageController.getString("install"));

        if (UpdateChecker.isUpdAvailable) {
            updStatusApp.setText(LanguageController.getString("update_available"));
            appbox.getChildren().addAll(updStatusApp, btn_downloadAppUpdate);
            updateChannelBox.setDisable(true);
        } else {
            appbox.getChildren().add(btn_chkAppUpdate);
        }

        btn_chkAppUpdate.setOnAction(event -> {

            updateChannelBox.setDisable(true);
            appbox.getChildren().remove(btn_chkAppUpdate);
            appbox.getChildren().add(updateBarApp);

            new Thread(() -> {

                int updatestatus = new UpdateChecker().checkProgramUpdate();

                Platform.runLater(() -> {
                    appbox.getChildren().remove(updateBarApp);
                    appbox.getChildren().add(updStatusApp);
                    switch (updatestatus) {
                        case 0:
                            updStatusApp.setText(LanguageController.getString("no_update"));
                            break;
                        case 1:
                            updStatusApp.setText(LanguageController.getString("update_available"));
                            appbox.getChildren().add(btn_downloadAppUpdate);
                            MainScene.mainScene.getWindowScene().getWin().getWindowStage().getStage().setTitle(AppContext.getContext().getApplicationName() + " - " + LanguageController.getString("update_available"));
                            break;
                        case 2:
                            updStatusApp.setText(LanguageController.getString("no_connection"));
                            break;
                        case 3:
                            updStatusApp.setText(LanguageController.getString("error"));
                            break;
                    }
                });
            }).start();
        });

        btn_downloadAppUpdate.setOnAction(event -> {
            appbox.getChildren().remove(btn_downloadAppUpdate);
            appbox.getChildren().remove(updStatusApp);
            appbox.getChildren().add(updateBarApp);

            updStatusApp.setText("");
            appbox.getChildren().add(updStatusApp);

            final FileDownloader fileDownloader;
            try {
                fileDownloader = new FileDownloader(new URL(Configuration.host + "files/apps/" + AppContext.getContext().getContextIdentifier() + "/download/" + updateChannel.get(Configuration.updateChannel) + File.separator), UpdateChecker.filesToDownload);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return;
            }

            // separate non-FX thread
            // runnable for that thread
            new Thread(() -> {
                while (fileDownloader.getStatus() == FileDownloader.DOWNLOADING) {
                    //System.out.println((updDL.getProgress() * 100) + " %");
                    double progress = fileDownloader.getProgress();
                    Platform.runLater(() -> {
                        updateBarApp.setProgress(progress);
                        updStatusApp.setText(LanguageController.getString("dlRemaining") + " " + fileDownloader.getDownloadList().size());
                    });

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }

                Platform.runLater(() -> {
                    appbox.getChildren().remove(updateBarApp);
                    updStatusApp.setText(LanguageController.getString("dlComplete"));
                    appbox.getChildren().add(btn_installAppUpdate);
                });
            }).start();
        });

        btn_installAppUpdate.setOnAction(event1 -> {
            Stage stage = MessageDialog.installUpdateDialog("update", 500, 220);
            stage.showAndWait();
        });

        Label debug = new Label(LanguageController.getString("logging"));
        debug.setStyle("-fx-font-size: 12pt;");
        GridPane.setConstraints(debug, 0, 0);
        tab1ContentGrid[2].getChildren().add(debug);

        logCBox = new CheckBox(LanguageController.getString("enableLog"));
        logCBox.setSelected(Configuration.logState);
        GridPane.setConstraints(logCBox, 0, 1);
        tab1ContentGrid[2].getChildren().add(logCBox);

        //Tab 2
        tab2ContentGrid = new GridPane[2];
        for (int i = 0; i < tab2ContentGrid.length; i++) {
            tab2ContentGrid[i] = new GridPane();
            tab2ContentGrid[i].setPadding(new Insets(15, 5, 5, 15));
            tab2ContentGrid[i].setHgap(15);
            tab2ContentGrid[i].setVgap(5);
        }
        GridPane.setConstraints(tab2ContentGrid[0], 0, 0);
        GridPane.setConstraints(tab2ContentGrid[1], 0, 1);

        Label uiText = new Label(LanguageController.getString("uiOptions"));
        appLanguage.setStyle("-fx-font-size: 12pt;");
        GridPane.setConstraints(uiText, 0, 0);
        tab2ContentGrid[0].getChildren().add(uiText);

        Label themeText = new Label("Theme");
        appLanguage.setStyle("-fx-font-size: 12pt;");
        GridPane.setConstraints(themeText, 0, 1);
        tab2ContentGrid[0].getChildren().add(themeText);

        themeCB = new ComboBox<>();
        themeCB.getItems().add("Dark");
        themeCB.getItems().add("Light");
        themeCB.getSelectionModel().select(Configuration.themeState);
        themeCB.setMinWidth(115);
        GridPane.setConstraints(themeCB, 1, 1);
        tab2ContentGrid[0].getChildren().add(themeCB);

        sysBorderCBox = new CheckBox(LanguageController.getString("sysDecorationEnable"));
        sysBorderCBox.setSelected(Configuration.useSystemBorders);
        GridPane.setConstraints(sysBorderCBox, 2, 1);
        GridPane.setMargin(sysBorderCBox, new Insets(0,0,0,35));
        tab2ContentGrid[0].getChildren().add(sysBorderCBox);

        CheckBox trayIconCBox = new CheckBox(LanguageController.getString("showTrayIcon"));
        trayIconCBox.setSelected(Configuration.showTrayIcon);
        GridPane.setConstraints(trayIconCBox, 2, 2);
        GridPane.setMargin(trayIconCBox, new Insets(0,0,0,35));
        tab2ContentGrid[0].getChildren().add(trayIconCBox);

        Label colorSchemeLabel = new Label();
        colorSchemeLabel.setText(LanguageController.getString("colorScheme"));
        colorSchemeLabel.setStyle("-fx-font-size: 12pt;");
        GridPane.setConstraints(colorSchemeLabel, 0, 2);
        tab2ContentGrid[0].getChildren().add(colorSchemeLabel);

        colBox = new ComboBox<>();
        colBox.setMinWidth(115);
        GridPane.setConstraints(colBox, 1, 2);
        tab2ContentGrid[0].getChildren().add(colBox);

        List<String> colorList = new ArrayList<>();
        colorList.add(LanguageController.getString("black"));
        colorList.add(LanguageController.getString("gray"));
        colorList.add(LanguageController.getString("white"));
        colorList.add(LanguageController.getString("red"));
        colorList.add(LanguageController.getString("orange"));
        colorList.add(LanguageController.getString("yellow"));
        colorList.add(LanguageController.getString("blue"));
        colorList.add(LanguageController.getString("magenta"));
        colorList.add(LanguageController.getString("purple"));
        colorList.add(LanguageController.getString("green"));

        colBox.getItems().addAll(colorList);
        colBox.getSelectionModel().select(Configuration.colorScheme);

        Label animateText = new Label(LanguageController.getString("effects"));
        animateText.setStyle("-fx-font-size: 12pt;");
        GridPane.setConstraints(animateText, 0, 0);
        tab2ContentGrid[1].getChildren().add(animateText);

        animateCBox = new CheckBox(LanguageController.getString("animationEnable"));
        animateCBox.setSelected(Configuration.animations);
        GridPane.setConstraints(animateCBox, 0, 1);
        tab2ContentGrid[1].getChildren().add(animateCBox);

        //Tab 3
        tab3ContentGrid = new GridPane[1];
        for (int i = 0; i < tab3ContentGrid.length; i++) {
            tab3ContentGrid[i] = new GridPane();
            tab3ContentGrid[i].setPadding(new Insets(15, 10, 10, 15));
            tab3ContentGrid[i].setVgap(5);
        }
        GridPane.setConstraints(tab3ContentGrid[0], 0, 0);


        Label advSet = new Label(LanguageController.getString("advanced_desc"));
        advSet.setStyle("-fx-font-size: 12pt;");

        Button reset = new Button(LanguageController.getString("reset"));
        reset.setStyle("-fx-font-size: 12pt;");

        AnchorPane advTopAnchor = new AnchorPane();
        advTopAnchor.getChildren().addAll(advSet, reset);
        AnchorPane.setTopAnchor(advSet, 5.0);
        AnchorPane.setRightAnchor(reset, 0.0);
        GridPane.setConstraints(advTopAnchor, 0, 0);
        tab3ContentGrid[0].getChildren().add(advTopAnchor);

        otherTable = new TableView<>();
        otherTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        otherTable.setEditable(true);
        GridPane.setConstraints(otherTable, 0, 1);
        GridPane.setHgrow(otherTable, Priority.ALWAYS);
        tab3ContentGrid[0].getChildren().add(otherTable);

        listData = getListData();
        listData.addListener((ListChangeListener<SettingsList>) c -> System.out.println(c + " changed"));

        TableColumn<SettingsList, String> settingName = new TableColumn<>(LanguageController.getString("name"));
        TableColumn<SettingsList, String> setting = new TableColumn<>(LanguageController.getString("value"));
        TableColumn<SettingsList, String> desc = new TableColumn<>(LanguageController.getString("setting_desc"));

        settingName.setCellValueFactory(new PropertyValueFactory<>("settingName"));
        setting.setCellValueFactory(new PropertyValueFactory<>("setting"));
        desc.setCellValueFactory(new PropertyValueFactory<>("desc"));

        setting.setCellFactory(TextFieldTableCell.forTableColumn());
        setting.setOnEditCommit(event -> {
                    checkSetting(event);
                    LogFileHandler.logger.log(Level.CONFIG, event.getRowValue().getSettingName() + ": " + event.getOldValue() + "->" + event.getNewValue());
                }
        );
        otherTable.getColumns().addAll(settingName, setting, desc);
        otherTable.setItems(listData);

        //controls
        HBox saveHBox = new HBox(10);
        saveHBox.setAlignment(Pos.CENTER);
        controls.getChildren().add(saveHBox);

        Label status = new Label();
        saveHBox.getChildren().add(status);

        Button saveCfg = new Button(LanguageController.getString("saveCfg"));
        saveHBox.getChildren().add(saveCfg);

        CheckBox advCBox = new CheckBox(LanguageController.getString("advanced"));
        controls.getChildren().add(advCBox);

        controls.setPadding(new Insets(12, 12, 12, 12));  //padding top, left, bottom, right
        AnchorPane.setBottomAnchor(saveHBox, 0.0);
        AnchorPane.setRightAnchor(saveHBox, 0.0);
        AnchorPane.setBottomAnchor(advCBox, 5.0);
        AnchorPane.setLeftAnchor(advCBox, 5.0);

        controls.setStyle("-fx-background-color: #1d1d1d;");

        //add tab content to main grid
        for (GridPane aTab1ContentGrid : tab1ContentGrid) { tabVBox[0].getChildren().add(aTab1ContentGrid); }
        for (GridPane aTab2ContentGrid : tab2ContentGrid) { tabVBox[1].getChildren().add(aTab2ContentGrid); }
        for (GridPane aTab3ContentGrid : tab3ContentGrid) { tabVBox[2].getChildren().add(aTab3ContentGrid); }

        //set BorderPane layout
        cfgContent.setCenter(tabPane);
        cfgContent.setBottom(controls);

        saveCfg.setOnAction(event -> {
            boolean shouldRestart = false;

            //color scheme change
            if (updateChannelBox.getSelectionModel().getSelectedIndex() != Configuration.updateChannel) {
                LogFileHandler.logger.config("change_update_channel: " + Configuration.updateChannel + " -> " + updateChannelBox.getSelectionModel().getSelectedIndex());
                ThemeSelector.changeColorScheme(updateChannelBox.getSelectionModel().getSelectedIndex());
                Configuration.updateChannel = updateChannelBox.getSelectionModel().getSelectedIndex();
            }

            //theme change
            if (themeCB.getSelectionModel().getSelectedIndex() != Configuration.themeState) {
                LogFileHandler.logger.config("change_theme: " + Configuration.themeState + " -> " + themeCB.getSelectionModel().getSelectedIndex());
                Configuration.themeState = themeCB.getSelectionModel().getSelectedIndex();
                ThemeSelector.onThemeChange();
            }

            //color scheme change
            if (colBox.getSelectionModel().getSelectedIndex() != Configuration.colorScheme) {
                LogFileHandler.logger.config("change_color_scheme: " + Configuration.colorScheme + " -> " + colBox.getSelectionModel().getSelectedIndex());
                ThemeSelector.changeColorScheme(colBox.getSelectionModel().getSelectedIndex());
                Configuration.colorScheme = colBox.getSelectionModel().getSelectedIndex();
            }

            //animation change
            if (animateCBox.isSelected() != Configuration.animations) {
                LogFileHandler.logger.config("change_window_animation: " + Configuration.animations + " -> " + animateCBox.isSelected());
                Configuration.animations = animateCBox.isSelected();
            }

            //system border change
            if (sysBorderCBox.isSelected() != Configuration.useSystemBorders) {
                LogFileHandler.logger.config("change_window_decoration: " + Configuration.useSystemBorders + " -> " + sysBorderCBox.isSelected());
                Configuration.useSystemBorders = sysBorderCBox.isSelected();
                shouldRestart = true;
            }

            //tray icon change
            if (trayIconCBox.isSelected() != Configuration.showTrayIcon) {
                LogFileHandler.logger.config("change_systray: " + Configuration.showTrayIcon + " -> " + trayIconCBox.isSelected());
                if (trayIconCBox.isSelected()) {
                    if (ToolTrayIcon.icon == null) {
                        ToolTrayIcon.icon = new ToolTrayIcon();
                    } else {
                        ToolTrayIcon.icon.addAppToTray();
                    }
                } else {
                    ToolTrayIcon.icon.removeTrayIcon();
                }
                Configuration.showTrayIcon = trayIconCBox.isSelected();
            }

            //logging change
            if (logCBox.isSelected() != Configuration.logState) {
                LogFileHandler.logger.config("change_log_state: " + Configuration.logState + " -> " + logCBox.isSelected());
                LogFileHandler.onLogStateChange();
                Configuration.logState = logCBox.isSelected();
            }

            //program language change
            if (langCB.getSelectionModel().getSelectedIndex() != Configuration.language) {
                LogFileHandler.logger.config("change_program_language: " + Configuration.language + " -> " + langCB.getSelectionModel().getSelectedIndex());
                Configuration.language = langCB.getSelectionModel().getSelectedIndex();
                shouldRestart = true;
                setChange.setVisible(true);
            }

            //show restart dialog if a setting that requires it has changed
            if (shouldRestart) {
                MessageDialog.restartDialog("restartApp_desc", 535, 230);
            }



            //save settings
            try { Configuration.config.save(); }
            catch (Exception e1) {
                if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
            }

            AnimationHandler.statusFade(status, "success", LanguageController.getString("cfgSaved"));
        });

        tabPane.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> {

            if (Configuration.animations) {
                if (arg2 == tabs[0]) {
                    for (GridPane aTab1ContentGrid : tab1ContentGrid) {
                        AnimationHandler.translate(aTab1ContentGrid, 150, -100, 0);
                        FadeTransition fade = AnimationHandler.fade(aTab1ContentGrid, 150, 0.1f, 1.0f);
                        fade.play();
                    }
                }

                if (arg2 == tabs[1]) {
                    for (GridPane aTab2ContentGrid : tab2ContentGrid) {
                        AnimationHandler.translate(aTab2ContentGrid, 150, -100, 0);
                        FadeTransition fade = AnimationHandler.fade(aTab2ContentGrid, 150, 0.1f, 1.0f);
                        fade.play();
                    }
                }

                if (arg2 == tabs[2]) {
                    for (GridPane aTab3ContentGrid : tab3ContentGrid) {
                        AnimationHandler.translate(aTab3ContentGrid, 150, -100, 0);
                        FadeTransition fade = AnimationHandler.fade(aTab3ContentGrid, 150, 0.1f, 1.0f);
                        fade.play();
                    }
                }
            }
        });

        advCBox.setOnAction(event -> {
            if (advCBox.isSelected()) {
                tabPane.getTabs().add(tabs[2]);
            } else {
                tabPane.getTabs().remove(2);
            }
        });

        reset.setOnAction(event -> {
            if (MessageDialog.confirmDialog("reset_desc", 535, 230)) {
                Configuration.config.setDefaults();
            }
        });

        return cfgContent;
    }

    private void debugCfg() {
        if (DebugHelper.isDebugVersion()) {
            controls.setStyle("-fx-background-color: #444444;");
            for (int i = 0; i < tabName.length; i++) {
                tabVBox[i].setStyle("-fx-background-color: blue;");
            }

            //tab 1
            for (GridPane aTab1ContentGrid : tab1ContentGrid) {
                aTab1ContentGrid.setStyle("-fx-background-color: gray;");
                aTab1ContentGrid.setGridLinesVisible(true);
            }

            //tab 2
            for (GridPane aTab2ContentGrid : tab2ContentGrid) {
                aTab2ContentGrid.setStyle("-fx-background-color: gray;");
                aTab2ContentGrid.setGridLinesVisible(true);
            }

            //tab 3
            for (GridPane aTab3ContentGrid : tab3ContentGrid) {
                aTab3ContentGrid.setStyle("-fx-background-color: gray;");
                aTab3ContentGrid.setGridLinesVisible(true);
            }

            //tab 4
            for (GridPane aTab4ContentGrid : tab3ContentGrid) {
                aTab4ContentGrid.setStyle("-fx-background-color: gray;");
                aTab4ContentGrid.setGridLinesVisible(true);
            }
        }
        else
        {
            controls.setStyle("-fx-background-color: #1d1d1d;");
            for (int i = 0; i < tabName.length; i++) {
                tabVBox[i].setStyle(null);
            }

            //tab 1
            for (GridPane aTab1ContentGrid : tab1ContentGrid) {
                aTab1ContentGrid.setStyle(null);
                aTab1ContentGrid.setGridLinesVisible(false);
            }

            //tab 2
            for (GridPane aTab2ContentGrid : tab2ContentGrid) {
                aTab2ContentGrid.setStyle(null);
                aTab2ContentGrid.setGridLinesVisible(false);
            }

            //tab 3
            for (GridPane aTab3ContentGrid : tab3ContentGrid) {
                aTab3ContentGrid.setStyle(null);
                aTab3ContentGrid.setGridLinesVisible(false);
            }
        }
    }

    /**
     * SettingsList Data Collection
     * @return All settings in an observable list
     */
    private ObservableList<SettingsList> getListData() {
        return FXCollections.observableArrayList(
                new SettingsList("language", String.valueOf(Configuration.language), "set program language"),
                new SettingsList("theme", String.valueOf(Configuration.themeState), "set program theme"),
                new SettingsList("logging", String.valueOf(Configuration.logState), "set program logging"),
                new SettingsList("decorationColor", ThemeSelector.getColorHexString(Configuration.decorationColor), "set window decoration color"),
                new SettingsList("shadowColorFocused", ThemeSelector.getColorHexString(Configuration.shadowColorFocused), "set shadow color when window focused"),
                new SettingsList("shadowColorNotFocused",ThemeSelector.getColorHexString(Configuration.shadowColorNotFocused), "set shadow color when window not focused"),
                new SettingsList("tabPosition", Configuration.tabPosition.name(), "set tab position in Settings window")
        );
    }

    /**
     * Checks which setting has changed and applies the new setting respectively.
     * @param event the CellEditEvent of the changed setting
     */
    private void checkSetting(TableColumn.CellEditEvent<SettingsList, String> event) {
        switch (event.getRowValue().getSettingName()) {
            case "language":
                try {
                    Configuration.language = Integer.parseInt(event.getNewValue());
                    updateGui(Configuration.class.getDeclaredField("language"));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    event.getTableView().getItems().get(event.getTablePosition().getRow()).setSetting(event.getOldValue());
                    event.getTableView().refresh();
                }
                break;
            case "theme":
                try {
                    Configuration.themeState = Integer.parseInt(event.getNewValue());
                    updateGui(Configuration.class.getDeclaredField("themeState"));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    event.getTableView().getItems().get(event.getTablePosition().getRow()).setSetting(event.getOldValue());
                    event.getTableView().refresh();
                }
                break;
            case "logging":
                try {
                    Configuration.logState = Boolean.parseBoolean(event.getNewValue());
                    updateGui(Configuration.class.getDeclaredField("logState"));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    event.getTableView().getItems().get(event.getTablePosition().getRow()).setSetting(event.getOldValue());
                    event.getTableView().refresh();
                }
                break;
            case "decorationColor":
                if (!Configuration.useSystemBorders) {
                    try { ThemeSelector.changeDecorationColor(event.getNewValue()); }
                    catch (IllegalArgumentException e1) {
                        new MessageDialog(0, LanguageController.getString("colorInvalid_errorDesc"), 400,230);
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setSetting(event.getOldValue());
                        event.getTableView().refresh();
                    }
                } else {
                    event.getTableView().getItems().get(event.getTablePosition().getRow()).setSetting(event.getOldValue());
                    event.getTableView().refresh();
                }
                break;
            case "shadowColorFocused":
                if (!Configuration.useSystemBorders) {
                    try {
                        ThemeSelector.changeWindowShadowColor(true, event.getNewValue());
                    } catch (IllegalArgumentException e1) {
                        new MessageDialog(0, LanguageController.getString("colorInvalid_errorDesc"), 400, 230);
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setSetting(event.getOldValue());
                        event.getTableView().refresh();
                    }
                } else {
                    event.getTableView().getItems().get(event.getTablePosition().getRow()).setSetting(event.getOldValue());
                    event.getTableView().refresh();
                }
                break;
            case "shadowColorNotFocused":
                if (!Configuration.useSystemBorders) {
                    try {
                        ThemeSelector.changeWindowShadowColor(false, event.getNewValue());
                    } catch (IllegalArgumentException e1) {
                        new MessageDialog(0, LanguageController.getString("colorInvalid_errorDesc"), 400, 230);
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setSetting(event.getOldValue());
                        event.getTableView().refresh();
                    }
                } else {
                    event.getTableView().getItems().get(event.getTablePosition().getRow()).setSetting(event.getOldValue());
                    event.getTableView().refresh();
                }
                break;
            case "tabPosition":
                try { Configuration.tabPosition = Side.valueOf(event.getNewValue()); tabPane.setSide(Configuration.tabPosition); }
                catch (IllegalArgumentException e1) {
                    new MessageDialog(0, LanguageController.getString("tabPosition_errorDesc"), 400,230);
                    event.getTableView().getItems().get(event.getTablePosition().getRow()).setSetting(event.getOldValue());
                    event.getTableView().refresh();
                }
                break;
        }
    }

    private void updateGui(Field f) throws IllegalAccessException {
        switch (f.getName()) {
            case "language":
                if (!MessageDialog.restartDialog("restartApp_desc", 535, 230)) {
                    Configuration.config.save();
                }
                break;
            case "themeState":
                ThemeSelector.onThemeChange();
                themeCB.getSelectionModel().select(Configuration.themeState);
                break;
            case "logState":
                LogFileHandler.onLogStateChange(); logCBox.setSelected(Configuration.logState);
                break;
            case "colorScheme":
                colBox.getSelectionModel().select(Configuration.colorScheme);
                ThemeSelector.changeColorScheme(Configuration.colorScheme);
                break;
            case "animations":
                animateCBox.setSelected(Configuration.animations);
                break;
            case "useSystemBorders":
                if (!MessageDialog.restartDialog("restartApp_desc", 535, 230)) {
                    sysBorderCBox.setSelected(Configuration.useSystemBorders);
                    Configuration.config.save();
                }
                break;
            case "decorationColor":
                for (SettingsList aListData3 : listData) {
                    if (aListData3.getSettingName().equals(f.getName())) {
                        aListData3.setSetting(ThemeSelector.getColorHexString((Color) f.get(LoadSave.class)));
                        break;
                    }
                }
                ThemeSelector.changeDecorationColor(String.valueOf(Configuration.decorationColor));
                break;
            case "shadowColorFocused":
                for (SettingsList aListData2 : listData) {
                    if (aListData2.getSettingName().equals(f.getName())) {
                        aListData2.setSetting(ThemeSelector.getColorHexString((Color) f.get(LoadSave.class)));
                        break;
                    }
                }
                ThemeSelector.changeWindowShadowColor(true, String.valueOf(Configuration.shadowColorFocused));
                break;
            case "shadowColorNotFocused":
                for (SettingsList aListData1 : listData) {
                    if (aListData1.getSettingName().equals(f.getName())) {
                        aListData1.setSetting(ThemeSelector.getColorHexString((Color) f.get(LoadSave.class)));
                        break;
                    }
                }
                ThemeSelector.changeWindowShadowColor(false, String.valueOf(Configuration.shadowColorNotFocused));
                break;
            case "tabPosition":
                for (SettingsList aListData : listData) {
                    if (aListData.getSettingName().equals(f.getName())) {
                        aListData.setSetting(String.valueOf(f.get(LoadSave.class)));
                        break;
                    }
                }
                tabPane.setSide(Configuration.tabPosition);
                break;
            default: System.out.println("nothing changed");
        }
    }

    public void updateGUI(List<Field> objectlist) {
        for (Field f : objectlist) {
            try {
                updateGui(f);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        otherTable.refresh();
    }
}