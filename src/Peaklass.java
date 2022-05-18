import java.io.File;
import java.io.StringReader;
import java.util.Random;
import java.util.Scanner;

public class Peaklass {

    public static boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception NumberFormatException ) {
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Tere tulemast!");
        System.out.print("Sisestage failinimi, kus soovite sündmusi muuta: ");
        String failinimi = scan.nextLine();

        TegevusList tegevusList = new TegevusList(failinimi.split("\\.")[0], new File(failinimi));

        boolean välju = false;
        while (!välju) {
            System.out.println("Sisesta number, mida soovite teha:");
            System.out.println("1 - Vaata sündmusi");
            System.out.println("2 - Lisa sündmus");
            System.out.println("3 - Kustuta sündmus");
            System.out.println("4 - Märgi sündmus tehtuks");
            System.out.println("5 - Puhasta sündmused, mis on tehtud");
            System.out.println("0 - Välju");
            System.out.print("Sisesta: ");
            int valik = Integer.parseInt(scan.nextLine());

            if (valik == 1){
                tegevusList.kuvaTegevused();
            } else if (valik == 2){
                System.out.print("Sisestage sündmuse nimi: ");
                String nimetus = scan.nextLine();

                while (nimetus.contains(";")) {
                    System.out.print("Sisestage sündmuse nimi (ilma märgita \";\" ): ");
                    nimetus = scan.nextLine();
                }
                System.out.print("Sisestage pikkem täpsustav kirjeldus sisu: ");
                String pikkKirjeldus = scan.nextLine();
                while (pikkKirjeldus.contains(";")) {
                    System.out.print("Sisestage kirjeldus (ilma märgita \";\" ): ");
                    pikkKirjeldus = scan.nextLine();
                }

                tegevusList.lisaListi(new Tegevus(nimetus, pikkKirjeldus, false, System.currentTimeMillis()));
                System.out.println("Sündmus lisatud!");
            } else if (valik == 3) {
                tegevusList.kuvaTegevused();
                System.out.println("Sisesta järjekorranumber, millist sündmust soovite eemaldada");
                System.out.print("Kui ei soovi, siis sisestage 0: ");
                String i = scan.nextLine();
                if (!isInteger(i)) {
                    continue;
                }
                int indeks = Integer.parseInt(i);
                if (indeks <= 0 || indeks >= tegevusList.listPikkus()+1) {
                    continue;
                }
                tegevusList.kustutaTegevus(indeks - 1);
            } else if (valik == 4) {
                tegevusList.kuvaTegevused();
                System.out.println("Sisesta järjekorranumber, millist sündmust soovite märkida tehtuks");
                System.out.print("Kui ei soovi, siis sisestage 0: ");
                String i = scan.nextLine();
                if (!isInteger(i)) {
                    continue;
                }
                int indeks = Integer.parseInt(i);
                if (indeks <= 0 || indeks >= tegevusList.listPikkus()+1) {
                    continue;
                }
                tegevusList.muudaTehtuks(indeks);
                System.out.println("Tegevus on nüüd märgitud tehtuks.");
            } else if (valik == 5){
                tegevusList.eemaldaTehtud();
            } else if (valik == 0){
                välju = true;
            }
        }
    }
}
