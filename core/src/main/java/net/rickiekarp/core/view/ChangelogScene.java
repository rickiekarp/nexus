package net.rickiekarp.core.view;

import net.rickiekarp.core.AppContext;
import net.rickiekarp.core.AppDatabase;
import net.rickiekarp.core.controller.LanguageController;
import net.rickiekarp.core.debug.LogFileHandler;
import net.rickiekarp.core.model.ChangelogEntry;
import net.rickiekarp.core.net.NetResponse;
import net.rickiekarp.core.net.NetworkApi;
import net.rickiekarp.core.ui.windowmanager.WindowStage;
import net.rickiekarp.core.util.parser.XmlParser;
import net.rickiekarp.core.ui.windowmanager.ThemeSelector;
import net.rickiekarp.core.ui.windowmanager.WindowScene;
import net.rickiekarp.core.util.ImageLoader;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is used for creating different message dialogs.
 * Example: Error Message
 */
public class ChangelogScene {

    public ChangelogScene() {
        create(500, 400);
    }

    public void create(int width, int height) {
        Stage stage = new Stage();
        stage.getIcons().add(ImageLoader.getAppIconSmall());
        stage.setWidth(width + 50); stage.setHeight(height + 50);
        stage.setMinWidth(width); stage.setMinHeight(height);
        stage.setTitle(LanguageController.getString("changelog"));

        //Layout
        BorderPane contentPane = new BorderPane();

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
        okButton.setOnAction(arg0 -> stage.close());
        controls.getChildren().add(okButton);

        // The UI (Client Area) to display
        contentPane.setCenter(vbox);
        contentPane.setBottom(controls);

        WindowScene modalDialogScene = new WindowScene(new WindowStage("changelog", stage), contentPane, 1);
        ThemeSelector.setTheme(modalDialogScene);

        stage.setScene(modalDialogScene);
        stage.show();

        // load changelog data
        new Thread(() -> {

            if (AppDatabase.changelogTreeMap == null) {
                AppDatabase.changelogTreeMap = getChangelogList();
            }

            Platform.runLater(() -> {
                if (AppDatabase.changelogTreeMap.size() > 0) {
                    vbox.getChildren().remove(progress);
                    vbox.getChildren().add(listview);
                    for (int i = 0; i < AppDatabase.changelogTreeMap.size(); i++) {
                        listview.getItems().addAll(
                                AppDatabase.changelogTreeMap.get(i).getVersion() + " - " + AppDatabase.changelogTreeMap.get(i).getDesc()
                        );
                    }
                } else {
                    Label message = new Label(LanguageController.getString("changelog_not_found"));
                    vbox.setAlignment(Pos.CENTER);
                    vbox.getChildren().remove(progress);
                    vbox.getChildren().add(message);
                }
            });
        }).start();

        LogFileHandler.logger.info("open.ChangelogScene");
    }

    /**
     * Compares local and remote program versions
     * @return Returns update status ID as an integer
     */
    private ArrayList<ChangelogEntry> getChangelogList() {
        ArrayList<ChangelogEntry> changelogList = new ArrayList<>();
        String remoteVersionsXml = NetResponse.getResponseString(AppContext.getContext().getNetworkApi().runNetworkAction(NetworkApi.requestChangelog()));

        try {
            Document doc = XmlParser.stringToDom(remoteVersionsXml);
            doc.getDocumentElement().normalize();

            NodeList moduleList = doc.getElementsByTagName("change");

            if (moduleList.getLength() > 0) {
                Element labTest;
                for (int i = 0; i < moduleList.getLength(); ++i) {
                    labTest = (Element) moduleList.item(i);
                    changelogList.add(new ChangelogEntry(labTest.getAttribute("version"), "1", labTest.getTextContent()));
                    LogFileHandler.logger.info("Changelog: " + labTest.getAttribute("version") + " - " + labTest.getTextContent());
                }
            }
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return changelogList;
    }
}
