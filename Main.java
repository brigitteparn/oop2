package com.example.ruhmatoo_kaks;
import javafx.application.Application;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {

    public static void main(String[] args) {

        launch();
    }

    @Override
    public void start(Stage peaLava) {
        GridPane juur = new GridPane();
        //akna servale ka ruumi
        juur.setPadding(new Insets(100));
        //muudame ruumi
        juur.setHgap(2);
        juur.setVgap(2);
        juur.backgroundProperty().setValue(new Background(new BackgroundFill(Color.LIGHTPINK,new CornerRadii(0),new Insets(0))));

        Background nuputaust = new Background(new BackgroundFill(Color.WHITE,new CornerRadii(0),new Insets(2)));
        Button b1 = new Button("Lünktekstiga küsimused");
        b1.setBackground(nuputaust);
        Button b2 = new Button("Valikvastustega küsimused");
        b2.setBackground(nuputaust);
        Button b3 = new Button("Lõpp");
        b3.setBackground(nuputaust);

        //b1.addEventHandler(MouseEvent.MOUSE_CLICKED,);
        b2.addEventHandler(MouseEvent.MOUSE_CLICKED,new ValikvastustegaKasitleja(peaLava));
        b3.setOnMouseClicked(mouseEvent -> System.exit(0));




        juur.add(b1,3,2);
        juur.add(b2,3,3);
        juur.add(b3,3,4);
        //juur.getChildren().addAll(b1,b2,b3);
        Scene scene = new Scene(juur,400,300);
        peaLava.setScene(scene);
        peaLava.setTitle("Mõistekaardid");
        //akna suurus muutub?
        peaLava.setResizable(true);
        peaLava.show();
    }

    public static int suvalineKusimus(int k) {
        Random rand = new Random();
        return rand.nextInt(k);
    }
}
