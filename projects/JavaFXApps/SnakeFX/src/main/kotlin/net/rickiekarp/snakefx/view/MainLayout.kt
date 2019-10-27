package net.rickiekarp.snakefx.view

import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.Tooltip
import javafx.scene.layout.*
import net.rickiekarp.core.components.FoldableListCell
import net.rickiekarp.core.controller.LanguageController
import net.rickiekarp.core.model.SettingEntry
import net.rickiekarp.core.settings.Configuration
import net.rickiekarp.core.view.AboutScene
import net.rickiekarp.core.view.SettingsScene
import net.rickiekarp.core.view.layout.AppLayout
import net.rickiekarp.snakefx.core.Grid
import net.rickiekarp.snakefx.util.FxmlFactory
import net.rickiekarp.snakefx.view.presenter.MainPresenter

class MainLayout(private val fxmlFactory: FxmlFactory, private val gridContainer: Pane) : AppLayout {
    override val layout: Node
        get() {

            val borderpane = BorderPane()

            val settingsGrid = GridPane()
            settingsGrid.prefWidth = 200.0
            settingsGrid.vgap = 10.0
            settingsGrid.padding = Insets(5.0, 0.0, 0.0, 0.0)  //padding top, left, bottom, right
            settingsGrid.alignment = Pos.BASELINE_CENTER

            //SETTINGS LIST
            val list = ListView<SettingEntry>()
            GridPane.setConstraints(list, 0, 0)
            GridPane.setVgrow(list, Priority.ALWAYS)
            settingsGrid.children.add(list)

            val items = FXCollections.observableArrayList<SettingEntry>()
            items.add(SettingEntry("setting_1",false, getOptions("setting_1_desc")))
            list.items.setAll(items)

            list.setCellFactory { FoldableListCell(list) }


            borderpane.center = gridContainer
//            borderpane.right = settingsGrid
            borderpane.top = fxmlFactory.getFxmlRoot(FXMLFile.PANEL)

            return borderpane
        }

    private fun getOptions(description: String): VBox {
        val content = VBox()
        content.spacing = 5.0

        content.children.add(Label(description))

        return content
    }

    override fun postInit() {
        // do nothing
    }

}
