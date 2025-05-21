package com.example.oop_projekt2_0;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Käitleb valikvastustega küsimusi.
 * Iga küsimus kuvatakse vaid korra ja loetakse logifaili
 * Õige vastuse korral värvitakse nupp roheliseks,
 * Vale vastuse korral kuvatakse popup-aken tekstiga "Proovi uuesti!".
 */
public class ValikvastustegaKasitleja implements EventHandler<MouseEvent> {
    private final Stage pealava;

    // Logihaldur, mis kirjutab näidatud piltide nimed faili
    private final KysitudKusimused log = new KysitudKusimused("naidatud_valik.txt");

    public ValikvastustegaKasitleja(Stage pealava) {
        this.pealava = pealava;
    }

    @Override
    public void handle(MouseEvent event) {
        näitaKüsimust();
    }

    /**
     * Kuvab ühte juhuslikku, veel mitte näidatud küsimust.
     * Kui küsimused on otsas, puhastab logi ja näitab menüüd.
     */
    private void näitaKüsimust() {
        Map<Integer,List<String[]>> küsimused = laeKusimused();
        List<Integer> saadaval = new ArrayList<>();
        // Juba näidatud küsimuste väljafiltreerimine
        for (var entry : küsimused.entrySet()) {
            String fail = entry.getValue().get(0)[0].trim();
            if (!log.onKasutud(fail)) {
                saadaval.add(entry.getKey());
            }
        }

        if (saadaval.isEmpty()) {
            // Kui kõik küsimused näidatud, puhastab logi ja läheb tagasi menüüsse
            log.kustuta();
            pealava.show();
            return;
        }

        // Juhusliku küsimuse valimine
        int valitud = saadaval.get(new Random().nextInt(saadaval.size()));
        var andmed = küsimused.get(valitud);
        String piltFail = andmed.get(0)[0].trim();
        log.lisa(piltFail); // Märgib küsimuse logis näidatuk

        String tekst = andmed.get(1)[0]; // Küsimuse tekst ja vastusevariandid
        String[] valikud = andmed.get(2);
        int õige = Integer.parseInt(andmed.get(3)[0]);

        // Uus aken küsimuse kuvamiseks
        Stage küsimusLava = new Stage();
        VBox juur = new VBox(10);
        juur.setPadding(new Insets(20));

        ImageView pilt = new ImageView( // Pildi lisamine
                new Image(Objects.requireNonNull(
                        getClass().getResource("/pildid/" + piltFail)
                ).toExternalForm())
        );
        Text küsimuseTekst = new Text(tekst); // Teksti lisamine
        juur.getChildren().addAll(pilt, küsimuseTekst);

        // Valikvastuste nupud
        List<Button> nupud = new ArrayList<>();
        VBox opsBox = new VBox(5);
        for (int i = 0; i < valikud.length; i++) {
            int idx = i;
            Button nupp = new Button(valikud[i]);
            nupp.setMaxWidth(Double.MAX_VALUE);
            nupp.setOnMouseClicked(e -> {
                if (idx == õige) {
                    // Kui õige vastus, värvib roheliseks
                    nupp.setBackground(new Background(new BackgroundFill(
                            Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY
                    )));
                } else {
                    // Kui vale vastus, tekib aken "Proovi veel! "
                    new TagasisideAken(küsimusLava).handle(e);
                }
            });
            nupud.add(nupp);
            opsBox.getChildren().add(nupp);
        }
        juur.getChildren().add(opsBox);

        // Nuppude lisamine
        Button vaataOige = new Button("Vaata õiget vastust");
        vaataOige.setOnAction(e ->
                nupud.get(õige).setBackground(new Background(new BackgroundFill(
                        Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY
                )))
        );
        Button edasi = new Button("Järgmine");
        edasi.setOnAction(e -> {
            küsimusLava.close();
            näitaKüsimust();
        });
        Button esileht = new Button("Esilehele");
        esileht.setOnAction(e -> {
            log.kustuta();
            pealava.show();
            küsimusLava.close();
        });

        HBox ctrlBox = new HBox(10, vaataOige, edasi, esileht);
        ctrlBox.setPadding(new Insets(10));
        juur.getChildren().add(ctrlBox);

        // Küsimuse aken
        küsimusLava.setScene(new Scene(juur));
        küsimusLava.setTitle("Valikvastustega küsimus");
        küsimusLava.show();
        pealava.hide();
    }

    // Laeb küsimused failist kusimused.txt
    private Map<Integer,List<String[]>> laeKusimused() {
        Map<Integer,List<String[]>> map = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/valik/kusimused.txt"),
                StandardCharsets.UTF_8
        ))) {
            String pilt; int idx = 0;
            while ((pilt = br.readLine()) != null) {
                String kys = br.readLine();
                String[] ops = br.readLine().split("/");
                String cor = br.readLine();

                List<String[]> andmed = new ArrayList<>();
                andmed.add(new String[]{pilt.trim()});
                andmed.add(new String[]{kys.trim()});
                andmed.add(Arrays.stream(ops)
                        .map(String::trim)
                        .toArray(String[]::new));
                andmed.add(new String[]{cor.trim()});

                map.put(idx++, andmed);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Viga küsimuste faili lugemisel", ex);
        }
        return map;
    }
}
