package org.gnubridge.model.search;

import org.gnubridge.core.Deal;

public class RootNode extends AbstractNode {

	public RootNode(Deal deal) {
		super(deal, null, new NodeNetwork());
		network.setRoot(this);
		isMax = true;
	}

	@Override
	public void updateTree() {

	}

	@Override
	protected Deal expandParentDeal() {
		// TODO Auto-generated method stub
		return null;
	}
}
