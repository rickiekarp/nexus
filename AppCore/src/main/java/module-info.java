module rkarp.appcore {
    opens com.rkarp.appcore;
    exports com.rkarp.appcore;

    requires java.logging;
    requires kotlin.stdlib;
    requires java.desktop;
    requires javafx.controls;
}