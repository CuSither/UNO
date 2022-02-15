package Cards;

/**
 * This class represents numbered cards
 */
public class NumberCard extends Card {
    /**
     * Cards.NumberCard constructor
     * @param initColor     Cards.Card color
     * @param initNumber    Cards.Card number
     * @throws Exception
     */
    public NumberCard(Card.Colors initColor, int initNumber) throws Exception {
        super(Types.Number, initColor, initNumber);
    }

    /**
     * Not relevant to number cards
     * @param color     New card color.
     * @throws Exception
     */
    public void setColor(Colors color) {
        System.out.println("Only wild cards can have their colors changed after initialization");
    }
}
