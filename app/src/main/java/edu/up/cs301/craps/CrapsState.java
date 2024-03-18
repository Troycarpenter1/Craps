package edu.up.cs301.craps;

import edu.up.cs301.GameFramework.infoMessage.GameState;


/**
 * This contains the state for the Craps game. The state consist the player and their money,
 * and the state of the dice
 *
 * @author Troy Carpenter
 * @version March 2024
 */
public class CrapsState extends GameState {

    //the players in the game, and their current net worth
    private int playerTurn;
    private double player0Funds;
    private double player1Funds;
    //x controls player, y controls bet
    //private Bet[][] bets;

    //the dice every roll
    private int die1CurrVal;
    private int die2CurrVal;
    private int dieTotal;

    public CrapsState(){
        this.playerTurn=0;
        this.player0Funds=1000.0;
        this.player1Funds=1000.0;
        this.setDice(0,0);
        //this.bets=new Bet[2][23];
    }

    //copy constructor, currently is deep since no objects are used as variables atm
    public CrapsState(CrapsState crap) {
		this.playerTurn=crap.playerTurn;
		this.dieTotal=crap.dieTotal;
		this.die1CurrVal=crap.die1CurrVal;
		this.die2CurrVal=crap.die2CurrVal;
		this.player0Funds=crap.player0Funds;
		this.player1Funds= crap.player1Funds;
        //TODO: make this deeper
        //this.bets= new Bet(craps.bets.);

    }

	//getters for instance variables
    public int getPlayerTurn() {return this.playerTurn;}

    public double getPlayer0Funds() {return this.player0Funds;}

    public double getPlayer1Funds() {return this.player1Funds;}

    public int getDie1CurrVal() {return this.die1CurrVal;}

    public int getDie2CurrVal() {return this.die2CurrVal;}

    public int getDieTotal() {return this.dieTotal;}

	//setters

    //sets die1 and die 2, and gets the total from those numbers
    public void setDice(int die1Val, int die2Val) {
        this.die1CurrVal = die1Val;
        this.die2CurrVal = die2Val;
        this.dieTotal = this.die1CurrVal + this.die2CurrVal;
    }

	//changes the current turn to the other player
	//only 2 player currently
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
    @Override
    public String toString(){
        return "Player 0 has "+this.player0Funds+" and Player 1 has "+this.player1Funds+
                ". The current dice are "+this.die1CurrVal+" and "+this.die2CurrVal;
    }
}
