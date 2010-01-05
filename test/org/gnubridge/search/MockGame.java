package org.gnubridge.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gnubridge.core.Deal;
import org.gnubridge.core.Player;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.presentation.GameUtils;

class MockGame extends Deal {
	Map<List<Integer>, int[]> values;

	private int[] tricksTaken;

	private int movesWhenDone;

	public MockGame(Map<List<Integer>, int[]> v, int m) {
		super(NoTrump.i());
		values = v;
		movesWhenDone = m;
	}

	public MockGame() {
		super(NoTrump.i());
		values = new HashMap<List<Integer>, int[]>();
		movesWhenDone = -1;
	}

	public void setPositionValue(List<Integer> moves, int westEast,
			int northSouth) {
		int[] tricks = new int[2];
		tricks[Player.WEST_EAST] = westEast;
		tricks[Player.NORTH_SOUTH] = northSouth;
		values.put(moves, tricks);
		movesWhenDone = moves.size();
	}
	
	public boolean oneTrickLeft() {
		return done;
	}

	public void playMoves(List<Integer> moves) {
		if (done) {
			return;
		}
		if (moves.size() == movesWhenDone) {
			done = true;
		}
		this.tricksTaken = values.get(moves);
	}

	public int getTricksTaken(int pair) {
		return this.tricksTaken[pair];
	}

	public Deal duplicate() {
		MockGame result = new MockGame(values, movesWhenDone);
		GameUtils.initializeSingleColorSuits(result, 1); //this is so that some garbage value is returned when player is asked for possible moves
		return result;
	}
}
