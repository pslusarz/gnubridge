package org.gnubridge.model.search;

import java.util.ArrayList;
import java.util.Collection;

import org.gnubridge.core.Card;
import org.gnubridge.core.Deal;

public abstract class AbstractNode {

	protected Deal deal;
	protected boolean isMax;
	protected final NodeNetwork network;
	protected ArrayList<ChildNode> children;
	protected final Card cardToPlay;
	protected final Deal parentsDeal;
	private int value;

	public AbstractNode(Deal parentsDeal, Card cardToPlay, NodeNetwork network) {
		this.parentsDeal = parentsDeal;
		this.cardToPlay = cardToPlay;
		this.network = network;
		this.children = new ArrayList<ChildNode>();
		this.value = -1;
	}

	public Collection<ChildNode> getChildren() {
		return children;
	}

	protected void populateChildren() {
		for (Card card : deal.getPossibleMoves()) {
			ChildNode child = new ChildNode(deal, card, this, network);
			children.add(child);
		}
	}

	//template? 
	//initializeDealFromParent
	//populateChildren
	//
	public void expand() {
		deal = expandParentDeal();
		if (deal.isDone()) {
			setValueFromDeal();
		} else {
			populateChildren();
		}
	}

	protected void setValueFromDeal() {
		value = deal.getDeclarerTricksTaken();

	}

	protected abstract Deal expandParentDeal();

	public abstract void updateTree();
}
