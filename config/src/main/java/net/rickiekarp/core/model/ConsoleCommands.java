package net.rickiekarp.core.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * This is the model class for all ConsoleCommands
 */
public class ConsoleCommands {
    private final SimpleStringProperty command_name;
    private final SimpleStringProperty command_helper;
    private final SimpleStringProperty command_desc;
    private final Class<?> clz;

    public ConsoleCommands(String aCommandName, String aCommandHelper, String aCommandDesc, Class<?> clazz) {
        this.clz = clazz;
        this.command_name = new SimpleStringProperty(aCommandName);
        this.command_helper = new SimpleStringProperty(aCommandHelper);
        this.command_desc = new SimpleStringProperty(aCommandDesc);
    }

    public String getCommandName() {
        return command_name.get();
    }
    public String getCommandHelper() {
        return command_helper.get();
    }
    public String getCommandDesc() {
        return command_desc.get();
    }
    public Class<?> getMethod() {
        return clz;
    }
}
