package edu.up.cs301.craps;

import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.GameFramework.players.GamePlayer;
import edu.up.cs301.GameFramework.LocalGame;
import edu.up.cs301.GameFramework.actionMessage.GameAction;

import android.util.Log;

/**
 * A class that represents the state of a game. In our craps game, the only
 * relevant piece of information is the value of the game's counter. The
 * CounterState object is therefore very simple.
 * 
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @author Wes H.       Last Revision: 4/22/2024
 * @author Troy C.      Last Revision: TBD
 * @author Rowena A.    Last Revision: TBD
 * @author Sydney D.    Last Revision: TBD
 * @version 4/22/2024
 */
public class CrapsLocalGame extends LocalGame {

	// When a craps game is played, any number of players. The first player
	// is trying to get the counter value to TARGET_MAGNITUDE; the second player,
	// if present, is trying to get the counter to -TARGET_MAGNITUDE. The
	// remaining players are neither winners nor losers, but can interfere by
	// modifying the counter.
	public static final int TARGET_MAGNITUDE = 10;

	// the game's state
	private CrapsState gameState;
	
	/**
	 * can this player move
	 * 
	 * @return
	 * 		true, because all player are always allowed to move at all times,
	 * 		as this is a fully asynchronous game
	 */
	@Override
	protected boolean canMove(int playerIdx) {
		return true;
	}

	/**
	 * This ctor should be called when a new craps game is started
	 */
	public CrapsLocalGame(GameState state) {
		// initialize the game state, with the counter value starting at 0
		if (! (state instanceof CrapsState)) {
			state = new CrapsState();
		}
		this.gameState = (CrapsState)state;
		super.state = state;

	}


	/**
	 * starts the game
	 * prints to log a list of players and their IDs (LocalGame)
	 *
	 * @param players
	 * 			the list of players who are playing in the game
	 */
	@Override
	public void start(GamePlayer[] players){
		super.start(players);

		//for each player, print out the ID
		for (GamePlayer player : this.getPlayers()){
			System.out.println("Name: "+ player.toString() + ", Id: " + this.getPlayerIdx(player) + "\n");

			//if this player is a human, set that player's ID its ID stored in localGame
			if (player instanceof CrapsHumanPlayer){
				CrapsHumanPlayer human = (CrapsHumanPlayer) player;
				human.setPlayerId(this.getPlayerIdx(player));

			}
			//if this player is a human, set that player's ID its ID stored in localGame
			//TODO gotta make sure this works for compplayer2, I think it will
			else if ((player instanceof CrapsComputerPlayer1) || (player instanceof CrapsComputerPlayer2)){
				CrapsComputerPlayer1 comp = (CrapsComputerPlayer1) player;
				comp.setPlayerId(this.getPlayerIdx(player));
			}

		}


	}

	/**
	 * The only type of GameAction that should be sent is CounterMoveAction
	 */
	@Override
	protected boolean makeMove(GameAction action) {
		Log.i("action", action.getClass().toString());
		//checks what action this move is
		if (action instanceof CrapsMoveAction) {
		
			// cast so that we Java knows it's a CounterMoveAction
			CrapsMoveAction cma = (CrapsMoveAction)action;

			// Update the counter values based upon the action
			//int result = gameState.getCounter() + (cma.isPlus() ? 1 : -1);
			//gameState.setCounter(result);
			
			// denote that this was a legal/successful move
			return true;
		} else if (action instanceof Ready2CrapAction) {
			//typecast
			Ready2CrapAction ready2crap = (Ready2CrapAction) action;
			//perform appropriate action handler method in gamestate
			gameState.ready(ready2crap);
			//denote legal move
			return true;
		}else if (action instanceof RollAction) {
			//typecast
			RollAction rolling = (RollAction) action;
			//perform appropriate action handler method in gamestate
			gameState.roll(rolling);
			//denote legal move
			return true;
		}else if (action instanceof PlaceBetAction) {
			//typecast
			PlaceBetAction placebet = (PlaceBetAction) action;
			//perform appropriate action handler method in gamestate
			gameState.placeBet(placebet);
			//denote legal move
			return true;
		}else if (action instanceof RemoveBetAction) {
			//typecast
			RemoveBetAction removebet = (RemoveBetAction) action;
			//perform appropriate action handler method in gamestate
			gameState.removeBet(removebet);
			//denote legal move
			return true;
		} else {
			// denote that this was an illegal move
			return false;
		}
	}//makeMove
	
	/**
	 * send the updated state to a given player
	 */
	@Override
	protected void sendUpdatedStateTo(GamePlayer p) {
		// this is a perfect-information game, so we'll make a
		// complete copy of the state to send to the player
		p.sendInfo(new CrapsState(this.gameState));
		
	}//sendUpdatedSate
	
	/**
	 * Check if the game is over. It is over, return a string that tells
	 * who the winner(s), if any, are. If the game is not over, return null;
	 *
	 * win lose condition
	 * 
	 * @return
	 * 		a message that tells who has won the game, or null if the
	 * 		game is not over
	 */
	@Override
	protected String checkIfGameOver() {
		int player0funds = gameState.getPlayerFunds(0);
		int player1funds = gameState.getPlayerFunds(1);
		for (int p = 0; p < gameState.bets.length; p++) {
			for (int b = 0; b < gameState.bets[p].length; b++) {
				if (p == 0) {
					player0funds += gameState.bets[p][b].getAmount();
				} else {
					player1funds += gameState.bets[p][b].getAmount();
				}
			}
		}
		if (player0funds == 0) {
			return "Player 0 lost all their money, Player 1 Wins!";
		} else if (player1funds == 0) {
			return "Player 1 lost all their money, Player 0 Wins!";
		}
		return "That's all folks";
	}

}// class CounterLocalGame
