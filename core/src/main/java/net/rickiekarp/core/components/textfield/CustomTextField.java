package net.rickiekarp.core.components.textfield;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * This class creates a basic TextField that can be configured in the following ways:
 * Restrict max text lenght, Restrict user input to certain characters e.g. [0-9]
 */
public class CustomTextField extends TextField {

    private IntegerProperty maxLength = new SimpleIntegerProperty(this, "maxLength", -1);
    private StringProperty restrict = new SimpleStringProperty(this, "restrict");

    public CustomTextField() {

        textProperty().addListener(new ChangeListener<String>() {

            private boolean ignore;

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
                if (ignore || s1 == null)
                    return;

                if (maxLength.get() > -1 && s1.length() > maxLength.get()) {
                    ignore = true;
                    setText(s1.substring(0, maxLength.get()));
                    ignore = false;
                }

                if (restrict.get() != null && !restrict.get().equals("") && !s1.matches(restrict.get() + "*")) {
                    ignore = true;
                    setText(s);
                    ignore = false;
                }
            }
        });
    }

    /**
     * Max TextField length property
     */
    public IntegerProperty maxLengthProperty() {
        return maxLength;
    }

    /**
     * Gets Max TextField length
     */
    public int getMaxLength() {
        return maxLength.get();
    }

    /**
     * Sets Max TextField length
     */
    public void setMaxLength(int maxLength) {
        this.maxLength.set(maxLength);
    }

    /**
     * Restrict property
     */
    public StringProperty restrictProperty() {
        return restrict;
    }

    /**
     * Gets the expression character class that restricts user input
     */
    public String getRestrict() {
        return restrict.get();
    }

    /**
     * Sets the expression character class that restricts user input
     * Example: [0-9] only allows numeric values
     */
    public void setRestrict(String restrict) {
        this.restrict.set(restrict);
    }
}