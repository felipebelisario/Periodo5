import javax.swing.*;

public abstract class Botoes extends JToggleButton {
    Mediator mediator;

    public Botoes(Mediator m){
        mediator = m;
    }

    public void send(boolean status, String label){
        mediator.send(status, label, this);
    }

    public Mediator getMediator(){
        return mediator;
    }

    public abstract void recieve(boolean Status);
}
