package GUIs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This GUIs.GUI is displayed at the beginning of the game and provides the players
 * a textfield with which to enter their names.
 */
public class StartGameGUI {
    JLabel label;
    JPanel labelPanel;
    public JTextField textField;
    JPanel textFieldPanel;
    JPanel bottomPanel;
    public JLabel playerMessageLabel;
    public JButton startButton;
    public JButton botButton;
    GridBagConstraints gbc;

    /**
     * Initializes the components of the game's start menu
     * @param frame     JFrame passed from GUIs.GUI which components are added to
     * @param listener  listener to be assigned to all ActionListeners
     */
    public StartGameGUI(JFrame frame, ActionListener listener) {
        label = new JLabel("Welcome to UNO! Please enter player names below:", JLabel.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(5,20,5,20));

        initPlayerMessage();
        initTextField(listener);
        initLabel(listener);
        initButton(listener);

        frame.add(textFieldPanel, BorderLayout.CENTER);
        frame.add(labelPanel, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Initializes the JLabel that displays info when a player
     * has been added.
     */
    public void initPlayerMessage() {
        playerMessageLabel = new JLabel("");

        labelPanel = new JPanel();
        labelPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridy = 0;
        labelPanel.add(label, gbc);

        gbc.gridy = 1;
        labelPanel.add(playerMessageLabel, gbc);
    }

    /**
     * Initializes the text field that players enter their names into
     * @param listener  listener to be assigned to all ActionListeners
     */
    public void initTextField(ActionListener listener) {
        textFieldPanel = new JPanel();
        textField = new JTextField(20);

        textFieldPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        textField.addActionListener(listener);
        textFieldPanel.add(textField);
    }

    /**
     * Initializes the panel and label that display info for the recently added player
     */
    public void initLabel(ActionListener listener) {
        bottomPanel = new JPanel();
        botButton = new JButton("Add bot");
        botButton.addActionListener(listener);

        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        bottomPanel.setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        bottomPanel.add(botButton, gbc);
    }

    /**
     * Initializes the "start game" button
     * @param listener  listener to be assigned to all ActionListeners
     */
    public void initButton(ActionListener listener) {
        startButton = new JButton("Start Game");

        gbc.gridx = 1;
        bottomPanel.add(startButton, gbc);
        startButton.addActionListener(listener);
    }
}
