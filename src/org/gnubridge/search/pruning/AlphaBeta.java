package org.gnubridge.search.pruning;

import org.gnubridge.search.Node;

public class AlphaBeta implements PruningStrategy {

	@Override
	public void prune(Node node) {
		if (node.shouldBeAlphaPruned()) {
			node.alphaPrune();
		}
		if (node.shouldBeBetaPruned()) {
			node.betaPrune();
		}

	}

}
