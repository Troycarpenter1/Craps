package edu.up.cs301.craps;

import edu.up.cs301.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;

import android.media.MediaPlayer;
import android.util.Log;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.View.OnClickListener;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

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

    // TextView displays the current counter value
    private TextView testResultsTextView;

    // the most recent game state, as given to us by the CounterLocalGame
    private CrapsState state;

    // the android activity that we are running
    private GameMainActivity myActivity;

    private MediaPlayer music;

    // amount player wants to bet
    private int amountBet;

    // player is ready (done placing bets)
    private boolean isReady;

    //increment bet slider changes by
    private int betIncrement = 1;

    //hashtable holding the ID of a bet in the array and the corresponding button ID
    Hashtable<Integer, List<Integer>> buttontable = new Hashtable<>();

    //hashtable holding a button ID's corresponding color
    Hashtable<Integer, String> colortable = new Hashtable<>();

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

    protected void updateDisplay(String displayText, int id) {
        testResultsTextView.setText("", TextView.BufferType.NORMAL);
    }

    /**
     * bet
     * helper method for betting
     * sends a bet action
     *
     * @param button the button that was clicked to bet
     * @param id the id of the bet in the bet class
     */
    public void bet(View button, int id) {
        Button but = (Button) button;

        Log.d("die", "amountBet: " + amountBet);
        if (amountBet > 0.0) {

            //sends bet action
            PlaceBetAction pba = new PlaceBetAction(this, this.playerNum, id,
                    amountBet);
            game.sendAction(pba);

            Log.d("die", "trying to place bet");

        } else {
            //send remove bet action
            RemoveBetAction rba = new RemoveBetAction(this, this.playerNum, id);
            game.sendAction(rba);

            Log.d("die", "trying to remove bet");
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

        //send appropriate action for each button that could be pressed
        //check ready button
        if (but.equals(myActivity.findViewById(R.id.ready))) {
            isReady = state.getPlayerReady(playerNum);
            Ready2CrapAction P1Ready = new Ready2CrapAction(this, !this.isReady, this.playerNum);
            game.sendAction(P1Ready);

        //check shoot button
        } else if (myActivity.findViewById(R.id.shoot) == button) {
            //create and send the roll action
            RollAction roll = new RollAction(this, this.playerNum);
            game.sendAction(roll);

        } else if (myActivity.findViewById(R.id.passLine1) == button) {  //pass line 1
            this.bet(myActivity.findViewById(R.id.passLine1), 1);
        } else if (myActivity.findViewById(R.id.passLine2) == button) {  //pass line 1
            this.bet(myActivity.findViewById(R.id.passLine2), 1);
        } else if (myActivity.findViewById(R.id.donPass1) == button) {  //don't pass line 1
            this.bet(myActivity.findViewById(R.id.donPass1), 2);
        } else if (myActivity.findViewById(R.id.dont_pass) == button) { //don't pass line 2
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
        } else if (myActivity.findViewById(R.id.place10) == button) {   //place10
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

        //for testing purposes display the state of the game as a string
        // (this now is also displayed in the help button)
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
     *                 is 0 and max is 1000.)
     * @param fromUser True if the progress change was initiated by the user.
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        //return if not from user
        if (!fromUser) {
            return;
        }
        //the text view that displays how much money is bet
        TextView betView = myActivity.findViewById(R.id.betAmount);

        //sets the max the bar can scroll to the players total money
        seekBar.setMax(state.getPlayerFunds(this.playerNum));

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
     * This method gets called each time a radio button (the bet increment /
     * chip buttons) is changed
     *
     * @param group     the group in which the checked radio button has changed
     * @param checkedId the unique identifier of the newly checked radio button
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

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
     * updateButton
     * helper method for receiveInfo
     *
     * Takes in a bet from the state's bet array
     * and updates the appropriate button text color.
     * on the GUI
     *
     * @param thisBet //the bet in the bet array
     * @param buttons //this list of buttons associated with that bet
     */
    public void updateButton(Bet thisBet, List<Integer> buttons){

        if (buttons == null){
            return;
        }

        //iterate through all the buttons in the button table and find all IDs
        for (int j = 0; j < buttons.size(); j++) {
            //get the ID associated with the spot in the array
            Integer buttonId = buttons.get(j);
            Button button = myActivity.findViewById(buttonId);

            //if there is a bet here, highlight the text
            if (thisBet.getAmount() != 0) {
                button.setTextColor(Color.parseColor("#FFA500"));

            //else, no bet. revert to original color
            } else {
                button.setTextColor(Color.parseColor(colortable.get(buttonId)));
            }
        }
    }

    /**
     * updateDie
     * helper for receiveInfo
     * updates one die based on the die value in the state
     * @param dieCurrValue //current value of dice to change
     * @param die //imageview of dice to change
     */
    public void updateDie(int dieCurrValue, ImageView die){

        if (dieCurrValue == 1) {
            die.setImageDrawable(myActivity.getDrawable(R.drawable.side1dice));
        } else if (dieCurrValue == 2) {
            die.setImageDrawable(myActivity.getDrawable(R.drawable.side2dice));
        } else if (dieCurrValue == 3) {
            die.setImageDrawable(myActivity.getDrawable(R.drawable.side3dice));
        } else if (dieCurrValue == 4) {
            die.setImageDrawable(myActivity.getDrawable(R.drawable.side4dice));
        } else if (dieCurrValue == 5) {
            die.setImageDrawable(myActivity.getDrawable(R.drawable.side5dice));
        } else if (dieCurrValue== 6) {
            die.setImageDrawable(myActivity.getDrawable(R.drawable.side6dice));
        }
    }

    /**
     * updateOnOff
     * helper method for receiveInfo
     * updates the onOff image appropriately based on the first roll
     * @param firstRoll
     */
    public void updateOnOff(int firstRoll){

        //if it is the first roll of turn, change the on/off bar appropriately
        ImageView onOff = myActivity.findViewById(R.id.onOff);

        if (firstRoll == 4) {
            onOff.setImageDrawable(myActivity.getDrawable(R.drawable.four));
        } else if (firstRoll == 5) {
            onOff.setImageDrawable(myActivity.getDrawable(R.drawable.five));
        } else if (firstRoll == 6) {
            onOff.setImageDrawable(myActivity.getDrawable(R.drawable.six));
        } else if (firstRoll == 8) {
            onOff.setImageDrawable(myActivity.getDrawable(R.drawable.eight));
        } else if (firstRoll == 9) {
            onOff.setImageDrawable(myActivity.getDrawable(R.drawable.nine));
        } else if (firstRoll == 10) {
            onOff.setImageDrawable(myActivity.getDrawable(R.drawable.ten));
        } else if(firstRoll==0){
            onOff.setImageDrawable(myActivity.getDrawable(R.drawable.off));
        }
    }

    /*
     * External Citation
     * Date:  13 April 2023
     * Problem: Wanted to get color from resources as an int
     * (used in updateReadyColor, updateShootColor)
     * Resource: https://stackoverflow.com/questions/5271387/how-can-i-get-
     * color-int-from-color-resource
     * Solution: Used ContextCompat
     */
    /**
     * updateReadyColor
     * helper method for receiveInfo
     * changes ready to red if player ready, black if not
     * @param isReady //whether or not human is ready according to state
     */
    public void updateReadyColor(boolean isReady){

        Button ready = myActivity.findViewById(R.id.ready);

        //changes ready to red when human ready
        if (isReady) {
            ready.setTextColor(ContextCompat.getColor(this.myActivity, R.color.red));
        }
        //else black
        else {
            ready.setTextColor(ContextCompat.getColor(this.myActivity, R.color.black));
        }

    }

    /**
     * updateShootColor
     * helper method for receiveInfo
     * updates shoot button to red if player's turn, black if not
     * @param isMyTurn //is it the player's turn
     */
    public void updateShootColor(boolean isMyTurn){

        Button shoot = myActivity.findViewById(R.id.shoot);
        //if it's your turn then the shooter button is red
        if (isMyTurn) {
            shoot.setTextColor(ContextCompat.getColor(this.myActivity, R.color.red));
        }
        //else black
        else {
            shoot.setTextColor(ContextCompat.getColor(this.myActivity, R.color.black));
        }
    }

    /**
     * receiveInfo
     * callback method when we get a message (e.g., from the game)
     * changes color of buttons appropriately
     *
     * @param info the message
     */
    //TODO make receiveInfo less bulky
    @Override
    public void receiveInfo(GameInfo info) {

        // ignore the message if it's not a CrapState message
        if (!(info instanceof CrapsState)) return;

        // update our state; then update the display
        this.state = (CrapsState) info;

        /*
            iterate through the bet array and change color for all
            bets placed or removed
            note the hashtables are instance variables and initialized
            in the setAsGui method
            written by Sydney
        */
        for (int i = 0; i < 23; i++){

            //get the bet at this index
            Bet thisBet = state.getBet(this.playerNum, i);

            //get all the the buttons (button IDs) associated with bet at index
            List <Integer> buttons = buttontable.get(i);

            //update the color of this button's text
            updateButton(thisBet, buttons);
        }

        //change the drawable for the dice
        ImageView dice1 = myActivity.findViewById(R.id.dice1);
        ImageView dice2 = myActivity.findViewById(R.id.dice2);

        updateDie(state.getDie1CurrVal(), dice1);
        updateDie(state.getDie2CurrVal(), dice2);

        //update the onOff based on first roll
        //todo: get this to stay on a single image instead of change every roll
        updateOnOff(state.getFirstRoll());

        //change shoot color based on roll
        updateShootColor(state.getPlayerTurn() == this.playerNum);

        //update color of ready button
        updateReadyColor(state.getPlayerReady(this.playerNum));

        //update textView showing player funds
        TextView myMoney = myActivity.findViewById(R.id.yourMoney);
        myMoney.setText("$" + state.getPlayerFunds(this.playerNum));

    } //receiveInfo

    /**
     *  fillButtontable
     *  Fills hashtable buttontable mapping all spots in the bet array to their
     *  corresponding buttons.
     *
     *  This hashtable needs to exist for updateButton
     *  to work
     *
     *  Called in setAsGui
     */
    public void fillButtontable(){

        List<Integer> passlist = new ArrayList<Integer>();
        passlist.add(R.id.passLine1);
        passlist.add(R.id.passLine2);
        buttontable.put(1, passlist);

        List<Integer> dontpasslist = new ArrayList<Integer>();
        dontpasslist.add(R.id.dont_pass);
        dontpasslist.add(R.id.donPass1);
        buttontable.put(2, dontpasslist);

        List<Integer> comelist = new ArrayList<Integer>();
        comelist.add(R.id.come);
        buttontable.put(3, comelist);

        List<Integer> fieldlist = new ArrayList<Integer>();
        fieldlist.add(R.id.field);
        buttontable.put(4, fieldlist);

        List<Integer> place4list = new ArrayList<Integer>();
        place4list.add(R.id.place4);
        buttontable.put(5, place4list);

        List<Integer> place5list = new ArrayList<Integer>();
        place5list.add(R.id.place5);
        buttontable.put(6, place5list);

        List<Integer> place6list = new ArrayList<Integer>();
        place6list.add(R.id.place6);
        buttontable.put(7, place6list);

        List<Integer> place8list = new ArrayList<Integer>();
        place8list.add(R.id.place8);
        buttontable.put(8, place8list);

        List<Integer> place9list = new ArrayList<Integer>();
        place9list.add(R.id.place9);
        buttontable.put(9, place9list);

        List<Integer> place10list = new ArrayList<Integer>();
        place10list.add(R.id.place10);
        buttontable.put(10, place10list);

        List<Integer> clist = new ArrayList<Integer>();
        clist.add(R.id.cButton1);
        clist.add(R.id.cButton2);
        clist.add(R.id.cButton3);
        clist.add(R.id.cButton4);
        clist.add(R.id.cButton5);
        clist.add(R.id.cButton6);
        clist.add(R.id.cButton7);
        buttontable.put(11, clist);

        List<Integer> elist = new ArrayList<Integer>();
        elist.add(R.id.eButton1);
        elist.add(R.id.eButton2);
        elist.add(R.id.eButton3);
        elist.add(R.id.eButton4);
        elist.add(R.id.eButton5);
        elist.add(R.id.eButton6);
        elist.add(R.id.eButton7);
        buttontable.put(12, elist);

        List<Integer> sevenslist = new ArrayList<Integer>();
        sevenslist.add(R.id.sevensBet);
        buttontable.put(13, sevenslist);

        List<Integer> pair2slist = new ArrayList<Integer>();
        pair2slist.add(R.id.pair2s);
        buttontable.put(14, pair2slist);

        List<Integer> pair3slist = new ArrayList<Integer>();
        pair3slist.add(R.id.pair3s);
        buttontable.put(15, pair3slist);

        List<Integer> pair4slist = new ArrayList<Integer>();
        pair4slist.add(R.id.pair4s);
        buttontable.put(16, pair4slist);

        List<Integer> pair5slist = new ArrayList<Integer>();
        pair5slist.add(R.id.pair5s);
        buttontable.put(17, pair5slist);

        List<Integer> twoAndOnelist = new ArrayList<Integer>();
        twoAndOnelist.add(R.id.twoAndOne);
        buttontable.put(18, twoAndOnelist);

        List<Integer> pair1slist = new ArrayList<Integer>();
        pair1slist.add(R.id.pair1s);
        buttontable.put(19, pair1slist);

        List<Integer> pair6slist = new ArrayList<Integer>();
        pair6slist.add(R.id.pair6s);
        buttontable.put(20, pair6slist);

        List<Integer> fiveAndSixlist = new ArrayList<Integer>();
        fiveAndSixlist.add(R.id.fiveAndSix);
        buttontable.put(21, fiveAndSixlist);

        List<Integer> crapslist = new ArrayList<Integer>();
        crapslist.add(R.id.craps);
        buttontable.put(22, crapslist);

    }

    /**
     * fillColortable
     * called in setAsGui
     * Fills hashtable color table mapping all IDs to
     * the colors they should be originally.
     *
     * This hashtable needs to exist for updateButton
     * to work
     *
     */
    public void fillColortable(){

        colortable.put(R.id.come, "#D61818"); //red

        //yellow
        colortable.put(R.id.field, "#FFD700");
        colortable.put(R.id.place4, "#FFD700");
        colortable.put(R.id.place5, "#FFD700");
        colortable.put(R.id.place6, "#FFD700");
        colortable.put(R.id.place8, "#FFD700");
        colortable.put(R.id.place9, "#FFD700");
        colortable.put(R.id.place10, "#FFD700");

        //white
        colortable.put(R.id.passLine1, "#FFFFFF");
        colortable.put(R.id.passLine2, "#FFFFFF");

        colortable.put(R.id.dont_pass, "#FFFFFF");
        colortable.put(R.id.donPass1, "#FFFFFF");

        colortable.put(R.id.cButton1, "#FFFFFF");
        colortable.put(R.id.cButton2, "#FFFFFF");
        colortable.put(R.id.cButton3, "#FFFFFF");
        colortable.put(R.id.cButton4, "#FFFFFF");
        colortable.put(R.id.cButton5, "#FFFFFF");
        colortable.put(R.id.cButton6, "#FFFFFF");
        colortable.put(R.id.cButton7, "#FFFFFF");

        colortable.put(R.id.eButton1, "#FFFFFF");
        colortable.put(R.id.eButton2, "#FFFFFF");
        colortable.put(R.id.eButton3, "#FFFFFF");
        colortable.put(R.id.eButton4, "#FFFFFF");
        colortable.put(R.id.eButton5, "#FFFFFF");
        colortable.put(R.id.eButton6, "#FFFFFF");
        colortable.put(R.id.eButton7, "#FFFFFF");

        colortable.put(R.id.sevensBet, "#FFFFFF");
        colortable.put(R.id.pair2s, "#FFFFFF");
        colortable.put(R.id.pair3s, "#FFFFFF");
        colortable.put(R.id.pair4s, "#FFFFFF");
        colortable.put(R.id.pair5s, "#FFFFFF");
        colortable.put(R.id.pair1s, "#FFFFFF");
        colortable.put(R.id.pair6s, "#FFFFFF");
        colortable.put(R.id.twoAndOne, "#FFFFFF");
        colortable.put(R.id.fiveAndSix, "#FFFFFF");
        colortable.put(R.id.craps, "#FFFFFF");
    }


    /**
     * setAsGui
     * callback method--our game has been chosen/rechosen to be the GUI,
     * called from the GUI thread
     *
     * creates all listeners
     * calls helpers to fill the buttontable and the colortable hashtables
     * starts music
     *
     * @param activity the activity under which we are running
     */
    public void setAsGui(GameMainActivity activity) {

        // remember the activity
        this.myActivity = activity;

        //fill buttontable and colortable
        fillButtontable();
        fillColortable();

        /*
         External Citation
         Date: 23 April 2024
         Problem: could not figure out how to play music
         Resource:
         https://www.geeksforgeeks.org/how-to-add-audio-files-to-android
         -app-in-android-studio/
         Solution: I used the example code from this post.
         */
        music = MediaPlayer.create(myActivity, R.raw.soldierofdance);
        music.start();
        music.setLooping(true);

        activity.setContentView(R.layout.craps_table);

        //CREATE ALL LISTENERS
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

