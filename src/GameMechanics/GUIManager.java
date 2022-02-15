package GameMechanics;

import Cards.Card;
import Cards.Card.Colors;
import GUIs.GUI;
import GUIs.ShowHandGUI;
import GUIs.StartGameGUI;
import GUIs.WinStateGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * This class is a real work horse...
 * This bad boy is basically the main game loop.
 * An instance of this class is passed as the listener
 * to every GUI control, and the included implementation
 * of ActionPerformed calls the appropriate functions from Game,
 * Player, AI, Card, GUI classes, and other classes.
 */
public class GUIManager implements ActionListener {
    public GUI gui;
    public Game game;

    /**
     * Initializes a start screen and adds it to gui. Game is stored
     * as a global variable so that the game state can be manipulated
     * throughout this file,
     * @param newGUI    Primary GUI for the game
     * @param newGame   The game itself, as a Game object
     */
    public GUIManager(GUI newGUI, Game newGame) {
        gui = newGUI;
        gui.startScreen = new StartGameGUI(gui.frame, this);
        gui.frame.pack();
        gui.frame.setVisible(true);

        game = newGame;
    }

    /**
     * Called when a player or bot's "deck" is emptied
     * @param winner    Name of the victor
     */
    public void declareWinner(String winner) {
        gui.frame.getContentPane().removeAll();
        new WinStateGUI(winner, gui.frame);
        gui.frame.pack();
        gui.frame.repaint();
    }

    /**
     * Draws the main game screen with the current player's cards
     * @param info  String to be represented in the info box at the top
     */
    public void drawGameGUI(String info) {
        String botOutput;
        if (game.numPlayers > 0) {
            while (game.curPlayer.isBot) {
                botOutput = runBot();
                if (game.curPlayer.getHandSize() == 0) {
                    System.out.println("The game is OVER!");
                    declareWinner(game.curPlayer.getName());
                    return;
                }
                if (!"".equals(botOutput)) {
                    info = botOutput;
                }
            }

            gui.frame.getContentPane().removeAll();
            gui.gameScreen = new ShowHandGUI(game.curPlayer, game.discardPile.peek(), gui.frame, this);
            gui.gameScreen.infoLabel.setText(info);
            gui.frame.pack();
            gui.frame.repaint();
        }
    }

    /**
     * The player's deck is not displayed by default.
     * This is called when the "show hand" button is pressed.
     */
    public void showCards() {
        for (int i = 0; i < game.curPlayer.getHandSize(); i++) {
            JButton curButton = gui.gameScreen.cards.get(i);
            Card curCard = game.curPlayer.getHand().get(i);
            curButton.setEnabled(true);
            curButton.setText(curCard.printCardName());
        }
        gui.gameScreen.drawButton.setEnabled(true);
        gui.gameScreen.showButton.setEnabled(false);
    }

    /**
     * Executes some final business for a player's successful turn
     * @param curCardIdx   index of Card being placed on discard pile
     */
    public void completeTurn(int curCardIdx) {
        Vector<Card> hand = game.curPlayer.getHand();
        Card curCard = hand.get(curCardIdx);
        String infoStr;

        game.placeCard(curCard);
        hand.remove(curCardIdx);

        if (game.curPlayer.getHandSize() == 0) {
            declareWinner(game.curPlayer.getName());
            return;
        }

        game.incTurn();

        infoStr = handleDrawCards();

        drawGameGUI(infoStr);

    }

    /**
     * Checks if the top of the discard pile is a draw card, and acts accordingly.
     * @return  String message describing how many cards were added to who's deck
     */
    public String handleDrawCards() {
        if (game.drawType != -1 && !game.curPlayer.parsePlayerHand(game.discardPile.peek())) {
            for (int i = 0; i < (game.drawType * game.drawStack); i++) {
                game.curPlayer.pushCard(game.drawPile.pull(game.discardPile));
            }

            String returnString = ("Added " + (game.drawType * game.drawStack) + " cards to " + game.curPlayer.getName() + "'s hand.");

            game.drawStack = 0;
            game.drawType = -1;
            game.discardPile.peek().neutralize();
            game.incTurn();

            return returnString;
        }

        return "";
    }

    /**
     * Main logic for executing a bot's turn. Check out that
     * nifty recursive return statement at the bottom the combines info messages
     * from the turns of multiple bots.
     * @return  info message
     */
    public String runBot() {
        String info = "";

        if (game.curPlayer.isBot) {
            AI bot = (AI) game.curPlayer;
            Card botCard = bot.chooseCard(game.discardPile.peek()); /* chooseCard DOES remove the card from the bot's hand */

            if (botCard != null) {
                game.placeCard(botCard);
                if (bot.getHandSize() == 0) {
                    declareWinner(bot.getName());
                    return "";
                }
            } else {
                bot.pushCard(game.drawPile.pull(game.discardPile));
            }

            info = game.curPlayer.getName() + " has " + bot.getHandSize() + " cards remaining. ";

            game.incTurn();
            return info + runBot() + handleDrawCards();
        }

        return "";
    }

    /**
     * Returns a Colors object based on i, the index of the color button pressed
     * @param i index of pressed color button
     * @return  Colors object
     */
    public Colors getColor(int i) {
        return switch (i) {
            case 0 -> Colors.Red;
            case 1 -> Colors.Blue;
            case 2 -> Colors.Green;
            default -> Colors.Yellow;
        };
    }

    /**
     * Assesses whether an ActionListener is being triggered by a card button,
     * and if so, acts upon the specified card.
     * @param source    Object that triggered the ActionListener
     */
    public void checkHandForAction(Object source) {
        Card curCard;

        for (int i = 0; i < game.curPlayer.getHandSize(); i++) {

            if (source == gui.gameScreen.cards.get(i)) {
                curCard = game.curPlayer.getHand().get(i);

                if (curCard.getColor() == Card.Colors.Undefined) {
                    game.mostRecentWildCardIdx = i;
                    gui.gameScreen.infoLabel.setText("Choose a color for the wild card!");
                    gui.gameScreen.enableColorButtons(true);
                }
                else {
                    if (game.discardPile.assessValidity(curCard)) {
                        completeTurn(i);
                    }
                    else {
                        gui.gameScreen.infoLabel.setText("Invalid card selection!");
                    }
                }

                break;
            }
        }
    }

    /**
     * Assesses whether a color selection button is responsible for an
     * ActionEvent, and acts accordingly.
     * @param source    Object that triggered the ActionEvent
     */
    public void checkColorButtonsForAction(Object source) {
        for (int i = 0; i < 4; i++) {

            if (source == gui.gameScreen.colorButtons[i]) {
                Card curCard = game.curPlayer.getHand().get(game.mostRecentWildCardIdx);
                Card.Colors newColor;

                newColor = getColor(i);

                curCard.setColor(newColor);
                completeTurn(game.mostRecentWildCardIdx);

                gui.gameScreen.enableColorButtons(false);

                break;
            }
        }
    }

    /**
     * Handles signals from the GUI
     * @param e an ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == gui.startScreen.startButton) {
            game.dealCards();
            game.curPlayer = game.players[0];
            drawGameGUI("");
        }
        else if (source == gui.startScreen.textField) {
            String playerName = gui.startScreen.textField.getText();
            gui.startScreen.playerMessageLabel.setText("Added player " + game.numPlayers + ": " + playerName);
            game.addPlayer(new Player(playerName));
            gui.startScreen.textField.setText("");
        }
        else if (source == gui.startScreen.botButton) {
            int botNum = Player.numBots;
            String botName = "Bot" + botNum;
            gui.startScreen.playerMessageLabel.setText("Added player " + game.numPlayers + ": " + botName);
            game.addPlayer(new AI(botName));
            Player.numBots++;
        }
        else if (source == gui.gameScreen.showButton) {
            showCards();
        }
        else if (source == gui.gameScreen.drawButton) {
            game.curPlayer.pushCard(game.drawPile.pop());
            drawGameGUI(game.curPlayer.getName() + " drew a new card");
            showCards();
            gui.gameScreen.drawButton.setEnabled(false);
        }
        else if (source == gui.gameScreen.skipButton) {
            String skipMessage = game.curPlayer.getName() + " skipped their turn";
            game.incTurn();
            drawGameGUI(skipMessage);
        }
        else {
            checkHandForAction(source);
            checkColorButtonsForAction(source);
        }
    }


}
