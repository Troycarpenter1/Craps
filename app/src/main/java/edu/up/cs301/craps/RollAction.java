package edu.up.cs301.craps;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

public class RollAction extends GameAction {

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public RollAction(GamePlayer player) {
        super(player);
    }
}
