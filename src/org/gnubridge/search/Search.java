package org.gnubridge.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.gnubridge.core.Card;
import org.gnubridge.core.Game;
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
			collectStats(node);
			examinePosition(node);
			i++;
		}
		runningTime = System.currentTimeMillis() - start;

	}

	private void initStats() {
		runningTime = 0;
		prunedAlpha = 0;
		prunedBeta = 0;
		positionsCount = 0;
	}

	private void collectStats(Node node) {
		positionsCount++;
		if (node.isAlphaPruned()) {
			prunedAlpha++;
		} else if (node.isBetaPruned()) {
			prunedBeta++;
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
				move.setCardPlayed(player.getPossibleMoves(position
						.getCurrentTrick()).get(0));
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
					stack.push(move);
				}
			}
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
		}
		System.out.println("West/East tricks taken: "
				+ root.getTricksTaken(Player.WEST_EAST));

	}

	public boolean usePruning() {
		return usePruning;
	}

	public void setMaxTricks(int i) {
		maxTricks = i;

	}

}
