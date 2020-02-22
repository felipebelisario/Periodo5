/*
 * ALUNO: Wellington Cesar Dias Limass
 * File:   main.cpp
 * Created on October 24, 2019
 */

#include <stdlib.h>
#include <time.h>

#include "arvoreB.h"

int main(int argc, char** argv) {
    srand(time(NULL));
    ArvoreB arv = arvoreB_cria(256);

    /*for(int i = 1; i < 1000; i++) {
        arvoreB_insere(arv, i, i);
    }*/

    printf("Use essas chaves como exemplos de consultas que devem ser encontradas: ");
    for (int i = 0; i < 10000; i++) {
        int valor = rand() % 1000000 + 1;
        arvoreB_insere(arv, valor, valor+3);
        if (i % 1000 == 0)
            printf("%d, ", valor);
    }
    printf("\n");

    /*printf("\n\nEstatisticas:\n");
    printf("Numero de elementos: %d\n", arvoreB_getNumeroElementos());
    printf("Altura da arvore: %d\n", arvoreB_getAlturaArvore());
    printf("Taxa de ocupacao: %d%%\n", arvoreB_computarTaxaOcupacao());*/

    int opcao = 0, chave, valor;
    while (opcao != 5) {
        printf("\nMenu:\n1-Inserir\n2-Remover\n3-Buscar\n4-Navegar\n5-Sair\n");
        scanf("%d",&opcao);
        if(opcao < 4) {
            printf("Chave: ");
            scanf("%d",&chave);
        }
        switch(opcao) {
            case 1: //Inserção
                arvoreB_insere(arv, chave, chave+2);
                break;
            case 2: //Remoção
                arvoreB_remove(arv, chave);
                break;
            case 3: //Busca
                valor = arvoreB_busca(arv, chave);
                if (valor == -1)
                    printf("Chave %d nao encontrada.\n",chave);
                else
                    printf("Valor da chave %d: %d\n", chave, valor);
                break;
            case 4: //Navegação
                arvoreB_navega(arv);
                break;
            case 5: //Sair
                break;
            default:
                puts("Opção inválida.");
                break;
        }
    }

    arvoreB_exclui(arv);

    return (EXIT_SUCCESS);
}
