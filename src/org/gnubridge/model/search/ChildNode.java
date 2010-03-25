package org.gnubridge.model.search;

import org.gnubridge.core.Card;
import org.gnubridge.core.Deal;

public class ChildNode extends AbstractNode {

	private final AbstractNode parent;

	public ChildNode(Deal parentsDeal, Card cardToPlay, AbstractNode parent, NodeNetwork network) {
		super(parentsDeal, cardToPlay, network);
		this.parent = parent;
	}

	@Override
	public void updateTree() {
		// TODO Auto-generated method stub

	}

	@Override
	protected Deal expandParentDeal() {
		// TODO Auto-generated method stub
		return null;
	}

}
