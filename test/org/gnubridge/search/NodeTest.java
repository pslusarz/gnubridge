package org.gnubridge.search;

import junit.framework.TestCase;

import org.gnubridge.core.Direction;

public class NodeTest extends TestCase {
	public void testGetMovesRoot() {
		Node root = new Node(null);
		assertEquals(0, root.getMoves().size());
	}

	public void testGetMovesFirstChild() {
		Node root = new Node(null);
		Node child = new Node(root);
		assertEquals(1, child.getMoves().size());
		assertEquals(0, child.getMoves().get(0).intValue());
	}

	public void testGetMovesSecondChild() {
		Node root = new Node(null);
		@SuppressWarnings("unused")
		Node child1 = new Node(root);
		Node child2 = new Node(root);
		assertEquals(1, child2.getMoves().size());
		assertEquals(1, child2.getMoves().get(0).intValue());
	}

	public void testGetMovesGrandchild() {
		Node root = new Node(null);
		@SuppressWarnings("unused")
		Node child1 = new Node(root);
		Node child2 = new Node(root);
		Node grandChild = new Node(child2);
		assertEquals(2, grandChild.getMoves().size());
		assertEquals(1, grandChild.getMoves().get(0).intValue());
		assertEquals(0, grandChild.getMoves().get(1).intValue());
	}












	

	
	public void testPrunedIfParentThenChild() {
		Node root = new Node(null);
		Node child = new Node(root);
		assertFalse(child.isPruned());
		root.setPruned(true, Node.PRUNE_ALPHA);
		assertTrue(child.isPruned());
	}
	
	public void testIsAlpha() {
	    Node root = new Node(null);
	    root.setPlayerTurn(Direction.WEST);
	    Node n_1 = new Node(root);
	    n_1.setPlayerTurn(Direction.NORTH);
	    Node n_1_1 = new Node(n_1);
	    n_1_1.setPlayerTurn(Direction.EAST);	    
	    Node n_1_1_1 = new Node(n_1_1);
	    n_1_1_1.setPlayerTurn(Direction.SOUTH);
	    Node n_1_1_1_1 = new Node(n_1_1_1);
	    n_1_1_1_1.setPlayerTurn(Direction.SOUTH);
	    Node n_1_1_1_2 = new Node(n_1_1_1);
	    n_1_1_1_2.setPlayerTurn(Direction.EAST);
	    assertTrue(root.isAlpha());
	    assertFalse(n_1.isAlpha());
	    assertTrue(n_1_1.isAlpha());
	    assertFalse(n_1_1_1.isAlpha());
	    assertFalse(n_1_1_1_1.isAlpha());
	    assertTrue(n_1_1_1_2.isAlpha());
	}
	
	public void testIsAlphaBetaPruned() {
		Node root = new Node(null);
		Node child = new Node(root);
		root.setPruned(true, Node.PRUNE_ALPHA);
		assertTrue(child.isAlphaPruned());
		assertTrue(child.isPruned());
		assertFalse(child.isBetaPruned());
		
		root.setPruned(true, Node.PRUNE_BETA);
		assertTrue(child.isBetaPruned());
		assertTrue(child.isPruned());
		assertFalse(child.isAlphaPruned());		
	}
	
	
	

}

class MockNode extends Node {

	public MockNode(Node parent) {
		super(parent);
	}

	public MockNode(Node parent, boolean trim, boolean leaf) {
		this(parent);
		trimmed = trim;
		isLeaf = leaf;
	}

	private boolean trimmed = false;

	private boolean isLeaf;

	public void trim() {
		this.trimmed = true;
	}

	public boolean trimmed() {
		return this.trimmed;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

}
