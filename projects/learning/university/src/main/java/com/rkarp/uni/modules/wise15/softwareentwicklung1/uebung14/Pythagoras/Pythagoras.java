package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung14.Pythagoras;

/**
 * Calculates Pythagorean theorem (a2=b2+c2)
 */
public class Pythagoras {

    public static double distance(float px, float py, float qx, float qy) {
        return Math.sqrt((px - qx) * (px - qx) + (py - qy) * (py - qy));
    }

}

/* Disassembled class file to show java virtual machine stack behaviour

Compiled from "Pythagoras.java"
public class com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung14.Pythagoras.Pythagoras {
public com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung14.Pythagoras.Pythagoras();
        Code:
        0: aload_0
        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
        4: return

public static double distance(float, float, float, float);
        Code:
        0: fload_0
        1: fload_2
        2: fsub
        3: fload_0
        4: fload_2
        5: fsub
        6: fmul
        7: fload_1
        8: fload_3
        9: fsub
        10: fload_1
        11: fload_3
        12: fsub
        13: fmul
        14: fadd
        15: f2d
        16: invokestatic  #2                  // Method java/lang/Math.sqrt:(D)D
        19: dreturn
        }
*/