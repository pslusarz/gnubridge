package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.PointCalculator;
import org.gnubridge.core.deck.NoTrump;


public class Open1NT extends BiddingRule {

	private PointCalculator pc;

	public Open1NT(Auctioneer a, Hand h) {
		super(a, h);
		pc = new PointCalculator(hand);
	}

	@Override
	protected Bid prepareBid() {
		if (pc.getHighCardPoints() >= 16 && pc.getHighCardPoints() <= 18 && pc.isBalanced()) {
			return new Bid(1, NoTrump.i());
		} else {
			return null;
		}
	}

}
