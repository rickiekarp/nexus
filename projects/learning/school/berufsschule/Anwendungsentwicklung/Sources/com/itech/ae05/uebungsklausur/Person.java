package com.itech.ae05.uebungsklausur;

public abstract class Person {
    private String name;
    private int alter;

    Person() {

    }

    Person(String name, int alter) {
        this.name = name;
        this.alter = alter;
    }

    public void geburtstag() {
        this.alter++;
    }

     @Override
    public String toString() {
        return name;
     }

    public String getName() {
        return name;
    }

     public int getAlter() {
        return alter;
     }

     public void setName(String name) {
        this.name = name;
     }

     public void setAlter(int alter) {
        this.alter = alter;
     }
}