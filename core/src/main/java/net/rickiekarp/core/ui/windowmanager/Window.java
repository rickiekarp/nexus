package net.rickiekarp.core.ui.windowmanager;

import net.rickiekarp.core.components.button.SidebarButton;
import net.rickiekarp.core.controller.LanguageController;
import net.rickiekarp.core.debug.DebugHelper;
import net.rickiekarp.core.debug.LogFileHandler;
import net.rickiekarp.core.settings.AppCommands;
import net.rickiekarp.core.settings.Configuration;
import net.rickiekarp.core.settings.LoadSave;
import net.rickiekarp.core.ui.anim.AnimationHandler;
import net.rickiekarp.core.util.ImageLoader;
import net.rickiekarp.core.view.AboutScene;
import net.rickiekarp.core.view.SettingsScene;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.*;

import java.net.URL;
import java.util.logging.Level;

/**
 * This class, with the WindowController, is the central class for the
 * decoration of Transparent Stages.
 *
 * Bugs (Mac only?): Accelerators crash JVM
 * KeyCombination does not respect keyboard's locale
 * Multi screen: On second screen JFX returns wrong value for MinY (300)
 */
public class Window extends StackPane {
    private int SHADOW_WIDTH = 10;
    private int SAVED_SHADOW_WIDTH = 15;
    private int RESIZE_PADDING = 7;
    private int TITLEBAR_HEIGHT = 35;

    private Region clientArea;
    public static String colorTheme;
    static DropShadow dsFocused;
    static DropShadow dsNotFocused;
    private TextField console;
    private static boolean isMenuOpen;
    SimpleDoubleProperty sideBarHeightProperty;
    private StageStyle stageStyle;
    private AnchorPane stageDecoration;
    private Rectangle shadowRectangle;
    private Rectangle dockFeedback;
    private Stage dockFeedbackPopup;
    private WindowController windowController;
    private WindowContentController windowContentController;
    private WindowStage windowStage;
    private Rectangle resizeRect;
    SimpleBooleanProperty maximizeProperty;
    private SimpleBooleanProperty minimizeProperty;
    private SimpleBooleanProperty closeProperty;
    private int windowType;

    public WindowController getController() {
        return windowController;
    }
    public WindowContentController getContentController() {
        return windowContentController;
    }
    public WindowStage getWindowStage() {
        return windowStage;
    }
    public HBox getTitlebarButtonBox() {
        return windowContentController.getTitlebarButtonBox();
    }
    public VBox getSidebarButtonBox() {
        return windowContentController.getSidebarButtonBox();
    }
    int getWindowType() {
        return windowType;
    }

    Region getClientArea() {
        return clientArea;
    }

    private SimpleBooleanProperty maximizeProperty() {
        return maximizeProperty;
    }
    private SimpleBooleanProperty minimizeProperty() {
        return minimizeProperty;
    }
    private SimpleBooleanProperty closeProperty() {
        return closeProperty;
    }

    public Window(WindowStage stag, Region rootArea, StageStyle st, int winType) {
        windowStage = stag;
        clientArea = rootArea;
        windowType = winType;

        create(stag, st, winType);
    }

    public void create(WindowStage stag, StageStyle st, int winType) {
        setStageStyle(st);

        // Properties
        maximizeProperty = new SimpleBooleanProperty(false);
        maximizeProperty.addListener((ov, t, t1) -> getController().maximizeOrRestore());
        minimizeProperty = new SimpleBooleanProperty(false);
        minimizeProperty.addListener((ov, t, t1) -> getController().minimize());
        closeProperty = new SimpleBooleanProperty(false);
        closeProperty.addListener((ov, t, t1) -> getController().close());

        sideBarHeightProperty = new SimpleDoubleProperty(stag.getStage().getHeight());

        // The controller
        windowController = new WindowController(this);
        windowContentController = new WindowContentController(this);

        //set default values if there is not config.xml and the values have not been set
        if (colorTheme == null) { colorTheme = "black"; }
        if (Configuration.decorationColor == null) {
            Configuration.decorationColor = LoadSave.decorationColor;
        }
        if (Configuration.shadowColorFocused == null) {
            Configuration.shadowColorFocused = LoadSave.shadowColorFocused;
        }
        if (Configuration.shadowColorNotFocused == null) {
            Configuration.shadowColorNotFocused = LoadSave.shadowColorNotFocused;
        }

        // Focus drop shadows: radius, spread, offsets
        dsFocused = new DropShadow(BlurType.THREE_PASS_BOX, Configuration.shadowColorFocused, SHADOW_WIDTH, 0.2, 0, 0);
        dsNotFocused = new DropShadow(BlurType.THREE_PASS_BOX, Configuration.shadowColorNotFocused, SHADOW_WIDTH, 0, 0, 0);

        //set highlight/focus color
        clientArea.setStyle("-fx-accent: " + colorTheme + ";" + "-fx-focus-color: " + colorTheme + ";");

        setStyle("-fx-background-color:transparent"); //J8

        // UI part of the decoration
        VBox vbox = new VBox();
        stageDecoration = getStageDecoration(stag.getStage(), stag.getStage().getTitle(), windowType);
        windowController.setAsStageDraggable(stag.getStage(), stageDecoration);

        StackPane contentStack = new StackPane(clientArea);
        VBox.setVgrow(contentStack, Priority.ALWAYS);

        switch (winType) {
            case 0:
                StackPane blurPane = new StackPane();
                blurPane.setStyle("-fx-background-color: #1d1d1d;");
                blurPane.setVisible(false);
                blurPane.setOnMouseClicked(event -> toggleSideBar());

                //create fadeIn/fadeOut transitions
                AnimationHandler.stackFadeIn = AnimationHandler.fade(blurPane, 200, 0.0f, 0.8f);
                AnimationHandler.stackFadeOut = AnimationHandler.fade(blurPane, 200, 0.8f, 0.0f);
                AnimationHandler.stackFadeOut.setOnFinished(event -> blurPane.setVisible(false));

                //add slide handler for sidebar
                AnimationHandler.addSlideHandlers(stag.getStage().getWidth());

                //get Menu content
                Node menuView = getMenu(stag.getStage().getHeight());

                contentStack.getChildren().addAll(blurPane, menuView);
                break;
        }

        vbox.getChildren().addAll(stageDecoration, contentStack);

        /*
         * Resize rectangle
         */
        resizeRect = new Rectangle();
        resizeRect.setFill(null);
        resizeRect.setStrokeWidth(RESIZE_PADDING);
        resizeRect.setStrokeType(StrokeType.INSIDE);
        resizeRect.setStroke(Configuration.decorationColor);
        windowController.setStageResizableWith(stag.getStage(), resizeRect, RESIZE_PADDING, SHADOW_WIDTH);

        buildDockFeedbackStage();

        shadowRectangle = new Rectangle();
        shadowRectangle.setMouseTransparent(true); // Do not intercept mouse events on stage's drop shadow

        // Add all layers
        super.getChildren().addAll(shadowRectangle, vbox, resizeRect);

        /*
         * Focused stage
         */
        stag.getStage().focusedProperty().addListener((ov, t, t1) -> setShadowFocused(t1));

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        clientArea.setMinSize(stag.getStage().getMinWidth() - SHADOW_WIDTH * 2 - RESIZE_PADDING * 2, stag.getStage().getMinHeight() - TITLEBAR_HEIGHT - SHADOW_WIDTH * 2 - RESIZE_PADDING * 2);
        clientArea.setPrefSize(stag.getStage().getWidth(), stag.getStage().getHeight() - SHADOW_WIDTH * 2 - TITLEBAR_HEIGHT - RESIZE_PADDING * 2);
        clientArea.setMaxSize(primaryScreenBounds.getWidth() - RESIZE_PADDING * 2, primaryScreenBounds.getHeight() - SHADOW_WIDTH * 2 - RESIZE_PADDING * 2);
    }

    /**
     * Sets the color of the window decoration & resize Rectangle
     */
    public void setDecorationColor() {
        resizeRect.setStroke(Configuration.decorationColor);
        stageDecoration.setStyle("-fx-background-color: " + ThemeSelector.getColorHexString(Configuration.decorationColor));
    }

    /**
     * Install default accelerators
     * @param scene The scene
     */
    void installAccelerators(Scene scene, int winType) {
        // Accelerators
        if (winType == 0) { scene.getAccelerators().put(new KeyCodeCombination(KeyCode.ESCAPE), () -> { if (isMenuOpen) { toggleSideBar(); } }); }
    }

    private void setStageStyle(StageStyle st) {
        stageStyle = st;
    }

    private StageStyle getStageStyle() {
        return stageStyle;
    }

    private void switchMinimize() {
        minimizeProperty().set(!minimizeProperty().get());
    }

    private void switchMaximize() {
        maximizeProperty().set(!maximizeProperty().get());
    }

    private void switchClose() {
        closeProperty().set(!closeProperty().get());
    }

    /**
     *  Returns the title bar in an AnchorPane.
     *  There are 2 different title bar types: (0/1)
     *  0: Creates a title bar with a menu button
     *  1: Creates a title bar with an application logo
     **/
    private AnchorPane getStageDecoration(Stage stage, String title, int type) {
        AnchorPane menuAnchor = new AnchorPane();
        URL titlebarStyle = WindowScene.class.getResource("ui/components/titlebar/TitleBarStyle.css");
        menuAnchor.getStylesheets().add(titlebarStyle.toString());
        menuAnchor.setStyle("-fx-background-color: " + ThemeSelector.getColorHexString(Configuration.decorationColor));
        menuAnchor.setMaxHeight(TITLEBAR_HEIGHT);

        //create left/right boxes for titlebar components
        HBox leftBox = new HBox(20);
        AnchorPane.setTopAnchor(leftBox, 0.0);
        AnchorPane.setLeftAnchor(leftBox, 5.0);
        menuAnchor.getChildren().add(leftBox);

        windowContentController.setTitlebarRightButtonBox(new HBox());
        HBox titlebar = windowContentController.getTitlebarButtonBox();
        titlebar.setAlignment(Pos.BASELINE_CENTER);
        titlebar.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        AnchorPane.setTopAnchor(titlebar, 0.0);
        AnchorPane.setRightAnchor(titlebar, 0.0);
        menuAnchor.getChildren().add(titlebar);

        //defines the type of the menu bar
        switch (type) {
            case 0:
                ImageView imageView = new ImageView(ImageLoader.getMenu());
                imageView.setViewport(new Rectangle2D(AnimationHandler.OFFSET_X, AnimationHandler.OFFSET_Y, AnimationHandler.WIDTH, TITLEBAR_HEIGHT));
                imageView.setPickOnBounds(true);
                imageView.setOnMouseMoved(event -> imageView.setImage(ImageLoader.getMenuHover()));
                imageView.setOnMouseExited(event -> imageView.setImage(ImageLoader.getMenu()));
                imageView.setOnMouseClicked(event -> toggleSideBar());

                //creates menu button animation
                AnimationHandler.createMenuBtnAnim(imageView, TITLEBAR_HEIGHT);

                leftBox.getChildren().add(imageView);
                break;
            case 1:
                System.out.println("stub -> getStageDecoration");
//                ImageView logo = new ImageView(stage.getIcons().get(0)); logo.fitHeightProperty().setValue(20); logo.fitWidthProperty().setValue(20);
//                HBox.setMargin(logo, new Insets(5, 0, 0, 4));
//                leftBox.getChildren().add(logo);
                break;
        }

        Label appTitle = new Label(title);
        appTitle.setPadding(new Insets(5, 0, 0, 0));
        stage.titleProperty().addListener((obs, oldTitle, newTitle) -> appTitle.setText(stage.getTitle()));
        leftBox.getChildren().add(appTitle);

        //define minimize/maximize/close button
        Button btn_close = new Button();
        btn_close.setTooltip(new Tooltip(LanguageController.getString("close")));
        btn_close.getStyleClass().add("decoration-button-close");
        btn_close.setOnAction(event -> switchClose());

        Button btn_minimize = new Button();
        btn_minimize.setTooltip(new Tooltip(LanguageController.getString("minimize")));
        btn_minimize.getStyleClass().add("decoration-button-minimize");
        btn_minimize.setOnAction(event -> switchMinimize());

//        Button btn_dev = new Button("dev");
//        btn_dev.setOnAction(event -> {
//            new MessageDialog(1, "dev button click", 300, 200);
//        });

        titlebar.getChildren().addAll(btn_close, btn_minimize);

        //hide minimize/close button if stage is modal
        if (stage.getModality().equals(Modality.APPLICATION_MODAL)) {
            btn_close.setDisable(true);
            btn_minimize.setDisable(true);
        }

        if (stage.isResizable()) {
            Button btn_maximize = new Button();
            btn_maximize.setTooltip(new Tooltip(LanguageController.getString("maximize")));
            btn_maximize.getStyleClass().add("decoration-button-maximize");

            btn_maximize.setOnAction(event -> switchMaximize());

            // Maximize on double click
            menuAnchor.setOnMouseClicked(mouseEvent -> {
                if (this.getStageStyle() != StageStyle.UTILITY && !stage.isFullScreen() && mouseEvent.getClickCount() > 1) {
                    switchMaximize();
                }
            });

            maximizeProperty().addListener((ov, t, t1) -> {
                Tooltip tooltip = btn_maximize.getTooltip();

                if (maximizeProperty().get()) {
                    btn_maximize.getStyleClass().remove("decoration-button-maximize");
                    btn_maximize.getStyleClass().add("decoration-button-restore");

                    if (tooltip.getText().equals(LanguageController.getString("maximize"))) {
                        tooltip.setText(LanguageController.getString("restore"));
                    }

                } else {
                    btn_maximize.getStyleClass().remove("decoration-button-restore");
                    btn_maximize.getStyleClass().add("decoration-button-maximize");

                    if (tooltip.getText().equals(LanguageController.getString("restore"))) {
                        tooltip.setText(LanguageController.getString("maximize"));
                    }
                }
            });
            titlebar.getChildren().add(1, btn_maximize);
        }

        //DEBUG COLORS AND DEV BUTTON
        if (DebugHelper.DEBUGVERSION) {
            leftBox.setStyle("-fx-background-color: gray");
            titlebar.setStyle("-fx-background-color: red");
//            titlebarRightButtonBox.getChildren().add(btn_dev);
        }
        return menuAnchor;
    }

    /**
     * Returns the SideBar in an AnchorPane
     * @param stageHeight The height of the sidebar
     */
    private AnchorPane getMenu(double stageHeight) {
        AnchorPane anchor = new AnchorPane();
        windowContentController.setSidebarButtonBox(new VBox());

        VBox sidebar = windowContentController.getSidebarButtonBox();
        sidebar.setPadding(new Insets(5, 5, 10, 5)); //top, right, bottom, left
        sidebar.setSpacing(10);

        Rectangle clipShape = new Rectangle(0, 0, AnimationHandler.menuWidth, stageHeight);
        anchor.setClip(clipShape);
        clipShape.xProperty().bind(AnimationHandler.xPosMenu);
        clipShape.heightProperty().bind(sideBarHeightProperty);

        SidebarButton btnCfg = new SidebarButton("settings");
        btnCfg.setOnAction(e -> {
            new SettingsScene();
            toggleSideBar();
        });

        SidebarButton btnAbout = new SidebarButton("about");
        btnAbout.setOnAction(e -> {
            new AboutScene();
            toggleSideBar();
        });

        windowContentController.addSidebarItem(btnCfg);
        windowContentController.addSidebarItem(btnAbout);
        getSidebarButtonBox().getChildren().setAll(windowContentController.getList());

        anchor.getChildren().addAll(getSidebarButtonBox());
        AnchorPane.setTopAnchor(sidebar, 0.0);

        console = new TextField();
        console.setTooltip(new Tooltip(LanguageController.getString("console_input_desc")));
        console.setPrefSize(AnimationHandler.menuWidth - 10, 30);
        console.setStyle("-fx-accent: " + colorTheme + ";" + "-fx-focus-color: " + colorTheme + ";");
        console.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                if (!console.getText().isEmpty()) {
                    LogFileHandler.logger.log(Level.INFO, "console_input: " + console.getText());
                    AppCommands.execCommand(console.getText());
                }
            }
        });
        AnchorPane.setBottomAnchor(console, 5.0);
        AnchorPane.setLeftAnchor(console, 5.0);
        anchor.getChildren().add(console);

        // set debug colors
        if (DebugHelper.isDebugVersion()) {
            anchor.setStyle("-fx-background-color: gray;");
        } else {
            anchor.setStyle("-fx-background-color: #1d1d1d;" + "-fx-focus-color: " + colorTheme + ";");
        }
        return anchor;
    }

    public void calcSidebarButtonSize(double stageHeight) {
        VBox sidebar = windowContentController.getSidebarButtonBox();

        // calculate button size
        double buttonSize = (stageHeight - TITLEBAR_HEIGHT - SHADOW_WIDTH * 2 - RESIZE_PADDING * 2 - console.getHeight() - sidebar.getSpacing() * (windowContentController.getList().size() + 1)) / windowContentController.getList().size();
        if (buttonSize > 90) { buttonSize = 90; } //set maximum button size

        // set prefSize of button
        SidebarButton button;
        for (int i = 0; i < windowContentController.getList().size(); i++) {
            button = windowContentController.getList().get(i);
            button.setPrefSize(AnimationHandler.menuWidth - 10, buttonSize);
        }

        sidebar.getChildren().setAll(windowContentController.getList());
    }

    /**
     *  Toggles the sidebar
     **/
    public void toggleSideBar() {
        isMenuOpen = !isMenuOpen;
        if (isMenuOpen) {
            AnimationHandler.slideOut.stop();
            AnimationHandler.stackFadeOut.stop();

            AnimationHandler.stackFadeIn.getNode().setVisible(true);
            AnimationHandler.stackFadeIn.play();

            AnimationHandler.menuBtnAnim.setRate(1);
            AnimationHandler.menuBtnAnim.play();
            AnimationHandler.slideIn.play();

            console.requestFocus();
        } else {
            AnimationHandler.slideIn.stop();
            AnimationHandler.stackFadeIn.stop();

            AnimationHandler.stackFadeOut.play();

            AnimationHandler.menuBtnAnim.setRate(-1);
            AnimationHandler.menuBtnAnim.playFrom("end");
            AnimationHandler.slideOut.play();
        }
    }


    /**
     * Switch the visibility of the window's drop shadow
     */
    void setShadow(boolean shadow) {
        // Already removed?
        if (!shadow && shadowRectangle.getEffect() == null) {
            return;
        }
        // From fullscreen to maximize case
        if (shadow && maximizeProperty.get()) {
            return;
        }
        if (!shadow) {
            shadowRectangle.setEffect(null);
            SAVED_SHADOW_WIDTH = SHADOW_WIDTH;
            SHADOW_WIDTH = 0;
        } else {
            shadowRectangle.setEffect(dsFocused);
            SHADOW_WIDTH = SAVED_SHADOW_WIDTH;
        }
    }

    /**
     * Set on/off the stage shadow effect
     * @param b Shadow effect bool
     */
    private void setShadowFocused(boolean b) {
        // Do not change anything while maximized (in case of dialog closing for instance)
        if (maximizeProperty().get()) {
            return;
        }
        if (b) {
            shadowRectangle.setEffect(dsFocused);
        } else {
            shadowRectangle.setEffect(dsNotFocused);
        }
    }

    /**
     * Set the layout of different layers of the stage
     */
    @Override
    public void layoutChildren() {
        Bounds b = super.getLayoutBounds();
        double w = b.getWidth();
        double h = b.getHeight();
        ObservableList<Node> list = super.getChildren();
        for (Node node : list) {
            if (node == shadowRectangle) {
                shadowRectangle.setWidth(w - SHADOW_WIDTH * 2);
                shadowRectangle.setHeight(h - SHADOW_WIDTH * 2);
                shadowRectangle.setX(SHADOW_WIDTH);
                shadowRectangle.setY(SHADOW_WIDTH);
            } else if (node == stageDecoration) {
                stageDecoration.resize(w - SHADOW_WIDTH * 2, h - SHADOW_WIDTH * 2);
                stageDecoration.setLayoutX(SHADOW_WIDTH);
                stageDecoration.setLayoutY(SHADOW_WIDTH);
            } else if (node == resizeRect) {
                resizeRect.setWidth(w - SHADOW_WIDTH * 2);
                resizeRect.setHeight(h - SHADOW_WIDTH * 2);
                resizeRect.setLayoutX(SHADOW_WIDTH);
                resizeRect.setLayoutY(SHADOW_WIDTH);
            } else {
                node.resize(w - SHADOW_WIDTH * 2 - RESIZE_PADDING * 2, h - SHADOW_WIDTH * 2 - RESIZE_PADDING * 2);
                node.setLayoutX(SHADOW_WIDTH + RESIZE_PADDING);
                node.setLayoutY(SHADOW_WIDTH + RESIZE_PADDING);
            }
        }
    }

    /**
     * Activate dock feedback on screen's bounds
     * @param x X-Coordinate
     * @param y Y-Coordinate
     */
    void setDockFeedbackVisible(double x, double y, double width, double height) {
        dockFeedbackPopup.setX(x);
        dockFeedbackPopup.setY(y);

        dockFeedback.setX(SHADOW_WIDTH);
        dockFeedback.setY(SHADOW_WIDTH);
        dockFeedback.setHeight(height - SHADOW_WIDTH * 2);
        dockFeedback.setWidth(width - SHADOW_WIDTH * 2);

        dockFeedbackPopup.setWidth(width);
        dockFeedbackPopup.setHeight(height);

        dockFeedback.setOpacity(1);
        dockFeedbackPopup.show();
    }

    void setDockFeedbackInvisible() {
        if (dockFeedbackPopup.isShowing()) {
            dockFeedbackPopup.hide();
        }
    }

    /**
     * Prepare Stage for dock feedback display
     */
    private void buildDockFeedbackStage() {
        dockFeedbackPopup = new Stage(StageStyle.TRANSPARENT);
        //dockFeedbackPopup.getIcons().add(ImageLoaderUtil.app_icon_small);
        dockFeedback = new Rectangle(0, 0, 100, 100);
        dockFeedback.setArcHeight(10);
        dockFeedback.setArcWidth(10);
        dockFeedback.setFill(Color.TRANSPARENT);
        dockFeedback.setStroke(Color.valueOf("#1d1d1d"));
        dockFeedback.setStrokeWidth(2);
        dockFeedback.setCache(true);
        dockFeedback.setCacheHint(CacheHint.SPEED);
        dockFeedback.setEffect(new DropShadow(BlurType.TWO_PASS_BOX, Color.BLACK, 10, 0.2, 3, 3));
        dockFeedback.setMouseTransparent(true);
        BorderPane borderpane = new BorderPane();
        borderpane.setStyle("-fx-background-color:transparent");
        borderpane.setCenter(dockFeedback);
        Scene scene = new Scene(borderpane);
        scene.setFill(Color.TRANSPARENT);
        dockFeedbackPopup.setScene(scene);
        dockFeedbackPopup.sizeToScene();
    }
}
