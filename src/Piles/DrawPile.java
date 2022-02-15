package Piles;

import Cards.ActionCard;
import Cards.Card;
import Cards.NumberCard;
import Cards.WildCard;

import java.util.Collections;

/**
 * The Piles.DrawPile class inherits from Piles.Pile and represents a draw pile in UNO.
 */
public class DrawPile extends Pile {

    /**
     * This constructor initializes an UNO draw pile, with 108 cards per the game's rules.
     * @throws Exception Cards.Card constructors can throw exceptions
     */
    public DrawPile() throws Exception {
        for (int i = 0; i < 4; i++) {
            Card newWild = new WildCard(Card.Types.Wild);
            Card newWDF = new WildCard(Card.Types.WildDrawFour);
            pile.push(newWild);
            pile.push(newWDF);

            Card.Colors color = Card.Colors.values()[i];

            Card newZero = new NumberCard(color, 0);
            pile.push(newZero);

            for (int j = 0; j < 2; j++) {
                for (int k = 1; k <= 9; k++) {
                    Card newNum = new NumberCard(color, k);
                    pile.push(newNum);
                }
                Card newSkip = new ActionCard(Card.Types.Skip, color);
                pile.push(newSkip);
                Card newReverse = new ActionCard(Card.Types.Reverse, color);
                pile.push(newReverse);
                Card newDrawTwo = new ActionCard(Card.Types.DrawTwo, color);
                pile.push(newDrawTwo);
            }
        }

        /* shuffle deck */
        Collections.shuffle(pile);
    }

    /**
     * Pops a card from the draw pile and refills it from a discard pile if necessary
     * @param discardPile   The discard pile that the GameMechanics.Game object is using, provided in case draw pile is empty
     * @return              Cards.Card that has been popped from the pile
     */
    public Card pull(DiscardPile discardPile) {
        if (size() < 1) {
            System.out.println("Draw pile is empty... reusing discard pile.");
            Card topCard = discardPile.pop();
            while (!discardPile.empty()) {
                pile.push(discardPile.pop());
            }
            Collections.shuffle(pile);
            discardPile.push(topCard);
        }
        return pile.pop();
    }

}
