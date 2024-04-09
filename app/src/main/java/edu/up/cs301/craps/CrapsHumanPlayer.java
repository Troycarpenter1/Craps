package edu.up.cs301.craps;

import edu.up.cs301.GameFramework.actionMessage.ReadyAction;
import edu.up.cs301.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private TextView testResultsTextView;

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
        return myActivity.findViewById(R.id.top_test_gui_layout);
    }
    //samba

    /**
     * sets the counter value in the text view
     */
    protected void updateDisplay() {
        // set the text in the appropriate widget
        //counterValueTextView.setText("" + state.getCounter());
    }

    protected void updateDisplay(String displayText) {
        testResultsTextView.setText(testResultsTextView.getText() + "\n" + displayText, TextView.BufferType.NORMAL);
    }

    protected void updateDisplay(String displayText, int id) {
        testResultsTextView.setText("", TextView.BufferType.NORMAL);
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
        //Log.d("TEST", String.valueOf(button.getId()));
        CrapsState firstInstance = new CrapsState();

        if (button.equals(myActivity.findViewById(R.id.ready))) { //checks if the button pressed is the ready button GONE WRONG
            //Log.d("TEST", "ready");
            Ready2CrapAction P1Ready = new Ready2CrapAction(this, true, 0);
            //Ready2CrapAction P2Ready = new Ready2CrapAction(this, true, 1);
            firstInstance.ready(P1Ready);
            //firstInstance.ready(P2Ready);
            game.sendAction(P1Ready);
            //game.sendAction(P2Ready);
        } else if (button.equals(myActivity.findViewById(R.id.shoot))) { //checks if the button pressed is the shoot button
            //Log.d("TEST", "shoot");
            Random rand = new Random();
            int die1 = rand.nextInt(6) + 1;
            int die2 = rand.nextInt(6) + 1;
            int totes = die1 + die2;
            RollAction roll = new RollAction(this, true, die1, die2, totes, (CrapsMainActivity) myActivity);
            firstInstance.roll(roll);
            game.sendAction(roll);
        }
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

        activity.setContentView(R.layout.craps_table);
        //makes the shooter button work
        Button shooter = (Button) activity.findViewById(R.id.shoot);
        shooter.setOnClickListener(this);
        //makes the ready button work
        Button join = (Button) activity.findViewById(R.id.ready);
        join.setOnClickListener(this);


        //makes the $1 button work
        Button oneChip = (Button) activity.findViewById(R.id.oneChip);
        oneChip.setOnClickListener(this);
        //makes the $5 button work
        Button twoChip = (Button) activity.findViewById(R.id.fiveChip);
        twoChip.setOnClickListener(this);
        //makes the $10 button work
        Button redChip = (Button) activity.findViewById(R.id.tenChip);
        redChip.setOnClickListener(this);
        //makes the $5 button work
        Button blueChip = (Button) activity.findViewById(R.id.hundredChip);
        blueChip.setOnClickListener(this);

        //makes the next implemented button work
        //Button newButton = (Button) activity.findViewById(R.id.nextButton);
        //nextButton.setOnClickListener(this);

    }

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

    public void setShooter(boolean newShooter) {
        isShooter = newShooter;
    }

    /**
     * Unused methods (here to satisfy implementing OnSeekbarChangedListener)
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

}// class CrapsHumanPlayer

