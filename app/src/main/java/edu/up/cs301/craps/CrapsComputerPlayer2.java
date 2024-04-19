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
* A computer-version of a counter-player.  Since this is such a simple game,
* it just sends "+" and "-" commands with equal probability, at an average
* rate of one per second. This computer player does, however, have an option to
* display the game as it is progressing, so if there is no human player on the
* device, this player will display a GUI that shows the value of the counter
* as the game is being played.
* 
* @author Steven R. Vegdahl
* @author Andrew M. Nuxoll
* @version September 2013
*/
public class CrapsComputerPlayer2 extends CrapsComputerPlayer1 {
	
	/*
	 * instance variables
	 */
	// If this player is running the GUI, the activity (null if the player is
	// not running a GUI).
	private Activity activityForGui = null;
	
	// If this player is running the GUI, the widget containing the counter's
	// value (otherwise, null);
	private TextView counterValueTextView = null;
	
	// If this player is running the GUI, the handler for the GUI thread (otherwise
	// null)
	private Handler guiHandler = null;

	//instance variables
	private double playerMoney;// my money
	private double amountBet;// amount I want to bet
	private boolean isShooter;// my shooter status
	private boolean isReady; // my ready status
	private int die1;
	private int die2;
	CrapsState crapsState;

	
	/**
	 * constructor
	 * 
	 * @param name
	 * 		the player's name
	 */
	public CrapsComputerPlayer2(String name) {
		super(name);

		// start the timer, ticking 20 times per second
		getTimer().setInterval(50);
		getTimer().start();

		//initialize instance variables
		this.playerMoney = 1000;
		this.amountBet = 200.0;
		this.isShooter = false;
		this.isReady = false;
		this.die1 = 0;
		this.die2 = 0;
	}




	/**
	 * callback method--game's state has changed
	 *
	 * @param info the information (presumably containing the game's state)
	 */
	@Override
	protected void receiveInfo(GameInfo info) {
		// Do nothing, as we ignore all state in deciding our next move. It
		// depends totally on the timer and random numbers.
		if (!(info instanceof CrapsState)) {
			return;
		}
		crapsState = (CrapsState) info;


		// if my turn take my turn
		if (crapsState.getPlayerTurn() == 1) {
			//have to delay BEFORE we take turn or we won't be able to see the 7 rolled

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			takeTurn();

		}

		//TODO we'll have to rethink this isShooter variable in the future, but right
		//now I'm putting this here just to ensure it's accurate.

		if (crapsState.getPlayerTurn() == 1){
			this.isShooter = true;
		}
		else {
			this.isShooter = false;
		}

	}

	/**
	 * bet
	 * places a bet of a semi-random amount on a random spot
	 * adds the bet to the local bet array
	 */
	@Override
	public void placeBet() {
		/*
		 * Computer Betting
		 * Rowena's Version
		 */

		// look at how much money I have according to my copy of the game state
		playerMoney = crapsState.getPlayer1Funds();

		// if I'm already ready, do not bet again
		if (isReady){
			return;
		}

		// if I somehow have less than $100 to spend, then bet all the money I have left
		if (playerMoney < 100){
			this.amountBet = playerMoney;
		}else{
			// Set my amount to bet to $100
			this.amountBet = 100.0;
		}

		// make a random bet
		Random rand = new Random();
		int betNum = rand.nextInt(23) + 1;
		PlaceBetAction pba = new PlaceBetAction(this, playerNum, betNum, amountBet);
		this.game.sendAction(pba);

		System.out.println("placed a computer bet");


		// Old code
		/*
		 * //Random rand = new Random();

		 * //not necessary for dumb AI
		 * //choose random bet amount
		 * //the random bet is between 1/6 and 1/2 of player's total money
		 * int cap = (2*this.playerMoney)/6;
		 * this.amountBet =  rand.nextInt(cap) + this.playerMoney/6;
		 *
		 * //random bet Type, adjust for more sophisticated AI
		 * //have to confirm with Troy, assuming
		 * //betID = spot in strings array
		 * int betID = rand.nextInt(numTypes);

		 * //create a new bet and add to the local bet array
		 * this.bets[betID] = new Bet(this.amountBet, 1, betID);
		 */
	}


	/** Old Code */

	/** 
	 * sets the counter value in the text view
	 *  */
//	private void updateDisplay() {
//		// if the guiHandler is available, set the new counter value
//		// in the counter-display widget, doing it in the Activity's
//		// thread.
//		if (guiHandler != null) {
//			guiHandler.post(
//					new Runnable() {
//						public void run() {
//						if (counterValueTextView != null && currentGameState != null) {
//							counterValueTextView.setText("" + currentGameState.getCounter());
//						}
//					}});
//		}
//	}
	
	/**
	 * Tells whether we support a GUI
	 * 
	 * @return
	 * 		true because we support a GUI
	 */
	public boolean supportsGui() {
		return true;
	}
	
//	/**
//	 * callback method--our player has been chosen/rechosen to be the GUI,
//	 * called from the GUI thread.
//	 *
//	 * @param a
//	 * 		the activity under which we are running
//	 */
//	@Override
//	public void setAsGui(GameMainActivity a) {
//
//		// remember who our activity is
//		this.activityForGui = a;
//
//		// remember the handler for the GUI thread
//		this.guiHandler = new Handler();
//
//		// Load the layout resource for the our GUI's configuration
//		activityForGui.setContentView(R.layout.counter_human_player);
//
//		// remember who our text view is, for updating the counter value
//		this.counterValueTextView =
//				(TextView) activityForGui.findViewById(R.id.counterValueTextView);
//
//		// disable the buttons, since they will have no effect anyway
//		Button plusButton = (Button)activityForGui.findViewById(R.id.plusButton);
//		plusButton.setEnabled(false);
//		Button minusButton = (Button)activityForGui.findViewById(R.id.minusButton);
//		minusButton.setEnabled(false);
//
//		// if the state is non=null, update the display
//		if (crapsState != null) {
//			//updateDisplay();
//		}
//	}

}
