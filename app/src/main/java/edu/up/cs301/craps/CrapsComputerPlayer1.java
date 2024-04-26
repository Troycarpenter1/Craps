package edu.up.cs301.craps;

import android.util.Log;


import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.players.GameComputerPlayer;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.GameFramework.utilities.Tickable;

/**
 * Dumb Computer Player
 *
 * Doesn't place any bets when the human is the shooter.
 * Will bet about every roll when it is the shooter, $100
 * on the pass line each time, until it runs out of money.
 *
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @author Troy Carpenter
 * @author Rowena Archer
 * @author Sydney Dean
 * @author Wes Helms
 * @version April 2024
 */
public class CrapsComputerPlayer1 extends GameComputerPlayer implements Tickable {
    //instance variables
    //private GameMainActivity myActivity; //no usages but might be important idk - W
    private int playerMoney;// my money
    private int amountBet;// amount I wants to bet
    CrapsState crapsState;// reference to the crapState

    /**
     * CrapsComputerPlayer1
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
        this.amountBet = 200;
    }

    /**
     * roll
     * sends a RollAction to the crapState
     */
    public void roll() {

        //create a roll action then send it
        //Log.d("die", "Computer player shooter? " + this.isShooter);
        RollAction roll = new RollAction(this, this.playerNum);
        game.sendAction(roll);

    }

    /**
     * ready
     * sends a ReadyAction to the crapState if I am not already ready
     * otherwise does nothing
     */
    public void ready() {
        if (!crapsState.getPlayerReady(playerNum)) {
            Ready2CrapAction ready = new Ready2CrapAction(this, true, this.playerNum);
            game.sendAction(ready);
        }
    }

    /**
     * bet
     * sends a betAction to the crapState
     * places a bet of $100 on pass line
     */
    public void placeBet() {

        // look at how much money I have according to my copy of the game state
        playerMoney = crapsState.getPlayerFunds(this.playerNum);

        // if I somehow have less than $100 to spend, then bet all the money I have left
        if (playerMoney < 100) {
            this.amountBet = playerMoney;
        } else {
            // Set my amount to bet to $100
            this.amountBet = 100;
        }

        // bet on the pass line every time
        PlaceBetAction pba = new PlaceBetAction(this, this.playerNum, 1, amountBet);
        this.game.sendAction(pba);
        System.out.println("computer trying to bet");
    }

    /**
     * receiveInfo
     * callback method--game's state has changed
     * the computer will execute actions automatically
     * whenever it receives new game info
     *
     * @param info the information (presumably containing the game's state)
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        if (!(info instanceof CrapsState)) {
            return;
        }
        crapsState = (CrapsState) info;

        // store locally how much money I have according to my copy of the game state
        playerMoney = crapsState.getPlayerFunds(this.playerNum);

        // if is shooter take my turn
        if (crapsState.getPlayerTurn() == this.playerNum) {
            System.out.println("IT is the computer's turn!!!");
            //have to delay BEFORE we take turn or we won't be able to see the 7 rolled

            //if I'm not already ready, then place a bet
            if (!crapsState.getPlayerReady(this.playerNum)) {
                placeBet();
                ready();
            }
            //if other player ready then roll
            if (crapsState.getPlayerReady(this.playerNum - 1)){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                roll();
            }

        //if I'm not the shooter
        } else {
            if (crapsState.getPlayerReady(this.playerNum)) {
                System.out.println("COMPUTER: already ready.");
            //if I'm not ready, then send a ready action
            } else {
                ready();
            }
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