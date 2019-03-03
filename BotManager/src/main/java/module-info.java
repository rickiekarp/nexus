module botmanager {
    requires botlib;
    requires core;
    requires javafx.graphics;

    opens net.rickiekarp.botter;
    exports net.rickiekarp.botter;
}