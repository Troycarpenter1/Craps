package edu.up.cs301.craps;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

/**
 * Ready2Crap Action (the Ready action name was taken)
 *
 * @author Wes Helms
 * @author Troy Carpenter
 * @author Sydney Dean
 * @author Rowena Archer
 * @version March 2024
 */

public class Ready2CrapAction extends GameAction {

    public boolean isReady;

    public int playerID;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public Ready2CrapAction(GamePlayer player, boolean isReady, int playerID) {
        super(player);
        this.isReady = isReady;
        this.playerID = playerID;

    }
}
