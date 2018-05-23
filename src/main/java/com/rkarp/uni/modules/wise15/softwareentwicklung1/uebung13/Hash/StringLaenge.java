package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Hash;

public class StringLaenge implements HashFunktion {

    public int f(Object x) {
        if (x instanceof String) {
            String s = (String) x;
            return s.length();
        } else {
            return x.hashCode();
        }
    }
}
