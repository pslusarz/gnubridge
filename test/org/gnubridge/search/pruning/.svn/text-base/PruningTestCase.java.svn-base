package org.gnubridge.search.pruning;

import java.util.Arrays;
import java.util.HashMap;

import junit.framework.TestCase;

import org.gnubridge.core.Direction;
import org.gnubridge.search.Node;

public abstract class PruningTestCase extends TestCase {

	protected NodeWrapper protoRoot;
	protected NodeWrapper root;
	protected HashMap<String, NodeWrapper> nodes;

	protected void whenPruning(NodeWrapper node) {
		AlphaBeta ab = new AlphaBeta();
		ab.prune(node.delegate);

	}

	protected NodeWrapper nodeWithPath(String... moveSelectionsFromRoot) {
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

	protected void givenMax(Direction maxPlayer) {
		protoRoot = new NodeWrapper(null, maxPlayer.getValue());
		root = new NodeWrapper(protoRoot, maxPlayer.getValue());
		nodes = new HashMap<String, NodeWrapper>();

	}

	class NodeWrapper {
		static final int NO_PLAYER_SELECTED = -1;
		Node delegate;
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

		public boolean isPruned() {
			return delegate.isPruned();
		}

		public int getLocalAlpha() {
			return delegate.getLocalAlpha();
		}

		public boolean isBetaPruned() {
			return delegate.isBetaPruned();
		}

		public int getLocalBeta() {
			return delegate.getLocalBeta();
		}

	}

}
