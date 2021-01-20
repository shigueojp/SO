package com.usp.so.ep3.victor.okuhama;

public class Thread implements Runnable {
    private SemaforoMutex mutex;
    private String threadProcess;
    public Thread(SemaforoMutex printer, String threadProcess) {
        this.mutex = printer;
        this.threadProcess = threadProcess;
    }
    @Override
    public void run() {
        System.out.println(threadProcess + " sendo executada.");
        mutex.print(threadProcess);
    }
}
