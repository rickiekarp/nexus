package com.rkarp.sha1pass.view;

//import com.rkarp.appcore.AppContext;
//import com.rkarp.appcore.components.textfield.CustomTextField;
//import com.rkarp.appcore.controller.LanguageController;
//import com.rkarp.appcore.debug.DebugHelper;
//import com.rkarp.appcore.util.crypt.*;
//import com.rkarp.appcore.view.AboutScene;
//import com.rkarp.appcore.components.textfield.CustomTextFieldSkin;
import com.rkarp.sha1pass.settings.AppConfiguration;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
//import javafx.scene.control.Button;
//import javafx.scene.control.*;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.security.SignatureException;

public class MainLayout {
    private boolean isSecure = false;
    private boolean hmac = false;
    private boolean complex = true;

    private HBox wordBox;
    private Rectangle color;
//    private CustomTextField sentence_tf_mask, sentence_tf, word_tf;
//    public CustomTextField getSentenceMaskTextField() {
//        return sentence_tf_mask;
//    }
//    private TextField peek_tf;

    private int colorPos = -1;

    public Node getMainLayout() {
        BorderPane mainContent = new BorderPane();

        GridPane mainGrid = new GridPane();
        mainGrid.setPadding(new Insets(5, 3, 0, 3));  //padding top, right, bottom, left

        wordBox = new HBox();
        wordBox.setPadding(new Insets(0, 0, 0, 10));  //padding top, right, bottom, left
        wordBox.setSpacing(5);
        wordBox.setAlignment(Pos.CENTER_LEFT);

        HBox encryptBtns = new HBox();
        encryptBtns.setPadding(new Insets(0, 0, 0, 0));  //padding top, right, bottom, left
        encryptBtns.setSpacing(7);
        encryptBtns.setAlignment(Pos.CENTER_LEFT);

        HBox controls = new HBox();
        controls.setPadding(new Insets(3, 3, 3, 7));  //padding top, right, bottom, left
        controls.setSpacing(2);

//        Label status = new Label(AppContext.getContext().getApplicationName());
//        status.setStyle("-fx-font-size: 9pt;");
//        controls.getChildren().add(status);

        //set Layout
        ColumnConstraints column1 = new ColumnConstraints(); column1.setPercentWidth(16);
        ColumnConstraints column2 = new ColumnConstraints(); column2.setPercentWidth(14);
        ColumnConstraints column3 = new ColumnConstraints(); column3.setPercentWidth(16);
        ColumnConstraints column4 = new ColumnConstraints(); column4.setPercentWidth(17);
        ColumnConstraints column5 = new ColumnConstraints(); column5.setPercentWidth(20);
        ColumnConstraints column6 = new ColumnConstraints(); column6.setPercentWidth(14);
        ColumnConstraints column7 = new ColumnConstraints(); column7.setPercentWidth(11);
        mainGrid.getColumnConstraints().addAll(column1, column2, column3, column4, column5, column6, column7);

        mainGrid.setHgap(5); mainGrid.setVgap(5);

//        //row 1
//        Label sentence_label = new Label(LanguageController.getString("u_sentence"));
//        sentence_label.setStyle("-fx-font-size: 10pt;");
//        GridPane.setConstraints(sentence_label, 0, 0);
//        mainGrid.getChildren().add(sentence_label);
//        sentence_label.setTooltip(new Tooltip(LanguageController.getString("type_sentence_tip")));
//
//
//        sentence_tf_mask = new CustomTextField();
//        sentence_tf_mask.setTooltip(new Tooltip(LanguageController.getString("type_sentence_tip")));
//        sentence_tf_mask.setSkin(new CustomTextFieldSkin(sentence_tf_mask));
//        GridPane.setConstraints(sentence_tf_mask, 1, 0);
//        GridPane.setColumnSpan(sentence_tf_mask, 3);
//        mainGrid.getChildren().add(sentence_tf_mask);
//
//        sentence_tf = new CustomTextField();
//        sentence_tf.setTooltip(new Tooltip(LanguageController.getString("type_sentence_tip")));
//        GridPane.setConstraints(sentence_tf, 1, 0);
//        GridPane.setColumnSpan(sentence_tf, 3);
//        sentence_tf.setManaged(false);
//        sentence_tf.setVisible(false);
//        mainGrid.getChildren().add(sentence_tf);
//
//        Label word_label = new Label(LanguageController.getString("u_word"));
//        word_label.setStyle("-fx-font-size: 10pt;");
//        word_label.setTooltip(new Tooltip(LanguageController.getString("type_word_tip")));
//        word_label.setPrefWidth(35);
//        wordBox.getChildren().add(word_label);
//
//        CustomTextField word_tf_mask = new CustomTextField();
//        word_tf_mask.setTooltip(new Tooltip(LanguageController.getString("type_word_tip")));
//        word_tf_mask.setPrefWidth(85);
//        word_tf_mask.setSkin(new CustomTextFieldSkin(word_tf_mask));
//        wordBox.getChildren().add(word_tf_mask);
//
//        word_tf = new CustomTextField();
//        word_tf.setTooltip(new Tooltip(LanguageController.getString("type_word_tip")));
//        word_tf.setPrefWidth(85);
//        word_tf.setManaged(false);
//        word_tf.setVisible(false);
//
//        Button helpBtn = new Button(LanguageController.getString("help_label"));
//        helpBtn.setStyle("-fx-font-size: 9pt;");
//        helpBtn.setTooltip(new Tooltip(LanguageController.getString("help_tip") + " " + AppContext.getContext().getApplicationName()));
//        GridPane.setConstraints(helpBtn, 6, 0);
//        GridPane.setHalignment(helpBtn, HPos.CENTER);
//        mainGrid.getChildren().add(helpBtn);
//
//        //row 2
//        CheckBox viewMode = new CheckBox(LanguageController.getString("vs"));
//        viewMode.setStyle("-fx-font-size: 9pt;");
//        viewMode.setTooltip(new Tooltip(LanguageController.getString("vs_tip")));
//        GridPane.setConstraints(viewMode, 1, 1);
//        mainGrid.getChildren().add(viewMode);
//
//        CheckBox secureMode = new CheckBox(LanguageController.getString("sm"));
//        secureMode.setStyle("-fx-font-size: 8pt;");
//        secureMode.setTooltip(new Tooltip(LanguageController.getString("sm_tip")));
//        GridPane.setConstraints(secureMode, 2, 1);
//        mainGrid.getChildren().add(secureMode);
//
//        CheckBox hmacMode = new CheckBox(LanguageController.getString("hm"));
//        hmacMode.setStyle("-fx-font-size: 9pt;");
//        hmacMode.setTooltip(new Tooltip(LanguageController.getString("hmac_tip")));
//        hmacMode.setSelected(hmac);
//        GridPane.setConstraints(hmacMode, 3, 1);
//        mainGrid.getChildren().add(hmacMode);
//
//        CheckBox complexMode = new CheckBox(LanguageController.getString("comp"));
//        complexMode.setStyle("-fx-font-size: 9pt;");
//        complexMode.setTooltip(new Tooltip(LanguageController.getString("comp_tip")));
//        complexMode.setSelected(complex);
//        GridPane.setConstraints(complexMode, 4, 1);
//        mainGrid.getChildren().add(complexMode);
//
//        peek_tf = new TextField(LanguageController.getString("pass_peek"));
//        peek_tf.setStyle("-fx-font-size: 10pt;");
//        peek_tf.setEditable(false); //peek_tf.setHighlighter(null);
//        peek_tf.setTooltip(new Tooltip(LanguageController.getString("pass_peek_tip")));
//        GridPane.setConstraints(peek_tf, 5, 1);
//        peek_tf.setPrefWidth(30);
//        mainGrid.getChildren().add(peek_tf);
//
//        Button colorBtn = new Button(LanguageController.getString("color_label"));
//        colorBtn.setStyle("-fx-font-size: 9pt;");
//        colorBtn.setTooltip(new Tooltip(LanguageController.getString("color_tip")));
//        GridPane.setConstraints(colorBtn, 6, 1);
//        GridPane.setHalignment(colorBtn, HPos.CENTER);
//        mainGrid.getChildren().add(colorBtn);
//
//        //row 3
//        Button hexBtn = new Button(LanguageController.getString("hex_label"));
//        hexBtn.setStyle("-fx-font-size: 10pt;");
//        hexBtn.setTooltip(new Tooltip(LanguageController.getString("a_40_char_tip")));
//        hexBtn.setMinWidth(103);
//        encryptBtns.getChildren().add(hexBtn);
//
//        Button b64Btn = new Button(LanguageController.getString("b64_label"));
//        b64Btn.setStyle("-fx-font-size: 10pt;");
//        b64Btn.setTooltip(new Tooltip(LanguageController.getString("a_28_char_tip")));
//        b64Btn.setMinWidth(103);
//        encryptBtns.getChildren().add(b64Btn);
//
//        Button bcryptBtn = new Button(LanguageController.getString("bcrypt_label"));
//        bcryptBtn.setStyle("-fx-font-size: 10pt;");
//        bcryptBtn.setTooltip(new Tooltip(LanguageController.getString("a_60_char_tip")));
//        bcryptBtn.setMinWidth(103);
//        encryptBtns.getChildren().add(bcryptBtn);
//
//        color = new Rectangle(30,30); color.setVisible(false);
//        color.setFill(Color.TRANSPARENT);
//        GridPane.setHalignment(color, HPos.CENTER);
//        GridPane.setConstraints(color, 6, 2);
//        mainGrid.getChildren().add(color);
//
//        mainGrid.getChildren().add(wordBox);
//        GridPane.setConstraints(wordBox, 4, 0);
//        GridPane.setColumnSpan(wordBox, 2);
//
//        mainGrid.getChildren().add(encryptBtns);
//        GridPane.setConstraints(encryptBtns, 1, 2);
//        GridPane.setColumnSpan(encryptBtns, 5);
//
//
        mainContent.setCenter(mainGrid);
        mainContent.setBottom(controls);
//
//        helpBtn.setOnAction(e -> new AboutScene());
//
//        colorBtn.setOnAction(actionEvent -> {
//            color.setVisible(true);
//            colorRotate();
//        });
//
//        hexBtn.setOnAction(actionEvent -> {
//            try {
//                calcHex();
//            } catch (SignatureException e) {
//                e.printStackTrace();
//            }
//            status.setText(LanguageController.getString("hex_password_copied"));
//        });
//
//        b64Btn.setOnAction(actionEvent -> {
//            try {
//                calcBase64();
//            } catch (SignatureException e) {
//                e.printStackTrace();
//            }
//            status.setText(LanguageController.getString("b64_password_copied"));
//        });
//
//        bcryptBtn.setOnAction(actionEvent -> {
//            try {
//                calcBCrypt();
//            } catch (SignatureException e) {
//                e.printStackTrace();
//            }
//            status.setText(LanguageController.getString("bcrypt_password_copied"));
//        });
//
//        viewMode.selectedProperty().addListener((ov, old_val, new_val) -> {
//            if (new_val) {
//                wordBox.getChildren().remove(word_tf_mask);
//                wordBox.getChildren().add(word_tf);
//                status.setText(LanguageController.getString("vs_on"));
//            } else {
//                wordBox.getChildren().remove(word_tf);
//                wordBox.getChildren().add(word_tf_mask);
//                status.setText(LanguageController.getString("vs_off"));
//            }
//        });
//
//        // Bind TextField properties to viewMode CheckBox
//        sentence_tf.managedProperty().bind(viewMode.selectedProperty());
//        sentence_tf.visibleProperty().bind(viewMode.selectedProperty());
//        word_tf.managedProperty().bind(viewMode.selectedProperty());
//        word_tf.visibleProperty().bind(viewMode.selectedProperty());
//
//        sentence_tf_mask.managedProperty().bind(viewMode.selectedProperty().not());
//        word_tf_mask.visibleProperty().bind(viewMode.selectedProperty().not());
//
//        // Bind TextField and masked TextField text values bidirectionally
//        sentence_tf.textProperty().bindBidirectional(sentence_tf_mask.textProperty());
//        word_tf.textProperty().bindBidirectional(word_tf_mask.textProperty());
//
//        secureMode.selectedProperty().addListener((ov, old_val, new_val) -> {
//            if (new_val) {
//                isSecure = true;
//                viewMode.setDisable(true);
//                viewMode.setSelected(false);
//                colorBtn.setDisable(true);
//                status.setText(LanguageController.getString("sm_on"));
//            } else {
//                isSecure = false;
//                viewMode.setDisable(false);
//                sentence_tf.setText("");
//                word_tf.setText("");
//                peek_tf.setText("Peek");
//                colorBtn.setDisable(false);
//                setStringToClipboard("");
//                status.setText(LanguageController.getString("sm_off"));
//            }
//        });
//
//        hmacMode.selectedProperty().addListener((ov, old_val, new_val) -> {
//            if (new_val)
//            {
//                hmac = true;
//                status.setText(LanguageController.getString("hmac_on"));
//            } else {
//                hmac = false;
//                status.setText(LanguageController.getString("hmac_off"));
//            }
//        });
//
//        complexMode.selectedProperty().addListener((ov, old_val, new_val) -> {
//            if (new_val) {
//                complex = true;
//                status.setText(LanguageController.getString("comp_on"));
//            } else {
//                complex = false;
//                status.setText(LanguageController.getString("comp_off"));
//            }
//        });
//
//        if (DebugHelper.DEBUGVERSION) {
//            mainGrid.setGridLinesVisible(true);
//            mainGrid.setStyle("-fx-background-color: gray");
//            encryptBtns.setStyle("-fx-background-color: #A36699;");
//            controls.setStyle("-fx-background-color: #336699;");
//        }

        return mainContent;
    }

    private void calcHex() throws SignatureException {
        String data = checkInputData();
//        byte[] sha1 = SHA1Coder.getSHA1Bytes(data, hmac);

//        String hash = HexCoder.bytesToHex(sha1);
//        if (complex) {
//            applyComplex(hash);
//        } else {
//            copyPassword(hash);
//        }
    }

    private void calcBase64() throws SignatureException {
        String data = checkInputData();
//        byte[] sha1 = SHA1Coder.getSHA1Bytes(data, hmac);
//
//        String hash = String.valueOf(Base64Coder.encode(sha1));
//        if (complex) {
//            applyComplex(hash);
//        } else {
//            copyPassword(hash);
//        }
    }

    private void calcBCrypt() throws SignatureException {
        String data = checkInputData();
//        byte[] sha1 = SHA1Coder.getSHA1Bytes(data, hmac);
//        String salt = "$2a$10$" + SHA1Coder.getSHA1String(sha1);
//        String hash = BCryptCoder.hashpw(data, salt);
//
//        if (complex) {
//            applyComplex(hash);
//        }
//        else {
//            copyPassword(hash);
//        }
    }

    private void applyComplex(String data) {
        //copy encoded string to clipboard and set the peek TextField text
        setStringToClipboard(data + AppConfiguration.comp_string);
        peekText(data + AppConfiguration.comp_string);
    }

    private void copyPassword(String hashedPass) {
        //copy encoded string to clipboard and set the peek TextField text
        setStringToClipboard(hashedPass);
        peekText(hashedPass);
    }

    /**
     * if a color is selected, the hex color code is added to the entered data string
     * @return to be encrypted user input
     */
    private String checkInputData() {
        if (color.getFill().equals(Color.TRANSPARENT)) {
//            return sentence_tf.getText() + word_tf.getText();
        } else {
            //System.out.println(sentence_tf.getText() + word_tf.getText() + ColorCoder.colorArray[colorPos].toString());
//            return sentence_tf.getText() + word_tf.getText() + ColorCoder.colorArray[colorPos].toString();
        }
        return "stub";
    }

    /**
     * Changes the color field
     */
    private void colorRotate() {
//        try {
//            colorPos++; color.setFill(ColorCoder.colorArray[colorPos]);
//        } catch (ArrayIndexOutOfBoundsException e) {
//            color.setFill(Color.TRANSPARENT); colorPos = -1;
//        }
    }

    /**
     * Shows the first part of the encrypted password in a text field
     * @param s Text to show in the text field
     */
    private void peekText(String s) {
//        if (isSecure) {
//            peek_tf.setText("Peek");
//        } else {
//            peek_tf.setText(s.substring(0, 4));
//        }
    }

    /**
     * Copies the given string to the clipboard
     * @param stringContent String to copy
     */
    private void setStringToClipboard(String stringContent) {
//        StringSelection stringSelection = new StringSelection(stringContent);
//        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }
}
