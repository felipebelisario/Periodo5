// File   : GameModel.java
// Purpose: blah, blah, blah
// Author : Fred Swartz - February 20, 2007 - Placed in public domain.

package mpointsgame.model;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import mpointsgame.view.Card;

//////////////////////////////////////////////////////////////// Class GameModel
public class GameModel implements Iterable<CardPile> {

    //=================================================================== fields
    private CardPile[] _tableau;

    private ArrayList<CardPile> _allPiles;

    private ArrayList<ChangeListener> _changeListeners;

    private int P1_POINTS = 0;
    private int P2_POINTS = 0;
    private String P1_NAME;
    private String P2_NAME;
    private int PLAYER_ATUAL = 1;
    private int maxPoints = 0;
    private int maxCards = 20;
    JLabel turn = new JLabel("Turn 1!");
    JLabel sc1 = new JLabel("0");
    JLabel sc2 = new JLabel("0");

    //============================================================== constructor
    public GameModel() {
        _allPiles = new ArrayList<CardPile>();

        _tableau = new CardPile[2];

        turn.setFont(new Font("Times New Roman",Font.BOLD,50));
        sc1.setFont(new Font("Times New Roman",Font.BOLD,50));
        sc2.setFont(new Font("Times New Roman",Font.BOLD,50));

        sc1.setForeground(Color.red);
        sc2.setForeground(Color.red);

        //... Arrange the cards into piles.
        int playerCount = 1;
        for (int pile = 0; pile < _tableau.length; pile++) {
            _tableau[pile] = new CardPile();
            _tableau[pile].setPlayer(playerCount);
            playerCount++;
            _allPiles.add(_tableau[pile]);
        }

        _changeListeners = new ArrayList<ChangeListener>();

        reset();
    }

    //==================================================================== reset
    public void reset() {
        Deck deck = new Deck();
        deck.shuffle();

        this.setAt(1);
        this.setP1(0);
        this.setP2(0);

        this.setTurn("Turn 1!", Color.red);
        this.getTurn().setBounds(150, 70, 310, 60);
        this.sc1.setText("0");
        this.sc2.setText("0");

        this.setMaxCards(20);


        //... Empty all the piles.
        for (CardPile p : _allPiles) {
            p.clear();
        }

        //... Deal the cards into the piles.
        int whichPile = 0;
        int cont = 0;
        for (Card crd : deck) {
            _tableau[whichPile].pushIgnoreRules(crd);
            whichPile = (whichPile + 1) % _tableau.length;

            cont = cont + 1;

            if (cont == 20) {
                break;
            }
        }


        //... Tell interested parties (eg, the View) that things have changed.
        _notifyEveryoneOfChanges();
    }

    //TODO: This is a little messy right now, having methods that both 
    //      return a pile by number, and the array of all piles.
    //      Needs to be simplified.

    //================================================================= iterator
    public Iterator<CardPile> iterator() {
        return _allPiles.iterator();
    }

    //=========================================================== getTableauPile
    public CardPile getTableauPile(int i) {
        return _tableau[i];
    }

    //========================================================== getTableauPiles
    public CardPile[] getTableauPiles() {
        return _tableau;
    }


    //======================================================== addChangeListener
    public void addChangeListener(ChangeListener someoneWhoWantsToKnow) {
        _changeListeners.add(someoneWhoWantsToKnow);
    }

    //================================================= _notifyEveryoneOfChanges
    private void _notifyEveryoneOfChanges() {
        for (ChangeListener interestedParty : _changeListeners) {
            interestedParty.stateChanged(new ChangeEvent("Game state changed."));
        }
    }


    public void setP1(int x) {
        this.P1_POINTS = x;
    }

    public void setP2(int y) {
        this.P2_POINTS = y;
    }

    public int getP1() {
        return this.P1_POINTS;
    }

    public int getP2() {
        return this.P2_POINTS;
    }

    public void setAt(int x) {
        this.PLAYER_ATUAL = x;
    }

    public int getAt() {
        return this.PLAYER_ATUAL;
    }

    public void setTurn(String x, Color font) {
        this.turn.setText(x);
        this.turn.setForeground(font);
    }

    public JLabel getTurn() {
        return this.turn;
    }

    public void setScore1() {
        this.sc1.setText(Integer.toString(this.getP1()));
    }

    public JLabel getScore1() {
        return this.sc1;
    }

    public void setScore2() {
        this.sc2.setText(Integer.toString(this.getP2()));
    }

    public JLabel getScore2() {
        return this.sc2;
    }

    public int getMaxP() {
        return this.maxPoints;
    }

    public void setMaxP(int x) {
        this.maxPoints = x;
    }

    public int getMaxCards() {
        return this.maxCards;
    }

    public void setMaxCards(int x) {
        this.maxCards = x;
    }

    public String getP1Name() {
        return this.P1_NAME;
    }

    public void setP1Name(String x) {
        this.P1_NAME = x;
    }

    public String getP2Name() {
        return this.P2_NAME;
    }

    public void setP2Name(String x) {
        this.P2_NAME = x;
    }

}