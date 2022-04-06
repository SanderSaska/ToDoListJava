import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Peaklass {

    public static DateTimeFormatter formaat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static Sündmused loeFaili(String failinimi) throws FileNotFoundException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Scanner sc = new Scanner(new File(failinimi), "UTF-8");
        Sündmused sündmused = new Sündmused();
        while (sc.hasNextLine()) {
            String rida = sc.nextLine();
            if (rida.isBlank()){
                continue;
            }
            String[] jupid = rida.split(";");
            String nimetus = jupid[0];
            boolean tehtud = Boolean.parseBoolean(jupid[1]);
            if (jupid.length == 3){
                LocalDateTime aeg = LocalDateTime.parse(jupid[2], formaat);
                sündmused.lisaSündmus(new SündmusAjaga(nimetus, aeg));
            } else {
                sündmused.lisaSündmus(new Sündmus(nimetus));
            }
        }
        sc.close();
        return sündmused;
    }

    public static void salvestaFaili(String failinimi){

    }

    public static void main(String[] args) throws IOException, ParseException {

        Scanner scan = new Scanner(System.in);
        Random rand = new Random();

        System.out.println("Tere tulemast!");
        System.out.println("Sisestage failinimi, kus soovite sündmusi muuta: ");
        String failinimi = scan.nextLine();
        File fail = new File(failinimi);
        if (fail.createNewFile()) {
            System.out.println("Fail '" + failinimi + "' loodud!");
        }
        Sündmused sündmused = loeFaili(failinimi);
        boolean välju = false;
        while(!välju){
            System.out.println("Sisesta number, mida soovite teha:");
            System.out.println("1 - Vaata sündmusi");
            System.out.println("2 - Lisa sündmus");
            System.out.println("3 - Kustuta sündmused");
            System.out.println("4 - Puhasta sündmused, mis on tehtud");
            System.out.println("5 - Salvesta");
            System.out.println("0 - Välju");
            System.out.println("Sisesta: ");
            int valik = scan.nextInt();

            if (valik == 1){
                System.out.println("Sündmused on: ");
                sündmused.väljastaSündmusi();
            }
            if (valik == 2){
                System.out.println("Sisestage sündmuse sisu: ");
                String nimetus = scan.nextLine();
                while (nimetus.contains(";")) {
                    System.out.println("Sisestage sündmuse sisu (ilma märgita \";\" ): ");
                    nimetus = scan.nextLine();
                }
                System.out.println("Võib jätta tühjaks(siis tuleb ilma ajata sündmus)");
                System.out.println("Sisestage aeg: ");
            }
            if (valik == 3){
                sündmused.väljastaSündmusi();
                System.out.println("Sisesta järjekorranumber, millist sündmust soovite eemaldada");
                System.out.println("Kui ei soovi, siis sisestage 0: ");
                int indeks = scan.nextInt();
                if (indeks == 0){
                    continue;
                }
                sündmused.kustutaSündmus(indeks-1);
            }
            if (valik == 4){
                for (int i = 0; i < sündmused.SündmusLength(); i++) {
                    Sündmus sündmus = sündmused.getSündmus(i);
                    String aeg = sündmus.toString();
                }
            }
            if (valik == 5){
                FileWriter fW = new FileWriter(failinimi);
                for (int i = 0; i < sündmused.SündmusLength(); i++) {
                    fW.write(sündmused.getSündmus(i).toString());
                }
            }
            if (valik == 0){
                välju = true;
            }
        }
    }
}
