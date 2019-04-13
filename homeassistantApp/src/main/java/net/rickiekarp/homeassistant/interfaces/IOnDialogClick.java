package net.rickiekarp.homeassistant.interfaces;

/**
 * Created by sebastian on 05.12.17.
 */

public interface IOnDialogClick {

    void onPositiveClick(String title, String type);
    void onNegativeClick(int id);
}
