package com.rkarp.botter.view;

import net.rickiekarp.core.controller.LanguageController;
import net.rickiekarp.core.debug.DebugHelper;
import net.rickiekarp.core.debug.LogFileHandler;
import net.rickiekarp.core.util.parser.JsonParser;
import net.rickiekarp.core.settings.Configuration;
import net.rickiekarp.core.view.MainScene;
import com.rkarp.botlib.BotConfig;
import com.rkarp.botlib.PluginConfig;
import com.rkarp.botlib.enums.BotType;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import org.json.JSONObject;

import java.io.File;
import java.util.logging.Level;

public class BotSetupLayout {
    private String[] supportedBrowsers      = {"chrome", "firefox"};
    private int browserSetupStep            = 1;
    private Label messageLabel;
    private GridPane setupGrid;
    private Button prevButton, nextButton;

    private final double MIN_TILE_SIZE      = 50;
    private double nColumns                 = supportedBrowsers.length;
    private double nRows                    = 1;
    private DoubleProperty prefTileSize     = new SimpleDoubleProperty(MIN_TILE_SIZE);

    public Node getLayout() {
        BorderPane borderpane = new BorderPane();

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.BOTTOM_CENTER);

        messageLabel = new Label();
        messageLabel.setPadding(new Insets(30, 0, 0, 0));

        // create a color swatch.
        setupGrid = new GridPane();
        setupGrid.setAlignment(Pos.CENTER);
        setupGrid.getStylesheets().add("ui/icons/browser/LogoStyle.css");

        VBox.setVgrow(setupGrid, Priority.ALWAYS);
        vbox.getChildren().addAll(messageLabel, setupGrid);

        //controls
        HBox controls = new HBox();
        controls.setPadding(new Insets(10, 12, 10, 12));  //padding top, left, bottom, right
        controls.setSpacing(10);
        controls.setAlignment(Pos.CENTER_RIGHT);

        prevButton = new Button(LanguageController.getString("back"));
        prevButton.setVisible(false);
        prevButton.setOnAction(event -> {
            setupGrid.getChildren().clear();
            browserSetupStep--;
            showSetupStep(browserSetupStep);
        });
        controls.getChildren().add(prevButton);

        nextButton = new Button(LanguageController.getString("next"));
        nextButton.setOnAction(event -> {
            setupGrid.getChildren().clear();
            browserSetupStep++;
            if (browserSetupStep == 4 && PluginConfig.botType == BotType.Bot.FIREFOX) {
                browserSetupStep++;
            }
            showSetupStep(browserSetupStep);
        });
        controls.getChildren().add(nextButton);

        showSetupStep(browserSetupStep);

        //set layouts
        borderpane.setCenter(vbox);
        borderpane.setBottom(controls);

        setupGrid.layoutBoundsProperty().addListener((observableValue, oldBounds, newBounds) -> {
            prefTileSize.set(Math.max(MIN_TILE_SIZE, Math.min(newBounds.getWidth() / nColumns, newBounds.getHeight() / nRows)));
            calculateSizes();
        });

        if (DebugHelper.isDebugVersion()) {
            setupGrid.setStyle("-fx-background-color: gray;");
        }

        LogFileHandler.logger.log(Level.INFO, "open.BotSetupDialog");
        return borderpane;
    }

    private void showSetupStep(int step) {
        switch (step) {

            //set nodejs path
            case 1:
                messageLabel.setText(LanguageController.getString("desc_setup_1_0"));
                nextButton.setDisable(true);

                Label desc_setup_4_1 = new Label(LanguageController.getString("desc_setup_1_1"));
                desc_setup_4_1.setAlignment(Pos.CENTER);
                desc_setup_4_1.setMaxHeight(100);
                desc_setup_4_1.setWrapText(true);
                GridPane.setConstraints(desc_setup_4_1, 0, 0);

                Button searchNode = new Button(LanguageController.getString("search"));
                searchNode.setMaxHeight(100);
                searchNode.setAlignment(Pos.CENTER);
                searchNode.setOnAction(event -> {
                    DirectoryChooser directoryChooser = new DirectoryChooser();
                    directoryChooser.setInitialDirectory(Configuration.config.getJarFile().getParentFile());
                    File selectedDirectory = directoryChooser.showDialog(MainScene.mainScene.getWindowScene().getWindow());

                    if (selectedDirectory != null) {
                        setupGrid.getChildren().clear();
                        Label text = new Label(selectedDirectory.getPath());
                        GridPane.setConstraints(text, 0, 0);
                        setupGrid.getChildren().add(text);
                        BotConfig.nodeBinary = selectedDirectory.getPath();
                        nextButton.setDisable(false);
                    }
                });
                GridPane.setConstraints(searchNode, 0, 1);

                setupGrid.getChildren().addAll(desc_setup_4_1, searchNode);
                calculateSizes();
                break;

            //select browser
            case 2:
                messageLabel.setText(LanguageController.getString("desc_setup_2_0"));
                prevButton.setVisible(true);
                nextButton.setDisable(true);

                // create n button controls for ui.icons.browser selection
                final ToggleGroup group = new ToggleGroup();
                for (int i = 1; i <= nColumns; i++) {
                    // create a ToggleButton for choosing a browser.
                    final ToggleButton browserChoice = new ToggleButton();
                    browserChoice.setToggleGroup(group);
                    browserChoice.setUserData(i);
                    browserChoice.getStyleClass().add(getBrowserStyle(i));

                    // position the button in the grid.
                    GridPane.setConstraints(browserChoice, i, 0);
                    browserChoice.setMinSize(MIN_TILE_SIZE, MIN_TILE_SIZE);
                    browserChoice.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    setupGrid.getChildren().add(browserChoice);
                }

                group.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
                    if (group.getSelectedToggle() != null) {
                        switch ((int) group.getSelectedToggle().getUserData()) {
                            case 1: PluginConfig.botType = BotType.Bot.CHROME; break;
                            case 2: PluginConfig.botType = BotType.Bot.FIREFOX; break;
                        }
                        nextButton.setDisable(false);
                    } else {
                        PluginConfig.botType = BotType.Bot.NONE;
                        nextButton.setDisable(true);
                    }
                });
                calculateSizes();
                break;

//            //select driver location
//            case 2:
//                messageLabel.setText(LanguageController.getString("desc_setup_2_0"));
//                prevButton.setVisible(true);
//                nextButton.setDisable(true);
//
//                // create a ToggleButton for choosing a browser.
//                final Button browserChoice = new Button(LanguageController.getString("search"));
//                browserChoice.setOnAction(event -> {
//                    DirectoryChooser directoryChooser = new DirectoryChooser();
//                    directoryChooser.setInitialDirectory(BotConfig.getModulesDirFile());
//                    File selectedDirectory = directoryChooser.showDialog(MainScene.mainScene.getWindowScene().getWindow());
//
//                    if (selectedDirectory != null) {
//                        setupGrid.getChildren().clear();
//                        Label text = new Label(selectedDirectory.getPath());
//                        GridPane.setConstraints(text, 0, 0);
//                        setupGrid.getChildren().add(text);
//                        PluginConfig.driverPath = selectedDirectory.getPath();
//                        nextButton.setDisable(false);
//                    }
//                });
//
//                final Button browserChoice1 = new Button(LanguageController.getString("download"));
//                browserChoice1.setOnAction(event -> {
//                    try {
//                        switch (PluginConfig.botType) {
//                            case CHROME: Desktop.getDesktop().browse(new URI(driverDownload[0])); break;
//                            case FIREFOX: Desktop.getDesktop().browse(new URI(driverDownload[1])); break;
//                        }
//                    } catch (IOException | URISyntaxException e1) {
//                        e1.printStackTrace();
//                    }
//                });
//
//                Label test = new Label(LanguageController.getString("desc_setup_2_1"));
//                test.setAlignment(Pos.CENTER);
//                GridPane.setConstraints(test, 0, 0);
//
//                Label test1 = new Label(LanguageController.getString("desc_setup_2_2"));
//                test1.setAlignment(Pos.CENTER);
//                GridPane.setConstraints(test1, 1, 0);
//
//
//                // position the button in the grid.
//                GridPane.setConstraints(browserChoice, 0, 1);
//                GridPane.setConstraints(browserChoice1, 1, 1);
//
//                browserChoice.setMinSize(MIN_TILE_SIZE, MIN_TILE_SIZE);
//                browserChoice.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//                setupGrid.getChildren().addAll(test, browserChoice, test1, browserChoice1);
//                calculateSizes();
//                break;

            //set profile name
            case 3:
                messageLabel.setText(LanguageController.getString("desc_setup_3_0"));
                nextButton.setDisable(true);

                Label testt = new Label(LanguageController.getString("desc_setup_3_1"));
                testt.setAlignment(Pos.CENTER);
                testt.setMaxHeight(50);
                GridPane.setConstraints(testt, 0, 0);

                TextField profileNameTextField = new TextField();
                profileNameTextField.setMaxHeight(50);
                profileNameTextField.setAlignment(Pos.CENTER);

                profileNameTextField.setOnKeyReleased(ke -> {
                    if (!profileNameTextField.getText().isEmpty()) {
                        nextButton.setDisable(false);
                        PluginConfig.browserProfileName = profileNameTextField.getText();
                        if (ke.getCode().equals(KeyCode.ENTER)) {
                            nextButton.fire();
                        }
                    } else {
                        nextButton.setDisable(true);
                    }
                });
                GridPane.setConstraints(profileNameTextField, 0, 1);


//                ComboBox<BotType.Bot> typeField = new ComboBox<>();
//                typeField.getItems().setAll(BotType.Bot.values());
//                typeField.setMaxHeight(50);
//                GridPane.setConstraints(profileNameTextField, 0, 2);

                setupGrid.getChildren().addAll(testt, profileNameTextField);
                calculateSizes();
                break;

            //set chrome config directory
            case 4:
                messageLabel.setText(LanguageController.getString("desc_setup_4_0"));
                prevButton.setVisible(true);
                nextButton.setDisable(true);

                Label testt2 = new Label(LanguageController.getString("desc_setup_4_1"));
                testt2.setAlignment(Pos.CENTER);
                testt2.setMaxHeight(100);
                GridPane.setConstraints(testt2, 0, 0);

                Button searchChrome = new Button(LanguageController.getString("search"));
                searchChrome.setMaxHeight(100);
                searchChrome.setAlignment(Pos.CENTER);
                searchChrome.setOnAction(event -> {
                    DirectoryChooser directoryChooser = new DirectoryChooser();
                    File selectedDirectory = directoryChooser.showDialog(MainScene.mainScene.getWindowScene().getWindow());

                    if (selectedDirectory != null) {
                        setupGrid.getChildren().clear();
                        Label text = new Label(selectedDirectory.getPath());
                        GridPane.setConstraints(text, 0, 0);
                        setupGrid.getChildren().add(text);
                        PluginConfig.chromeConfigDirectory = selectedDirectory.getPath();
                        nextButton.setDisable(false);
                    }
                });
                GridPane.setConstraints(searchChrome, 0, 1);

                setupGrid.getChildren().addAll(testt2, searchChrome);
                calculateSizes();
                nextButton.setText(LanguageController.getString("finish"));
                break;

            case 5:
                writeBrowserSetting(); //save browser selection
                Configuration.config.saveProperties(PluginConfig.class);
                Configuration.config.saveProperties(BotConfig.class);
                MainScene.mainScene.getBorderPane().setCenter(null);
                MainLayout layout = new MainLayout();
                MainScene.mainScene.getBorderPane().setTop(layout.getLaunchNode());
                break;
        }
    }

    private String getBrowserStyle(int styleType) {
        switch (styleType) {
            case 1:
                return "logo-button-chrome";
            case 2:
                return "logo-button-firefox";
            default: return "";
        }
    }
    private void calculateSizes() {
        for (Node child : setupGrid.getChildren()) {
            Control tile = (Control) child;
            final double margin = prefTileSize.get() / 15;
            tile.setPrefSize(prefTileSize.get(), prefTileSize.get());
            GridPane.setMargin(child, new Insets(margin));
        }
    }

    private void writeBrowserSetting() {
        final JSONObject[] deviceJson = {JsonParser.readJsonFromFile(new File(BotConfig.getModulesDirFile() + File.separator + "devices" + File.separator + PluginConfig.botType.getDisplayableType().toLowerCase() + ".json"))};
        if (deviceJson[0] == null) {
            deviceJson[0] = new JSONObject();
            deviceJson[0].put("browser", PluginConfig.botType);
        } else {
            deviceJson[0].put("browser", PluginConfig.botType);
        }
        JsonParser.writeJsonObjectToFile(deviceJson[0], new File(BotConfig.getModulesDirFile() + File.separator + "devices"), PluginConfig.botType.getDisplayableType().toLowerCase() + ".json");
    }
 }

