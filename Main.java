package com.example.oop_projekt2_0;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class Main extends Application {
    private Stage pealava;
    private final KysitudKusimused fillLog = new KysitudKusimused("naidatud_fill.txt");

    @Override
    public void start(Stage lava) {
        this.pealava = lava;

        // Menüünupud
        Button nuppValik = new Button("Valikvastustega küsimused");
        nuppValik.setMaxWidth(Double.MAX_VALUE);
        nuppValik.setBackground(
                new Background(new BackgroundFill(Color.WHITE, new CornerRadii(4), Insets.EMPTY))
        );
        nuppValik.setOnAction(e ->
                new ValikvastustegaKasitleja(pealava).handle(null)
        );

        Button nuppTäida = new Button("Täida lüngad");
        nuppTäida.setMaxWidth(Double.MAX_VALUE);
        nuppTäida.setBackground(
                new Background(new BackgroundFill(Color.WHITE, new CornerRadii(4), Insets.EMPTY))
        );
        nuppTäida.setOnAction(e -> alustaLunk());

        // Pink taust menüüks
        VBox juur = new VBox(10, nuppValik, nuppTäida);
        juur.setPadding(new Insets(20));
        juur.setBackground(
                new Background(new BackgroundFill(Color.LIGHTPINK, CornerRadii.EMPTY, Insets.EMPTY))
        );

        Scene stseen = new Scene(juur, 300, 150);
        lava.setTitle("Peamenüü");
        lava.setScene(stseen);
        lava.show();
    }

    /** Käivitab järgmise täida-lüngad küsimuse või läheb tagasi menüüsse. */
    public void alustaLunk() {
        String fail = KusimusteHaldur.suvalineKasutamata(
                "pildid", fillLog, "fill"
        );
        if (fail == null) {
            fillLog.kustuta();
            pealava.show();
            return;
        }
        fillLog.lisa(fail);

        String vastusteFail = "vastused/" + fail.replace(".png", ".txt");
        String[] vastused = Failihaldur.loeVastused(vastusteFail);

        String pildiTee = "pildid/" + fail;
        LungadPaneel panel = new LungadPaneel(pildiTee, vastused, this, fail);

        Stage täidaLava = new Stage();
        täidaLava.setScene(new Scene(panel));
        täidaLava.setTitle("Täida lüngad");
        täidaLava.show();
        pealava.hide();
    }

    /** Tagasi peamenüüsse. */
    public void tagasiEsilehele() {
        pealava.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
