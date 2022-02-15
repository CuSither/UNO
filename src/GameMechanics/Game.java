package GameMechanics;

import Cards.Card;
import Cards.WildCard;
import Cards.Card.Types;
import Piles.DiscardPile;
import Piles.DrawPile;


/**
 * The GameMechanics.Game class contains all the main functionality for the UNO game,
 * including the management of game and player states and the capacity
 * to correctly increment turns.
 */
public class Game {
    public DrawPile drawPile;
    public DiscardPile discardPile;
    public int numPlayers;
    public Player[] players;
    public Player curPlayer;
    public int p; /* index of current player */
    public int direction; /* determines whether the game is playing clockwise */
    public int drawType; /* -1, 2, or 4, depending on whether the top card is a draw card */
    public int drawStack; /* number of draw cards stacked on each other */
    public int mostRecentWildCardIdx;

    /**
     * GameMechanics.Game object constructor. This function initializes function parameters
     * and places an initial card on the drawPile.
     * @throws Exception    - Piles.DrawPile() may throw exception
     */
    public Game() throws Exception {
        drawPile = new DrawPile();
        discardPile = new DiscardPile();

        numPlayers = 0;
        players = new Player[10];
        p = 0;

        direction = 1;
        drawStack = 0;
        drawType = -1;

        discardPile.push(drawPile.pop()); /* add one card to the discard pile */

        /* ensure that the first card placed on the discard pile is not too funky */
        Types topCardType = discardPile.peek().getType();
        while (topCardType != Types.Number) {
            System.out.println("Card drawn was of type " + topCardType.name() + ", redrawing first card");
            discardPile.push(drawPile.pop());
            topCardType = discardPile.peek().getType();
        }
    }

    /**
     *  Start each player with 7 cards
     */
    public void dealCards() {
        for (int i = 0; i < numPlayers; i++) {
            for (int k = 0; k < 7; k++) {
                players[i].pushCard(drawPile.pull(discardPile));
            }
        }
    }

    /***
     * Adds a new player to the game
     * @param newPlayer The player object being added
     */
    public void addPlayer(Player newPlayer) {
        players[numPlayers] = newPlayer;
        numPlayers++;
    }

    /**
     * Sets a wild card's color from a string
     * @param selectedCard  Wild card whose color is being changed
     * @param newColor      Color string representing new card color
     * @throws Exception
     */
    public void handleWildCards(WildCard selectedCard, String newColor) throws Exception {
        if ("RED".equals(newColor)) {
            selectedCard.setColor(Card.Colors.Red);
        } else if ("BLUE".equals(newColor)) {
            selectedCard.setColor(Card.Colors.Blue);
        } else if ("GREEN".equals(newColor)) {
            selectedCard.setColor(Card.Colors.Green);
        } else if ("YELLOW".equals(newColor)) {
                selectedCard.setColor(Card.Colors.Yellow);
        } else {
            throw new Exception("Invalid color");
        }
    }

    /**
     * places card on discardPile and accounts for any way in which the card changes the game mechanics
     * @param card  The card being placed on the discard pile
     */
    public void placeCard(Card card) {
        Card.Types type = card.getType();
        if (type == Card.Types.WildDrawFour) {
            drawType = 4;
            drawStack++;
        }
        else if (type == Card.Types.DrawTwo) {
            drawType = 2;
            drawStack++;
        }
        else {
            drawType = -1;
        }

        if (type == Card.Types.Reverse) {
            direction *= -1;
        }
        else if (type == Card.Types.Skip) {
            incTurn();
        }

        discardPile.push(card);
    }

    /**
     * Increments the turn based on whether a reverse card has been played or not
     */
    public void incTurn() {
        p = (((p + direction) % numPlayers) + numPlayers) % numPlayers;
        curPlayer = players[p];
    }
}
