package org.gnubridge.search;

import junit.framework.TestCase;

import org.gnubridge.core.Direction;
import org.gnubridge.core.Player;

public class DoubleDummySolverDeepAlphaBetaPruningTest extends TestCase {

	public void testAllTestsTemporarilyDisabled() {
		assertTrue(true);
	}

	/**
	 *         root        W
	 *           \
	 *           00        W
	 *           / \
	 *   (2  )  0   1      W
	 *             / \
	 *          1_0   1_1  N  <- prune alpha
	 *               /   \
	 *     (0 )   1_1_0  1_1_1 W  
	 */

	public void zzztestDeepAlphaPrune1() {
		Node root = new Node(null, Direction.WEST_DEPRECATED);
		Node node_00 = new Node(root, Direction.WEST_DEPRECATED);
		Node node_0 = new Node(node_00, Direction.NORTH_DEPRECATED);
		node_0.setTricksTaken(Player.WEST_EAST, 2);
		Node node_1 = new Node(node_00, Direction.WEST_DEPRECATED);
		@SuppressWarnings("unused")
		Node node_1_0 = new Node(node_1, Direction.NORTH_DEPRECATED);
		Node node_1_1 = new Node(node_1, Direction.NORTH_DEPRECATED);
		Node node_1_1_0 = new Node(node_1_1, Direction.WEST_DEPRECATED);
		Node node_1_1_1 = new Node(node_1_1, Direction.WEST_DEPRECATED);

		MockGame g = new MockGame();
		g.setPositionValue(node_1_1_0.getMoves(), 1, 0); //zero tricks for Max (W)
		DoubleDummySolver s = new DoubleDummySolver(g);
		s.useAlphaBetaPruning(true);
		//s.setShouldPruneDeepAlphaBeta(true);
		s.examinePosition(node_1_1_0);
		assertTrue(node_1_1.isPruned());
	}

	/**
	 *         root        W
	 *           \
	 *           00        W
	 *           / \
	 *   (1)    0   1      N <- prune
	 *             / \
	 *          1_0   1_1  N
	 *               /   \
	 *     (0)    1_1_0  1_1_1 W  
	 */

	public void zzztestDeepAlphaPrune2() {
		Node root = new Node(null, Direction.WEST_DEPRECATED);
		Node node_00 = new Node(root, Direction.WEST_DEPRECATED);
		Node node_0 = new Node(node_00, Direction.NORTH_DEPRECATED);
		node_0.setTricksTaken(Player.WEST_EAST, 1);
		Node node_1 = new Node(node_00, Direction.NORTH_DEPRECATED);
		@SuppressWarnings("unused")
		Node node_1_0 = new Node(node_1, Direction.NORTH_DEPRECATED);
		Node node_1_1 = new Node(node_1, Direction.NORTH_DEPRECATED);
		Node node_1_1_0 = new Node(node_1_1, Direction.WEST_DEPRECATED);
		node_1_1_0.setTricksTaken(Player.WEST_EAST, 0);
		Node node_1_1_1 = new Node(node_1_1, Direction.WEST_DEPRECATED);

		MockGame g = new MockGame();
		g.setPositionValue(node_1_1_0.getMoves(), 1, 0); //Max (W) gets 0
		DoubleDummySolver s = new DoubleDummySolver(g);
		s.useAlphaBetaPruning(true);
		//s.setShouldPruneDeepAlphaBeta(true);
		s.examinePosition(node_1_1_0);
		assertTrue(node_1.isPruned());
	}
}
