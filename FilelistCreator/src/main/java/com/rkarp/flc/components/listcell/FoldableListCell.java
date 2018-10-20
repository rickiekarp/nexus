package com.rkarp.flc.components.listcell;

import net.rickiekarp.core.controller.LanguageController;
import net.rickiekarp.core.debug.DebugHelper;
import net.rickiekarp.core.debug.LogFileHandler;
import net.rickiekarp.core.view.AboutScene;
import net.rickiekarp.core.view.SettingsScene;
import com.rkarp.flc.controller.FilelistController;
import com.rkarp.flc.model.FilelistSettings;
import com.rkarp.flc.settings.AppConfiguration;
import com.rkarp.flc.tasks.FileSizeTask;
import com.rkarp.flc.tasks.FilelistPreviewTask;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;

public class FoldableListCell extends ListCell<FilelistSettings> {

    public static FoldableListCell foldableListCell;
    private final ListView list;

    private VBox vboxContent_0;
    private VBox vboxContent_1;
    private VBox vboxContent_2;

    public FoldableListCell(ListView list) {
        this.list = list;
        foldableListCell = this;
    }

    @Override
    protected void updateItem(final FilelistSettings item, boolean empty) {
        super.updateItem(item, empty);

        if (!empty) {
            final VBox vbox = new VBox();
            setGraphic(vbox);

            final Label labelHeader = new Label(LanguageController.getString(item.getTitle()));
            labelHeader.setGraphicTextGap(10);
            labelHeader.setId("tableview-columnheader-default-bg");
            labelHeader.setPrefWidth(list.getWidth() - 40);
            labelHeader.setPrefHeight(30);
            if (DebugHelper.DEBUGVERSION) { labelHeader.setStyle("-fx-background-color: gray;"); }

            vbox.getChildren().add(labelHeader);

            labelHeader.setGraphic(createArrowPath(30, !item.isHidden()));

            VBox settingVBox = loadVBox(item.getTitle());

            labelHeader.setOnMouseEntered(me -> {
                labelHeader.setStyle("-fx-background-color: derive(-fx-base, 5%);");
                settingVBox.setStyle("-fx-background-color: derive(-fx-base, 5%);");
            });
            labelHeader.setOnMouseExited(me -> {
                labelHeader.setStyle(null);
                settingVBox.setStyle(null);
            });

            if (!item.isHidden()) { vbox.getChildren().add(settingVBox); }

            labelHeader.setOnMouseClicked(me -> {
                item.setHidden(!item.isHidden());

                if (item.isHidden()) {
                    switch (getItem().getTitle()) {
                        case "flSetting_0":
                            labelHeader.setGraphic(createArrowPath(30, false));
                            for (int i = 1; i < vbox.getChildren().size(); i++) {
                                vbox.getChildren().remove(i);
                            }
                            break;
                        case "flSetting_1":
                            labelHeader.setGraphic(createArrowPath(30, false));
                            for (int i = 1; i < vbox.getChildren().size(); i++) {
                                vbox.getChildren().remove(i);
                            }
                            break;
                        case "flSetting_2":
                            labelHeader.setGraphic(createArrowPath(30, false));
                            for (int i = 1; i < vbox.getChildren().size(); i++) {
                                vbox.getChildren().remove(i);
                            }
                            break;
                        case "scSetting_0":
                            labelHeader.setGraphic(createArrowPath(30, false));
                            for (int i = 1; i < vbox.getChildren().size(); i++) {
                                vbox.getChildren().remove(i);
                            }
                            break;
                    }
                } else {
                    switch (getItem().getTitle()) {
                        case "flSetting_0":
                            labelHeader.setGraphic(createArrowPath(30, true));
                            vbox.getChildren().add(loadVBox("flSetting_0"));
                            break;
                        case "flSetting_1":
                            labelHeader.setGraphic(createArrowPath(30, true));
                            vbox.getChildren().add(loadVBox("flSetting_1"));
                            break;
                        case "flSetting_2":
                            labelHeader.setGraphic(createArrowPath(30, true));
                            vbox.getChildren().add(loadVBox("flSetting_2"));
                            break;
                        case "scSetting_0":
                            labelHeader.setGraphic(createArrowPath(30, true));
                            vbox.getChildren().add(loadVBox("scSetting_0"));
                            break;
                    }
                }
            });
        } else {
            setText(null);
            setGraphic(null);
        }
    }

    private VBox loadVBox(String name) {
        switch (name) {
            case "flSetting_0": return getGeneralOptions();
            case "flSetting_1": return getContentVBox1();
            case "flSetting_2": return getContentVBox2();
            default: return null;
        }
    }


    private SVGPath createArrowPath(int height, boolean up) {
        SVGPath svg = new SVGPath();
        int width = height / 4;
        if (up) {
            svg.setContent("M" + width + " 0 L" + (width * 2) + " " + width + " L0 " + width + " Z");
            svg.setStroke(Paint.valueOf("white"));
            svg.setFill(Paint.valueOf("white"));
        }
        else {
            svg.setContent("M0 0 L" + (width * 2) + " 0 L" + width + " " + width + " Z");
            svg.setStroke(Paint.valueOf("white"));
            svg.setFill(Paint.valueOf("white"));
        }
        return svg;
    }

    private VBox getGeneralOptions() {

        VBox content = new VBox();
        content.setSpacing(5);

        Button btn_settings = new Button();
        btn_settings.setTooltip(new Tooltip(LanguageController.getString("settings")));
        btn_settings.getStyleClass().add("decoration-button-settings");

        Button btn_about = new Button();
        btn_about.setTooltip(new Tooltip(LanguageController.getString("about")));
        btn_about.getStyleClass().add("decoration-button-about");

        btn_settings.setOnAction(event -> new SettingsScene());
        btn_about.setOnAction(event -> new AboutScene());

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(5);
        hbox.getChildren().addAll(btn_settings, btn_about);

        content.getChildren().addAll(hbox);
        return content;
    }

    private VBox getContentVBox1() {

        VBox content = new VBox();
        content.setSpacing(5);

        FilelistSettings l = (FilelistSettings) list.getItems().get(0);

        Label option1_desc = new Label(LanguageController.getString(l.getDesc()));
        option1_desc.setWrapText(true);
        option1_desc.setStyle("-fx-font-size: 9pt;");
        option1_desc.setMaxWidth(175);

        Label option = new Label(LanguageController.getString("grouping"));
        Label option1 = new Label(LanguageController.getString("unit"));

        ComboBox<String> cbox_sorting = new ComboBox<>();
        cbox_sorting.getItems().addAll(LanguageController.getString("none"), LanguageController.getString("folder"));
        cbox_sorting.getSelectionModel().select(0);
        cbox_sorting.setMinWidth(100);

        ComboBox<String> cbox_unit = new ComboBox<>();
        cbox_unit.getItems().addAll(AppConfiguration.unitList);
        cbox_unit.getSelectionModel().select(FilelistController.UNIT_IDX);
        cbox_unit.setMinWidth(100);

        CheckBox header = new CheckBox(LanguageController.getString("headerShow"));
        header.setSelected(FilelistController.canShowHeader);

        CheckBox empty = new CheckBox(LanguageController.getString("emptyFolderShow"));
        empty.setSelected(true);

        cbox_sorting.valueProperty().addListener((ov, t, t1) -> {
            FilelistController.sortingIdx = cbox_sorting.getSelectionModel().getSelectedIndex();
            if (AppConfiguration.fileData.size() > 0) {
                new FilelistPreviewTask();
            }
            switch (cbox_sorting.getSelectionModel().getSelectedIndex()) {
                case 0: content.getChildren().remove(empty); break;
                case 1: content.getChildren().add(empty); break;
            }
        });

        cbox_unit.valueProperty().addListener((ov, t, t1) -> {
            FilelistController.UNIT_IDX = cbox_unit.getSelectionModel().getSelectedIndex();
            if (AppConfiguration.fileData.size() > 0) {
                new FileSizeTask();
            }
        });

        header.setOnAction(event -> {
            FilelistController.canShowHeader = header.isSelected();
            if (AppConfiguration.fileData.size() > 0) {
                new FilelistPreviewTask();
            }

        });

        empty.setOnAction(event -> {
            FilelistController.canShowEmptyFolder = empty.isSelected();
            if (AppConfiguration.fileData.size() > 0) {
                new FilelistPreviewTask();
            }
        });

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setSpacing(5);
        hbox.getChildren().addAll(cbox_sorting, option);

        HBox hbox1 = new HBox();
        hbox1.setAlignment(Pos.CENTER_LEFT);
        hbox1.setSpacing(5);
        hbox1.getChildren().addAll(cbox_unit, option1);

        content.getChildren().addAll(option1_desc, hbox, hbox1, header);
        return content;
    }

    private VBox getContentVBox2() {

        VBox content = new VBox();
        content.setSpacing(5);

        FilelistSettings l = (FilelistSettings) list.getItems().get(1);

        Label option2_desc = new Label(LanguageController.getString(l.getDesc()));
        option2_desc.setWrapText(true);
        option2_desc.setStyle("-fx-font-size: 9pt;");
        option2_desc.setMaxWidth(175);

        CheckBox filename = new CheckBox(LanguageController.getString("name"));
        filename.setSelected(true); FilelistController.option[0] = true;
        filename.setOnAction(event -> {
            FilelistController.option[0] = !FilelistController.option[0];
            LogFileHandler.logger.config("change_filename_option: " + !FilelistController.option[0] + " -> " + FilelistController.option[0]);
            if (AppConfiguration.fileData.size() > 0) { new FilelistPreviewTask(); }
        });

        CheckBox type = new CheckBox(LanguageController.getString("ftype"));
        type.setSelected(false); FilelistController.option[1] = false;
        type.setOnAction(event -> {
            FilelistController.option[1] = !FilelistController.option[1];
            LogFileHandler.logger.config("change_type_option: " + !FilelistController.option[1] + " -> " + FilelistController.option[1]);
            if (AppConfiguration.fileData.size() > 0) { new FilelistPreviewTask(); }
        });

        CheckBox path = new CheckBox(LanguageController.getString("fpath"));
        path.setSelected(true); FilelistController.option[2] = true;
        path.setOnAction(event -> {
            FilelistController.option[2] = !FilelistController.option[2];
            LogFileHandler.logger.config("change_path_option: " + !FilelistController.option[2] + " -> " + FilelistController.option[2]);
            if (AppConfiguration.fileData.size() > 0) { new FilelistPreviewTask(); }
        });

        CheckBox size = new CheckBox(LanguageController.getString("fsize"));
        size.setSelected(true); FilelistController.option[3] = true;
        size.setOnAction(event -> {
            FilelistController.option[3] = !FilelistController.option[3];
            LogFileHandler.logger.config("change_filesize_option: " + !FilelistController.option[3] + " -> " + FilelistController.option[3]);
            if (AppConfiguration.fileData.size() > 0) { new FilelistPreviewTask(); }
        });

        CheckBox created = new CheckBox(LanguageController.getString("fcreation"));
        created.setSelected(false); FilelistController.option[4] = false;
        created.setOnAction(event -> {
            FilelistController.option[4] = !FilelistController.option[4];
            LogFileHandler.logger.config("change_creationdate_option: " + !FilelistController.option[4] + " -> " + FilelistController.option[4]);
            if (AppConfiguration.fileData.size() > 0) { new FilelistPreviewTask(); }
        });

        CheckBox changed = new CheckBox(LanguageController.getString("fmodif"));
        changed.setSelected(true); FilelistController.option[5] = true;
        changed.setOnAction(event -> {
            FilelistController.option[5] = !FilelistController.option[5];
            LogFileHandler.logger.config("change_lastchanged_option: " + !FilelistController.option[5] + " -> " + FilelistController.option[5]);
            if (AppConfiguration.fileData.size() > 0) { new FilelistPreviewTask(); }
        });

        CheckBox lastAccess = new CheckBox(LanguageController.getString("faccessed"));
        lastAccess.setSelected(false); FilelistController.option[6] = false;
        lastAccess.setOnAction(event -> {
            FilelistController.option[6] = !FilelistController.option[6];
            LogFileHandler.logger.config("change_lastaccess_option: " + !FilelistController.option[6] + " -> " + FilelistController.option[6]);
            if (AppConfiguration.fileData.size() > 0) { new FilelistPreviewTask(); }
        });

        CheckBox hidden = new CheckBox(LanguageController.getString("fhidden"));
        hidden.setSelected(false); FilelistController.option[7] = false;
        hidden.setOnAction(event -> {
            FilelistController.option[7] = !FilelistController.option[7];
            LogFileHandler.logger.config("change_hidden: " + !FilelistController.option[7] + " -> " + FilelistController.option[7]);
            if (AppConfiguration.fileData.size() > 0) { new FilelistPreviewTask(); }
        });

        content.getChildren().addAll(option2_desc, filename, type, path, size, created, changed, lastAccess, hidden);
        return content;
    }

}
