package edu.up.cs301.craps;

import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.GameFramework.players.GamePlayer;
import edu.up.cs301.GameFramework.LocalGame;
import edu.up.cs301.GameFramework.actionMessage.GameAction;

import android.util.Log;

/**
 * A class that represents the state of a game. In our counter game, the only
 * relevant piece of information is the value of the game's counter. The
 * CounterState object is therefore very simple.
 * 
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @version July 2013
 */
public class CrapsLocalGame extends LocalGame {

	// When a counter game is played, any number of players. The first player
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
	 * This ctor should be called when a new counter game is started
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
	 * @return
	 * 		a message that tells who has won the game, or null if the
	 * 		game is not over
	 */
	@Override
	protected String checkIfGameOver() {
		return "That's all folks";
	}

}// class CounterLocalGame
