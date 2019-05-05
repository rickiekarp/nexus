package net.rickiekarp.core.ui.windowmanager;

import javafx.scene.image.Image;
import net.rickiekarp.api.HelloService;
import net.rickiekarp.core.HelloFactory;

import java.net.URL;

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

    static Image getMenu() {
        if (menu == null) {
            URL buttonStyle = ImageLoader.class.getResource("ui/components/titlebar/menu.png");
            menu = new Image(buttonStyle.getFile());
        }
        System.out.println(menu);
        return menu;
    }

    static Image getMenuHover() {
        if (menuHover == null) {
            URL buttonStyle = ImageLoader.class.getResource("ui/components/titlebar/menu-hover.png");
            menuHover = new Image(buttonStyle.getFile());
        }
        System.out.println(menuHover);
        return menuHover;
    }
}
