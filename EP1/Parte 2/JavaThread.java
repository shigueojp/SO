public class JavaThread extends Thread {
    String name;

    // Cria construtor com parâmetro name
    public JavaThread(String name) {
        this.name = name;
    }

    // Onde ocorre a execução do codigo da thread
    public void run() {
        for(int j = 0; j < 5; j++) {
            // Loopa de 0 a até 4
            System.out.println("Hello World, sou a thread: "+ this.name + " com valor: " + j);
        }
    }

    public static void main(String args[]) {
        // Executa 3 threads como exemplo
        (new JavaThread("1")).start();
        (new JavaThread("2")).start();
        (new JavaThread("3")).start();
    }
}