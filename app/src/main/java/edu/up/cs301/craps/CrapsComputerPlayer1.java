package edu.up.cs301.craps;

import android.util.Log;


import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.players.GameComputerPlayer;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.GameFramework.utilities.Tickable;

/**
 * Simple Computer Player, at this point should always be in player 1 spot (not player 0).
 * Doesn't place any bets when the human is the shooter.
 * Will bet about every roll when it is the shooter, $100 on the pass line each time, until
 * it runs out of money.
 *
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @author Troy Carpenter
 * @author Rowena Archer
 * @author Sydney Dean
 * @author Wes Helms
 * @version September 2013
 */
public class CrapsComputerPlayer1 extends GameComputerPlayer implements Tickable {

    //instance variables
    private GameMainActivity myActivity;
    private double playerMoney;// my money
    private double amountBet;// amount I wants to bet
    private boolean isShooter;// my shooter status
    private boolean isReady; // my ready status
    private int die1;
    private int die2;
    public boolean isHuman;
    CrapsState crapsState;

    private int playerId;

    /**
     * Constructor for objects of class CounterComputerPlayer1
     *
     * @param name the player's name
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
    }

    /** roll
     *  roll the dice on the game-board
     */
    public void roll() {

        //create a roll action then send it
        Log.d("die", "Computer player shooter? " + this.isShooter);
        RollAction roll = new RollAction(this, this.playerId);
        game.sendAction(roll);

    }

    /** ready
     *  tells the game I'm (comp player) is ready
     */
    public void ready(){
        //make myself ready
        isReady = true;
        //create a ready action then send it
        Ready2CrapAction ready = new Ready2CrapAction(this, true, this.playerId);
        game.sendAction(ready);
    }


    //place a random set of bets on a turn, then ready up
    /**
     * takeTurn
     * called when it's time to take bets (shooter has rolled, or everyone
     * is ready and they are the shooter)
     * shoots if they're the shooter, then
     * places a random number (between 0-5) of random bets
     */
    public void takeTurn() {
        // roll the dice
        placeBet();
        //ready();

        roll();
        //state will unready both players after roll

		/* Sydney commented this out for testing the changing turns
		 *
         * //make a random amount of bets
		 * Random rand = new Random();
		 * int numBets = rand.nextInt(5); //make 0 to 4 bets
		 * for (int i = 0; i < numBets; i++){
		 *	bet();
		 *}
		 */
        Log.d("computer", "Computer Money: $" + this.playerMoney);
    }

    /**
     * bet
     * places a bet of a semi-random amount on a random spot
     * adds the bet to the local bet array
     */
    public void placeBet() {
        /*
         * Computer Betting
         * Rowena's Version
         */

        // look at how much money I have according to my copy of the game state
        playerMoney = crapsState.getPlayer1Funds();

        // if I'm already ready, do not bet again
        if (isReady){
            System.out.println("placeBet returned, computer is already ready");
            return;
        }

        // if I somehow have less than $100 to spend, then bet all the money I have left
        if (playerMoney < 100){
            this.amountBet = playerMoney;
        }else{
            // Set my amount to bet to $100
            this.amountBet = 100.0;
        }

        // bet on the pass line every time
        PlaceBetAction pba = new PlaceBetAction(this, this.playerId, 1, amountBet, false);
        this.game.sendAction(pba);
        System.out.println("computer trying to bet");

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

        // if is shooter take my turn
        if (crapsState.getPlayerTurn() == this.playerId) {
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


    public boolean getIsShooter() {
        return isShooter;
    }

    public boolean getIsHuman(){return this.isHuman;}

    public void setPlayerId(int playerId){this.playerId = playerId;}

}



