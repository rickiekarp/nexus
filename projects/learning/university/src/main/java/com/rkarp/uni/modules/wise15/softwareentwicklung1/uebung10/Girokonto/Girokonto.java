package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung10.Girokonto;

import java.util.ArrayList;
import java.util.List;

/**
 * Ein einfaches Girokonto fuer ganzzahlige Betraege.
 * Es kann abgehoben und eingezahlt werden. Ein Girokonto kann
 * nur bis zu einem Dispo-Limit ueberzogen werden.
 * Dieses Dispo-Limit kann veraendert werden. Als Dispo-Limit
 * sind nur positive Werte erlaubt.
 * 
 * @author Martti Jeenicke
 * @author Axel Schmolitzky
 * @author Petra Becker-Pechau
 * @version WiSe 2013
 */
public class Girokonto
{
    // der ganzzahlige Saldo
    private int _saldo;

    // das Dispolimit
    private int _dispoLimit;

    // Aufg 10.2.1
    private List<Kontobewegung> kontoBewegungList;

    /**
     * Ein neues Konto hat einen Anfangssaldo und ein Dispo-Limit von 0.
     */
    public Girokonto()
    {
        _saldo = 0;
        _dispoLimit = 0;

        // Aufg 10.2.1
        kontoBewegungList = new ArrayList<>();
    }

    /**
     * Liefert das Dispo-Limit dieses Kontos.
     */
    public int gibDispoLimit()
    {
        return _dispoLimit;
    }

    /**
     * Setze das Dispo-Limit auf den gegebenen Wert.
     * 
     * @param dispoLimit ein nicht-negativer Wert als neues Dispo-Limit
     */
    public void setzeDispoLimit(int dispoLimit)
    {
        if (dispoLimit < 0)
        {
            throw new IllegalArgumentException("Fehler: das Limit muss positiv sein.");
        }
        _dispoLimit = dispoLimit;
    }

    /**
     * Zahlt einen Betrag auf das Konto ein.
     * 
     * @param betrag
     *            der einzuzahlende Betrag
     */
    public void zahleEin(int betrag)
    {
        if (betrag <= 0)
        {
            throw new IllegalArgumentException("Fehler: der Betrag muss positiv sein.");
        }
        _saldo = _saldo + betrag;

        // Aufg 10.2.1
        kontoBewegungList.add(new Kontobewegung(betrag));
    }

    /**
     * Hebt einen Betrag vom Konto ab.
     * 
     * @param betrag
     *            der abzuhebende Betrag
     */
    public void hebeAb(int betrag)
    {
        if (betrag <= 0)
        {
            throw new IllegalArgumentException("Fehler: der Betrag muss positiv sein.");
        }
        if ((_saldo - betrag) < -_dispoLimit)
        {
            throw new IllegalArgumentException("Fehler: Auszahlung nicht moeglich, Dispo-Limit wuerde ueberschritten.");
        }
        _saldo = _saldo - betrag;

        // Aufg 10.2.1
        kontoBewegungList.add(new Kontobewegung(betrag));
    }

    /**
     * Liefert den Saldo des Kontos.
     */
    public int gibSaldo()
    {
        return _saldo;
    }


    /**
     * Aufg 10.2.2
     * Liefert alle Kontobewegungen des Kontos.
     */
    public void druckeKontobewegungen()
    {
        if (kontoBewegungList.isEmpty())
        {
            System.out.println("Keine Kontobewegung gefunden!");
        }
        else
        {
            for (Kontobewegung k : kontoBewegungList)
            {
                //System.out.println(k.gibBetrag());
                System.out.println(k.gibDruckbareForm());
            }
        }
    }
}
