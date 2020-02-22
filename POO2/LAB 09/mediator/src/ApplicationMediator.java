import javax.swing.*;
import java.util.ArrayList;

public class ApplicationMediator implements Mediator {
    private ArrayList<Botoes> botoes;
    private JLabel display;

    public ApplicationMediator(){
        botoes = new ArrayList<Botoes>();
    }

    public void addBotao(Botoes btn){
        botoes.add(btn);
    }

    public void addDisplay(JLabel label){
        display = label;
    }

    public void send(boolean status, String label, Botoes originator){
        for(Botoes botao: botoes){
            if(botao != originator){
                botao.recieve(status);
                display.setText(label);
            }
        }
    }
}
