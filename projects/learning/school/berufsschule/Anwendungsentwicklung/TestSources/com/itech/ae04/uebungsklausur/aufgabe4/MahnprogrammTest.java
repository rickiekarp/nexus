package com.itech.ae04.uebungsklausur.aufgabe4;

import org.junit.Test;

/**
 * Created by rickie on 1/10/17.
 */
public class MahnprogrammTest {

    @Test
    public void testeEinnahmenBerechner() {
        Mahnprogramm programm = new Mahnprogramm(3,4,2011, 2010);
        programm.berechneOriginal();
        programm.berechneNeu();
    }
}
