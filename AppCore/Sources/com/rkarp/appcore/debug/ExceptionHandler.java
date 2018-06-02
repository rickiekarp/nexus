package com.rkarp.appcore.debug;

import com.rkarp.appcore.controller.LanguageController;
import com.rkarp.appcore.ui.windowmanager.ThemeSelector;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.logging.Level;

/**
 * This class is the default uncaught exception handler.
 * It contains the GUI if an exception is thrown
 */
public class ExceptionHandler {

    public ExceptionHandler(Thread thread, Throwable throwable) {
        LogFileHandler.logger.log(Level.SEVERE, getExceptionString(throwable));
        createExceptionGUI(thread, throwable);
    }

    /**
     * Creates exception gui
     */
    private void createExceptionGUI(Thread t, Throwable e) {
        Stage modalDialog = new Stage();
        modalDialog.setTitle(LanguageController.getString("error"));
        //modalDialog.getIcons().add(ImageLoaderUtil.app_icon);
        modalDialog.initModality(Modality.APPLICATION_MODAL);
        modalDialog.setResizable(true);
        modalDialog.setOnCloseRequest(event -> modalDialog.close());

        BorderPane borderpane = new BorderPane();
        GridPane grid = new GridPane();
        HBox controls = new HBox();

        Scene exception = new Scene(borderpane, 800, 440, Color.TRANSPARENT);
        exception.getStylesheets().add(ThemeSelector.DARK_THEME_CSS);

        modalDialog.setScene(exception);
        modalDialog.show();

        //add components
        TextArea exTF = new TextArea();
        exTF.setEditable(false);

        Label status = new Label();
        status.setVisible(false);
        controls.getChildren().add(status);

        Button copy = new Button(LanguageController.getString("copy"));
        controls.getChildren().add(copy);

        //set layout
        grid.setPadding(new Insets(5));
        ColumnConstraints column1 = new ColumnConstraints(); column1.setPercentWidth(100);
        RowConstraints row1 = new RowConstraints(); row1.setPercentHeight(100);
        grid.getColumnConstraints().addAll(column1);
        grid.getRowConstraints().add(row1);

        controls.setPadding(new Insets(15, 12, 15, 12));  //padding top, left, bottom, right
        controls.setSpacing(10);
        controls.setAlignment(Pos.CENTER_RIGHT);

        grid.getChildren().add(0, exTF);

        borderpane.setCenter(grid);
        borderpane.setBottom(controls);

        //action listener
        copy.setOnAction(event -> {
            status.setVisible(true);
            setStringToClipboard(exTF.getText());
            status.setStyle("-fx-text-fill: #55c4fe;");
            status.setText(LanguageController.getString("exception_copied"));
        });

        exTF.setText(getExceptionString(e));
    }

    /**
     * Returns exception string
     */
    public static String getExceptionString(Throwable t) {

        StringBuilder sb = new StringBuilder();

        sb.append(String.valueOf(t)).append(System.getProperty("line.separator"));

        StackTraceElement[] trace = t.getStackTrace();
        for (StackTraceElement aTrace : trace) {
            sb.append("       at ").append(String.valueOf(aTrace)).append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }


    /**
     * Throws an exception (for testing)
     */
    public static void throwTestException() {
        try { throw new IndexOutOfBoundsException("TEST"); }
        catch (IndexOutOfBoundsException e1) {
            if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); new ExceptionHandler(Thread.currentThread(), e1); }
            else { new ExceptionHandler(Thread.currentThread(), e1); }
        }
    }

    /** Copy string to clipboard **/
    private void setStringToClipboard(String stringContent) {
        StringSelection stringSelection = new StringSelection(stringContent);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }
}
