package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung3.Zeichnung;

/**
 * Exemplare dieser Klasse zeichnen eine einfache Zeichnung.
 * Um die Zeichnung auf dem Bildschirm anzuzeigen, muss die
 * zeichneSimpel-Methode an einem Exemplar aufgerufen werden.
 *
 * Diese Klasse ist als fruehes Java-Lehrbeispiel mit BlueJ gedacht.
 * 
 * @author Petra Becker-Pechau
 */
class Zeichner
{
    /**
     * Zeichnet das Haus mit einer gelben Wand.
     */
    public void zeichneSimpel()
    {
        Kreis sonne;
        Quadrat wand;
        Quadrat fenster;
        Dreieck dach;

        sonne = new Kreis();
        sonne.sichtbarMachen();
        sonne.farbeAendern("rot");
        sonne.horizontalBewegen(180);
        sonne.vertikalBewegen(-10);
        sonne.groesseAendern(60);

        wand = new Quadrat();
        wand.sichtbarMachen();
        wand.farbeAendern("gelb");
        wand.vertikalBewegen(80);
        wand.groesseAendern(100);
        
        fenster = new Quadrat();
        fenster.sichtbarMachen();
        fenster.farbeAendern("blau");
        fenster.horizontalBewegen(20);
        fenster.vertikalBewegen(100);

        dach = new Dreieck();  
        dach.sichtbarMachen();
        dach.groesseAendern(50, 140);
        dach.horizontalBewegen(60);
        dach.vertikalBewegen(70);
    }



    /**
     * Aufgabe 3.2 & 3.3
     * Zeichnet das Haus mit einer gelben Wand.
     */
    public void zeichneSimpelNeu()
    {
        Kreis sonne;

        sonne = new Kreis();
        sonne.sichtbarMachen();
        sonne.farbeAendern("rot");
        sonne.horizontalBewegen(250);
        sonne.vertikalBewegen(-40);
        sonne.groesseAendern(30);

        for (int i = 0; i < 4; i++)
        {
            zeichneHaus(i*40);
        }

        for (int i = 0; i < 15; i++)
        {
            zeichneStraße(i*30);
        }
    }

    /**
     * Aufgabe 3.2 & 3.3
     * Zeichnet ein Haus.
     * @param entfernung Die Entfernung des Hauses von der Ausgangsposition
     */
    public void zeichneHaus(int entfernung)
    {
        Quadrat wand;
        Quadrat fenster;
        Dreieck dach;

        wand = new Quadrat();
        wand.sichtbarMachen();
        wand.farbeAendern("gelb");
        wand.horizontalBewegen(0 + entfernung);
        wand.vertikalBewegen(80);
        wand.groesseAendern(100);

        fenster = new Quadrat();
        fenster.sichtbarMachen();
        fenster.farbeAendern("blau");
        fenster.horizontalBewegen(20 + entfernung);
        fenster.vertikalBewegen(100);

        dach = new Dreieck();
        dach.sichtbarMachen();
        dach.groesseAendern(50, 140);
        dach.horizontalBewegen(60 + entfernung);
        dach.vertikalBewegen(70);
    }

    /**
     * Aufgabe 3.2 & 3.3
     * Zeichnet eine Straße.
     * @param entfernung Die Entfernung des Hauses von der Ausgangsposition
     */
    public void zeichneStraße(int entfernung)
    {
        Quadrat wand;
        Quadrat fenster;

        fenster = new Quadrat();
        fenster.sichtbarMachen();
        fenster.farbeAendern("rot");
        fenster.horizontalBewegen(-60 + entfernung);
        fenster.vertikalBewegen(200);

        wand = new Quadrat();
        wand.sichtbarMachen();
        wand.farbeAendern("schwarz");
        wand.horizontalBewegen(-55 + entfernung);
        wand.vertikalBewegen(210);
        wand.groesseAendern(10);
    }
    
    
    
    
    
    
    
    
    /**
     * Zeichnet das Haus mit einer Wandfarbe, die der Anwender bestimmt.
     * 
     * @param wandfarbe die Farbe der Wand, z.B. "gelb" oder "gruen"
     */
    public void zeichneStrukturiert(String wandfarbe)
    {
        // Fuer das Zeichnen von Sonne und Wand gibt es bereits Hilfsmethoden :-)
        zeichneSonne();
        zeichneWand(wandfarbe);
        
        // Fuer das Zeichnen von Fenster und Dach bisher noch nicht :-(
        Quadrat fenster = new Quadrat();
        fenster.sichtbarMachen();
        fenster.farbeAendern("blau");
        fenster.horizontalBewegen(20);
        fenster.vertikalBewegen(100);

        Dreieck dach = new Dreieck();  
        dach.sichtbarMachen();
        dach.groesseAendern(50, 140);
        dach.horizontalBewegen(60);
        dach.vertikalBewegen(70);
    }

    private void zeichneSonne()
    {
        Kreis sonne = new Kreis();
        sonne.sichtbarMachen();
        sonne.farbeAendern("rot");
        sonne.horizontalBewegen(180);
        sonne.vertikalBewegen(-10);
        sonne.groesseAendern(60);
    }
    
    private void zeichneWand(String farbe)
    {
        Quadrat wand = new Quadrat();
        wand.sichtbarMachen();
        wand.farbeAendern(farbe);
        wand.vertikalBewegen(80);
        wand.groesseAendern(100);
    }
}
