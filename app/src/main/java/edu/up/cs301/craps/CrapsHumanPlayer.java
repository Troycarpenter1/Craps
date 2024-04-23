package edu.up.cs301.craps;

import edu.up.cs301.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;

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

    //hashtable holding the ID of a bet in the array and the corresponding button
    Hashtable<Integer, List<Integer>> buttontable = new Hashtable<>();

    Hashtable<Integer, String> colortable = new Hashtable<>();

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
     * @param button
     * @param id
     */
    public void bet(View button, int id) {
        Button but = (Button) button;

        Log.d("die", "amountBet: " + amountBet);
        if (amountBet > 0.0) {

            //sends the bet action to the state
            PlaceBetAction pba = new PlaceBetAction(this, this.playerId, id,
                    amountBet);
            game.sendAction(pba);

            Log.d("die", "HumanPlayer: trying to place bet");

            //bet corresponding to the button that was just pressed


            //Bet thisBet = state.getBet(this.playerId, table.get(button.getId()));
            //Log.d("bet", "this bet's name is " + thisBet.getName());

            //if the bet at the location ISN'T null
            //if () {
                //set the color if we place a bet (only if it actually placed)
                //but.setTextColor(Color.parseColor("#FFA500"));
            //}


            // }
        } else {
            //update player funds
            //TextView playerMoney = myActivity.findViewById(R.id.yourMoney); //todo I only think receiveInfo needs this
            //send remove bet action
            RemoveBetAction rba = new RemoveBetAction(this, this.playerId, id);
            Log.d("die", "trying to remove bet");
            game.sendAction(rba);

            //playerMoney.setText("$" + state.getPlayer0Funds());


            System.out.println(Bet.ALL_BET_NAMES); //get all the bet names

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
    }

    /**
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

        //check shoot button
        } else if (myActivity.findViewById(R.id.shoot) == button) {
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
    //TODO make receiveInfo less bulky
    @Override
    public void receiveInfo(GameInfo info) {

        // ignore the message if it's not a CrapState message
        if (!(info instanceof CrapsState)) return;

        // update our state; then update the display
        this.state = (CrapsState) info;

        //iterate through the bet array and highlight all buttons for bets placed
        for (int i = 0; i < 23; i++){
            Bet thisBet = state.getBet(this.playerId, i);

            //if the name isn't no bet (a bet exists)
            //if (thisBet.getName().equals("NO BET") == false){
                //get all the the buttons associated with bet at index
                List <Integer> buttons = buttontable.get(i);
                //iterate through all the buttons in the button table and find all IDs

            //at the very beginning of the game, sometimes the button is null, and that causes a crash
            if (buttons!= null) {
                for (int j = 0; j < buttons.size(); j++) {
                    Integer buttonId = buttons.get(j); //get the ID associated with the spot in the array
                    Button button = myActivity.findViewById(buttonId);

                    //if there is a bet here
                    if (thisBet.getName().equals("NO BET") == false) {
                        button.setTextColor(Color.parseColor("#FFA500"));
                    } else {
                        button.setTextColor(Color.parseColor(colortable.get(buttonId)));
                        //revert to original color
                    }
                }
            }

            //}
            //if there's no bet there

        }

        //change the drawable for the dice
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
        if (state.getPlayerTurn() == this.playerId) {
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

        //todo troy killed this. I'm worried
        //updateDisplay();

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

        //create all listeners
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

        //TODO there's gotta be a better place to put this

        /*
            Makes a hashtable mapping all spots in the bet array to their
            corresponding buttons. name is button table
         */
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

        /*
            Makes a hashtable mapping all IDs to
            the colors they should be originally
         */
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

    public int getPlayerId(){return this.playerId;}

    public boolean getIsReady() {
        return isReady;
    }

    public void setPlayerMoney(int newVal) {
        playerMoney = newVal;
    }

    public void setShooter(boolean newShooter) {
        isShooter = newShooter;
    }

    public void setAmountBet(int amountBet) {
        this.amountBet = amountBet;
    }

    public void setPlayerId(int playerId){this.playerId = playerId;}


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

