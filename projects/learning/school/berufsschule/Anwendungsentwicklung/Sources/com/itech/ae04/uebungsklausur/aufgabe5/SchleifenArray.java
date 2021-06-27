package com.itech.ae04.uebungsklausur.aufgabe5;

/**
 * Created by rickie on 1/11/17.
 */
public class SchleifenArray {
    private int[] array;
    private int highestNumber;

    public SchleifenArray(int[] array) {
        this.array = array;
    }

    public void printHighestNumber() {
        for (int number : array) {
            if (highestNumber < number) {
                highestNumber = number;
            }
        }
        System.out.println("Highest number: " + highestNumber);
    }
}
