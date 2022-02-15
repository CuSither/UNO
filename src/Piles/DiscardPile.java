package Piles;

import Cards.Card;

/**
 * The Piles.DiscardPile class inherits from Piles.Pile, and is used to represent
 * the discard pile in a standard game of UNO.
 */
public class DiscardPile extends Pile {
    /**
     * This constructor calls the parent constructor.
     */
    public DiscardPile() {
        super();
    }

    /**
     * Compares the provided card with the top card on the discard pile.
     * @param card  Cards.Card to be compared against top card
     * @return      Boolean representing whether the play is valid
     */
    public boolean assessValidity(Card card) {
        Card.Types type = card.getType();
        Card curTop = pile.peek();

        /* This if statement forces the player to choose a draw card to stack on an existing one.
         * Whether the player has a valid draw card is decided before this function is called.
         */
        if ((curTop.getType() == Card.Types.DrawTwo || curTop.getType() == Card.Types.WildDrawFour) && !curTop.isNeutralized()) {
            return type == curTop.getType();
        }

        if (curTop.getColor() == Card.Colors.Undefined) {
            return true;
        }
        if (type == Card.Types.Wild || type == Card.Types.WildDrawFour) {
            return true;
        }

        if (card.getColor() == curTop.getColor()) {
            return true;
        }
        else if (type == Card.Types.Number) {
            return card.getNumber() == curTop.getNumber();
        }
        else return type == curTop.getType();
    }
}
