package com.rkarp.appcore.util;

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

    public static Image getMenu() {
        if (menu == null) {
            menu = new Image("ui/components/titlebar/menu.png");
        }
        return menu;
    }

    public static Image getMenuHover() {
        if (menuHover == null) {
            menuHover = new Image("ui/components/titlebar/menu-hover.png");
        }
        return menuHover;
    }
}
