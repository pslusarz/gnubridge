package org.gnubridge.search;

import java.util.List;

import org.gnubridge.core.Card;
import org.gnubridge.core.Game;
import org.gnubridge.core.Hand;

public class PositionLookup {
	// private Hand move1;
	private PositionLookupNode root;

	public PositionLookup() {
		// move1 = new Hand();
		root = new PositionLookupNode(null, null);
	}

	public boolean positionEncountered(Game g) {
		boolean result = false;
		List<Card> playedCards = g.getPlayedCards().getCardsHighToLow();
		return root.positionEncountered(playedCards);
	}

}

class PositionLookupNode {
	PositionLookupNode[] moves;
	private Card card;
	private boolean isLeaf;

	PositionLookupNode(PositionLookupNode parent, Card card) {
		moves = new PositionLookupNode[Card.COUNT];
		this.card = card;
		isLeaf = true;
	}

	public boolean positionEncountered(List<Card> playedCards) {
		if (!haveCard(playedCards.get(0))) {
			store(playedCards);
			return false;
		} else {
			Card top = playedCards.get(0);
			if (playedCards.size() > 1) {
				playedCards.remove(0);
				return moves[top.getIndex()].positionEncountered(playedCards);
			} else {
				return moves[top.getIndex()].isLeaf;
			}
		}
	}

	private void store(List<Card> cards) {
		isLeaf = false;
		Card top = cards.get(0);
		moves[top.getIndex()] = new PositionLookupNode(this, top);
		if (cards.size() > 1) {
			cards.remove(0);
			moves[top.getIndex()].store(cards);
		}

	}

	private boolean haveCard(Card c) {
		return moves[c.getIndex()] != null;
	}
}
