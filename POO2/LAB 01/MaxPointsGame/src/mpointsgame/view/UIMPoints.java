// File  : mpointsgame/UIFreeSell.java
// Description: mpointsgame program.
//         Main program / JFrame.  Adds a few components and the
//         main graphics area, UICardPanel, that handles the mouse and painting.

package mpointsgame.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import mpointsgame.model.GameModel;

/////////////////////////////////////////////////////////////// class UIFreeSell
public class UIMPoints extends JFrame {
    //=================================================================== fields
    private GameModel _model = new GameModel();
    
    private UICardPanel _boardDisplay;

    
    //===================================================================== main
    public static void main(String[] args) {
        //... Do all GUI initialization on Event Dispatching Thread.  This is the
        //    correct way, but is often omitted because the other
        //    almost always(!) works.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UIMPoints();
            }
        });
    }
    
    //============================================================== constructor
    public UIMPoints() {
        _boardDisplay = new UICardPanel(_model);

        //... Create button and check box.
        JButton newGameBtn = new JButton("New Game");
        newGameBtn.addActionListener(new ActionNewGame());
        
        //... Do layout
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(newGameBtn);

        //... Create content pane with graphics area in center (so it expands)
        JPanel content = new JPanel();
        JPanel players = new JPanel(new FlowLayout());


        JLabel player1 = new JLabel(_model.getP1Name() + " : Player 1 (P1)");
        JLabel player2 = new JLabel("       ||       " + _model.getP2Name() + " : Player 2 (P2)");



        player1.setFont(new Font("Times New Roman",Font.BOLD,25));
        player1.setForeground(Color.black);
        player2.setFont(new Font("Times New Roman",Font.BOLD,25));
        player2.setForeground(Color.black);

        player2.setBounds(10,40,100,35);

        players.add(player1);
        players.add(player2);


        content.setLayout(new BorderLayout());
        content.add(controlPanel, BorderLayout.NORTH);
        content.add(_boardDisplay, BorderLayout.CENTER);
        content.add(players, BorderLayout.PAGE_END);

        //... Set this window's characteristics.
        setContentPane(content);
        setTitle("20PointsGame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }
    
    ////////////////////////////////////////////////////////////// ActionNewGame
    class ActionNewGame implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            _model.reset();
        }
    }
    
}
