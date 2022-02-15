import GUIs.GUI;
import GameMechanics.GUIManager;
import GameMechanics.Game;

/**
 * There isn't much here. Run Main to start the game.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Game game = new Game();

        GUI gui = new GUI();

        new GUIManager(gui, game);
    }
}
