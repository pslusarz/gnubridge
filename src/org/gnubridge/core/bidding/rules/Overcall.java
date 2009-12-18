package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;

public class Overcall extends BiddingRule {

	public Overcall(Auctioneer a, Hand h) {
		super(a, h);
	}

	@Override
	protected boolean applies() {
		return this.auction.mayOvercall();
	}

	@Override
	protected Bid prepareBid() {
		if (hand.getLongestColorLength() >= 6) {
			return new Bid(1, hand.getLongestSuit());
		} else {
			return null;
		}
	}

}
