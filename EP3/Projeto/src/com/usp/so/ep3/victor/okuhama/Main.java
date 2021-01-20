package com.usp.so.ep3.victor.okuhama;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {
    public static void main(String[] args) {
        // ExecutorService classe que executa as 10 threads
        final ExecutorService executorThreads = Executors.newFixedThreadPool(10);

        // Classe Semáforo Mutex
        final SemaforoMutex semaforoMutex = new SemaforoMutex();
        // Executando 10 threads
        for (int i=1; i<= 10; i++) {
            executorThreads.execute(new Thread(semaforoMutex, "Thread-"+i));
        }
        // Finalizando Threads
        executorThreads.shutdown();
    }
}