package com.example.ryhmatoo;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.HashMap;

import static java.lang.Double.MAX_VALUE;

public class Liides extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage peaLava) {

        peaLava.show();
        peaLava.setResizable(false);

        algus(peaLava);

        // Kui pannakse aknast kinni
        peaLava.setOnHiding(event -> {

            Stage kusimus = new Stage();

            kusimus.setMinWidth(250);
            kusimus.setMinHeight(150);

            Label label = new Label("Kas tõesti tahad kinni panna?");
            Button okButton = new Button("Jah");
            Button cancelButton = new Button("Ei");


            okButton.setOnAction(event1 -> kusimus.hide());


            cancelButton.setOnAction(event12 -> {
                peaLava.show();
                kusimus.hide();
            });


            FlowPane pane = new FlowPane(10, 10);
            pane.setAlignment(Pos.CENTER);
            pane.getChildren().addAll(okButton, cancelButton);


            VBox vBox1 = new VBox(10);
            vBox1.setAlignment(Pos.CENTER);
            vBox1.getChildren().addAll(label, pane);


            Scene stseen2 = new Scene(vBox1);
            kusimus.setScene(stseen2);
            kusimus.show();
        });
    }

    private void algus(Stage peaLava) {

        VBox read = new VBox(5);

        // Nupp
        BorderPane nuppBP = new BorderPane();
        Button sisestus = new Button("Sisesta");
        sisestus.setPrefSize(100, 50);
        sisestus.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 18.0));
        nuppBP.setCenter(sisestus);

        // Tekstiväljad erinevatel ridadel
        BorderPane algusBP = new BorderPane();
        Label algus = new Label("Tere tulemast!");
        algus.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 18.0));
        algusBP.setCenter(algus);
        algusBP.setPadding(new Insets(15, 5, 5, 5));

        BorderPane failBP = new BorderPane();
        Label failinimeTekst = new Label("Sisestage failinimi, kus soovite sündmusi muuta");
        failinimeTekst.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 18.0));
        failBP.setCenter(failinimeTekst);
        failBP.setPadding(new Insets(5, 5, 10, 5));

        // Failinime sisestusväli
        HBox sisestusHBox = new HBox();
        TextField failinimiSisend = new TextField();
        failinimiSisend.setPrefSize(200, 25);
        sisestusHBox.setAlignment(Pos.CENTER);
        sisestusHBox.getChildren().addAll(failinimiSisend);

        // Viga sisestamisel tekst
        BorderPane valeBP = new BorderPane();
        Label valeSisestus = new Label("Palun sisestage failinimi laiendiga .txt!");
        valeSisestus.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18.0));
        valeBP.setCenter(valeSisestus);
        valeBP.setVisible(false);

        read.getChildren().addAll(algusBP, failBP, sisestusHBox, nuppBP, valeBP);

        Scene algusStseen = new Scene(read, 400, 200, Color.SNOW);
        peaLava.setScene(algusStseen);
        peaLava.setTitle("Failinime sisestamine");

        sisestus.setOnAction(event -> failinimeSisestus(peaLava, failinimiSisend, valeBP, valeSisestus));
        algusStseen.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                failinimeSisestus(peaLava, failinimiSisend, valeBP, valeSisestus);
            }
        });
    }

    private void failinimeSisestus(Stage peaLava, TextField failinimiSisend, BorderPane bpVale, Label valeSisestus) {
        if (!failinimiSisend.getText().matches("\\w+\\.txt")) {
            bpVale.setVisible(true);
            FadeTransition kaotaTekst = new FadeTransition(Duration.seconds(5), valeSisestus);
            kaotaTekst.setFromValue(1.0);
            kaotaTekst.setToValue(0.0);
            kaotaTekst.play();
        } else {
            String failinimi;
            failinimi = failinimiSisend.getText();
            loeFailist(peaLava, failinimi);
        }
    }

    private void loeFailist(Stage peaLava, String failinimi) {
        VBox read = new VBox();

        // Tekst failist lugemine
        BorderPane tekstBP = new BorderPane();
        Label tekst = new Label("Loen failist andmeid");
        tekst.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18.0));
        tekstBP.setCenter(tekst);

        // Tekst failist loetud
        BorderPane tekstLoetudBP = new BorderPane();
        Label tekstLoetud = new Label("Failist andmed loetud");
        tekstLoetud.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18.0));
        tekstLoetudBP.setCenter(tekstLoetud);
        tekstLoetudBP.setVisible(false);

        read.getChildren().addAll(tekstBP, tekstLoetudBP);

        Scene lugemisStseen = new Scene(read, 400, 200, Color.SNOW);
        peaLava.setScene(lugemisStseen);
        peaLava.setTitle("Failist lugemine");

        // Proovib failist lugeda
        try {
            TegevusList tegevusList = new TegevusList(failinimi.split("\\.")[0], new File(failinimi));
            tekstLoetudBP.setVisible(true);
            tekstBP.setVisible(false);
            FadeTransition kaotaTekst = new FadeTransition(Duration.seconds(3), tekstLoetudBP);
            kaotaTekst.setFromValue(1.0);
            kaotaTekst.setToValue(0.0);
            kaotaTekst.play();
            kaotaTekst.setOnFinished(event -> peaMenüü(peaLava, tegevusList));
        } catch (Exception e) {
            Label viga = new Label("Failist lugemisel tekkis viga: ");
            viga.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 24.0));
            Label veateade = new Label(e.getMessage());
            veateade.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 24.0));
            veateke(peaLava, viga, veateade);
        }
    }

    private void veateke(Stage peaLava, Label viga, Label veateade) {

        VBox read = new VBox();

        // Tekst vea näitamiseks
        BorderPane bpViga = new BorderPane();
        bpViga.setCenter(viga);
        BorderPane bpVeateade = new BorderPane();
        bpVeateade.setCenter(veateade);

        // Nupud
        HBox nupudHBox = new HBox();
        Button prooviUuesti = new Button("Proovi uuesti!");
        prooviUuesti.setPrefSize(150, 25);
        prooviUuesti.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 18.0));
        Button välju = new Button("Välju");
        välju.setPrefSize(75, 25);
        välju.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 18.0));
        nupudHBox.getChildren().addAll(prooviUuesti, välju);
        nupudHBox.setAlignment(Pos.CENTER);

        read.getChildren().addAll(bpViga, bpVeateade, nupudHBox);

        Scene veaStseen = new Scene(read, 500, 300, Color.SNOW);
        peaLava.setScene(veaStseen);
        peaLava.setTitle("Viga!!!");

        välju.setOnAction(event -> peaLava.hide());
        prooviUuesti.setOnAction(event -> algus(peaLava));
    }

    private void peaMenüü(Stage peaLava, TegevusList tegevusList) {

        VBox read = new VBox();

        Button vaataNupp = new Button("Vaata sündmusi");
        vaataNupp.setPrefSize(MAX_VALUE, 200);
        vaataNupp.setStyle("-fx-font-size:36");
        Button lisaNupp = new Button("Lisa sündmus");
        lisaNupp.setPrefSize(MAX_VALUE, 200);
        lisaNupp.setStyle("-fx-font-size:36");
        Button kustutaNupp = new Button("Kustuta sündmus");
        kustutaNupp.setPrefSize(MAX_VALUE, 200);
        kustutaNupp.setStyle("-fx-font-size:36");
        Button tehtudNupp = new Button("Märgi sündmus tehtuks");
        tehtudNupp.setPrefSize(MAX_VALUE, 200);
        tehtudNupp.setStyle("-fx-font-size:36");
        Button puhastaNupp = new Button("Puhasta sündmused, mis on tehtud");
        puhastaNupp.setPrefSize(MAX_VALUE, 200);
        puhastaNupp.setStyle("-fx-font-size:36");
        Button väljuNupp = new Button("Välju");
        väljuNupp.setPrefSize(MAX_VALUE, 200);
        väljuNupp.setStyle("-fx-font-size:36");


        read.getChildren().addAll(vaataNupp, lisaNupp, kustutaNupp, tehtudNupp, puhastaNupp, väljuNupp);

        vaataNupp.setOnAction(event -> vaata(peaLava, tegevusList));

        lisaNupp.setOnAction(event -> lisa1(peaLava, tegevusList));

        kustutaNupp.setOnAction(event -> kustuta(peaLava, tegevusList));

        tehtudNupp.setOnAction(event -> tehtud(peaLava, tegevusList));

        puhastaNupp.setOnAction(event -> puhasta(peaLava, tegevusList));

        väljuNupp.setOnAction(event -> peaLava.hide());

        Scene peaStseen = new Scene(read, 640, 550, Color.SNOW);
        peaLava.setTitle("ToDo list");
        peaLava.setScene(peaStseen);
    }

    private void vaata(Stage peaLava, TegevusList tegevusList) {

        ScrollPane sp = new ScrollPane();

        VBox read = new VBox(10);
        read.setPadding(new Insets(10, 10, 10, 10));

        // Tekst kui sündmusi pole
        BorderPane tühiBP = new BorderPane();
        Label tühi = new Label("Sündmusi pole");
        tühi.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 16.0));
        tühi.setVisible(false);
        tühiBP.setCenter(tühi);
        read.getChildren().add(tühiBP);

        // Tagasi peamenüüsse nupp
        HBox nupudHBox = new HBox();
        Button tagasi = new Button("Tagasi");
        tagasi.setPrefSize(75, 25);
        tagasi.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 18.0));
        nupudHBox.getChildren().add(tagasi);
        nupudHBox.setAlignment(Pos.CENTER);

        // Sündmuste kirjeldused igal real eraldi
        int pikkus = 0;
        if (tegevusList.listPikkus() > 0){
            for (int i = 0; i < tegevusList.listPikkus(); i++) {
                Label sündmus = new Label("* " + tegevusList.getTegevused().get(i).toString());
                sündmus.setWrapText(true);
                sündmus.setMaxWidth(450);
                sündmus.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 16.0));
                read.getChildren().add(sündmus);
                pikkus += 25;
            }
        }
        if (tegevusList.listPikkus() < 1){
            tühi.setVisible(true);
        }

        // Lisab pärast kirjeldusi nupud
        read.getChildren().add(nupudHBox);

        Scene vaataStseen = new Scene(read, 600, 400);

        sp.setContent(read);
        peaLava.setScene(vaataStseen);
        peaLava.setTitle("Vaata sündmusi");

        tagasi.setOnAction(event -> peaMenüü(peaLava, tegevusList));
    }

    private void lisa1(Stage peaLava, TegevusList tegevusList) {

        VBox read = new VBox(5);

        // Tekst failist lugemine
        BorderPane sisestusBP = new BorderPane();
        Label sisestus = new Label("Sisestage sündmuse nimi");
        sisestus.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 18.0));
        sisestus.setPadding(new Insets(10,0,0,0));
        sisestusBP.setCenter(sisestus);

        // Tekstiväli
        HBox sisestusHBox = new HBox();
        TextField sisend = new TextField();
        sisend.setPrefSize(200, 25);
        sisestusHBox.setAlignment(Pos.CENTER);
        sisestusHBox.getChildren().addAll(sisend);

        // Nupud
        HBox nupudHBox = new HBox();
        Button sisesta = new Button("Sisesta");
        sisesta.setPrefSize(75, 25);
        sisesta.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 18.0));
        Button tagasi = new Button("Tagasi");
        tagasi.setPrefSize(75, 25);
        tagasi.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 18.0));
        nupudHBox.getChildren().addAll(tagasi, sisesta);
        nupudHBox.setAlignment(Pos.CENTER);

        // Tekst kui on vale sisestus
        BorderPane valeBP = new BorderPane();
        Label vale = new Label("Sisestage sündmuse nimi ilma märgita ;");
        vale.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15.0));
        vale.setPadding(new Insets(10,0,0,0));
        valeBP.setCenter(vale);
        valeBP.setVisible(false);

        read.getChildren().addAll(sisestusBP, sisestusHBox, nupudHBox, valeBP);

        Scene lisa1Stseen = new Scene(read, 300, 200, Color.SNOW);
        peaLava.setScene(lisa1Stseen);
        peaLava.setTitle("Sündmuse nimi");

        sisesta.setOnAction(event -> {
            kontrolliSisendLisamiselLisa1(peaLava, tegevusList, sisend, valeBP, vale);
        });

        lisa1Stseen.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                kontrolliSisendLisamiselLisa1(peaLava, tegevusList, sisend, valeBP, vale);
            }
        });

        tagasi.setOnAction(event -> peaMenüü(peaLava, tegevusList));
    }

    private void kontrolliSisendLisamiselLisa1(Stage peaLava, TegevusList tegevusList, TextField sisend, BorderPane valeBP, Label vale) {
        String sündmuseNimi = sisend.getText();
        if (sündmuseNimi.matches(".*;.*")) {
            valeBP.setVisible(true);
            FadeTransition kaotaTekst = new FadeTransition(Duration.seconds(5), vale);
            kaotaTekst.setFromValue(1.0);
            kaotaTekst.setToValue(0.0);
            kaotaTekst.play();
        } else {
            lisa2(peaLava, tegevusList, sündmuseNimi);
        }
    }

    private void lisa2(Stage peaLava, TegevusList tegevusList, String sündmuseNimi) {

        VBox read = new VBox(5);

        // Tekst failist lugemine
        BorderPane tekstBP = new BorderPane();
        Label tekst = new Label("Sisestage pikem täpsustav kirjeldus: ");
        tekst.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 18.0));
        tekstBP.setCenter(tekst);
        tekstBP.setPadding(new Insets(10,0,0,0));

        // Tekstiväli
        HBox sisestusHBox = new HBox();
        TextField sisend = new TextField();
        sisend.setPrefSize(200, 25);
        sisestusHBox.setAlignment(Pos.CENTER);
        sisestusHBox.getChildren().addAll(sisend);

        // Nupud
        HBox nupudHBox = new HBox();
        Button sisesta = new Button("Sisesta");
        sisesta.setPrefSize(75, 25);
        sisesta.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 18.0));
        Button tagasi = new Button("Tagasi");
        tagasi.setPrefSize(75, 25);
        tagasi.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 18.0));
        nupudHBox.getChildren().addAll(tagasi, sisesta);
        nupudHBox.setAlignment(Pos.CENTER);

        // Tekst kui on vale sisestus
        BorderPane valeBP = new BorderPane();
        Label vale = new Label("Sisestage sündmuse nimi ilma märgita ;");
        vale.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15.0));
        vale.setPadding(new Insets(10,0,0,0));
        valeBP.setCenter(vale);
        valeBP.setVisible(false);

        read.getChildren().addAll(tekstBP, sisestusHBox, nupudHBox, valeBP);

        Scene lisa2Stseen = new Scene(read, 300, 200, Color.SNOW);
        peaLava.setScene(lisa2Stseen);
        peaLava.setTitle("Sündmuse kirjeldus");

        sisesta.setOnAction(event -> {
            kontrolliSisendLisamiselLisa2(peaLava, tegevusList, sündmuseNimi, sisend, valeBP, vale);
        });

        lisa2Stseen.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                kontrolliSisendLisamiselLisa2(peaLava, tegevusList, sündmuseNimi, sisend, valeBP, vale);
            }
        });

        tagasi.setOnAction(event -> lisa1(peaLava, tegevusList));
    }

    private void kontrolliSisendLisamiselLisa2(Stage peaLava, TegevusList tegevusList, String sündmuseNimi, TextField sisend, BorderPane valeBP, Label vale) {
        String sündmuseKirjeldus = sisend.getText();
        if (sündmuseKirjeldus.matches(".*;.*")) {
            valeBP.setVisible(true);
            FadeTransition kaotaTekst = new FadeTransition(Duration.seconds(5), vale);
            kaotaTekst.setFromValue(1.0);
            kaotaTekst.setToValue(0.0);
            kaotaTekst.play();
        } else {
            try {
                tegevusList.lisaListi(new Tegevus(sündmuseNimi, sündmuseKirjeldus, false, System.currentTimeMillis()));
                tehtudAken(peaLava, tegevusList, " lisatud");
            } catch (Exception e) {
                veateke(peaLava, new Label("Tekkis viga sündmuse lisamisel"), new Label(e.getMessage()));
            }
        }
    }

    private void kustuta(Stage peaLava, TegevusList tegevusList) {

        ScrollPane sp = new ScrollPane();

        VBox read = new VBox(10);
        read.setPadding(new Insets(0, 0, 0, 5));

        // Tekst kui pole sündmusi
        BorderPane bpTühi = new BorderPane();
        Label tühi = new Label("Pole midagi võtta");
        tühi.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 16.0));
        tühi.setVisible(false);
        bpTühi.setCenter(tühi);

        read.getChildren().add(bpTühi);

        // Valiknupud
        ToggleGroup nuppudeValik = new ToggleGroup();

        HBox nupudHBox = new HBox();
        Button tagasi = new Button("Tagasi");
        tagasi.setPrefSize(75, 25);
        tagasi.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 18.0));

        Button vali = new Button("Vali");
        vali.setPrefSize(75, 25);
        vali.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 18.0));
        vali.setVisible(false);
        nupudHBox.getChildren().addAll(tagasi, vali);
        nupudHBox.setAlignment(Pos.CENTER);

        // Paneb iga tegevuse eraldi reale
        HashMap<RadioButton, Integer> valikud = new HashMap<>();
        if (tegevusList.listPikkus() > 0){
            for (int i = 0; i < tegevusList.listPikkus(); i++) {
                RadioButton nupp = new RadioButton(tegevusList.getTegevused().get(i).toString());
                nupp.setWrapText(true);
                nupp.maxWidth(500);
                nupp.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 16.0));
                nupp.setToggleGroup(nuppudeValik);
                read.getChildren().add(nupp);
                valikud.put(nupp, i);
            }
            vali.setVisible(true);
        }
        if (tegevusList.listPikkus() < 1){
            tühi.setVisible(true);
        }

        read.getChildren().add(nupudHBox);

        Scene kustutaStseen = new Scene(read, 600, 400);

        sp.setContent(read);
        peaLava.setTitle("Kustuta sündmus");
        peaLava.setScene(kustutaStseen);

        tagasi.setOnAction(event -> peaMenüü(peaLava, tegevusList));

        vali.setOnAction(event -> {
            if (nuppudeValik.getSelectedToggle() != null){
                RadioButton valitud = (RadioButton) nuppudeValik.getSelectedToggle();
                int indeks = valikud.get(valitud);
                tegevusList.kustutaTegevus(indeks);
                tehtudAken(peaLava, tegevusList, " kustutatud");
            }
        });
    }

    private void tehtud(Stage peaLava, TegevusList tegevusList) {

        ScrollPane sp = new ScrollPane();

        VBox read = new VBox(10);
        read.setPadding(new Insets(0, 0, 0, 5));

        BorderPane bpTühi = new BorderPane();
        Label tühi = new Label("Pole midagi võtta");
        tühi.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 16.0));
        tühi.setVisible(false);
        bpTühi.setCenter(tühi);

        read.getChildren().add(bpTühi);

        ToggleGroup nuppudeValik = new ToggleGroup();

        HBox nupudHBox = new HBox();
        Button tagasi = new Button("Tagasi");
        tagasi.setPrefSize(75, 25);
        tagasi.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 18.0));

        Button vali = new Button("Vali");
        vali.setPrefSize(75, 25);
        vali.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 18.0));
        vali.setVisible(false);
        nupudHBox.getChildren().addAll(tagasi, vali);
        nupudHBox.setAlignment(Pos.CENTER);

        HashMap<RadioButton, Integer> valikud = new HashMap<>();
        if (tegevusList.listPikkus() > 0){
            for (int i = 0; i < tegevusList.listPikkus(); i++) {
                RadioButton nupp = new RadioButton(tegevusList.getTegevused().get(i).toString());
                nupp.setWrapText(true);
                nupp.maxWidth(500);
                nupp.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 16.0));
                nupp.setToggleGroup(nuppudeValik);
                read.getChildren().add(nupp);
                valikud.put(nupp, i);
            }
            vali.setVisible(true);
        }
        if (tegevusList.listPikkus() < 1){
            tühi.setVisible(true);
        }

        read.getChildren().add(nupudHBox);

        Scene tehtudStseen = new Scene(read, 600, 400);

        sp.setContent(read);
        peaLava.setTitle("Märgi sündmus tehtuks");
        peaLava.setScene(tehtudStseen);

        tagasi.setOnAction(event -> peaMenüü(peaLava, tegevusList));

        vali.setOnAction(event -> {
            if (nuppudeValik.getSelectedToggle() != null){
                RadioButton valitud = (RadioButton) nuppudeValik.getSelectedToggle();
                int indeks = valikud.get(valitud);
                tegevusList.muudaTehtuks(indeks);
                tehtudAken(peaLava, tegevusList, " märgitud tehtuks");
            }
        });
    }

    private void tehtudAken(Stage peaLava, TegevusList tegevusList, String protsess) {

        // Tekst failist loetud
        BorderPane bpMärgitudTehtuks = new BorderPane();
        Label tekstLoetud = new Label("Sündmus on" + protsess);
        tekstLoetud.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25.0));
        bpMärgitudTehtuks.setCenter(tekstLoetud);

        Scene tehtudAknaStseen = new Scene(bpMärgitudTehtuks, 500, 300, Color.SNOW);
        FadeTransition kaotaTekst = new FadeTransition(Duration.seconds(3), bpMärgitudTehtuks);
        kaotaTekst.setFromValue(1.0);
        kaotaTekst.setToValue(0.0);
        kaotaTekst.play();
        kaotaTekst.setOnFinished(event -> peaMenüü(peaLava, tegevusList));

        peaLava.setScene(tehtudAknaStseen);
        peaLava.setTitle("Tehtud");

    }

    private void puhasta(Stage peaLava, TegevusList tegevusList) {

        BorderPane bpTekst = new BorderPane();
        Label tekst = new Label("Tehtud sündmused on puhastatud");
        tekst.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25.0));
        bpTekst.setCenter(tekst);

        Scene puhastaStseen = new Scene(bpTekst, 500, 300, Color.SNOW);
        peaLava.setScene(puhastaStseen);
        peaLava.setTitle("Puhasta tehtud sündmused");

        tegevusList.eemaldaTehtud();


        FadeTransition kaotaTekst = new FadeTransition(Duration.seconds(3), bpTekst);
        kaotaTekst.setFromValue(1.0);
        kaotaTekst.setToValue(0.0);
        kaotaTekst.play();
        kaotaTekst.setOnFinished(event -> peaMenüü(peaLava, tegevusList));
    }
}
