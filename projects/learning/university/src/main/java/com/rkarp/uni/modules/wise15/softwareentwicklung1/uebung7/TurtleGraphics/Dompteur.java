package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung7.TurtleGraphics;

 /**
 * Exemplare dieser Klasse veranlassen Turtles dazu,
 * Spuren auf einer Zeichenflaeche zu hinterlassen.
 *
 * @author Till Aust
 * @author Axel Schmolitzky
 * @version 26. November 2005
 */
 public class Dompteur
{
    /**
     * 'SE1' auf die Zeichenflaeche zeichnen.
     */
    public void start()
    {
        // Neue Turtle an Position (50, 100) erzeugen.
        Turtle turtle = new Turtle(50, 100);

        // 'S' zeichnen:
        turtle.geheVor(30);
        turtle.drehe(-90);
        turtle.geheVor(30);
        turtle.drehe(-90);
        turtle.geheVor(30);
        turtle.drehe(90);
        turtle.geheVor(30);
        turtle.drehe(90);
        turtle.geheVor(30);

        // Ohne Spur zum naechsten Buchstaben bewegen:
        turtle.hinterlasseKeineSpur();
        turtle.geheZu(130, 100);

        // 'E' zeichnen:
        turtle.hinterlasseSpur();
        turtle.drehe(-180);
        turtle.geheVor(30);
        turtle.drehe(90);
        turtle.geheVor(30);
        turtle.drehe(90);
        turtle.geheVor(30);
        turtle.drehe(-180);
        turtle.geheVor(30);
        turtle.drehe(90);
        turtle.geheVor(30);
        turtle.drehe(90);
        turtle.geheVor(30);

        // Ohne Spur zum naechsten Buchstaben bewegen:
        turtle.hinterlasseKeineSpur();
        turtle.geheZu(180, 100);

        // '1' zeichnen:
        turtle.hinterlasseSpur();
        turtle.setzeFarbe("rot");
        turtle.drehe(-90);
        turtle.geheVor(60);
        turtle.drehe(-120);
        turtle.geheVor(20);
    }

    /**
     * Aufg 7.3.1
     * 'n-Eck' auf die Zeichenflaeche zeichnen.
     */
    public void nEckZeichnen(int ecken, double kantenlaenge)
    {
        nEckZeichnen(ecken, kantenlaenge, 100, 100, 12);
    }

    /**
     * Aufg 7.3.2
     * 'n-Eck' auf die Zeichenflaeche zeichnen.
     */
    public void nEckZeichnen(int ecken, double kantenlaenge, int posX, int posY, int farbnummer)
    {
        // Neue Turtle an Position (posX, posY) erzeugen.
        Turtle turtle = new Turtle(posX, posY);
        turtle.setzeFarbe(farbnummer);

        double winkel = 360.0 / ecken;

        for (int i = 1; i <= ecken; i++)
        {
            System.out.println("i: " + i + " - laenge: " + kantenlaenge + " - winkel: " + winkel);
            turtle.geheVor(kantenlaenge);
            turtle.drehe(winkel);
        }
    }

    /**
     * Aufg 7.3.3
     * Zeichnet kleiner werdende, ineinander geschachtelte n-Ecke
     */
    public void nEckeVerschachtelt(int nEckAnzahl, int ecken, double kantenlaenge)
    {
        for (int i = 0; i < nEckAnzahl; i++)
        {
            nEckZeichnen(ecken, kantenlaenge);
            kantenlaenge -= kantenlaenge/nEckAnzahl;
        }
    }
}
