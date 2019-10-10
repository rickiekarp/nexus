package net.rickiekarp.botter.view;

import net.rickiekarp.core.AppContext;
import net.rickiekarp.core.controller.LanguageController;
import net.rickiekarp.core.debug.DebugHelper;
import net.rickiekarp.core.debug.ExceptionHandler;
import net.rickiekarp.core.debug.LogFileHandler;
import net.rickiekarp.core.net.NetResponse;
import net.rickiekarp.core.net.update.FileDownloader;
import net.rickiekarp.core.ui.windowmanager.WindowStage;
import net.rickiekarp.core.settings.Configuration;
import net.rickiekarp.core.ui.windowmanager.WindowScene;
import net.rickiekarp.core.util.FileUtil;
import net.rickiekarp.core.ui.windowmanager.ImageLoader;
import net.rickiekarp.core.view.MainScene;
import net.rickiekarp.botlib.enums.BotPlatforms;
import net.rickiekarp.botlib.model.PluginData;
import net.rickiekarp.botlib.net.BotNetworkApi;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PluginManagerLayout {
    private TableView<PluginData> pluginTable;
    private Label status;
    private ProgressIndicator progressIndicator;

    public PluginManagerLayout() {
        create();
        setupTable();
        loadRemotePlugins();
    }

    private void loadRemotePlugins() {
        setLoadingBar(LanguageController.getString("loading"), true);

//        new Thread(() -> {
//            //list all plugins where a new version needs to be fetched
//            List<PluginData> toFetchVersion = new ArrayList<>();
//            for (int i = 0; i < PluginData.pluginData.size(); i++) {
//                if (PluginData.pluginData.get(i).getPluginNewVersion() == null) {
//                    toFetchVersion.add(PluginData.pluginData.get(i));
//                    PluginData.pluginData.get(i).setPluginNewVersion(LanguageController.getString("fetching"));
//                }
//            }
//
//            if (toFetchVersion.size() > 0 || PluginData.pluginData.size() == 0) {
//                pluginTable.refresh();
//                String response = NetResponse.getResponseString(AppContext.getContext().getNetworkApi().runNetworkAction(BotNetworkApi.requestPlugins()));
//
//                switch (response) {
//                    case "no_connection":
//                        Platform.runLater(() -> setLoadingBar(LanguageController.getString("no_connection"), false));
//                        return;
//                    case "file_not_found":
//                        Platform.runLater(() -> setLoadingBar(LanguageController.getString("no_connection"), false));
//                        return;
//                    default:
//                        JSONArray pluginArray = new JSONArray(response);
//                        JSONObject pluginEntry;
//                        entry: for (int i = 0; i < pluginArray.length(); i++) {
//                            pluginEntry = (JSONObject) pluginArray.get(i);
//                            LogFileHandler.logger.info("Remote Plugin: " + pluginEntry.getString("identifier") + " - " + pluginEntry.getString("version"));
//                            for (int localPlugin = 0 ; localPlugin < PluginData.pluginData.size(); localPlugin++) {
//                                if (pluginEntry.getString("identifier").equals(PluginData.pluginData.get(localPlugin).getPluginName())) {
//                                    PluginData.pluginData.get(i).setPluginNewVersion(pluginEntry.getString("version"));
//                                    PluginData.pluginData.get(i).setUpdateEnable(pluginEntry.getBoolean("updateEnable"));
//                                    continue entry;
//                                }
//                            }
//
//                            //if no corresponding plugin entry is found, add new entry
//                            PluginData pluginData = new PluginData(
//                                    null,
//                                    pluginEntry.getString("identifier"),
//                                    null,
//                                    pluginEntry.getString("version"),
//                                    BotPlatforms.valueOf(pluginEntry.getString("type"))
//                            );
//                            pluginData.setUpdateEnable(pluginEntry.getBoolean("updateEnable"));
//                            PluginData.pluginData.add(pluginData);
//                        }
//                        pluginTable.refresh();
//                }
//            }
//
//            //set version to 'no version found' for plugins without remote version
//            for (int i = 0; i < PluginData.pluginData.size(); i++) {
//                if (PluginData.pluginData.get(i).getPluginNewVersion().equals(LanguageController.getString("fetching"))) {
//                    PluginData.pluginData.get(i).setPluginNewVersion(LanguageController.getString("no_version_found"));
//                }
//            }
//
//            Platform.runLater(() -> setLoadingBar("", false));
//
//        }).start();
    }

    private void create() {
        Stage pluginStage = new Stage();
        pluginStage.setTitle(LanguageController.getString("pluginmanager"));
        pluginStage.getIcons().add(ImageLoader.getAppIconSmall());
        pluginStage.setResizable(true);
        //infoStage.setMinWidth(500); infoStage.setMinHeight(320);
        pluginStage.setWidth(700); pluginStage.setHeight(500);

        BorderPane contentVbox = new BorderPane();

        // The UI (Client Area) to display
        contentVbox.setCenter(getContent());

        // The Window as a Scene
        WindowStage windowStage = new WindowStage("plugin", pluginStage);
        WindowScene aboutWindow = new WindowScene(windowStage, contentVbox, 1);

        pluginStage.setScene(aboutWindow);
        pluginStage.show();

        MainScene.Companion.getStageStack().push(windowStage);

        LogFileHandler.logger.info("open.pluginmanager");
    }

    private void setLoadingBar(String text, boolean isProgressIndicatorVisible) {
        status.setText(text);
        progressIndicator.setVisible(isProgressIndicatorVisible);
    }


    private BorderPane getContent() {
        BorderPane borderpane = new BorderPane();
        borderpane.setStyle("-fx-background-color: #1d1d1d;");

        HBox controls = new HBox();

        HBox statusBox = new HBox(10);
        statusBox.setAlignment(Pos.BOTTOM_RIGHT);
        status = new Label();
        progressIndicator = new ProgressIndicator();
        progressIndicator.setMaxHeight(30);
        progressIndicator.setMaxWidth(30);
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        progressIndicator.setVisible(false);
        statusBox.getChildren().addAll(progressIndicator, status);


        pluginTable = new TableView<>();
        pluginTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        pluginTable.setPlaceholder(new Label(LanguageController.getString("no_plugin_found")));
        GridPane.setConstraints(pluginTable, 0, 1);
        GridPane.setHgrow(pluginTable, Priority.ALWAYS);

        pluginTable.setFixedCellSize(40);
        pluginTable.setItems(PluginData.pluginData);

        controls.getChildren().addAll(statusBox);

        controls.setPadding(new Insets(10, 7, 10, 7));  //padding top, left, bottom, right
        controls.setSpacing(10);
        controls.setAlignment(Pos.CENTER_RIGHT);

        if (DebugHelper.isDebugVersion()) {
            controls.setStyle("-fx-background-color: #336699;");
        } else {
            controls.setStyle(null);
        }

        //add vbox & controls pane to borderpane layout
        borderpane.setCenter(pluginTable);
        borderpane.setBottom(controls);

        return borderpane;
    }

    private void setupTable() {
        TableColumn<PluginData, String> pluginName = new TableColumn<>(LanguageController.getString("name"));
        TableColumn<PluginData, String> type = new TableColumn<>(LanguageController.getString("type"));
        TableColumn<PluginData, String> pluginVersion = new TableColumn<>(LanguageController.getString("oldVersion"));
        TableColumn<PluginData, String> newVersion = new TableColumn<>(LanguageController.getString("newVersion"));
        TableColumn<PluginData, Object> download = new TableColumn<>(LanguageController.getString("download"));

        pluginName.setCellValueFactory(new PropertyValueFactory<>("pluginName"));
        type.setCellValueFactory(new PropertyValueFactory<>("pluginType"));
        pluginVersion.setCellValueFactory(new PropertyValueFactory<>("pluginOldVersion"));
        newVersion.setCellValueFactory(new PropertyValueFactory<>("pluginNewVersion"));
//        download.setCellValueFactory(new PropertyValueFactory<>("editButton"));

        // create a cell value factory with an add button for each row in the table.
        download.setCellFactory(personBooleanTableColumn -> new AddPluginCell());

        pluginName.setStyle("-fx-alignment: CENTER-LEFT;");
        pluginVersion.setStyle("-fx-alignment: CENTER-LEFT;");
        newVersion.setStyle("-fx-alignment: CENTER-LEFT;");
        download.setStyle("-fx-alignment: CENTER-LEFT;");

        pluginTable.getColumns().addAll(pluginName, type, pluginVersion, newVersion, download);
    }

    /** A table cell containing a button for adding a new plugin. */
    private class AddPluginCell extends TableCell<PluginData, Object> {
        private final Button downloadButton = new Button();
        private final ProgressBar progressBar = new ProgressBar();

        AddPluginCell() {
            downloadButton.setVisible(false);
            downloadButton.setOnAction(actionEvent -> startDownload());
        }

        private void startDownload() {
            setGraphic(progressBar);

            final FileDownloader fileDownloader;
            try {
                fileDownloader = new FileDownloader(new URL(Configuration.host + "files/apps/" + AppContext.getContext().getContextIdentifier() + "/download/plugins/" + PluginData.pluginData.get(getTableRow().getIndex()).getPluginName() + ".jar") );
            } catch (MalformedURLException e) {
                if (DebugHelper.DEBUGVERSION) { e.printStackTrace(); }
                else { LogFileHandler.logger.warning(ExceptionHandler.Companion.getExceptionString(e)); }
                return;
            }

            // separate non-FX thread
            // runnable for that thread
            new Thread(() -> {
                while (fileDownloader.getStatus() == FileDownloader.DOWNLOADING) {
                    double progress = fileDownloader.getProgress();
                    Platform.runLater(() -> progressBar.setProgress(progress));

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }

                Platform.runLater(() -> {
                    downloadButton.setText(LanguageController.getString("install"));
                    downloadButton.setOnAction(actionEvent -> installPlugin());
                    setGraphic(downloadButton);
                });
            }).start();
        }

        private void installPlugin() {
            setGraphic(progressBar);
            progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);

            // separate non-FX thread
            // runnable for that thread
            new Thread(() -> {
                File pluginFile = new File(Configuration.config.getJarFile().getParentFile() + "/data/update/" + PluginData.pluginData.get(getTableRow().getIndex()).getPluginName() + ".jar");

                //move plugin to plugin directory
                if (pluginFile.exists()) {
                    FileUtil.moveFile(pluginFile.toPath(), Configuration.config.getPluginDirFile().toPath());
                } else {
                    System.out.println("Plugin does not exist in " + pluginFile.getPath());
                }

                updateLocalPluginData(Configuration.config.getPluginDirFile() + File.separator + PluginData.pluginData.get(getTableRow().getIndex()).getPluginName() + ".jar");

                Platform.runLater(() -> setGraphic(new Label(LanguageController.getString("ready"))));
            }).start();
        }

        private void updateLocalPluginData(String pluginJarPath) {
            PluginData updatedData = PluginData.pluginData.get(getTableRow().getIndex());
            List<String> manifestValues;
            try {
                manifestValues = FileUtil.readManifestPropertiesFromJar(pluginJarPath, "Main-Class", "Version");
                updatedData.setPluginClazz(manifestValues.get(0));
                updatedData.setPluginOldVersion(manifestValues.get(1));
            } catch (IOException e) {
                if (DebugHelper.DEBUGVERSION) { e.printStackTrace(); }
                else { LogFileHandler.logger.warning(ExceptionHandler.Companion.getExceptionString(e)); }
            }
            PluginData.pluginData.set(getTableRow().getIndex(), updatedData);
        }

        /** places an add button in the row only if the row is not empty. */
        @Override
        protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                if (PluginData.pluginData.get(getTableRow().getIndex()).setNewEditButtonName() != null && !downloadButton.isVisible()) {
                    downloadButton.setText(PluginData.pluginData.get(getTableRow().getIndex()).setNewEditButtonName());
                    downloadButton.setDisable(!PluginData.pluginData.get(getTableRow().getIndex()).getUpdateEnable());
                    downloadButton.setVisible(true);
                    setGraphic(downloadButton);
                }
            }
        }
    }
}