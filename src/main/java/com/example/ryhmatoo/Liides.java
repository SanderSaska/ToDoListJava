package com.example.ryhmatoo;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

import static java.lang.Double.MAX_VALUE;

public class Liides extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage peaLava) {

        peaLava.show();

        algus(peaLava);

        // Kui pannakse aknast kinni
        peaLava.setOnHiding(event -> {

            Stage kusimus = new Stage();

            kusimus.setMinWidth(250);
            kusimus.setMinHeight(100);

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

        peaLava.setMinWidth(425);
        peaLava.setMinHeight(250);

        VBox read = new VBox(5);

        // Nupp
        BorderPane bpNupp = new BorderPane();
        Button sisestus = new Button("Sisesta");
        sisestus.setPrefSize(100, 50);
        sisestus.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 18.0));
        bpNupp.setCenter(sisestus);

        // Tekstiväljad erinevatel ridadel
        BorderPane bpAlgus = new BorderPane();
        BorderPane bpFail = new BorderPane();
        VBox tekstid = new VBox();
        Label algus = new Label("Tere tulemast!");
        Label failinimeTekst = new Label("Sisestage failinimi, kus soovite sündmusi muuta");

        algus.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 18.0));
        failinimeTekst.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 18.0));
        bpAlgus.setCenter(algus);
        bpAlgus.setPadding(new Insets(15, 5, 5, 5));
        bpFail.setCenter(failinimeTekst);
        bpFail.setPadding(new Insets(5, 5, 10, 5));

        // Failinime sisestusväli
        HBox HBoxSisestus = new HBox();
        TextField failinimiSisend = new TextField();
        failinimiSisend.setPrefSize(200, 25);
        HBoxSisestus.setAlignment(Pos.CENTER);
        HBoxSisestus.getChildren().addAll(failinimiSisend);

        //
        BorderPane bpVale = new BorderPane();
        Label valeSisestus = new Label("Pole midagi sisestatud. Palun sisestage failinimi!");
        valeSisestus.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18.0));
        bpVale.setCenter(valeSisestus);
        bpVale.setVisible(false);

        read.getChildren().addAll(bpAlgus, bpFail, HBoxSisestus, bpNupp, bpVale);

        Scene algusStseen = new Scene(read, 500, 300, Color.SNOW);
        peaLava.setScene(algusStseen);
        peaLava.setTitle("Failinime sisestamine");

        sisestus.setOnAction(event -> {
            failinimeSisestus(peaLava, failinimiSisend, bpVale, valeSisestus);
        });
        algusStseen.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                failinimeSisestus(peaLava, failinimiSisend, bpVale, valeSisestus);
            }
        });
    }

    private void failinimeSisestus(Stage peaLava, TextField failinimiSisend, BorderPane bpVale, Label valeSisestus) {
        if (failinimiSisend.getText() == null || failinimiSisend.getText().equals("")) {
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
        BorderPane bpTekst = new BorderPane();
        Label tekst = new Label("Loen failist andmeid");
        tekst.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18.0));
        bpTekst.setCenter(tekst);

        // Tekst failist loetud
        BorderPane bpTekstLoetud = new BorderPane();
        Label tekstLoetud = new Label("Failist andmed loetud");
        tekstLoetud.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18.0));
        bpTekstLoetud.setCenter(tekstLoetud);
        bpTekstLoetud.setVisible(false);

        read.getChildren().addAll(bpTekst, bpTekstLoetud);

        Scene lugemisStseen = new Scene(read, 500, 300, Color.SNOW);
        peaLava.setScene(lugemisStseen);
        peaLava.setTitle("Failist lugemine");

        // Proovib failist lugeda
        try {
            TegevusList tegevusList = new TegevusList(failinimi.split("\\.")[0], new File(failinimi));
            bpTekstLoetud.setVisible(true);
            bpTekst.setVisible(false);
            FadeTransition kaotaTekst = new FadeTransition(Duration.seconds(3), bpTekstLoetud);
            kaotaTekst.setFromValue(1.0);
            kaotaTekst.setToValue(0.0);
            kaotaTekst.play();
            kaotaTekst.setOnFinished(event -> {
                peaMenüü(peaLava, tegevusList);
            });
        } catch (Exception e) {
            Label viga = new Label("Failist lugemisel tekkis viga: ");
            viga.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 24.0));
            Label veateade = new Label(e.getMessage());
            veateade.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 24.0));
            veateke(peaLava, viga, veateade);
        }
    }

    private void veateke(Stage peaLava, Label viga, Label veateade) {
        peaLava.setMinHeight(300);
        peaLava.setMinWidth(500);

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

        välju.setOnAction(event -> {
            peaLava.hide();
        });
        prooviUuesti.setOnAction(event -> {
            algus(peaLava);
        });
    }

    private void peaMenüü(Stage peaLava, TegevusList tegevusList) {

        VBox vBox = new VBox();

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


        vBox.getChildren().addAll(vaataNupp, lisaNupp, kustutaNupp, tehtudNupp, puhastaNupp, väljuNupp);

        vaataNupp.setOnAction(event -> {

        });

        lisaNupp.setOnAction(event -> {

        });

        kustutaNupp.setOnAction(event -> {

        });

        tehtudNupp.setOnAction(event -> {
            tehtud(peaLava, tegevusList);
        });

        puhastaNupp.setOnAction(event -> {
            puhasta(peaLava, tegevusList);
        });

        väljuNupp.setOnAction(event -> {
            peaLava.hide();
        });

        Scene stseen = new Scene(vBox, 640, 480, Color.SNOW);
        peaLava.setTitle("ToDo list");
        peaLava.setScene(stseen);
    }

    private void vaata() {

    }

    private void lisa() {

    }

    private void kustuta() {

    }

    private void tehtud(Stage peaLava, TegevusList tegevusList) {

        VBox read = new VBox(5);

        for (int i = 0; i < tegevusList.listPikkus(); i++) {

        }
    }

    private void puhasta(Stage peaLava, TegevusList tegevusList) {

        BorderPane bpTekst = new BorderPane();
        Label tekst = new Label("Tehtud sündmused on puhastatud");
        tekst.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18.0));
        bpTekst.setCenter(tekst);

        Scene puhasta = new Scene(bpTekst, 500, 300, Color.SNOW);
        peaLava.setScene(puhasta);
        peaLava.setTitle("Puhasta tehtud sündmused");

        tegevusList.eemaldaTehtud();


        FadeTransition kaotaTekst = new FadeTransition(Duration.seconds(3), bpTekst);
        kaotaTekst.setFromValue(1.0);
        kaotaTekst.setToValue(0.0);
        kaotaTekst.play();
        kaotaTekst.setOnFinished(event -> {
            peaMenüü(peaLava, tegevusList);
        });
    }
}
