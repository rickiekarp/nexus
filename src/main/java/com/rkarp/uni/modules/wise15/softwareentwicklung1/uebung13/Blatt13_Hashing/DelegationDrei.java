package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Blatt13_Hashing;

/**
 * Aufg 13.3.4
 */
public class DelegationDrei implements HashWertBerechner {

    public int hashWert(String wort) {
        int count = 0;
        char[] chars = wort.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            count += chars[i] * 11;
        }
        return count;
    }

    public String gibBeschreibung() {
        return null;
    }
}
