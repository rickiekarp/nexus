module qaacc {
    requires javafx.controls;
    requires api;
    requires core;
    requires java.xml;
    requires java.logging;

    opens net.rickiekarp.qaacc;
    exports net.rickiekarp.qaacc;
}