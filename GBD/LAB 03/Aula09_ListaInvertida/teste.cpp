#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fstream>
#include <iostream>
#include <list>
#include <bitset>
#include <windows.h>

using namespace std;

typedef struct teste{
    list<int> it;
    string palavra;
}teste;

int main(){
    FILE *SecIndex;
    list<int>::iterator i;
    string word = " ";
    char letter = ' ';

    teste aux;
    teste aux2;

    char *oi =  new char[100];
    char *oi2;

    strcpy(oi,"AOPAAAAAAA");

    aux.it.push_back(1);
    aux.it.push_back(2);
    aux.it.push_back(3);

    aux.palavra = "teii";

    cout << aux.palavra;

    for(i = aux.it.begin(); i!=aux.it.end();i++){
		//printa os numeros pares começando do inicio da lista

            cout << *i << "\n";
    }

    SecIndex = fopen("secindex.txt","w+b");

    fwrite(&oi,sizeof(char*),1,SecIndex);

    fseek(SecIndex, 0, SEEK_SET);

    fread(&oi2,sizeof(char*),1,SecIndex);

    printf("%s\n", oi2);
    
    fclose(SecIndex);
    
}