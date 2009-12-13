package org.gnubridge.core.bidding.rules;

import static org.gnubridge.core.bidding.Bid.ONE_DIAMONDS;

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
		// TODO Auto-generated method stub
		return ONE_DIAMONDS;
	}

}
