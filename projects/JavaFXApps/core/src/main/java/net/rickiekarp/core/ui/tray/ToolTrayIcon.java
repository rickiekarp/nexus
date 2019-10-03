package net.rickiekarp.core.ui.tray;

import net.rickiekarp.core.AppContext;
import net.rickiekarp.core.view.MainScene;
import javafx.application.Platform;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class ToolTrayIcon {

    public static ToolTrayIcon icon;
    private java.awt.TrayIcon trayIcon;
    private java.awt.SystemTray tray;

    public ToolTrayIcon() {
        setUpTray();
        addAppToTray();
        icon = this;
    }

    public java.awt.SystemTray getSystemTray() {
        return tray;
    }

    private void setUpTray() {
        try {
            // ensure awt toolkit is initialized.
            java.awt.Toolkit.getDefaultToolkit();

            // app requires system tray support, just exit if there is no support.
            if (!java.awt.SystemTray.isSupported()) {
                System.out.println("No system tray support, application exiting.");
                Platform.exit();
            }

            // set up a system tray icon.
            tray = java.awt.SystemTray.getSystemTray();

            String imgName = "ui/icons/app_icon_small.png";
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(imgName);

            java.awt.Image image = ImageIO.read(is);
            trayIcon = new java.awt.TrayIcon(image);

            trayIcon.setToolTip("Giveaway Bot");

            // if the user double-clicks on the tray icon, show the main app stage.
            trayIcon.addActionListener(event -> Platform.runLater(this::showStage));

            // if the user selects the default menu item (which includes the app name),
            // show the main app stage.
            java.awt.MenuItem openItem = new java.awt.MenuItem(AppContext.getContext().getApplicationName());
            openItem.addActionListener(event -> Platform.runLater(this::showStage));

            // the convention for tray icons seems to be to set the default icon for opening
            // the application stage in a bold font.
            java.awt.Font defaultFont = java.awt.Font.decode(null);
            java.awt.Font boldFont = defaultFont.deriveFont(java.awt.Font.BOLD);
            openItem.setFont(boldFont);

            // to really exit the application, the user must go to the system tray icon
            // and select the exit option, this will shutdown JavaFX and remove the
            // tray icon (removing the tray icon will also shut down AWT).
            java.awt.MenuItem exitItem = new java.awt.MenuItem("Exit");
            exitItem.addActionListener(event -> {
                System.exit(0);
            });

            // setup the popup menu for the application.
            final java.awt.PopupMenu popup = new java.awt.PopupMenu();
            popup.add(openItem);
            popup.addSeparator();
            popup.add(exitItem);
            trayIcon.setPopupMenu(popup);

        } catch (IOException e) {
            System.out.println("Unable to init system tray");
            e.printStackTrace();
        }
    }

    /**
     * Sets up a system tray icon for the application.
     */
    public void addAppToTray() {

        // add the application tray icon to the system tray.
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }

        // instructs the javafx system not to exit implicitly when the last application window is shut.
        Platform.setImplicitExit(false);
    }

    public void displayTrayMessage() {
        trayIcon.displayMessage(
                "Information!",
                "Bot starts in 15 seconds...",
                java.awt.TrayIcon.MessageType.INFO
        );
    }
    
    public void removeTrayIcon() {
        tray.remove(trayIcon);
        Platform.setImplicitExit(true);
    }

    /**
     * Shows the application stage and ensures that it is brought ot the front of all stages.
     */
    private void showStage() {
        if (MainScene.mainScene.getWindowScene().getWin().getWindowStage() != null) {
            MainScene.mainScene.getWindowScene().getWin().getWindowStage().getStage().show();
            MainScene.mainScene.getWindowScene().getWin().getWindowStage().getStage().toFront();
        }
    }
}