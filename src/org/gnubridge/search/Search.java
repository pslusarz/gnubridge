package org.gnubridge.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.gnubridge.core.Card;
import org.gnubridge.core.Game;
import org.gnubridge.core.Hand;
import org.gnubridge.core.Player;

public class Search {

	private Node root;

	private Stack<Node> stack;

	private Game game;

	private List<Integer> finalMoves;

	private boolean usePruning = true;

	private int prunedAlpha;

	private int prunedBeta;

	private int positionsCount;

	private long runningTime;

	private int maxTricks = 13;

	private int prunedSequence;

	private int prunedPlayedSequence;

	private boolean useDuplicateRemoval = false;

	private int prunedDuplicatePosition;
	PositionLookup lookup;

	public Search(Node root) {
		this.root = root;
	}

	public Search(Game game) {
		this.game = game;
		stack = new Stack<Node>();
		finalMoves = new ArrayList<Integer>();
		finalMoves.add(0);
		finalMoves.add(0);
		finalMoves.add(0);
		finalMoves.add(0);
		lookup = new PositionLookup();
	}

	public Stack<Node> getStack() {
		return stack;
	}

	public Node getRoot() {
		return root;
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
	}

	public void setUseDuplicateRemoval(boolean b) {
		useDuplicateRemoval = b;
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

		if (position.oneTrickLeft()) {
			if (node == root) {
				Node move = new Node(node);
				move.setCardPlayed(player.getPossibleMoves(
						position.getCurrentTrick()).get(0));
				move.setPlayerCardPlayed(player);
			}
			position.playMoves(finalMoves);
			handleLeafNode(node, position);

		} else {
			if (position.getTricksPlayed() >= maxTricks) {
				handleLeafNode(node, position);
			} else {
				List<Card> moves = player.getPossibleMoves(position
						.getCurrentTrick());
				for (Card card : moves) {
					Node move = new Node(node);
					move.setCardPlayed(card);
					move.setPlayerCardPlayed(player);
					removeSiblingsInSequence(move, position);
					removeSiblingsInSequenceWithPlayedCards(move, position);
					removeDuplicatePositions(move);
					stack.push(move);
				}
			}
		}
	}

	private void removeDuplicatePositions(Node node) {
		if (useDuplicateRemoval()) {
			Game position = game.duplicate();
			position.playMoves(node.getMoves());
			  if (lookup.positionEncountered(position))	{
				  //System.out.println(node.printMoves());
				  node.pruneAsDuplicatePosition();
			  }
			}
		
	}

	private void removeSiblingsInSequenceWithPlayedCards(Node move,
			Game position) {
		boolean shouldTrim = false;

		List<Card> siblingsInSuit = move.getSiblingsInColor();
		List<Card> orderedPlayedCardsInSuit = position
				.getPlayedCardsHiToLow(move.getCardPlayed().getDenomination());
		Card lowerCard;
		Card higherCard;
		for (Card sibling : siblingsInSuit) {
			higherCard = getHigher(move.getCardPlayed(), sibling);
			lowerCard = getLower(move.getCardPlayed(), sibling);
			boolean isSequence = false;
			Card previous = higherCard;
			for (Card played : orderedPlayedCardsInSuit) {
				if (played.getValue() > higherCard.getValue()) {
					continue;
				}
				if (previous.getValue() == played.getValue() + 1) {
					isSequence = true;
				} else {
					isSequence = false;
					break;
				}
				if (isSequence && played.getValue() == lowerCard.getValue() + 1) {
					shouldTrim = true;
					break;
				}
				previous = played;
			}
			if (shouldTrim) {
				break;
			}
		}
		if (shouldTrim) {
			move.setPruned(true, Node.PRUNE_SEQUENCE_SIBLINGS_PLAYED);
		}

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
			if (Math.abs(sibling.getValue() - move.getCardPlayed().getValue()) == 1) {
				shouldTrim = true;
				break;
			}

		}

		if (shouldTrim) {
			move.setPruned(true, Node.PRUNE_SEQUENCE_SIBLINGS);
		}

	}

	private void handleLeafNode(Node node, Game position) {
		node.setLeaf(true);
		node.setTricksTaken(Player.WEST_EAST, position
				.getTricksTaken(Player.WEST_EAST));
		node.setTricksTaken(Player.NORTH_SOUTH, position
				.getTricksTaken(Player.NORTH_SOUTH));
		if (usePruning) {
			if (node.shouldBeAlphaPruned()) {
				node.alphaPrune();
			} else if (node.shouldBeBetaPruned()) {
				node.betaPrune();
			}

		}
		removeExpandedBranches(node);

	}

	/**
	 * 1. evaluate all child nodes and find one where current player or his
	 * partner takes the most tricks. 2. delete all other nodes 3. set tricks
	 * taken on current node to the value of the child selected in 1. 4. if last
	 * child, then call trim on parent
	 */

	public void trim(Node node) {
		int i = 0;
		Node maxChild = null;
		for (Node child : node.children) {
			if (!child.isPruned()
					&& (maxChild == null || child.getTricksTaken(node
							.getCurrentPair()) > maxChild.getTricksTaken(node
							.getCurrentPair()))) {
				maxChild = child;
				node.trimAllPriorChildren(i);

			} else {
				child.trimmed = true;
				node.children.set(i, null);
			}
			i++;
		}

		if (maxChild != null) {
			node.setTricksTaken(Player.WEST_EAST, maxChild
					.getTricksTaken(Player.WEST_EAST));
			node.setTricksTaken(Player.NORTH_SOUTH, maxChild
					.getTricksTaken(Player.NORTH_SOUTH));

			if (usePruning() && node.shouldBeAlphaPruned()) {
				node.alphaPrune();
			}
			if (usePruning() && node.shouldBeBetaPruned()) {
				node.betaPrune();
			}
			

		}
		if (node.parent != null && node.parent.isLastVisitedChild(node)) {
			trim(node.parent);
		}
		node.trimmed = true;

	}

	private boolean useDuplicateRemoval() {
		return useDuplicateRemoval;
	}

	private void removeExpandedBranches(Node node) {
		if (node.parent != null
				&& (node.parent.isLastVisitedChild(node) || node.parent
						.isPruned())) {
			trim(node.parent);
		}
	}

	public List<Card> getBestMoves() {
		List<Card> result = new ArrayList<Card>();
		result.add(root.getBestMove().getCardPlayed());
		return result;
	}

	public void printOptimalPath() {
		root.printOptimalPath(game);
	}

	public void usePruning(boolean b) {
		usePruning = b;

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
		if (usePruning) {
			pruneType = "Pruned";
		}
		System.out.println(pruneType + " search took (msec): "
				+ getRunningTime());
		System.out.println("  Positions examined: " + getPositionsExamined());
		if (usePruning) {
			System.out.println("  Alpha prunes: " + getPrunedAlpha());
			System.out.println("  Beta prunes: " + getPrunedBeta());
			System.out.println("  Sequence prunes: " + getPrunedSequence());
			System.out.println("  Played Sequence prunes: "
					+ getPrunedPlayedSequence());
		}
		if (useDuplicateRemoval()) {
			System.out.println("  Duplicate position prunes: " + prunedDuplicatePosition);	
		}
		System.out.println("West/East tricks taken: "
				+ root.getTricksTaken(Player.WEST_EAST));

	}

	private int getPrunedPlayedSequence() {
		return prunedPlayedSequence;
	}

	private int getPrunedSequence() {
		return prunedSequence;
	}

	public boolean usePruning() {
		return usePruning;
	}

	public void setMaxTricks(int i) {
		maxTricks = i;

	}

}
