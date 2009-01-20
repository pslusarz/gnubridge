package org.gnubridge.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.gnubridge.core.Card;
import org.gnubridge.core.Direction;
import org.gnubridge.core.Game;
import org.gnubridge.core.Hand;
import org.gnubridge.core.Player;

public class PositionLookup {
	private PositionLookupNode root;

	public PositionLookup() {
		root = new PositionLookupNode(null, null);
	}

	public boolean positionEncountered(Game g,
			Node candidateFirstNodeForThePosition) {
		List<Card> playedCards = g.getPlayedCards().getCardsHighToLow();
		if (isFreshTrick(playedCards)) {
			return root.positionEncountered(g, playedCards,
					candidateFirstNodeForThePosition) != null;
		} else {
			return false;
		}

	}

	private boolean isFreshTrick(List<Card> playedCards) {
		return playedCards.size() % 4 == 0;
	}

	public Node getNode(Game g) {
		List<Card> playedCards = g.getPlayedCards().getCardsHighToLow();
		return root.positionEncountered(g, playedCards, null);
	}

}

class PositionLookupNode {
	//PositionLookupNode[] moves;
	List<PositionLookupNode> children;
	private boolean terminatesAPosition;
	private List<AdditionalUniquePositionIdentifiers> identifiersList;
	private Card card;

	PositionLookupNode(PositionLookupNode parent, Card card) {
		//moves = new PositionLookupNode[Card.COUNT];
		children = new LinkedList<PositionLookupNode>();
		terminatesAPosition = false;
		identifiersList = new LinkedList<AdditionalUniquePositionIdentifiers>();
		this.card = card;
	}

	public Node positionEncountered(Game g, List<Card> playedCards,
			Node candidateFirstNodeForThePosition) {
		if (playedCards.size() == 0) {
			return null;
		}
		Card top = playedCards.get(0);
		AdditionalUniquePositionIdentifiers identifiersForCurrentPosition = new AdditionalUniquePositionIdentifiers(
				g, candidateFirstNodeForThePosition);
		if (!haveCard(top)) {
			store(identifiersForCurrentPosition, playedCards,
					candidateFirstNodeForThePosition);
			return null;
		} else {
			PositionLookupNode currentNode = findNode(top);//moves[top.getIndex()];
			if (playedCards.size() > 1) {
				playedCards.remove(0);
				return currentNode.positionEncountered(g, playedCards,
						candidateFirstNodeForThePosition);
			} else {

				boolean result = currentNode.terminatesAPosition
						&& currentNode
								.playersTurnContains(identifiersForCurrentPosition);
				currentNode.terminatesAPosition = true;
				currentNode
						.addAdditionalUniquePositionIdentifiers(identifiersForCurrentPosition, candidateFirstNodeForThePosition);
				Node toBeReturned = null;
				if (result) {
					for (AdditionalUniquePositionIdentifiers currentUnique : currentNode.identifiersList) {
						if (currentUnique.hasSameIdentifiers(identifiersForCurrentPosition)) {
							toBeReturned = currentUnique.getNode();
						}
					} 	
				} 
				return toBeReturned;
			}
		}
	}

	private PositionLookupNode findNode(Card card) {
		for (PositionLookupNode node : children) {
			if (card.equals(node.card)) {
				return node;
			}
		} 
		return null;
	}

	private void addAdditionalUniquePositionIdentifiers(
			AdditionalUniquePositionIdentifiers unique, Node node) {
		if (!playersTurnContains(unique)) {
			identifiersList.add(unique);
		}

	}

	private boolean playersTurnContains(
			AdditionalUniquePositionIdentifiers candidate) {
		for (AdditionalUniquePositionIdentifiers currentUnique : identifiersList) {
			if (currentUnique.hasSameIdentifiers(candidate)) {
				return true;
			}
		}
		return false;
	}

	private void store(
			AdditionalUniquePositionIdentifiers additionalUniquePositionIdentifiers,
			List<Card> cards, Node node) {
		Card top = cards.get(0);
		PositionLookupNode nodeForTopCard = new PositionLookupNode(this, top);
		//moves[top.getIndex()] = nodeForTopCard;
		children.add(nodeForTopCard);
		if (cards.size() > 1) {
			cards.remove(0);
			nodeForTopCard.store(additionalUniquePositionIdentifiers,
					cards, node);
		} else {
			nodeForTopCard.terminatesAPosition = true;
			nodeForTopCard
					.addAdditionalUniquePositionIdentifiers(additionalUniquePositionIdentifiers, node);
		}

	}

	private boolean haveCard(Card c) {
		return findNode(c) != null;//moves[c.getIndex()] != null;
	}
}

class AdditionalUniquePositionIdentifiers {
	private final Direction nextToMove;
	private final byte tricksTakenByNorthSouth;
	private Node node;

	public AdditionalUniquePositionIdentifiers(Game g, Node node) {
		this.nextToMove = g.getNextToPlay().getDirection2();
		this.tricksTakenByNorthSouth = (byte) g.getTricksTaken(Player.NORTH_SOUTH);
		this.node = node;
	}

	public Node getNode() {
		return node;
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