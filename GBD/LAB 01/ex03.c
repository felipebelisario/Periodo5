// Executar primeiro "ex01.c" para ter acesso ao arquivo "cep_barra.txt"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct item {    
    int cep;    
    char uf[3];    
    char cidade[39];    
    char logradouro[67]; 
} item;

int main(){
    FILE *cepBD, *cepBD2, *indices;
    char str[100];
    char *value, *all_indices;
    int i = 0;
    struct item *it;
    int tamanhoArq, tamanhoArqIndices;
    fpos_t position;

	it = (struct item*)malloc(585882*sizeof(struct item));

    cepBD = fopen("cep_barra.txt","r");

    if(cepBD == NULL){
        printf("\nErro ao abrir o arquivo para leitura/escrita!\n");
		exit(1);
    }

    while(1){

        fgets(str, 100, cepBD);
        if(feof(cepBD)) break;

        it[i].cep = atoi(strtok(str,"|"));
        strcpy(it[i].uf, strtok(NULL,"|"));
        strcpy(it[i].cidade, strtok(NULL,"|"));
        
        // Alguns registros do arquivo possuem o campo "logradouro" nulo
        value = strtok(NULL, "|");
        if(value != NULL) {
            strcpy(it[i].logradouro, value);
            it[i].logradouro[strcspn(it[i].logradouro, "\n")] = 0;        
        }
        else{
            strcpy(it[i].logradouro, "NONE");
            it[i].cidade[strcspn(it[i].cidade, "\n")] = 0;
        }

        i++;
    }

    fclose(cepBD);

    indices = fopen("indices.txt","w+");
    cepBD2 = fopen("cep_struct.txt","w+");

    if(cepBD2 == NULL || indices == NULL){
        printf("\nErro ao abrir o arquivo para leitura/escrita!\n");
		exit(1);
    }

    fprintf(indices, "0 ");
    fprintf(cepBD2, "#");

    for(i=0;i<585882;i++){
        fprintf(cepBD2, "%d ", 12 + strlen(it[i].cidade) + strlen(it[i].uf) + strlen(it[i].logradouro));
        fprintf(cepBD2, "%d|", it[i].cep);
        fprintf(cepBD2, it[i].uf, 100);
        fprintf(cepBD2, "|");
        fprintf(cepBD2, it[i].cidade, 100);
        fprintf(cepBD2, "|");
        fprintf(cepBD2, it[i].logradouro, 100);
        fprintf(cepBD2, "|");

        fprintf(cepBD2, "#");

        fgetpos(cepBD2, &position);

        fprintf(indices, "%d ", position);
        
    }
   
 
    fclose(cepBD2);
    fclose(indices);
    
    free(it);
}