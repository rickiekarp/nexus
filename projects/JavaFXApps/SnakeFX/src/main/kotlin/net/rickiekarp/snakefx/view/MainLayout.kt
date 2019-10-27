package net.rickiekarp.snakefx.view

import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.layout.*
import net.rickiekarp.core.components.FoldableListCell
import net.rickiekarp.core.model.SettingEntry
import net.rickiekarp.core.view.layout.AppLayout
import net.rickiekarp.snakefx.util.FxmlFactory

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
//            borderpane.top = getPanel()

            return borderpane
        }

    private fun getPanel(): Node {
        val hbox = HBox()
        hbox.spacing = 15.0
        hbox.prefWidth = 74.0
        hbox.alignment = Pos.CENTER_LEFT
        hbox.padding = Insets(10.0, 10.0, 10.0, 10.0)


//        Button fx:id="playPause" focusTraversable="false" mnemonicParsing="false" onAction="#togglePlayPause" text="Start" />
//        <Button fx:id="newGame" focusTraversable="false" mnemonicParsing="false" onAction="#newGame" text="New Game" />
//        <Button fx:id="showHighscores" focusTraversable="false" mnemonicParsing="false" onAction="#showHighscores" text="Highscores" />
//        <Label text="Difficulty:" />
//        <ChoiceBox fx:id="speed" focusTraversable="false" />

        val pointsText = Label("Points: ")
        val pointsLabel = Label("0")
        val playPause = Button("playPause")
        val newGame = Button("newGame")
        val showHighscores = Button("showHighscores")
        val difficultyLabel = Label("Difficulty:")
        val speed = ChoiceBox<String>()


        hbox.children.addAll(pointsText, pointsLabel, playPause, newGame, showHighscores, difficultyLabel, speed)

        return hbox
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
