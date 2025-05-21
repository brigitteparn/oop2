package com.example.oop_projekt2_0;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Klass küsimuste haldamiseks — leiab suvalise veel kasutamata küsimusepildi.
 */

public class KusimusteHaldur {

    /**
     * Tagastab suvalise veel kasutamata pildi failinime määratud kaustast.
     *
     * @param kaust  Kausta nimi ressurssides, kus pildid asuvad (nt "pildid")
     * @param logi   Objekt, mis hoiab meeles juba kasutatud küsimused
     * @param prefix Pildi failinime algus (nt "fill" või "pilt")
     * @return       Suvaline kasutamata faili nimi või null, kui kõik on kasutatud
     */

    public static String suvalineKasutamata(String kaust, KysitudKusimused logi, String prefix) {
        File folder = new File("src/main/resources/" + kaust);
        if (!folder.exists()) return null;

        File[] failid = folder.listFiles((dir, name) -> name.startsWith(prefix) && name.endsWith(".png"));
        if (failid == null || failid.length == 0) return null;

        List<String> valimata = new ArrayList<>();
        for (File f : failid) {
            // Kui fail pole veel kasutusel olnud, listakase nimi loendisse
            if (!logi.onKasutud(f.getName())) {
                valimata.add(f.getName());
            }
        }

        if (valimata.isEmpty()) return null; // Kui kõik failid on juba kasutatud, siis tagstatakse null

        // Kasutamata faili valimine juhuslikult
        Random rand = new Random();
        return valimata.get(rand.nextInt(valimata.size()));
    }
}
