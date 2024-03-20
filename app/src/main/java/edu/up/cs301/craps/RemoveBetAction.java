package edu.up.cs301.craps;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

public class RemoveBetAction extends GameAction {
    public int betLocation;
    public int betAmount;
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public RemoveBetAction(GamePlayer player, int betLocation, int betAmount) {
        super(player);
        this.betAmount = 0;
        this.betLocation = betLocation;
    }
    /**
     * constructor for GameAction
     *
     * @param player      the player who created the action
     * @param betLocation
     * @param betAmount
     */

}
