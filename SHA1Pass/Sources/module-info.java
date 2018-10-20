module sha1pass {
    opens com.rkarp.sha1pass;
    exports com.rkarp.sha1pass;

    requires javafx.controls;
    requires api;
    requires config;
}