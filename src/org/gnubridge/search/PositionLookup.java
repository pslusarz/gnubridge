package org.gnubridge.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.gnubridge.core.Card;
import org.gnubridge.core.Direction;
import org.gnubridge.core.Game;
import org.gnubridge.core.Hand;
import org.gnubridge.core.Player;

public class PositionLookup {
	private PositionLookupNode root;

	public PositionLookup() {
		root = new PositionLookupNode(null);
	}

	public boolean positionEncountered(Game g) {
		List<Card> playedCards = g.getPlayedCards().getCardsHighToLow();
		if (isFreshTrick(playedCards)) {
			return root.positionEncountered(g, playedCards);
		} else {
			return false;
		}
	}

	private boolean isFreshTrick(List<Card> playedCards) {
		return playedCards.size() % 4 == 0;
	}

}

class PositionLookupNode {
	PositionLookupNode[] moves;
	private boolean terminatesAPosition;
	private List<AdditionalUniquePositionIdentifiers> additionalUniquePositionIdentifiers;

	PositionLookupNode(PositionLookupNode parent) {
		moves = new PositionLookupNode[Card.COUNT];
		terminatesAPosition = false;
		additionalUniquePositionIdentifiers = new ArrayList<AdditionalUniquePositionIdentifiers>();
	}

	public boolean positionEncountered(Game g, List<Card> playedCards) {
		if (playedCards.size() == 0) {
			return false;
		}
		Card top = playedCards.get(0);
		AdditionalUniquePositionIdentifiers additionalUniquePositionIdentifiers = new AdditionalUniquePositionIdentifiers(
				g);
		if (!haveCard(top)) {
			store(additionalUniquePositionIdentifiers, playedCards);
			return false;
		} else {
			PositionLookupNode currentNode = moves[top.getIndex()];
			if (playedCards.size() > 1) {
				playedCards.remove(0);
				return currentNode.positionEncountered(g, playedCards);
			} else {

				boolean result = currentNode.terminatesAPosition
						&& currentNode
								.playersTurnContains(additionalUniquePositionIdentifiers);
				currentNode.terminatesAPosition = true;
				currentNode
						.addAdditionalUniquePositionIdentifiers(additionalUniquePositionIdentifiers);
				return result;
			}
		}
	}

	private void addAdditionalUniquePositionIdentifiers(
			AdditionalUniquePositionIdentifiers unique) {
		if (!playersTurnContains(unique)) {
			additionalUniquePositionIdentifiers.add(unique);
		}

	}

	private boolean playersTurnContains(
			AdditionalUniquePositionIdentifiers candidate) {
		for (AdditionalUniquePositionIdentifiers currentUnique : additionalUniquePositionIdentifiers) {
			if (currentUnique.hasSameIdentifiers(candidate)) {
				return true;
			}
		}
		return false;
	}

	private void store(
			AdditionalUniquePositionIdentifiers additionalUniquePositionIdentifiers,
			List<Card> cards) {
		Card top = cards.get(0);
		moves[top.getIndex()] = new PositionLookupNode(this);
		if (cards.size() > 1) {
			cards.remove(0);
			moves[top.getIndex()].store(additionalUniquePositionIdentifiers,
					cards);
		} else {
			moves[top.getIndex()].terminatesAPosition = true;
			moves[top.getIndex()]
					.addAdditionalUniquePositionIdentifiers(additionalUniquePositionIdentifiers);
		}

	}

	private boolean haveCard(Card c) {
		return moves[c.getIndex()] != null;
	}
}

class AdditionalUniquePositionIdentifiers {
	private final Direction nextToMove;
	private final int tricksTakenByNorthSouth;

	public AdditionalUniquePositionIdentifiers(Game g) {
		this.nextToMove = g.getNextToPlay().getDirection2();
		this.tricksTakenByNorthSouth = g.getTricksTaken(Player.NORTH_SOUTH);
	}

	public boolean hasSameIdentifiers(AdditionalUniquePositionIdentifiers other) {
		return other.getNextToMove().equals(getNextToMove())
				&& other.getTricksTakenNorthSouth() == getTricksTakenNorthSouth();
	}

	public int getTricksTakenNorthSouth() {
		return tricksTakenByNorthSouth;
	}

	public Direction getNextToMove() {
		return nextToMove;
	}
}