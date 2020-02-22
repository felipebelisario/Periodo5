import javax.swing.*;

public class ClientMediator extends JFrame {

    public ClientMediator(ConcreteBotoes b1, ConcreteBotoes b2, ConcreteBotoes b3, JLabel display) {
        JPanel p = new JPanel();
        p.add(b1);
        p.add(b2);
        p.add(b3);
        getContentPane().add(display, "North");
        getContentPane().add(p, "South");
        setSize(400, 200);
        setVisible(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        b1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(!b1.isSelected()) b1.send(false, "Just start...");
                else b1.send(false, "viewing...");
            }
        });

        b2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(!b2.isSelected()) b2.send(false, "Just start...");
                else b2.send(false, "booking...");
            }
        });

        b3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(!b3.isSelected()) b3.send(false, "Just start...");
                else b3.send(false, "searching...");
            }
        });
    }


    public static void main(String[] args) {
        ApplicationMediator mediator = new ApplicationMediator();

        ConcreteBotoes btn_view = new ConcreteBotoes(mediator, "View");
        ConcreteBotoes btn_book = new ConcreteBotoes(mediator, "Book");
        ConcreteBotoes btn_search = new ConcreteBotoes(mediator, "Search");
        JLabel display = new JLabel("Just start...");

        mediator.addDisplay(display);
        mediator.addBotao(btn_view);
        mediator.addBotao(btn_book);
        mediator.addBotao(btn_search);

        new ClientMediator(btn_view, btn_book, btn_search, display);


    }


}