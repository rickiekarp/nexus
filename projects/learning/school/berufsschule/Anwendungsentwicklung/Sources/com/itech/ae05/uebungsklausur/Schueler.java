package com.itech.ae05.uebungsklausur;

public class Schueler extends Person {
    private int ausbildungsjahr;
    private String klasse;

    public Schueler() {
        super();
    }

    Schueler(String name, int alter, int jahr, String klasse) {
        super(name, alter);
        this.ausbildungsjahr = jahr;
        this.klasse = klasse;
    }

    String versetzung() {
        ausbildungsjahr++;
        return getName() + " ist jetzt im " + ausbildungsjahr + " Ausbildungsjahr";
    }

    @Override
    public String toString() {
        return "Schueler{" +
                "name='" + getName() + '\'' +
                ", alter='" + getAlter() + '\'' +
                ", ausbildungsjahr=" + ausbildungsjahr +
                ", klasse='" + klasse + '\'' +
                '}';
    }
}
