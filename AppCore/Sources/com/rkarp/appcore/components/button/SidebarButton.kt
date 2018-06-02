package com.rkarp.appcore.components.button

import com.rkarp.appcore.controller.LanguageController
import javafx.scene.control.Button

/**
 * Creates a basic button using some css properties defined in a Theme.css file com.rkarp.flc.themes
 */
class SidebarButton(val identifier: String) : Button(LanguageController.getString(identifier)) {

    init {
        styleClass.addAll("sidebar-button")
    }
}