module botlib {
    requires api;
    requires core;
    requires java.logging;
    requires java.desktop;
    requires javafx.base;

    opens net.rickiekarp.botlib;
    exports net.rickiekarp.botlib;
    exports net.rickiekarp.botlib.model;
    exports net.rickiekarp.botlib.net;
}