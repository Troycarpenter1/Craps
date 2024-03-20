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
    public int betAmount;

    public int playerMoney;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public PlaceBetAction(GamePlayer player, int betID, int betAmount, int playerMoney) {
        super(player);                  //I don't remember what super(player) does - S
        this.betAmount = betAmount;
        this.betID = betID;
        this.playerMoney = playerMoney;

    }
}
