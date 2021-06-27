package com.itech.ae04.uebungsklausur.aufgabe4;

/**
 * Created by rickie on 1/11/17.
 */
public class Mahnprogramm {
    private boolean bezahlt = false;
    private int fm;
    private int am;
    private int fj;
    private int aj;

    public Mahnprogramm(int fm, int am, int fj, int aj) {
        this.fm = fm;
        this.am = am;
        this.fj = fj;
        this.aj = aj;
    }

    public void berechneOriginal() {
        if (bezahlt) {

        } else {
            if (fm < am) {
                System.out.println("1");
            } else {
                if (fj <= aj) {
                    System.out.println("2");
                }
            }
        }
    }

    public void berechneNeu() {
        if (bezahlt) {

        } else {
            if (fj <= aj) {
                if (fm < am) {
                    System.out.println("1");
                }
            }
        }
    }
}
