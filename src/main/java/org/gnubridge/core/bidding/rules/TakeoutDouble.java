package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;

public class TakeoutDouble extends BiddingRule {

	public TakeoutDouble(Auctioneer a, Hand h) {
		super(a, h);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean applies() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Bid prepareBid() {
		// TODO Auto-generated method stub
		return null;
	}

}
