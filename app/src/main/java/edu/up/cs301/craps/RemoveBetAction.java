package edu.up.cs301.craps;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

/**
 * RemoveBet Action
 *
 * @author Wes Helms
 * @author Troy Carpenter
 * @author Sydney Dean
 * @author Rowena Archer
 * @version March 2024
 */

public class RemoveBetAction extends GameAction {
    public int betID;
    public int betAmount;
    public int playerId;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public RemoveBetAction(GamePlayer player,int playerId, int betID) {
        super(player);
        this.betAmount = 0;
        this.betID = betID;
        this.playerId=playerId;

    }

}
