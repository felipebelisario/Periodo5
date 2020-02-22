/*
 * File:   main.cpp
 * Author: humberto
 *
 * Created on September 12, 2019
 * 
 * AVISO: Ola Humberto tudo bem? Esse foi o metodo mais eficiente que consegui fazer para
 * poder testar o codigo, pois armazenar a lista invertida no disco deixava INCRIVELMENTE lenta a
 * compilacao sendo impossivel o teste. Ainda sim essa versao ficou bem lenta mas em comparacao
 * com o acesso a um arquivo em disco, implementado anteriormente, esta um pouco mais rapido, 
 * agradeco a compreensao.
 * 
 * PS: O codigo faz todo o sentido para mim mas nao sei o motivo do nao funcionamento.
 * 
 */
#include <iostream>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fstream>
#include <list>

using namespace std;

// remove pontuacao de uma palavra
void removePontuacao (string palavra) {
    int length = palavra.size();
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
    // construtor (cria cabecalho da lista dinamica)
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
    int adiciona(string palavra, int offset) {
        
        

    }

    // realiza busca, retornando vetor de offsets que referenciam a palavra
    list<int> busca(string palavra, int *quantidade) {
        // substituir pelo resultado da busca na lista invertida
        quantidade[0] = 0;
        list<int> offs;
        list<LI>::iterator i;
        list<int>::iterator j;
        
        for(i = words.begin(); i!=words.end();i++){
		//printa os numeros pares começando do inicio da lista

            if(palavra.compare((*i).palavra) == 0){
                for(j = (*i).offsets.begin(); j!=(*i).offsets.end();j++){
                    offs.push_back(*j);
                    quantidade[0]++;
                }
            }
        }

        return offs;
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
        int cont = 0;
        string palavra;
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
                //cout << palavra << " " << offset << "\n"; fflush(stdout); // debug :-)
                lista.adiciona(palavra, offset);
                contadorDePalavras++;
                if (contadorDePalavras % 1000 == 0) { printf("."); fflush(stdout); }
            }
            
        }
        in.close();
        list<int>::iterator i;

        // agora que ja construimos o indice, podemos realizar buscas
        do {
            printf("\nDigite a palavra desejada ou \"SAIR\" para sair: ");
            
            cin >> palavra;
            
            if (palavra.compare("SAIR") != 0) {
                
                int quantidade;
                
                list<int> offsets = lista.busca(palavra,&quantidade);
                
                if (quantidade > 0) {
                    for (i = offsets.begin(); i!= offsets.end(); i++)
                        imprimeLinha(*i);
                }
                else
                    printf("nao encontrou %s\n",palavra);
            }
        } while (palavra.compare("SAIR") != 0);

        printf("\n\nAte mais!\n\n");
    }

    return (EXIT_SUCCESS);
}

