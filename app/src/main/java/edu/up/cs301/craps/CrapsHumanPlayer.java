package edu.up.cs301.craps;

import edu.up.cs301.GameFramework.actionMessage.ReadyAction;
import edu.up.cs301.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
public class CrapsHumanPlayer extends GameHumanPlayer implements OnClickListener,
        SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {

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

    private int betIncrement = 1;

    // we'll get the bet array by going to game state

    /**
     * constructor
     *
     * @param name the player's name
     */
    public CrapsHumanPlayer(String name) {
        super(name);
        //for now, sets human player to shooter by default when initialized - syd
        this.isShooter = true;
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

        if (button.equals(myActivity.findViewById(R.id.ready))) { //checks if the button pressed is the ready button
            //Log.d("TEST", "ready");
            Ready2CrapAction P1Ready = new Ready2CrapAction(this, true, 0);
            //Ready2CrapAction P2Ready = new Ready2CrapAction(this, true, 1);
            game.sendAction(P1Ready);
            //game.sendAction(P2Ready);
        } else if (myActivity.findViewById(R.id.shoot) == button) {
            //changed this to not pass in true always
            RollAction roll = new RollAction(this, this.isShooter, (CrapsMainActivity) myActivity);
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
        //checks if the progress was changed by the user (good) or if it was updated using this method plz return
        if (!fromUser) {
            return;
        }
        //the text view that displays how much money is bet
        TextView betView = myActivity.findViewById(R.id.betAmount);
        //sets the max the bar can scroll to the players total money
        seekBar.setMax((int) state.getPlayer0Funds());

        //remainder when progress is divided by the bet increment
        int r = progress % betIncrement;
        //set amount bet to the difference
        amountBet = progress - r;
        //update seekbar
        seekBar.setProgress(amountBet);
        //update text
        betView.setText("$" + amountBet);
    }

    /**
     * This method gets called each time a radio button thing happens
     *
     * @param group     the group in which the checked radio button has changed
     * @param checkedId the unique identifier of the newly checked radio button
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        SeekBar seeker = myActivity.findViewById(R.id.betAmountSelector);

        //checks which radio button in the group is being changed
        if (checkedId == R.id.oneChip) {
            betIncrement = 1;
        } else if (checkedId == R.id.fiveChip) {
            betIncrement = 5;
        } else if (checkedId == R.id.tenChip) {
            betIncrement = 10;
        } else if (checkedId == R.id.hundredChip) {
            betIncrement = 100;
        }
        Log.d("TEST", "" + betIncrement);
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

        SeekBar betSelector = myActivity.findViewById(R.id.betAmountSelector);
        betSelector.setOnSeekBarChangeListener(this);

        //this.testResultsTextView = (TextView) activity.findViewById(R.id.editTextTest);

        //Makes the radio group and its buttons all work cool
        RadioGroup chips = (RadioGroup) activity.findViewById(R.id.betSetGroup);
        chips.setOnCheckedChangeListener(this);
        //makes the $1 button work
        RadioButton oneChip = (RadioButton) activity.findViewById(R.id.oneChip);
        oneChip.setOnCheckedChangeListener(this);
        //makes the $5 button work
        RadioButton twoChip = (RadioButton) activity.findViewById(R.id.fiveChip);
        twoChip.setOnCheckedChangeListener(this);
        //makes the $10 button work
        RadioButton redChip = (RadioButton) activity.findViewById(R.id.tenChip);
        redChip.setOnCheckedChangeListener(this);
        //makes the $5 button work
        RadioButton blueChip = (RadioButton) activity.findViewById(R.id.hundredChip);
        blueChip.setOnCheckedChangeListener(this);

        //makes the next implemented button work
        //Button newButton = (Button) activity.findViewById(R.id.nextButton);
        //nextButton.setOnClickListener(this);

        // remember the field that we update to display the counter's value
        this.testResultsTextView =
                (TextView) activity.findViewById(R.id.counterValueTextView);

        // if we have a game state, "simulate" that we have just received
        // the state from the game so that the GUI values are updated
        if (state != null) {
            receiveInfo(state);
        }
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}// class CrapsHumanPlayer

