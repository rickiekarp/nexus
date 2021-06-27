package com.itech.ae03.klausur.rickie;

public class Mitarbeiter {
    private String vorname;
    private String nachname;
    private int gehaltsstufe;
    private boolean pruefer;

    public Mitarbeiter() {
        //empty
    }

    /**
     * Constructor with parameters
     * @param mVorname First name of the employee
     * @param mNachname Last name of the employee
     * @param mGehaltsstufe Pay level of the employee
     * @param isPruefer If the employee
     */
    public Mitarbeiter(String mVorname, String mNachname, int mGehaltsstufe, boolean isPruefer) {
        this.vorname = mVorname;
        this.nachname = mNachname;
        this.gehaltsstufe = mGehaltsstufe;
        this.pruefer = isPruefer;
    }

    /**
     * Prints the current vorname/nachname/gehaltsstufe/pruefer values
     * @return Formatted Mitarbeiter String
     */
    @Override
    public String toString() {
        return "Mitarbeiter{" +
                "vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", gehaltsstufe=" + gehaltsstufe +
                ", pruefer=" + pruefer +
                '}';
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public int getGehaltsstufe() {
        return gehaltsstufe;
    }

    public void setGehaltsstufe(int gehaltsstufe) {
        if (gehaltsstufe >= 1 && gehaltsstufe <= 8) {
            this.gehaltsstufe = gehaltsstufe;
        } else {
            this.gehaltsstufe = 0;
            System.out.println("kein Gültiger Wert");
        }
    }

    public boolean isPruefer() {
        return pruefer;
    }

    public void setPruefer(boolean isPruefer) {
        this.pruefer = isPruefer;
    }
}
