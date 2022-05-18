package com.example.ryhmatoo;

import java.text.SimpleDateFormat;

public class Tegevus {
    private String nimi;
    private final long sisestusAeg;
    private String pikkKirjeldus;
    private boolean tehtud;
    private final SimpleDateFormat ajaFormaat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

    public Tegevus(String nimi, String pikkKirjeldus, boolean tehtud) {
        this.nimi = nimi;
        this.sisestusAeg = System.currentTimeMillis();
        this.pikkKirjeldus = pikkKirjeldus;
        this.tehtud = tehtud;
    }
    public Tegevus(String nimi, String pikkKirjeldus, boolean tehtud, long sisestusAeg) {
        this.nimi = nimi;
        this.pikkKirjeldus = pikkKirjeldus;
        this.tehtud = tehtud;
        this.sisestusAeg = sisestusAeg;
    }


    public String failiFormaat() {
        return this.sisestusAeg+";"+nimi+";"+this.pikkKirjeldus+";"+this.tehtud;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    public void setTehtud(boolean state) { this.tehtud = state; }
    public void setpikkKirjeldus(String pikkKirjeldus) {
        this.pikkKirjeldus = pikkKirjeldus;
    }

    public String getNimi() {
        return nimi;
    }
    public long getSisestusAeg() {
        return sisestusAeg;
    }
    public String getpikkKirjeldus() {
        return pikkKirjeldus;
    }
    public boolean onTehtud() { return tehtud; }

    public String toString() {
        if (this.tehtud) {
            return this.ajaFormaat.format(this.sisestusAeg) + ": " + nimi + " :: " + pikkKirjeldus + " (tehtud!)";
        } else {
            return this.ajaFormaat.format(this.sisestusAeg) + ": " + nimi + " :: " + pikkKirjeldus + " (tegemata)";
        }
    }
}
