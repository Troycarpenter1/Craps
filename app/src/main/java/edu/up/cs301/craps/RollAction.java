package edu.up.cs301.craps;

import java.io.Serializable;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

/**
 * Roll Action is an action that will roll the dice on the craps board
 * and in turn update the gui accordingly
 *
 * @author Wes Helms
 * @author Troy Carpenter
 * @author Sydney Dean
 * @author Rowena Archer
 * @version April 2024
 */

public class RollAction extends GameAction {
    private static final long serialVersionUID = 5;
    int playerId;

    /**
     * RollAction constructor
     *
     * @param player player that sent the action
     * @param playerId Id of the player that sent the action
     */
    public RollAction(GamePlayer player, int playerId) {
        super(player);
        this.playerId = playerId;
    }
}
