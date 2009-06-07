package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;

public abstract class BiddingRule {
	protected Auctioneer auction;
	protected Hand hand;

	public BiddingRule(Auctioneer a, Hand h) {
		auction = a;
		hand = h;
	}
	
	protected abstract Bid prepareBid();
	
	public Bid getBid() {
		if (!applies()) {
			return null;
		}
		Bid candidate = prepareBid();
		if (auction == null || auction.isValid(candidate)) {
			return candidate;
		} else {
			return null;
		}
	}

	protected boolean applies() {
		return true;
	}
	
}
