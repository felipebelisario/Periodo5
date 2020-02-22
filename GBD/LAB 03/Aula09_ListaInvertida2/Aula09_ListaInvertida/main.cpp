/*
 * File:   main.cpp
 * Author: humberto
 *
 * Created on September 12, 2019
 */

#include <iostream>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fstream>

using namespace std;

// remove pontuacao de uma palavra
void removePontuacao (char *palavra) {
    int length = strlen(palavra);
    if (
        (palavra[length-1] == '.') || (palavra[length-1] == ',') || (palavra[length-1] == ';') ||
        (palavra[length-1] == ':') || (palavra[length-1] == '?') || (palavra[length-1] == '!')
       )
        palavra[length-1] = '\0';
}

// imprime linha do arquivo com base no offset da palavra
void imprimeLinha(int offset) {
    FILE *f = fopen("biblia.txt","rt");
    int pos = -1;
    char linha[2048];
    while (pos < offset) {
        fgets(linha,2047,f);
        pos = ftell(f);
    }
    printf("%s",linha);
    fclose(f);
}

// classe que implementa a lista invertida
class listaInvertida {
public:
    // construtor
    listaInvertida() {
        sec = fopen("secindex.txt","w+b");
        id = fopen("idlist.txt","w+b");
    }
    // destrutor
    ~listaInvertida() {
        fclose(sec);
        fclose(id);
    }
    // adiciona palavra na estrutura
    void adiciona(char *palavra, int offset) { 
        char *str = new char[100];
        char *pal = new char[100];
        char offset_id, offset_id_atual;
        fpos_t offset_id_aux;

        fgetpos(id,&offset_id_aux);
        offset_id_atual = offset_id_aux;

        while (!feof(sec)) {
            fgets(str,100,sec);
            
            pal = strtok(str," ");
            offset_id = atoi(strtok(NULL," "));

            if(strcmp(pal,palavra)==0){
                fwrite(&offset,sizeof(char),1,id);
                fwrite(&offset_id,sizeof(char),1,id);

            }
        }

        

        fwrite(&len,sizeof(char),1,sec);
        fprintf(sec,"%s",palavra);
        fwrite(&offset_id, sizeof(char),1,sec);

        offset_id = offset;

        fwrite(&offset,sizeof(char),1,id);
        


    }

    void print(){
        char *aux_print = new char[100], len;

        fseek(sec, 0, SEEK_SET);
        while(!feof(sec)){
            fread(&len, sizeof(char),1,sec);

            for (int i = 0; i < len; i++) {
                aux_print[i] = fgetc(sec);
                aux_print[i+1] = '\0';
            }

            cout << aux_print;
        }
    }
    // realiza busca, retornando vetor de offsets que referenciam a palavra
    int * busca(char *palavra, int *quantidade) {
        // substituir pelo resultado da busca na lista invertida
        quantidade[0] = 10;
        int *offsets = new int[10];
        int i = 0;
        // exemplo: retornar os primeiros 10 offsets da palavra "terra"
        offsets[i++] = 58;
        offsets[i++] = 69;
        offsets[i++] = 846;
        offsets[i++] = 943;
        offsets[i++] = 1083;
        offsets[i++] = 1109;
        offsets[i++] = 1569;
        offsets[i++] = 1792;
        offsets[i++] = 2041;
        offsets[i++] = 2431;
        return offsets;
    }
private:
    FILE *sec, *id;
};

// programa principal
int main(int argc, char** argv) {
    // abrir arquivo
    ifstream in("biblia.txt");
    if (!in.is_open()){
        printf("\n\n Nao consegui abrir arquivo biblia.txt. Sinto muito.\n\n\n\n");
    }
    else{
        // vamos ler o arquivo e criar a lista invertida com as palavras do arquivo
        char *palavra = new char[100];
        int offset, contadorDePalavras = 0;
        listaInvertida lista;
        // ler palavras
        while (!in.eof()) {
            // ler palavra
            in >> palavra;
            // pegar offset
            offset = in.tellg();
            // remover pontuacao
            removePontuacao(palavra);
            // desconsiderar palavras que sao marcadores do arquivo
            if (!((palavra[0] == '#') || (palavra[0] == '[') || ((palavra[0] >= '0') && (palavra[0] <= '9')))) {
                //printf("%d %s\n", offset,palavra); fflush(stdout); // debug :-)
                lista.adiciona(palavra, offset);
                contadorDePalavras++;
                if (contadorDePalavras % 1000 == 0) { printf(".");  fflush(stdout); }
            }
        }
        in.close();
        lista.print();

        // agora que ja construimos o indice, podemos realizar buscas
        do {
            printf("\nDigite a palavra desejada ou \"SAIR\" para sair: ");
            scanf("%s",palavra);
            if (strcmp(palavra,"SAIR") != 0) {
                int quantidade;
                int *offsets = lista.busca(palavra,&quantidade);
                if (quantidade > 0) {
                    for (int i = 0; i < quantidade; i++)
                        imprimeLinha(offsets[i]);
                }
                else
                    printf("nao encontrou %s\n",palavra);
            }
        } while (strcmp(palavra,"SAIR") != 0);

        printf("\n\nAte mais!\n\n");
    }

    return (EXIT_SUCCESS);
}

