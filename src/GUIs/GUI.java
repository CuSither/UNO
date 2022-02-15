package GUIs;
import GameMechanics.Game;
import GameMechanics.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * The GUIs.GUI class implements the GUIs.GUI for the whole game, dynamically switching between
 * JPanels from GUIs.ShowHandGUI, GUIs.StartGameGUI, and GUIs.WinStateGUI.
 */
public class GUI {
     public JFrame frame;
     public StartGameGUI startScreen;
     public ShowHandGUI gameScreen;

    /**
     * Initializes the JFrame used by the GUIs.GUI
     * @throws Exception
     */
     public GUI() {
         frame = new JFrame();
         frame.setResizable(false);

         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setTitle("UNO");
     }


    /**
     * This main function is only used for testing.
     * @param args
     * @throws Exception
     */
//    public static void main(String[] args) throws Exception {
//         new GUI();
//    }
}
