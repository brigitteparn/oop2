package com.example.ruhmatoo_kaks;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TagasisideLavaKasitleja implements EventHandler<MouseEvent> {
    private Stage lava;
    private boolean oige;

    public TagasisideLavaKasitleja(Stage lava, boolean oige) {
        this.lava = lava;
        this.oige = oige;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        Stage tagasisideLava = new Stage();
        VBox juur = new VBox();
        Text tagasiside = new Text("vale");
        if(oige){
            tagasiside = new Text("õige");
        }

        tagasisideLava.setOnHiding(windowEvent -> {
            lava.show();
            tagasisideLava.hide();
        });

        juur.getChildren().add(tagasiside);

        Scene stseen = new Scene(juur);
        tagasisideLava.setScene(stseen);
        tagasisideLava.setTitle("Tagasiside");
        tagasisideLava.show();
        lava.hide();

    }
}
