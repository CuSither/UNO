package GUIs;

import Cards.Card;
import GameMechanics.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Vector;


/**
 * The ShowHandGui class produces a JPanel that is used by GUIs.GUI to render
 * the player hand, draw pile, discard pile, and other options related to
 * game's running state.
 */
public class ShowHandGUI  {
    static Dimension cardDimension = new Dimension(120, 180);
    GridBagConstraints gbc;
    public JButton drawButton;
    public JButton skipButton;
    public JButton showButton;
    public JLabel infoLabel;
    public JButton[] colorButtons; /* red, blue, green, yellow */
    public Vector<JButton> cards;

    /**
     * Constructor
     * @param player    player whose turn it is
     * @param frame     the JFrame passed from the GUIs.GUI object
     * @param listener  the ActionListener passed from the GUIs.GUI object that all buttons should report to
     * @throws Exception
     */
    public ShowHandGUI(Player player, Card topCard, JFrame frame, ActionListener listener) {
        cards = new Vector<>();
        gbc = new GridBagConstraints();
        colorButtons = new JButton[4];

        frame.add(drawCards(player, listener), BorderLayout.CENTER);
        frame.add(drawLabel(player), BorderLayout.NORTH);
        frame.add(drawOptionsPanel(listener), BorderLayout.EAST);
        frame.add(drawDiscardPile(topCard), BorderLayout.WEST);
        frame.add(drawColorButtons(listener), BorderLayout.SOUTH);
    }

    /**
     * Creates a panel that contains show cards, draw card, and skip turn buttons
     * @param listener  Listener passed from GUIs.GUI which should be used to set all ActionListeners
     * @return          Returns a JPanel containing this part of the menu
     */
    public JPanel drawOptionsPanel(ActionListener listener) {
        JPanel drawPanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        gbc.fill = GridBagConstraints.VERTICAL;
        drawPanel.setLayout(layout);
        drawPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        drawButton = new JButton("Draw Card");
        drawButton.setPreferredSize(cardDimension);
        drawButton.addActionListener(listener);
        drawButton.setEnabled(false);

        skipButton = new JButton("Skip Turn");
        skipButton.setPreferredSize(new Dimension(120, 20));
        skipButton.addActionListener(listener);

        showButton = new JButton("Show Hand");
        showButton.setPreferredSize(new Dimension(120, 20));
        showButton.addActionListener(listener);

        gbc.gridy=0;
        drawPanel.add(showButton, gbc);
        gbc.gridy=1;
        drawPanel.add(drawButton, gbc);
        gbc.gridy=2;
        drawPanel.add(skipButton, gbc);

        return drawPanel;
    }

    /**
     * Creates a panel with a label showing whose turn it is
     * @param player    GameMechanics.Player.GameMechanics.Player whose turn it is
     * @return          Panel containing the label
     */
    public JPanel drawLabel(Player player) {
        JLabel label = new JLabel(player.getName() + "'s Turn", JLabel.CENTER);
        JPanel labelPanel = new JPanel();
        infoLabel = new JLabel("", JLabel.CENTER);

        infoLabel.setPreferredSize(new Dimension(360, 20));

        labelPanel.setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.VERTICAL;

        gbc.gridy = 0;
        labelPanel.add(label, gbc);

        gbc.gridy = 1;
        labelPanel.add(infoLabel, gbc);
        return labelPanel;
    }

    /**
     * Creates a JPanel depicting the top card on the discard pile
     * @param topCard   The top card on the discard pile (hopefully)
     * @return          Returns the new JPanel
     * @throws Exception
     */
    public JPanel drawDiscardPile(Card topCard) {
        String cardName = topCard.printCardName();
        JButton topCardLabel = new JButton(cardName);
        JPanel topCardPanel = new JPanel();

        topCardLabel.setPreferredSize(cardDimension);

        topCardPanel.setLayout(new GridBagLayout());
        topCardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        topCardPanel.add(topCardLabel, gbc);

        return topCardPanel;
    }

    /**
     * initializes the color buttons, so that the color of wild cards may be chosen
     * @param listener  listener
     * @return  The panel with the buttons on it
     */
    public JPanel drawColorButtons(ActionListener listener) {
        JPanel colorPanel = new JPanel();
        colorPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JButton colorLabel = new JButton("Wild card color:");
        colorLabel.setHorizontalAlignment(SwingConstants.CENTER);

        colorPanel.setLayout(new GridBagLayout());
        JButton redButton = initColorButton("Red", listener);
        colorButtons[0] = redButton;
        JButton blueButton = initColorButton("Blue", listener);
        colorButtons[1] = blueButton;
        JButton greenButton = initColorButton("Green", listener);
        colorButtons[2] = greenButton;
        JButton yellowButton = initColorButton("Yellow", listener);
        colorButtons[3] = yellowButton;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy = 0;
        colorPanel.add(colorLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        colorPanel.add(redButton, gbc);
        gbc.gridx = 1;
        colorPanel.add(blueButton, gbc);
        gbc.gridx = 2;
        colorPanel.add(greenButton, gbc);
        gbc.gridx = 3;
        colorPanel.add(yellowButton, gbc);

        return colorPanel;
    }

    public JButton initColorButton(String color, ActionListener listener) {
        JButton button = new JButton(color);
        Dimension buttonSize = new Dimension(120, 20);
        button.addActionListener(listener);
        button.setPreferredSize(buttonSize);
        button.setEnabled(false);

        return button;
    }

    public void enableColorButtons(boolean enable) {
        for (JButton colorButton : colorButtons) {
            colorButton.setEnabled(enable);
        }
    }


    /**
     * Creates a panel containing a button for each card in the player's deck.
     * This function needs to be modified so that each card button is added to a vector.
     * @param player    GameMechanics.Player.GameMechanics.Player whose turn it is
     * @return          JPanel with buttons representing player's hand
     * @throws Exception
     */
    public JPanel drawCards(Player player, ActionListener listener) {
        JButton cardButton;
        int numCards = player.getHandSize();
        JPanel cardPanel = new JPanel();

        cardPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        GridBagLayout layout = new GridBagLayout();
        cardPanel.setLayout(layout);

        for (int i = 0; i < numCards; i++) {
            gbc.gridy = i / 7;
            cardButton = new JButton();
            cardButton.setPreferredSize(cardDimension);
            cardButton.setEnabled(false);
            cardButton.addActionListener(listener);
            cardPanel.add(cardButton, gbc);
            cards.add(cardButton);
        }

        return cardPanel;
    }
}
