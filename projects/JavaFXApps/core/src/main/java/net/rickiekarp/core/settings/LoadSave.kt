package net.rickiekarp.core.settings

import net.rickiekarp.core.controller.LanguageController
import javafx.geometry.Side
import javafx.scene.paint.Color

/** Annotation for indicating load/save a field.  */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class LoadSave {
    companion object {

        /** settings defaults  */
        val host = "https://app.rickiekarp.net/"
        val updateChannel = 0
        val language = LanguageController.locale
        val themeState = 0
        val colorScheme = 0
        val animations = true
        val useSystemBorders = false
        val logState = false
        val showTrayIcon = false

        /** advanced settings defaults  */
        val debugState = false
        val decorationColor = Color.valueOf("#1d1d1d")
        val shadowColorFocused = Color.valueOf("#000000")
        val shadowColorNotFocused = Color.valueOf("#a9a9a9")
        val tabPosition = Side.LEFT
    }
}
