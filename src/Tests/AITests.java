package Tests;

import Cards.Card;
import Cards.Card.Colors;
import Cards.NumberCard;
import GameMechanics.AI;
import org.junit.Test;
import org.junit.Assert;

import java.util.Arrays;

public class AITests {

    /**
     * Asserts that setColorHierarchy works correctly when given 6 cards
     */
    @Test
    public void testSetHierarchy() throws Exception {
        AI bot0 = new AI("bot0");
        bot0.pushCard(new NumberCard(Colors.Red, 0));
        bot0.pushCard(new NumberCard(Colors.Red, 0));
        bot0.pushCard(new NumberCard(Colors.Blue, 0));
        bot0.pushCard(new NumberCard(Colors.Blue, 0));
        bot0.pushCard(new NumberCard(Colors.Blue, 0));
        bot0.pushCard(new NumberCard(Colors.Yellow, 0));
        bot0.setHierarchy();
        System.out.println("num each color: " + Arrays.toString(bot0.numEachColor));
        System.out.println("color hierarchy: " + Arrays.toString(bot0.colorHierarchy));
        Assert.assertEquals(true, Arrays.deepEquals(new Object[]{new int[]{ 1, 0, 3, 2 }}, new Object[]{bot0.colorHierarchy}));
    }
}
