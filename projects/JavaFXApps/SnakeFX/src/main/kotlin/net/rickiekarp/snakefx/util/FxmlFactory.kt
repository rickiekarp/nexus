package net.rickiekarp.snakefx.util

import net.rickiekarp.snakefx.view.FXMLFile
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.util.Callback

import java.io.IOException

/**
 * This factory can be used to load FXML documents and get the root element of
 * the given fxml document.
 */
class FxmlFactory(private val controllerInjector: Callback<Class<*>, Any>) {

    fun getFxmlRoot(file: FXMLFile): Parent {
        val loader = FXMLLoader(file.url())

        loader.controllerFactory = controllerInjector

        try {
            loader.load<Any>()
        } catch (e: IOException) {
            throw IllegalStateException("Can't load FXML file [" + file.url() + "]", e)
        }

        return loader.getRoot()
    }
}
