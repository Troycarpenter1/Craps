package edu.up.cs301.craps;

import edu.up.cs301.GameFramework.actionMessage.ReadyAction;
import edu.up.cs301.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;

import android.util.Log;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.View.OnClickListener;

import androidx.annotation.ColorInt;

import androidx.core.content.ContextCompat;

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
 * @version April 2024
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
    private int amountBet;// amount player wants to bet
    private boolean isReady; // player is ready (done placing bets)
    private int playerId;
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
        //this.isShooter = true;

    }

    /**
     * Returns the GUI's top view object
     *
     * @return the top object in the GUI's view heirarchy
     */
    public View getTopView() {
        return myActivity.findViewById(R.id.top_test_gui_layout);
    }

    /*
        protected void updateDisplay(String displayText) {
            testResultsTextView.setText(testResultsTextView.getText() + "\n" + displayText, TextView.BufferType.NORMAL);
        }
     */

    protected void updateDisplay(String displayText, int id) {
        testResultsTextView.setText("", TextView.BufferType.NORMAL);
    }


    /**
     * bet
     * helper method for betting
     *
     * @param button the button that was clicked to bet
     * @param id the id of the bet in the bet class
     */
    public void bet(View button, int id) {
        Button but = (Button) button;
        int prevAmountBet;

        Log.d("die", "amountBet: " + amountBet);
        if (amountBet > 0.0) {
            //  if (state.getBet(0, id).getAmount() == 0.0) {
            but.setTextColor(Color.parseColor("#FFA500"));
            //sends the bet action to the state
            PlaceBetAction pba = new PlaceBetAction(this, this.playerId, id,
                    amountBet);
            game.sendAction(pba);

            Log.d("die", "trying to place bet");

            // }
        } else {
            //update player funds
            //TextView playerMoney = myActivity.findViewById(R.id.yourMoney); //todo I only think receiveInfo needs this
            //send remove bet action
            RemoveBetAction rba = new RemoveBetAction(this, this.playerId, id);
            Log.d("die", "trying to remove bet");
            game.sendAction(rba);

            //playerMoney.setText("$" + state.getPlayer0Funds());

            //change the color for removing the bet
            if (but.equals(myActivity.findViewById(R.id.come))) {
                but.setTextColor(Color.parseColor("#D61818")); //sets the button red
            } else if (
                    but.equals(myActivity.findViewById(R.id.field)) ||
                            but.equals(myActivity.findViewById(R.id.place4)) ||
                            but.equals(myActivity.findViewById(R.id.place5)) ||
                            but.equals(myActivity.findViewById(R.id.place6)) ||
                            but.equals(myActivity.findViewById(R.id.place8)) ||
                            but.equals(myActivity.findViewById(R.id.place9)) ||
                            but.equals(myActivity.findViewById(R.id.place10))
            ) {
                but.setTextColor(Color.parseColor("#FFD700")); //sets the buttons to yellow
            } else {
                but.setTextColor(Color.parseColor("#FFFFFF")); //sets the button to white
            }
        }
    } //bet

    /**
     * onClick
     * This method gets called when the user clicks a button. It
     * creates a new action to return to the parent activity.The
     * action type depends on the respective button clicked.
     *
     * @param button the button that was clicked
     */
    public void onClick(View button) {
        Button but = (Button) button;
        // if we are not yet connected to a game, ignore
        if (game == null) return;
        //checks all of the buttons that could be pressed
        if (but.equals(myActivity.findViewById(R.id.ready))) { //checks if the button pressed is the ready button
            //removed isReady here
            Ready2CrapAction P1Ready = new Ready2CrapAction(this, !this.isReady, this.playerId);
            game.sendAction(P1Ready);
        } else if (myActivity.findViewById(R.id.shoot) == button) { // checks shoot button

            //change the color for removing the bet after rolling for all buttons that are bets

            //resets come bet colors
            Button come = (Button) myActivity.findViewById(R.id.come);      //come
            come.setTextColor(Color.parseColor("#D61818")); //red

            //resets field bet colors
            Button field = (Button) myActivity.findViewById(R.id.field);    //field
            field.setTextColor(Color.parseColor("#FFD700"));  //yellow

            //resets place bets colors
            Button place4 = (Button) myActivity.findViewById(R.id.place4);  //place4
            place4.setTextColor(Color.parseColor("#FFD700"));  //yellow
            Button place5 = (Button) myActivity.findViewById(R.id.place5);  //place5
            place5.setTextColor(Color.parseColor("#FFD700"));  //yellow
            Button place6 = (Button) myActivity.findViewById(R.id.place6);  //place6
            place6.setTextColor(Color.parseColor("#FFD700"));  //yellow
            Button place8 = (Button) myActivity.findViewById(R.id.place8);  //place8
            place8.setTextColor(Color.parseColor("#FFD700"));  //yellow
            Button place9 = (Button) myActivity.findViewById(R.id.place9);  //place9
            place9.setTextColor(Color.parseColor("#FFD700"));  //yellow
            Button place10 = (Button) myActivity.findViewById(R.id.place10);  //place10
            place10.setTextColor(Color.parseColor("#FFD700"));  //yellow

            //resets the pass line colors
            Button pass1 = (Button) myActivity.findViewById(R.id.passLine1);//pass1
            pass1.setTextColor(Color.parseColor("#FFFFFF"));  //white
            Button pass2 = (Button) myActivity.findViewById(R.id.passLine2);//pass2
            pass2.setTextColor(Color.parseColor("#FFFFFF"));  //white

            //resets the don't pass line colors
            Button dontpass1 = (Button) myActivity.findViewById(R.id.donPass1);//don't pass1
            dontpass1.setTextColor(Color.parseColor("#FFFFFF"));  //white
            Button dontpass2 = (Button) myActivity.findViewById(R.id.dont_pass);//don't pass2
            dontpass2.setTextColor(Color.parseColor("#FFFFFF"));  //white

            //resets the C button colors
            Button c1 = (Button) myActivity.findViewById(R.id.cButton1);//c1
            c1.setTextColor(Color.parseColor("#FFFFFF"));  //white
            Button c2 = (Button) myActivity.findViewById(R.id.cButton2);//c2
            c2.setTextColor(Color.parseColor("#FFFFFF"));  //white
            Button c3 = (Button) myActivity.findViewById(R.id.cButton3);//c3
            c3.setTextColor(Color.parseColor("#FFFFFF"));  //white
            Button c4 = (Button) myActivity.findViewById(R.id.cButton4);//c4
            c4.setTextColor(Color.parseColor("#FFFFFF"));  //white
            Button c5 = (Button) myActivity.findViewById(R.id.cButton5);//c5
            c5.setTextColor(Color.parseColor("#FFFFFF"));  //white
            Button c6 = (Button) myActivity.findViewById(R.id.cButton6);//c6
            c6.setTextColor(Color.parseColor("#FFFFFF"));  //white
            Button c7 = (Button) myActivity.findViewById(R.id.cButton7);//c7
            c7.setTextColor(Color.parseColor("#FFFFFF"));  //white

            //resets the E button colors
            Button e1 = (Button) myActivity.findViewById(R.id.eButton1);//e1
            e1.setTextColor(Color.parseColor("#FFFFFF"));  //white
            Button e2 = (Button) myActivity.findViewById(R.id.eButton2);//e2
            e2.setTextColor(Color.parseColor("#FFFFFF"));  //white
            Button e3 = (Button) myActivity.findViewById(R.id.eButton3);//e3
            e3.setTextColor(Color.parseColor("#FFFFFF"));  //white
            Button e4 = (Button) myActivity.findViewById(R.id.eButton4);//e4
            e4.setTextColor(Color.parseColor("#FFFFFF"));  //white
            Button e5 = (Button) myActivity.findViewById(R.id.eButton5);//e5
            e5.setTextColor(Color.parseColor("#FFFFFF"));  //white
            Button e6 = (Button) myActivity.findViewById(R.id.eButton6);//e6
            e6.setTextColor(Color.parseColor("#FFFFFF"));  //white
            Button e7 = (Button) myActivity.findViewById(R.id.eButton7);//e7
            e7.setTextColor(Color.parseColor("#FFFFFF"));  //white

            //resets special bets
            Button seven = (Button) myActivity.findViewById(R.id.sevensBet);//seven bet
            seven.setTextColor(Color.parseColor("#FFFFFF"));  //white
            Button pair2 = (Button) myActivity.findViewById(R.id.pair2s);//Pair 2s bet
            pair2.setTextColor(Color.parseColor("#FFFFFF"));  //white
            Button pair3 = (Button) myActivity.findViewById(R.id.pair3s);//Pair 3s bet
            pair3.setTextColor(Color.parseColor("#FFFFFF"));  //white
            Button pair4 = (Button) myActivity.findViewById(R.id.pair4s);//Pair 4s bet
            pair4.setTextColor(Color.parseColor("#FFFFFF"));  //white
            Button pair5 = (Button) myActivity.findViewById(R.id.pair5s);//Pair 5s bet
            pair5.setTextColor(Color.parseColor("#FFFFFF"));  //white
            Button pair1 = (Button) myActivity.findViewById(R.id.pair1s);//Pair 1s bet
            pair1.setTextColor(Color.parseColor("#FFFFFF"));  //white
            Button pair6 = (Button) myActivity.findViewById(R.id.pair6s);//Pair 6s bet
            pair6.setTextColor(Color.parseColor("#FFFFFF"));  //white
            Button two1 = (Button) myActivity.findViewById(R.id.twoAndOne);//2 and 1
            two1.setTextColor(Color.parseColor("#FFFFFF"));  //white
            Button five6 = (Button) myActivity.findViewById(R.id.fiveAndSix);//5 and 6
            five6.setTextColor(Color.parseColor("#FFFFFF"));  //white
            Button crap = (Button) myActivity.findViewById(R.id.craps);//crap bet
            crap.setTextColor(Color.parseColor("#FFFFFF"));  //white

            //create and send the roll action
            RollAction roll = new RollAction(this, this.playerId);
            game.sendAction(roll);

        } else if (myActivity.findViewById(R.id.passLine1) == button) {    //pass line 1
            this.bet(myActivity.findViewById(R.id.passLine1), 1);
        } else if (myActivity.findViewById(R.id.passLine2) == button) {    //pass line 1
            this.bet(myActivity.findViewById(R.id.passLine2), 1);
        } else if (myActivity.findViewById(R.id.donPass1) == button) {    //don't pass line 1
            this.bet(myActivity.findViewById(R.id.donPass1), 2);
        } else if (myActivity.findViewById(R.id.dont_pass) == button) {   //don't pass line 2
            this.bet(myActivity.findViewById(R.id.dont_pass), 2);
        } else if (myActivity.findViewById(R.id.come) == button) {      //come
            this.bet(button, 3);
        } else if (myActivity.findViewById(R.id.field) == button) {     //field
            this.bet(button, 4);
        } else if (myActivity.findViewById(R.id.place4) == button) {    //place4
            this.bet(button, 5);
        } else if (myActivity.findViewById(R.id.place5) == button) {    //place5
            this.bet(button, 6);
        } else if (myActivity.findViewById(R.id.place6) == button) {    //place6
            this.bet(button, 7);
        } else if (myActivity.findViewById(R.id.place8) == button) {    //place8
            this.bet(button, 8);
        } else if (myActivity.findViewById(R.id.place9) == button) {    //place9
            this.bet(button, 9);
        } else if (myActivity.findViewById(R.id.place10) == button) {    //place10
            this.bet(button, 10);
        } else if (myActivity.findViewById(R.id.cButton1) == button ||
                myActivity.findViewById(R.id.cButton2) == button ||
                myActivity.findViewById(R.id.cButton3) == button ||
                myActivity.findViewById(R.id.cButton4) == button ||
                myActivity.findViewById(R.id.cButton5) == button ||
                myActivity.findViewById(R.id.cButton6) == button ||
                myActivity.findViewById(R.id.cButton7) == button) {     // ALL C Buttons
            this.bet(button, 11);
        } else if (myActivity.findViewById(R.id.eButton1) == button ||
                myActivity.findViewById(R.id.eButton2) == button ||
                myActivity.findViewById(R.id.eButton3) == button ||
                myActivity.findViewById(R.id.eButton4) == button ||
                myActivity.findViewById(R.id.eButton5) == button ||
                myActivity.findViewById(R.id.eButton6) == button ||
                myActivity.findViewById(R.id.eButton7) == button) {     // ALL E Buttons
            this.bet(button, 12);
        } else if (myActivity.findViewById(R.id.sevensBet) == button) { //bet 7
            this.bet(button, 13);
        } else if (myActivity.findViewById(R.id.pair2s) == button) {    //bet pair 2s
            this.bet(button, 14);
        } else if (myActivity.findViewById(R.id.pair3s) == button) {    //bet pair 3s
            this.bet(button, 15);
        } else if (myActivity.findViewById(R.id.pair4s) == button) {    //bet pair 4s
            this.bet(button, 16);
        } else if (myActivity.findViewById(R.id.pair5s) == button) {    //bet pair 5s
            this.bet(button, 17);
        } else if (myActivity.findViewById(R.id.twoAndOne) == button) { //bet 21
            this.bet(button, 18);
        } else if (myActivity.findViewById(R.id.pair1s) == button) {    //snake eyes
            this.bet(button, 19);
        } else if (myActivity.findViewById(R.id.pair6s) == button) {    //bet pair 6s
            this.bet(button, 20);
        } else if (myActivity.findViewById(R.id.fiveAndSix) == button) {//bet 56
            this.bet(button, 21);
        } else if (myActivity.findViewById(R.id.craps) == button) {     //crap
            this.bet(button, 22);
        }

        //for testing purposes display the state of the game as a string (this now is also displayed in the help button)
        Log.d("string", state.toString());

    }// onClick

    /**
     * onProgressChanged
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

        seekBar.setMax((int) state.getPlayerFunds(this.playerId));


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
     * onCheckedChanged
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
     * receiveInfo
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


        //change the drawable
        ImageView dice1 = myActivity.findViewById(R.id.dice1);
        ImageView dice2 = myActivity.findViewById(R.id.dice2);

        if (state.getDie1CurrVal() == 1) {
            dice1.setImageDrawable(myActivity.getDrawable(R.drawable.side1dice));
        } else if (state.getDie1CurrVal() == 2) {
            dice1.setImageDrawable(myActivity.getDrawable(R.drawable.side2dice));
        } else if (state.getDie1CurrVal() == 3) {
            dice1.setImageDrawable(myActivity.getDrawable(R.drawable.side3dice));
        } else if (state.getDie1CurrVal() == 4) {
            dice1.setImageDrawable(myActivity.getDrawable(R.drawable.side4dice));
        } else if (state.getDie1CurrVal() == 5) {
            dice1.setImageDrawable(myActivity.getDrawable(R.drawable.side5dice));
        } else if (state.getDie1CurrVal() == 6) {
            dice1.setImageDrawable(myActivity.getDrawable(R.drawable.side6dice));
        }
        //updates the ImageView of the second die
        if (state.getDie2CurrVal() == 1) {
            dice2.setImageDrawable(myActivity.getDrawable(R.drawable.side1dice));
        } else if (state.getDie2CurrVal() == 2) {
            dice2.setImageDrawable(myActivity.getDrawable(R.drawable.side2dice));
        } else if (state.getDie2CurrVal() == 3) {
            dice2.setImageDrawable(myActivity.getDrawable(R.drawable.side3dice));
        } else if (state.getDie2CurrVal() == 4) {
            dice2.setImageDrawable(myActivity.getDrawable(R.drawable.side4dice));
        } else if (state.getDie2CurrVal() == 5) {
            dice2.setImageDrawable(myActivity.getDrawable(R.drawable.side5dice));
        } else if (state.getDie2CurrVal() == 6) {
            dice2.setImageDrawable(myActivity.getDrawable(R.drawable.side6dice));
        }

        //todo: get this to stay on a single image instead of change every roll
        //if it is the first roll of turn, change the on/off bar appropriately
        ImageView onOff = myActivity.findViewById(R.id.onOff);

        //if (state.getOffOn()) {
        if (state.getFirstRoll() == 4) {
            onOff.setImageDrawable(myActivity.getDrawable(R.drawable.four));
        } else if (state.getFirstRoll() == 5) {
            onOff.setImageDrawable(myActivity.getDrawable(R.drawable.five));
        } else if (state.getFirstRoll() == 6) {
            onOff.setImageDrawable(myActivity.getDrawable(R.drawable.six));
        } else if (state.getFirstRoll() == 8) {
            onOff.setImageDrawable(myActivity.getDrawable(R.drawable.eight));
        } else if (state.getFirstRoll() == 9) {
            onOff.setImageDrawable(myActivity.getDrawable(R.drawable.nine));
        } else if (state.getFirstRoll() == 10) {
            onOff.setImageDrawable(myActivity.getDrawable(R.drawable.ten));
        }
        //}else {
        if(state.getFirstRoll()==0){
            onOff.setImageDrawable(myActivity.getDrawable(R.drawable.off));
        }


        /*
         * External Citation
         * Date:  13 April 2023
         * Problem: Wanted to get color from resources as an int
         * Resource: https://stackoverflow.com/questions/5271387/how-can-i-get-color-int-from-color-resource
         * Solution: Used ContextCompat
         */

        //change shoot color based on roll
        //this doesn't work when player turn is based on order
        Button shoot = myActivity.findViewById(R.id.shoot);
        //if it's your turn then the shooter button is red
        if (state.getPlayerTurn() == 0) {
            shoot.setTextColor(ContextCompat.getColor(this.myActivity, R.color.red));
        }
        //else black
        else {
            shoot.setTextColor(ContextCompat.getColor(this.myActivity, R.color.black));
        }

        Button ready = myActivity.findViewById(R.id.ready);
        //changes ready to red when human ready
        TextView myMoney = myActivity.findViewById(R.id.yourMoney);
        if (state.getPlayerReady(this.playerId)) {
            ready.setTextColor(ContextCompat.getColor(this.myActivity, R.color.red));
        }
        //else black
        else {
            ready.setTextColor(ContextCompat.getColor(this.myActivity, R.color.black));
        }

        myMoney.setText("$" + state.getPlayerFunds(this.playerId));

    } //receiveInfo

    /**
     * setAsGui
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

        //pass lines
        Button passLine = activity.findViewById(R.id.passLine1);
        passLine.setOnClickListener(this);
        Button passLine2 = activity.findViewById(R.id.passLine2);
        passLine2.setOnClickListener(this);

        //don't pass line
        Button dontPass = activity.findViewById(R.id.donPass1);
        dontPass.setOnClickListener(this);
        Button dontPass1 = activity.findViewById(R.id.dont_pass);
        dontPass1.setOnClickListener(this);

        Button come = activity.findViewById(R.id.come);
        come.setOnClickListener(this);

        Button field = activity.findViewById(R.id.field);
        field.setOnClickListener(this);

        Button place4 = activity.findViewById(R.id.place4);
        place4.setOnClickListener(this);

        Button place5 = activity.findViewById(R.id.place5);
        place5.setOnClickListener(this);

        Button place6 = activity.findViewById(R.id.place6);
        place6.setOnClickListener(this);

        Button place8 = activity.findViewById(R.id.place8);
        place8.setOnClickListener(this);

        Button place9 = activity.findViewById(R.id.place9);
        place9.setOnClickListener(this);

        Button place10 = activity.findViewById(R.id.place10);
        place10.setOnClickListener(this);

        //c bet listeners
        Button c1 = activity.findViewById(R.id.cButton1);
        c1.setOnClickListener(this);
        Button c2 = activity.findViewById(R.id.cButton2);
        c2.setOnClickListener(this);
        Button c3 = activity.findViewById(R.id.cButton3);
        c3.setOnClickListener(this);
        Button c4 = activity.findViewById(R.id.cButton4);
        c4.setOnClickListener(this);
        Button c5 = activity.findViewById(R.id.cButton5);
        c5.setOnClickListener(this);
        Button c6 = activity.findViewById(R.id.cButton6);
        c6.setOnClickListener(this);
        Button c7 = activity.findViewById(R.id.cButton7);
        c7.setOnClickListener(this);

        //e bet listeners
        Button e1 = activity.findViewById(R.id.eButton1);
        e1.setOnClickListener(this);
        Button e2 = activity.findViewById(R.id.eButton2);
        e2.setOnClickListener(this);
        Button e3 = activity.findViewById(R.id.eButton3);
        e3.setOnClickListener(this);
        Button e4 = activity.findViewById(R.id.eButton4);
        e4.setOnClickListener(this);
        Button e5 = activity.findViewById(R.id.eButton5);
        e5.setOnClickListener(this);
        Button e6 = activity.findViewById(R.id.eButton6);
        e6.setOnClickListener(this);
        Button e7 = activity.findViewById(R.id.eButton7);
        e7.setOnClickListener(this);

        Button seven = activity.findViewById(R.id.sevensBet);
        seven.setOnClickListener(this);

        //pair bet listeners
        Button pair2 = activity.findViewById(R.id.pair2s);
        pair2.setOnClickListener(this);
        Button pair3 = activity.findViewById(R.id.pair3s);
        pair3.setOnClickListener(this);
        Button pair4 = activity.findViewById(R.id.pair4s);
        pair4.setOnClickListener(this);
        Button pair5 = activity.findViewById(R.id.pair5s);
        pair5.setOnClickListener(this);
        Button pair6 = activity.findViewById(R.id.pair6s);
        pair6.setOnClickListener(this);
        Button pair1 = activity.findViewById(R.id.pair1s);
        pair1.setOnClickListener(this);

        Button twenty1 = activity.findViewById(R.id.twoAndOne);
        twenty1.setOnClickListener(this);

        Button fifty6 = activity.findViewById(R.id.fiveAndSix);
        fifty6.setOnClickListener(this);

        Button crap = activity.findViewById(R.id.craps);
        crap.setOnClickListener(this);


        SeekBar betSelector = activity.findViewById(R.id.betAmountSelector);
        betSelector.setOnSeekBarChangeListener(this);

        //this.testResultsTextView =
        //         (TextView) activity.findViewById(R.id.editTextTest);

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
    } //setAsGui

    /**
     * getters & setters
     */
    public int getAmountBet() {
        return amountBet;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }


    /**
     * Unused methods  to satisfy implementing all of the listeners in this class
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

