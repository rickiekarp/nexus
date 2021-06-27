package com.itech.ae03.uebungsklausur.rickie;

/**
 * Created by rickie on 12/15/16.
 */
public class Main {

    public static void main(String[] args) {
        Kfz auto1 = new Kfz();
        auto1.setKennzeichen("Haha");
        auto1.setBaujahr(1200);

        Kfz auto2 = new Kfz("yxc", 1200);

        System.out.println(auto1.ueberpruefeBaujahr(1171));
        System.out.println(auto2.ueberpruefeBaujahr(1170));
    }
}
