package net.rickiekarp.core.util;

import javafx.scene.image.Image;
import net.rickiekarp.api.HelloService;
import net.rickiekarp.core.HelloFactory;

public class ImageLoader {
    private static Image appIcon;
    private static Image appIconSmall;
    private static Image menu;
    private static Image menuHover;

    public static Image getAppIcon() {
        if (appIcon == null) {
            final HelloService service = HelloFactory.createService();
            appIcon = new Image(service.getStream("icons/app_icon_big.png"));
        }
        return appIcon;
    }

    public static Image getAppIconSmall() {
        if (appIconSmall == null) {
            final HelloService service = HelloFactory.createService();
            appIconSmall = new Image(service.getStream("icons/app_icon_small.png"));
        }
        return appIconSmall;
    }

    public static Image getMenu() {
        if (menu == null) {
            final HelloService service = HelloFactory.createService();
            menu = new Image(service.getStream("ui/components/titlebar/menu.png"));
        }
        return menu;
    }

    public static Image getMenuHover() {
        if (menuHover == null) {
            final HelloService service = HelloFactory.createService();
            menuHover = new Image(service.getStream("ui/components/titlebar/menu-hover.png"));
        }
        return menuHover;
    }
}
