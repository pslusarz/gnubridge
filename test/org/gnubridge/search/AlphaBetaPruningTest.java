package org.gnubridge.search;

import org.gnubridge.core.Player;

import junit.framework.TestCase;

public class AlphaBetaPruningTest extends TestCase {
	/**
	 *
	 *          root       W
	 *           / \
	 *   (1,1)  0   1      S
	 *             / \
	 *    (0,2) 1_0   1_1  E
	 *        
	 */

	public void testOneLevelAlphaPrune() {
		Node root = new Node(null, Player.WEST);
		Node node_0 = new Node(root, Player.SOUTH);
		node_0.setTricksTaken(Player.WEST_EAST, 1);
		Node node_1 = new Node(root, Player.SOUTH);
		Node node_1_0 = new Node(node_1, Player.EAST);
		@SuppressWarnings("unused")
		Node node_1_1 = new Node(node_1, Player.EAST);

		MockGame g = new MockGame();
		g.setPositionValue(node_1_0.getMoves(), 0, 2);
		Search s = new Search(g);
		s.examinePosition(node_1_0);
		assertTrue(node_1.isPruned());
		assertTrue(node_1.trimmed());
	}

	/**
	 *     
	 *                root       W
	 *                 / \
	 *         (1,1)  0   1      S
	 *                   / \
	 *          (0,2) 1_0   1_1  E
	 *        
	 */

	public void testAlphaPruneAlphaIsLocal() {
		Node root = new Node(null, Player.WEST);
		Node node_0 = new Node(root, Player.SOUTH);
		node_0.setTricksTaken(Player.WEST_EAST, 1);
		Node node_1 = new Node(root, Player.SOUTH);
		Node node_1_0 = new Node(node_1, Player.EAST);
		@SuppressWarnings("unused")
		Node node_1_1 = new Node(node_1, Player.EAST);

		MockGame g = new MockGame();
		g.setPositionValue(node_1_0.getMoves(), 0, 2);
		Search s = new Search(g);
		s.examinePosition(node_1_0);
		assertTrue(node_1.isPruned());
		assertTrue(node_1.trimmed());
	}

	/**
	 *
	 *          root       W
	 *             \
	 *              0      N
	 *             / \
	 *    (1,0) 0_0   0_1 (0,1) E
	 *        
	 */

	public void testNoAlphaPruneSubsequentChildren() {

		Node root = new Node(null, Player.WEST);
		Node node_0 = new Node(root, Player.NORTH);
		Node node_0_0 = new Node(node_0, Player.EAST);
		node_0_0.setTricksTaken(Player.WEST_EAST, 1);
		Node node_0_1 = new Node(node_0, Player.EAST);

		MockGame g = new MockGame();
		g.setPositionValue(node_0_1.getMoves(), 0, 1);
		Search s = new Search(g);
		s.examinePosition(node_0_1);
		assertFalse(node_0.isPruned());

	}

	/**
	 *
	 *          root       W
	 *           / \
	 *   (1,1)  0   1      W
	 *             / \
	 *    (0,2) 1_0   1_1  E
	 *        
	 */

	public void testAlphaPruneOnlyMinNodes() {
		Node root = new Node(null, Player.WEST);
		Node node_0 = new Node(root, Player.WEST);
		node_0.setTricksTaken(Player.WEST_EAST, 1);
		node_0.setTricksTaken(Player.NORTH_SOUTH, 1);
		Node node_1 = new Node(root, Player.WEST);
		Node node_1_0 = new Node(node_1, Player.EAST);
		@SuppressWarnings("unused")
		Node node_1_1 = new Node(node_1, Player.EAST);

		MockGame g = new MockGame();
		g.setPositionValue(node_1_0.getMoves(), 0, 2);
		Search s = new Search(g);
		s.examinePosition(node_1_0);
		assertFalse(node_1.isPruned());
	}

	/**
	 *
	 *          root       W
	 *           / \
	 *   (2,1)  0   1      N
	 *             / \
	 *          1_0   1_1  W
	 *               /   \
	 *     (0,3)  1_1_0  1_1_1 (1,2) W  
	 */

	public void testAlphaPruneWhenTrimming() {
		Node root = new Node(null, Player.WEST);
		Node node_0 = new Node(root, Player.NORTH);
		node_0.setTricksTaken(Player.WEST_EAST, 1);
		Node node_1 = new Node(root, Player.NORTH);
		@SuppressWarnings("unused")
		Node node_1_0 = new Node(node_1, Player.WEST);
		Node node_1_1 = new Node(node_1, Player.WEST);
		Node node_1_1_0 = new Node(node_1_1, Player.WEST);
		node_1_1_0.setLeaf(true);
		node_1_1_0.setTricksTaken(Player.WEST_EAST, 0);
		Node node_1_1_1 = new Node(node_1_1, Player.WEST);

		MockGame g = new MockGame();
		g.setPositionValue(node_1_1_1.getMoves(), 1, 2);
		Search s = new Search(g);
		s.usePruning(true);
		s.examinePosition(node_1_1_1);
		assertTrue(node_1.isPruned());
	}

	/**
	 *
	 *          root         W
	 *           / \
	 *   (1,1)  0   1        N
	 *              |
	 *             1_0       N
	 *            /   \
	 *    (0,2)1_0_0  1_0_1  E
	 *    
	 *        
	 */

	public void testAlphaPruneToNearestAlphaAncestor() {
		Node root = new Node(null, Player.WEST);
		Node node_0 = new Node(root, Player.NORTH);
		node_0.setTricksTaken(Player.WEST_EAST, 1);
		node_0.setTricksTaken(Player.NORTH_SOUTH, 1);
		Node node_1 = new Node(root, Player.NORTH);
		Node node_1_0 = new Node(node_1, Player.NORTH);

		Node node_1_0_0 = new Node(node_1_0, Player.EAST);
		@SuppressWarnings("unused")
		Node node_1_0_1 = new Node(node_1_0, Player.EAST);

		MockGame g = new MockGame();
		g.setPositionValue(node_1_0_0.getMoves(), 0, 2);
		Search s = new Search(g);
		s.examinePosition(node_1_0_0);
		assertTrue(node_1_0.isPruned());
		assertTrue(node_1_0.trimmed());
		assertTrue(node_1.isPruned());
		assertTrue(node_1.trimmed());
	}

	public void testLocalAlphaGrows() {
		Node root = new Node(null);
		root.setPlayerTurn(Player.WEST);
		Node node_0 = new Node(root);
		node_0.setPlayerTurn(Player.NORTH);
		node_0.setTricksTaken(Player.WEST_EAST, 1);
		Node node_1 = new Node(root);
		MockGame g = new MockGame();
		g.setPositionValue(node_1.getMoves(), 3, 2);

		Search s = new Search(g);
		assertEquals(1, root.getLocalAlpha());
		s.examinePosition(node_1);
		assertEquals(3, root.getLocalAlpha());
	}

	/**
	 *                 root              W
	 *                 /
	 *                0                  N
	 *              /  \ 
	 *     (1,1)  0_0   0_1              E
	 *                  /  \           
	 *        (2,0)  0_1_0  0_1_1        E       
	 */

	public void testOneLevelBetaPrune() {
		Node root = new Node(null, Player.WEST);
		Node node_0 = new Node(root, Player.NORTH);
		Node node_0_0 = new Node(node_0, Player.EAST);
		node_0_0.setTricksTaken(Player.WEST_EAST, 1);
		Node node_0_1 = new Node(node_0, Player.EAST);
		Node node_0_1_0 = new Node(node_0_1, Player.EAST);
		@SuppressWarnings("unused")
		Node node_0_1_1 = new Node(node_0_1, Player.EAST);

		MockGame g = new MockGame();
		g.setPositionValue(node_0_1_0.getMoves(), 2, 0);
		Search s = new Search(g);
		s.examinePosition(node_0_1_0);
		assertTrue(node_0_1.isBetaPruned());
		assertTrue(node_0_1.trimmed());
	}

	public void testBetaGetsReduced() {
		Node root = new Node(null);
		root.setPlayerTurn(Player.WEST);
		Node node_0 = new Node(root);
		node_0.setPlayerTurn(Player.NORTH);
		Node child = new Node(node_0);
		child.setTricksTaken(Player.WEST_EAST, 4);
		Node grandchild = new Node(node_0);
		MockGame g = new MockGame();
		g.setPositionValue(grandchild.getMoves(), 3, 2);
		Search s = new Search(g);
		assertEquals(4, node_0.getLocalBeta());
		s.examinePosition(grandchild);
		assertEquals(3, node_0.getLocalBeta());
	}

	public void testUnvisitedNodeIgnoredInLocalBeta() {
		Node root = new Node(null);
		root.setPlayerTurn(Player.WEST);
		Node node_0 = new Node(root);
		node_0.setPlayerTurn(Player.NORTH);
		Node child = new Node(node_0);
		child.setTricksTaken(Player.WEST_EAST, -1);
		Node grandchild = new Node(node_0);
		MockGame g = new MockGame();
		g.setPositionValue(grandchild.getMoves(), 3, 2);
		Search s = new Search(g);
		assertEquals(Node.BETA_UNINIT, node_0.getLocalBeta());
		s.examinePosition(grandchild);
		assertEquals(3, node_0.getLocalBeta());
	}

	/**         
	 *           root      W
	 *             |
	 *            00       N
	 *           / \
	 *   (0,3)  0   1      E
	 *             / \
	 *          1_0   1_1  S
	 *               /   \
	 *     (1,2)  1_1_0  1_1_1 (2,1) S  
	 */

	public void testBetaPruneWhenTrimming() {
		Node root = new Node(null, Player.WEST);
		Node node_00 = new Node(root, Player.NORTH);
		Node node_0 = new Node(node_00, Player.EAST);
		node_0.setTricksTaken(Player.WEST_EAST, 0);
		Node node_1 = new Node(node_00, Player.EAST);
		@SuppressWarnings("unused")
		Node node_1_0 = new Node(node_1, Player.SOUTH);
		Node node_1_1 = new Node(node_1, Player.SOUTH);
		Node node_1_1_0 = new Node(node_1_1, Player.SOUTH);
		node_1_1_0.setLeaf(true);
		node_1_1_0.setTricksTaken(Player.WEST_EAST, 1);
		Node node_1_1_1 = new Node(node_1_1, Player.SOUTH);

		MockGame g = new MockGame();
		g.setPositionValue(node_1_1_1.getMoves(), 2, 1);
		Search s = new Search(g);
		s.usePruning(true);
		s.examinePosition(node_1_1_1);
		assertTrue(node_1.isPruned());
	}
	
	/**         
	 *           root      W
	 *             |
	 *            00       N
	 *           / \
	 *   (0,3)  0   1      E
	 *             / \
	 *          1_0   1_1  E
	 *               /   \
	 *           1_1_0  1_1_1 (2,1) S  
	 */

	public void testBetaPruneToNearestBetaAncestor() {
		Node root = new Node(null, Player.WEST);
		Node node_00 = new Node(root, Player.NORTH);
		Node node_0 = new Node(node_00, Player.EAST);
		node_0.setTricksTaken(Player.WEST_EAST, 0);
		Node node_1 = new Node(node_00, Player.EAST);
		@SuppressWarnings("unused")
		Node node_1_0 = new Node(node_1, Player.SOUTH);
		Node node_1_1 = new Node(node_1, Player.EAST);
		Node node_1_1_0 = new Node(node_1_1, Player.SOUTH);
		node_1_1_0.setLeaf(true);
		Node node_1_1_1 = new Node(node_1_1, Player.SOUTH);

		MockGame g = new MockGame();
		g.setPositionValue(node_1_1_1.getMoves(), 2, 1);
		Search s = new Search(g);
		s.usePruning(true);
		s.examinePosition(node_1_1_1);
		assertTrue(node_1.isPruned());
	}


}
