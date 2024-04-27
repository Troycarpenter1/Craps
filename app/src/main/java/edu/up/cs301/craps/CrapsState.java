package edu.up.cs301.craps;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Random;

import edu.up.cs301.GameFramework.infoMessage.GameState;

/**
 * This contains the state for the Craps game. The state consist the player and
 * their money,and the state of the dice
 * <p>
 * Not implementing select dice at the current time: will add if time (see
 * actions.txt) join is being handled automatically by the game framework so
 * not implementing join button consolidated place/remove bet into single method
 * <p>
 * selecting the bet is always legal and is in the player class
 *
 * @author Troy Carpenter
 * @author Rowena Archer
 * @author Wes Helms
 * @author Sydney Dean
 * @version April 2024
 */
public class CrapsState extends GameState {

    //for network play
    private static final long serialVersionUID = 1;

    //the players in the game, their current net worth, and ready status
    private int playerTurn;
    private int funds[] = new int[2];
    private boolean ready[] = new boolean[2];

    //x controls player, y controls bet
    protected Bet[][] bets = new Bet[2][23];

    //the dice every roll
    private int die1CurrVal;
    private int die2CurrVal;
    private int dieTotal;
    private int firstDieShot; //the total of the first roll the shooter rolls

    //on off status of the game
    private boolean offOn;
    private boolean isFirstRoll;

    public static final boolean ON = true;
    public static final boolean OFF = false;

    /*
    purpose is to prevent one roll counting double
    false = a new roll has been made
    true = we're still on the 7 from last time
     */
    private boolean playerSwitched;


    /**
     * CrapsState
     * constructor
     */
    public CrapsState() {
        this.playerTurn = 0;
        this.setDice(0, 0);
        this.offOn = OFF;
        this.playerSwitched = false;
        this.isFirstRoll = true;
        this.firstDieShot = 0;
        // iterates through a master 2d array and makes all bets for each player
        for (int p = 0; p < bets.length; p++) { // iterates through number of players
            this.funds[p] = 1000; // initializes all players funds
            this.ready[p] = false; // initializes all players ready status
            for (int b = 0; b < bets[p].length; b++) { // iterates through all bet IDs
                this.bets[p][b] = new Bet(b);
            }
        }
        // makes the 2nd player ready by default in case there is no player 2
        this.ready[1] = true;
    }

    /**
     * CrapsState
     * copy constructor, it's currently a deep copy since no objects are used as variables
     *
     * @param crap the other crap we want to copy
     */
    public CrapsState(CrapsState crap) {
        this.playerTurn = crap.playerTurn;
        this.setDice(crap.die1CurrVal, crap.die2CurrVal);
        this.offOn = crap.offOn;
        this.playerSwitched = crap.playerSwitched;
        this.isFirstRoll = crap.isFirstRoll;
        this.firstDieShot = crap.firstDieShot;
        //iterates through all arrays of player data (mainly bets)
        for (int p = 0; p < bets.length; p++) {
            this.funds[p] = crap.funds[p]; //copies all player funds
            this.ready[p] = crap.ready[p]; //copies all players ready status
            //uses the copy constructor of the bet class for all bets
            for (int b = 0; b < bets[p].length; b++) {
                this.bets[p][b] = new Bet(crap.bets[p][b]);
            }
        }
    }


    // getter methods for instance variables
    public int getPlayerTurn() {
        return this.playerTurn;
    }

    public Bet getBet(int playerId, int index) {
        return this.bets[playerId][index];
    }

    public int getFirstRoll() {
        return this.firstDieShot;
    }

    public int getPlayerFunds(int playerId) {
        return funds[playerId];
    }

    public int getDie1CurrVal() {
        return this.die1CurrVal;
    }

    public int getDie2CurrVal() {
        return this.die2CurrVal;
    }

    public boolean getPlayerReady(int playerId) {
        return this.ready[playerId];
    }

    //setters

    /**
     * setDice
     * sets both dice sets the total after it finds the sum
     *
     * @param die1Val the first dice to set
     * @param die2Val the other dice to set
     */
    public void setDice(int die1Val, int die2Val) {
        this.die1CurrVal = die1Val;
        this.die2CurrVal = die2Val;
        this.dieTotal = this.die1CurrVal + this.die2CurrVal;
    }

    /**
     * changeTurn
     * switches whose turn it is
     * only works for 2 players at the moment
     */
    public void changeTurn() {
        this.playerTurn = 1 - this.playerTurn;
    }

    /**
     * setPlayerFunds
     *
     * @param playerId    the id of the player we are changing their money
     * @param playerFunds the new funds to update
     */
    public void setPlayerFunds(int playerId, int playerFunds) {
        this.funds[playerId] = playerFunds;
    }

    /**
     * toString
     * Creates the string value of ALL of the information this class stores
     *
     * @return the tale of the game
     */
    @NonNull
    public String toString() {
        String player0 = this.toString(0);
        String player1 = this.toString(1);
        String gameON = this.offOn ? "ON" : "OFF";
        String gameStats = "The game is " + gameON +
                " The current dice are " + this.die1CurrVal + " and " + this.die2CurrVal +
                " which total to " + this.dieTotal +
                " The first dice roll of this shooter was " + this.firstDieShot +
                " the current shooter is player" + this.playerTurn;
        String gameStory = gameStats + "\nAll Player Stats: \n" + player0 + "\n" + player1;
        return gameStory.trim(); //snip, snip
    }

    /**
     * toString
     * Creates all of the information the game has on a specified player id
     *
     * @param id the player that is being reported into a string
     * @return the string value of the information of a given player
     */
    public String toString(int id) {
        //creating helpful string to be concat with the final string that will be returned
        String readyOrNot = this.ready[id] ? "READY" : "NOT READY";
        String betString = "";
        //iterates through the strings of all of the player of this
        // id's bets and adds them to the player string
        for (int b = 0; b < this.bets[id].length; b++) {
            if (this.bets[id][b].getAmount() != 0) { //checks if this bet in the array has any money bet on it
                betString = betString + ("\t" + this.bets[id][b].toString() + ", \n");
            }
        }
        //checks if the String list of bets contains any bets
        if (betString.length() < 2) {
            betString = "NO BETS PLACED";
        }
        return "Player" + id + " is " + readyOrNot +
                " has $" + this.funds[id] +
                " and has the following bets:\n" + betString;
    }

    /*
     * Actions.txt checkers
     * see header comment for omitted methods
     */

    /**
     * called when a player wants to roll
     * only for two players at this time
     *
     * @return boolean
     */
    public boolean checkCanRoll() {
        if (this.ready[0] && this.ready[1]) {
            return true;
            //player will update the Game State from here
        }
        return false;
    }

    /**
     * placeBet
     * checks to see if bet is valid
     * changes the bet in the GameState bet array
     * if sender is ready, they can't bet
     *
     * @param action the place bet action that is being passed into the craps game state
     * @return boolean value that dictates if this action is a legal move
     */
    public boolean placeBet(PlaceBetAction action) {
        //Is it the player's turn?

        //Does the player have enough money to bet? if not return
        if (action.betAmount > funds[action.playerId]) {
            return false;
        }
        //is the player ready? if so, return
        if (ready[action.playerId]) {
            return false;
        }

        System.out.println("The bet sender is not ready. Proceeding");

        //Is the bet ID a valid point in the array?
        //23 is how many types of bets there are
        if (action.betID > 22 || action.betID < 0) {
            return false;
        }

        //1. Change the state to add the new bet
        //2. a bet's bet ID is the same as it's position in the array
        //3. get the bet from the proper location based on the ID
        //4. adds the bet's new amount to it's current amount
        this.bets[action.playerId][action.betID].setBetAmount
                (this.bets[action.playerId][action.betID].getAmount() + action.betAmount);

        //set player fundsSystem
        this.setPlayerFunds(action.playerId, this.funds[action.playerId] - action.betAmount);
        Log.d("die", "player " + action.playerId + "funds changed to " + this.funds[action.playerId]);

        //note that we will eventually initialize all bets in the array to have
        //the correct IDs and names before the game is started.
        Log.d("die", "PLACED BET bet ID: " + action.betID + ", betAmount: " + action.betAmount);

        return true;
    }//placeBet

    /**
     * removeBet
     * checks to see if there is a bet to remove
     * changes the bet in the GameState bet array
     *
     * @param action the remove bet action that is being passed to the craps game state
     * @return boolean value that dictates if this action is a legal move
     */
    public boolean removeBet(RemoveBetAction action) {

        //Does the player have enough money?
        if (bets[action.playerId][action.betID].getAmount() <= 0) {
            return false;
        }

        //player shouldn't be able to remove bet if they're already ready
        if (ready[action.playerId]) {
            return false;
        }

        //add bet amount back to human's funds
        this.setPlayerFunds(action.playerId, this.funds[action.playerId] +
                bets[action.playerId][action.betID].getAmount());

        //adjust the bet's amount
        this.bets[action.playerId][action.betID].setBetAmount(0); //'removes' the bet (set to 0)

        return true;
    }//removeBet

    /**
     * ready
     * set player's ready to what is sent in the action
     *
     * @param action the Ready2CrapAction action that is being passed to the craps game state
     * @return boolean value that dictates if this action is a legal move
     */
    public boolean ready(Ready2CrapAction action) {
        /*
        If ready then changes appropriate player to ready
        Note: this is only for 2 players right now
         */
        if (action.playerID == 0) {
            this.ready[0] = action.isReady;
            return true;
        } else if (action.playerID == 1) {
            this.ready[1] = action.isReady;
            return true;
        }
        return false;
    }//Ready2CrapAction


    /**
     * payoutOnetime
     * This method pays out all of the bets that are paid every roll (if won)
     *
     * @param action the roll action being passed to the craps game state
     */
    public void payoutOnetime(RollAction action) {
        for (int p = 0; p < bets.length; p++) {
            //sets to look at only bets that pay out after a single roll
            for (int b = 11; b < bets[p].length; b++) {
                // calculates how much money the player has won
                int newFunds = this.funds[action.playerId] +
                        this.bets[p][b].payoutBet(this.die1CurrVal, this.die2CurrVal,
                                this.dieTotal, this.firstDieShot);
                // updates the players funds
                this.setPlayerFunds(action.playerId, newFunds);
                //removes the bet
                this.bets[p][b].setBetAmount(0);
            }
        }
    }

    /**
     * payoutAccordance
     * This method pays out all of the bets that last as long as the game is ON (if won)
     *
     * @param action the roll action being passed to the craps game state
     */
    public void payoutAccordance(RollAction action) {
        for (int p = 0; p < bets.length; p++) {
            //sets to look at only bets that pay out continuously through a round
            for (int b = 0; b < 12; b++) {
                //removes the lasting bet depending on on/off
                if (!offOn) {
                    this.bets[p][b].setBetAmount(0);
                } else {
                    // calculates how much money the player has won
                    int newFunds = this.funds[action.playerId] +
                            this.bets[p][b].payoutBet(this.die1CurrVal, this.die2CurrVal,
                                    this.dieTotal, this.firstDieShot);
                    // updates the players funds
                    this.setPlayerFunds(action.playerId, newFunds);
                }
            }
        }
    }

    /**
     * roll
     * if player is shooter they can roll
     *
     * @param action the roll action being passed to the craps game state
     * @return boolean value that dictates if this action is a legal move
     */
    public boolean roll(RollAction action) {

        //checks if both players are ready
        if (!(this.checkCanRoll())) {
            return false;
        }
        //checks if it is the shooters turn
        if (action.playerId != this.playerTurn) {
            return false;
        }

        //updates the values of the die
        Random rand = new Random();
        this.setDice(rand.nextInt(6) + 1, rand.nextInt(6) + 1);

        //checks if this is the first round and updates the first roll
        //or if the shooter just lost (player switched)
        if (this.firstDieShot == 0 || playerSwitched) {
            //updates the first roll of the round
            this.firstDieShot = this.dieTotal;
        }
        if (!isFirstRoll) {
            payoutAccordance(action);
            payoutOnetime(action);
        }

        /*
        sets the value for the on/off button and makes sure that
        the roll is the first of the "round"
         */
        if (this.offOn == OFF && this.isFirstRoll) {
            //makes sure the roll is a valid place bet
            if ((this.dieTotal > 3 && this.dieTotal < 11) && this.dieTotal != 7) {
                /*
                sets the first roll variable,
                and sets the other booleans to appropriate values
                 */
                this.isFirstRoll = false;
                this.firstDieShot = this.dieTotal;
                this.offOn = ON;
                //pays any bets made before on/off initialized
                payoutAccordance(action);
                payoutOnetime(action);
            }
        } else if (this.offOn == ON) {
            if (this.dieTotal == firstDieShot) {
                //resets instance variables for the next round
                this.offOn = OFF;
                this.isFirstRoll = true;
                this.firstDieShot = 0;

                //pays out and removes all bets upon round end
                payoutOnetime(action);
                payoutAccordance(action);
                for (int p = 0; p < bets.length; p++) {
                    for (int b = 0; b < bets[p].length; b++) {
                        bets[p][b].setBetAmount(0);
                    }
                }
            }
        }

        //change playerSwitched to false after roll
        this.playerSwitched = false;

        //reset both player's ready after roll
        this.ready[0] = false;
        this.ready[1] = false;

        //checks if the shooter wins or loses
        if ((this.dieTotal == 7 || this.firstDieShot == 2 || this.firstDieShot == 3) && !playerSwitched) {
            Log.d("die", "SWITCH SHOOTER!");
            this.changeTurn();
            Log.d("die", "Switching to player: " + this.playerTurn);
            //set playerSwitched to true since the shooter lost
            this.playerSwitched = true;
            this.firstDieShot = 0;
            this.isFirstRoll = true;
            this.offOn = OFF;
            //pays out and removes all bets upon turn switching
            payoutOnetime(action);
            payoutAccordance(action);
            for (int p = 0; p < bets.length; p++) {
                for (int b = 0; b < bets[p].length; b++) {
                    bets[p][b].setBetAmount(0);
                }
            }
        }

        return true;
    }//rollAction

}