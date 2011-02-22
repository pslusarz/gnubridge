package org.gnubridge.search.pruning;

import static org.gnubridge.core.Direction.*;

import java.util.HashMap;

import junit.framework.TestCase;

import org.gnubridge.core.Direction;
import org.gnubridge.core.Player;
import org.gnubridge.search.Node;

public class AlphaBetaTest extends TestCase {
	private static final String ACTING_ROOT_NODE = "*";
	private static final int NO_PLAYER_SELECTED = -1;
	/**
	 *
	 *        root         W
	 *           \
	 *           00        W
	 *           / \
	 *   (1,1)  0   1      S
	 *             / \
	 *    (0,2) 1_0   1_1  E
	 *        
	 */

	Node root;
	private Node currentNode;
	String currentNodeMnemonic;

	private HashMap<String, Node> nodes;

	AlphaBetaTest givenMaxPlayer(Direction maxPlayer) {
		currentNodeMnemonic = "";
		if (root == null) {
			root = new Node(null, maxPlayer.getValue());
			currentNode = new Node(root, maxPlayer.getValue());
			nodes = new HashMap<String, Node>();
			nodes.put(ACTING_ROOT_NODE, currentNode);
		} else {
			currentNode = nodes.get(ACTING_ROOT_NODE);
			assertEquals("may not switch the max player for a scenario", currentNode.getPlayerTurn(),
					maxPlayer.getValue());
		}
		return this;
	}

	AlphaBetaTest makingMove(String mnemonic) {
		assertNotNull("start building scenario with givenMaxPlayer()", root);
		String candidateMnemonic = currentNodeMnemonic + "_" + mnemonic;
		Node candidateNode = nodes.get(candidateMnemonic);
		if (candidateNode == null) {
			candidateNode = new Node(currentNode, NO_PLAYER_SELECTED);
			nodes.put(candidateMnemonic, candidateNode);
		}
		currentNodeMnemonic = candidateMnemonic;
		currentNode = candidateNode;

		return this;
	}

	AlphaBetaTest nextTurn(Direction playersTurn) {
		assertNotNull("initialize node with makingMove()", currentNode);
		assertTrue(
				"may not change players turn on existing node " + currentNodeMnemonic + " from "
						+ currentNode.getPlayerTurn() + " to " + playersTurn.getValue(),
				(currentNode.getPlayerTurn() == NO_PLAYER_SELECTED)
						|| (currentNode.getPlayerTurn() == playersTurn.getValue()));
		currentNode.setPlayerTurn(playersTurn.getValue());
		return this;
	}

	AlphaBetaTest maxCanCaptureTricks(int numOfTricks) {
		assertNotNull("initialize node with makingMove()", currentNode);
		currentNode.setTricksTaken(root.getCurrentPair(), numOfTricks);
		return this;
	}

	Node asNode() {
		return currentNode;
	}

	public void testOneLevelAlphaPrune1() {
		givenMaxPlayer(WEST).makingMove("1").maxCanCaptureTricks(1).nextTurn(SOUTH);
		givenMaxPlayer(WEST).makingMove("2").nextTurn(SOUTH).makingMove("1").maxCanCaptureTricks(0).nextTurn(EAST);
		givenMaxPlayer(WEST).makingMove("2").makingMove("2").nextTurn(EAST);

		whenPruning(givenMaxPlayer(WEST).makingMove("2").makingMove("1").asNode());

		assertTrue(givenMaxPlayer(WEST).makingMove("2").makingMove("1").asNode().isAlphaPruned());
		assertTrue(givenMaxPlayer(WEST).makingMove("2").asNode().isAlphaPruned());

	}

	/**
	 * 
	 * givenMax(West);
	 * select("1").maxCanCaptureTricks(1).nextTurn(SOUTH);
	 * select("2").nextTurn(SOUTH);
	 * select("2","1").nextTurn(EAST).maxCanCaptureTricks(0);
	 * select("2","2").nextTurn(EAST);
	 * 
	 * whenPruning(select("2","1"));
	 * assertTrue(select("2","1").isAlphaPruned());
	 * assertTrue(select("2").isAlphaPruned());
	 */

	private void whenPruning(Node node) {
		AlphaBeta ab = new AlphaBeta();
		ab.prune(node);
	}

	public void testOneLevelAlphaPrune() {
		Node root = new Node(null, WEST.getValue());
		Node node_00 = new Node(root, WEST.getValue());
		Node node_0 = new Node(node_00, SOUTH.getValue());
		node_0.setTricksTaken(Player.WEST_EAST, 1);
		Node node_1 = new Node(node_00, SOUTH.getValue());
		Node node_1_0 = new Node(node_1, EAST.getValue());
		@SuppressWarnings("unused")
		Node node_1_1 = new Node(node_1, EAST.getValue());

		//node_1_0.setTricksTaken(Player.NORTH_SOUTH, 2);
		node_1_0.setTricksTaken(Player.WEST_EAST, 0);
		AlphaBeta ab = new AlphaBeta();
		ab.prune(node_1_0);
		assertTrue(node_1_0.isAlphaPruned());
		assertTrue(node_1.isAlphaPruned());
	}

	/**
	 *
	 *          root       W
	 *           / \
	 *   (1,1)  0   1      S
	 *             / \
	 *    (0,2) 1_0   1_1  E
	 *        
	 */
	/**
	 * given(West)
	 */

	public void testDoNotAlphaPruneRootsChildrenSoThatHeuristicsMayBeUsed() {
		Node root = new Node(null, Direction.WEST_DEPRECATED);
		Node node_0 = new Node(root, Direction.SOUTH_DEPRECATED);
		node_0.setTricksTaken(Player.WEST_EAST, 1);
		Node node_1 = new Node(root, Direction.SOUTH_DEPRECATED);
		Node node_1_0 = new Node(node_1, Direction.EAST_DEPRECATED);
		@SuppressWarnings("unused")
		Node node_1_1 = new Node(node_1, Direction.EAST_DEPRECATED);

		node_1_0.setTricksTaken(Player.NORTH_SOUTH, 2);
		node_1_0.setTricksTaken(Player.WEST_EAST, 0);
		AlphaBeta ab = new AlphaBeta();
		ab.prune(node_1_0);
		assertFalse(node_1.isPruned());
	}
}
