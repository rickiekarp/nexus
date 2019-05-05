package net.rickiekarp.core.view;

import net.rickiekarp.core.AppContext;
import net.rickiekarp.core.controller.LanguageController;
import net.rickiekarp.core.debug.DebugHelper;
import net.rickiekarp.core.debug.ExceptionHandler;
import net.rickiekarp.core.ui.windowmanager.WindowScene;
import net.rickiekarp.core.ui.windowmanager.WindowStage;
import net.rickiekarp.core.ui.windowmanager.ImageLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * About Stage GUI.
 */
public class AboutScene {
    private GridPane grid;
    private GridPane grid2;
    private HBox controls;
    private final String WEBSITE = "www.rickiekarp.net";

    public AboutScene() {
        create();
    }

    private void create() {
        Stage infoStage = new Stage();
        infoStage.setTitle(LanguageController.getString("about") + " " + AppContext.getContext().getApplicationName());
        infoStage.getIcons().add(ImageLoader.getAppIconSmall());
        infoStage.setResizable(false);
        //infoStage.setMinWidth(500); infoStage.setMinHeight(320);
        infoStage.setWidth(500); infoStage.setHeight(320);

        BorderPane contentVbox = new BorderPane();

        // The UI (Client Area) to display
        contentVbox.setCenter(getContent());

        // The Window as a Scene
        WindowScene aboutWindow = new WindowScene(new WindowStage("about", infoStage), contentVbox, 1);

        infoStage.setScene(aboutWindow);
        infoStage.show();

        debugAbout();

        MainScene.stageStack.push(new WindowStage("about", infoStage));
    }


    private BorderPane getContent() {

        BorderPane borderpane = new BorderPane();
        borderpane.setStyle("-fx-background-color: #1d1d1d;");

        HBox hbox = new HBox();

        grid = new GridPane();
        grid2 = new GridPane();
        controls = new HBox();

        hbox.setAlignment(Pos.CENTER_LEFT);

        Separator separator2 = new Separator();
        separator2.setOrientation(Orientation.VERTICAL);
        separator2.setMaxHeight(160);
        separator2.setPadding(new Insets(0, 0, 0, 0));
        if (DebugHelper.isDebugVersion()) { separator2.setStyle("-fx-background-color: red;"); }

        grid.setVgap(8);
        grid.setPadding(new Insets(20, 15, 0, 20));  //padding top, left, bottom, right
        grid.setMinWidth(180);

        grid2.setVgap(20);
        grid2.setPadding(new Insets(20, 15, 0, 20));  //padding top, left, bottom, right
        //grid2.setMaxWidth(250);

        HBox.setHgrow(grid2, Priority.ALWAYS);


        //add Grids to VBox Layout
        hbox.getChildren().add(0, grid);
        hbox.getChildren().add(1, separator2);
        hbox.getChildren().add(2, grid2);

        //add components
        Label title = new Label(AppContext.getContext().getApplicationName());
        title.setStyle("-fx-font-size: 16pt;");
        GridPane.setConstraints(title, 0, 0);
        grid.getChildren().add(title);

        ImageView logo = new ImageView(ImageLoader.getAppIcon()); logo.fitHeightProperty().setValue(60); logo.fitWidthProperty().setValue(60);
        GridPane.setHalignment(logo, HPos.CENTER);
        GridPane.setConstraints(logo, 0, 1);
        grid.getChildren().add(logo);

        Label version = new Label("Version: " + AppContext.getContext().getVersionNumber());
        version.setStyle("-fx-font-size: 12pt;");
        GridPane.setHalignment(version, HPos.CENTER);
        GridPane.setConstraints(version, 0, 2);
        grid.getChildren().add(version);

        Label description = new Label(LanguageController.getString("desc"));
        description.setMaxSize(350, 200);
        description.setWrapText(true);
        GridPane.setConstraints(description, 0, 0);
        grid2.getChildren().add(description);

        Label copyright = new Label(LanguageController.getString("copyright"));
        copyright.setStyle("-fx-font-size: 10pt;");
        GridPane.setConstraints(copyright, 0, 1);
        grid2.getChildren().add(copyright);

        Button clBtn = new Button(LanguageController.getString("changelog"));
        controls.getChildren().add(clBtn);

        Button urlBtn = new Button(LanguageController.getString("website"));
        controls.getChildren().add(urlBtn);

        controls.setPadding(new Insets(10, 7, 10, 7));  //padding top, left, bottom, right
        controls.setSpacing(10);
        controls.setAlignment(Pos.CENTER_RIGHT);

        //add vbox & controls pane to borderpane layout
        borderpane.setCenter(hbox);
        borderpane.setBottom(controls);

        clBtn.setOnAction(e -> new ChangelogScene());

        //TODO: fix the urlBtn for Linux systems
        urlBtn.setOnAction(e -> {
            try { Desktop.getDesktop().browse(new URI(WEBSITE));
            }
            catch (IOException | URISyntaxException e1) {
                if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
            }
        });

        return borderpane;
    }

    private void debugAbout() {
        if (DebugHelper.isDebugVersion()) {
            grid.setGridLinesVisible(true);
            grid.setStyle("-fx-background-color: #333333;");
            grid2.setGridLinesVisible(true);
            grid2.setStyle("-fx-background-color: #444444;");
            controls.setStyle("-fx-background-color: #336699;");
        } else {
            grid.setGridLinesVisible(false);
            grid.setStyle(null);
            grid2.setGridLinesVisible(false);
            grid2.setStyle(null);
            controls.setStyle(null);
        }
    }
}