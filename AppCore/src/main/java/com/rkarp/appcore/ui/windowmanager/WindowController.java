package com.rkarp.appcore.ui.windowmanager;

import com.rkarp.appcore.debug.LogFileHandler;
import com.rkarp.appcore.view.MainScene;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class WindowController {

    private final int DOCK_NONE = 0x0;
    private final int DOCK_LEFT = 0x1;
    private final int DOCK_RIGHT = 0x2;
    private final int DOCK_TOP = 0x4;
    private int lastDocked = DOCK_NONE;
    private double initX = -1;
    private double initY = -1;
    private double newX;
    private double newY;
    private int RESIZE_PADDING;
    private int SHADOW_WIDTH;
    private Window window;
    private BoundingBox savedBounds;
    private boolean maximized = false;

    WindowController(Window ud) {
        window = ud;
    }

    void maximizeOrRestore() {
        Stage stage = window.getWindowStage().getStage();

        if (maximized) {
            restoreSavedBounds(stage);
            window.setShadow(true);
            savedBounds = null;
            maximized = false;
        } else {
            ObservableList<Screen> screensForRectangle = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
            Screen screen = screensForRectangle.get(0);
            Rectangle2D visualBounds = screen.getVisualBounds();

            savedBounds = new BoundingBox(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());

            window.setShadow(false);

            stage.setX(visualBounds.getMinX());
            stage.setY(visualBounds.getMinY());
            stage.setWidth(visualBounds.getWidth());
            stage.setHeight(visualBounds.getHeight());

            window.sideBarHeightProperty.set(visualBounds.getHeight());
            maximized = true;
        }
    }

    private void restoreSavedBounds(Stage stage) {
        stage.setX(savedBounds.getMinX());
        stage.setY(savedBounds.getMinY());
        stage.setWidth(savedBounds.getWidth());
        stage.setHeight(savedBounds.getHeight());
        savedBounds = null;
    }

    public void close() {
        final WindowStage stage = window.getWindowStage();
        LogFileHandler.logger.info("close." + stage.getIdentifier());
        Platform.runLater(() -> stage.getStage().fireEvent(new WindowEvent(stage.getStage(), WindowEvent.WINDOW_CLOSE_REQUEST)));
        MainScene.stageStack.pop(stage.getIdentifier());
    }

    public void minimize() {
        if (!Platform.isFxApplicationThread()) // Ensure on correct thread else hangs X under Unbuntu
        {
            Platform.runLater(this::_minimize);
        } else {
            _minimize();
        }
    }

    private void _minimize() {
        final WindowStage stage = window.getWindowStage();
        stage.getStage().setIconified(true);
    }

    /**
     * Stage resize management
     * @param stage Stage to resize
     * @param node Node to make draggable
     * @param PADDING Resize padding
     * @param SHADOW Shadow width
     */
    void setStageResizableWith(final Stage stage, final Node node, int PADDING, int SHADOW) {
        RESIZE_PADDING = PADDING;
        SHADOW_WIDTH = SHADOW;

        node.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown()) {
                initX = mouseEvent.getScreenX();
                initY = mouseEvent.getScreenY();
                mouseEvent.consume();
            }
        });
        node.setOnMouseDragged(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown() || (initX == -1 && initY == -1)) {
                return;
            }

            //Long press generates drag event!
            if (mouseEvent.isStillSincePress()) {
                return;
            }
            if (maximized) {
                // Remove maximized state
                window.maximizeProperty.set(false);
                return;
            } // Docked then moved, so restore state
            else if (savedBounds != null) {
                window.setShadow(true);
            }


            newX = mouseEvent.getScreenX();
            newY = mouseEvent.getScreenY();
            double deltax = newX - initX;
            double deltay = newY - initY;

            Cursor cursor = node.getCursor();
            if (Cursor.E_RESIZE.equals(cursor)) {
                setStageWidth(stage, stage.getWidth() + deltax);
                mouseEvent.consume();
            } else if (Cursor.NE_RESIZE.equals(cursor)) {
                if (setStageHeight(stage, stage.getHeight() - deltay)) {
                    setStageY(stage, stage.getY() + deltay);
                }
                setStageWidth(stage, stage.getWidth() + deltax);
                mouseEvent.consume();
            } else if (Cursor.SE_RESIZE.equals(cursor)) {
                setStageWidth(stage, stage.getWidth() + deltax);
                setStageHeight(stage, stage.getHeight() + deltay);
                mouseEvent.consume();
            } else if (Cursor.S_RESIZE.equals(cursor)) {
                setStageHeight(stage, stage.getHeight() + deltay);
                mouseEvent.consume();
            } else if (Cursor.W_RESIZE.equals(cursor)) {
                if (setStageWidth(stage, stage.getWidth() - deltax)) {
                    stage.setX(stage.getX() + deltax);
                }
                mouseEvent.consume();
            } else if (Cursor.SW_RESIZE.equals(cursor)) {
                if (setStageWidth(stage, stage.getWidth() - deltax)) {
                    stage.setX(stage.getX() + deltax);
                }
                setStageHeight(stage, stage.getHeight() + deltay);
                mouseEvent.consume();
            } else if (Cursor.NW_RESIZE.equals(cursor)) {
                if (setStageWidth(stage, stage.getWidth() - deltax)) {
                    stage.setX(stage.getX() + deltax);
                }
                if (setStageHeight(stage, stage.getHeight() - deltay)) {
                    setStageY(stage, stage.getY() + deltay);
                }
                mouseEvent.consume();
            } else if (Cursor.N_RESIZE.equals(cursor)) {
                if (setStageHeight(stage, stage.getHeight() - deltay)) {
                    setStageY(stage, stage.getY() + deltay);
                }
                mouseEvent.consume();
            }
        });

        node.setOnMouseMoved(mouseEvent -> {
            if (maximized) {
                setCursor(node, Cursor.DEFAULT);
                return; // maximized mode does not support resize
            }
            if (!stage.isResizable()) {
                return;
            }
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            Bounds boundsInParent = node.getBoundsInParent();
            if (isRightEdge(x, y, boundsInParent)) {
                if (y < RESIZE_PADDING + SHADOW_WIDTH) {
                    setCursor(node, Cursor.NE_RESIZE);
                } else if (y > boundsInParent.getHeight() - (double) (RESIZE_PADDING + SHADOW_WIDTH)) {
                    setCursor(node, Cursor.SE_RESIZE);
                } else {
                    setCursor(node, Cursor.E_RESIZE);
                }

            } else if (isLeftEdge(x, y, boundsInParent)) {
                if (y < RESIZE_PADDING + SHADOW_WIDTH) {
                    setCursor(node, Cursor.NW_RESIZE);
                } else if (y > boundsInParent.getHeight() - (double) (RESIZE_PADDING + SHADOW_WIDTH)) {
                    setCursor(node, Cursor.SW_RESIZE);
                } else {
                    setCursor(node, Cursor.W_RESIZE);
                }
            } else if (isTopEdge(x, y, boundsInParent)) {
                setCursor(node, Cursor.N_RESIZE);
            } else if (isBottomEdge(x, y, boundsInParent)) {
                setCursor(node, Cursor.S_RESIZE);
            } else {
                setCursor(node, Cursor.DEFAULT);
            }
        });
    }

    /**
     * Under Windows, the window Stage could be been dragged below the Task
     * bar and then no way to grab it again... On Mac, do not drag above the
     * menu bar
     *
     * @param y The Y-Location
     */
    private void setStageY(Stage stage, double y) {
        try {
            ObservableList<Screen> screensForRectangle = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
            if (screensForRectangle.size() > 0) {
                Screen screen = screensForRectangle.get(0);
                Rectangle2D visualBounds = screen.getVisualBounds();
                if (y < visualBounds.getHeight() - 30 && y + SHADOW_WIDTH >= visualBounds.getMinY()) {
                    stage.setY(y);
                }
            }
        } catch (Exception e) {
            //ignore
        }
    }

    private boolean setStageWidth(Stage stage, double width) {
        if (width >= stage.getMinWidth()) {
            stage.setWidth(width);
            initX = newX;
            return true;
        }
        return false;
    }

    private boolean setStageHeight(Stage stage, double height) {
        if (height >= stage.getMinHeight()) {
            stage.setHeight(height);
            if (window.getWindowType() == 0) {
                window.sideBarHeightProperty.set(height);
                window.calcSidebarButtonSize(stage.getHeight());
            }
            initY = newY;
            return true;
        }
        return false;
    }

    /**
     * Allow this node to drag the Stage
     */
    void setAsStageDraggable(final Stage stage, final Node node) {
        node.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown()) {
                initX = mouseEvent.getScreenX();
                initY = mouseEvent.getScreenY();
                mouseEvent.consume();
            } else {
                initX = -1;
                initY = -1;
            }
        });
        node.setOnMouseDragged(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown() || initX == -1) {
                return;
            }

            /*
             * Long press generates drag event!
             */
            if (mouseEvent.isStillSincePress()) {
                return;
            }
            if (maximized) {
                // Remove Maximized state
                window.maximizeProperty.set(false);
                // Center
                stage.setX(mouseEvent.getScreenX() - stage.getWidth() / 2);
                stage.setY(mouseEvent.getScreenY() - SHADOW_WIDTH);
            } // Docked then moved, so restore state
            else if (savedBounds != null) {
                restoreSavedBounds(stage);
                window.setShadow(true);
                // Center
                stage.setX(mouseEvent.getScreenX() - stage.getWidth() / 2);
                stage.setY(mouseEvent.getScreenY() - SHADOW_WIDTH);
            }
            double newX1 = mouseEvent.getScreenX();
            double newY1 = mouseEvent.getScreenY();
            double deltax = newX1 - initX;
            double deltay = newY1 - initY;
            initX = newX1;
            initY = newY1;
            setCursor(node, Cursor.HAND);
            stage.setX(stage.getX() + deltax);
            setStageY(stage, stage.getY() + deltay);

            testDock(stage, mouseEvent);
            mouseEvent.consume();
        });

        node.setOnMouseReleased(t -> {
            if (stage.isResizable()) {
                window.setDockFeedbackInvisible();
                setCursor(node, Cursor.DEFAULT);
                initX = -1;
                initY = -1;
                dockActions(stage, t);
            }
        });

        node.setOnMouseExited(mouseEvent -> setCursor(node, Cursor.DEFAULT));
    }

    /**
     * (Humble) Simulation of Windows behavior on screen's edges Feedbacks
     */
    private void testDock(Stage stage, MouseEvent mouseEvent) {
        if (!stage.isResizable()) {
            return;
        }

        int dockSide = getDockSide(mouseEvent);
        // Dock Left
        if (dockSide == DOCK_LEFT) {
            if (lastDocked == DOCK_LEFT) {
                return;
            }
            ObservableList<Screen> screensForRectangle = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
            Screen screen = screensForRectangle.get(0);
            Rectangle2D visualBounds = screen.getVisualBounds();
            // Dock Left
            double x = visualBounds.getMinX();
            double y = visualBounds.getMinY();
            double width = visualBounds.getWidth() / 2;
            double height = visualBounds.getHeight();

            window.setDockFeedbackVisible(x, y, width, height);
            lastDocked = DOCK_LEFT;
        } // Dock Right
        else if (dockSide == DOCK_RIGHT) {
            if (lastDocked == DOCK_RIGHT) {
                return;
            }
            ObservableList<Screen> screensForRectangle = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
            Screen screen = screensForRectangle.get(0);
            Rectangle2D visualBounds = screen.getVisualBounds();
            // Dock Right (visualBounds = (javafx.geometry.Rectangle2D) Rectangle2D [minX = 1440.0, minY=300.0, maxX=3360.0, maxY=1500.0, width=1920.0, height=1200.0])
            double x = visualBounds.getMinX() + visualBounds.getWidth() / 2;
            double y = visualBounds.getMinY();
            double width = visualBounds.getWidth() / 2;
            double height = visualBounds.getHeight();

            window.setDockFeedbackVisible(x, y, width, height);
            lastDocked = DOCK_RIGHT;
        } // Dock top
        else if (dockSide == DOCK_TOP) {
            if (lastDocked == DOCK_TOP) {
                return;
            }
            ObservableList<Screen> screensForRectangle = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
            Screen screen = screensForRectangle.get(0);
            Rectangle2D visualBounds = screen.getVisualBounds();
            // Dock Left
            double x = visualBounds.getMinX();
            double y = visualBounds.getMinY();
            double width = visualBounds.getWidth();
            double height = visualBounds.getHeight();
            window.setDockFeedbackVisible(x, y, width, height);
            lastDocked = DOCK_TOP;
        } else {
            window.setDockFeedbackInvisible();
            lastDocked = DOCK_NONE;
        }
    }

    /**
     * Based on mouse position returns dock side
     * @param mouseEvent The mouse event
     * @return DOCK_LEFT,DOCK_RIGHT,DOCK_TOP
     */
    private int getDockSide(MouseEvent mouseEvent) {
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxX = 0;
        double maxY = 0;

        // Get "big" screen bounds
        ObservableList<Screen> screens = Screen.getScreens();
        for (Screen screen : screens) {
            Rectangle2D visualBounds = screen.getVisualBounds();
            minX = Math.min(minX, visualBounds.getMinX());
            minY = Math.min(minY, visualBounds.getMinY());
            maxX = Math.max(maxX, visualBounds.getMaxX());
            maxY = Math.max(maxY, visualBounds.getMaxY());
        }
        // Dock Left
        if (mouseEvent.getScreenX() == minX) {
            return DOCK_LEFT;
        } else if (mouseEvent.getScreenX() >= maxX - 1) { // MaxX returns the width? Not width -1 ?!
            return DOCK_RIGHT;
        } else if (mouseEvent.getScreenY() <= minY) {   // Mac menu bar
            return DOCK_TOP;
        }
        return 0;
    }

    /**
     * (Humble) Simulation of Windows behavior on screen's edges Actions
     */
    private void dockActions(Stage stage, MouseEvent mouseEvent) {
        ObservableList<Screen> screensForRectangle = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
        Screen screen = screensForRectangle.get(0);
        Rectangle2D visualBounds = screen.getVisualBounds();
        // Dock Left
        if (mouseEvent.getScreenX() == visualBounds.getMinX()) {
            savedBounds = new BoundingBox(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());

            stage.setX(visualBounds.getMinX());
            stage.setY(visualBounds.getMinY());
            // Respect Stage Max size
            double width = visualBounds.getWidth() / 2;
            if (stage.getMaxWidth() < width) {
                width = stage.getMaxWidth();
            }

            stage.setWidth(width);

            double height = visualBounds.getHeight();
            if (stage.getMaxHeight() < height) {
                height = stage.getMaxHeight();
            }

            stage.setHeight(height);
            window.setShadow(false);
        } // Dock Right (visualBounds = [minX = 1440.0, minY=300.0, maxX=3360.0, maxY=1500.0, width=1920.0, height=1200.0])
        else if (mouseEvent.getScreenX() >= visualBounds.getMaxX() - 1) { // MaxX returns the width? Not width -1 ?!
            savedBounds = new BoundingBox(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());

            stage.setX(visualBounds.getWidth() / 2 + visualBounds.getMinX());
            stage.setY(visualBounds.getMinY());
            // Respect Stage Max size
            double width = visualBounds.getWidth() / 2;
            if (stage.getMaxWidth() < width) {
                width = stage.getMaxWidth();
            }

            stage.setWidth(width);

            double height = visualBounds.getHeight();
            if (stage.getMaxHeight() < height) {
                height = stage.getMaxHeight();
            }

            stage.setHeight(height);
            window.setShadow(false);
        } else if (mouseEvent.getScreenY() <= visualBounds.getMinY()) { // Mac menu bar
            window.maximizeProperty.set(true);
        }
    }

    private boolean isRightEdge(double x, double y, Bounds boundsInParent) {
        return x < boundsInParent.getWidth() && x > boundsInParent.getWidth() - RESIZE_PADDING - SHADOW_WIDTH;
    }

    private boolean isTopEdge(double x, double y, Bounds boundsInParent) {
        return y >= 0 && y < RESIZE_PADDING + SHADOW_WIDTH;
    }

    private boolean isBottomEdge(double x, double y, Bounds boundsInParent) {
        return y < boundsInParent.getHeight() && y > boundsInParent.getHeight() - RESIZE_PADDING - SHADOW_WIDTH;
    }

    private boolean isLeftEdge(double x, double y, Bounds boundsInParent) {
        return x >= 0 && x < RESIZE_PADDING + SHADOW_WIDTH;
    }

    private void setCursor(Node n, Cursor c) { n.setCursor(c); }
}
