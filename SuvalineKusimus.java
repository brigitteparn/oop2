package com.example.ruhmatoo_kaks;

import java.util.Random;

public class SuvalineKusimus {

    public static int suvalineKusimus(int k) {
        Random rand = new Random();
        return rand.nextInt(k);
    }
}
