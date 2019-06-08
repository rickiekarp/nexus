package net.rickiekarp.core.ui.windowmanager;

import javafx.scene.image.Image;

public class ImageLoader {
    private static Image appIcon;
    private static Image appIconSmall;
    private static Image menu;
    private static Image menuHover;

    public static Image getAppIcon() {
        if (appIcon == null) {
            appIcon = new Image("ui/icons/app_icon_big.png");
        }
        return appIcon;
    }

    public static Image getAppIconSmall() {
        if (appIconSmall == null) {
            appIconSmall = new Image("ui/icons/app_icon_small.png");
        }
        return appIconSmall;
    }

    static Image getMenu() {
        if (menu == null) {
            menu = new Image(String.valueOf(ImageLoader.class.getResource("components/titlebar/menu.png")));
        }
        System.out.println(menu);
        return menu;
    }

    static Image getMenuHover() {
        if (menuHover == null) {
            menuHover = new Image(String.valueOf(ImageLoader.class.getResource("components/titlebar/menu-hover.png")));
        }
        System.out.println(menuHover);
        return menuHover;
    }
}
