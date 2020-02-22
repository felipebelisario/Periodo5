public class ConcreteBotoes extends Botoes {

    public ConcreteBotoes(Mediator m, String name) {
        super(m);
        this.setText(name);
    }

    public void recieve(boolean status){
        this.setSelected(status);
    }
}
