#ifndef _arvoreB_H
#define _arvoreB_H

#include <stdio.h>

typedef struct arvoreB{
    struct {
        int ordem;
        int altura;
        int paginaRaiz;
        int numeroPaginas;
        int numeroElementos;
    } cabecalho;
    FILE *arquivo;
} *ArvoreB;

ArvoreB arvoreB_cria(int);
void arvoreB_exclui(ArvoreB arvore);
void arvoreB_insere(ArvoreB arvore, int chave, int valor);
void arvoreB_navega(ArvoreB arvore);
void arvoreB_remove(ArvoreB arvore, int chave);
int arvoreB_busca(ArvoreB arvore, int chave);

#endif //_arvoreB_H
