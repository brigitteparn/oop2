package com.example.ruhmatoo_kaks;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class KasOnKusitud {
    private final String failinimi;
    private int failiread;

    public KasOnKusitud(String failinimi) {
        this.failinimi = failinimi;
        this.failiread = 0;
    }

    public int getFailiread() {
        return failiread;
    }

    public void setFailiread() {
        this.failiread = 0;
    }

    /**
     * Loeb failist kõik kirjed ja kontrollib, kas indeks on juba olemas
     * @param fail - kontrollitav fail
     * @param indeks - otsitav indeks
     * @return tõene, kui indeks on failis juba olemas, vastasel juhul väär
     */

    private boolean loeFailist(File fail, int indeks){
        try(BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(failinimi),StandardCharsets.UTF_8))){
            String rida = bf.readLine();
            while (rida != null){
                failiread++;
                if (Integer.parseInt(rida.trim()) == indeks){
                    return true;
                }
                rida = bf.readLine();
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * Kirjutab faili uue indeksi
     * @param fail - fail, kuhu on salvestatud varem väljastatud küsimuste indeksid
     * @param indeks - lisatav indeks
     */
    private void kirjutaFaili(File fail, int indeks) throws IOException {
        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fail,true),StandardCharsets.UTF_8))){
            bw.write(indeks + "\n"); // Uue indeksi lisamine faili lõppu
        }

    }

    /**
     * Kontrollib, kas indeks on juba küsitud, ning vajadusel lisab selle faili
     * @param indeks - kontrollitav indeks
     * @return tõene, kui indeks juba küsitud
     */

    public boolean kasJubaKüsitud(int indeks){
        File fail = new File(failinimi);
        try{
            if (fail.exists() && loeFailist(fail, indeks)) {
                return true;
            }
            kirjutaFaili(fail, indeks);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return false; // Indeksi lisamine faili, kui seda seal polnud
    }
    
    // Kustutab faili (kui kõik küsimused küsitud või kasutaja soovib vahetada kategooriat)
    public void kustudaFail(String nimi){
        File fail = new File(nimi);
        fail.delete();
    }
}
