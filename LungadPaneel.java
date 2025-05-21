package com.example.oop_projekt2_0;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LungadPaneel extends BorderPane {


    /**
     * Täida-lüngad-paneel, mis kuvab pildi ja tekstiväljad vastuste sisestamiseks
     * Kui kõik lüngad täidetud, pakub kontrollinupu ja võimalused näidata õigeid vastuseid,
     * edasi liikuda järgmisele küsimusele või naasta peamenüüsse.
     */
    public LungadPaneel(String pildiTee, String[] vastused, Main app, String failinimi) {

        // Ülemine osa: pilt ja sisestusväljad
        VBox ylaosa = new VBox(10);
        ylaosa.setPadding(new Insets(15));
        ylaosa.setAlignment(Pos.CENTER);

        ImageView pilt = new ImageView( // Pildi kuvamine
                new Image(getClass().getResource("/" + pildiTee).toExternalForm())
        );
        pilt.setFitWidth(700);
        pilt.setPreserveRatio(true);
        ylaosa.getChildren().add(pilt);


        // Tekstiväljade loomine iga lünga jaoks
        TextField[] sisestused = new TextField[vastused.length];
        VBox sisendkastid = new VBox(8);
        sisendkastid.setAlignment(Pos.CENTER);
        for (int i = 0; i < vastused.length; i++) {
            TextField tf = new TextField();
            tf.setPromptText("Lünk " + (i + 1));
            tf.setMaxWidth(300);
            sisestused[i] = tf;
            sisendkastid.getChildren().add(tf);
        }
        ylaosa.getChildren().add(sisendkastid);
        setTop(ylaosa);

        // Keskosa: tagasiside silt
        Label tagasiside = new Label();
        tagasiside.setPadding(new Insets(10));
        setCenter(tagasiside);

        // Alumine osa: nupud
        Button kontrolli = new Button("Kontrolli");
        Button naita     = new Button("Näita õigeid vastuseid");
        Button edasi      = new Button("Järgmine");
        Button tagasi     = new Button("Esilehele");

        // Nup
        kontrolli.setOnAction(e -> {
            boolean koikOiged = true;
            for (int i = 0; i < vastused.length; i++) {
                if (!sisestused[i].getText().trim().equalsIgnoreCase(vastused[i])) {
                    koikOiged = false;
                    break;
                }
            }
            tagasiside.setText(koikOiged
                    ? "Kõik vastused õiged!"
                    : "Proovi veel.");
        });

        // Õigete vastuste nupp: kõikide lünkade täitmine õigete vastustega
        naita.setOnAction(e -> {
            for (int i = 0; i < vastused.length; i++) {
                sisestused[i].setText(vastused[i]);
            }
        });

        // Nupp 'Järgmine':  akna sulgemine ja järgmisele küsimusele liikumine
        edasi.setOnAction(e -> {
            ((Stage) getScene().getWindow()).close();
            app.alustaLunk();
        });
        // Tagasi nupp: akna sulgemine ja esilehele naasmine
        tagasi.setOnAction(e -> {
            ((Stage) getScene().getWindow()).close();
            app.tagasiEsilehele();
        });

        HBox nupud = new HBox(10, kontrolli, naita, edasi, tagasi);
        nupud.setPadding(new Insets(10));
        nupud.setAlignment(Pos.CENTER_LEFT);
        setBottom(nupud);
    }
}
