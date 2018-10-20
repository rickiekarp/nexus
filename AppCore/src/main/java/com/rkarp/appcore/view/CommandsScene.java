package com.rkarp.appcore.view;

import com.rkarp.appcore.controller.LanguageController;
import com.rkarp.appcore.debug.DebugHelper;
import com.rkarp.appcore.debug.LogFileHandler;
import com.rkarp.appcore.settings.AppCommands;
import com.rkarp.appcore.ui.windowmanager.WindowScene;
import com.rkarp.appcore.ui.windowmanager.WindowStage;
import com.rkarp.appcore.util.ImageLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.logging.Level;

public class CommandsScene {

    public static CommandsScene commandsScene;
    private WindowScene commandsWindow;
    public WindowScene getCommandsWindow() {
        return commandsWindow;
    }

    public CommandsScene() {
            commandsScene = this;
            createStage();
    }

    private void createStage() {
        Stage commandsStage = new Stage();
        commandsStage.getIcons().add(ImageLoader.getAppIconSmall());
        commandsStage.setResizable(true);
        commandsStage.setWidth(640); commandsStage.setHeight(200 + AppCommands.commandsList.size() * 35);
        commandsStage.setMinWidth(620); commandsStage.setMinHeight(180 + AppCommands.commandsList.size() * 35);
        commandsStage.setTitle(LanguageController.getString("commands"));

        VBox contentVbox = new VBox();

        BorderPane borderpane = new BorderPane();

        GridPane grid = new GridPane();
        HBox controls = new HBox();
        controls.setPadding(new Insets(15, 12, 15, 12));  //padding top, left, bottom, right
        controls.setSpacing(10);
        controls.setAlignment(Pos.CENTER_RIGHT);

        //set Layout
        ColumnConstraints column1 = new ColumnConstraints(); column1.setPercentWidth(45);
        ColumnConstraints column2 = new ColumnConstraints(); column2.setPercentWidth(45);
        grid.getColumnConstraints().addAll(column1, column2);

        if (DebugHelper.isDebugVersion()) {
            grid.setStyle("-fx-background-color: gray;");
            grid.setGridLinesVisible(true);
            controls.setStyle("-fx-background-color: #336699;");
        }

        for (int i = 0; i < AppCommands.commandsList.size(); i++) {

            //build the commandNameLabel string
            StringBuilder sb = new StringBuilder();
            sb.append(AppCommands.commandsList.get(i).getCommandName());
            if (!AppCommands.commandsList.get(i).getCommandHelper().isEmpty()) { sb.append(" ").append(AppCommands.commandsList.get(i).getCommandHelper()); }

            Label commandNameLabel = new Label(sb.toString());
            GridPane.setConstraints(commandNameLabel, 0, i);
            grid.getChildren().add(commandNameLabel);

            Label commandDescLabel = new Label(AppCommands.commandsList.get(i).getCommandDesc());
            GridPane.setConstraints(commandDescLabel, 1, i);
            grid.getChildren().add(commandDescLabel);
        }

        Button okButton = new Button(LanguageController.getString("close"));
        controls.getChildren().add(okButton);

        grid.setAlignment(Pos.BASELINE_CENTER);
        grid.setHgap(25); grid.setVgap(15);
        grid.setPadding(new Insets(15, 0, 0, 0));

        borderpane.setCenter(grid);
        borderpane.setBottom(controls);

        okButton.setOnAction(arg0 -> commandsStage.close());


        // The UI (Client Area) to display
        contentVbox.getChildren().addAll(borderpane);
        VBox.setVgrow(borderpane, Priority.ALWAYS);

        // The Window as a Scene
        commandsWindow = new WindowScene(new WindowStage("commands", commandsStage), contentVbox, 1);

        commandsStage.setScene(commandsWindow);
        commandsStage.show();

        LogFileHandler.logger.log(Level.INFO, "open.CommandsDialog");
    }
}
