function calculator(x) {

    if(x == "c"){
        zeraCalculator();
        return;
    }

    if(x == '+'){      // SOMA

        opWithMoreThan2();

        if(operando1 != "") {
            flag = 1;
            document.getElementById("panel").innerHTML += '+';
        }
        else{ 
            zeraCalculator();
            alert("Entrada inválida!");
            zeraCalculator();
        }

        return;
    }

    if(x == '-'){      // SOMA

        opWithMoreThan2();

        if(operando1 != "") {
            flag = 4;
            document.getElementById("panel").innerHTML += '-';
        }
        else{ 
            zeraCalculator();
            alert("Entrada inválida!");
            zeraCalculator();
        }

        return;
    }

    if(x == '*'){      // MULTIPLICACAO

        opWithMoreThan2();

        if(operando1 != "") {
            flag = 2;
            document.getElementById("panel").innerHTML += 'x';
        }
        else{ 
            zeraCalculator();
            alert("Entrada inválida!");
            zeraCalculator();
        }

        return;
    }

    if(x == '/'){      // DIVISAO
        
        opWithMoreThan2();

        if(operando1 != "") {
            flag = 3;
            document.getElementById("panel").innerHTML += '÷';
        }
        else{ 
            zeraCalculator();
            alert("Entrada inválida!");
            zeraCalculator();
        }

        return;
    }


    if(x == '='){      // IGUAL
        if(flag != 0 && operando2 != ""){

            if(flag == 1) resultado = parseFloat(operando1) + parseFloat(operando2);

            if(flag == 2) resultado = parseFloat(operando1) * parseFloat(operando2);
                
            if(flag == 3) resultado = parseFloat(operando1) / parseFloat(operando2);  
    
            if(flag == 4) resultado = parseFloat(operando1) - parseFloat(operando2);  

            zeraCalculator();


            
            document.getElementById("panel").innerHTML = resultado.toString();

            
            return;
        }
        else{ 
            zeraCalculator();
            alert("Entrada inválida!");
            zeraCalculator();
        }

        
    }
    
    if(flag == 0 && x != "." || flag == 0 && x == "." && operando1 != ""){
        if(counterOp1 < 16){
            operando1 += x;

            document.getElementById("panel").innerHTML += x;

            counterOp1++;

        }

        
    }
    if(flag >= 1 && x != "." || flag >=1 && x == "." && operando2 != ""){ 
        if(counterOp2 < 16){
            operando2 += x;

            document.getElementById("panel").innerHTML += x;

            counterOp2++;
        }
    }



    
    
}

function zeraCalculator(){
    operando1 = "";
    operando2 = "";
    flag = 0;
    counterOp1 = 0;
    counterOp2 = 0;

    document.getElementById("panel").innerHTML = "";
}

function opWithMoreThan2(){
    if(flag == 1){
        operando1 = parseFloat(operando1) + parseFloat(operando2);
        operando2 = "";
    }
    if(flag == 2){
        operando1 = parseFloat(operando1) * parseFloat(operando2);
        operando2 = "";
    }
    if(flag == 3){
        operando1 = parseFloat(operando1) / parseFloat(operando2);
        operando2 = "";
    }
    if(flag == 4){
        operando1 = parseFloat(operando1) - parseFloat(operando2);
        operando2 = "";
    }
}