package Cards;

/**
 * The Cards.WildCard class represents wild and wild draw four cards.
 */
public class WildCard extends Card {
    /**
     * Wild card constructor
     * @param initType  card type
     * @throws Exception
     */
    public WildCard(Types initType) {
        super(initType, Colors.Undefined, -1);
    }

    /**
     * Used to set the wild card's color once being played
     * @param setColor      new card color
     * @throws Exception    Exception is thrown if the wild card's color has already been set
     */
    public void setColor(Colors setColor) {
        if (color == Colors.Undefined) {
            color = setColor;
        }
    }


}
