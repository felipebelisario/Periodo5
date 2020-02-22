#ifndef _paginaB_H
#define _paginaB_H

#include "arvoreB.h"

typedef struct{
    int chave;
    int valor;
} Par;

struct paginaB{
    int numeroElementos;
    bool folha;
    Par pares[];
};

typedef struct paginaB *PaginaB;

PaginaB paginaB_cria(int ordem, bool folha);
Par paginaB_insere(ArvoreB, int, Par);
PaginaB paginaB_carrega(ArvoreB arvore, int idPagina);
void paginaB_salva(ArvoreB arvore, PaginaB pagina, int idPagina);
void paginaB_imprime(ArvoreB arvore, int idPagina);

#endif //_paginaB_H
