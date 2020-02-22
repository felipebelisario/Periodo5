package mpointsgame.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.*;

import mpointsgame.model.CardPile;
import mpointsgame.model.GameModel;

//////////////////////////////////////////////////////////////// class UICardPanel

class UICardPanel extends JComponent implements
        MouseListener,
        MouseMotionListener,
        ChangeListener {
    //================================================================ constants
    private static final int NUMBER_OF_PILES = 2;
    
    private static final int TABLEAU_TOP = 80;
    private static final int TABLEAU_INCR_Y  = 80;
    private static final int TABLEAU_START_X = 180;
    private static final int TABLEAU_INCR_X  = 160;
    
    private static final int DISPLAY_WIDTH = 900;
    private static final int DISPLAY_HEIGHT = 500;

    int turnCont = 1;

    JLabel p1 = new JLabel("P1");
    JLabel p2 = new JLabel("P2");
    JLabel maxPointsLabel = new JLabel("Max points to win: ");
    JLabel info = new JLabel("Each player pick 1 card per turn, starting by player 1 (P1)");
    JLabel maxPointsQnt = new JLabel();


    private static final Color BACKGROUND_COLOR = new Color(0, 200, 0);
    
    //=================================================================== fields
    /** Initial image coords. */
    private int _initX     = 0;   // x coord - set from drag
    private int _initY     = 0;   // y coord - set from drag

    /** Position in image of mouse press to make dragging look better. */
    private int _dragFromX = 0;  // Displacement inside image of mouse press.
    private int _dragFromY = 0;

    //... Selected card and its pile for dragging purposes.
    private Card     _draggedCard = null;  // Current draggable card.
    private CardPile _draggedFromPile = null;  // Which pile it came from.
    
    //... Remember where each pile is located.
    private IdentityHashMap<CardPile, Rectangle> _whereIs =
            new IdentityHashMap<CardPile, Rectangle>();
    
    private GameModel _model;
    
    //============================================================== constructor
    /** Constructor sets size, colors, and adds mouse listeners.*/
    UICardPanel(GameModel model) {
        //... Save the model.
        _model = model;
        
        //... Initialize graphics
        setPreferredSize(new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));
        setBackground(Color.blue);

        
        //... Add mouse listeners.
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        
        //... Set location of all piles in model.
        int x = TABLEAU_START_X;   // Initial x position.
        for (int pileNum = 0; pileNum < NUMBER_OF_PILES; pileNum++) {
            CardPile p;

            p = _model.getTableauPile(pileNum);
            _whereIs.put(p, new Rectangle(x, TABLEAU_TOP, Card.CARD_WIDTH,
                    3 * Card.CARD_HEIGHT));
            
            x += TABLEAU_INCR_X;
        }
        
        //... Make sure model calls us whenever something changes.
        _model.addChangeListener(this);

        JPanel pane = new JPanel();
        JFrame frame = new JFrame("Insert information");
        pane.setLayout(new GridLayout(0, 2, 2, 2));

        JTextField p1field = new JTextField(5);
        JTextField p2field = new JTextField(5);
        JTextField pointsfield = new JTextField(5);

        while (_model.getMaxP() == 0 || _model.getMaxP() > 130 || _model.getP1Name() == "" || _model.getP2Name() == "") {

            pane.add(new JLabel("Player 1 name: "));
            pane.add(p1field);

            pane.add(new JLabel("Player 2 name: "));
            pane.add(p2field);

            pane.add(new JLabel("Enter max points to win (<= 130): "));
            pane.add(pointsfield);

            JOptionPane.showMessageDialog(frame, pane);

            _model.setP1Name(p1field.getText());
            _model.setP2Name(p2field.getText());
            _model.setMaxP(Integer.parseInt(pointsfield.getText()));
        }

        paintScore();
    }
    
    //=========================================================== paintComponent
    /** Draw the cards. */
    @Override
    public void paintComponent(Graphics g) {
        //... Paint background.
        int width  = getWidth();
        int height = getHeight();
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);   // Restore pen color.


        g.setFont(new Font("Times New Roman",Font.BOLD,50));

        g.drawString("P1", 10,240);
        g.drawString("P2", 10,400);


        for (CardPile pile : _model.getTableauPiles()) {
            _drawPile(g, pile);
        }
        
        //... Draw the dragged card, if any.
        if (_draggedCard != null) {
            _draggedCard.draw(g);
        }
    }
    
    //================================================================ _drawPile
    private void _drawPile(Graphics g, CardPile pile) {
        Rectangle loc = _whereIs.get(pile);

        int y = loc.y;
        for (Card card : pile) {
            if (card != _draggedCard) {
                //... Draw only non-dragged card.
                card.setPosition(y, loc.x);
                card.draw(g);
                y += TABLEAU_INCR_Y;
            }
        }

    }
    
    //============================================================= mousePressed
    public void mousePressed(MouseEvent e) {
        int x = e.getX();   // Save the x coord of the click
        int y = e.getY();   // Save the y coord of the click
        
        //... Find card image this is in.  Check top of every pile.
        _draggedCard = null;  // Assume not in any image.
        for (CardPile pile : _model) {
            for (Card testCard : pile) {
                if (testCard.isInside(x, y) && pile.getPlayer() == _model.getAt() && testCard.get_CardStatus() == false) {
                    _dragFromX = x - testCard.getX();  // how far from left
                    _dragFromY = y - testCard.getY();  // how far from top
                    _draggedCard = testCard;  // Remember what we're dragging.
                    _draggedFromPile = pile;

                    // Seleciona carta como ja selecionada por um player
                    testCard.set_CardStatus(true);
                    // Diminui uma carta das ainda nao selecionadas
                    _model.setMaxCards(_model.getMaxCards() - 1);

                    break;   // Stop when we find the first match.
                }
            }

        }
    }
    
    //============================================================= stateChanged
    // Implementing ChangeListener means we had to define this.
    // Because we added ourselves as a change listener in the model,
    // This method will be called whenever anything changes in the model.
    // All we have to do is repaint.
    public void stateChanged(ChangeEvent e) {
        _clearDrag();     // Perhaps not needed, but this makes sure.
        this.repaint();
    }
    
    //============================================================ mouseReleased
    // Funcionamento do score de cada player esta todo aqui
    public void mouseReleased(MouseEvent e) {
        //... Check to see if something was being dragged.
        if (_draggedFromPile != null) {
            int x = e.getX();
            int y = e.getY();


            _draggedCard.turnFaceUp();

            if (_model.getAt() == 1) {
                _model.setP1(_model.getP1() + _draggedCard.get_NUMBER());
                _model.setScore1();

                _model.setAt(2);

            }
            else {
                if (_model.getAt() == 2) {
                    _model.setP2(_model.getP2() + _draggedCard.get_NUMBER());
                    _model.setScore2();

                    if(_model.getP2() >= _model.getMaxP() && _model.getP1() < _model.getMaxP()) {
                        _model.setTurn("Player 2 wins!", Color.BLACK);
                        _model.setAt(0);
                        turnCont = 1;
                    }
                    else {
                        if(_model.getP1() >= _model.getMaxP() && _model.getP2() < _model.getMaxP()) {
                            _model.setTurn("Player 1 wins!", Color.black);
                            _model.setAt(0);
                            turnCont = 1;
                        }
                        else{
                            if(_model.getP1() >= _model.getMaxP() && _model.getP2() >= _model.getMaxP()) {
                                _model.setTurn("Draw!", Color.black);
                                _model.setAt(0);
                                turnCont = 1;
                            }
                            else{
                                if(_model.getP1() < _model.getMaxP() && _model.getP2() < _model.getMaxP() && _model.getMaxCards() == 0) {
                                    _model.setTurn("Max points not reached!", Color.black);
                                    _model.getTurn().setBounds(30, 70, 530, 60);
                                    _model.setAt(0);
                                    turnCont = 1;
                                }
                                else{
                                    turnCont++;
                                    _model.setTurn("Turn " + Integer.toString(turnCont) + "!", Color.red);
                                    _model.setAt(1);
                                }
                            }
                        }
                    }
                }
            }

            _clearDrag();
            this.repaint();
        }
    }

    
    //=============================================================== _clearDrag

    private void _clearDrag() {
        _draggedCard = null;
        _draggedFromPile = null;
    }


    // Pinta a tela inicial do jogo
    private void paintScore(){


        maxPointsLabel.setFont(new Font("Times New Roman",Font.BOLD,22));
        maxPointsLabel.setForeground(Color.black);
        maxPointsLabel.setBounds(100,10,200,35);

        this.add(maxPointsLabel,null);

        maxPointsQnt.setText(Integer.toString(_model.getMaxP()));
        maxPointsQnt.setFont(new Font("Times New Roman",Font.BOLD,22));
        maxPointsQnt.setForeground(Color.black);
        maxPointsQnt.setBounds(290,10,130,35);

        this.add(maxPointsQnt,null);

        p1.setFont(new Font("Times New Roman",Font.BOLD,50));
        p1.setForeground(Color.black);
        p1.setBounds(600,30,100,35);

        this.add(p1,null);


        _model.getScore1().setBounds(700,30,100,35);

        this.add(_model.getScore1(),null);

        p2.setFont(new Font("Times New Roman",Font.BOLD,50));
        p2.setForeground(Color.black);
        p2.setBounds(600,100,100,35);

        this.add(p2,null);

        _model.getScore2().setBounds(700,100,100,35);

        this.add(_model.getScore2(),null);

        if(_model.getTurn().equals("Max points not reached!") == false) {
            _model.getTurn().setBounds(150, 70, 310, 60);
        }
        this.add(_model.getTurn(),null);

        info.setFont(new Font("Times New Roman",Font.BOLD,18));
        info.setForeground(Color.black);
        info.setBounds(230,460,600,35);

        this.add(info,null);

        this.repaint();
    }
    
    //=============================================== Ignore other mouse events.
    public void mouseMoved  (MouseEvent e) {}   // ignore these events
    public void mouseEntered(MouseEvent e) {}   // ignore these events
    public void mouseClicked(MouseEvent e) {}   // ignore these events
    public void mouseDragged(MouseEvent e) {}
    public void mouseExited(MouseEvent e) { ; }
}
