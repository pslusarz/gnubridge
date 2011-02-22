package org.gnubridge.search.pruning;

import static org.gnubridge.core.Direction.*;

import java.util.Arrays;
import java.util.HashMap;

import junit.framework.TestCase;

import org.gnubridge.core.Direction;
import org.gnubridge.core.Player;
import org.gnubridge.search.Node;

public class AlphaBetaTest extends TestCase {

	private NodeWrapper protoRoot;
	private NodeWrapper root;
	private HashMap<String, NodeWrapper> nodes;

	/**
	 *
	 *      protoRoot      W
	 *           \
	 *          root       W
	 *           / \
	 *  (max:1) 0   1      S
	 *             / \
	 *   (max:0) 1_0  1_1  E        
	 */

	public void testOneLevelAlphaPrune1() {
		givenMax(WEST);
		nodeWithPath("0").withTricksForMax(1).withNextTurn(SOUTH);
		nodeWithPath("1").withNextTurn(SOUTH);
		nodeWithPath("1", "0").withNextTurn(EAST).withTricksForMax(0);
		nodeWithPath("1", "1").withNextTurn(EAST);

		whenPruning(nodeWithPath("1", "0"));

		assertTrue(nodeWithPath("1", "0").isAlphaPruned());
		assertTrue(nodeWithPath("1").isAlphaPruned());
	}

	private void whenPruning(NodeWrapper node) {
		AlphaBeta ab = new AlphaBeta();
		ab.prune(node.delegate);

	}

	private NodeWrapper nodeWithPath(String... moveSelectionsFromRoot) {
		if (moveSelectionsFromRoot.length == 1) {
			NodeWrapper node = nodes.get(moveSelectionsFromRoot[0]);
			if (node == null) {
				node = new NodeWrapper(root, NodeWrapper.NO_PLAYER_SELECTED);
				node.setKey(moveSelectionsFromRoot[0]);
				nodes.put(node.getKey(), node);
			}
			return node;
		} else {
			NodeWrapper parent = nodeWithPath(truncateLast(moveSelectionsFromRoot));
			String childKey = parent.getKey() + "_" + moveSelectionsFromRoot[moveSelectionsFromRoot.length - 1];
			NodeWrapper child = nodes.get(childKey);
			if (child == null) {
				child = new NodeWrapper(parent, NodeWrapper.NO_PLAYER_SELECTED);
				child.setKey(childKey);
				nodes.put(child.getKey(), child);
			}
			return child;
		}

	}

	private String[] truncateLast(String[] toBeTruncated) {
		return Arrays.copyOf(toBeTruncated, toBeTruncated.length - 1);
	}

	private void givenMax(Direction maxPlayer) {
		protoRoot = new NodeWrapper(null, maxPlayer.getValue());
		root = new NodeWrapper(protoRoot, maxPlayer.getValue());
		nodes = new HashMap<String, NodeWrapper>();

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

	private class NodeWrapper {
		private static final int NO_PLAYER_SELECTED = -1;
		private Node delegate;
		private String key = "";

		public NodeWrapper(NodeWrapper parent, int nextToPlay) {
			if (parent != null) {
				this.delegate = new Node(parent.delegate, nextToPlay);
			} else {
				this.delegate = new Node(null, nextToPlay);
			}
		}

		public void setKey(String key) {
			this.key = key;

		}

		public String getKey() {
			return key;
		}

		public NodeWrapper withTricksForMax(int numOfTricks) {
			delegate.setTricksTaken(protoRoot.delegate.getCurrentPair(), numOfTricks);
			return this;

		}

		public NodeWrapper withNextTurn(Direction playersTurn) {
			delegate.setPlayerTurn(playersTurn.getValue());
			return this;

		}

		public boolean isAlphaPruned() {
			return delegate.isAlphaPruned();
		}

	}
}
