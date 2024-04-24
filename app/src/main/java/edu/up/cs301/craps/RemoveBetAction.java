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
 * @version April 2024
 */

public class RemoveBetAction extends GameAction {

    private static final long serialVersionUID = 4;
    public int betID;
    public int playerId;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public RemoveBetAction(GamePlayer player,int playerId, int betID) {
        super(player);
        this.betID = betID;
        this.playerId=playerId;

    }




}
