package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung11.Elf;

import java.lang.reflect.Field;

/**
 * Ausgabe der Felder einer übergebenen Klasse.
 */
public class Mirror {
    public static void printFields(Object x) {
        System.out.println(x.getClass() + " has the following fields:");
        Field[] fields = x.getClass().getFields();
        for (Field field : fields) {
            System.out.println(field);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Wire wire = new Wire();
        printFields(wire);
        printFields(args);
    }
}
