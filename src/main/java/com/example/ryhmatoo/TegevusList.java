package com.example.ryhmatoo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TegevusList {
    private final String nimi;
    private final File fail;
    private List<Tegevus> tegevused;

    public TegevusList(String nimi, File fail) {
        this.nimi = nimi;
        this.fail = fail;
        this.tegevused = this.loeTegevusedFailist();
    }

    private ArrayList<Tegevus> loeTegevusedFailist() {
        ArrayList<Tegevus> tulemus = new ArrayList<>();
        String[] rida;
        Tegevus tegevus;

        // Loeb read failist
        try (Scanner sc = new Scanner(fail, "UTF-8")) {
            while (sc.hasNextLine()) {
                rida = sc.nextLine().split(";");
                tegevus = new Tegevus(rida[1], rida[2], Boolean.parseBoolean(rida[3]), Long.parseLong(rida[0]));

                tulemus.add(tegevus);
            }
        // Loob uue faili, kui faili ei eksisteeri
        } catch (FileNotFoundException fnfe) {
            System.out.println("Faili ei leitud.");
            try {
                 if (fail.createNewFile()) {
                     System.out.println("Uus fail loodud.");
                 } else {
                     System.out.println("Error!");
                 }
            } catch (IOException ioe) {
                System.out.println("Midagi läks valest.");
            }
        }
        return tulemus;
    }

    private void uuendaFaili() {
        try (PrintWriter pw = new PrintWriter(fail, "UTF-8")) {
            for (Tegevus t : this.tegevused) {
                pw.println(t.failiFormaat());
            }
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("Midagi läks valesti.");
        }
    }

    public void lisaListi(Tegevus tegevus) {
        this.tegevused.add(tegevus);
        this.uuendaFaili();
    }

    public void kustutaTegevus(int i) {
        this.tegevused.remove(i);
        this.uuendaFaili();
    }

    public void muudaTehtuks(int i) {
        this.tegevused.get(i).setTehtud(true);
        this.uuendaFaili();
    }

    public int listPikkus(){
        return this.tegevused.size();
    }

    public void eemaldaTehtud() {
        ArrayList<Tegevus> uuedTegevused = new ArrayList<>();
        for (Tegevus t : this.tegevused) {
            if (!t.onTehtud()) {
                uuedTegevused.add(t);
            }
        }
        this.tegevused = uuedTegevused;
        this.uuendaFaili();
    }

    public void kuvaTegevused() {
        int i = 1;
        System.out.println("------ "+this.nimi+" ------");
        for (Tegevus t : this.tegevused) {
            System.out.println(i+". "+t.toString());
            i++;
        }
        System.out.println("-".repeat(nimi.length()+14));
    }

    public List<Tegevus> getTegevused() {
        return tegevused;
    }
}
