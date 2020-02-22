#include <stdio.h>
#include <string.h>

int main(){
    FILE *cepBD, *cepBD2;
    char str[100];
    int i;

    cepBD = fopen("cep.txt","r");
    cepBD2 = fopen("cep_barra.txt","w");

    if(cepBD == NULL || cepBD2 == NULL){
        printf("\nErro ao abrir o arquivo para leitura/escrita!\n");
		exit(1);
    }

    while(1){
        fgets(str, 100, cepBD);
        if(feof(cepBD)) break;
        
        // Condicoes adicionadas para tratar \n perdidos no meio dos dados
        if(strcmp(str, "\n") != 0){
        
            for(i=0; i<strlen(str); i++){
                if(str[i] == '\t'){
                    if(i != strlen(str)-2) str[i] = '|';

                    else{
                        str[i] = '\n';
                        str[i+1] = '\0';
                    }
                    
                }
            }
            
            
            fprintf(cepBD2, str, 100);

        }

    }

    fclose(cepBD);
    fclose(cepBD2);
}