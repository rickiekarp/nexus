package com.itech.ae03.uebungsklausur.rickie;

public class Kfz {
    private String kennzeichen;
    private int baujahr;

    public Kfz() {
        kennzeichen = "dfjgj";
        baujahr = 1234;
    }

    public Kfz(String kennzeichen, int baujahr) {
        this.kennzeichen = kennzeichen;
        this.baujahr = baujahr;
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

    public String ueberpruefeBaujahr(int baujahr) {
        if (this.baujahr - 30 < baujahr) {
            return "Auto ist ein Oldtimer";
        } else {
            return "Auto sit noch kein Oldtimer";
        }
    }

    @Override
    public String toString() {
        return kennzeichen;
    }
}
