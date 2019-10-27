package net.rickiekarp.snakefx.view

import javafx.scene.Node
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import net.rickiekarp.core.view.layout.AppLayout
import net.rickiekarp.snakefx.core.Grid
import net.rickiekarp.snakefx.util.FxmlFactory
import net.rickiekarp.snakefx.view.presenter.MainPresenter

class MainLayout(private val fxmlFactory: FxmlFactory, private val gridContainer: Pane) : AppLayout {
    override val layout: Node
        get() {

            val borderpane = BorderPane()

            borderpane.center = gridContainer
            borderpane.left = fxmlFactory.getFxmlRoot(FXMLFile.PANEL)

            return borderpane
        }

    override fun postInit() {
        // do nothing
    }

}
