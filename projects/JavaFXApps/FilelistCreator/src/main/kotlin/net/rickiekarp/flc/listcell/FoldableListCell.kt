package net.rickiekarp.flc.listcell

import net.rickiekarp.core.controller.LanguageController
import net.rickiekarp.core.debug.DebugHelper
import net.rickiekarp.core.debug.LogFileHandler
import net.rickiekarp.core.view.AboutScene
import net.rickiekarp.core.view.SettingsScene
import net.rickiekarp.flc.controller.FilelistController
import net.rickiekarp.flc.model.FilelistSettings
import net.rickiekarp.flc.settings.AppConfiguration
import net.rickiekarp.flc.tasks.FileSizeTask
import net.rickiekarp.flc.tasks.FilelistPreviewTask
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Paint
import javafx.scene.shape.SVGPath

class FoldableListCell(private var list: ListView<FilelistSettings>?) : ListCell<FilelistSettings>() {

    private val generalOptions: VBox
        get() {

            val content = VBox()
            content.spacing = 5.0

            val btn_settings = Button()
            btn_settings.tooltip = Tooltip(LanguageController.getString("settings"))
            btn_settings.styleClass.add("decoration-button-settings")

            val btn_about = Button()
            btn_about.tooltip = Tooltip(LanguageController.getString("about"))
            btn_about.styleClass.add("decoration-button-about")

            btn_settings.setOnAction { event -> SettingsScene() }
            btn_about.setOnAction { event -> AboutScene() }

            val hbox = HBox()
            hbox.alignment = Pos.CENTER
            hbox.spacing = 5.0
            hbox.children.addAll(btn_settings, btn_about)

            content.children.addAll(hbox)
            return content
        }

    private val contentVBox1: VBox
        get() {

            val content = VBox()
            content.spacing = 5.0

            val l = list!!.items[0] as FilelistSettings

            val option1_desc = Label(LanguageController.getString(l.desc!!))
            option1_desc.isWrapText = true
            option1_desc.style = "-fx-font-size: 9pt;"
            option1_desc.maxWidth = 175.0

            val option = Label(LanguageController.getString("grouping"))
            val option1 = Label(LanguageController.getString("unit"))

            val cbox_sorting = ComboBox<String>()
            cbox_sorting.items.addAll(LanguageController.getString("none"), LanguageController.getString("folder"))
            cbox_sorting.selectionModel.select(0)
            cbox_sorting.minWidth = 100.0

            val cbox_unit = ComboBox<String>()
            cbox_unit.items.addAll(AppConfiguration.unitList)
            cbox_unit.selectionModel.select(FilelistController.UNIT_IDX)
            cbox_unit.minWidth = 100.0

            val header = CheckBox(LanguageController.getString("headerShow"))
            header.isSelected = FilelistController.canShowHeader

            val empty = CheckBox(LanguageController.getString("emptyFolderShow"))
            empty.isSelected = true

            cbox_sorting.valueProperty().addListener { ov, t, t1 ->
                FilelistController.sortingIdx = cbox_sorting.selectionModel.selectedIndex
                if (AppConfiguration.fileData.size > 0) {
                    FilelistPreviewTask()
                }
                when (cbox_sorting.selectionModel.selectedIndex) {
                    0 -> content.children.remove(empty)
                    1 -> content.children.add(empty)
                }
            }

            cbox_unit.valueProperty().addListener { ov, t, t1 ->
                FilelistController.UNIT_IDX = cbox_unit.selectionModel.selectedIndex
                if (AppConfiguration.fileData.size > 0) {
                    FileSizeTask()
                }
            }

            header.setOnAction { event ->
                FilelistController.canShowHeader = header.isSelected
                if (AppConfiguration.fileData.size > 0) {
                    FilelistPreviewTask()
                }

            }

            empty.setOnAction { event ->
                FilelistController.canShowEmptyFolder = empty.isSelected
                if (AppConfiguration.fileData.size > 0) {
                    FilelistPreviewTask()
                }
            }

            val hbox = HBox()
            hbox.alignment = Pos.CENTER_LEFT
            hbox.spacing = 5.0
            hbox.children.addAll(cbox_sorting, option)

            val hbox1 = HBox()
            hbox1.alignment = Pos.CENTER_LEFT
            hbox1.spacing = 5.0
            hbox1.children.addAll(cbox_unit, option1)

            content.children.addAll(option1_desc, hbox, hbox1, header)
            return content
        }

    private val contentVBox2: VBox
        get() {

            val content = VBox()
            content.spacing = 5.0

            val l = list!!.items[1] as FilelistSettings

            val option2_desc = Label(LanguageController.getString(l.desc!!))
            option2_desc.isWrapText = true
            option2_desc.style = "-fx-font-size: 9pt;"
            option2_desc.maxWidth = 175.0

            val filename = CheckBox(LanguageController.getString("name"))
            filename.isSelected = true
            FilelistController.option[0] = true
            filename.setOnAction { event ->
                FilelistController.option[0] = !FilelistController.option[0]
                LogFileHandler.logger.config("change_filename_option: " + !FilelistController.option[0] + " -> " + FilelistController.option[0])
                if (AppConfiguration.fileData.size > 0) {
                    FilelistPreviewTask()
                }
            }

            val type = CheckBox(LanguageController.getString("ftype"))
            type.isSelected = false
            FilelistController.option[1] = false
            type.setOnAction { event ->
                FilelistController.option[1] = !FilelistController.option[1]
                LogFileHandler.logger.config("change_type_option: " + !FilelistController.option[1] + " -> " + FilelistController.option[1])
                if (AppConfiguration.fileData.size > 0) {
                    FilelistPreviewTask()
                }
            }

            val path = CheckBox(LanguageController.getString("fpath"))
            path.isSelected = true
            FilelistController.option[2] = true
            path.setOnAction { event ->
                FilelistController.option[2] = !FilelistController.option[2]
                LogFileHandler.logger.config("change_path_option: " + !FilelistController.option[2] + " -> " + FilelistController.option[2])
                if (AppConfiguration.fileData.size > 0) {
                    FilelistPreviewTask()
                }
            }

            val size = CheckBox(LanguageController.getString("fsize"))
            size.isSelected = true
            FilelistController.option[3] = true
            size.setOnAction { event ->
                FilelistController.option[3] = !FilelistController.option[3]
                LogFileHandler.logger.config("change_filesize_option: " + !FilelistController.option[3] + " -> " + FilelistController.option[3])
                if (AppConfiguration.fileData.size > 0) {
                    FilelistPreviewTask()
                }
            }

            val created = CheckBox(LanguageController.getString("fcreation"))
            created.isSelected = false
            FilelistController.option[4] = false
            created.setOnAction { event ->
                FilelistController.option[4] = !FilelistController.option[4]
                LogFileHandler.logger.config("change_creationdate_option: " + !FilelistController.option[4] + " -> " + FilelistController.option[4])
                if (AppConfiguration.fileData.size > 0) {
                    FilelistPreviewTask()
                }
            }

            val changed = CheckBox(LanguageController.getString("fmodif"))
            changed.isSelected = true
            FilelistController.option[5] = true
            changed.setOnAction { event ->
                FilelistController.option[5] = !FilelistController.option[5]
                LogFileHandler.logger.config("change_lastchanged_option: " + !FilelistController.option[5] + " -> " + FilelistController.option[5])
                if (AppConfiguration.fileData.size > 0) {
                    FilelistPreviewTask()
                }
            }

            val lastAccess = CheckBox(LanguageController.getString("faccessed"))
            lastAccess.isSelected = false
            FilelistController.option[6] = false
            lastAccess.setOnAction { event ->
                FilelistController.option[6] = !FilelistController.option[6]
                LogFileHandler.logger.config("change_lastaccess_option: " + !FilelistController.option[6] + " -> " + FilelistController.option[6])
                if (AppConfiguration.fileData.size > 0) {
                    FilelistPreviewTask()
                }
            }

            val hidden = CheckBox(LanguageController.getString("fhidden"))
            hidden.isSelected = false
            FilelistController.option[7] = false
            hidden.setOnAction { event ->
                FilelistController.option[7] = !FilelistController.option[7]
                LogFileHandler.logger.config("change_hidden: " + !FilelistController.option[7] + " -> " + FilelistController.option[7])
                if (AppConfiguration.fileData.size > 0) {
                    FilelistPreviewTask()
                }
            }

            content.children.addAll(option2_desc, filename, type, path, size, created, changed, lastAccess, hidden)
            return content
        }

    override fun updateItem(item: FilelistSettings?, empty: Boolean) {
        super.updateItem(item, empty)

        if (!empty) {
            val vbox = VBox()
            graphic = vbox

            val labelHeader = Label(LanguageController.getString(item!!.title!!))
            labelHeader.graphicTextGap = 10.0
            labelHeader.id = "tableview-columnheader-default-bg"
            labelHeader.prefWidth = list!!.width - 40
            labelHeader.prefHeight = 30.0
            if (DebugHelper.DEBUGVERSION) {
                labelHeader.style = "-fx-background-color: gray;"
            }

            vbox.children.add(labelHeader)

            labelHeader.graphic = createArrowPath(30, !item.isHidden)

            val settingVBox = loadVBox(item.title!!)

            labelHeader.setOnMouseEntered { me ->
                labelHeader.style = "-fx-background-color: derive(-fx-base, 5%);"
                settingVBox!!.style = "-fx-background-color: derive(-fx-base, 5%);"
            }
            labelHeader.setOnMouseExited { me ->
                labelHeader.style = null
                settingVBox!!.style = null
            }

            if (!item.isHidden) {
                vbox.children.add(settingVBox)
            }

            labelHeader.setOnMouseClicked { me ->
                item.isHidden = !item.isHidden

                if (item.isHidden) {
                    when (getItem().title) {
                        "flSetting_0" -> {
                            labelHeader.graphic = createArrowPath(30, false)
                            for (i in 1 until vbox.children.size) {
                                vbox.children.removeAt(i)
                            }
                        }
                        "flSetting_1" -> {
                            labelHeader.graphic = createArrowPath(30, false)
                            for (i in 1 until vbox.children.size) {
                                vbox.children.removeAt(i)
                            }
                        }
                        "flSetting_2" -> {
                            labelHeader.graphic = createArrowPath(30, false)
                            for (i in 1 until vbox.children.size) {
                                vbox.children.removeAt(i)
                            }
                        }
                        "scSetting_0" -> {
                            labelHeader.graphic = createArrowPath(30, false)
                            for (i in 1 until vbox.children.size) {
                                vbox.children.removeAt(i)
                            }
                        }
                    }
                } else {
                    when (getItem().title) {
                        "flSetting_0" -> {
                            labelHeader.graphic = createArrowPath(30, true)
                            vbox.children.add(loadVBox("flSetting_0"))
                        }
                        "flSetting_1" -> {
                            labelHeader.graphic = createArrowPath(30, true)
                            vbox.children.add(loadVBox("flSetting_1"))
                        }
                        "flSetting_2" -> {
                            labelHeader.graphic = createArrowPath(30, true)
                            vbox.children.add(loadVBox("flSetting_2"))
                        }
                        "scSetting_0" -> {
                            labelHeader.graphic = createArrowPath(30, true)
                            vbox.children.add(loadVBox("scSetting_0"))
                        }
                    }
                }
            }
        } else {
            text = null
            graphic = null
        }
    }

    private fun loadVBox(name: String): VBox? {
        when (name) {
            "flSetting_0" -> return generalOptions
            "flSetting_1" -> return contentVBox1
            "flSetting_2" -> return contentVBox2
            else -> return null
        }
    }


    private fun createArrowPath(height: Int, up: Boolean): SVGPath {
        val svg = SVGPath()
        val width = height / 4
        if (up) {
            svg.content = "M" + width + " 0 L" + width * 2 + " " + width + " L0 " + width + " Z"
            svg.stroke = Paint.valueOf("white")
            svg.fill = Paint.valueOf("white")
        } else {
            svg.content = "M0 0 L" + width * 2 + " 0 L" + width + " " + width + " Z"
            svg.stroke = Paint.valueOf("white")
            svg.fill = Paint.valueOf("white")
        }
        return svg
    }

}
