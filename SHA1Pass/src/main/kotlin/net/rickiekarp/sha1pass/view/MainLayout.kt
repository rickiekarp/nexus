package net.rickiekarp.sha1pass.view

import net.rickiekarp.sha1pass.settings.AppConfiguration
import javafx.geometry.HPos
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import net.rickiekarp.core.AppContext
import net.rickiekarp.core.components.textfield.CustomTextField
import net.rickiekarp.core.components.textfield.CustomTextFieldSkin
import net.rickiekarp.core.controller.LanguageController
import net.rickiekarp.core.debug.DebugHelper
import net.rickiekarp.core.util.crypt.*
import net.rickiekarp.core.view.AboutScene
import net.rickiekarp.core.view.layout.AppLayout

class MainLayout : AppLayout {
    private var isSecure = false
    private var hmac = false
    private var complex = true

    private var wordBox: HBox? = null
    private var color: Rectangle? = null
    private var sentence_tf_mask: CustomTextField? = null
    private var sentence_tf: CustomTextField? = null
    private var word_tf: CustomTextField? = null
    private var peek_tf: TextField? = null

    private var colorPos = -1

    private//padding top, right, bottom, left
    //padding top, right, bottom, left
    //padding top, right, bottom, left
    //padding top, right, bottom, left
    //set Layout
    //row 1
    //row 2
    //peek_tf.setHighlighter(null);
    //row 3
    // Bind TextField properties to viewMode CheckBox
    // Bind TextField and masked TextField text values bidirectionally
    val mainLayout: Node
        get() {
            val mainContent = BorderPane()

            val mainGrid = GridPane()
            mainGrid.padding = Insets(5.0, 3.0, 0.0, 3.0)

            wordBox = HBox()
            wordBox!!.padding = Insets(0.0, 0.0, 0.0, 10.0)
            wordBox!!.spacing = 5.0
            wordBox!!.alignment = Pos.CENTER_LEFT

            val encryptBtns = HBox()
            encryptBtns.padding = Insets(0.0, 0.0, 0.0, 0.0)
            encryptBtns.spacing = 7.0
            encryptBtns.alignment = Pos.CENTER_LEFT

            val controls = HBox()
            controls.padding = Insets(3.0, 3.0, 3.0, 7.0)
            controls.spacing = 2.0

            val status = Label(AppContext.getContext().applicationName)
            status.style = "-fx-font-size: 9pt;"
            controls.children.add(status)
            val column1 = ColumnConstraints()
            column1.percentWidth = 16.0
            val column2 = ColumnConstraints()
            column2.percentWidth = 14.0
            val column3 = ColumnConstraints()
            column3.percentWidth = 16.0
            val column4 = ColumnConstraints()
            column4.percentWidth = 17.0
            val column5 = ColumnConstraints()
            column5.percentWidth = 20.0
            val column6 = ColumnConstraints()
            column6.percentWidth = 14.0
            val column7 = ColumnConstraints()
            column7.percentWidth = 11.0
            mainGrid.columnConstraints.addAll(column1, column2, column3, column4, column5, column6, column7)

            mainGrid.hgap = 5.0
            mainGrid.vgap = 5.0
            val sentence_label = Label(LanguageController.getString("u_sentence"))
            sentence_label.style = "-fx-font-size: 10pt;"
            GridPane.setConstraints(sentence_label, 0, 0)
            mainGrid.children.add(sentence_label)
            sentence_label.tooltip = Tooltip(LanguageController.getString("type_sentence_tip"))


            sentence_tf_mask = CustomTextField()
            sentence_tf_mask!!.tooltip = Tooltip(LanguageController.getString("type_sentence_tip"))
            sentence_tf_mask!!.skin = CustomTextFieldSkin(sentence_tf_mask)
            GridPane.setConstraints(sentence_tf_mask, 1, 0)
            GridPane.setColumnSpan(sentence_tf_mask, 3)
            mainGrid.children.add(sentence_tf_mask)

            sentence_tf = CustomTextField()
            sentence_tf!!.tooltip = Tooltip(LanguageController.getString("type_sentence_tip"))
            GridPane.setConstraints(sentence_tf, 1, 0)
            GridPane.setColumnSpan(sentence_tf, 3)
            sentence_tf!!.isManaged = false
            sentence_tf!!.isVisible = false
            mainGrid.children.add(sentence_tf)

            val word_label = Label(LanguageController.getString("u_word"))
            word_label.style = "-fx-font-size: 10pt;"
            word_label.tooltip = Tooltip(LanguageController.getString("type_word_tip"))
            word_label.prefWidth = 35.0
            wordBox!!.children.add(word_label)

            val word_tf_mask = CustomTextField()
            word_tf_mask.tooltip = Tooltip(LanguageController.getString("type_word_tip"))
            word_tf_mask.prefWidth = 85.0
            word_tf_mask.skin = CustomTextFieldSkin(word_tf_mask)
            wordBox!!.children.add(word_tf_mask)

            word_tf = CustomTextField()
            word_tf!!.tooltip = Tooltip(LanguageController.getString("type_word_tip"))
            word_tf!!.prefWidth = 85.0
            word_tf!!.isManaged = false
            word_tf!!.isVisible = false

            val helpBtn = Button(LanguageController.getString("help_label"))
            helpBtn.style = "-fx-font-size: 9pt;"
            helpBtn.tooltip = Tooltip(LanguageController.getString("help_tip") + " " + AppContext.getContext().applicationName)
            GridPane.setConstraints(helpBtn, 6, 0)
            GridPane.setHalignment(helpBtn, HPos.CENTER)
            mainGrid.children.add(helpBtn)
            val viewMode = CheckBox(LanguageController.getString("vs"))
            viewMode.style = "-fx-font-size: 9pt;"
            viewMode.tooltip = Tooltip(LanguageController.getString("vs_tip"))
            GridPane.setConstraints(viewMode, 1, 1)
            mainGrid.children.add(viewMode)

            val secureMode = CheckBox(LanguageController.getString("sm"))
            secureMode.style = "-fx-font-size: 8pt;"
            secureMode.tooltip = Tooltip(LanguageController.getString("sm_tip"))
            GridPane.setConstraints(secureMode, 2, 1)
            mainGrid.children.add(secureMode)

            val hmacMode = CheckBox(LanguageController.getString("hm"))
            hmacMode.style = "-fx-font-size: 9pt;"
            hmacMode.tooltip = Tooltip(LanguageController.getString("hmac_tip"))
            hmacMode.isSelected = hmac
            GridPane.setConstraints(hmacMode, 3, 1)
            mainGrid.children.add(hmacMode)

            val complexMode = CheckBox(LanguageController.getString("comp"))
            complexMode.style = "-fx-font-size: 9pt;"
            complexMode.tooltip = Tooltip(LanguageController.getString("comp_tip"))
            complexMode.isSelected = complex
            GridPane.setConstraints(complexMode, 4, 1)
            mainGrid.children.add(complexMode)

            peek_tf = TextField(LanguageController.getString("pass_peek"))
            peek_tf!!.style = "-fx-font-size: 10pt;"
            peek_tf!!.isEditable = false
            peek_tf!!.tooltip = Tooltip(LanguageController.getString("pass_peek_tip"))
            GridPane.setConstraints(peek_tf, 5, 1)
            peek_tf!!.prefWidth = 30.0
            mainGrid.children.add(peek_tf)

            val colorBtn = Button(LanguageController.getString("color_label"))
            colorBtn.style = "-fx-font-size: 9pt;"
            colorBtn.tooltip = Tooltip(LanguageController.getString("color_tip"))
            GridPane.setConstraints(colorBtn, 6, 1)
            GridPane.setHalignment(colorBtn, HPos.CENTER)
            mainGrid.children.add(colorBtn)
            val hexBtn = Button(LanguageController.getString("hex_label"))
            hexBtn.style = "-fx-font-size: 10pt;"
            hexBtn.tooltip = Tooltip(LanguageController.getString("a_40_char_tip"))
            hexBtn.minWidth = 103.0
            encryptBtns.children.add(hexBtn)

            val b64Btn = Button(LanguageController.getString("b64_label"))
            b64Btn.style = "-fx-font-size: 10pt;"
            b64Btn.tooltip = Tooltip(LanguageController.getString("a_28_char_tip"))
            b64Btn.minWidth = 103.0
            encryptBtns.children.add(b64Btn)

            val bcryptBtn = Button(LanguageController.getString("bcrypt_label"))
            bcryptBtn.style = "-fx-font-size: 10pt;"
            bcryptBtn.tooltip = Tooltip(LanguageController.getString("a_60_char_tip"))
            bcryptBtn.minWidth = 103.0
            encryptBtns.children.add(bcryptBtn)

            color = Rectangle(30.0, 30.0)
            color!!.isVisible = false
            color!!.fill = Color.TRANSPARENT
            GridPane.setHalignment(color, HPos.CENTER)
            GridPane.setConstraints(color, 6, 2)
            mainGrid.children.add(color)

            mainGrid.children.add(wordBox)
            GridPane.setConstraints(wordBox, 4, 0)
            GridPane.setColumnSpan(wordBox, 2)

            mainGrid.children.add(encryptBtns)
            GridPane.setConstraints(encryptBtns, 1, 2)
            GridPane.setColumnSpan(encryptBtns, 5)


            mainContent.center = mainGrid
            mainContent.bottom = controls

            helpBtn.setOnAction { e -> AboutScene() }

            colorBtn.setOnAction { actionEvent ->
                color!!.isVisible = true
                colorRotate()
            }

            hexBtn.setOnAction { actionEvent ->
                calcHex()
                status.text = LanguageController.getString("hex_password_copied")
            }

            b64Btn.setOnAction { actionEvent ->
                calcBase64()
                status.text = LanguageController.getString("b64_password_copied")
            }

            bcryptBtn.setOnAction { actionEvent ->
                calcBCrypt()
                status.text = LanguageController.getString("bcrypt_password_copied")
            }

            viewMode.selectedProperty().addListener { ov, old_val, new_val ->
                if (new_val!!) {
                    wordBox!!.children.remove(word_tf_mask)
                    wordBox!!.children.add(word_tf)
                    status.text = LanguageController.getString("vs_on")
                } else {
                    wordBox!!.children.remove(word_tf)
                    wordBox!!.children.add(word_tf_mask)
                    status.text = LanguageController.getString("vs_off")
                }
            }
            sentence_tf!!.managedProperty().bind(viewMode.selectedProperty())
            sentence_tf!!.visibleProperty().bind(viewMode.selectedProperty())
            word_tf!!.managedProperty().bind(viewMode.selectedProperty())
            word_tf!!.visibleProperty().bind(viewMode.selectedProperty())

            sentence_tf_mask!!.managedProperty().bind(viewMode.selectedProperty().not())
            word_tf_mask.visibleProperty().bind(viewMode.selectedProperty().not())
            sentence_tf!!.textProperty().bindBidirectional(sentence_tf_mask!!.textProperty())
            word_tf!!.textProperty().bindBidirectional(word_tf_mask.textProperty())

            secureMode.selectedProperty().addListener { ov, old_val, new_val ->
                if (new_val!!) {
                    isSecure = true
                    viewMode.isDisable = true
                    viewMode.isSelected = false
                    colorBtn.isDisable = true
                    status.text = LanguageController.getString("sm_on")
                } else {
                    isSecure = false
                    viewMode.isDisable = false
                    sentence_tf!!.text = ""
                    word_tf!!.text = ""
                    peek_tf!!.text = "Peek"
                    colorBtn.isDisable = false
                    setStringToClipboard("")
                    status.text = LanguageController.getString("sm_off")
                }
            }

            hmacMode.selectedProperty().addListener { ov, old_val, new_val ->
                if (new_val!!) {
                    hmac = true
                    status.text = LanguageController.getString("hmac_on")
                } else {
                    hmac = false
                    status.text = LanguageController.getString("hmac_off")
                }
            }

            complexMode.selectedProperty().addListener { ov, old_val, new_val ->
                if (new_val!!) {
                    complex = true
                    status.text = LanguageController.getString("comp_on")
                } else {
                    complex = false
                    status.text = LanguageController.getString("comp_off")
                }
            }

            if (DebugHelper.DEBUGVERSION) {
                mainGrid.isGridLinesVisible = true
                mainGrid.style = "-fx-background-color: gray"
                encryptBtns.style = "-fx-background-color: #A36699;"
                controls.style = "-fx-background-color: #336699;"
            }

            return mainContent
        }

    private fun calcHex() {
        val data = checkInputData()
        val sha1 = SHA1Coder.getSHA1Bytes(data, hmac)

        val hash = HexCoder.bytesToHex(sha1)
        if (complex) {
            applyComplex(hash)
        } else {
            copyPassword(hash)
        }
    }

    private fun calcBase64() {
        val data = checkInputData()
        val sha1 = SHA1Coder.getSHA1Bytes(data, hmac)

        val hash = String(Base64Coder.encode(sha1))
        if (complex) {
            applyComplex(hash)
        } else {
            copyPassword(hash)
        }
    }

    private fun calcBCrypt() {
        val data = checkInputData()
        val sha1 = SHA1Coder.getSHA1Bytes(data, hmac)
        val salt = "$2a$10$" + SHA1Coder.getSHA1String(sha1)
        val hash = BCryptCoder.hashpw(data, salt)

        if (complex) {
            applyComplex(hash)
        } else {
            copyPassword(hash)
        }
    }

    private fun applyComplex(data: String) {
        //copy encoded string to clipboard and set the peek TextField text
        setStringToClipboard(data + AppConfiguration.comp_string)
        peekText(data + AppConfiguration.comp_string)
    }

    private fun copyPassword(hashedPass: String) {
        //copy encoded string to clipboard and set the peek TextField text
        setStringToClipboard(hashedPass)
        peekText(hashedPass)
    }

    /**
     * if a color is selected, the hex color code is added to the entered data string
     * @return to be encrypted user input
     */
    private fun checkInputData(): String {
        return if (color!!.fill == Color.TRANSPARENT) {
            sentence_tf!!.text + word_tf!!.text
        } else {
            //System.out.println(sentence_tf.getText() + word_tf.getText() + ColorCoder.colorArray[colorPos].toString());
            sentence_tf!!.text + word_tf!!.text + ColorCoder.colorArray[colorPos].toString()
        }
    }

    /**
     * Changes the color field
     */
    private fun colorRotate() {
        try {
            colorPos++
            color!!.fill = ColorCoder.colorArray[colorPos]
        } catch (e: ArrayIndexOutOfBoundsException) {
            color!!.fill = Color.TRANSPARENT
            colorPos = -1
        }

    }

    /**
     * Shows the first part of the encrypted password in a text field
     * @param s Text to show in the text field
     */
    private fun peekText(s: String) {
        if (isSecure) {
            peek_tf!!.text = "Peek"
        } else {
            peek_tf!!.text = s.substring(0, 4)
        }
    }

    /**
     * Copies the given string to the clipboard
     * @param stringContent String to copy
     */
    private fun setStringToClipboard(stringContent: String) {
        val content = ClipboardContent()
        content.putString(stringContent)
        Clipboard.getSystemClipboard().setContent(content)
    }

    override fun getLayout(): Node {
        return mainLayout
    }

    override fun postInit() {
        sentence_tf_mask!!.requestFocus()
    }
}
