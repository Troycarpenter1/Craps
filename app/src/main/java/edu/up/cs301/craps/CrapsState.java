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

    //purpose is to prevent one roll counting double
    private boolean playerSwitched; //local variable used to indicate if the turn has just been switched (7 was the last number rolled)
    //if false > a new roll has been made
    //true > we're still on the 7 from last time

    /**
     * CrapsState
     * ctor
     */
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
                this.bets[p][b] = new Bet(b);
            }
        }
    }

    /**
     * CrapsState
     * copy constructor, currently is deep since no objects are used as variables
     *
     * @param crap the other crap we want to copy
     */
    public CrapsState(CrapsState crap) {
        this.playerTurn = crap.playerTurn;
        this.setDice(crap.die1CurrVal, crap.die2CurrVal);
        this.offOn = crap.offOn;
        this.playerSwitched = crap.playerSwitched;
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

    /*
     * getter methods for instance variables
     */
    public int getPlayerTurn() {
        return this.playerTurn;
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
        Log.d("die", "Player turn just switched to:" + this.playerTurn);
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
    @Override
    public String toString() {
        String player0 = this.toString(0);
        String player1 = this.toString(1);
        String gameON = this.offOn ? "ON" : "OFF";
        String gameStats = "The game is " + gameON + " The current dice are " + this.die1CurrVal + " and " + this.die2CurrVal
                + " which total to " + this.dieTotal + " The first dice roll of this shooter was " + this.firstDieShot + " the current shooter is player" + this.playerTurn;
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
            if (this.bets[id][b].getAmount() != 0) { //checks if this bet in the array has any money bet on it
                betString = betString + ("\t" + this.bets[id][b].toString() + ", \n");
            }
        }
        if (betString.length() < 3) {
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

        //Change the state to add the new bet

        //assumes that a bet's bet ID is the same as it's position in the array
        // get the bet from the proper location based on the ID

        //adds the bet's new amount to it's current amount
        this.bets[action.playerId][action.betID].setBetAmount(this.bets[action.playerId][action.betID].getAmount() + action.betAmount);

        //set player fundsSyste
        this.setPlayerFunds(action.playerId, this.funds[action.playerId] - action.betAmount);
        Log.d("die", "player " + action.playerId + "funds changed to " + this.funds[action.playerId]);


        //TODO automatically ready the computer after they bet
        //if the sender was the human player
//        if (action.getPlayer() instanceof CrapsHumanPlayer) {
//            //if the player Id is 1
////            if (action.playerId == 1) {
////                this.ready[0] = true;  //set the computer to true
////            } else {
////                this.ready[1] = true;
////            }
//        } else {
//            this.ready[action.playerId] = true;
//        }

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

        //add bet amount back to human's funds

        //Log.d("die", "added $" + bets[action.playerId][action.betID].getAmount() +
        // "to player's account.");
        this.setPlayerFunds(action.playerId, this.funds[action.playerId] +
                bets[action.playerId][action.betID].getAmount());


        //adjust the bet's amount
        this.bets[action.playerId][action.betID].setBetAmount(0); //'removes' the bet (set to 0)
        //this.bets[action.playerId][action.betID] = new Bet(); //'removes' the bet (set to 0)

        Log.d("die", "REMOVED BET bet ID: " + action.betID);
        Bet thisBet = this.bets[action.playerId][action.betID];
        Log.d("die", "amount of REMOVED bet: " + thisBet.getAmount());


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
    }//Ready2CrapAction

    /**
     * payoutPlayers
     * helper method for the roll method
     * this method is only for paying the players whenever it is called
     * and does not check anything within the state it ONLY UPDATES VALUES
     *
     * @param action the rollAction that it needs to know to pay the player based on the bets
     */
    public void payoutPlayers(RollAction action, boolean oneTimeBet) {
        // iterates through a master 2d array and pays all bets to each player
        for (int p = 0; p < bets.length; p++) { // iterates through number of players
            for (int b = 0; b < bets[p].length; b++) { // iterates through all bet IDs
                //checks if this bet should pay out at all
                if ((this.bets[p][b].isOneTimeBet() == oneTimeBet) || oneTimeBet) {
                    // calculates how much money the player has won
                    int newFunds = this.funds[action.playerId] + this.bets[p][b].payoutBet(this.die1CurrVal, this.die2CurrVal, this.dieTotal, this.firstDieShot);
                    // updates the players funds
                    this.setPlayerFunds(action.playerId, newFunds);
                    //removes the bets that player has actually made
                    if (!oneTimeBet) {
                        this.bets[p][b].setBetAmount(0); //resets the bet amount
                    }
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
        this.setDice(6, 6); //always rolls 3 6s (for testing purposes)

        //checks if this is the first round and updates the first roll
        //or if the shooter just lost (player switched)
        if (this.firstDieShot == 0 || playerSwitched) {
            //updates the first roll of the round
            this.firstDieShot = this.dieTotal;
            //pays out all of the bets that are paid only every round
            payoutPlayers(action, false);
        }

        // pays out all of the bets that are paid out every roll
        payoutPlayers(action, true);

        Log.d("die", "DIE TOTAL: " + this.dieTotal);
        Log.d("die", "FIRST ROLL : " + this.firstDieShot);


        //sets the value for the on/off button
        if (!offOn) {
            //makes sure that the roll is the first of the "round"
            if (isFirstRoll) {
                //makes sure the roll is a valid place bet
                if ((dieTotal > 3 && dieTotal < 11) && dieTotal != 7) {
                    //sets the first roll variable, and sets the other booleans to appropriate values
                    this.isFirstRoll = false;
                    this.firstDieShot = dieTotal;
                    this.offOn = true;
                }
            }
        }


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
            this.firstDieShot = 0;
        }

        System.out.println("STATE: after roll, player turn: " + this.getPlayerTurn());

        return true;
    }//rollAction

}