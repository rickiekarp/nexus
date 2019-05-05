module qaacc {
    requires javafx.controls;
    requires api;
    requires core;
    requires java.xml;
    requires java.logging;

    opens net.rickiekarp.qaacc;
    opens net.rickiekarp.qaacc.settings;

    exports net.rickiekarp.qaacc;
}