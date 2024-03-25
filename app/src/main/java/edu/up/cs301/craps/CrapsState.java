package edu.up.cs301.craps;

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
 * @version March 2024
 */

public class CrapsState extends GameState {

    //the players in the game, and their current net worth
    private int playerTurn;
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

    //ctor
    public CrapsState() {
        this.playerTurn = 0;
        this.player0Funds = 1000.00;
        this.player1Funds = 1000.00;
        this.player0Ready = false;
        this.player1Ready = false;
        this.setDice(0, 0);
        this.offOn = false;
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

    //setters

    /**
     * setDice
     * sets dice and finds sum
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
        if (this.playerTurn == 0) {
            this.playerTurn = 1;
        } else {
            this.playerTurn = 0;
        }
    }

    public void setPlayer0Funds(int player0Funds) {
        this.player0Funds = player0Funds;
    }

    public void setPlayer1Funds(int player1Funds) {
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
        if (action.betAmount > action.playerMoney){
            return false;
        }

        //Is the bet ID a valid point in the array?
        //23 is how many types of bets there are
        if (action.betID > 22 || action.betID  < 0){
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
        this.bets[0][action.betID].setBetAmount(action.betAmount);

        //note that we will eventually initialize all bets in the array to have
        //the correct IDs and names before the game is started.

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
    public boolean removeBet(RemoveBetAction action, int id, int betAmount){

        //Does the player have enough money?
        if (action.betAmount <= 0){
            return false;
        }

        /*
           TODO: need to figure out how to get the ID of the player
            we could put the ID in the RemoveBetAction like we did for ReadyAction
         */

        //adjust the bet's amount
        this.bets[0][action.betID].setBetAmount(0); //'removes' the bet (set to 0)

        return true;
    }

    /**
     * ready
     *
     * if player is ready, set player to ready :)
     *
     * @param action
     * @return
     */
    public boolean ready (Ready2CrapAction action){
        //what conditions should a person ready under...
        //might have to time that later

        //note this is only for 2 players right now :)
        //if ready then changes appropriate player to ready
        if (action.playerID == 0){
            this.player0Ready = action.isReady;
        }
        else if (action.playerID ==1){
            this.player1Ready = action.isReady;
        }

        return true;
    }

    /**
     * roll
     *
     * if player is shooter they can roll
     *
     * @param action
     * @return
     */
    public boolean roll(RollAction action){
        if (action.isShooter == false){
            return false;
        }

        this.die1CurrVal = action.die1;
        this.die2CurrVal = action.die2;
        this.dieTotal = action.dieTotal;

        return true;

        //note, later on we might want to calculate roll in here instead

    }

}



