package com.rkarp.appcore.ui.windowmanager;

import javafx.stage.Stage;

public class WindowStage {
    private Stage stage;
    private String identifier;

    public WindowStage(String identifier) {
        this.stage = new Stage();
        this.identifier = identifier;
    }

    public WindowStage(String identifier, Stage stage) {
        this.stage = stage;
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public String toString() {
        return identifier + " - " + stage;
    }
}
