 #include <pthread.h>
 #include <stdio.h>
 #include <stdlib.h>
 #include <errno.h>
 #define FALSE 0
 #define TRUE 1

static volatile int count = 0;
static int vez = 0;
int processos[2];

//Inspirado na solução de Peterson
void secao_critica(long id)
{
   printf("Entrando na secao critica com vez = %d e thread %d \n", vez, id);
   
   int outro_processo;
   outro_processo = 1 - id;
   processos[id] = TRUE;
   vez = id;
   //while(vez == id && processos[outro_processo] == FALSE);
      
   if(count == 100)
   {
      printf("End \n");
      exit(0);
   } 
   count++;
   printf("count atual = %d  \n", count);

}

void secao_ncritica(long id)
{
   printf("Entrando na secao nao critica com vez: %d e thread: %d \n", vez,id);
   processos[id] = FALSE;
}

//Thread que faz somas
 void *thread1(void *thread_id)
 {
    intptr_t meu_id = 0;
    intptr_t meu_id_ptr = (intptr_t)(void *)meu_id;

    intptr_t outro = 1;
    intptr_t outro_ptr = (intptr_t)(void *)outro;

    intptr_t tid= (intptr_t)(void*)thread_id;

    printf("Hello World, Thread = %ld! \n", tid);
    
    while(1)
    {
        while(vez != meu_id)
        {
           //Esperando.....
        }
      //Entrando na região crítica:
      secao_critica(meu_id);
      
         int soma = 0;
         soma++;
         vez = outro;

      secao_ncritica(meu_id);
    }
 }

//Thread que faz multiplicações
 void *thread2(void *thread_id)
 {
    //Usamos o tipo intptr_t para tratar as warnings que dava sobre diferença de tamanho entre int e void*. Continua não sendo ideal, mas para esses casos mais simples, o método quebra o galho.
    intptr_t meu_id = 1;
    intptr_t meu_id_ptr = (intptr_t)(void *)meu_id;

    intptr_t outro = 0;
    intptr_t outro_ptr = (intptr_t)(void *)outro;

    intptr_t tid= (intptr_t)(void*)thread_id;

    printf("Hello World, Thread = %ld! \n", tid);
    
    while(1)
    {
        while(vez != meu_id)
        {
           //Aguardando....
        }
      secao_critica(meu_id);

         int multipliquei = 1;
         multipliquei  = multipliquei * rand()%4;
         vez = outro;

      secao_ncritica(meu_id);
    }
 }

 int main (int argc, char *argv[])
 {
    pthread_t th1,th2;
    int instancia1,instancia2,instancia3;
    printf("inicio do count: %d \n",count);
    instancia1 = pthread_create(&th1, NULL, thread1,(void *)1);
    instancia2 = pthread_create(&th2, NULL, thread2,(void *)2);
    pthread_join(th1, NULL);
    pthread_join(th2, NULL);

    if (instancia1)
    {
        printf("ERROR; exit code from pthread_create() is %d\n", errno);
        exit(-1);
    }
   
    printf("fim do count: %d \n",count);
   /* Funcao de saida do Main */
   exit(0);
 }
