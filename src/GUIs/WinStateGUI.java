package GUIs;

import GameMechanics.Player;

import javax.swing.*;
import java.awt.*;

/**
 * This is the last window that is displayed, announcing the winner's name (and maybe more)
 */
public class WinStateGUI {
    /**
     * constructor
     * @param winner    winner's name
     * @param frame     JFrame that components are being added to
     */
    public WinStateGUI(String winner, JFrame frame) {
        JPanel winnerPanel = new JPanel();
        winnerPanel.setPreferredSize(new Dimension(600,400));
        winnerPanel.setLayout(new GridLayout());
        JLabel winnerLabel = new JLabel(winner + " won the game!", JLabel.CENTER);
        winnerPanel.add(winnerLabel, BorderLayout.CENTER);

        frame.add(winnerPanel);
    }
}
