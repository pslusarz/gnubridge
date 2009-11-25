package org.gnubridge.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.gnubridge.core.Card;
import org.gnubridge.core.Game;
import org.gnubridge.core.Player;
import org.gnubridge.core.Trick;

public class DoubleDummySolver {

	private Node root;

	private Stack<Node> stack;

	private Game game;

	private List<Integer> finalMoves;

	private int prunedAlpha;

	private int prunedBeta;

	private int positionsCount;

	private long runningTime;

	private int maxTricks = 13;

	private int prunedSequence;

	private int prunedPlayedSequence;

	private boolean useAlphaBetaPruning = true;
	private boolean useDuplicateRemoval = true;
	private boolean shouldPruneLowestCardInLostTrick = false;
	private boolean shouldPruneCardsInSequence = true;
	private boolean shouldPruneCardsInPlayedSequence = true;

	private int prunedDuplicatePosition;
	PositionLookup lookup;

	private int prunedLowestCardInLostTrick;

	private boolean terminateIfRootOnlyHasOneValidMove = true;

	public void setTerminateIfRootOnlyHasOneValidMove(boolean terminateIfRootOnlyHasOneValidMove) {
		this.terminateIfRootOnlyHasOneValidMove = terminateIfRootOnlyHasOneValidMove;
	}

	public DoubleDummySolver(Node root) {
		this.root = root;
	}

	public DoubleDummySolver(Game game) {
		this.game = game;
		stack = new Stack<Node>();
		finalMoves = new ArrayList<Integer>();
		finalMoves.add(0);
		finalMoves.add(0);
		finalMoves.add(0);
		finalMoves.add(0);
		lookup = new PositionLookup();
	}

	public void search() {
		long start = System.currentTimeMillis();
		initStats();
		root = new Node(null);
		stack.push(root);
		int i = 0;

		while (!stack.empty()) {
			Node node = stack.pop();
			examinePosition(node);
			collectStats(node);
			i++;
		}
		runningTime = System.currentTimeMillis() - start;

	}

	private void initStats() {
		runningTime = 0;
		prunedAlpha = 0;
		prunedBeta = 0;
		prunedDuplicatePosition = 0;
		prunedSequence = 0;
		prunedPlayedSequence = 0;
		positionsCount = 0;
		prunedLowestCardInLostTrick = 0;
	}

	public void setUseDuplicateRemoval(boolean b) {
		useDuplicateRemoval = b;
	}

	public void setShouldPruneCardsInSequence(boolean b) {
		shouldPruneCardsInSequence = b;
	}

	@Deprecated
	/**
	 *  @deprecated "feature currently disabled"
	 */
	public void setShouldPruneCardsInPlayedSequence(boolean b) {
		shouldPruneCardsInPlayedSequence = b;
	}

	private void collectStats(Node node) {
		positionsCount++;
		if (node.isAlphaPruned()) {
			prunedAlpha++;
		} else if (node.isBetaPruned()) {
			prunedBeta++;
		} else if (node.isSequencePruned()) {
			prunedSequence++;
		} else if (node.isPlayedSequencePruned()) {
			prunedPlayedSequence++;
		} else if (node.isPrunedDuplicatePosition()) {
			prunedDuplicatePosition++;
		} else if (node.isPrunedLowestCardInLostTrick()) {
			prunedLowestCardInLostTrick++;
		}

	}

	public int getPositionsExamined() {
		return positionsCount;
	}

	public void examinePosition(Node node) {
		if (node.isPruned()) {
			return;
		}
		Game position = game.duplicate();
		position.playMoves(node.getMoves());

		Player player = position.getNextToPlay();
		node.setPlayerTurn(player.getDirection());
		node.setPosition(position);
		for (Card card : player.getPossibleMoves(position.getCurrentTrick())) {
			makeChildNodeForCardPlayed(node, player, card);
		}

		if (position.oneTrickLeft()) {
			position.playMoves(finalMoves);
		}
		checkDuplicatePositions(node, position);
		if (position.getTricksPlayed() >= maxTricks || position.isDone() || node.hasIdenticalTwin()) {
			node.setLeaf(true);
			trim(node);
		} else {
			for (Node move : node.children) {
				if (shouldPruneCardsInSequence) {
					removeSiblingsInSequence(move, position);
				}
				if (shouldPruneCardsInPlayedSequence) {
					//removeSiblingsInSequenceWithPlayedCards(move, position);
				}
			}
			if (shouldPruneLowestCardInLostTrick) {
				for (Node move : node.children) {
					ifCannotTakeTrickPlayLowestCardInColor(move, position);
				}
			}
			if (!rootOnlyHasOneValidMove(node) || !terminateIfRootOnlyHasOneValidMove) {
				for (Node move : node.children) {
					// TODO later if (!move.isPruned()) {
					stack.push(move);

				}
			}
		}
	}

	private boolean rootOnlyHasOneValidMove(Node node) {
		if (node == root && node.getUnprunedChildCount() == 1) {
			return true;
		} else {
			return false;
		}
	}

	private void ifCannotTakeTrickPlayLowestCardInColor(Node move, Game position) {
		Trick currentTrick = position.getCurrentTrick();
		if (currentTrick.getCards().size() == 0) {
			return;
		}
		if (!currentTrick.getDenomination().equals(move.getCardPlayed().getDenomination())) {
			return;
		}
		if (move.isPruned()) {
			return;
		}

		if (currentTrick.whoPlayed(currentTrick.getHighestCard()).isPartnerWith(move.getPlayerCardPlayed())) {
			return;
		}

		List<Card> siblingsInSuit = move.getSiblingsInColor();

		Card higherCard;
		for (Card sibling : siblingsInSuit) {
			if (move.getSiblingNodeForCard(sibling).isPruned()) {
				continue;
			}
			higherCard = getHigher(move.getCardPlayed(), sibling);

			if (higherCard.equals(move.getCardPlayed()) && cannotTakeTrick(higherCard, currentTrick)) {
				move.pruneAsLowestCardInLostTrick();

				break;
			}
		}

	}

	private boolean cannotTakeTrick(Card card, Trick trick) {
		return trick.getHighestCard().trumps(card, trick.getTrump())
				|| (trick.getHighestCard().hasSameColorAs(card) && trick.getHighestCard().hasGreaterValueThan(card));
	}

	private void checkDuplicatePositions(Node node, Game position) {
		if (useDuplicateRemoval()) {
			if (lookup.positionEncountered(position, node.getTricksTaken())) {
				byte[] previouslyEncounteredNode = lookup.getNode(position);
				node.setIdenticalTwin(previouslyEncounteredNode);
			}
		}

	}

	private void makeChildNodeForCardPlayed(Node parent, Player player, Card card) {
		Node move = new Node(parent);
		move.setCardPlayed(card);
		move.setPlayerCardPlayed(player);
	}

	private void removeSiblingsInSequenceWithPlayedCards(Node move, Game position) {
		List<Card> orderedPlayedCardsInSuit = position.getPlayedCardsHiToLow(move.getCardPlayed().getDenomination());
		if (orderedPlayedCardsInSuit.isEmpty()) {
			return;
		}
		boolean shouldTrim = false;

		List<Card> siblingsInSuit = move.getSiblingsInColor();
		for (Card sibling : siblingsInSuit) {
			Card higherCard = getHigher(move.getCardPlayed(), sibling);
			Card lowerCard = getLower(move.getCardPlayed(), sibling);
			boolean isSequence = cardsInSuitContainSequence(lowerCard.getValue(), higherCard.getValue(),
					orderedPlayedCardsInSuit);

			if (isSequence && higherCard.equals(move.getCardPlayed())) {
				shouldTrim = true;
				break;
			}
		}
		if (shouldTrim) {
			move.pruneAsSequenceSiblingPlayed();
		}

	}

	private boolean cardsInSuitContainSequence(int low, int high, List<Card> othersInSuitHighToLow) {
		List<Card> inBetween = discardCardsOutsideLimits(low, high, othersInSuitHighToLow);
		return high - low == inBetween.size() + 1;
	}

	private List<Card> discardCardsOutsideLimits(int low, int high, List<Card> orderedPlayedCardsInSuit) {
		List<Card> result = new ArrayList<Card>();
		for (Card card : orderedPlayedCardsInSuit) {
			if (card.getValue() > low && card.getValue() < high) {
				result.add(card);
			}
		}
		return result;
	}

	private Card getLower(Card c1, Card c2) {
		if (c1.getValue() < c2.getValue()) {
			return c1;
		} else {
			return c2;
		}
	}

	private Card getHigher(Card c1, Card c2) {
		if (c1.getValue() > c2.getValue()) {
			return c1;
		} else {
			return c2;
		}
	}

	private void removeSiblingsInSequence(Node move, Game position) {
		boolean shouldTrim = false;
		List<Card> cardsInSuit = move.getSiblingsInColor();
		for (Card sibling : cardsInSuit) {
			if (sibling.getValue() - move.getCardPlayed().getValue() == 1) {
				shouldTrim = true;
				break;
			}

		}

		if (shouldTrim) {
			move.pruneAsSequenceSibling();
		}

	}

	/**
	 * 1. evaluate all child nodes and find one where current player or his partner takes the most tricks. 
	 * 2. delete all other nodes 
	 * 3. set tricks taken on current node to the value of the child selected in 1. 
	 * 4. if last child, then call trim on parent
	 */

	public void trim(Node node) {
		if (root == node) {
			node.nullAllSubstandardChildren();
		} else {
			node.nullAllChildrenExceptOne();
		}
		node.calculateValue();
		if (useAlphaBetaPruning) {
			pruneAlphaBeta(node);
		}
		if (node.canTrim()) {
			trim(node.parent);
		}
		node.trimmed = true;

	}

	private void pruneAlphaBeta(Node node) {
		if (node.shouldBeAlphaPruned()) {
			node.alphaPrune();
		}
		if (node.shouldBeBetaPruned()) {
			node.betaPrune();
		}

	}

	private boolean useDuplicateRemoval() {
		return useDuplicateRemoval;
	}

	public List<Card> getBestMoves() {
		List<Card> result = new ArrayList<Card>();
		result.add(root.getBestMove().getCardPlayed());
		return result;
	}

	public void printOptimalPath() {
		System.out.println("Optimal path in this search: ");
		root.printOptimalPath(game);
	}

	public void useAlphaBetaPruning(boolean b) {
		useAlphaBetaPruning = b;

	}

	public long getRunningTime() {
		return runningTime;
	}

	public int getPrunedCount() {
		return prunedAlpha + prunedBeta;
	}

	public int getPrunedAlpha() {
		return prunedAlpha;
	}

	public int getPrunedBeta() {
		return prunedBeta;
	}

	public void printStats() {
		String pruneType = "Unpruned";
		if (useAlphaBetaPruning) {
			pruneType = "Pruned";
		}
		System.out.println(pruneType + " search took (msec): " + getRunningTime());
		System.out.println("  Positions examined: " + getPositionsExamined());
		if (useAlphaBetaPruning) {
			System.out.println("  Alpha prunes: " + getPrunedAlpha());
			System.out.println("  Beta prunes: " + getPrunedBeta());
			System.out.println("  Sequence prunes: " + getPrunedSequence());
			System.out.println("  Played Sequence prunes: " + getPrunedPlayedSequence());
		}
		if (useDuplicateRemoval()) {
			System.out.println("  Duplicate position prunes: " + prunedDuplicatePosition);
		}
		if (shouldPruneLowestCardInLostTrick) {
			System.out.println("  Lowest card to lost trick prunes: " + getPrunedLowestCardInLostTrick());
		}
		System.out.println("West/East tricks taken: " + root.getTricksTaken(Player.WEST_EAST));
		System.out.println("North/South tricks taken: " + root.getTricksTaken(Player.NORTH_SOUTH));

	}

	private int getPrunedLowestCardInLostTrick() {
		return prunedLowestCardInLostTrick;
	}

	private int getPrunedPlayedSequence() {
		return prunedPlayedSequence;
	}

	private int getPrunedSequence() {
		return prunedSequence;
	}

	public boolean isAlphaBetaPruning() {
		return useAlphaBetaPruning;
	}

	public void setMaxTricks(int i) {
		maxTricks = i;

	}

	public Stack<Node> getStack() {
		return stack;
	}

	public Node getRoot() {
		return root;
	}

	public void setUsePruneLowestCardToLostTrick(boolean b) {
		shouldPruneLowestCardInLostTrick = b;

	}

}
