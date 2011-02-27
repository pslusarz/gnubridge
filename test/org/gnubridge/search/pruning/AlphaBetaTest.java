package org.gnubridge.search.pruning;

import static org.gnubridge.core.Direction.*;

import org.gnubridge.core.Direction;
import org.gnubridge.core.Player;
import org.gnubridge.search.Node;

public class AlphaBetaTest extends PruningTestCase {

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

	/**      
	 *        protoRoot            W
	 *           \
	 *          root               W
	 *             \
	 *              0              N
	 *             / \
	 *   (max:1) 0_0   0_1 (max:0) E
	 *        
	 */

	public void testNoAlphaPruneSubsequentChildren() {
		givenMax(WEST);
		nodeWithPath("0").withNextTurn(NORTH);
		nodeWithPath("0", "0").withNextTurn(EAST).withTricksForMax(1);
		nodeWithPath("0", "1").withNextTurn(EAST).withTricksForMax(0);
		whenPruning(nodeWithPath("0", "1"));
		assertFalse(nodeWithPath("0").isAlphaPruned());
	}

	/**
	 *       protoRoot          W   not realistic to have one player move 3 times, 
	 *                              but we can allow any number of nodes between  
	 *           \                  protoroot and root to include min moves
	 *          root            W
	 *           / \
	 * (max:1) 0    1           W
	 *             / \
	 *  (max:0)  1_0  1_1       E 
	 *        
	 */

	public void testAlphaPruneOnlyMinNodes() {
		givenMax(WEST);
		nodeWithPath("0").withNextTurn(WEST).withTricksForMax(1);
		nodeWithPath("1").withNextTurn(WEST);
		nodeWithPath("1", "0").withNextTurn(EAST).withTricksForMax(0);
		nodeWithPath("1", "1").withNextTurn(EAST);
		whenPruning(nodeWithPath("1", "0"));
		assertFalse(nodeWithPath("1").isAlphaPruned());
	}

	/**
	 *       protoRoot                  W
	 *           \
	 *           root                   W
	 *           / \
	 * (max:2)  0   1                   N
	 *             / \
	 *          1_0   1_1     (max:1)   E
	 *               /   \
	 *   (max:0)  1_1_0  1_1_1 (max:1)  E  
	 */

	public void testAlphaPruneUpstream() {
		givenMax(WEST);
		nodeWithPath("0").withNextTurn(NORTH).withTricksForMax(2);
		nodeWithPath("1").withNextTurn(NORTH);
		nodeWithPath("1", "0").withNextTurn(EAST);
		nodeWithPath("1", "1").withNextTurn(EAST).withTricksForMax(1);
		nodeWithPath("1", "1", "0").withNextTurn(EAST).withTricksForMax(0);
		nodeWithPath("1", "1", "1").withNextTurn(EAST).withTricksForMax(1);
		whenPruning(nodeWithPath("1", "1", "1"));
		assertTrue(nodeWithPath("1").isAlphaPruned());
	}

	/**
	 *       protoRoot         W
	 *           \
	 *           root          W
	 *           / \
	 * (max:1)  0   1          N
	 *              |
	 *             1_0         N
	 *            /   \
	 * (max:0) 1_0_0  1_0_1    E
	 *    
	 *        
	 */

	public void testAlphaPruneToNearestAlphaAncestor() {
		givenMax(WEST);
		nodeWithPath("0").withNextTurn(NORTH).withTricksForMax(2);
		nodeWithPath("1").withNextTurn(NORTH);
		nodeWithPath("1", "0").withNextTurn(NORTH);
		nodeWithPath("1", "0", "0").withNextTurn(EAST).withTricksForMax(0);
		nodeWithPath("1", "0", "1").withNextTurn(EAST);
		whenPruning(nodeWithPath("1", "0", "0"));
		assertTrue(nodeWithPath("1", "0").isAlphaPruned());
		assertTrue(nodeWithPath("1").isAlphaPruned());
	}

	//TODO: this test seems to belong to NodeTest
	public void testLocalAlphaGrows() {
		givenMax(WEST);
		nodeWithPath("0").withNextTurn(NORTH).withTricksForMax(1);
		assertEquals(1, root.getLocalAlpha());
		nodeWithPath("1").withTricksForMax(3);
		assertEquals(3, root.getLocalAlpha());
	}

	//TODO: this test seems to belong to NodeTest
	public void testBetaGetsReduced() {
		givenMax(WEST);
		nodeWithPath("0").withNextTurn(NORTH);
		nodeWithPath("0", "0").withTricksForMax(4);
		assertEquals(4, nodeWithPath("0").getLocalBeta());
		nodeWithPath("0", "1").withTricksForMax(3);
		assertEquals(3, nodeWithPath("0").getLocalBeta());
	}

	//TODO: this test seems to belong to NodeTest
	public void testUnvisitedNodeIgnoredInLocalBeta() {
		givenMax(WEST);
		nodeWithPath("0").withNextTurn(NORTH);
		nodeWithPath("0", "0").withTricksForMax(Node.BETA_UNINIT);
		assertEquals(Node.BETA_UNINIT, nodeWithPath("0").getLocalBeta());
		nodeWithPath("0", "1").withTricksForMax(3);
		assertEquals(3, nodeWithPath("0").getLocalBeta());
	}

	/**                  
	 *              protoRoot            W
	 *                   |
	 *                 root              W
	 *                 /
	 *                0                  N
	 *              /  \ 
	 *     (1,1)  0_0   0_1              E
	 *                  /  \           
	 *        (2,0)  0_1_0  0_1_1        E       
	 */

	public void testOneLevelBetaPrune() {
		givenMax(WEST);
		nodeWithPath("0").withNextTurn(NORTH);
		nodeWithPath("0", "0").withNextTurn(EAST).withTricksForMax(1);
		nodeWithPath("0", "1").withNextTurn(EAST);
		nodeWithPath("0", "1", "0").withNextTurn(EAST).withTricksForMax(2);
		nodeWithPath("0", "1", "1").withNextTurn(EAST);
		whenPruning(nodeWithPath("0", "1", "0"));
		assertTrue(nodeWithPath("0", "1", "0").isBetaPruned());
	}

	/**      
	 *         protoRoot                 W
	 *             |
	 *           root                    W
	 *             |
	 *            0                      N
	 *           / \
	 * (max:0) 0_0 0_1                   E
	 *             / \
	 *        0_1_0  0_1_1               S
	 *               /   \
	 *  (max:1) 0_1_1_0  0_1_1_1 (max:2) S  
	 */

	public void testBetaPruneUpstream() {
		givenMax(WEST);
		nodeWithPath("0").withNextTurn(NORTH);
		nodeWithPath("0", "0").withNextTurn(EAST).withTricksForMax(0);
		nodeWithPath("0", "1").withNextTurn(EAST);
		nodeWithPath("0", "1", "0").withNextTurn(SOUTH);
		nodeWithPath("0", "1", "1").withNextTurn(SOUTH).withTricksForMax(2);
		nodeWithPath("0", "1", "1", "0").withNextTurn(SOUTH).withTricksForMax(1);
		nodeWithPath("0", "1", "1", "1").withNextTurn(SOUTH).withTricksForMax(2);
		whenPruning(nodeWithPath("0", "1", "1", "1"));
		assertTrue(nodeWithPath("0", "1").isBetaPruned());
	}

	/** 
	 *          protoRoot               W
	 *             |
	 *           root                   W
	 *             |
	 *            0                     N
	 *           / \
	 * (max:0) 0_0  0_1                 E
	 *             / \
	 *         0_1_0  0_1_1             E
	 *               /   \
	 *         0_1_1_0  0_1_1_1 (max:2) S  
	 */

	public void testBetaPruneToNearestBetaAncestor() {
		givenMax(WEST);
		nodeWithPath("0").withNextTurn(NORTH);
		nodeWithPath("0", "0").withNextTurn(EAST).withTricksForMax(0);
		nodeWithPath("0", "1").withNextTurn(EAST);
		nodeWithPath("0", "1", "0").withNextTurn(EAST);
		nodeWithPath("0", "1", "1").withNextTurn(EAST);
		nodeWithPath("0", "1", "1", "0").withNextTurn(SOUTH);
		nodeWithPath("0", "1", "1", "1").withNextTurn(SOUTH).withTricksForMax(2);
		whenPruning(nodeWithPath("0", "1", "1", "1"));
		assertTrue(nodeWithPath("0", "1").isBetaPruned());
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

	@Override
	protected void whenPruning(NodeWrapper node) {
		AlphaBeta ab = new AlphaBeta();
		ab.prune(node.delegate);

	}

}
