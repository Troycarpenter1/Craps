package edu.up.cs301.craps;

import java.io.Serializable;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

/**
 * Roll Action
 *
 * @author Wes Helms
 * @author Troy Carpenter
 * @author Sydney Dean
 * @author Rowena Archer
 * @version March 2024
 */

public class RollAction extends GameAction {

    private static final long serialVersionUID = 5;

    public boolean isShooter;
    public int playerId;


    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public RollAction(GamePlayer player, int playerId) {
        super(player);
        this.playerId = playerId;
    }
}
