package Tests;

import Cards.ActionCard;
import Cards.Card;
import Cards.NumberCard;
import Cards.WildCard;
import GameMechanics.Game;
import GameMechanics.Player;
import Piles.DiscardPile;
import Piles.DrawPile;
import org.junit.Test;
import org.junit.Assert;


public class Tests {
    Card card;
    Game game;
    Card.Colors color;
    Card.Types type;
    DiscardPile discardPile;
    DrawPile drawPile;
    Player player;
    Player player1;
    Player player2;

    @Test
    public void testCardConstructors() throws Exception {
        for (int i = 0; i < 4; i++) {
            color = Card.Colors.values()[i];
            for (int k = 0; k <= 9; k++) {
                card = new NumberCard(color, k);
                Assert.assertEquals(color, card.getColor());
                Assert.assertEquals(k, card.getNumber());
                Assert.assertEquals(Card.Types.Number, card.getType());
            }
            for (int k = 1; k < 6; k++) {
                type = Card.Types.values()[k];
                if (k <= 3) {
                    card = new ActionCard(type, color);
                    Assert.assertEquals(type, card.getType());
                    Assert.assertEquals(color, card.getColor());
                } else {
                    card = new WildCard(type);
                    Assert.assertEquals(type, card.getType());
                    Assert.assertEquals(Card.Colors.Undefined, card.getColor());
                }
            }
        }
    }

    @Test
    public void testCardAuxFunctions() throws Exception {
        card = new NumberCard(Card.Colors.Red, 0);
        Assert.assertEquals("Red 0", card.printCardName());
        card = new ActionCard(Card.Types.DrawTwo, Card.Colors.Red);
        Assert.assertEquals("Red DrawTwo", card.printCardName());

        card = new NumberCard(Card.Colors.Red, 4);
        Card card2 = new NumberCard(Card.Colors.Red, 5);
        Card arithCard = card.cardsArithmetic(card2, 1);
        Assert.assertEquals(9, arithCard.getNumber());
        card2 = new WildCard(Card.Types.Wild);
        arithCard = card.cardsArithmetic(card2, 1);
        Assert.assertNull(arithCard);
    }

    @Test
    public void testDiscardPile() throws Exception {
        drawPile = new DrawPile();
        discardPile = new DiscardPile();
        Assert.assertEquals(108, drawPile.size());

        for (int i = 0; i < 109; i++) {
            discardPile.push(drawPile.pull(discardPile));
        }

        Assert.assertEquals(106, drawPile.size());
        Assert.assertEquals(2, discardPile.size());

        card = new NumberCard(Card.Colors.Red, 0);
        discardPile.push(card);
        Assert.assertEquals(true, discardPile.assessValidity(card));
        card = new NumberCard(Card.Colors.Blue, 9);
        Assert.assertEquals(false, discardPile.assessValidity(card));
    }

    @Test
    public void testPlayer() throws Exception {
        player = new Player("Alex");
        card = new WildCard(Card.Types.WildDrawFour);
        player.pushCard(card);
        //player.pushCard(card);
        Assert.assertEquals("Alex", player.getName());
        Assert.assertEquals(true, player.parsePlayerHand(card));
        Assert.assertEquals(1, player.getHandSize());
        Assert.assertEquals(1, (player.getHand()).size());
        Assert.assertEquals(true, card.equals(player.popCard(0)));
        //player.popCard(0);
        Card numCard = new NumberCard(Card.Colors.Red, 0);
        player.pushCard(numCard);
        Assert.assertEquals(false, player.parsePlayerHand(card));
    }

    @Test
    public void testGameMechanics() throws Exception {
        game = new Game();
        player = new Player("Alex");
        player1 = new Player("Bob");
        player2 = new Player("Claudia");
        game.addPlayer(player);
        game.addPlayer(player1);
        game.addPlayer(player2);

        game.dealCards();

        for (int i = 0; i < 3; i++) {
            Player curPlayer = game.players[i];
            Assert.assertEquals(7, curPlayer.getHandSize());
        }

        card = new WildCard(Card.Types.Wild);
        Assert.assertThrows(Exception.class, () -> game.handleWildCards((WildCard)card, "sdasdsad"));
    }

}

//public class Tests.Tests {
//    @Test
//    public void testCardConstructors() throws Exception {
//        /* test card constructor exceptions */
//        Assert.assertThrows(InvalidCardParametersException.class, () -> new Cards.Card(Cards.Card.Types.Wild, Cards.Card.Colors.Red, 0));
//        Assert.assertThrows(InvalidCardParametersException.class, () -> new Cards.Card(Cards.Card.Types.WildDrawFour, Cards.Card.Colors.Red, 0));
//        Assert.assertThrows(InvalidCardParametersException.class, () -> new Cards.Card(Cards.Card.Types.Reverse, Cards.Card.Colors.Red, 0));
//        Assert.assertThrows(InvalidCardParametersException.class, () -> new Cards.Card(Cards.Card.Types.Skip, Cards.Card.Colors.Red, 0));
//        Assert.assertThrows(InvalidCardParametersException.class, () -> new Cards.Card(Cards.Card.Types.Wild, Cards.Card.Colors.Red));
//        Assert.assertThrows(InvalidCardParametersException.class, () -> new Cards.Card(Cards.Card.Types.WildDrawFour, Cards.Card.Colors.Red));
//        Assert.assertThrows(InvalidCardParametersException.class, () -> new Cards.Card(Cards.Card.Types.Number, Cards.Card.Colors.Red));
//        Assert.assertThrows(InvalidCardParametersException.class, () -> new Cards.Card(Cards.Card.Types.Number));
//        Assert.assertThrows(InvalidCardParametersException.class, () -> new Cards.Card(Cards.Card.Types.Skip));
//        Assert.assertThrows(InvalidCardParametersException.class, () -> new Cards.Card(Cards.Card.Types.DrawTwo));
//
//        /* test constructors and get functions */
//        Cards.Card.Types type;
//        Cards.Card.Colors color;
//        Cards.Card newCard;
//        for (int t = 0; t < 6; t++) {
//            type = Cards.Card.Types.values()[t];
//            if (type != Cards.Card.Types.Wild && type != Cards.Card.Types.WildDrawFour) {
//                for (int c = 0; c < 4; c++) {
//                    color = Cards.Card.Colors.values()[c];
//                    if (type == Cards.Card.Types.Number) {
//                        for (int n = 0; n <= 9; n++) {
//                            /* test number card */
//                            newCard = new Cards.Card(type, color, n);
//                            Assert.assertEquals(newCard.getType(), type);
//                            Assert.assertEquals(newCard.getColor(), color);
//                            Assert.assertEquals(newCard.getNumber(), n);
//                        }
//                    }
//                    else {
//                        /* test reverse, skip, and draw two cards */
//                        newCard = new Cards.Card(type, color);
//                        Assert.assertEquals(newCard.getType(), type);
//                        Assert.assertEquals(newCard.getColor(), color);
//                    }
//                }
//            }
//            else {
//                /* test wild and wild draw four cards */
//                newCard = new Cards.Card(type);
//                Assert.assertEquals(newCard.getType(), type);
//            }
//        }
//
//    }
//
//
//    @Test
//    public void testPlayerFunctions() throws Exception {
//        /* test constructor and get functions */
//        for (int i = 0; i < 10; i++) {
//            GameMechanics.Player.GameMechanics.Player newPlayer = new GameMechanics.Player.GameMechanics.Player(i, ("Bob" + i));
//            Assert.assertEquals(newPlayer.getPlayerNum(), i);
//            Assert.assertEquals(newPlayer.getPlayerName(), ("Bob" + i));
//        }
//
//        /* test obtainCard function */
//        Cards.Card newCard;
//        GameMechanics.Player.GameMechanics.Player newPlayer = new GameMechanics.Player.GameMechanics.Player(0, "Bob");
//        for (int i = 0; i < 10; i++) {
//            newCard = new Cards.Card(Cards.Card.Types.Number, Cards.Card.Colors.Red, (9-i));
//            newPlayer.obtainCard(newCard);
//            Assert.assertEquals(newPlayer.getDeckSize(), (i+1));
//            Assert.assertEquals(newPlayer.getDeck().get(i).getNumber(), 9-i);
//        }
//    }
//
//    @Test
//    public void testInitDeck() throws Exception {
//        GameMechanics.Game game = new GameMechanics.Game();
//        game.initDeck();
//        int numNumberCards = 0;
//        int numSkipCards = 0;
//        int numReverseCards = 0;
//        int numDrawTwoCards = 0;
//        int numWildCards = 0;
//        int numWDFCards = 0;
//        int numRedCards = 0;
//        int numBlueCards = 0;
//        int numYellowCards = 0;
//        int numGreenCards = 0;
//        Vector<Cards.Card> deck = game.deck;
//        int deckSize = deck.size();
//        Cards.Card card;
//        Cards.Card.Types type;
//        Cards.Card.Colors color;
//
//        Assert.assertEquals(108, deckSize);
//
//        for (int i = 0; i < deckSize; i++) {
//            card = deck.get(i);
//            type = card.getType();
//            color = card.getColor();
//
//            if (type == Cards.Card.Types.Number) {
//                numNumberCards++;
//            }
//            else if (type == Cards.Card.Types.Reverse) {
//                numReverseCards++;
//            }
//            else if (type == Cards.Card.Types.Skip) {
//                numSkipCards++;
//            }
//            else if (type == Cards.Card.Types.WildDrawFour) {
//                numWDFCards++;
//            }
//            else if (type == Cards.Card.Types.Wild) {
//                numWildCards++;
//            }
//            else if (type == Cards.Card.Types.DrawTwo) {
//                numDrawTwoCards++;
//            }
//
//            if (color == Cards.Card.Colors.Red) {
//                numRedCards++;
//            }
//            else if (color == Cards.Card.Colors.Blue) {
//                numBlueCards++;
//            }
//            else if (color == Cards.Card.Colors.Yellow) {
//                numYellowCards++;
//            }
//            else if (color == Cards.Card.Colors.Green) {
//                numGreenCards++;
//            }
//        }
//
//        Assert.assertEquals(25, numRedCards);
//        Assert.assertEquals(25, numBlueCards);
//        Assert.assertEquals(25, numGreenCards);
//        Assert.assertEquals(25, numYellowCards);
//
//        Assert.assertEquals(76, numNumberCards);
//        Assert.assertEquals(8, numSkipCards);
//        Assert.assertEquals(8, numReverseCards);
//        Assert.assertEquals(8, numDrawTwoCards);
//        Assert.assertEquals(4, numWDFCards);
//        Assert.assertEquals(4, numWildCards);
//
//        GameMechanics.Game.deck.clear();
//    }
//
//    @Test
//    public void testAssessValidity() throws Exception {
//        GameMechanics.Game game = new GameMechanics.Game();
//        Cards.Card newCard;
//        Cards.Card.Colors color;
//        Cards.Card.Types type;
//        for (int t = 0; t < 6; t++) {
//            type = Cards.Card.Types.values()[t];
//            if (t < 4) {
//                for (int c = 0; c < 4; c++) {
//                    color = Cards.Card.Colors.values()[c];
//                    if (t == 0) {
//                        for (int n = 0; n <= 9; n++) {
//                            newCard = new Cards.Card(type, color, n);
//                            game.discardPile.push(newCard);
//                            Assert.assertEquals(true, game.assessValidity(newCard));
//                        }
//                    }
//                    else {
//                        newCard = new Cards.Card(type, color);
//                        game.discardPile.push(newCard);
//                        Assert.assertEquals(true, game.assessValidity(newCard));
//                    }
//
//                }
//            }
//            else {
//                newCard = new Cards.Card(type);
//                game.discardPile.push(newCard);
//                Assert.assertEquals(true, game.assessValidity(newCard));
//            }
//        }
//
//        GameMechanics.Game.discardPile.clear();
//        game.deck.clear();
//    }
//
//
//    @Test
//    public void testPullFromDeck() throws Exception {
//        GameMechanics.Game game = new GameMechanics.Game();
//        game.initDeck();
//        for (int i = 0; i < 109; i++) {
//            game.discardPile.push(game.pullFromDeck());
//        }
//        Assert.assertEquals(2, game.discardPile.size());
//        Assert.assertEquals(106, game.deck.size());
//
//        GameMechanics.Game.discardPile.clear();
//        GameMechanics.Game.deck.clear();
//    }
//}
