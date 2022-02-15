package Cards;

/**
 * The Cards.Card class is an abstract class that encompasses Cards.NumberCard, Cards.WildCard, and Cards.ActionCard.
 * Every card played throughout the game is the type of one of Cards.Card's children.
*/
public abstract class Card {
    /** Types represents the different card types that exist in a standard game of UNO */
    public enum Types {Number, Skip, Reverse, DrawTwo, Wild, WildDrawFour};

    /** Colors represents the four UNO card colors,
     * plus Undefined to represent wild cards with undefined colors
    */
    public enum Colors {Red, Yellow, Green, Blue, Undefined};

    public Types type;
    public Colors color;
    public int number;
    public boolean neutralized; /* used with DrawTwo and WildDrawFour cards */

    /**
     * Constructor for the Cards.Card class. Initializes all parameters.
     * @param initType      The card's type
     * @param initColor     The card's color
     * @param initNumber    The card's number
     * @throws Exception
     */
    public Card(Card.Types initType, Card.Colors initColor, int initNumber) {
        type = initType;
        color = initColor;
        number = initNumber;
        neutralized = false;
    }

    /**
     * Returns a string that represents the card's parameters
     * @return String representation of card
     * @throws Exception
     */
    public String printCardName() {
        String cardStr;

        if (type == Card.Types.Number) {
            cardStr = (color.name() + " " + number);
        }
        else {
            cardStr = (color.name() + " " + type.name());
        }
        return cardStr;
    }

    /**
     * Implements the addition and subtraction custom rules
     * @param card      Cards.Card being added or subtracted to the existing card
     * @param arithType Either 1 or -1, representing addition or subtractions, respectively
     * @return          A new Cards.NumberCard object with original cards' color and an added/subtracted number,
     *                  or null if the cards are invalid
     * @throws Exception
     */
    public Card cardsArithmetic(Card card, int arithType) throws Exception {
        Card.Types cardType = card.getType();
        Card.Colors cardColor = card.getColor();

        if (type == Types.Number && cardType == type && cardColor == color) {
            int arithNumber = number + (card.getNumber() * arithType);
            return new NumberCard(color, arithNumber);
        }
        else {
            return null;
        }
    }

    /**
     * Used to prevent wild cards from recking havoc on multiple rounds
     */
    public void neutralize() {
        neutralized = true;
    }

    /**
     * Used to prevent wild cards from recking havoc on multiple rounds
     */
    public boolean isNeutralized() {
        return neutralized;
    }

    /** @return card type */
    public Types getType() { return type; }

    /** @return card color */
    public Colors getColor() { return color; }

    /** @return card number */
    public int getNumber() { return number; }

    /**
     * Compares two cards
     * @param card  Cards.Card to be compared with "this"
     * @return      Boolean value representing cards' equality
     */
    public boolean equals(Card card) {
        return (card.getType() == type && card.getColor() == color && card.getNumber() == number);
    }

    /**
     * Abstract function used in Cards.WildCard to set the card's color after being played.
     * @param color     New card color.
     * @throws Exception
     */
    public abstract void setColor(Colors color);

}