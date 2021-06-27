package com.itech.ae04.uebungsklausur.aufgabe6;

public class Arrays {
    public static void main(String[] args) {
        int[][][] meinArray3D = new int[2][2][2];
        meinArray3D[0][0][0] = 1;
        meinArray3D[0][1][1] = 2;
        meinArray3D[0][1][0] = 2;
        meinArray3D[0][0][1] = 1;

        meinArray3D[1][0][0] = 3;
        meinArray3D[1][1][1] = 4;
        meinArray3D[1][1][0] = 4;
        meinArray3D[1][0][1] = 3;

        System.out.println(meinArray3D[0][0][0] + meinArray3D[0][0][1]);
        System.out.println(meinArray3D[0][1][0] + meinArray3D[0][1][1]);
        System.out.println(meinArray3D[1][0][0] + meinArray3D[1][0][1]);
        System.out.println(meinArray3D[1][1][0] + meinArray3D[1][1][1]);
    }
}
