package edu.up.cs301.craps;

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

    public boolean isShooter;

    public CrapsMainActivity craps;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public RollAction(GamePlayer player, boolean isShooter, CrapsMainActivity crap) {
        super(player);
        this.isShooter = isShooter;
        this.craps = crap;
    }
}
