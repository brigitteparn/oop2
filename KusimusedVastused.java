package com.example.ruhmatoo_kaks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class KusimusedVastused {
    private final Map<String, String> failid;

    public KusimusedVastused() {
        failid = Map.of(
                "definitsioonid", "definitsioonid.txt",
                "teoreemid", "teoreemid.txt",
                "tnt", "tnt.txt"
        );

    }

    /**
     * Loeb küsimused ja vastused määratud kategooria failist
     * ning tagastab kas küsimuste või vastuste loendi
     * Faili sisu jagatakse eraldajate &&& ja ;;; abil küsimus-vastus paarideks
     * @param liik - kas küsimus või vastus
     * @param kategooria - määratud kategooria, mille failist andmeid lugeda
     * @return vastavalt määratud liigile kas küsimuste või vastuste loend
     */

    private List<String> andmedListi(String liik, String kategooria) {
        List<String> kusimused = new ArrayList<>();
        List<String> vastused = new ArrayList<>();
        String failinimi = failid.get(kategooria); // Vastava kategooria faili leidmine

        try {
            String kogufail = Files.readString(Path.of(failinimi), StandardCharsets.UTF_8);
            // Jagab faili sisu "&&&" jörgi, et saada küsimuste-vastuste komplektid
            String[] kusVas = kogufail.split("&&&");

            for (String s : kusVas) {
                // Jagab iga komplekti eraldaja ";;;" järgi, et saada eraldi küsimus ja vastus
                String[] komplekt = s.split(";;;");

                // Lisab küsimuse ja vastuse vastavasse loendisse
                kusimused.add(komplekt[0].trim());
                vastused.add(komplekt[1].trim());
            }
        } catch (IOException e) {
            throw new RuntimeException("Viga faili lugemisel: " + failinimi);
        }
        // Tagastab kas küsimuste või vastuste loendi sõltuvalt määratud liigist
        return liik.equals("k") ? kusimused : vastused;
    }
    
    
    public List<String> kusimused(String kategooria) {
        return andmedListi("k", kategooria);
    } // Tagastab määratud kategooria küsimuste loendi

    public List<String> vastused(String kategooria) {
        return andmedListi("v", kategooria);
    } // Tagastab määratud kategooria vastuste loendi
}
