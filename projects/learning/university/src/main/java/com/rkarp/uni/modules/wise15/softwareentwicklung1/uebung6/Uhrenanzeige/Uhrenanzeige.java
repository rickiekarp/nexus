package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung6.Uhrenanzeige;

/**
 * Write a description of class Uhrenanzeige here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Uhrenanzeige
{
    // instance variables - replace the example below with your own
    private Nummernanzeige stunde = new Nummernanzeige(60);
    private Nummernanzeige minute = new Nummernanzeige(60);
    private Nummernanzeige sekunde = new Nummernanzeige(60);

    /**
     * Constructor for objects of class Uhrenanzeige
     */
    public Uhrenanzeige()
    {
        // ignore
    }
    
    /**
     * Constructor for objects of class Uhrenanzeige
     */
    public Uhrenanzeige(int sek, int min, int std)
    {
        setzeUhrzeit(sek, min, std);
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public String gibUhrzeitAlsString()
    {
        // put your code here
        return stunde.gibAnzeigewert() + ":" + minute.gibAnzeigewert() + ":" + sekunde.gibAnzeigewert();
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void setzeUhrzeit(int std, int min, int sek)
    {
        // put your code here
        sekunde.setzeWert(std);
        minute.setzeWert(min);
        stunde.setzeWert(sek);
    }
    
        /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void taktsignalGeben()
    {
        minute.erhoehen();
    }
}
