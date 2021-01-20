#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
// Lib a ser implementada.
#include <errno.h>

static volatile int counter = 0;
static int vez = 0;

void secao_critica()
{
    printf("Sessao Critica: Executando sessao critica através da thread %d \n", vez);
    //  Saindo quando counter acima ou igual a 10000.
    if (counter >= 10000) exit(0);
    counter++;
    printf("Contador: %ld \n", (long) counter);
    
    
}

void secao_nao_critica()
{
    printf("Seccao Nao Critica: Executando sessao não critica através da thread %d \n", vez);
}

//
// mythread()
//
// Adicionar 1 no metodo seção critica na variavel count
void *mythread(void* arg)
{
  int meu_id = 0;
  int outro = 1;
  printf("Thread %ld: begin\n", (long) arg);
  
  while(1)
  {
    while(vez != meu_id)
    /*laço*/ ;
    secao_critica();
    vez = outro;
    secao_nao_critica();
  }
}

void *mythread2(void* arg)
{
  int meu_id = 1;
  int outro = 0;
  printf("Thread %ld: begin\n", (long) arg);
  while(1){
    while(vez != meu_id)
    /*laço*/ ;
    secao_critica();
    vez = outro;
    secao_nao_critica();
 }
}

int main(int argc, char*argv[])
{
  // Numero de threads: 2
  pthread_t p1, p2;
  
  // Status da thread: 0 == ok || != == erro
  int status, status2;
    
  printf("Classe main: Inicou com (counter = %d)\n", counter);
  status = pthread_create(&p1, NULL, mythread, (void *)1);
  status2 = pthread_create(&p2, NULL, mythread2, (void *)2);
  
  pthread_join(p1, NULL);
  pthread_join(p2, NULL);
  
  if (status) {
        //   Usando a lib errno.h
      printf("ERROR; retornou numero != 0: %d\n", errno);
      exit(-1);
    }
       
  printf("Classe main: Encerrou com (counter = %d)\n", counter);
  return 0;
}

