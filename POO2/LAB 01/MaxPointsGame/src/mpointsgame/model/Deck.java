// File: cards2/Deck.java
// Description: A Deck is a particular kind of CardPile with 52 Cards in it.

package mpointsgame.model;


import mpointsgame.view.Card;

public class Deck extends CardPile {

    //============================================================== constructor
    /** Creates a new instance of Deck */
    public Deck() {

        for (Suit s : Suit.values()) {
            for (Face f : Face.values()) {
                Card c = new Card(f, s);
                c.turnFaceDown();
                this.push(c);
            }
        }
        shuffle();
    }
}
