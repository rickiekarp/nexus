package com.itech.ae05.uebungsklausur;

public class Lehrer extends Person {
    private String fach;

    Lehrer() {

    }

    public Lehrer(String name, int alter, String fach) {
        super(name, alter);
        this.fach = fach;
    }

    public String getFach() {
        return fach;
    }

    void setFach(String fach) {
        this.fach = fach;
    }

    String pension() {
        if (getAlter() < 60) {
            return getName() + " muss noch 24 Jahre arbeiten";
        } else {
            return getName() + " geht in den verdienten Ruhestand";
        }
    }

    @Override
    public String toString() {
        return "Lehrer{" +
                "name='" + getName() + '\'' +
                ", alter='" + getAlter() + '\'' +
                ", fach='" + fach + '\'' +
                '}';
    }
}
