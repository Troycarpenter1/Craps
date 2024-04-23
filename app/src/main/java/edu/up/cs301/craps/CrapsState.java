package edu.up.cs301.craps;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import java.util.Random;

import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.GameFramework.players.GameComputerPlayer;
import edu.up.cs301.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.GameFramework.players.GamePlayer;

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
 * @version March 2024
 */

public class CrapsState extends GameState {

    //the players in the game, and their current net worth
    private int playerTurn; //right now 0 is human, 1 is comp player
    private double funds[] = new double[2];
    private boolean ready[] = new boolean[2];

    //x controls player, y controls bet
    private Bet[][] bets = new Bet[2][23];

    //the dice every roll
    private int die1CurrVal;
    private int die2CurrVal;
    private int dieTotal;
    private int firstDieShot; //the total of the first roll the shooter rolls
    private boolean offOn;

    //purpose is to prevent one roll counting double
    boolean playerSwitched; //local variable used to indicate if the turn has just been switched (7 was the last number rolled)
    //if false > a new roll has been made
    //true > we're still on the 7 from last time

    //ctor
    public CrapsState() {
        this.playerTurn = 0;
        this.setDice(0, 0);
        this.offOn = false;
        this.playerSwitched = false;
        this.firstDieShot = 0;
        // iterates through a master 2d array and makes all bets for each player
        for (int p = 0; p < bets.length; p++) { // iterates through number of players
            this.funds[p] = 1000; // initializes all players funds
            this.ready[p] = false; // initializes all players ready status
            for (int b = 0; b < bets[p].length; b++) { // iterates through all bet IDs
                this.bets[p][b] = new Bet();
                //this.bets[p][b] = new Bet(0, 1.0, b);
            }
        }
    }

    //copy constructor, currently is deep since no objects are used as variables atm
    public CrapsState(CrapsState crap) {
        this.playerTurn = crap.playerTurn;
        this.dieTotal = crap.dieTotal;
        this.die1CurrVal = crap.die1CurrVal;
        this.die2CurrVal = crap.die2CurrVal;
        this.funds[0] = crap.funds[0];
        this.funds[1] = crap.funds[1];
        this.ready[0] = crap.ready[0];
        this.ready[1] = crap.ready[1];
        this.offOn = crap.offOn;
        this.playerSwitched = crap.playerSwitched;
        this.firstDieShot = crap.firstDieShot;
        //uses the copy constructor of the bet class
        for (int p = 0; p < bets.length; p++) {
            for (int b = 0; b < bets[p].length; b++) {
                this.bets[p][b] = new Bet(crap.bets[p][b]);
            }
        }
    }

    //getters for instance variables
    public int getPlayerTurn() {
        return this.playerTurn;
    }

    public Bet getBet(int playerId, int betId) {
        return bets[playerId][betId];
    }

    public double getPlayerFunds(int playerId) {
        return funds[playerId];
    }

    public int getDie1CurrVal() {
        return this.die1CurrVal;
    }

    public int getDie2CurrVal() {
        return this.die2CurrVal;
    }

    public int getDieTotal() {
        return this.dieTotal;
    }

    public int getFirstDieShot() {
        return this.firstDieShot;
    }

    public boolean getPlayerSwitched() {
        return this.playerSwitched;
    }

    public boolean getPlayerReady(int playerId) {
        return this.ready[playerId];
    }

    public boolean isOffOn() {
        return offOn;
    }

    //setters

    /**
     * setDice
     * sets dice and finds sum
     *
     * @param die1Val
     * @param die2Val
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
        Log.d("die", "Player turn just switched to:" + this.playerTurn);
    }

    public void setPlayerFunds(int playerId, double playerFunds) {
        this.funds[playerId] = playerFunds;
    }

    /**
     * toString
     * Creates the string value of ALL of the information this class stores
     * @return the tale of the game
     */
    @Override
    public String toString() {
        String player0 = this.toString(0);
        String player1 = this.toString(1);
        String gameON = this.offOn ? "ON" : "OFF";
        String gameStats = "The game is " + gameON + " The current dice are " + this.die1CurrVal + " and " + this.die2CurrVal
                + " which total to " + this.dieTotal + " The first dice roll of this shooter was " + this.firstDieShot;
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
        //iterates through the strings of all of the player of this id's bets and adds them to the player string
        for (int b = 0; b < this.bets[id].length; b++) {
            if (this.bets[id][b].getID() != 0) { //checks if this bet in the array has any valuable data
                betString = betString + ("\t" + this.bets[id][b].toString() + ", \n");
            }
        }
        if (betString.length() < 4) {
            betString = "NO BETS PLACED";
        }
        return "Player" + id + " is " + readyOrNot + " has $" + this.funds[id] + " and has the following bets:\n" + betString;
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
     * @param action
     * @return
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

        //Change the state to add the new bet

        //assumes that a bet's bet ID is the same as it's position in the array
        // get the bet from the proper location based on the ID

        //adds the bet's new amount to it's current amount
        this.bets[action.playerId][action.betID] = new Bet(this.bets[action.playerId][action.betID].getAmount() + action.betAmount, action.betID);

        //set player fundsSyste
        this.setPlayerFunds(action.playerId, this.funds[action.playerId] - action.betAmount);
        Log.d("die", "player " + action.playerId + "funds changed to " + this.funds[action.playerId]);


        //TODO automatically ready the computer after they bet
        //if the sender was the human player
        if (action.getPlayer() instanceof CrapsHumanPlayer) {
            //if the player Id is 1
            if (action.playerId == 1) {
                this.ready[0] = true;  //set the computer to true
            } else {
                this.ready[1] = true;
            }
        } else {
            this.ready[action.playerId] = true;
        }

        //note that we will eventually initialize all bets in the array to have
        //the correct IDs and names before the game is started.
        Log.d("die", "PLACED BET bet ID: " + action.betID + ", betAmount: " + action.betAmount);

        return true;
    }

    /**
     * removeBet
     * checks to see if there is a bet to remove
     * changes the bet in the GameState bet array
     *
     * @param action
     * @return
     */
    public boolean removeBet(RemoveBetAction action) {

        //Does the player have enough money?
        if (bets[action.playerId][action.betID].getAmount() <= 0) {
            return false;
        }

        //add bet amount back to human's funds

        //Log.d("die", "added $" + bets[action.playerId][action.betID].getAmount() +
        // "to player's account.");
        this.setPlayerFunds(action.playerId, this.funds[action.playerId] +
                bets[action.playerId][action.betID].getAmount());


        //adjust the bet's amount
        this.bets[action.playerId][action.betID] = new Bet(); //'removes' the bet (set to 0)

        Log.d("die", "REMOVED BET bet ID: " + action.betID);
        Bet thisBet = this.bets[action.playerId][action.betID];
        Log.d("die", "amount of REMOVED bet: " + thisBet.getAmount());


        return true;
    }

    /**
     * ready
     * <p>
     * set player's ready to what is sent in the action
     *
     * @param action
     * @return
     */
    public boolean ready(Ready2CrapAction action) {
        //what conditions should a person ready under...
        //might have to time that later

        System.out.println("Received ready action from player " + action.playerID);

        //note this is only for 2 players right now :)
        //if ready then changes appropriate player to ready
        if (action.playerID == 0) {
            this.ready[0] = action.isReady;
        } else if (action.playerID == 1) {
            this.ready[1] = action.isReady;
        }

        System.out.println("STATE: Player 0 ready:" + this.ready[0]);
        System.out.println("STATE: Player 1 ready:" + this.ready[1]);

        return true;
    }

    /**
     * roll
     * <p>
     * if player is shooter they can roll
     *
     * @param action
     * @return NOTE: later on we might want to calculate roll in here instead - Troy
     */

    public boolean roll(RollAction action) {

        //checks if both players are ready
        if (!checkCanRoll()) {
            return false;
        }
        //checks if it is the shooters turn
        if (action.playerId != this.playerTurn) {
            return false;
        }

        //updates the values of the die
        Random rand = new Random();
        this.setDice(rand.nextInt(6) + 1, rand.nextInt(6) + 1);
        //this.setDice(1, 1); //always rolls a crap (for testing purposes)
        //this.setDice(5, 5); //always rolls 2 5s (for testing purposes)
        //this.setDice(5, 6); //always rolls an 11 (for testing purposes)

        //checks if this is the first round and updates the first roll
        //or if the shooter just lost (player switched)
        if (this.firstDieShot == 0 || playerSwitched) {
            this.firstDieShot = this.dieTotal;
        }

        // iterates through a master 2d array and pays all bets to each player
        for (int p = 0; p < bets.length; p++) { // iterates through number of players
            for (int b = 0; b < bets[p].length; b++) { // iterates through all bet IDs
                if (p == 0) { //updates human player money
                    this.setPlayerFunds(action.playerId,
                            (int) this.funds[action.playerId] + this.bets[p][b].payoutBet(this.die1CurrVal,
                                    this.die2CurrVal, this.dieTotal, this.firstDieShot)
                    );
                    //only prints out the bets that player has actually made
                    if (this.bets[p][b].getID() != 0) {
                        Log.d("funds", "human money: " + this.funds[action.playerId]);
                        Log.d("funds", "bet: " + this.bets[p][b].toString());
                        this.bets[p][b].setBetAmount(0.0); //resets the bet amount
                    }
                    //TODO put this in a separate method??
                } else { //update computer money
                    this.setPlayerFunds(action.playerId,
                            (int) this.funds[action.playerId] + this.bets[p][b].payoutBet(this.die1CurrVal,
                                    this.die2CurrVal, this.dieTotal, this.firstDieShot)
                    );
                    //only prints out the bets that player has actually made
                    if (this.bets[p][b].getID() != 0) {
                        Log.d("funds", "human money: " + this.funds[action.playerId]);
                        Log.d("funds", "bet: " + this.bets[p][b].toString());
                        this.bets[p][b].setBetAmount(0.0); //resets the bet amount
                    }
                }
            }
        }

        Log.d("die", "DIE TOTAL: " + this.dieTotal);
        Log.d("die", "FIRST ROLL : " + this.firstDieShot);

        //change playerSwitched to false after roll
        playerSwitched = false;

        //reset both player's ready after roll
        ready[0] = false;
        ready[1] = false;

        //SYDNEY -- switch player
        // WES -- added first die 2 or 3
        //TODO this is written assuming there is one human and one computer playing
        //checks if the shooter wins or loses
        if ((this.dieTotal == 7 || this.firstDieShot == 2 || this.firstDieShot == 3) && !playerSwitched) {
            Log.d("die", "SWITCH SHOOTER!");
            this.changeTurn();
            Log.d("die", "Switching to player: " + this.playerTurn);
            //set playerSwitched to true since the shooter lost
            playerSwitched = true;
        }

        System.out.println("STATE: after roll, player turn: " + this.getPlayerTurn());

        return true;
    }

}
