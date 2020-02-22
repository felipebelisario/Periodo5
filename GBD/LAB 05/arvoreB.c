#include <stdlib.h>
#include <stdbool.h>
#include "arvoreB.h"
#include "paginaB.h"

void arvoreB_salvaCabecalho(ArvoreB arvore) {
    fseek(arvore->arquivo, 0, SEEK_SET);
    fwrite(&arvore->cabecalho, sizeof(arvore->cabecalho), 1, arvore->arquivo);
}

void arvoreB_carregaCabecalho(ArvoreB arvore) {
    fseek(arvore->arquivo, 0, SEEK_SET);
    fread(&arvore->cabecalho, sizeof(arvore->cabecalho), 1, arvore->arquivo);
}

ArvoreB arvoreB_cria(int ordem) {
    ArvoreB A = (ArvoreB) malloc(sizeof(struct arvoreB));
    A->arquivo = fopen("arvoreB.dat", "r+");
    if(A->arquivo != NULL) { //Arquivo já existe, nada a ser feito.
        printf("Arvore carregada.\n");
        return A;
    }
    A->arquivo = fopen("arvoreB.dat", "w+");
    A->cabecalho.altura = 0;
    A->cabecalho.ordem = ordem;
    A->cabecalho.paginaRaiz = -1;
    A->cabecalho.numeroPaginas = 0;
    A->cabecalho.numeroElementos = 0;
    arvoreB_salvaCabecalho(A);
    printf("Arvore criada.\n");
    return A;
}

void arvoreB_exclui(ArvoreB arvore) {
    fclose(arvore->arquivo);
    free(arvore);
}

void arvoreB_insere(ArvoreB arvore, int chave, int valor) {
    arvoreB_carregaCabecalho(arvore);
    if(arvore->cabecalho.paginaRaiz == -1) { //Árvore vazia.
        PaginaB pagina = paginaB_cria(arvore->cabecalho.ordem, true);
        paginaB_salva(arvore, pagina, 0);
        arvore->cabecalho.altura = 1;
        arvore->cabecalho.paginaRaiz = 0;
        arvore->cabecalho.numeroPaginas = 1;
        arvore->cabecalho.numeroElementos = 0;
    }
    Par p = paginaB_insere(arvore, arvore->cabecalho.paginaRaiz, (Par) {chave, valor});
    if(p.chave != -1) { //Raiz transbordou
        PaginaB novaPagina = paginaB_cria(arvore->cabecalho.ordem, false);
        PaginaB raiz = paginaB_carrega(arvore, arvore->cabecalho.paginaRaiz);
        novaPagina->numeroElementos = 2;
        novaPagina->pares[0] = (Par) {raiz->pares[0].chave, arvore->cabecalho.paginaRaiz};
        novaPagina->pares[1] = p;
        paginaB_salva(arvore, novaPagina, arvore->cabecalho.numeroPaginas);
        arvore->cabecalho.paginaRaiz = arvore->cabecalho.numeroPaginas;
        arvore->cabecalho.numeroPaginas++;
        arvore->cabecalho.altura++;
    }
    arvore->cabecalho.numeroElementos++;
    arvoreB_salvaCabecalho(arvore);
}

void arvoreB_navega(ArvoreB arvore) {
    arvoreB_carregaCabecalho(arvore);
    printf("Altura: %d\nElementos: %d\nPáginas: %d\nOrdem: %d\nRaiz: %d\n",
        arvore->cabecalho.altura, arvore->cabecalho.numeroElementos,
        arvore->cabecalho.numeroPaginas, arvore->cabecalho.ordem,
        arvore->cabecalho.paginaRaiz);
    puts("Página raiz:");
    paginaB_imprime(arvore, arvore->cabecalho.paginaRaiz);
    int proxPagina;
    printf("Próxima página: ");
    scanf("%d", &proxPagina);
    while(proxPagina != -1) {
        paginaB_imprime(arvore, proxPagina);
        printf("Próxima página: ");
        scanf("%d", &proxPagina);
    }
}
  
void arvoreB_remove(ArvoreB arvore, int chave) {
    printf("Não implementado!\n");
}

int arvoreB_busca(ArvoreB arvore, int chave) {
    arvoreB_carregaCabecalho(arvore);
    PaginaB pagina = paginaB_carrega(arvore, arvore->cabecalho.paginaRaiz);
    int i;
    for(i = 0; i < pagina->numeroElementos-1 && pagina->pares[i+1].chave <= chave; i++);

    while(!pagina->folha) {
        pagina = paginaB_carrega(arvore, pagina->pares[i].valor);
        for(i = 0; i < pagina->numeroElementos-1 && pagina->pares[i+1].chave <= chave; i++);
    }
    if(pagina->pares[i].chave == chave) return pagina->pares[i].valor;
    else return -1;
}
