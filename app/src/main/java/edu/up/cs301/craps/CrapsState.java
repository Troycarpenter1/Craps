package edu.up.cs301.craps;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import java.util.Random;

import edu.up.cs301.GameFramework.infoMessage.GameState;
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
    private double player0Funds;
    private boolean player0Ready;
    private double player1Funds;
    private boolean player1Ready;
    //x controls player, y controls bet
    private Bet[][] bets = new Bet[2][23];

    //the dice every roll
    private int die1CurrVal;
    private int die2CurrVal;
    private int dieTotal;
    private boolean offOn;

    //purpose is to prevent one roll counting double
    boolean playerSwitched; //local variable used to indicate if the turn has just been switched (7 was the last number rolled)
    //if false > a new roll has been made
    //true > we're still on the 7 from last time

    //ctor
    public CrapsState() {
        this.playerTurn = 0;
        this.player0Funds = 1000.00;
        this.player1Funds = 1000.00;
        this.player0Ready = false;
        this.player1Ready = true;
        this.setDice(0, 0);
        this.offOn = false;
        this.playerSwitched = false;
        // iterates through a master 2d array and makes all bets for each player
        for (int p = 0; p < bets.length; p++) { // iterates through number of players
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
        this.player0Funds = crap.player0Funds;
        this.player1Funds = crap.player1Funds;
        this.player0Ready = crap.player0Ready;
        this.player1Ready = crap.player1Ready;
        this.offOn = crap.offOn;
        this.playerSwitched = crap.playerSwitched;
        //uses the copy constructor of the bet class
        for (int x = 0; x < bets.length; x++) {
            for (int y = 0; y < bets[x].length; y++) {
                this.bets[x][y] = new Bet(crap.bets[x][y]);
            }
        }
    }

    //getters for instance variables
    public int getPlayerTurn() {
        return this.playerTurn;
    }

    public Bet getBet(int playerId, int betId){
        return bets[playerId][betId];
    }

    public double getPlayer0Funds() {
        return this.player0Funds;
    }

    public double getPlayer1Funds() {
        return this.player1Funds;
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

    public boolean getPlayerSwitched() {return this.playerSwitched;}

    public boolean isPlayer0Ready() {
        return player0Ready;
    }

    public boolean isPlayer1Ready() {
        return player1Ready;
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
    }

    public void setPlayer0Funds(double player0Funds) {
        this.player0Funds = player0Funds;
    }

    public void setPlayer1Funds(double player1Funds) {
        this.player1Funds = player1Funds;
    }

    //tells the tale of the game
    @Override
    public String toString() {
        return "Player 0 has " + this.player0Funds + "$, and Player 1 has " + this.player1Funds +
                "$. The current dice are " + this.die1CurrVal + " and " + this.die2CurrVal;
    }

    //Actions.txt checkers
    //see header comment for omitted methods

    /**
     * called when a player wants to roll
     * only for two players at this time
     *
     * @return boolean
     */
    public boolean checkCanRoll() {
        if (this.player0Ready && this.player1Ready) {
            return true;
            //player will update the Game State from here
        }
        return false;
    }

    /*
    //checks if players can bet (can't bet negative money)
    public boolean check0CanBet() {
        return player0Funds != 0;
    }

    public boolean check1CanBet() {
        return player1Funds != 0;
    }

     */

    /**
     * placeBet
     * checks to see if bet is valid
     * changes the bet in the GameState bet array
     *
     * @param action
     * @return
     */
    public boolean placeBet(PlaceBetAction action) {
        //Is it the player's turn?

        //Does the player have enough money?
        if (action.playerId == 0) {
            if (action.betAmount > player0Funds) {
                return false;
            }
        } else if (action.playerId == 1) {
            if (action.betAmount > player1Funds) {
                return false;
            }
        }

        //Is the bet ID a valid point in the array?
        //23 is how many types of bets there are
        if (action.betID > 22 || action.betID < 0) {
            return false;
        }

        //Change the state to add the new bet

        //assumes that a bet's bet ID is the same as it's position in the array
        // get the bet from the proper location based on the ID

        /*
           TODO: need to figure out how to get the ID of the player
            we could put the ID in the RemoveBetAction like we did for ReadyAction
         */
        //adjust the bet's amount
        this.bets[action.playerId][action.betID].setBetAmount(action.betAmount);
        if (action.playerId == 0) {
        /*this.bets[action.playerId][action.betID]=new Bet((int)action.betAmount, 1.0,
                action.betID);

        if(action.playerId==0) { */
            //adjust the player's funds, remove the bet amount
            this.setPlayer0Funds(this.getPlayer0Funds() - action.betAmount);
        } else {
            this.setPlayer1Funds(this.getPlayer1Funds() - action.betAmount);
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
        if (action.playerId == 0) {
            this.setPlayer0Funds(this.getPlayer0Funds() +
                    bets[action.playerId][action.betID].getAmount());

        //add bet amount back to computer player's funds
        } else {
            this.setPlayer1Funds(this.getPlayer1Funds() +
                    bets[action.playerId][action.betID].getAmount());
        }

        //adjust the bet's amount
        this.bets[action.playerId][action.betID].setBetAmount(0); //'removes' the bet (set to 0)

        Log.d("die", "REMOVED BET bet ID: " + action.betID);
        Bet thisBet = this.bets[action.playerId][action.betID];
        Log.d("die", "amount of REMOVED bet: " + thisBet.getAmount());

        return true;
    }

    /**
     * ready
     * <p>
     * if player is ready, set player to ready :)
     *
     * @param action
     * @return
     */
    public boolean ready(Ready2CrapAction action) {
        //what conditions should a person ready under...
        //might have to time that later

        //note this is only for 2 players right now :)
        //if ready then changes appropriate player to ready
        if (action.playerID == 0) {
            this.player0Ready = action.isReady;
        } else if (action.playerID == 1) {
            this.player1Ready = action.isReady;
        }
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

        /*
        //purpose is to prevent one roll counting double
        boolean playerSwitched; //local variable used to indicate if the turn has just been switched (7 was the last number rolled)
        //if false > a new roll has been made
        //true > we're still on the 7 from last time
         */

        //checks if it is the shooters turn
        if (action.playerId != this.playerTurn) {
            return false;
        }


        //checks if both players are ready
        if (!checkCanRoll()) {
            return false;
        }

        //updates the values of the die
        Random rand = new Random();
        this.setDice(rand.nextInt(6) + 1, rand.nextInt(6) + 1);
        playerSwitched = false;
        Log.d("die", "DIETOTAL: " + this.dieTotal);

        player0Ready = false; //reset player 0's ready after roll
                              //if it weren't reset, then there's no time to bet

        //SYDNEY -- switch player
        //TODO this is written assuming there is one human and one computer playing

        //TODO playerswitched isn't working
        if ((this.dieTotal == 7) && !playerSwitched) {
            Log.d("die", "7 ROLLED! SWITCH! ");

            GamePlayer player = action.getPlayer();
            //Log.d("die", "the player is: " + player);

            if (this.playerTurn == 0){
                this.playerTurn = 1;
                Log.d("die", "Switching to computer player.");
            }
            else{
                this.playerTurn = 0;
                Log.d("die", "Switching to human player.");
            }

            playerSwitched = true;

        }

        /*
        Log.d("die", "player turn: " + this.getPlayerTurn());
        Log.d("die", "player 0 ready? " + this.player0Ready);
        Log.d("die", "player 1 ready? " + this.player1Ready);
        */


        return true;
    }
}
