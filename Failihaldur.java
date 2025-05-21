package com.example.oop_projekt2_0;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Tekstifailidest vastuste lugemine
 */
public class Failihaldur {
    public static String[] loeVastused(String failinimi) {
        try (InputStream in = Failihaldur.class.getResourceAsStream("/" + failinimi)) {
            if (in == null) {
                System.out.println("Vastusefail puudub: " + failinimi);
                return new String[0];
            }
            List<String> vastused = new ArrayList<>();

            // Fail loetakse ridade kaupa
            try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                String rida;
                while ((rida = br.readLine()) != null) {
                    vastused.add(rida.trim()); // Rea lisamine vastuste loendisse
                }
            }
            // Tagastatakse loend massiivina
            return vastused.toArray(new String[0]);
        } catch (IOException e) {
            System.out.println(" Viga vastuste lugemisel: " + failinimi);
            return new String[0]; // Vea tekkimisel tagstatakse tühi massiiv
        }
    }
}
