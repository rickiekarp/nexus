package net.rickiekarp.core.components.button;

import javafx.scene.control.Button;
import kotlin.jvm.internal.Intrinsics;

public final class SidebarButton extends Button {
    private final String identifier;

    public final String getIdentifier() {
        return this.identifier;
    }

    public SidebarButton(String identifier) {
        Intrinsics.checkParameterIsNotNull(identifier, "identifier");
        this.identifier = identifier;
        this.getStyleClass().addAll("sidebar-button");
    }
}