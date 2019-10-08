package net.rickiekarp.core.view.login;

import net.rickiekarp.core.controller.LanguageController;
import net.rickiekarp.core.ui.windowmanager.ThemeSelector;
import net.rickiekarp.core.ui.windowmanager.WindowScene;
import net.rickiekarp.core.ui.windowmanager.WindowStage;
import net.rickiekarp.core.ui.windowmanager.ImageLoader;
import net.rickiekarp.core.view.MainScene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class is used for creating different message dialogs.
 * Example: Error Message
 */
public class AccountScene {

    public AccountScene() {
        create(500, 400);
    }

    public void create(int width, int height) {
        Stage stage = new Stage();
        stage.getIcons().add(ImageLoader.getAppIconSmall());
        stage.setWidth(width + 50); stage.setHeight(height + 50);
        stage.setMinWidth(width); stage.setMinHeight(height);
        stage.setTitle(LanguageController.getString("account"));
        stage.initModality(Modality.APPLICATION_MODAL);
        WindowStage windowStage = new WindowStage("account", stage);

        //Layout
        BorderPane contentPane = new BorderPane();

        WindowScene modalDialogScene = new WindowScene(windowStage, contentPane, 1);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(0,0,0,20));

        HBox controls = new HBox();
        controls.setPadding(new Insets(10, 0, 10, 0));  //padding top, left, bottom, right
        controls.setAlignment(Pos.CENTER);

        ProgressIndicator progress = new ProgressIndicator();
        progress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        vbox.getChildren().add(progress);

        ListView<String> listview = new ListView<>();
        listview.setPadding(new Insets(10, 0, 0, 0));
        listview.setStyle("-fx-font-size: 11pt;");

        Button okButton = new Button("OK");
        okButton.setOnAction(arg0 -> modalDialogScene.getWin().getController().close());
        controls.getChildren().add(okButton);

        // The UI (Client Area) to display
        contentPane.setCenter(vbox);
        contentPane.setBottom(controls);

        ThemeSelector.setTheme(modalDialogScene, this.getClass().getClassLoader());

        stage.setScene(modalDialogScene);
        stage.show();

        MainScene.stageStack.push(windowStage);
    }
}
