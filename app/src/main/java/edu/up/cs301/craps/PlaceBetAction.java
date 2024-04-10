package edu.up.cs301.craps;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

/**
 * PlaceBet Action
 *
 * @author Wes Helms
 * @author Troy Carpenter
 * @author Sydney Dean
 * @author Rowena Archer
 * @version March 2024
 */

public class PlaceBetAction extends GameAction {
    public int betID;
    public double betAmount;
    public int playerId;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public PlaceBetAction(GamePlayer player){
        super(player);
    }
    public PlaceBetAction(GamePlayer player,int playerId, int betID, double betAmount) {
        super(player);  //I don't remember what super(player) does - S
        this.playerId=playerId;
        this.betAmount = betAmount;
        this.betID = betID;
    }
}
