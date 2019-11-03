package net.rickiekarp.snakefx.view

import java.net.URL

enum class FXMLFile private constructor(path: String) {

    PANEL("panel.fxml");

    private val url: URL?
    private val BASEDIR = "fxml"

    init {

        val base = this.javaClass.classLoader.getResource(BASEDIR)
        checkNotNull(base) { "Can't find the base directory of the fxml files [$base]" }

        val fxmlFilePath = "$BASEDIR/$path"
        url = this.javaClass.classLoader.getResource(fxmlFilePath)

        checkNotNull(url) { "Can't find the fxml file [$fxmlFilePath]" }
    }

    fun url(): URL? {
        return url
    }
}
