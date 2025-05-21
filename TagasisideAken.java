package com.example.oop_projekt2_0;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

/**
 * Väike aken, mis kuvatakse ainult siis, kui vastus on vale
 */

public class TagasisideAken implements EventHandler<MouseEvent> {
    private final Stage parent;

    public TagasisideAken(Stage parent) {
        this.parent = parent;
    }

    @Override
    public void handle(MouseEvent event) {
        Stage aken = new Stage();
        VBox juur = new VBox(10);
        juur.setPadding(new Insets(20));
        juur.getChildren().add(new Text("Proovi veel! :)"));

        Scene stseen = new Scene(juur, 100, 100);
        aken.setScene(stseen);
        aken.setTitle("Tagasiside");

        // Vanem aken kuvatakse uuesti, kui aken suletakse
        aken.setOnHidden(e -> parent.show());
        parent.hide(); // Peidetakse vanem aken
        aken.show(); // Kuvatakse uus hõpikaken
    }
}
