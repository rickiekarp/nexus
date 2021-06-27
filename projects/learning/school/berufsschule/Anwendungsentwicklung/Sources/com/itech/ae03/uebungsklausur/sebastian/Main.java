package com.itech.ae03.uebungsklausur.sebastian;

/**
 * Created by sebastian on 15.12.16.
 */
public class Main {

    public static void main(String[] args) {

         Kfz golf = new Kfz();
         golf.setBaujahr(1990);
         golf.setKennzeichen("HH BS14");

         Kfz notGolf = new Kfz(1910, "nichtHH BS14");

        System.out.println(golf.ueberpruefeBaujahr(golf.getBaujahr()));
        System.out.println(notGolf.ueberpruefeBaujahr(notGolf.getBaujahr()));
    }
}
