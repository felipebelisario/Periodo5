// File  : cards2/CardPile.java
// Description: A pile of cards (can be used for a hand, deck, discard pile...)
//               Subclasses: Deck (a CardPile of 52 Cards)

package mpointsgame.model;

import java.util.*;

import mpointsgame.view.Card;

public class CardPile implements Iterable<Card> {
    //======================================================= instance variables
    private ArrayList<Card> _cards = new ArrayList<Card>(); // All the cards.

    private int player;

    //========================================================== pushIgnoreRules
    public void pushIgnoreRules(Card newCard) {
        _cards.add(newCard);
    }


    //===================================================================== push
    public void push(Card newCard) {
        _cards.add(newCard);
    }

    
    //================================================================== shuffle
    public void shuffle() {
        Collections.shuffle(_cards);
    }

    
    //================================================================= iterator
    public Iterator<Card> iterator() {
        return _cards.iterator();
    }

    
    //==================================================================== clear
    public void clear() {
        _cards.clear();
    }


    public void setPlayer(int p){
        this.player = p;
    }

    public int getPlayer(){
        return this.player;
    }
}