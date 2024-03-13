package edu.up.cs301.craps;

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
 * 
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 *
 * @author Wes Helms
 * @author Troy Carpenter
 * @author Sydney Dean
 * @author Rowena Archer
 *
 * @version March 2024
 */
public class CrapsHumanPlayer extends GameHumanPlayer implements OnClickListener {

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
	 * @param name
	 * 		the player's name
	 */
	public CrapsHumanPlayer(String name) {
		super(name);
	}

	/**
	 * Returns the GUI's top view object
	 * 
	 * @return
	 * 		the top object in the GUI's view heirarchy
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
		counterValueTextView.setText("" + state.getCounter());
	}

	/**
	 * this method gets called when the user clicks the '+' or '-' button. It
	 * creates a new CounterMoveAction to return to the parent activity.
	 * 
	 * @param button
	 * 		the button that was clicked
	 */
	public void onClick(View button) {
		// if we are not yet connected to a game, ignore
		if (game == null) return;

		// Construct the action and send it to the game
		GameAction action = null;
		if (button.getId() == R.id.plusButton) {
			// plus button: create "increment" action
			action = new CrapsMoveAction(this, true);
		}
		else if (button.getId() == R.id.minusButton) {
			// minus button: create "decrement" action
			action = new CrapsMoveAction(this, false);
		}
		else {
			// something else was pressed: ignore
			return;
		}
		
		game.sendAction(action); // send action to the game
	}// onClick
	
	/**
	 * callback method when we get a message (e.g., from the game)
	 * 
	 * @param info
	 * 		the message
	 */
	@Override
	public void receiveInfo(GameInfo info) {
		// ignore the message if it's not a CrapState message
		if (!(info instanceof CrapsState)) return;
		
		// update our state; then update the display
		this.state = (CrapsState)info;
		updateDisplay();
	}
	
	/**
	 * callback method--our game has been chosen/rechosen to be the GUI,
	 * called from the GUI thread
	 * 
	 * @param activity
	 * 		the activity under which we are running
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

	/**
	 * This method gets called when the user clicks/places a bet.
	 * Update bet in array of bets
	 * @param button
	 * 		the button that was clicked
	 */
	public void bet(View button){

		// TODO: change "the bet" to a reference to my bet array (in game state)


		// if bet is != 0:
		// set this bet amount to amountBet
		// subtract bet amount from player money
		// else:
		// do nothing

		/*
		if (the bet != 0){
		the bet.setBetAmount(amountBet);
		playerMoney = playerMoney - amountBet;
		}
		else{
		do nothing

		 */

	}

	/**
	 * This method gets called when the user clicks on an already existing bet.
	 * Removes an existing bet and sends it to the game.
	 */
	public void removeBet(View button){
		// if the bet here is 0:
		// set respective bet in myBetArray to 0

		/*
		if (the bet.betAmount != 0){
		the bet.setBetAmount(0);
		}
		else{
		do nuthin
		}
		 */
	}

	/**
	 * This method is called when the user changes the seekbar for the amount
	 * they want to bet
	 *  ~basically an OnProgressChanged method
	 */
	public void selectBet(SeekBar seekBar, int progress, boolean fromUser){
		/*
		  if (seekbar.getid() == R.id.moneySeekBar){
		  amountBet = progress;
		  }
		 */
	}

	/**
	 * sets player to ready if ready button is clicked
	 * @param button
	 */
	public void ready(View button){
		/*
		if (button instanceof readyButton) {
			isReady = true;
		}
		*/
	}

	public void roll() {
		// check if Im shooter, otherwise return
		if (isShooter) {
			// randomize dice
			Random rand = new Random();
			die1 = (rand.nextInt(6) + 1);
			die2 = (rand.nextInt(6) + 1);
		}
	}

	/**
	 * getters & setters
	 */
	public int getPlayerMoney(){
		return playerMoney;
	}

	public int getDie1() {
		return die1;
	}

	public int getDie2() {
		return die2;
	}
	public boolean getIsShooter(){
		return isShooter;
	}
	public boolean getIsReady(){
		return isReady;
	}
	public void setPlayerMoney(int newVal){
		playerMoney = newVal;
	}

}// class CounterHumanPlayer

