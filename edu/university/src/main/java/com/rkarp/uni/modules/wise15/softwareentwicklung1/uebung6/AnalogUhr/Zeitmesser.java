package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung6.AnalogUhr;

/**
 * Ein Zeitmesser misst die Zeit, die seit seiner Erzeugung vergangen ist.
 * 
 * @author Fredrik Winkler
 * @version 12. November 2015
 */
class Zeitmesser
{
    private final long _ersteMessung;
    private long _letzteMessung;
//    private long _sekSeitMitternacht;

    /**
     * Die Zeitmessung beginnt bei der Erzeugung.
     */
    public Zeitmesser()
    {
        _letzteMessung = _ersteMessung = System.nanoTime();
    }

    /**
     * Diese Methode sollte pro Animationsschritt nur 1x aufgerufen werden,
     * damit alle Klienten mit demselben Wert arbeiten!
     */
    public void notiereAktuelleZeit()
    {
//        //Aufg 6.4
//        //siehe: http://stackoverflow.com/a/4389560
//        Calendar c = Calendar.getInstance();
//        long now = c.getTimeInMillis();
//        c.set(Calendar.HOUR_OF_DAY, 0);
//        c.set(Calendar.MINUTE, 0);
//        c.set(Calendar.SECOND, 0);
//        c.set(Calendar.MILLISECOND, 0);
//        long passed = now - c.getTimeInMillis();
//        _sekSeitMitternacht = passed / 1000;
//
//        System.out.println("now: " + now + " passed: " + _sekSeitMitternacht + " - " + System.nanoTime());
//
//        _letzteMessung = _sekSeitMitternacht;
        _letzteMessung = System.nanoTime();
    }

    /**
     * Bestimmt, wie viel Zeit zwischen der Erzeugung des Zeitmessers und dem
     * letzten Aufruf von notiereAktuelleZeit vergangen ist.
     * 
     * @return die vergangene Zeit in Sekunden, mit Nanosekunden-Genauigkeit
     */
    public double abgelaufeneSekunden()
    {
        return (_letzteMessung - _ersteMessung) / 1e9;
    }
}
