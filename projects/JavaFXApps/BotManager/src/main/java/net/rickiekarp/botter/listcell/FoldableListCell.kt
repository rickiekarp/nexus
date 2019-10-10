package net.rickiekarp.botter.listcell;

import net.rickiekarp.core.debug.DebugHelper;
import net.rickiekarp.botlib.plugin.BotSetting;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;

public class FoldableListCell extends ListCell<BotSetting> {
    private ListView list;

    public FoldableListCell(ListView list) {
        this.list = list;
    }

    @Override
    protected void updateItem(final BotSetting item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {

            final VBox vbox = new VBox();
            setGraphic(vbox);

            //title
            final Label labelHeader = new Label(item.getTitle());
            labelHeader.setGraphicTextGap(10);
            labelHeader.setId("tableview-columnheader-default-bg");
            labelHeader.setPrefWidth(list.getWidth() - 40);
            labelHeader.setPrefHeight(30);
            if (DebugHelper.DEBUGVERSION) { labelHeader.setStyle("-fx-background-color: gray;"); }

            labelHeader.setGraphic(createArrowPath(30, !item.isVisible()));

            //description
            final Label labelDescription = new Label(item.getDesc());
            labelDescription.setWrapText(true);
            labelDescription.setStyle("-fx-font-size: 9pt;");

            //node
            Node settingVBox = item.getNode();

            vbox.getChildren().add(labelHeader);
            if (item.isVisible()) { vbox.getChildren().addAll(labelDescription, settingVBox); }

            labelHeader.setOnMouseEntered(me -> {
                labelHeader.setStyle("-fx-background-color: derive(-fx-base, 5%);");
                settingVBox.setStyle("-fx-background-color: derive(-fx-base, 5%);");
            });
            labelHeader.setOnMouseExited(me -> {
                labelHeader.setStyle(null);
                settingVBox.setStyle(null);
            });

            labelHeader.setOnMouseClicked(me -> {
                if (item.isVisible()) {
                    labelHeader.setGraphic(createArrowPath(30, true));
                    for (int i = vbox.getChildren().size() - 1; i >= 1; i--) {
                        vbox.getChildren().remove(i);
                    }
                } else {
                    labelHeader.setGraphic(createArrowPath(30, false));
                    vbox.getChildren().add(labelDescription);
                    vbox.getChildren().add(item.getNode());
                }
                item.setVisible(!item.isVisible());
            });

        } else {
            setText(null);
            setGraphic(null);
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
}
