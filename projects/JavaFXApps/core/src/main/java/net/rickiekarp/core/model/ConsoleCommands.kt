package net.rickiekarp.core.model

import javafx.beans.property.SimpleStringProperty

/**
 * This is the model class for all ConsoleCommands
 */
class ConsoleCommands(aCommandName: String, aCommandHelper: String, aCommandDesc: String, val method: Class<*>) {
    private val command_name: SimpleStringProperty = SimpleStringProperty(aCommandName)
    private val command_helper: SimpleStringProperty = SimpleStringProperty(aCommandHelper)
    private val command_desc: SimpleStringProperty = SimpleStringProperty(aCommandDesc)

    val commandName: String
        get() = command_name.get()
    val commandHelper: String
        get() = command_helper.get()
    val commandDesc: String
        get() = command_desc.get()

}
