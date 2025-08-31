package edu.up.cs301.craps;

import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.GameFramework.players.GamePlayer;
import edu.up.cs301.GameFramework.LocalGame;
import edu.up.cs301.GameFramework.actionMessage.GameAction;

import android.util.Log;

/**
 * A class that represents the state of a game.
 *
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @author Wes H.       Last Revision: April 2024
 * @author Troy C.      Last Revision: April 2024
 * @author Rowena A.    Last Revision: April 2024
 * @author Sydney D.    Last Revision: April 2024
 * @version April 2024
 */
public class CrapsLocalGame extends LocalGame {

    // When a craps game is played, any number of players. The players
    // place bets on the board to try to increase their own money.
    public static final int TARGET_MAGNITUDE = 10;

    // the game's state
    private CrapsState gameState;

    /**
     * canMove
     * can this player move
     *
     * @param playerIdx the index of the player making a move
     * @return true, because all player are always allowed to move at all times,
     * as this is a fully asynchronous game
     */
    @Override
    protected boolean canMove(int playerIdx) {
        return true;
    }

    /**
     * CrapsLocalGame
     * This ctor should be called when a new craps game is started
     *
     * @param state the new GameState
     */
    public CrapsLocalGame(GameState state) {
        // initialize the game state
        if (!(state instanceof CrapsState)) {
            state = new CrapsState();
        }
        this.gameState = (CrapsState) state;
        super.state = state;

    }

    /**
     * makeMove
     * Denote whether the different actions sent are legal
     * and perform appropriate action handler method in
     * the game state
     * <p>
     * Possible actions to receive are: Ready2CrapAction, RollAction
     * PlaceBetAction, and RemoveBetAction
     *
     * @param action The move that the player has sent to the game
     * @return boolean value that is true if a move can be made
     */
    @Override
    protected boolean makeMove(GameAction action) {
        Log.i("action", action.getClass().toString());
        //checks what action this move is
        if (action instanceof CrapsMoveAction) {

            // cast so that we Java knows it's a CrapsMoveAction
            CrapsMoveAction cma = (CrapsMoveAction) action;

            // denote that this was a legal/successful move
            return true;
        } else if (action instanceof Ready2CrapAction) {
            //typecast
            Ready2CrapAction ready2crap = (Ready2CrapAction) action;
            //perform appropriate action handler method in gamestate
            gameState.ready(ready2crap);
            //denote legal move
            return true;
        } else if (action instanceof RollAction) {
            //typecast
            RollAction rolling = (RollAction) action;
            //perform appropriate action handler method in gamestate
            gameState.roll(rolling);
            //denote legal move
            return true;
        } else if (action instanceof PlaceBetAction) {
            //typecast
            PlaceBetAction placebet = (PlaceBetAction) action;
            //perform appropriate action handler method in gamestate
            gameState.placeBet(placebet);
            //denote legal move
            return true;
        } else if (action instanceof RemoveBetAction) {
            //typecast
            RemoveBetAction removebet = (RemoveBetAction) action;
            //perform appropriate action handler method in gamestate
            gameState.removeBet(removebet);
            //denote legal move
            return true;
        } else {
            // denote that this was an illegal move
            return false;
        }
    }//makeMove

    /**
     * sendUpdatedStateTo
     * send the updated state to a given player
     *
     * @param p the player to notify
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        // this is a perfect-information game, so we'll make a
        // complete copy of the state to send to the player
        p.sendInfo(new CrapsState(this.gameState));

    }//sendUpdatedSate

    /**
     * checkIfGameOver
     * Check if the game is over. It is over, return a string that tells
     * who the winner(s), if any, are. If the game is not over, return null;
     * <p>
     * win lose condition
     *
     * @return a message that tells who has won the game, or null if the
     * game is not over
     */
    @Override
    protected String checkIfGameOver() {
        // initializes the players money to what they currently have
        int player0funds = gameState.getPlayerFunds(0);
        int player1funds = gameState.getPlayerFunds(1);
        // checks if either player has too much money
        if (player0funds >= Integer.MIN_VALUE && player0funds < 0) {
            return "Player 0 won too much money and has been kicked out of the casino and lost,\nPlayer 0 Wins!\n";
        } else if (player1funds >= Integer.MIN_VALUE && player1funds < 0) {
            return "Player 1 won too much money and has been kicked out of the casino and lost,\nPlayer 1 Wins!\n";
        }
        // iterates through all of the bets in the game and adds their amount to the correct player
        for (int p = 0; p < gameState.bets.length; p++) {
            for (int b = 0; b < gameState.bets[p].length; b++) {
                if (p == 0) {
                    player0funds += gameState.bets[p][b].getAmount();
                } else {
                    player1funds += gameState.bets[p][b].getAmount();
                }
            }
        }
        // checks if the player has any money (now including the money they have bet)
        if (player0funds == 0) {
            return "Player 0 lost all their money,\nPlayer 1 Wins!\n";
        } else if (player1funds == 0) {
            return "Player 1 lost all their money,\nPlayer 0 Wins!\n";
        }
        return "That's all folks";
    }

}// class CrapsLocalGame
