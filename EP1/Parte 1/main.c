#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

int
main()
{
  // variável id
  int pid;
  
  /* 
  fork retorna:
  Valor Negativo: Retorna processo de falha da da filha.
  Zero: Retorna o processo da filha.
  Valor Positivo: Retorna um processo que representa o pai.
  */
    
  // Criação de um processo retorna na variável pid
  pid = fork();
  
  // pid < 0 => falha    
  if (pid== -1) {		
    perror("fork() failed");
    exit(1);
  }
    
  //  pid == 0 => Processo filho
  if (pid == 0) {
    /* child process */
    printf("Hello World filho! ");
  }
  else {
  // pid > 0 => Processo pai
    printf("World World pai!\n");
  }

  exit(0);
}