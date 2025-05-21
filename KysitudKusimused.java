package com.example.oop_projekt2_0;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Logihaldur, mis jälgib, millised pildifailid (või muud üksused)
 * on juba nähtud, kasutades lihtsat teksti logi faili \"logid/<failinimi>\".
 * Iga kord, kui küsimus/laaditav üksus näidatakse, lisatakse selle nimi logifaili
 * (kui seda juba seal pole). Kui logi on tühi, tähistab see, et kõik on veel näitamata.
 */
public class KysitudKusimused {
    private final Path logiFail;
    private final Set<String> nahtud = new HashSet<>();

    // Konstruktor, mis määrab logifaili nime (failinimi) ja laeb olemasoleva sisu
    public KysitudKusimused(String failinimi) {
        // we’ll write into ./logid/failinimi
        this.logiFail = Paths.get("logid", failinimi);
        laeLogist();
    }

    // Laeb olemasolevad nimed mällu, et neid enam mitte korrata
    private void laeLogist() {
        if (!Files.exists(logiFail)) return; // kui pole veel logifaili
        try {
            List<String> lines = Files.readAllLines(logiFail, StandardCharsets.UTF_8);
            lines.stream()
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .forEach(nahtud::add);
        } catch (IOException e) {
            System.err.println("️Viga logi lugemisel " + logiFail + ": " + e);
        }
    }

    // Kontrollib, kas antud nimi on juba logi lisatud.
    public boolean onKasutud(String nimi) {
        return nahtud.contains(nimi.trim());
    }

    // Lisab antud nime logifaili ja mällu, kui seda veel ei ole.
    public void lisa(String nimi) {
        String key = nimi.trim();
        if (nahtud.contains(key)) return;

        try {
            Files.createDirectories(logiFail.getParent());
            Files.writeString(
                    logiFail,
                    key + "\n",
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
            nahtud.add(key);
        } catch (IOException e) {
            System.err.println(" Viga logi kirjutamisel " + logiFail + ": " + e);
        }
    }

    // Kustutab failisisu, kui on välja kutsutud
    public void kustuta() {
        try {
            Files.deleteIfExists(logiFail);
            nahtud.clear();
        } catch (IOException e) {
            System.err.println(" Ei saanud logifaili kustutada " + logiFail + ": " + e);
        }
    }
}
