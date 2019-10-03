package net.rickiekarp.core.components.button;

import javafx.scene.control.Button;

public final class SidebarButton extends Button {
    private final String identifier;

    public final String getIdentifier() {
        return this.identifier;
    }

    public SidebarButton(String identifier) {
        this.identifier = identifier;
        setText(identifier);
        getStyleClass().addAll("sidebar-button");
    }
}