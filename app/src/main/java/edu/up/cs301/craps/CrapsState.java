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
    private int player0Funds;
    private int player1Funds;

    //the dice every roll
    private int die1CurrVal;
    private int die2CurrVal;
    private int dieTotal;

	//TODO make this constructor instantiate properly
    public CrapsState(int playerTurn) {
        this.playerTurn = playerTurn;

    }

    public CrapsState(CrapsState crap) {
		this.playerTurn=crap.playerTurn;
		this.dieTotal=crap.dieTotal;
		this.die1CurrVal=crap.die1CurrVal;
		this.die2CurrVal=crap.die2CurrVal;
		this.player0Funds=crap.player0Funds;
		this.player1Funds= crap.player1Funds;
    }

	//getters for instance variables
    public int getPlayerTurn() {return this.playerTurn;}

    public int getPlayer0Funds() {return this.player0Funds;}

    public int getPlayer1Funds() {return this.player1Funds;}

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
}
