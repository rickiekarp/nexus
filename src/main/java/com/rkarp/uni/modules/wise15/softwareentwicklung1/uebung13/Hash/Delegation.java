package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Hash;

public class Delegation implements HashFunktion {

    public int f(Object x){
        return x.hashCode();
    }
}
