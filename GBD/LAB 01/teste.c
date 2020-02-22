#include <stdio.h>

int main () {
   FILE *fp;
   long long int qnt;

   fp = fopen("indices.txt","r");
  
   fseek(fp, 0, SEEK_END);
 
   // pega a posição corrente de leitura no arquivo
   qnt = ftell(fp);
 
   // imprime o tamanho do arquivo
   printf("O arquivo %s possui %ld bytes", fp, qnt);
   
   return(0);
}