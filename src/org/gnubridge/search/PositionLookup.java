package org.gnubridge.search;

import java.util.List;

import org.gnubridge.core.Card;
import org.gnubridge.core.Game;
import org.gnubridge.core.Hand;

public class PositionLookup {
	private PositionLookupNode root;

	public PositionLookup() {
		root = new PositionLookupNode(null, null);
	}

	public boolean positionEncountered(Game g) {
		List<Card> playedCards = g.getPlayedCards().getCardsHighToLow();
		return root.positionEncountered(playedCards);
	}

}

class PositionLookupNode {
	PositionLookupNode[] moves;
	private Card card;
	private boolean terminatesAPosition;

	PositionLookupNode(PositionLookupNode parent, Card card) {
		moves = new PositionLookupNode[Card.COUNT];
		this.card = card;
		terminatesAPosition = false;
	}

	public boolean positionEncountered(List<Card> playedCards) {
		Card top = playedCards.get(0);
		if (!haveCard(top)) {
			store(playedCards);
			return false;
		} else {
			if (playedCards.size() > 1) {
				playedCards.remove(0);
				return moves[top.getIndex()].positionEncountered(playedCards);
			} else {
				boolean result =  moves[top.getIndex()].terminatesAPosition;
				moves[top.getIndex()].terminatesAPosition = true;
				return result;
			}
		}
	}

	private void store(List<Card> cards) {
		Card top = cards.get(0);
		moves[top.getIndex()] = new PositionLookupNode(this, top);
		if (cards.size() > 1) {
			cards.remove(0);
			moves[top.getIndex()].store(cards);
		} else {
			moves[top.getIndex()].terminatesAPosition = true;
		}

	}

	private boolean haveCard(Card c) {
		return moves[c.getIndex()] != null;
	}
}
