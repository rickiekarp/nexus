package com.itech.ae04.uebungsklausur.aufgabe5;

import org.junit.Test;

/**
 * Created by rickie on 1/11/17.
 */
public class SchleifenArrayTest {

    @Test
    public void testHighestNumber() {
        SchleifenArray array = new SchleifenArray(new int[] {1,2,7,2,5,10});
        array.printHighestNumber();
    }
}
