package com.usp.so.ep4.victor.okuhama;

enum StatusFilosofo {
    STARVING,
    COMENDO,
    PENSANDO
}

public class Filosofo implements Runnable {
    private final int id;
    private int talherDireita;
    private int talherEsquerda;
    private String status;
    private int count = 0;

//    Todo filosofo irá começar com status inicial de PENSANDO (em pegar o hashi)
//    Para contextualizar, existem 3 estados: FOME, COMENDO, PENSANDO
    public Filosofo(int id, int quantidadeDeFilosofos) {
        this.id = id;
        setTalheres(quantidadeDeFilosofos);
        this.status = StatusFilosofo.PENSANDO.toString();
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private void setTalheres(int quantidadeDeFilosofos) {
//      Felipe criar desenho
//      Talher 4 <- F5 -> Talher 3 -> F4 -> Talher 2 -> F3 -> Talher 1 -> F2 -> Talher 0  <- F1 -> Talher 4
        if (id == 1) {
            talherDireita = quantidadeDeFilosofos - 1;
        } else {
            talherDireita = id - 2;
        }
        talherEsquerda = id - 1;
    }

    private boolean tentarComer() throws InterruptedException {
        Hashi hashiDireita =  HashiSingleton.getHashis()[talherDireita];

        // Bloquear Hashi da direita via Semáforo Mutex
        hashiDireita.getSemaforo().acquire();

        // Se Hashi a Direita do filosofo estiver em uso, liberar Hashi da direita via Semáforo Mutex
        if (hashiDireita.getStatus().equals("USANDO")) {
            hashiDireita.getSemaforo().release();
            return false;
        }

        // Filosofo pegou o hashi da direita e libera o Hashi via Semáforo Mutex
        hashiDireita.setStatus("USANDO");
        hashiDireita.getSemaforo().release();

        // Filósofo Bloqueia o Hashi da esquerda via Semáforo Mutex
        Hashi hashiEsquerda = HashiSingleton.getHashis()[talherEsquerda];
        hashiEsquerda.getSemaforo().acquire();

        // Se alguém tiver usando Hashi da Esquerda, soltar Hashi Esquerda e o Hashi da direita via Semáforo MUTEX
        if (hashiEsquerda.getStatus() == "USANDO") {
            hashiEsquerda.getSemaforo().release();

            hashiDireita.getSemaforo().acquire();
            hashiDireita.setStatus("LIVRE");
            hashiDireita.getSemaforo().release();

            // Não pode pegar o hashi da esquerda, então, solta tambem o da direita.
            return false;
        }

        // Pegou os 2 hashis
        hashiEsquerda.setStatus("USANDO");
        hashiEsquerda.getSemaforo().release();

        return true;
    }

    // Após parar de comer seta como livre
    private void pararDeComer() throws InterruptedException {
        HashiSingleton.getHashis()[talherDireita].setStatus("LIVRE");
        HashiSingleton.getHashis()[talherEsquerda].setStatus("LIVRE");
    }

    @Override
    public void run() {
        // Cada filósofo irá tentar até 10 vezes
        while (count < 10) {
            try {
                // O filósofo vai tentar comer e fica  segundos aleatórios com fome
                System.out.println("Filosofo " + getId() + " com status: " + getStatus());
                setStatus(StatusFilosofo.STARVING.toString());
                System.out.println("Filosofo " + getId() + " com status: " + getStatus());
                java.lang.Thread.sleep((int) (Math.random() * 1000) + 500);
                if (tentarComer()) {
                    // Conseguiu pegar as duas talheres.
                    setStatus(StatusFilosofo.COMENDO.toString());
                    System.out.println("Filosofo " + getId() + " conseguiu pegar ambos hashi e mudou status para: " + getStatus());
                    // Tempo que o filósofo fica comendo e depois para de comer
                    java.lang.Thread.sleep((int) (Math.random() * 1000) + 500);
                    pararDeComer();
                    System.out.println("Filosofo " + getId() + " parou de comer e soltou ambos hashi");
                }
                setStatus(StatusFilosofo.PENSANDO.toString());
                System.out.println("Filosofo " + getId() + " mudou status para: " + getStatus());

                // Tempo que o filósofo fica pensando.
                java.lang.Thread.sleep((int) (Math.random() * 1000) + 500);
                count++;
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}

