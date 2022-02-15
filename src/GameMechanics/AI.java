package GameMechanics;

import Cards.Card;
import Cards.Card.Colors;
import Cards.Card.Types;
import java.util.Vector;

/**
 * The AI class inherits from player, and adds
 * parameters and methods that implement functionality
 * for a basic, but fairly effective UNO bot.
 */
public class AI extends Player {
    public int[] numEachColor;
    public Vector<Vector<Card>> hands;
    public int[] colorHierarchy;
    public int totalNumCards;

    protected Vector<Card> redHand;
    protected Vector<Card> blueHand;
    protected Vector<Card> greenHand;
    protected Vector<Card> yellowHand;
    protected Vector<Card> wildHand;


    /**
     * AI constructor
     * @param name  name of the little botty boo
     */
    public AI(String name) {
        super(name);
        numEachColor = new int[]{ 0, 0, 0, 0 };
        totalNumCards = 0;

        redHand = new Vector<>();
        blueHand = new Vector<>();
        greenHand = new Vector<>();
        yellowHand = new Vector<>();
        wildHand = new Vector<>();
        hands = new Vector<>();
        hands.add(redHand);
        hands.add(blueHand);
        hands.add(greenHand);
        hands.add(yellowHand);
        hands.add(wildHand);

        colorHierarchy = new int[]{ 0, 1, 2, 3};
        isBot = true;
    }

    /**
     * Adds a card to the correct hand and manages the numEachColor array,
     * which is used to manage the color hierarchy.
     * @param card  Card being pushed
     */
    public void pushCard(Card card) {
        Colors color = card.getColor();

        if (color == Colors.Red) {
            ++numEachColor[0];
            redHand.add(card);
        }
        else if (color == Colors.Blue) {
            ++numEachColor[1];
            blueHand.add(card);
        }
        else if (color == Colors.Green) {
            ++numEachColor[2];
            greenHand.add(card);
        }
        else if (color == Colors.Yellow) {
            ++numEachColor[3];
            yellowHand.add(card);
        }
        else {
            wildHand.add(card);
        }

        totalNumCards++;
        setHierarchy();
    }

    /**
     * Chooses a card from the bot's hands to play.
     * This is where the color hierarchy is important.
     * Colors higher in the hierarchy are always prioritized,
     * in an attempt to keep the game's color state forever in
     * the bot's favor.
     * Note: this function removes the selected card from the bot's hand.
     * @param topCard   Top of the discard pile
     * @return          Card being played
     */
    public Card chooseCard(Card topCard) {
        Vector<Card> curHand;
        Card curCard;

        for (int i = 0; i < 4; i++) {
            curHand = hands.get(colorHierarchy[i]);

            for (int j = 0; j < curHand.size(); j++) {
                curCard = curHand.get(j);

                if (assessValidity(topCard, curCard)) {
                    popCard(curHand, j);

                    return curCard;
                }
            }
        }

        if (wildHand.size() > 0) {
            curCard = wildHand.get(0);
            curCard.setColor(getColorFromIndex(colorHierarchy[0]));
            wildHand.remove(0);
            totalNumCards--;

            return curCard;
        }
        else {
            return null;
        }

    }

    /**
     * Similar to the assessValidity function in DiscardPile
     * @param topCard   top of discard pile
     * @param curCard   card being compared to topCard
     * @return          true or false depending on card validity
     */
    public boolean assessValidity(Card topCard, Card curCard) {
        Colors topColor = topCard.getColor();
        Colors curColor = curCard.getColor();
        Types topType;
        Types curType;

        if (topColor == curColor) {
            return true;
        }

        topType = topCard.getType();
        curType = curCard.getType();

        if (curType == Types.Number && curType == topType) {
            if (topCard.getNumber() == curCard.getNumber()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Removes a card from the bot's hand
     * @param curHand   The hand, defined by its color class, that the card is being drawn from
     * @param idx       The index of the card in curHand
     * @return          The Card object
     */
    public Card popCard(Vector<Card> curHand, int idx) {
        Card card = curHand.get(idx);
        int colorIdx = getColorIndex(card.getColor());

        curHand.removeElementAt(idx);
        numEachColor[colorIdx]--;
        totalNumCards--;

        setHierarchy();

        return card;
    }

    /**
     * Rewrites the colorHierarchy parameter.
     * The lower the index in colorHierarchy, the more cards there are
     * of that color, and the values in colorHierarchy represent the indexes
     * of that color in numEachColor (confusing, I know).
     */
    public void setHierarchy() {
        for (int j = 1; j < 3; j++) {
            for (int i = 3; i >= j; i--) {
                if (numEachColor[colorHierarchy[i]] > numEachColor[colorHierarchy[i - 1]]) {
                    int temp = colorHierarchy[i];
                    colorHierarchy[i] = colorHierarchy[i - 1];
                    colorHierarchy[i - 1] = temp;
                }
            }
        }
    }

    /**
     * Converts int (0-3) to Colors object
     * @param i index
     * @return  Colors object
     */
    public Colors getColorFromIndex(int i) {
        return switch (i) {
            case 0 -> Colors.Red;
            case 1 -> Colors.Blue;
            case 2 -> Colors.Green;
            case 3 -> Colors.Yellow;
            default -> Colors.Undefined;
        };
    }

    /**
     * Converts Colors object to int (0-3)
     * @param c Colors object
     * @return  index
     */
    public int getColorIndex(Colors c) {
        return switch (c) {
            case Red -> 0;
            case Blue -> 1;
            case Green -> 2;
            case Yellow -> 3;
            default -> -1;
        };
    }

    /**
     * overrides Player's getHandSize
     * @return hand size
     */
    public int getHandSize() { return totalNumCards; }
}
