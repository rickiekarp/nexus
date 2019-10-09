package net.rickiekarp.flc.view.layout;

import net.rickiekarp.core.controller.LanguageController;
import net.rickiekarp.core.debug.DebugHelper;
import net.rickiekarp.core.debug.LogFileHandler;
import net.rickiekarp.core.settings.Configuration;
import net.rickiekarp.core.ui.anim.AnimationHandler;
import net.rickiekarp.core.view.MainScene;
import net.rickiekarp.core.view.layout.AppLayout;
import net.rickiekarp.flc.listcell.FoldableListCell;
import net.rickiekarp.flc.controller.FilelistController;
import net.rickiekarp.flc.model.Directorylist;
import net.rickiekarp.flc.model.Filelist;
import net.rickiekarp.flc.model.FilelistFormats;
import net.rickiekarp.flc.model.FilelistSettings;
import net.rickiekarp.flc.settings.AppConfiguration;
import net.rickiekarp.flc.tasks.FilelistPreviewTask;
import net.rickiekarp.flc.tasks.ListTask;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.text.DecimalFormatSymbols;
import java.util.Collections;

public class MainLayout implements AppLayout {
    public static MainLayout mainLayout;
    public static TableView<Filelist> fileTable;
    public static TableView<Directorylist> dirTable;
    public static Button btn_removeAll, btn_saveFileList;
    public static TextField pathTF;
    public static TextArea previewTA;
    private Label status;
    public static ComboBox<String> cbox_saveFormat;
    public static HBox fileControls, saveControls;
    public static TableColumn<Filelist, String> fileColumn[] = new TableColumn[8];
    private ObservableList<FilelistFormats> fileListFormats = FXCollections.observableArrayList();

    public MainLayout() {
        mainLayout = this;
    }

    private Node getMainLayout() {

        BorderPane mainContent = new BorderPane();

        GridPane fileGrid = new GridPane();
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setFillWidth(true);
        columnConstraints.setHgrow(Priority.ALWAYS);
        fileGrid.getColumnConstraints().add(columnConstraints);

        GridPane settingsGrid = new GridPane();
        settingsGrid.setPrefWidth(200);
        settingsGrid.setVgap(10);
        settingsGrid.setPadding(new Insets(5, 0, 0, 0));  //padding top, left, bottom, right
        settingsGrid.setAlignment(Pos.BASELINE_CENTER);

        AnchorPane controls = new AnchorPane();
        controls.setMinHeight(50);

        fileControls = new HBox(5);

        saveControls = new HBox(8);
        saveControls.setAlignment(Pos.CENTER);

        fileGrid.setHgap(15);
        fileGrid.setVgap(5);
        fileGrid.setPadding(new Insets(5, 10, 5, 10));  //padding top, left, bottom, right

        //add components
        pathTF = new TextField(); pathTF.setEditable(false);
        fileGrid.getChildren().add(pathTF);

        Button btn_browse = new Button(LanguageController.getString("browse"));
        fileGrid.getChildren().add(btn_browse);

        CheckBox subFolderBox = new CheckBox(LanguageController.getString("inclSubdir")); //cb_subFolders.setSelected(subDirs);
        subFolderBox.setSelected(AppConfiguration.INSTANCE.getSubFolderCheck());
        fileGrid.getChildren().add(subFolderBox);

        //FILE TABLE
        fileTable = new TableView<>();
        //fileTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        fileTable.setPlaceholder(new Label(LanguageController.getString("no_file_found")));
        fileTable.setItems(null); //fixes NullPointerException when using arrow keys in empty table
        fileGrid.getChildren().add(fileTable);

        fileColumn[0] = new TableColumn<>(LanguageController.getString("name"));
        fileColumn[0].setCellValueFactory(new PropertyValueFactory<>("filename"));
        fileColumn[0].setPrefWidth(175);
        fileColumn[1] = new TableColumn<>(LanguageController.getString("ftype"));
        fileColumn[1].setCellValueFactory(new PropertyValueFactory<>("filetype"));
        fileColumn[1].setPrefWidth(100);
        fileColumn[2] = new TableColumn<>(LanguageController.getString("fpath"));
        fileColumn[2].setCellValueFactory(new PropertyValueFactory<>("filepath"));
        fileColumn[2].setPrefWidth(180);
        fileColumn[3] = new TableColumn<>(LanguageController.getString("fsize"));
        fileColumn[3].setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getSize() + " " + AppConfiguration.INSTANCE.getUnitList().get(FilelistController.getUNIT_IDX()));
            } else {
                return new SimpleStringProperty("null");
            }
        });
        fileColumn[3].setPrefWidth(75);
        fileColumn[4] = new TableColumn<>(LanguageController.getString("fcreation"));
        fileColumn[4].setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        fileColumn[4].setPrefWidth(175);
        fileColumn[5] = new TableColumn<>(LanguageController.getString("fmodif"));
        fileColumn[5].setCellValueFactory(new PropertyValueFactory<>("lastModif"));
        fileColumn[5].setPrefWidth(175);
        fileColumn[6] = new TableColumn<>(LanguageController.getString("faccessed"));
        fileColumn[6].setCellValueFactory(new PropertyValueFactory<>("lastAccessDate"));
        fileColumn[6].setPrefWidth(175);
        fileColumn[7] = new TableColumn<>(LanguageController.getString("fhidden")); //column8.setVisible(false);
        fileColumn[7].setCellValueFactory(new PropertyValueFactory<>("isHidden"));

        fileTable.tableMenuButtonVisibleProperty().set(true);
        fileTable.getColumns().setAll(fileColumn.clone());

        //DIRECTORY TABLE
        dirTable = new TableView<>();
        dirTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        dirTable.setPlaceholder(new Label(LanguageController.getString("no_dir_found")));
        dirTable.setItems(null); //fixes NullPointerException when using arrow keys in empty table
        fileGrid.getChildren().add(dirTable);


        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');

        TableColumn<Directorylist, String> tcolumn1 = new TableColumn<>(LanguageController.getString("name"));
        tcolumn1.setCellValueFactory(new PropertyValueFactory<>("dir"));
        TableColumn<Directorylist, Integer> tcolumn2 = new TableColumn<>(LanguageController.getString("filesTotal"));
        tcolumn2.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getFilesTotal()).asObject());
        TableColumn<Directorylist, Integer> tcolumn3 = new TableColumn<>(LanguageController.getString("filesindir"));
        tcolumn3.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getFilesInDir()).asObject());
        TableColumn<Directorylist, Integer> tcolumn4 = new TableColumn<>(LanguageController.getString("foldersInAmount"));
        tcolumn4.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getFoldersInDir()).asObject());
        TableColumn<Directorylist, String> tcolumn5 = new TableColumn<>(LanguageController.getString("fsize"));
        tcolumn5.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getFileSizeInDir() + " B");
            } else {
                return new SimpleStringProperty("null");
            }
        });

        dirTable.tableMenuButtonVisibleProperty().set(true);
        dirTable.getColumns().setAll(tcolumn1, tcolumn2, tcolumn3, tcolumn4, tcolumn5);

        previewTA = new TextArea();
        previewTA.setMinHeight(200);
        fileGrid.getChildren().add(previewTA);

        Button btn_remove = new Button(LanguageController.getString("removeFile"));

        btn_removeAll = new Button(LanguageController.getString("removeAllFiles"));

        Label fileunit = new Label(LanguageController.getString("unit"));
        GridPane.setConstraints(fileunit, 0, 2);
        GridPane.setHalignment(fileunit, HPos.RIGHT);

        //SETTINGS LIST
        ListView<FilelistSettings> list = new ListView<>();
        GridPane.setConstraints(list, 0, 0);
        GridPane.setVgrow(list, Priority.ALWAYS);
        settingsGrid.getChildren().add(list);

        ObservableList<FilelistSettings> items = FXCollections.observableArrayList();
        if (Configuration.useSystemBorders) {
            items.add(new FilelistSettings("flSetting_0", "flSetting_0_desc", false));
        }
        items.add(new FilelistSettings("flSetting_1", "flSetting_1_desc", false));
        items.add(new FilelistSettings("flSetting_2", "flSetting_2_desc", false));
        list.setItems(items);

        list.setCellFactory(lv -> new FoldableListCell(list));


        status = new Label();
        saveControls.getChildren().add(status);

        cbox_saveFormat = new ComboBox<>();
        btn_saveFileList = new Button(LanguageController.getString("saveList"));

        AnchorPane.setLeftAnchor(fileControls, 5.0);
        AnchorPane.setBottomAnchor(fileControls, 6.0);
        AnchorPane.setRightAnchor(saveControls, 5.0);
        AnchorPane.setBottomAnchor(saveControls, 6.0);

        controls.setStyle("-fx-background-color: #1d1d1d;");


        controls.getChildren().add(fileControls);
        controls.getChildren().add(saveControls);

        GridPane.setConstraints(fileGrid, 0, 0);
        GridPane.setConstraints(settingsGrid, 1, 0);

        GridPane.setConstraints(pathTF, 0, 0);
        GridPane.setConstraints(btn_browse, 1, 0);
        GridPane.setConstraints(subFolderBox, 2, 0);
        GridPane.setConstraints(fileTable, 0, 1);
        GridPane.setColumnSpan(fileTable, 3);
        GridPane.setConstraints(dirTable, 0, 2);
        GridPane.setColumnSpan(dirTable, 3);
        GridPane.setConstraints(previewTA, 0, 3);
        GridPane.setColumnSpan(previewTA, 3);


        //add components to borderpane
        mainContent.setCenter(fileGrid);
        mainContent.setRight(settingsGrid);
        mainContent.setBottom(controls);

        btn_browse.setOnAction(event -> {
            status.setText(LanguageController.getString("files_loading"));

            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(MainScene.Companion.getMainScene().getWindowScene().getWindow());

            if (selectedDirectory == null) {
                status.setText(LanguageController.getString("no_dir_selected"));
            } else {

                //clear all data
                if (AppConfiguration.INSTANCE.getFileData().size() > 0 || AppConfiguration.INSTANCE.getDirData().size() > 0) {
                    removeAllFiles();
                }

                pathTF.setText(selectedDirectory.getPath());

                //add start directory entry
                AppConfiguration.INSTANCE.getDirData().add(new Directorylist(selectedDirectory.getPath(), 0, 0, 0, 0));

                new ListTask(selectedDirectory);
            }
        });

        //Add change listener
        fileTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected
            if (fileTable.getSelectionModel().getSelectedItem() != null) {

                //check if dirTable is selected
                if (dirTable.getSelectionModel().isSelected(dirTable.getSelectionModel().getSelectedIndex())) {
                    dirTable.getSelectionModel().select(null);
                }

                if (oldValue == null) {
                    fileControls.getChildren().add(btn_remove);
                }
            } else {
                fileControls.getChildren().remove(btn_remove);
                if (AppConfiguration.INSTANCE.getFileData().size() == 0) {
                    fileTable.setItems(null);
                    AppConfiguration.INSTANCE.getDirData().clear();
                    dirTable.setItems(null);
                    ListTask.listTask.resetTask();
                    previewTA.clear();
                    pathTF.clear();
                    fileControls.getChildren().removeAll(btn_removeAll);
                    saveControls.getChildren().removeAll(cbox_saveFormat, btn_saveFileList);
                }
            }
        });

        fileTable.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.DELETE)) {
                if (!btn_remove.isDisabled()) {
                    if (fileTable.getSelectionModel().isSelected(fileTable.getSelectionModel().getSelectedIndex())) {
                        disable(btn_remove, 1000);
                        removeFile();
                    }
                }
            }
        });

        dirTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected
            if (dirTable.getSelectionModel().getSelectedItem() != null) {

                //check if fileTable is selected
                if (fileTable.getSelectionModel().isSelected(fileTable.getSelectionModel().getSelectedIndex())) {
                    fileTable.getSelectionModel().select(null);
                }

                if (oldValue == null) {
                    fileControls.getChildren().add(btn_remove);
                }
            } else {
                fileControls.getChildren().remove(btn_remove);
                if (AppConfiguration.INSTANCE.getDirData().size() == 0) {
                    dirTable.setItems(null);
                    ListTask.listTask.resetTask();
                    previewTA.clear();
                    pathTF.clear();
                    fileControls.getChildren().removeAll(btn_removeAll);
                    saveControls.getChildren().removeAll(cbox_saveFormat, btn_saveFileList);
                }
            }
        });

        dirTable.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.DELETE)) {
                if (!btn_remove.isDisabled()) {
                    if (dirTable.getSelectionModel().isSelected(dirTable.getSelectionModel().getSelectedIndex())) {
                        disable(btn_remove, 3000);
                        removeFile();
                    }
                }
            }
        });

        btn_remove.setOnAction(event -> {
            disable(btn_remove, 3000);
            removeFile();
        });

        btn_removeAll.setOnAction(event -> {
            removeAllFiles();
        });

        subFolderBox.setOnAction(event -> {
                    AppConfiguration.INSTANCE.setSubFolderCheck(subFolderBox.isSelected());
                    if (subFolderBox.isSelected()) { status.setText(LanguageController.getString("incl_sub_on")); }
                    else { status.setText(LanguageController.getString("incl_sub_off")); }
                }
        );

        btn_saveFileList.setOnAction(event -> {
            int fileformatIDx = cbox_saveFormat.getSelectionModel().getSelectedIndex();
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter(
                            fileListFormats.get(fileformatIDx).getFileTypeName(),
                            fileListFormats.get(fileformatIDx).getFileTypeEnding())
            );
            File file = fileChooser.showSaveDialog(MainScene.Companion.getMainScene().getWindowScene().getWindow());

            if (file != null) {
                FilelistController.getFlController().saveToFile(file, fileformatIDx);
                AnimationHandler.statusFade(status, "success", LanguageController.getString("save_filelist_success"));
                LogFileHandler.logger.info("save.filelist:Success");
            } else {
                AnimationHandler.statusFade(status, "neutral", LanguageController.getString("save_filelist_fail"));
                LogFileHandler.logger.info("save.filelist:Fail");
            }
        });

        //add all file formats to a list
        fillFileFormatList();

        //debug colors
        if (DebugHelper.isDebugVersion()) {
            controls.setStyle("-fx-background-color: #336699;");
            fileGrid.setStyle("-fx-background-color: #555555");
            settingsGrid.setStyle("-fx-background-color: #444444");
            settingsGrid.setGridLinesVisible(true);
            fileGrid.setGridLinesVisible(true);
        }
        else
        {
            controls.setStyle("-fx-background-color: #1d1d1d;");
            fileGrid.setStyle(null);
            settingsGrid.setStyle(null);
            settingsGrid.setGridLinesVisible(false);
            fileGrid.setGridLinesVisible(false);
        }

        return mainContent;
    }

    /**
     * Removes the selected index in the fileTable and calculates new file amount
     */
    private void removeFile() {

        if (fileTable.getSelectionModel().isSelected(fileTable.getSelectionModel().getSelectedIndex())) {

            //calculate new file amount
            Directorylist dir;
            for (int i = 0; i < AppConfiguration.INSTANCE.getDirData().size(); i++) {
                if (AppConfiguration.INSTANCE.getFileData().get(fileTable.getSelectionModel().getSelectedIndex()).getFilepath().equals(AppConfiguration.INSTANCE.getDirData().get(i).getDir())) {
                    dir = AppConfiguration.INSTANCE.getDirData().get(i);
                    AppConfiguration.INSTANCE.getDirData().set(i, dir).setFilesFromDir(1);
                    AppConfiguration.INSTANCE.getDirData().set(i, dir).setFilesFromTotal(1);
                    AppConfiguration.INSTANCE.getDirData().set(i, dir).setFileSizeFromDir(AppConfiguration.INSTANCE.getFileData().get(fileTable.getSelectionModel().getSelectedIndex()).getSize());
                }
            }

            //remove selected index
            AppConfiguration.INSTANCE.getFileData().remove(fileTable.getSelectionModel().getSelectedIndex());

            //if no files are in the filelist, remove the directory from the table
//        if (AppConfiguration.dirData.get(selIdx).getFilesTotal() == 0) {
//            for (int i = 0; i < AppConfiguration.dirData.size(); i++) {
//                if (AppConfiguration.fileData.get(fileTable.getSelectionModel().getSelectedIndex()).getFilepath().equals(AppConfiguration.dirData.get(i).getDir())) {
//                    AppConfiguration.dirData.remove(i);
//                }
//            }
//        }

            if (AppConfiguration.INSTANCE.getFileData().size() > 0) {
                new FilelistPreviewTask();
                status.setText(LanguageController.getString("remove_file_success"));
            } else {
                status.setText(LanguageController.getString("clear_filelist_success"));
            }
        } else if (dirTable.getSelectionModel().isSelected(dirTable.getSelectionModel().getSelectedIndex())) {
            removeFolder();
            if (AppConfiguration.INSTANCE.getDirData().size() > 0) {
                status.setText(LanguageController.getString("remove_folder_success"));
            } else {
                status.setText(LanguageController.getString("clear_filelist_success"));
            }
        }
    }

    /**
     * Removes the selected folder in the TableView
     */
    public void removeFolder() {

        ObservableList<Integer> toDelList = FXCollections.observableArrayList();

        //iterate through filedata and add 'to delete items' to a list
        for (int i = 0; i < AppConfiguration.INSTANCE.getFileData().size(); i++) {
            if (AppConfiguration.INSTANCE.getFileData().get(i).getFilepath().equals(AppConfiguration.INSTANCE.getDirData().get(dirTable.getSelectionModel().getSelectedIndex()).getDir())) {
                toDelList.add(i);
            }
        }

        //reverse sorting order of list
        Collections.reverse(toDelList);

        //delete items from list
        for (Integer aList : toDelList) {
            Filelist test = fileTable.getItems().get(aList);
            AppConfiguration.INSTANCE.getFileData().remove(test);
        }

        //remove directory entry from dirTable
        AppConfiguration.INSTANCE.getDirData().remove(dirTable.getSelectionModel().getSelectedIndex());

        if (AppConfiguration.INSTANCE.getFileData().size() > 0) { new FilelistPreviewTask(); }
        else if (AppConfiguration.INSTANCE.getFileData().size() == 0) { AppConfiguration.INSTANCE.getDirData().clear(); }
    }

    /**
     * Removes all data in the TableView
     */
    public void removeAllFiles() {
        LogFileHandler.logger.info("Removing file data...");
        previewTA.clear();
        ListTask.listTask.deleteData();
        ListTask.listTask.resetTask();
        fileControls.getChildren().remove(btn_removeAll);
        saveControls.getChildren().removeAll(cbox_saveFormat, btn_saveFileList);
        setStatus("neutral", LanguageController.getString("clear_filelist_success"));
    }

    /**
     * Sets a status text in the MainScene
     */
    public void setStatus(String type, String msg) {
        AnimationHandler.statusFade(status, type, msg);
    }

    /**
     * Adds all FileList formats to a list
     */
    private void fillFileFormatList(){
        fileListFormats.addAll(new FilelistFormats("Text (.txt)", "*.txt"));
        fileListFormats.addAll(new FilelistFormats("HTML (.html)", "*.html"));

        for (int i = 0; fileListFormats.size() > i; i++) {
            cbox_saveFormat.getItems().addAll(fileListFormats.get(i).getFileTypeName());
        }
        cbox_saveFormat.setValue(fileListFormats.get(0).getFileTypeName());
    }

    /**
     * Disables the given button for a period amount of time
     * @param b The given button
     * @param ms The sleep duration
     */
    static void disable(final Button b, final long ms) {
        b.setDisable(true);
        new SwingWorker() {
            @Override protected Object doInBackground() throws Exception {
                Thread.sleep(ms);
                return null;
            }
            @Override protected void done() {
                b.setDisable(false);
            }
        }.execute();
    }

    @Override
    public Node getLayout() {
        return getMainLayout();
    }

    @Override
    public void postInit() {

    }
}
