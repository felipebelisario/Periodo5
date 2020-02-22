#include <bits/stdc++.h>
#include <iostream>
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

using namespace std;

// utilize um tamanho de histograma adequado para a quantidade de buckets para os conjuntos de dados fornecidos
#define TAM_HISTOGRAMA 50
int histograma[TAM_HISTOGRAMA];
    
void print_histograma(){
    printf("\n\nHistograma:\n\n");

    for(int i=0;i<TAM_HISTOGRAMA;i++){
        printf("[%d] = %d\n",i,histograma[i]);
    }
}


int getHash(char *str) {
    
    // implementar funcao hash para distribuir strings com uniformidade e aleatoriedade
    int tam = strlen(str);
    int i, valor = 7;

    unsigned long long int pos=0;
    for(int i=0; i<tam; i++){
        pos = 31 * valor + (int) str[i];    // aqui eh feito o hashing para calculo da posicao
    }                                       
    
    if(histograma[pos % TAM_HISTOGRAMA]>=200){      // se >=200 incrementa aleatoriamente o valor
        while(1){                                   // da posicao ate encontrar <200 (no histograma)
            int incremento = rand()%TAM_HISTOGRAMA; 
            
            if(histograma[(pos+incremento) % TAM_HISTOGRAMA]<200){
                pos += incremento;
                break;
            }
        }
    }
    
    return pos % TAM_HISTOGRAMA;
}

int main() {
    FILE *f = fopen("cidadesbr.txt","rb");
    if (f == NULL) {
        printf("Sinto muito, nao consegui abrir arquivo de dados.");
        return 0;
    }

    srand (time(NULL));
    char linha[1024];

    // vetor que armazenara histograma de contagem
    for (int i = 0; i < TAM_HISTOGRAMA; i++)
        histograma[i] = 0;

    int contagem = 0;
    while (true) {
        // ler linha
        fgets(linha,1024,f);
        if (feof(f))
            break;

        // computar hash e atualizar contagem no histograma
        int hash = getHash(linha);
        histograma[hash]++;

        // para fornecer algum feedback para usuario nao pensar que programa travou, imprimir um caractere a cada 1000 elementos
        contagem++; if (contagem % 1000 == 0) { printf("."); fflush(stdout); }
    }
    fclose(f);

    print_histograma();

    // criar grafico de contagem do hash
    FILE *grafico = fopen("grafico.plt","wb");
    fprintf(grafico,"# executar esse arquivo com GNUPLOT\n");
    fprintf(grafico,"# http://www.gnuplot.info/download.html\n");
    fprintf(grafico,"# no linux: gnuplot grafico.plt\n");
    fprintf(grafico,"# no windows: wgnuplot grafico.plt\n");
    fprintf(grafico,"set title \"Histograma\"\n");
    fprintf(grafico,"set encoding iso_8859_1\n");
    fprintf(grafico,"set xlabel \"Hash\"\n");
    fprintf(grafico,"set ylabel \"Quantidade\"\n");
    fprintf(grafico,"plot '-' title 'Quantidade' with linespoints linewidth 2 linetype 1 pointtype 1\n");
    for (int i = 0; i < TAM_HISTOGRAMA; i++)
        fprintf(grafico,"%d %d\n",i,histograma[i]);
    fprintf(grafico,"end\n");
    fprintf(grafico,"pause -1\n");
    fclose(grafico);

    printf("\n\nThat's all, folks!\n\n");

    return 0;
}
