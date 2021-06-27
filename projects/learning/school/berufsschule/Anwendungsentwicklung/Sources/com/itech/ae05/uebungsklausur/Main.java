package com.itech.ae05.uebungsklausur;

public class Main {

    public static void main(String[] args) {
        Lehrer lehrer = new Lehrer();
        lehrer.setName("Renfordt");
        lehrer.setAlter(59);
        lehrer.setFach("AE");

        Schueler schueler = new Schueler("Meier", 21, 1, "FIAE");

        System.out.println(lehrer);
        System.out.println(schueler);

        lehrer.geburtstag();
        schueler.geburtstag();

        System.out.println(lehrer.pension());
        System.out.println(schueler.versetzung());
    }

}
