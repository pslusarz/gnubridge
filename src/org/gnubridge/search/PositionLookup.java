package org.gnubridge.search;

import java.util.List;

import org.gnubridge.core.Card;
import org.gnubridge.core.Game;
import org.gnubridge.core.Hand;

public class PositionLookup {
	private Hand move1;


	public PositionLookup() {
	  move1 = new Hand();
	}


	public boolean positionEncountered(Game g) {
		boolean result = false;
		List<Card> playedCards = g.getPlayedCards().getCardsHighToLow();
		if ( (move1.isEmpty() && playedCards.isEmpty())
				|| (playedCards.size() > 0 && move1.contains(playedCards.get(0)))) {
			result = true;
		} else if (playedCards.size() > 0) {
			move1.add(playedCards.get(0));
		}
		return result;
	}

}
