package edu.up.cs301.craps;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

/**
 * Ready2Crap Action (the Ready action name was taken)
 * This action will set the given GamePlayer to ready
 * when delivered to the game state
 *
 * @author Wes Helms
 * @author Troy Carpenter
 * @author Sydney Dean
 * @author Rowena Archer
 * @version April 2024
 */

public class Ready2CrapAction extends GameAction {

    private static final long serialVersionUID = 3;
    public int playerID;
    public boolean isReady;

    /**
     * Ready2CrapAction constructor
     *
     * @param player the player that sent the action
     * @param isReady the ready status that we want to send
     * @param playerID Id of the player that sent the action
     */
    public Ready2CrapAction(GamePlayer player, boolean isReady, int playerID) {
        super(player);
        this.isReady = isReady;
        this.playerID = playerID;
    }
}
