package edu.up.cs301.craps;

import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

/**
 * Dumb Computer Player
 *
 * Doesn't place any bets when the human is the shooter.
 * Will bet about every roll when it is the shooter, $100
 * on a random bet each time, until it runs out of money.
 *
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @author Troy Carpenter
 * @author Rowena Archer
 * @author Sydney Dean
 * @author Wes Helms
 * @version April 2024
 */
public class CrapsComputerPlayer2 extends CrapsComputerPlayer1 {

    /*
     * instance variables
     */

    // the most recent game state, as given to us by the CounterLocalGame
    private CrapsState currentGameState = null;

    // If this player is running the GUI, the activity (null if the player is
    // not running a GUI).
    private Activity activityForGui = null;

    // If this player is running the GUI, the handler for the GUI thread (otherwise
    // null)
    private Handler guiHandler = null;
    private int playerMoney;// my money
    private int amountBet;// amount I wants to bet

    /**
     * constructor
     *
     * @param name the player's name
     */
    public CrapsComputerPlayer2(String name) {
        super(name);
        // start the timer, ticking 20 times per second
        getTimer().setInterval(50);
        getTimer().start();

        //initialize instance variables
        this.playerMoney = 1000;
        this.amountBet = 200;
    }

    /**
     * callback method--game's state has changed
     *
     * @param info the information (presumably containing the game's state)
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        // perform superclass behavior
        super.receiveInfo(info);

        Log.i("computer player", "receiving");

        // if there is no game, ignore
        if (game == null) {
            return;
        } else if (info instanceof CrapsState) {
            // if we indeed have a counter-state, update the GUI
            currentGameState = (CrapsState) info;
            //updateDisplay();
        }
    }

    /**
     *  placeBet
     *  Sends a PlaceBetAction to the crapState
     *  Will place a random bet on the board whenever called
     */
    @Override
    public void placeBet() {
        // if I somehow have less than $100 to spend, then bet all the money I have left
        if (playerMoney < 100) {
            this.amountBet = playerMoney;
        } else {
            // Set my amount to bet to $100
            this.amountBet = 100;
        }

        // bet on the pass line every time
        // make a random bet
        Random rand = new Random();
        int betNum = rand.nextInt(22) + 1;
        PlaceBetAction pba = new PlaceBetAction(this, playerNum, betNum, amountBet);
        this.game.sendAction(pba);
        System.out.println("computer trying to bet");
    }

    /**
     * Tells whether we support a GUI
     *
     * @return true because we support a GUI
     */
    public boolean supportsGui() {
        return true;
    }

    /**
     * callback method--our player has been chosen/rechosen to be the GUI,
     * called from the GUI thread.
     *
     * @param a the activity under which we are running
     */
    @Override
    public void setAsGui(GameMainActivity a) {

        // remember who our activity is
        this.activityForGui = a;

        // remember the handler for the GUI thread
        this.guiHandler = new Handler();

        // Load the layout resource for the our GUI's configuration
        activityForGui.setContentView(R.layout.craps_table);

        // if the state is non=null, update the display
        if (currentGameState != null) {
            //updateDisplay();
        }
    }

}
