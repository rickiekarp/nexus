module rkarp.appcore {
    requires javafx.controls;
    requires java.logging;
    requires java.desktop;
    requires kotlin.stdlib;
    exports com.rkarp.appcore;
    exports com.rkarp.appcore.account;
    exports com.rkarp.appcore.controller;
    exports com.rkarp.appcore.debug;
    exports com.rkarp.appcore.settings;
    exports com.rkarp.appcore.ui.tray;
    exports com.rkarp.appcore.ui.windowmanager;
    exports com.rkarp.appcore.util;
    exports com.rkarp.appcore.util.crypt;
    exports com.rkarp.appcore.view;
    exports com.rkarp.appcore.components.textfield;
}