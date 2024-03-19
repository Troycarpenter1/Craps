package edu.up.cs301.craps.Actions;

import edu.up.cs301.GameFramework.players.GamePlayer;

public class RemoveBetAction extends PlaceBetAction{
    /**
     * constructor for GameAction
     *
     * @param player      the player who created the action
     * @param betLocation
     * @param betAmount
     */
    public RemoveBetAction(GamePlayer player, int betLocation, int betAmount) {
        super(player, betLocation, betAmount);
        this.betAmount = 0;
    }
}
