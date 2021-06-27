package com.itech.ae04.uebungsklausur.aufgabe3;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by rickie on 1/10/17.
 */
public class EinnahmenBerechnerTest {

    @Test
    public void testeEinnahmenBerechner() {
        EinnahmenBerechner berechner = new EinnahmenBerechner(new int[] {700, 800, 900, 1000, 1100, 1200});
        berechner.berechneEinnahmen();
        Assert.assertEquals(5680, berechner.getWochenEinnahme());
        Assert.assertEquals(20, berechner.getKaffeekasse());
    }
}
