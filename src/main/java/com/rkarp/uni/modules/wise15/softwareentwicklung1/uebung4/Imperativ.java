package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung4;

/**
 * Created by rickie on 11/23/15.
 */

//Wurzel ziehen
public class Imperativ
{
    public double average(double a, double b)
    {
        double sum = a + b;
        return sum / 2;
    }

    public double root(double x)
    {
        //für große Zahlen ungeeignet, da bereits eine Schätzung der Wurzel gemacht wurde.
        //double g = 1.0; //Schätzung der Wurzel

        //für alle Zahlen geeignet
        double g = guessRoot(x);

        g = average(g, x/g);
        g = average(g, x/g);
        g = average(g, x/g);
        g = average(g, x/g);
        g = average(g, x/g);

        return g;
    }

    public float guessRoot(float x)
    {
        int a = Float.floatToIntBits(x);
        int b = a + (127 << 23);
        int c = b >>> 1;
        float d = Float.intBitsToFloat(c);
        return d;
    }

    public double guessRoot(double x)
    {
        long a = Double.doubleToLongBits(x);
        long b = a + (1023L << 52);
        long c = b >>> 1;
        double d = Double.longBitsToDouble(c);
        return d;
    }
}