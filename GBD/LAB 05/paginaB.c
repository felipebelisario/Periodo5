#include <stdbool.h>
#include <stdlib.h>
#include "paginaB.h"
#include "arvoreB.h"

int paginaB_tamanho(int ordem) {
    return sizeof(struct paginaB) + (ordem-1) * sizeof(Par);
}

PaginaB paginaB_cria(int ordem, bool folha) {
    PaginaB P = (PaginaB) malloc(paginaB_tamanho(ordem));
    P->numeroElementos = 0;
    P->folha = folha;
    return P;
}

void paginaB_salva(ArvoreB arvore, PaginaB pagina, int idPagina) {
    fseek(arvore->arquivo, sizeof(arvore->cabecalho) + idPagina * paginaB_tamanho(arvore->cabecalho.ordem), SEEK_SET);
    fwrite(pagina, paginaB_tamanho(arvore->cabecalho.ordem), 1, arvore->arquivo);
}

PaginaB paginaB_carrega(ArvoreB arvore, int idPagina) {
    PaginaB P = (PaginaB) malloc(paginaB_tamanho(arvore->cabecalho.ordem)); //TODO Vazamento
    fseek(arvore->arquivo, sizeof(arvore->cabecalho) + idPagina * paginaB_tamanho(arvore->cabecalho.ordem), SEEK_SET);
    fread(P, paginaB_tamanho(arvore->cabecalho.ordem), 1, arvore->arquivo);
    return P;
}

Par paginaB_insere(ArvoreB arvore, int idPagina, Par par) {
    PaginaB pagina = paginaB_carrega(arvore, idPagina);
    int ordem = arvore->cabecalho.ordem;
    if(!pagina->folha) { //Nó não é folha
        int i;
        for(i = 0; i < pagina->numeroElementos-1 && pagina->pares[i+1].chave < par.chave; i++);
        if(i == 0 && pagina->pares[i].chave > par.chave) {
            pagina->pares[i].chave = par.chave;
        }   
        par = paginaB_insere(arvore, pagina->pares[i].valor, par);
    }
    if(par.chave == -1) {
        paginaB_salva(arvore, pagina, idPagina);
        return par;
    }

    if(pagina->numeroElementos == ordem - 1) { //Página cheia
        PaginaB novaPagina = paginaB_cria(ordem, pagina->folha);
        if(par.chave > pagina->pares[ordem / 2-1].chave) { //Inserir par na nova página
            for(int i = 0; i < ordem/2-1; i ++) {
                novaPagina->pares[i] = pagina->pares[ordem/2 + i];
            }
            int i;
            for(i = ordem/2-1; i > 0 && novaPagina->pares[i-1].chave > par.chave; i--) {
                novaPagina->pares[i] = novaPagina->pares[i-1];
            }
            novaPagina->pares[i] = par;
        }else{
            for(int i = 0; i < ordem/2; i ++) {
                novaPagina->pares[i] = pagina->pares[ordem/2 + i - 1];
            }
            int i;
            for(i = ordem/2-1; i > 0 && pagina->pares[i-1].chave > par.chave; i--) {
                pagina->pares[i] = pagina->pares[i-1];
            }
            pagina->pares[i] = par;
        }
        pagina->numeroElementos = ordem/2;
        novaPagina->numeroElementos = ordem/2;
        paginaB_salva(arvore, novaPagina, arvore->cabecalho.numeroPaginas);
        arvore->cabecalho.numeroPaginas++;
        paginaB_salva(arvore, pagina, idPagina);
        return (Par) {novaPagina->pares[0].chave, arvore->cabecalho.numeroPaginas-1};
    }else{
        int i;
        for(i = pagina->numeroElementos; i > 0 && pagina->pares[i-1].chave > par.chave; i--) {
            pagina->pares[i] = pagina->pares[i-1];
        }
        pagina->pares[i] = par;
        pagina->numeroElementos++;
        paginaB_salva(arvore, pagina, idPagina);
        return (Par) {-1, -1};
    }
}

void paginaB_imprime(ArvoreB arvore, int idPagina) {
    PaginaB pagina = paginaB_carrega(arvore, idPagina);
    printf("Página com %d elementos:\n", pagina->numeroElementos);
    puts("┌──────┬──────┬──────┐\n│Índice│ Chave│ Valor│\n├──────┼──────┼──────┤");
    for(int i = 0; i < pagina->numeroElementos; i++) {
        printf("│%6d│%6d│%6d│\n", i, pagina->pares[i].chave, pagina->pares[i].valor);
    }
    puts("└──────┴──────┴──────┘");
}
