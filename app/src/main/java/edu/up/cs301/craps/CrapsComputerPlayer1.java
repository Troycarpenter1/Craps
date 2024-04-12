package edu.up.cs301.craps;

import java.util.Random;

import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.players.GameComputerPlayer;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.GameFramework.utilities.Tickable;

/**
 * A computer-version of a counter-player.  Since this is such a simple game,
 * it just sends "+" and "-" commands with equal probability, at an average
 * rate of one per second. 
 * 
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @version September 2013
 *
 * @author Troy Carpenter
 * @author Rowena Archer
 * @author Sydney Dean
 * @author Wes Helms
 */
public class CrapsComputerPlayer1 extends GameComputerPlayer implements Tickable {

	//instance variables
	private GameMainActivity myActivity;
	private int playerMoney;// player's money
	private double amountBet;// amount player wants to bet
	private boolean isShooter;// shooter status
	private boolean isReady; // player is ready (done placing bets)
	private int die1;
	private int die2;

	CrapsState crapsState;

	//TODO should I have a local version of this bet array? didn't see one in human player
	//I'll put one here for now just for me

	int numTypes = 23; //TODO this should be a static variable in the Bet class
	                   //TODO so delete this later
	                   //represents how many types of bets there are
	//private Bet [] bets = new Bet[numTypes];


    /**
     * Constructor for objects of class CounterComputerPlayer1
     * 
     * @param name
     * 		the player's name
     */
    public CrapsComputerPlayer1(String name) {
        // invoke superclass constructor
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

		//initializes the bet array
		//TODO delete/edit if we don't end up using the local array
		//commented out until we merge
		/*
		int num_bets = 23;
		for (int i = 0; i < num_bets; i++){
			bets[i] = new Bet(0, 1, i);
			//somehow set the name to the name in the public static array
			//TODO add a way to set name in the bet? or is there something
			//TODO that automatically in bet??

		}
		*/
    }

	//roll
	//this part is copied directly from Rowena's code
	public void roll() {

		//create a roll action
		RollAction roll= new RollAction(this,this.isShooter, (CrapsMainActivity) myActivity);
		game.sendAction(roll);

	}

	//place a random set of bets on a turn, then ready up

	/**
	 * takeTurn
	 * called when it's time to take bets (shooter has rolled, or everyone
	 * is ready and they are the shooter)
	 * shoots if they're the shooter, then
	 * places a random number (between 0-5) of random bets
	 *
	 */
	public void takeTurn(){

		roll(); //roll the dice

		//Sydney commented this out for testing the changing turns
		/*
		//make a random amount of bets
		Random rand = new Random();
		int numBets = rand.nextInt(5); //make 0 to 4 bets
		for (int i = 0; i < numBets; i++){
			bet();
		}
		*/

		//ready up
		this.isReady = true;

	}

	/**
	 * bet
	 * places a bet of a semi-random amount on a random spot
	 * adds the bet to the local bet array
	 */
	public void bet(){
		Random rand = new Random();

		//TODO this won't compile until merged, commented out

		/*

		//choose random bet amount
		//the random bet is between 1/6 and 1/2 of player's total money
		int cap = (2*this.playerMoney)/6;
		this.amountBet =  rand.nextInt(cap) + this.playerMoney/6;

		//random bet Type, adjust for more sophisticated AI
		//have to confirm with Troy, assuming
		//betID = spot in strings array
		int betID = rand.nextInt(numTypes);

		//create a new bet and add to the local bet array
		this.bets[betID] = new Bet(this.amountBet, 1, betID);

		 */

	}
    
    /**
     * callback method--game's state has changed
     * 
     * @param info
     * 		the information (presumably containing the game's state)
     */
	@Override
	protected void receiveInfo(GameInfo info) {
		// Do nothing, as we ignore all state in deciding our next move. It
		// depends totally on the timer and random numbers.
		if (!(info instanceof CrapsState)){
			return;
		}
		crapsState = (CrapsState)info;
		//TODO uh you can't hardcode this eventually
		if (crapsState.getPlayerTurn() == 1){
			takeTurn();
		}
	}
	
	/**
	 * callback method: the timer ticked
	 */
	protected void timerTicked() {
		// 5% of the time, increment or decrement the counter
		if (Math.random() >= 0.05) return; // do nothing 95% of the time

		// "flip a coin" to determine whether to increment or decrement
		boolean move = Math.random() >= 0.5;
		
		// send the move-action to the game
		game.sendAction(new CrapsMoveAction(this, move));
	}
}
