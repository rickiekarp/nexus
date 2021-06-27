package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung5.Ampeln;

/**
 * Eine Ampel besteht aus drei Lampen in den Farben rot, gelb und gruen.
 * Sie durchlaeuft periodisch vier Phasen: gruen, gelb, rot, rot/gelb.
 * In den ersten drei Phasen leuchtet also nur jeweils eine Lampe,
 * waehrend in der vierten Phase zwei Lampen gleichzeitig leuchten.
 * Der Zustand der einzelnen Lampen kann abgefragt werden,
 * und die Ampel kann in die naechste Phase geschaltet werden.
 * Die Schaltung geschieht explizit durch einen Methodenaufruf
 * (und nicht etwa durch einen automatischen Timer).
 * 
 * @author Fredrik Winkler
 * @author Christian Spaeh
 * @version November 2013
 */
class Ampel
{
    private boolean _rotLeuchtet;
    private boolean _gelbLeuchtet;
    private boolean _gruenLeuchtet;

    /**
     * Initialisiert eine neue Ampel auf die erste Phase (gruen).
     */
    public Ampel()
    {
        _gruenLeuchtet = true;
    }
    
    /**
     * Gibt an, ob die rote Lampe leuchtet.
     */
    public boolean leuchtetRot()
    {
        // unnoetig kompliziert geloest
        if (_rotLeuchtet == true)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Gibt an, ob die gelbe Lampe leuchtet.
     */
    public boolean leuchtetGelb()
    {
        // immer noch recht kompliziert
        if (_gelbLeuchtet)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Gibt an, ob die gruene Lampe leuchtet.
     */
    public boolean leuchtetGruen()
    {
        // keine unnoetige Fallunterscheidung
        return _gruenLeuchtet;
    }

    /**
     * Schaltet die Ampel in die naechste Phase (gruen -> gelb -> rot -> rot/gelb -> gruen).
     */
    public void schalteWeiter()
    {
        if (_gruenLeuchtet)
        {
            _rotLeuchtet = false;
            _gelbLeuchtet = true;
            _gruenLeuchtet = false;
        }
        else
        {
            if (_gelbLeuchtet)
            {
                if (!_rotLeuchtet)
                {
                    _rotLeuchtet = true;
                    _gelbLeuchtet = false;
                    _gruenLeuchtet = false;
                }
                else
                {
                    _rotLeuchtet = false;
                    _gelbLeuchtet = false;
                    _gruenLeuchtet = true;
                }

            }
            else
            {
                _rotLeuchtet = true;
                _gelbLeuchtet = true;
                _gruenLeuchtet = false;
            }
        }
    }
}
