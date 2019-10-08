package net.rickiekarp.botter.view;

import net.rickiekarp.core.controller.LanguageController;
import net.rickiekarp.core.debug.DebugHelper;
import net.rickiekarp.core.debug.ExceptionHandler;
import net.rickiekarp.core.debug.LogFileHandler;
import net.rickiekarp.core.util.parser.JsonParser;
import net.rickiekarp.core.settings.Configuration;
import net.rickiekarp.core.view.ChangelogScene;
import net.rickiekarp.core.view.MainScene;
import net.rickiekarp.core.view.MessageDialog;
import net.rickiekarp.core.view.SettingsScene;
import net.rickiekarp.botlib.BotConfig;
import net.rickiekarp.botlib.BotLauncher;
import net.rickiekarp.botlib.PluginConfig;
import net.rickiekarp.botlib.enums.BotPlatforms;
import net.rickiekarp.botlib.enums.BotType;
import net.rickiekarp.botlib.model.Credentials;
import net.rickiekarp.botlib.model.PluginData;
import net.rickiekarp.botlib.plugin.BotSetting;
import net.rickiekarp.botlib.plugin.PluginExecutor;
import net.rickiekarp.botter.botservice.BotTask;
import net.rickiekarp.botter.listcell.FoldableListCell;
import net.rickiekarp.botter.settings.AppConfiguration;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import net.rickiekarp.core.view.layout.AppLayout;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.Base64;

public class MainLayout implements AppLayout {
    public static MainLayout mainLayout;
    private HBox optionBox;
    private ComboBox<PluginData> modCBox;
    private Button runBtn, stopBtn;
    private Label status, timeRemainL;
    private VBox timeBox;
    private ProgressIndicator loadBar;
    private ListView<BotSetting> listView;

    private BotTask botTask;
    private BotLauncher botLauncher;

    public static MainLayout getInstance() {
        if (mainLayout == null) {
            mainLayout = new MainLayout();
        }
        return mainLayout;
    }

    public void clearModules() {
        modCBox.getItems().clear();
    }

    public MainLayout() {
        mainLayout = this;

        optionBox = new HBox(10);
        optionBox.setAlignment(Pos.BOTTOM_LEFT);
        optionBox.setPadding(new Insets(5, 0, 5, 5));

        VBox moduleBox = new VBox();
        VBox asgmBox = new VBox();

        modCBox = new ComboBox<>();
        modCBox.setPromptText("none");
        modCBox.setMinWidth(175);
//        for (int i = 0; i < PluginData.pluginData.size(); i++) {
//            modCBox.getItems().add(PluginData.pluginData.get(i));
//        }

        //selected value showed in combo box
        modCBox.setConverter(new StringConverter<PluginData>() {
            @Override
            public String toString(PluginData plugin) {
                if (plugin == null){
                    return null;
                } else {
                    return plugin.getPluginName();
                }
            }

            @Override
            public PluginData fromString(String plugin) {
                return new PluginData(null, plugin, null, null, null);
            }
        });

        PluginData.pluginData.addListener((ListChangeListener<PluginData>) change -> {
            change.next();
            if (change.wasAdded()) {
                if (PluginData.pluginData.get(change.getFrom()).getPluginOldVersion() != null) {
                    //add plugin to module combobox if it doesn't exist already
                    if (!modCBox.getItems().contains(PluginData.pluginData.get(change.getFrom()))) {
                        modCBox.getItems().add(PluginData.pluginData.get(change.getFrom()));
                    }
                }
            }
        });


        runBtn = new Button("Run");
        runBtn.setDisable(true);

        stopBtn = new Button("Stop");
        stopBtn.setDisable(true);


        timeBox = new VBox();
        timeBox.setVisible(false);
        timeBox.setAlignment(Pos.BOTTOM_CENTER);
        timeBox.setPadding(new Insets(0, 0, 0, 15));

        Label timeL = new Label("Time until next run:");
        timeL.setStyle("-fx-font-size: 9pt;");
        timeRemainL = new Label("00:00:00");
        timeRemainL.setStyle("-fx-font-size: 9pt;");

        timeBox.getChildren().addAll(timeL, timeRemainL);

        moduleBox.getChildren().add(modCBox);
        optionBox.getChildren().addAll(moduleBox, asgmBox, runBtn, stopBtn, timeBox);

        //ActionListener
        modCBox.valueProperty().addListener((ov, t, t1) -> {
            if (t == null) {
                runBtn.setDisable(false);
                MainScene.Companion.getMainScene().getBorderPane().setRight(getSettingsNode());
                MainScene.Companion.getMainScene().getBorderPane().setBottom(getControlsNode());
            }

            PluginConfig.settingsList.clear();
            PluginConfig.settingsList.add(
                    BotSetting.Builder.create().setName(LanguageController.getString("option_timer")).setDescription(LanguageController.getString("option_timer_desc")).setVisible(true).setNode(getTimerSection()).build()
            );

            switch(modCBox.getSelectionModel().getSelectedItem().getPluginType()) {
                case ANDROID:
                    PluginConfig.botType = BotType.Bot.ANDROID;
                    PluginConfig.settingsList.add(
                            BotSetting.Builder.create().setName(LanguageController.getString("androidSelect")).setDescription(LanguageController.getString("androidSelect_desc")).setVisible(true).setNode(getAndroidDeviceSelection()).build()
                    );
                    break;
                case WEB:
                    PluginConfig.settingsList.add(
                            BotSetting.Builder.create().setName(LanguageController.getString("credentialsSelect")).setDescription(LanguageController.getString("credentialsSelect_desc")).setVisible(true).setNode(getCredentialsSection()).build()
                    );
                    PluginConfig.settingsList.add(
                            BotSetting.Builder.create().setName(LanguageController.getString("browserSelect")).setDescription(LanguageController.getString("browserSelect_desc")).setVisible(true).setNode(getBrowserSelectionSection()).build()
                    );
                    break;
            }

            PluginConfig.botPlatform = modCBox.getSelectionModel().getSelectedItem().getPluginType();
            botLauncher = new BotLauncher();
            botLauncher.createBotRunner(modCBox.getSelectionModel().getSelectedItem());

            try {
                PluginExecutor.executeLayoutSetter(BotLauncher.getRunnerInstance(), modCBox.getSelectionModel().getSelectedItem());
            } catch (Exception e) {
                if (DebugHelper.DEBUGVERSION) { e.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e); }
            }
        });

        runBtn.setOnAction(event -> {
            setStatus("neutral", "Loading bot...");
            setLoadBarVisible(true);
            switchMode();
            MainScene.Companion.getMainScene().getBorderPane().setCenter(null);

            //remove all buttons from control node
            AnchorPane pane = (AnchorPane) MainScene.Companion.getMainScene().getBorderPane().getBottom();
            HBox controls = (HBox) pane.getChildren().get(0);
            for (int size = controls.getChildren().size() - 1; size >= 0; size--) {
                controls.getChildren().remove(size);
            }

            if (botTask != null) {
                botTask.resetTimer();
            }
            botTask = new BotTask(botLauncher, modCBox.getSelectionModel().getSelectedItem());
        });

        stopBtn.setOnAction(event -> {
            setStatus("neutral", "Stopping...");
            botTask.cancel();
        });

        if (DebugHelper.DEBUGVERSION) {
            optionBox.setStyle("-fx-background-color: darkgray");
            moduleBox.setStyle("-fx-background-color: yellow");
            moduleBox.setStyle("-fx-background-color: green");
            timeBox.setStyle("-fx-background-color: darkblue");
        }
    }

    public Node getLaunchNode() {
        return optionBox;
    }

    private Node getControlsNode() {
        AnchorPane node = new AnchorPane();
        node.setMinHeight(50);

        HBox controls = new HBox(5);

        HBox statusBox = new HBox(10);
        statusBox.setAlignment(Pos.BOTTOM_RIGHT);

        status = new Label();

        loadBar = new ProgressIndicator();
        loadBar.setMaxHeight(20);
        loadBar.setMaxWidth(20);
        loadBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        loadBar.setVisible(false);

        statusBox.getChildren().addAll(loadBar, status);

        AnchorPane.setLeftAnchor(controls, 10.0);
        AnchorPane.setBottomAnchor(controls, 5.0);
        AnchorPane.setRightAnchor(statusBox, 15.0);
        AnchorPane.setBottomAnchor(statusBox, 10.0);

        if (DebugHelper.DEBUGVERSION) {
            statusBox.setStyle("-fx-background-color: blue");
            controls.setStyle("-fx-background-color: black");
        }

        node.getChildren().addAll(controls, statusBox);
        return node;
    }

    private ListView<BotSetting> getSettingsNode() {
        //SETTINGS LIST
        listView = new ListView<>();
        PluginConfig.settingsList = FXCollections.observableArrayList();
        PluginConfig.settingsList.add(
                BotSetting.Builder.create().setName(LanguageController.getString("option_timer")).setDescription(LanguageController.getString("option_timer_desc")).setVisible(true).setNode(getTimerSection()).build()
        );
        listView.setItems(PluginConfig.settingsList);

        listView.setCellFactory(lv -> new FoldableListCell(listView));

        if (DebugHelper.DEBUGVERSION) {
            listView.setStyle("-fx-background-color: gray");
        }
        return listView;
    }

    public void switchMode() {
        modCBox.setDisable(!modCBox.isDisabled());
        runBtn.setDisable(!runBtn.isDisabled());
        stopBtn.setDisable(!stopBtn.isDisabled());
    }

    public void switchTimeBox() {
        timeBox.setVisible(!timeBox.isVisible());
    }

    public void loadBot() {
        runBtn.fire();
    }

    public void setStatus(String type, String text) {
        switch (type) {
            case "success":
                status.setStyle("-fx-text-fill: #55c4fe;");
                break;
            case "fail":
                status.setStyle("-fx-text-fill: red;" );
                break;
            case "neutral":
                status.setStyle("-fx-text-fill: white;");
                break;
        }
        status.setText(text);
    }

    public void updateCountdown(String text) {
        timeRemainL.setText(text);
    }

    public void setLoadBarVisible(boolean bool) {
        loadBar.setVisible(bool);
    }


    public VBox getGeneralSection() {

        VBox content = new VBox();
        content.setSpacing(5);

        Button btn_settings = new Button();
        btn_settings.setTooltip(new Tooltip(LanguageController.getString("settings")));
        btn_settings.getStyleClass().add("decoration-button-settings");

        Button btn_about = new Button();
        btn_about.setTooltip(new Tooltip(LanguageController.getString("changelog")));
        btn_about.getStyleClass().add("decoration-button-about");

        btn_settings.setOnAction(event -> new SettingsScene());
        btn_about.setOnAction(event -> new ChangelogScene());

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(0, 0, 0, listView.getWidth() / 4));
        hbox.setSpacing(5);
        hbox.getChildren().addAll(btn_settings, btn_about);

        content.getChildren().addAll(hbox);
        return content;
    }

    private VBox getTimerSection() {
        VBox content = new VBox();
        content.setSpacing(5);

        CheckBox periodCheck = new CheckBox(LanguageController.getString("setPeriodicalRun"));
        periodCheck.setStyle("-fx-font-size: 10pt;");

        ComboBox<String> periods = new ComboBox<>();
        periods.getItems().addAll("30 minutes", "45 minutes", "60 minutes");
        periods.setValue(AppConfiguration.runInterval +  " minutes");
        periods.valueProperty().addListener((ov, t, t1) -> {
            switch (periods.getSelectionModel().getSelectedIndex()) {
                case 0: AppConfiguration.runInterval = 30; break;
                case 1: AppConfiguration.runInterval = 45; break;
                case 2: AppConfiguration.runInterval = 60; break;
            }
        });

        periodCheck.setOnAction(event -> {
            if (periodCheck.isSelected()) { content.getChildren().add(periods); AppConfiguration.canBotRunPeriodical = true; }
            else { content.getChildren().remove(periods); AppConfiguration.canBotRunPeriodical = false; }
        });

        content.getChildren().addAll(periodCheck);
        return content;
    }

    private VBox getCredentialsSection() {
        LogFileHandler.logger.info("Loading credentials section");
        VBox content = new VBox();
        content.setSpacing(5);

        Label loginLabel = new Label(LanguageController.getString("login"));
        Label passwordLabel = new Label(LanguageController.getString("password"));
        TextField loginTF = new TextField();
        PasswordField passTF = new PasswordField();
        Button saveButton = new Button(LanguageController.getString("saveCfg"));
        Button deleteButton = new Button(LanguageController.getString("remove"));

        content.getChildren().addAll(loginLabel, loginTF, passwordLabel, passTF);

        final JSONObject[] deviceJson = {JsonParser.readJsonFromFile(new File(Configuration.config.getConfigDirFile() + File.separator + "plugins" + File.separator + "credentials.json"))};
        if (deviceJson[0] != null) {
            JSONArray jsonArray = deviceJson[0].getJSONArray("credentials");
            JSONObject cJson;
            for (int i = 0; i < jsonArray.length(); i++) {
                cJson = jsonArray.getJSONObject(i);
                if (cJson.getString("primaryKey").equals(modCBox.getSelectionModel().getSelectedItem().getPluginName())) {
                    loginTF.setText(cJson.getString("login"));

                    String encodedPass = cJson.getString("password");
                    String trimmedPass = encodedPass.substring(16);
                    byte[] bytes;
                    try {
                        bytes = trimmedPass.getBytes("UTF-8");
                        byte[] decoded = Base64.getDecoder().decode(bytes);
                        String decodedString = new String(decoded);
                        passTF.setText(decodedString);
                        modCBox.getSelectionModel().getSelectedItem().setPluginCredentials(new Credentials(loginTF.getText(), decodedString));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    content.getChildren().add(deleteButton);
                    break;
                }
            }
        }

        deleteButton.setOnAction(event -> {
            JSONArray jsonArray = deviceJson[0].getJSONArray("credentials");
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.getJSONObject(i).getString("primaryKey").equals(modCBox.getSelectionModel().getSelectedItem().getPluginName())) {
                    jsonArray.remove(i);
                    JsonParser.writeJsonObjectToFile(deviceJson[0], new File(Configuration.config.getConfigDirFile() + File.separator + "plugins"), "credentials.json");
                    break;
                }
            }
            modCBox.getSelectionModel().getSelectedItem().setPluginCredentials(null);
            loginTF.clear();
            passTF.clear();
            content.getChildren().remove(deleteButton);
            content.getChildren().remove(saveButton);
        });

        //ActionListener
        loginTF.setOnKeyReleased(event -> {
            if (!content.getChildren().contains(saveButton)) {
                content.getChildren().add(saveButton);
            }
        });
        passTF.setOnKeyReleased(event -> {
            if (!content.getChildren().contains(saveButton)) {
                content.getChildren().add(saveButton);
            }
        });

        saveButton.setOnAction(event -> {
            if (loginTF.getText().isEmpty() || passTF.getText().isEmpty()) {
                new MessageDialog(1, "Please enter your full login details!", 450, 200);
            } else {
                JSONArray jsonArray;
                final String login = loginTF.getText();
                final String pass = /*RandomStringUtils.randomAlphanumeric(16) +*/ Base64.getEncoder().encodeToString(passTF.getText().getBytes());
                if (deviceJson[0] == null) {
                    JSONObject newDeviceJson = new JSONObject();
                    jsonArray = new JSONArray();
                    newDeviceJson.put("credentials", jsonArray);

                    JSONObject credentialsJson = new JSONObject();
                    credentialsJson.put("primaryKey", modCBox.getSelectionModel().getSelectedItem().getPluginName());
                    credentialsJson.put("login", login);
                    credentialsJson.put("password", pass);
                    jsonArray.put(credentialsJson);

                    JsonParser.writeJsonObjectToFile(newDeviceJson, new File(Configuration.config.getConfigDirFile() + File.separator + "plugins"), "credentials.json");
                    deviceJson[0] = newDeviceJson;
                    content.getChildren().remove(saveButton);
                } else {
                    boolean addEntry = true;
                    jsonArray = deviceJson[0].getJSONArray("credentials");
                    JSONObject currentCredentials;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        currentCredentials = jsonArray.getJSONObject(i);
                        if (jsonArray.getJSONObject(i).getString("primaryKey").equals(modCBox.getSelectionModel().getSelectedItem().getPluginName())) {
                            currentCredentials.put("login", login);
                            currentCredentials.put("password", pass);
                            addEntry = false;
                            break;
                        }
                    }

                    if (addEntry) {
                        //if the plugin json can not be found earlier, add a new entry
                        JSONObject credentialsJson = new JSONObject();
                        credentialsJson.put("primaryKey", modCBox.getSelectionModel().getSelectedItem().getPluginName());
                        credentialsJson.put("login", login);
                        credentialsJson.put("password", pass);
                        jsonArray.put(credentialsJson);
                    }

                    JsonParser.writeJsonObjectToFile(deviceJson[0], new File(Configuration.config.getConfigDirFile() + File.separator + "plugins"), "credentials.json");
                }

                modCBox.getSelectionModel().getSelectedItem().setPluginCredentials(new Credentials(loginTF.getText(), passTF.getText()));
                setStatus("neutral", LanguageController.getString("browser_info_updated"));
                content.getChildren().remove(saveButton);
                if (!content.getChildren().contains(deleteButton)) { content.getChildren().add(deleteButton); }
            }
        });

        return content;
    }

    private HBox getBrowserSelectionSection() {
        HBox content = new HBox();
        content.setSpacing(10);

        ComboBox<BotType.Bot> browserSelector = new ComboBox<>();
        browserSelector.setPrefWidth(120);
        browserSelector.setPromptText("NONE");
        browserSelector.setStyle("-fx-font-size: 10pt;");

        for (BotType.Bot bot : BotType.Bot.values()) {
            if (bot.getBotPlatform() == BotPlatforms.WEB) {
                browserSelector.getItems().add(bot);
            }
        }

        final JSONObject[] deviceJson = {JsonParser.readJsonFromFile(new File(BotConfig.getModulesDirFile() + File.separator + "devices" + File.separator + modCBox.getSelectionModel().getSelectedItem().getPluginType().getDisplayableType().toLowerCase() + ".json"))};
        if (deviceJson[0] != null) {
            for (BotType.Bot bot : BotType.Bot.values()) {
                if (BotType.Bot.valueOf(deviceJson[0].getString("browser")) == bot && bot.getBotPlatform() == BotPlatforms.WEB) {
                    browserSelector.getSelectionModel().select(bot);
                    PluginConfig.botType = bot;
                    break;
                }
            }
        }

        Button saveButton = new Button(LanguageController.getString("saveCfg"));
        saveButton.setStyle("-fx-font-size: 10pt;");
        saveButton.setVisible(false);

        //ActionListener
        browserSelector.valueProperty().addListener((ov, t, t1) -> {
            PluginConfig.botType = t1;
            saveButton.setVisible(true);

        });

        saveButton.setOnAction(event -> {
            if (deviceJson[0] == null) {
                deviceJson[0] = new JSONObject();
                deviceJson[0].put("browser", browserSelector.getSelectionModel().getSelectedItem());
            } else {
                deviceJson[0].put("browser", browserSelector.getSelectionModel().getSelectedItem());
            }

            JsonParser.writeJsonObjectToFile(deviceJson[0], new File(BotConfig.getModulesDirFile() + File.separator + "devices"), modCBox.getSelectionModel().getSelectedItem().getPluginType().getDisplayableType().toLowerCase() + ".json");
            setStatus("neutral", LanguageController.getString("device_info_updated"));
            saveButton.setVisible(false);
        });

        content.getChildren().addAll(browserSelector, saveButton);
        return content;
    }

    private VBox getAndroidDeviceSelection() {
        LogFileHandler.logger.info("Loading android device selection");
        VBox content = new VBox();
        content.setSpacing(5);

        Label nameLabel = new Label(LanguageController.getString("devicename"));
        Label verLabel = new Label(LanguageController.getString("deviceversion"));
        Label serialLabel = new Label(LanguageController.getString("deviceserial"));
        TextField nameTF = new TextField();
        TextField verTF = new TextField();
        TextField deviceSerialTF = new TextField();

        final JSONObject[] deviceJson = {JsonParser.readJsonFromFile(new File(BotConfig.getModulesDirFile() + File.separator + "devices" + File.separator + modCBox.getSelectionModel().getSelectedItem().getPluginType().getDisplayableType().toLowerCase() + ".json"))};
        if (deviceJson[0] != null) {
            nameTF.setText(deviceJson[0].getJSONObject("1").getString("name"));
            verTF.setText(deviceJson[0].getJSONObject("1").getString("version"));
            deviceSerialTF.setText(deviceJson[0].getJSONObject("1").getString("serial"));
            //also update the current device info variables
            updateAndroidDeviceInfo(nameTF.getText(), verTF.getText(), deviceSerialTF.getText());
        }

        Button saveButton = new Button(LanguageController.getString("saveCfg"));
        saveButton.setVisible(false);

        //ActionListener
        nameTF.setOnKeyReleased(event -> saveButton.setVisible(true));
        verTF.setOnKeyReleased(event -> saveButton.setVisible(true));
        deviceSerialTF.setOnKeyReleased(event -> saveButton.setVisible(true));


        saveButton.setOnAction(event -> {
            if (deviceJson[0] == null) {
                deviceJson[0] = new JSONObject();
                JSONObject deviceObj = new JSONObject();
                deviceObj.put("name", nameTF.getText());
                deviceObj.put("version", verTF.getText());
                deviceObj.put("serial", deviceSerialTF.getText());
                deviceJson[0].put("1", deviceObj);
            } else {
                deviceJson[0].getJSONObject("1").put("name", nameTF.getText());
                deviceJson[0].getJSONObject("1").put("version", verTF.getText());
                deviceJson[0].getJSONObject("1").put("serial", deviceSerialTF.getText());
            }

            JsonParser.writeJsonObjectToFile(deviceJson[0], new File(BotConfig.getModulesDirFile() + File.separator + "devices"), modCBox.getSelectionModel().getSelectedItem().getPluginType().getDisplayableType().toLowerCase() + ".json");
            updateAndroidDeviceInfo(nameTF.getText(), verTF.getText(), deviceSerialTF.getText());
            setStatus("neutral", LanguageController.getString("browser_info_updated"));
            saveButton.setVisible(false);
        });

        updateAndroidDeviceInfo(nameTF.getText(), verTF.getText(), deviceSerialTF.getText());

        content.getChildren().addAll(nameLabel, nameTF, verLabel, verTF, serialLabel, deviceSerialTF, saveButton);
        return content;
    }

    private void updateAndroidDeviceInfo(String name, String version, String udid) {
        BotConfig.DEVICE_NAME = name;
        BotConfig.VERSION = version;
        BotConfig.UDID = udid;
    }

    @Override
    public Node getLayout() {
        return null;
    }

    @Override
    public void postInit() {

    }
}

