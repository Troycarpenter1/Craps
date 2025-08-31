package edu.up.cs301.craps;

import android.view.MenuItem;

import java.util.ArrayList;

import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.GameFramework.players.GamePlayer;
import edu.up.cs301.GameFramework.LocalGame;
import edu.up.cs301.GameFramework.gameConfiguration.*;

/**
 * CrapsMainActivity
 * this is the primary activity for the Craps game
 *
 * <p>
 * Project #i - Beta Release Information:
 * 1)
 * The game has been implemented in accordance with
 * the rules that are in the requirements of the developers
 * 2)
 * All functionality of the GUI that was specified
 * in the requirements are be present
 * 3)
 * The game includes both a "smart" AI and a "dumb" AI
 * that are clearly different in play style and effectiveness
 * Note: This game is entirely chance based
 * it is possible for either AI to outperform the other
 * in any given game depending on pure chance
 * ("random" values the app generates)
 * 4)
 * The game supports network play
 * (as far as I know I honestly did not do that part
 * but I do understand it - Wes)
 * 5)
 * I think the GUI looks nice, there have been a few minor improvements
 * since the requirements to be more visually pleasing :)
 * 6)
 * The game must be ran with 2 players
 * with at least 1 of which being a local human player
 * the combinations player order is not important
 * and changes play only by the rules in the requirements (turn order)
 * only one GUI player can be supported per device
 * 7)
 * The GUI is really pretty but also effective:
 * The GUI updates the colors of text on buttons
 * based on the actions sent to the gameState
 * Additionally if you select the help menu button
 * a message popup will appear that displays additional information
 * regarding the state of the game that is very
 * helpful for any player/developer/professor/grader
 * so that they can know how much money are on what bets for each player
 * (this may be considered additional functionality as it is not required
 * by any means to actually play and enjoy the game but is very informative)
 * <p>
 * For more information about the Craps Main Activity refer to the
 * first comment header written
 *
 * @author Andrew M. Nuxoll
 * @author Steven R. Vegdahl
 * @author Wes H.       Last Revision: April 2024
 * @author Troy C.      Last Revision: April 2024
 * @author Rowena A.    Last Revision: April 2024
 * @author Sydney D.    Last Revision: April 2024
 * @version April 2024
 */
public class CrapsMainActivity extends GameMainActivity {

    // the port number that this game will use when playing over the network
    private static final int PORT_NUMBER = 2234;

    /**
     * Create the default configuration for this game:
     * - one human player vs. one computer player
     * - minimum of 1 player, maximum of 2
     * - one kind of computer player and one kind of human player available
     * [7:12 PM] Dean, Sydney
     * Instructions:
     * If the "Shoot" text is red it means it's your turn to shoot.
     * If shoot is black then it's the other player's turn.
     * Both players must be ready for the shooter to shoot (including the shooter).
     * Readying indicates to the game that you're done making adjustments to bets.
     * Click the ready button to ready/unready.
     * Red text on the Ready button indicates you're ready, Black indicates
     * you're not ready.
     * <p>
     * You can bet as long as you're not ready.
     * Adjust the slider to adjust the amount of your bet. Press the radio
     * buttons to adjust the increment of your bet.
     * <p>
     * Click on any place on the board to place a bet.
     * A bet will highlight gold if a bet has been placed there.
     * To remove a bet, adjust the slider to 0 and click a place on the board.
     * (Note, you must adjust the slider once after the game starts before
     * you can place a bet.)
     * <p>
     * You win if the other player has lost all their money (including money they
     * have placed on bets.)
     * You lose if you lose all your money (including all money you placed on bets).
     * <p>
     * For example, you would lose if you bet all your money on a place and you
     * lost the bet.
     *
     * @return the new configuration object, representing the default configuration
     */
    @Override
    public GameConfig createDefaultConfig() {

        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        // a human player player type (player type 0)
        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) {
                return new CrapsHumanPlayer(name);
            }
        });

        // a computer player type (player type 1)
        playerTypes.add(new GamePlayerType("Computer Player") {
            public GamePlayer createPlayer(String name) {
                return new CrapsComputerPlayer1(name);
            }
        });

        // a computer player type (player type 2)
        playerTypes.add(new GamePlayerType("Smart Computer Player") {
            public GamePlayer createPlayer(String name) {
                return new CrapsComputerPlayer2(name);
            }
        });

        // Create a game configuration class for Craps:
        // - player types as given above
        // - from 1 to 2 players
        // - name of game is "Craps Game"
        // - port number as defined above
        GameConfig defaultConfig = new GameConfig(playerTypes, 1, 2, "Craps Game",
                PORT_NUMBER);

        // Add the default players to the configuration
        defaultConfig.addPlayer("Human", 0); // player 1: a human player
        defaultConfig.addPlayer("Computer", 1); // player 2: a computer player

        // Set the default remote-player setup:
        // - player name: "Remote Player"
        // - IP code: (empty string)
        // - default player type: human player
        defaultConfig.setRemoteData("Remote Player", "", 0);

        // return the configuration
        return defaultConfig;
    }//createDefaultConfig

    /**
     * create a local game
     *
     * @param state the gameState to make into a local game
     * @return the local game, a craps game
     */
    @Override
    public LocalGame createLocalGame(GameState state) {
        if (state == null) state = new CrapsState();
        return new CrapsLocalGame(state);
    }

}
