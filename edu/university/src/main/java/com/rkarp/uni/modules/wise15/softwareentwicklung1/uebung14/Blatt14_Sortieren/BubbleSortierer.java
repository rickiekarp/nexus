package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung14.Blatt14_Sortieren;

/**
 * Sortiert eine InPlaceSortierbareIntListe mit Bubblesort.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BubbleSortierer implements Sortierer
{
    /**
     * Sortiere die angegebene InPlaceSortierbareIntListe aufsteigend in situ.
     * @param liste die zu sortierende Liste
     */
    public void sortiere(InPlaceSortierbareIntListe liste)
    {
        for(int i=0; i < liste.gibLaenge() - 1; i++){

            for(int j=1; j < liste.gibLaenge() - i; j++){
                if(liste.gib(j-1) > liste.gib(j)){
                    liste.vertausche(j-1, j);
                }
            }
        }
    }
}
