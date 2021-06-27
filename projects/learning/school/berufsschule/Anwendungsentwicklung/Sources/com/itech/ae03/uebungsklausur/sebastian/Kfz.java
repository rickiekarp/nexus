package com.itech.ae03.uebungsklausur.sebastian;

import java.util.Calendar;

/**
 * Created by rickie on 12/15/16.
 */
public class Kfz {

    private String kennzeichen;
    private int baujahr;

    public Kfz() {

    }

    public Kfz(int mbaujahr, String mkennzeichen) {
        this.kennzeichen = mkennzeichen;
        this.baujahr = mbaujahr;
    }

    public String getKennzeichen() {
        return kennzeichen;
    }

    public void setKennzeichen(String kennzeichen) {
        this.kennzeichen = kennzeichen;
    }

    public int getBaujahr() {
        return baujahr;
    }

    public void setBaujahr(int baujahr) {
        this.baujahr = baujahr;
    }

    public String ueberpruefeBaujahr(int mBaujahr) {
        if (mBaujahr < Calendar.getInstance().get(Calendar.YEAR) - 30) {
            return "Auto ist ein Oldtimer";
        } else {
            return "Auto sit noch kein Oldtimer";
        }
    }


}
