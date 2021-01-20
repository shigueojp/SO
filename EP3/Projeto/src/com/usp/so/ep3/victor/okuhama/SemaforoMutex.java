package com.usp.so.ep3.victor.okuhama;

import java.util.concurrent.Semaphore;

public class SemaforoMutex {
    private static final int MAX_PERMIT = 1;

    // Mutex = 1, livre
    private final Semaphore mutex = new Semaphore(MAX_PERMIT, true);

    // Variável count
    private int count;

    public void print(String threadProcess) {
        regiao_critica(threadProcess);
        regiao_nao_critica(threadProcess);
    }

    public void regiao_critica(String threadProcess) {
        try {
    // Mutex = 0 -> Bloqueando recurso
            mutex.acquire();
            System.out.println(threadProcess + " executando Sessão Crítica - Count inicial: " + count);
            count++;
            java.lang.Thread.sleep(1000);
            System.out.println(threadProcess + " executando Sessão Não Crítica - Count final: " + count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
    // Mutex = 1 -> Recurso Liberado
            mutex.release();
        }
    }

    public void regiao_nao_critica(String threadProcess) {
        System.out.println(threadProcess + " Seccao Não Critica - Executando sessao não critica através da thread.");
        System.out.println("Tem threads na fila: " + this.temThreadsNaFila());
    }

    boolean temThreadsNaFila() {
        return mutex.hasQueuedThreads();
    }
}
