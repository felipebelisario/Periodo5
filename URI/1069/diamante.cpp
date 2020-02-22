#include <iostream>
using namespace std;

int main(){
    string dima;
    int i, j, qnt, controle = 0, qnt_dima = 0;

    cin >> qnt;

    for(i=0; i < qnt; i++){
        cin >> dima;

        for(j=0; j < dima.length(); j++){
            if(dima[j] == '>'){
                controle--;

                if(controle >= 0){
                    qnt_dima++;
                }
                else{
                    controle = 0;
                }
            }
            
            if(dima[j] == '<') controle++;
        }

        cout << qnt_dima << endl;

        controle = 0;
        qnt_dima = 0;

    }


    return 0;

}