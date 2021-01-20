
// Usado para posix thread
#include <pthread.h>

// Lib padrão
#include <stdio.h>
#include <stdlib.h>

// Lib a ser implementada.
#include <errno.h>

// Metodo que a thread irá executar
 void *PrintHello(void *threadid)
 {
    //  thread ID
    long tid;
    tid = (long)threadid;
    printf("Hello World! Sou a thread %ld!\n", tid);
    
    //  pthread_exit termina a chamada da thread.
    pthread_exit(NULL);
 }

 int main (int argc, char *argv[])
 {
    // Numero de threads: 5
    pthread_t threads[5];
    // Status da thread: 0 == ok || != == erro
    int status;
    
    // Loopa de 0 a 4 para criar 4 threads
    long t;
    for(t=0; t<5; t++){
       printf("Criando thread %ld\n", t);
       /*
        pthread_create inicia uma nova thread no processo de chamada: 
            O primeiro argumento, thread, é um ponteiro para o tipo pthread_t no qual o id do thread é retornado. 
            O segundo argumento, attr, é um ponteiro para os atributos desejados e pode ser NULL se você estiver satisfeito com os atributos padrão. 
            O terceiro parâmetro, start_routine, é a função que o thread recém-criado começa a executar.
            O último parâmetro, arg, é um ponteiro e fornece uma maneira de passar dados para a função. 
        */
       status = pthread_create(&threads[t], NULL, PrintHello, (void *)t);
       
       //   pthread_create retorna 0 em sucesso e um != 0 em falha.
       if (status){
        //   Usando a lib errno.h
          printf("ERROR; retornou numero != 0: %d\n", errno);
          exit(-1);
       }
    }
    
    //  pthread_exit termina a chamada da thread.
    pthread_exit(NULL);
 }