package com.usp.so.ep4.victor.okuhama;

import java.util.concurrent.Semaphore;

enum StatusHashi {
    LIVRE,
    USANDO,
}

public class Hashi {

    private int id;
    private Semaphore semaforo;
    private String status;

    public Semaphore getSemaforo() {
        return semaforo;
    }
    public void setSemaforo(Semaphore semaforo) {
        this.semaforo = semaforo;
    }

    Hashi(int id) {
        this.id = id;
        //  Mutex = 1, livre
        this.semaforo = new Semaphore(1);
        //  Começa sempre com os Hashi na mesa, ou seja, livre
        this.status = StatusHashi.LIVRE.toString();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
