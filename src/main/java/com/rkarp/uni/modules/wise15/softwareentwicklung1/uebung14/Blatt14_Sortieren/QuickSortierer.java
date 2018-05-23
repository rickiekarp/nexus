package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung14.Blatt14_Sortieren;

/**
 * Sortiert eine InPlaceSortierbareIntListe mit Quicksort.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class QuickSortierer implements Sortierer
{
    /**
     * Sortiere die angegebene InPlaceSortierbareIntListe aufsteigend in situ.
     * 
     * @param liste die zu sortierende Liste
     */
    public void sortiere(InPlaceSortierbareIntListe liste)
    {
        sortiere(liste, 0, liste.gibLaenge() - 1);
    }
    
    /**
     * Sortiere den Ausschnitt der InPlaceSortierbareIntListe aufsteigend in situ.
     * 
     * @param liste die zu sortierende Liste
     * @param von der Anfang des Ausschnitts
     * @param bis das Ende des Ausschnitts
     */
    private void sortiere(InPlaceSortierbareIntListe liste, final int von, final int bis)
    {
        int i = von;
        int j = bis;

        // calculate pivot number, I am taking pivot as middle index number
        int pivot = von+(bis-von)/2;

        // Divide into two arrays
        while (i <= j) {
            /**
             * In each iteration, we will identify a number from left side which
             * is greater then the pivot value, and also we will identify a number
             * from right side which is less then the pivot value. Once the search
             * is done, then we exchange both numbers.
             */
            while (liste.gib(i) < pivot) {
                i++;
            }
            while (liste.gib(j) > pivot) {
                j--;
            }
            if (i <= j) {
                liste.vertausche(i, j);
                //move index to next position on both sides
                i++;
                j--;
            }
        }

        // call sortiere() method recursively
        if (von < j)
            sortiere(liste, von, j);
        if (i < bis)
            sortiere(liste, i, bis);
    }
}
