package org.gnubridge.search;

import java.util.ArrayList;
import java.util.List;

import org.gnubridge.core.Card;
import org.gnubridge.core.Game;
import org.gnubridge.core.Player;
import org.gnubridge.core.Trick;

public class Node {
	public static final byte UNITNITIALIZED = -1;

	public static final byte PRUNE_ALPHA = 0;

	public static final byte PRUNE_BETA = PRUNE_ALPHA + 1;

	private static final byte ALPHA_UNINIT = -1;

	static final byte BETA_UNINIT = 14;

	public static final byte PRUNE_SEQUENCE_SIBLINGS = PRUNE_BETA + 1;

	public static final byte PRUNE_SEQUENCE_SIBLINGS_PLAYED = PRUNE_SEQUENCE_SIBLINGS + 1;

	private static final byte PRUNE_DUPLICATE_POSITION = PRUNE_SEQUENCE_SIBLINGS_PLAYED + 1;

	public static final byte PRUNE_LOWEST_CARD_IN_LOST_TRICK = PRUNE_DUPLICATE_POSITION + 1;

	int value;

	Node parent;

	List<Node> children;

	private byte playerTurn;

	private byte[] tricksTaken = new byte[2];

	private Card cardPlayed;

	boolean trimmed = false;

	private boolean isLeaf = false;

	private boolean pruned = false;

	private byte pruneType;

	private Player playerCardPlayed;

	private boolean valueSet = false;

	private Game position;

	private byte[] identicalTwin;

	public Node(Node parent) {
		this.parent = parent;
		children = new ArrayList<Node>();
		if (parent != null) {
			parent.children.add(this);
		}
		tricksTaken[Player.WEST_EAST] = UNITNITIALIZED;
		tricksTaken[Player.NORTH_SOUTH] = UNITNITIALIZED;
	}

	public Node(Node parent, int playerTurn) {
		this(parent);
		setPlayerTurn(playerTurn);
	}

	public List<Integer> getMoves() {
		if (parent == null) {
			return new ArrayList<Integer>();
		} else {
			List<Integer> result = parent.getMoves();
			result.add(new Integer(parent.getMyIndex(this)));
			return result;
		}
	}

	private int getMyIndex(Node node) {
		return children.indexOf(node);
	}

	public void setPlayerTurn(int direction) {
		this.playerTurn = (byte) direction;

	}

	public void setTricksTaken(int pair, int i) {
		valueSet = true;
		tricksTaken[pair] = (byte) i;
	}

	public boolean isLastVisitedChild(Node child) {
		boolean hasThisChild = false;
		for (Node sibling : children) {
			if (sibling == null) {
				continue;
			}
			if (sibling == child) {
				hasThisChild = true;
			} else {
				if (!sibling.isLeaf() && !sibling.trimmed()
						&& !sibling.isPruned()) {
					return false;
				}
			}
		}
		return hasThisChild;
	}

	private boolean isLeaf() {
		return isLeaf;
	}

	public boolean trimmed() {
		return trimmed;
	}

	public int getCurrentPair() {
		return Player.matchPair(getPlayerTurn());
	}

	int getPlayerTurn() {
		return this.playerTurn;
	}

	public int getTricksTaken(int pair) {
		return tricksTaken[pair];
	}

	public byte[] getTricksTaken() {
		return tricksTaken;
	}

	public void setCardPlayed(Card card) {
		this.cardPlayed = card;

	}

	public Card getCardPlayed() {
		return this.cardPlayed;
	}

	public Node getBestMove() {
		List<Node> childrenWithSameTricksTaken = new ArrayList<Node>();
		for (Node move : children) {
			if (move != null && !move.isPruned()) {
				childrenWithSameTricksTaken.add(move);
			}
		}
		return getNodeWithLowestValueCard(childrenWithSameTricksTaken);
	}

	private Node getNodeWithLowestValueCard(
			List<Node> nodes) {
		Node lowest = nodes.get(0);
		for (Node node : nodes) {
			if (node.cardPlayed.getValue() < lowest.cardPlayed.getValue()) {
				lowest = node;
			}
		}
		return lowest;
	}

	public void printOptimalPath(Game g) {
		Node move = getBestMove();
		if (move == null) {
			for (int moveIdx : getMoves()) {
				Trick currentTrick = g.getCurrentTrick();
				System.out.println(g.getNextToPlay()
						+ ": "
						+ g.getNextToPlay().getPossibleMoves(currentTrick).get(
								moveIdx));
				g.doNextCard(moveIdx);
				if (currentTrick.isDone()) {
					System.out.println("  Trick taken by "
							+ g.getPlayer(g.getWinnerIndex(currentTrick)));
				}
			}
		} else {
			move.printOptimalPath(g);
		}

	}

	public void printLeafs() {
		if (isLeaf()) {
			System.out.println("*********\nNode: " + getMoves());
			System.out.println(printMoves());
		} else {
			for (Node child : children) {
				if (child != null) {
					child.printLeafs();
				}
			}
		}
	}

	public String printMoves() {
		if (isRoot()) {
			return "";
		} else {
			return parent.printMoves() + getPlayerCardPlayed() + ": "
					+ getCardPlayed() + (isPruned() ? " (pruned)" : "") + "\n";
		}
	}

	private boolean isRoot() {
		return parent == null;
	}

	public void setLeaf(boolean b) {
		isLeaf = b;

	}

	public void setPruned(boolean b, byte type) {
		this.pruned = b;
		this.pruneType = type;
	}

	public boolean isPruned() {
		if (parent == null) {
			return pruned;
		} else if (pruned) {
			return true;
		} else {
			return parent.isPruned();
		}
	}

	public boolean isAlpha() {
		return getMaxPlayer() == getCurrentPair();
	}

	Node getRoot() {

		if (parent == null) {
			return this;
		} else {
			return parent.getRoot();
		}
	}

	public boolean isAlphaPruned() {
		return isPruned() && (getPruneType() == PRUNE_ALPHA);
	}

	public boolean isBetaPruned() {
		return isPruned() && (getPruneType() == PRUNE_BETA);
	}

	public int getPruneType() {
		if (parent == null) {
			return pruneType;
		} else if (pruned) {
			return pruneType;
		} else {
			return parent.getPruneType();
		}
	}

	public boolean hasAlphaAncestor() {
		if (parent == null) {
			return false;
		} else if (parent.isAlpha()) {
			return true;
		} else {
			return parent.hasAlphaAncestor();
		}
	}

	public boolean hasBetaAncestor() {
		if (parent == null) {
			return false;
		} else if (parent.isBeta()) {
			return true;
		} else {
			return parent.hasBetaAncestor();
		}
	}

	boolean isBeta() {
		return !isAlpha();
	}

	public void betaPrune() {
		if (parent != null && !parent.isBeta()) {
			parent.setTricksTaken(Player.WEST_EAST,
					getTricksTaken(Player.WEST_EAST));
			parent.setTricksTaken(Player.NORTH_SOUTH,
					getTricksTaken(Player.NORTH_SOUTH));
			parent.setPruned(true, Node.PRUNE_BETA);
			parent.betaPrune();
		}

	}
	public void alphaPrune() {
		
		if (parent != null && !parent.isAlpha()) {
			parent.setTricksTaken(Player.WEST_EAST,
					getTricksTaken(Player.WEST_EAST));
			parent.setTricksTaken(Player.NORTH_SOUTH,
					getTricksTaken(Player.NORTH_SOUTH));
			parent.setPruned(true, Node.PRUNE_ALPHA);
			parent.alphaPrune();
		}
		
	}

	public void setPlayerCardPlayed(Player player) {
		playerCardPlayed = player;

	}

	public Player getPlayerCardPlayed() {
		return playerCardPlayed;
	}

	public boolean hasAncestor(Node ancestor) {
		if (this == ancestor) {
			return true;
		} else if (parent == null) {
			return false;
		} else {
			return parent.hasAncestor(ancestor);
		}
	}


	public int getLocalAlpha() {
		if (isAlpha()) {
			int max = ALPHA_UNINIT;
			for (Node child : children) {
				if (child.getTricksTaken(getMaxPlayer()) > max) {
					max = child.getTricksTaken(getMaxPlayer());
				}
			}
			return max;
		} else {
			return parent.getLocalAlpha();
		}
	}

	public int getLocalBeta() {
		if (isBeta()) {
			int min = BETA_UNINIT;
			for (Node child : children) {
				if (child.getTricksTaken(getMaxPlayer()) != -1
						&& child.getTricksTaken(getMaxPlayer()) < min) {
					min = child.getTricksTaken(getMaxPlayer());
				}
			}
			return min;
		} else {
			return parent.getLocalBeta();
		}
	}

	private int getMaxPlayer() {
		return getRoot().getCurrentPair();
	}

	public boolean shouldBeAlphaPruned() {
		return valueSet && parent != null && parent.parent != null
				&& hasAlphaAncestor() && !parent.isAlpha()
				&& (getTricksTaken(getMaxPlayer()) <= parent.getLocalAlpha());
	}

	public boolean shouldBeBetaPruned() {
		return valueSet && parent != null && parent.parent != null
				&& hasBetaAncestor() && !parent.isBeta()
				&& (getTricksTaken(getMaxPlayer()) >= parent.getLocalBeta());

	}

	@Override
	public String toString() {
		return "Node " + getMoves().toString();
	}

	private List<Node> siblings() {
		List<Node> result = new ArrayList<Node>();
		if (parent != null) {
			for (Node node : parent.children) {
				if (!node.equals(this)) {
					result.add(node);
				}
			}
		}
		return result;
	}

	public boolean isSequencePruned() {
		return isPruned() && (getPruneType() == PRUNE_SEQUENCE_SIBLINGS);
	}

	public List<Card> getSiblingsInColor() {
		List<Card> cardsInSuit = new ArrayList<Card>();
		for (Node sibling : siblings()) {
			if (sibling.getCardPlayed().hasSameColorAs(getCardPlayed())) {
				cardsInSuit.add(sibling.getCardPlayed());
			}
		}
		return cardsInSuit;
	}

	public boolean isPlayedSequencePruned() {
		return isPruned() && (getPruneType() == PRUNE_SEQUENCE_SIBLINGS_PLAYED);
	}

	public void pruneAsDuplicatePosition() {
		// setPruned(true, Node.PRUNE_DUPLICATE_POSITION);
		setPruned(false, Node.PRUNE_DUPLICATE_POSITION);

	}

	public boolean isPrunedDuplicatePosition() {
		// return isPruned() && (getPruneType() == PRUNE_DUPLICATE_POSITION);
		// return (pruneType == PRUNE_DUPLICATE_POSITION);
		return hasIdenticalTwin();
	}

	public String toDebugString() {
		String result = "";
		result += "Node: " + parent.getMyIndex(this) + ", " + cardPlayed + "\n";
		result += "pruned? " + isPruned() + "\n";
		result += "   alpha/beta: " + isAlphaPruned() + "/" + isBetaPruned()
				+ "\n";
		result += "   sequence/played sequence: " + isSequencePruned() + "/"
				+ isPlayedSequencePruned() + "\n";

		return result;
	}

	public void nullAllChildrenExceptOne() {
		Node exception = getUnprunedChildWithMostTricksForCurrentPair();
		for (int i = 0; i < children.size(); i++) {
			if (exception == null || !exception.equals(children.get(i))) {
				children.get(i).trimmed = true;
				children.set(i, null);
			}
		}

	}

	Node getUnprunedChildWithMostTricksForCurrentPair() {
		Node maxChild = null;
		for (Node child : children) {
			if (child != null
					&& !child.isPruned()
					&& (maxChild == null || child
							.getTricksTaken(getCurrentPair()) > maxChild
							.getTricksTaken(getCurrentPair()))) {
				maxChild = child;
			}
		}
		return maxChild;
	}

	public void calculateValueFromChild() {
		Node maxChild = getUnprunedChildWithMostTricksForCurrentPair();
		if (maxChild != null) {
			setTricksTaken(Player.WEST_EAST, maxChild
					.getTricksTaken(Player.WEST_EAST));
			setTricksTaken(Player.NORTH_SOUTH, maxChild
					.getTricksTaken(Player.NORTH_SOUTH));

		}

	}

	public void calculateValueFromPosition() {
		setTricksTaken(Player.WEST_EAST, position
				.getTricksTaken(Player.WEST_EAST));
		setTricksTaken(Player.NORTH_SOUTH, position
				.getTricksTaken(Player.NORTH_SOUTH));

	}

	public void setPosition(Game position) {
		this.position = position;

	}

	public void calculateValue() {
		if (isLeaf()) {
			if (hasIdenticalTwin()) {
				calculateValueFromIdenticalTwin();
			} else {
				calculateValueFromPosition();
			}
		} else {
			calculateValueFromChild();
		}

	}

	private void calculateValueFromIdenticalTwin() {
		setTricksTaken(Player.NORTH_SOUTH, identicalTwin[Player.NORTH_SOUTH]);
		setTricksTaken(Player.WEST_EAST, identicalTwin[Player.WEST_EAST]);

	}

	boolean hasIdenticalTwin() {
		return identicalTwin != null;
	}

	public boolean canTrim() {
		return parent != null && (parent.isLastVisitedChild(this));
	}

	public void setIdenticalTwin(byte[] node) {
		identicalTwin = node;

	}

	public Node getSiblingNodeForCard(Card card) {
		for (Node sibling : siblings()) {
			if (sibling.getCardPlayed().equals(card)) {
				return sibling;
			}
		}
		throw new RuntimeException("Cannot find appropriate sibling node");
	}

	public boolean isPrunedLowestCardInLostTrick() {
		return isPruned()
				&& (getPruneType() == PRUNE_LOWEST_CARD_IN_LOST_TRICK);
	}

	public int getUnprunedChildCount() {
		int unprunedChildCount = 0;
		for (Node child : children) {
			if (!child.isPruned()) {
				unprunedChildCount++;
			}
		}
		return unprunedChildCount;
	}

	public void nullAllSubstandardChildren() {
       Node best = getUnprunedChildWithMostTricksForCurrentPair();
       for (Node child : children) {
    	   if (child.isPruned() ||
    			   child.getTricksTaken(getCurrentPair()) < best.getTricksTaken(getCurrentPair())) {
    		   children.set(children.indexOf(child), null);
    		   child.trimmed = true;
    		   
    	   }
       }
	}

}
