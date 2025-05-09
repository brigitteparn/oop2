package com.example.ruhmatoo_kaks;

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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.ruhmatoo_kaks.Main.suvalineKusimus;

public class ValikvastustegaKasitleja implements EventHandler<MouseEvent> {
    private String failnimi = "valikvastustega.txt";
    private Stage lava;

    public ValikvastustegaKasitleja(Stage lava) {
        this.lava= lava;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        Stage kusimusteLava = new Stage();
        VBox juur = new VBox();

        Map<Integer,List<String[]>> küsimused = failiLugeja();
        KasOnKusitud kkk = new KasOnKusitud("kysitud_kusimused.txt");
        int kpikkus = küsimused.size();
        while(true){
            teeKusimus(kusimusteLava,juur,küsimused,kkk,kpikkus);
        }

    }

    private String teeKusimus(Stage kusimusteLava, VBox juur, Map<Integer, List<String[]>> küsimused, KasOnKusitud kkk, int kpikkus) {
        int indeks = suvalineKusimus(küsimused.size());
        kkk.setFailiread();
        boolean onKusitud = kkk.kasJubaKüsitud(indeks);

        if (!onKusitud) {
            List<String[]> küsimus = küsimused.get(indeks);
            System.out.println("file:matana2Pildid\\" + küsimus.get(0)[0]);
            Image pilt = new Image("file:matana2Pildid\\" + küsimus.get(0)[0]);
            ImageView kaetudPilt = new ImageView(pilt);

            Text kusimus = new Text(küsimus.get(1)[0]);
            int nuppe = küsimus.get(2).length;
            int oigeIndeks = Integer.parseInt(küsimus.get(3)[0]);
            List<Button> nupud = teeNupud(nuppe, küsimus.get(2));

            juur.getChildren().addAll(kaetudPilt, kusimus);

            int lisanupud = 0;
            while (lisanupud != nuppe) {
                boolean oige = false;
                if (oigeIndeks == lisanupud) {
                    oige = true;
                }


                nupud.get(lisanupud).addEventHandler(MouseEvent.MOUSE_CLICKED, new TagasisideLavaKasitleja(kusimusteLava, oige));
                juur.getChildren().add(nupud.get(lisanupud));

                lisanupud++;
            }
            Button vastus = new Button("Vaata vastust");
            vastus.setOnMouseClicked(mouseEvent1 -> nupud.get(oigeIndeks).setBackground(new Background(new BackgroundFill(Color.GREEN,new CornerRadii(0),new Insets(0)))));
            Button edasi = new Button("Järgmine küsimus");
            //edasi.setOnMouseClicked(mouseEvent1 -> {return " ";});
            Button esilehele = new Button("Esilehele");
            esilehele.setOnMouseClicked(mouseEvent1 -> {
                kkk.kustudaFail("kysitud_kusimused.txt");
                lava.show();
                kusimusteLava.hide();
            });
            juur.getChildren().addAll(vastus,edasi,esilehele);

            Scene stseen = new Scene(juur);
            kusimusteLava.setScene(stseen);
            kusimusteLava.setTitle("Küsimused");
            kusimusteLava.show();
            lava.hide();
        }
        if(kkk.getFailiread() == kpikkus){
            Stage kusimusedOtsas = new Stage();
            HBox alamjuur = new HBox();
            Text otsas = new Text("Küsimused on otsas");
            alamjuur.getChildren().add(otsas);

            Scene stseen = new Scene(alamjuur);
            kusimusedOtsas.setScene(stseen);
            kusimusedOtsas.setTitle("Küsimused otsas");
            kusimusedOtsas.show();
            lava.hide();
            kusimusteLava.hide();
        }
        return "";
    }

    private Map<Integer, List<String[]>> failiLugeja(){
        Map<Integer,List<String[]>> küsimused = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(failnimi), StandardCharsets.UTF_8))){
            String pilt = br.readLine().trim();
            String küsimus = br.readLine().trim();
            String[] valikvastused = br.readLine().trim().split("/");
            String õigevastus = br.readLine().trim();
            int lugeja = 0;
            while(br.readLine() != null){

                List<String[]> küsimusteKoostamine = new ArrayList<>();
                küsimusteKoostamine.add(new String[]{pilt});
                küsimusteKoostamine.add(new String[]{küsimus});
                küsimusteKoostamine.add(valikvastused);
                küsimusteKoostamine.add(new String[]{õigevastus});

                küsimused.put(lugeja,küsimusteKoostamine);
                lugeja++;

                pilt = br.readLine().trim();
                küsimus = br.readLine().trim();
                valikvastused = br.readLine().trim().split("/");
                õigevastus = br.readLine().trim();
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return küsimused;
    }

    private List<Button> teeNupud(int kuiPalju, String[] tekstid){
        List<Button> nupud = new ArrayList<>();
        int indeks = 0;
        while (kuiPalju!=0){
            Button nupp = new Button(tekstid[indeks]);
            nupud.add(nupp);

            kuiPalju--;
            indeks++;
        }
        return nupud;

    }
}
