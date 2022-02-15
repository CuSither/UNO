package GameMechanics;

import Cards.Card;

import java.util.Vector;

/**
 * The GameMechanics.Player.GameMechanics.Player class is used to represent each player in the game.
 * Functionality exists to add and remove cards to the player's hand,
 * as well as search their hand for valid draw cards for stacking.
 */
public class Player {
    protected String playerName;
    protected Vector<Card> hand;
    public boolean isBot;
    public static int numBots = 0;

    /**
     * Initializes player with a specified name.
     * @param name
     */
    public Player(String name) {
        playerName = name;
        hand = new Vector<>();
        isBot = false;
    }

    /**
     * Called when a new card is added to the player's deck
     * @param card  Cards.Card being pushed
     */
    public void pushCard(Card card) {
        hand.add(card);
    }

    /**
     * Pops a card from the player's deck
     * @param idx   Index of card being removed
     * @return      Cards.Card that has been removed
     */
    public Card popCard(int idx) {
        Card card = hand.get(idx);
        hand.removeElementAt(idx);
        return card;
    }

    /**
     * Tests if the player has a draw card that can be stacked with the currently played card
     * @param card  The draw card that the function is searching for a match of
     * @return      Boolean representing whether the match has been found
     */
    public boolean parsePlayerHand(Card card) {
        for (int c = 0; c < hand.size(); c++) {
            if (card.getType() == hand.get(c).getType() &&
                    (card.getColor() == hand.get(c).getColor()
                            || hand.get(c).getColor() == Card.Colors.Undefined)
                                || hand.get(c).getType() == Card.Types.DrawTwo) {
                return true;
            }
        }
        return false;
    }

    /** Get name */
    public String getName() {
        return playerName;
    }
    /** Get hand size */
    public int getHandSize() { return hand.size(); }
    /** Get hand */
    public Vector<Card> getHand() {
        return hand;
    }

}
