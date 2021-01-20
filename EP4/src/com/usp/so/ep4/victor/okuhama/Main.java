package com.usp.so.ep4.victor.okuhama;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        // ExecutorService classe que executa as 5 threads (filósofos)
        final ExecutorService executorThreads = Executors.newFixedThreadPool(5);
        HashiSingleton.getHashis(5);
        // Executando 5 threads
        for (int i=1; i<=5; i++) {
            executorThreads.execute(new Filosofo(i, 5));
        }
        // Finalizando Threads
        executorThreads.shutdown();
    }
}
