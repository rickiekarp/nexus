package net.rickiekarp.flc.view.dialogs;

import net.rickiekarp.core.controller.LanguageController;
import net.rickiekarp.core.debug.DebugHelper;
import net.rickiekarp.core.debug.LogFileHandler;
import net.rickiekarp.core.ui.windowmanager.ImageLoader;
import net.rickiekarp.core.ui.windowmanager.WindowScene;
import net.rickiekarp.core.ui.windowmanager.WindowStage;
import net.rickiekarp.flc.tasks.ListTask;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This class is used for creating the 'In progress' loading dialog.
 */
public class ProgressDialog extends Stage {

    public ProgressDialog() {
        createProgressDialog(400, 250);
    }

    private void createProgressDialog(int width, int height) {
        this.getIcons().add(ImageLoader.getAppIconSmall());
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setWidth(width); this.setHeight(height);
        this.setTitle(LanguageController.getString("inprogress"));

        // The UI (Client Area) to display
        BorderPane borderpane = new BorderPane();

        VBox vbox = new VBox();vbox.setSpacing(15);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(30));

        AnchorPane controls = new AnchorPane();
        controls.setPadding(new Insets(10, 7, 10, 7));  //padding top, left, bottom, right

        // Components
        Label label = new Label(LanguageController.getString("scanning"));

        ProgressBar loadBar = new ProgressBar(0);
        loadBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);

        Button abort = new Button(LanguageController.getString("abort"));

        // Add components to layout
        vbox.getChildren().addAll(label, loadBar);
        controls.getChildren().add(abort);
        AnchorPane.setRightAnchor(abort, 7.0);
        AnchorPane.setBottomAnchor(abort, 0.0);

        borderpane.setCenter(vbox);
        borderpane.setBottom(controls);

        // The Window as a Scene
        WindowScene modalDialogScene = new WindowScene(new WindowStage("progress", this), borderpane, 1);

        this.setScene(modalDialogScene);
        this.show();

        LogFileHandler.logger.info("open.progressDialog");

        if (DebugHelper.isDebugVersion()) {
            vbox.setStyle("-fx-background-color: gray");
            controls.setStyle("-fx-background-color: #444444;");
        }

        abort.setOnAction(event -> ListTask.listTask.cancel());
    }

    public void close() {
        Platform.runLater(() -> this.fireEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSE_REQUEST)));
    }
}
