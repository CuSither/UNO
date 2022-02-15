package Cards;

/**
 * The Cards.ActionCard classes represents skip, reverse, and draw two cards.
 * Inherits from Cards.Card class.
 */
public class ActionCard extends Card {
    /**
     * Cards.ActionCard Constructor
     * @param initType  The card's Cards.Card.Types type
     * @param initColor The card's Cards.Card.Colors color
     * @throws Exception
     */
    public ActionCard(Types initType, Colors initColor) {
        super(initType, initColor, -1);
    }

    /**
     * Not relevant for this class.
     * @param color change to color
     * @throws Exception
     */
    public void setColor(Colors color) {
        System.out.println("Only wild cards can have their colors changed after initialization");
    }
}
