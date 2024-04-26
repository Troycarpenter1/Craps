package edu.up.cs301.craps;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

/**
 * PlaceBet Action is an action that will place a bet on the board
 *
 * @author Wes Helms
 * @author Troy Carpenter
 * @author Sydney Dean
 * @author Rowena Archer
 * @version April 2024
 */

public class PlaceBetAction extends GameAction {

    private static final long serialVersionUID = 2;
    public int betID;
    public int betAmount;
    public int playerId;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public PlaceBetAction(GamePlayer player){
        super(player);
    }

    /**
     * PlaceBetAction constructor
     *
     * @param player the player that sent the action
     * @param playerId Id of the given player
     * @param betID Id of the bet the player wants to place
     * @param betAmount Amount the player wants to place on that bet
     */
    public PlaceBetAction(GamePlayer player,int playerId, int betID, int betAmount) {
        super(player);  //I don't remember what super(player) does - S
        this.playerId=playerId;
        this.betAmount = betAmount;
        this.betID = betID;
    }
}
