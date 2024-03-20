package edu.up.cs301.craps;

import edu.up.cs301.GameFramework.actionMessage.ReadyAction;
import edu.up.cs301.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;

import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.util.Random;

/**
 * A GUI of a craps-player. The GUI displays the current bets placed, and allows the
 * user to edit bets, ready up, join a game, or roll.
 *
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @author Wes Helms
 * @author Troy Carpenter
 * @author Sydney Dean
 * @author Rowena Archer
 * @version March 2024
 */
public class CrapsHumanPlayer extends GameHumanPlayer implements OnClickListener, SeekBar.OnSeekBarChangeListener {

    /* instance variables */

    // The TextView the displays the current counter value
    private TextView counterValueTextView;

    // the most recent game state, as given to us by the CounterLocalGame
    private CrapsState state;

    // the android activity that we are running
    private GameMainActivity myActivity;
    private int playerMoney;// player's money
    private int amountBet;// amount player wants to bet
    private boolean isShooter;// shooter status
    private boolean isReady; // player is ready (done placing bets)
    private int die1;
    private int die2;

    // we'll get the bet array by going to game state

    /**
     * constructor
     *
     * @param name the player's name
     */
    public CrapsHumanPlayer(String name) {
        super(name);
    }

    /**
     * Returns the GUI's top view object
     *
     * @return the top object in the GUI's view heirarchy
     */
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }
    //samba

    /**
     * sets the counter value in the text view
     */
    protected void updateDisplay() {
        // set the text in the appropriate widget
        //counterValueTextView.setText("" + state.getCounter());
    }

    /**
     * This method gets called when the user clicks a button. It
     * creates a new action to return to the parent activity.The
     * action type depends on the respective button clicked.
     *
     * @param button the button that was clicked
     */
    public void onClick(View button) {
        // if we are not yet connected to a game, ignore
        if (game == null) return;

        // Construct the action and send it to the game
        GameAction action = null;

        int anyBet = R.id.plusButton; // placeholder
        int rollButton = R.id.plusButton; //placeholder
        int readyButton = R.id.plusButton; //placeholder

        // creating and sending action
        // this conditional will later be expanded to include all bets
        if (button.getId() == anyBet) {
            action = new PlaceBetAction(this, 0, 0, 0);
        } else if (button.getId() == rollButton) {
            Random rand = new Random();
            die1 = (rand.nextInt(6) + 1);
            die2 = (rand.nextInt(6) + 1);
            action = new RollAction(this, isShooter, die1, die2, die1 + die2);
        } else if (button.getId() == readyButton) {
            action = new Ready2CrapAction(this, true, playerNum);
        }


        game.sendAction(action); // send action to the game
    }// onClick

    /**
     * This method gets called each time the money seekbar is changed
     *
     * @param seekBar  The SeekBar whose progress has changed
     * @param progress The current progress level. This will be in the
     *                 range 0..playerMoney, where min and max were set by the
     *                 money progress bar. (The default values for min
     *                 is 0 and max is 100.)
     * @param fromUser True if the progress change was initiated by the user.
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        amountBet = progress;
    }

    /**
     * callback method when we get a message (e.g., from the game)
     *
     * @param info the message
     */
    @Override
    public void receiveInfo(GameInfo info) {
        // ignore the message if it's not a CrapState message
        if (!(info instanceof CrapsState)) return;

        // update our state; then update the display
        this.state = (CrapsState) info;
        updateDisplay();
    }

    /**
     * callback method--our game has been chosen/rechosen to be the GUI,
     * called from the GUI thread
     *
     * @param activity the activity under which we are running
     */
    public void setAsGui(GameMainActivity activity) {

        // remember the activity
        this.myActivity = activity;

        // Load the layout resource for our GUI
        activity.setContentView(R.layout.counter_human_player);

        // make this object the listener for both the '+' and '-' 'buttons
        Button plusButton = (Button) activity.findViewById(R.id.plusButton);
        plusButton.setOnClickListener(this);
        Button minusButton = (Button) activity.findViewById(R.id.minusButton);
        minusButton.setOnClickListener(this);

        // remember the field that we update to display the counter's value
        this.counterValueTextView =
                (TextView) activity.findViewById(R.id.counterValueTextView);

        // if we have a game state, "simulate" that we have just received
        // the state from the game so that the GUI values are updated
        if (state != null) {
            receiveInfo(state);
        }

    }

    /*
    I don't think we need these methods anymore but
     I'm keeping these here in case I need to copy
     some code later - Rowena

    public void changeBet(View button) {
		if (the bet != 0){
		the bet.setBetAmount(amountBet);
		playerMoney = playerMoney - amountBet;
		}
		else if (the bet.betAmount != 0){
		the bet.setBetAmount(0);
		}
    }

    public void ready(View button) {
		if (button instanceof readyButton) {
			isReady = true;
		}
    }

    public void roll(View button) {
        // check if Im shooter, if yes roll dice
        if (this.state.checkCanRoll() && isShooter) {
            // randomize dice
            Random rand = new Random();
            die1 = (rand.nextInt(6) + 1);
            die2 = (rand.nextInt(6) + 1);
            //this.state.setDice(die1, die2);
        }
    }
    */

    /**
     * getters & setters
     */
    public int getPlayerMoney() {
        return playerMoney;
    }

    public int getAmountBet() {
        return amountBet;
    }

    public boolean getIsShooter() {
        return isShooter;
    }

    public boolean getIsReady() {
        return isReady;
    }

    public void setPlayerMoney(int newVal) {
        playerMoney = newVal;
    }

    public void setShooter(boolean shooterChange) {
        isShooter = shooterChange;
    }

    /**
     * Unused methods (here to satisfy OnSeekbarChangedListener)
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

}// class CrapsHumanPlayer

