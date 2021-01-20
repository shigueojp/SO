package com.usp.so.ep4.victor.okuhama;


// Singleton serve para existir APENAS 5 HASHIS, e não ficar instanciando a cada looping do while dos filósofos.
public class HashiSingleton {

    private static Hashi[] hashis;

    public static Hashi[] getHashis(int qdeHashi) {
        if (hashis == null) {
            hashis = new Hashi[qdeHashi];
            init(qdeHashi);
        }
        return hashis;
    }

    public static Hashi[] getHashis() {
        return getHashis(0);
    }

    public static void init(int qdehashi) {
        for(int i=0; i < qdehashi; i++) {
            hashis[i] = new Hashi(i);
        }
    }
}
