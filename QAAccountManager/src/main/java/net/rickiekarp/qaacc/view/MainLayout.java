package net.rickiekarp.qaacc.view;

import net.rickiekarp.core.components.textfield.CustomTextField;
import net.rickiekarp.core.controller.LanguageController;
import net.rickiekarp.core.debug.DebugHelper;
import net.rickiekarp.core.debug.ExceptionHandler;
import net.rickiekarp.core.debug.LogFileHandler;
import net.rickiekarp.core.util.CommonUtil;
import net.rickiekarp.core.util.ImageLoader;
import net.rickiekarp.core.view.MessageDialog;
import net.rickiekarp.qaacc.factory.AccountXmlFactory;
import net.rickiekarp.qaacc.model.Account;
import net.rickiekarp.qaacc.settings.AppConfiguration;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.logging.Level;

public class MainLayout {
    public static MainLayout mainLayout;
    private Button findAccount;
    private Button saveAccount;
    private TextField nameTextField, mailTextField, passTextField;
    private CustomTextField acronymTextField;
    private ComboBox<String> gameComboBox;
    public static Label status;
    private ImageView logo;
    private GridPane inputGrid;
    private GridPane outputGrid;
    private HBox controls;

    public MainLayout() {
        mainLayout = this;
    }

    public Node getMainLayout() {
        BorderPane mainContent = new BorderPane();

        inputGrid = new GridPane();
        outputGrid = new GridPane();
        VBox vbox = new VBox();
        controls = new HBox();

        //set Layout
        ColumnConstraints column1 = new ColumnConstraints(); column1.setPercentWidth(40);
        ColumnConstraints column2 = new ColumnConstraints(); column2.setPercentWidth(20);
        ColumnConstraints column3 = new ColumnConstraints(); column3.setPercentWidth(40);
        inputGrid.getColumnConstraints().addAll(column1, column2, column3); // each get 33% of width

        ColumnConstraints column1a = new ColumnConstraints(); column1a.setPercentWidth(20);
        ColumnConstraints column2a = new ColumnConstraints(); column2a.setPercentWidth(60);
        ColumnConstraints column3a = new ColumnConstraints(); column3a.setPercentWidth(20);
        outputGrid.getColumnConstraints().addAll(column1a, column2a, column3a); // each get 33% of width


        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.setPadding(new Insets(20, 5, 0, 5));  //padding top, left, bottom, right
        outputGrid.setHgap(5);
        outputGrid.setVgap(10);
        outputGrid.setPadding(new Insets(20, 5, 0, 5));  //padding top, left, bottom, right

        vbox.setPadding(new Insets(5));

        controls.setPadding(new Insets(5, 5, 5, 5));

        //add Grids to VBox Layout
        vbox.getChildren().add(0, inputGrid);
        vbox.getChildren().add(1, outputGrid);

        //add components
        Label acronymLabel = new Label(LanguageController.getString("acronym_input"));
        GridPane.setConstraints(acronymLabel, 0, 0);
        inputGrid.getChildren().add(acronymLabel);

        acronymTextField = new CustomTextField();
        acronymTextField.setMaxLength(4);
        GridPane.setConstraints(acronymTextField, 1, 0);
        inputGrid.getChildren().add(acronymTextField);

        logo = new ImageView(ImageLoader.getAppIconSmall()); logo.fitHeightProperty().setValue(50); logo.fitWidthProperty().setValue(50);
        GridPane.setConstraints(logo, 2, 0);
        inputGrid.getChildren().add(logo);

        nameTextField = new TextField(); nameTextField.setEditable(false); nameTextField.setAlignment(Pos.CENTER);
        nameTextField.setTooltip(new Tooltip(LanguageController.getString("name")));
        GridPane.setConstraints(nameTextField, 0, 0);
        outputGrid.getChildren().add(nameTextField);

        mailTextField = new TextField(); mailTextField.setEditable(false); mailTextField.setAlignment(Pos.CENTER);
        mailTextField.setTooltip(new Tooltip(LanguageController.getString("mail")));
        GridPane.setConstraints(mailTextField, 1, 0);
        outputGrid.getChildren().add(mailTextField);

        passTextField = new TextField(); passTextField.setEditable(false); passTextField.setAlignment(Pos.CENTER);
        passTextField.setTooltip(new Tooltip(LanguageController.getString("password")));
        GridPane.setConstraints(passTextField, 2, 0);
        outputGrid.getChildren().add(passTextField);

        Button copyNameBtn = new Button(LanguageController.getString("copy"));
        copyNameBtn.setTooltip(new Tooltip(LanguageController.getString("copy_name_desc")));
        GridPane.setConstraints(copyNameBtn, 0, 1);
        outputGrid.getChildren().add(copyNameBtn);

        Button copyMailBtn = new Button(LanguageController.getString("copy"));
        copyMailBtn.setTooltip(new Tooltip(LanguageController.getString("copy_mail_desc")));
        GridPane.setConstraints(copyMailBtn, 1, 1);
        outputGrid.getChildren().add(copyMailBtn);

        Button copyPassBtn = new Button(LanguageController.getString("copy"));
        copyPassBtn.setTooltip(new Tooltip(LanguageController.getString("copy_pass_desc")));
        GridPane.setConstraints(copyPassBtn, 2, 1);
        outputGrid.getChildren().add(copyPassBtn);

        gameComboBox = new ComboBox<>();
        int mainAppWidth = 750;
        gameComboBox.setPrefSize(mainAppWidth / 3.8, 40);
        GridPane.setConstraints(gameComboBox, 0, 0);

        status = new Label();
        status.setPrefSize(mainAppWidth / 4.5, 40);
        GridPane.setConstraints(status, 1, 0);

        findAccount = new Button(LanguageController.getString("account_manager"));
        findAccount.setTooltip(new Tooltip(LanguageController.getString("open_account_manager")));
        findAccount.setPrefSize(mainAppWidth / 4, 40);
        GridPane.setConstraints(findAccount, 2, 0);

        saveAccount = new Button(LanguageController.getString("saveAcc"));
        saveAccount.setTooltip(new Tooltip(LanguageController.getString("saveAcc")));
        saveAccount.setPrefSize(mainAppWidth / 4, 40);
        GridPane.setConstraints(saveAccount, 3, 0);

        controls.getChildren().addAll(gameComboBox, status, findAccount, saveAccount);
        controls.setSpacing(5);

        //Center components in GridPane
        GridPane.setHalignment(acronymLabel, HPos.CENTER);
        GridPane.setHalignment(logo, HPos.CENTER);
        GridPane.setHalignment(copyNameBtn, HPos.CENTER);
        GridPane.setHalignment(copyMailBtn, HPos.CENTER);
        GridPane.setHalignment(copyPassBtn, HPos.CENTER);

        //add vbox & controls pane to borderpane layout
        mainContent.setCenter(vbox);
        mainContent.setBottom(controls);

        copyNameBtn.setOnAction(event -> {
            AppConfiguration.setStringToClipboard(nameTextField.getText());
            status.setText(LanguageController.getString("name_copied"));
            LogFileHandler.logger.log(Level.INFO, "copy2clipboard.name");
        });

        copyMailBtn.setOnAction(event -> {
            AppConfiguration.setStringToClipboard(mailTextField.getText());
            status.setText(LanguageController.getString("mail_copied"));
            LogFileHandler.logger.log(Level.INFO, "copy2clipboard.mail");
        });

        copyPassBtn.setOnAction(event -> {
            AppConfiguration.setStringToClipboard(passTextField.getText());
            status.setText(LanguageController.getString("password_copied"));
            LogFileHandler.logger.log(Level.INFO, "copy2clipboard.pass");
        });


        findAccount.setOnAction(event -> {
            switch (AppConfiguration.pjState) {
                case -1:
                    new MessageDialog(0, LanguageController.getString("project_not_selected"), 350, 200);
                    break;
                default:
                    new AccountOverview(gameComboBox.getSelectionModel().getSelectedIndex());
                    break;
            }
        });

        saveAccount.setOnAction(event -> {
            switch (AppConfiguration.pjState){
                case -1:
                    new MessageDialog(0, LanguageController.getString("project_not_selected"), 350, 200);
                    break;
                default:
                    if (acronymTextField.getText().isEmpty()) { new MessageDialog(0, LanguageController.getString("acronymTextField_empty"), 400, 200); }
                    else {
                        try {
                            AccountXmlFactory.addAccount(AppConfiguration.pjState, nameTextField.getText(), mailTextField.getText(), "1", "");
                        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e1) {
                            if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
                        }
                    }

                    AppConfiguration.accountData.add(new Account(nameTextField.getText(), mailTextField.getText(), "1", ""));

                    if (AccountOverview.overview != null && AccountOverview.overview.getAccOverviewScene().getWin().getWindowStage().getStage().isShowing()) {
                        AccountOverview.refreshPersonTable(-1);
                    }
            }
        });


        acronymTextField.setOnKeyReleased(ke -> {
            if (acronymTextField.getText().equals("")) {  nameTextField.setText(""); mailTextField.setText(""); passTextField.setText(""); }
            else {
                nameTextField.setText(acronymTextField.getText() + CommonUtil.getDate("ddMMyy") + AppConfiguration.dept);
                mailTextField.setText(AppConfiguration.mail_pref + acronymTextField.getText() + CommonUtil.getDate("ddMMyy") + AppConfiguration.mail_end);
                passTextField.setText(AppConfiguration.pass); }
        });

        if (AppConfiguration.projectData.size() > 0) {
            gameComboBox.valueProperty().addListener((ov, t, t1) -> {
                try {
                    if (gameComboBox.getValue().equals(LanguageController.getString("projSel"))) {
                        AppConfiguration.pjState = -1;
                        gameComboBox.setTooltip(new Tooltip(LanguageController.getString("project_not_selected")));
                        logo.setImage(ImageLoader.getAppIconSmall());
                    }
                    else if (gameComboBox.getValue().equals(AppConfiguration.projectData.get(0).getProjectName())) {
                        AppConfiguration.pjState = 0;
                        gameComboBox.setTooltip(new Tooltip(AppConfiguration.projectData.get(0).getProjectName()));
                        status.setText(gameComboBox.getValue() + " " + LanguageController.getString("selected"));
                        logo.setImage(ImageLoader.getAppIconSmall());
                    }
                    else if (gameComboBox.getValue().equals(AppConfiguration.projectData.get(1).getProjectName())) {
                        AppConfiguration.pjState = 1;
                        gameComboBox.setTooltip(new Tooltip(AppConfiguration.projectData.get(1).getProjectName()));
                        status.setText(gameComboBox.getValue() + " " + LanguageController.getString("selected"));
                        logo.setImage(ImageLoader.getAppIconSmall());
                    }
                    else if (gameComboBox.getValue().equals(AppConfiguration.projectData.get(2).getProjectName())) {
                        AppConfiguration.pjState = 2;
                        gameComboBox.setTooltip(new Tooltip(AppConfiguration.projectData.get(2).getProjectName()));
                        status.setText(gameComboBox.getValue() + " " + LanguageController.getString("selected"));
                        logo.setImage(ImageLoader.getAppIconSmall());
                    }
                    else if (gameComboBox.getValue().equals(AppConfiguration.projectData.get(3).getProjectName())) {
                        AppConfiguration.pjState = 3;
                        gameComboBox.setTooltip(new Tooltip(AppConfiguration.projectData.get(3).getProjectName()));
                        status.setText(gameComboBox.getValue() + " " + LanguageController.getString("selected"));
                        logo.setImage(ImageLoader.getAppIconSmall());
                    }
                    else {
                        AppConfiguration.pjState = gameComboBox.getSelectionModel().getSelectedIndex();
                        gameComboBox.setTooltip(new Tooltip(AppConfiguration.projectData.get(AppConfiguration.pjState).getProjectName()));
                        status.setText(gameComboBox.getValue() + " " + LanguageController.getString("selected"));
                        logo.setImage(ImageLoader.getAppIconSmall());
                    }
                } catch (Exception e1) {
                    if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
                }
            });
        }

        acronymTextField.requestFocus();
//        if (!AppConfiguration.acronym.isEmpty()) { acronymTextField.setText(AppConfiguration.acronym); createName(); } //set acronym setting

        debugMain();

        return mainContent;
    }

    //create name after reading config file
    void createName() {
        if (acronymTextField.getText().equals("")) {
            nameTextField.setText(""); mailTextField.setText("");
            passTextField.setText("");
        }
        else
        {
            nameTextField.setText(acronymTextField.getText() + CommonUtil.getDate("ddMMyy") + AppConfiguration.dept);
            mailTextField.setText(AppConfiguration.mail_pref + acronymTextField.getText() + CommonUtil.getDate("ddMMyy") + AppConfiguration.mail_end);
            passTextField.setText(AppConfiguration.pass);
        }
        status.setText(LanguageController.getString("ready"));
    }

    /** sets the items of the project ComboBox **/
    public void fillComboBox() {
        if (AppConfiguration.projectData.size() != 0) {
            for (int i = 0; i < AppConfiguration.projectData.size(); i++) { gameComboBox.getItems().add(AppConfiguration.projectData.get(i).getProjectName()); }

            if (AppConfiguration.pjCfgSelection == -1) { gameComboBox.setValue(LanguageController.getString("projSel")); }
            else { gameComboBox.setValue(AppConfiguration.projectData.get(AppConfiguration.pjCfgSelection).getProjectName()); status.setText(LanguageController.getString("ready"));}
        } else {
            gameComboBox.setValue(LanguageController.getString("noProjSel"));
            gameComboBox.setTooltip(new Tooltip(LanguageController.getString("project_not_found_desc")));
            findAccount.setDisable(true);
            saveAccount.setDisable(true);
        }
    }

    private void debugMain() {
        if (DebugHelper.isDebugVersion()) {
            inputGrid.setStyle("-fx-background-color: #155ff9;");
            outputGrid.setStyle("-fx-background-color: #536699;");
            controls.setStyle("-fx-background-color: #336699;");
        }
        else
        {
            inputGrid.setStyle(null);
            outputGrid.setStyle(null);
            controls.setStyle(null);
        }
    }
}
