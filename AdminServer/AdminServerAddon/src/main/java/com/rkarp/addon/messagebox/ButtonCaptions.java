package com.rkarp.addon.messagebox;

import java.util.ListResourceBundle;

public class ButtonCaptions extends ListResourceBundle {

    /**
     * See {@link java.util.ListResourceBundle#getContents()}
     */
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {ButtonType.OK.name(), "OK"},
                {ButtonType.ABORT.name(), "Abort"},
                {ButtonType.CANCEL.name(), "Cancel"},
                {ButtonType.YES.name(), "Yes"},
                {ButtonType.NO.name(), "No"},
                {ButtonType.CLOSE.name(), "Close"},
                {ButtonType.SAVE.name(), "Save"},
                {ButtonType.RETRY.name(), "Retry"},
                {ButtonType.IGNORE.name(), "Ignore"},
                {ButtonType.HELP.name(), "Help"},
        };
    }

}
