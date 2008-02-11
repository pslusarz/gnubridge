package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.Pass;

public abstract class BiddingRule {
	protected Auctioneer auction;
	protected Hand hand;

	public BiddingRule(Auctioneer a, Hand h) {
		auction = a;
		hand = h;
	}
	
	protected abstract Bid prepareBid();
	
	public Bid getBid() {
		Bid candidate = prepareBid();
		if (candidate != null) {
			if (!candidate.equals(new Pass()) && !candidate.greaterThan(auction.getHighBid())) {
				candidate = null;
			}
		}
		return candidate;
	}
	
}
