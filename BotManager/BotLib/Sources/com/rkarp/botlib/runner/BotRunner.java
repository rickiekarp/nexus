package com.rkarp.botlib.runner;

import com.rkarp.botlib.model.Credentials;
import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * This class starts the bot on the defined platform.
 * Supported browsers: chrome, firefox
 * Supported mobile platforms: android
 * @param <DRI> Driver
 * @param <SVC> Driver service
 */
public abstract class BotRunner<DRI, SVC> {
    private DRI object;
    public DRI get() {
        return object;
    }
    void set(DRI obj) {
        this.object = obj;
    }

    private SVC driverService;
    SVC getDriverService() {
        return driverService;
    }
    public void setDriverService(SVC obj) {
        this.driverService = obj;
    }

    public abstract void start();

    public abstract void setLayout(Node layoutNode);

    public abstract void addSetting(String title, String description, boolean isVisible, Node settingNode);

    public abstract void addControlButton(Button... controlButton);
    public abstract void removeControlButton(int index);


    //--- Credentials
    private Credentials loginCredentials;
    public Credentials getCredentials() {
        return loginCredentials;
    }
    public void setCredentials(Credentials obj) {
        this.loginCredentials = obj;
    }
    public boolean hasCredentials() {
        return loginCredentials != null;
    }
}
