package edu.up.cs301.craps;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

/**
 * RemoveBet Action is an action that will remove an existing
 * bet on the craps board
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
     * RemoveBetAction constructor
     *
     * @param player the player that sent the action
     * @param playerId Id of the player that sent the action
     * @param betID Id of the bet that we want to remove
     */
    public RemoveBetAction(GamePlayer player,int playerId, int betID) {
        super(player);
        this.betID = betID;
        this.playerId=playerId;

    }




}
