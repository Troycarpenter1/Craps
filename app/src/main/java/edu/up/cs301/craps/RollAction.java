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
    public int die1;
    public int die2;
    public int dieTotal;

    public CrapsMainActivity craps;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public RollAction(GamePlayer player, boolean isShooter, int die1, int die2, int dieTotal, CrapsMainActivity crap) {
        super(player);
        this.isShooter = isShooter;
        this.die1 = die1;
        this.die2 = die2;
        this.dieTotal = dieTotal;
        this.craps = crap;
    }
}
