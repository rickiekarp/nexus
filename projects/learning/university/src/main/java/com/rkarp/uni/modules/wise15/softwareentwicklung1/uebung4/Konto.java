package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung4;

/**
 * Created by rickie on 11/23/15.
 */

class Konto
{

    // Wir speichern den Saldo in Eurocent.
    private int _saldo;

    /**
     * Ein neues Konto wird mit 10 EUR Startsaldo erstellt.
     **/
    public Konto()
    {
        _saldo = 1000;
    }

    /**
     * Zahlt einen Betrag ein.
     * Der Betrag darf nicht negativ sein.
     **/
    public void zahleEin(int betrag)
    {
        if (betrag > 0)
        {
            int neuerSaldo = _saldo + betrag;
            if (neuerSaldo > 0)
            {
                _saldo = neuerSaldo;
            }
            else
            {
                System.out.println("So viel Geld passt nicht.");
            }
        }
        else
        {
            System.out.println("Man kann nur positive Beträge einzahlen.");
        }
    }

    /**
     * Hebt einen Betrag von dem Konto ab.
     **/
    public String hebeAb2(int betrag)
    {
        if (betrag > 0)
        {
            if (betrag <= _saldo)
            {
                _saldo = _saldo - betrag;
                return "Alles Ok!";
            }
            else
            {
                return "Es ist nicht genug Geld auf dem Konto.";
            }
        }
        else
        {
            return "Man kann nur positive Beträge abheben.";
        }

    }

    /**
     * Hebt einen Betrag von dem Konto ab.
     **/
    public void hebeAb(int betrag)
    {
        if (betrag > 0)
        {
            if (betrag <= _saldo)
            {
                _saldo = _saldo - betrag;
            }
            else
            {
                System.out.println("Es ist nicht genug Geld auf dem Konto.");
            }
        }
        else
        {
            System.out.println("Man kann nur positive Beträge abheben.");
        }
    }
}