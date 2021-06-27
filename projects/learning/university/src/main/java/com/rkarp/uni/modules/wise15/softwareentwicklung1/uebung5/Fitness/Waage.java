package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung5.Fitness;

/**
 * Write a description of class Waage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Waage
{
    // instance variables - replace the example below with your own
    private int _letztesGewicht;
    private int _gewichtDiff;
    private int minGewicht;
    private int maxGewicht;
    private boolean _trend;

    /**
     * Constructor for objects of class Waage
     * @param grammGewichtMessung Das gemessene Gewicht.
     */
    public Waage(int grammGewichtMessung)
    {
        // initialise instance variables
        _letztesGewicht = grammGewichtMessung;
        
        minGewicht = grammGewichtMessung;
        maxGewicht = grammGewichtMessung;
        
        registriere(grammGewichtMessung);
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void registriere(int neuesGewicht)
    {           
        // Vergleiche altes und neues Gewicht
        if (_letztesGewicht != neuesGewicht)
        {
            _trend = true;
            _gewichtDiff = neuesGewicht - _letztesGewicht;
            
            //definiere die extremen Messwerte (Aufg. 5.1.4)
            if (minGewicht > neuesGewicht)
            {
                minGewicht = neuesGewicht;
            }
            
            if (maxGewicht < neuesGewicht)
            {
                maxGewicht = neuesGewicht;
            }
            
            _letztesGewicht = neuesGewicht;
        }
        else
        {
            _trend = false;
            _gewichtDiff = 0;
        }
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public int gibTrend()
    {
        if (_trend)
        {
            if (_gewichtDiff < 0)
            {
                System.out.println("Leichter geworden nach der letzten Messung!");
                return -1;
            }
            else
            {
                System.out.println("Schwerer geworden nach der letzten Messung!");
                return 1;
            }
        }

        System.out.println("Keine Änderung nach der letzten Messung!");
        return 0;
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public int gibMinimalgewicht()
    {    
        return minGewicht;
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public int gibMaximalgewicht()
    {     
        return maxGewicht;
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public int gibDurchschnittsgewicht()
    {     
        return (maxGewicht + minGewicht) / 2;
    }
}
