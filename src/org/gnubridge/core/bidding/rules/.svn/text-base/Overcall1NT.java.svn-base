package org.gnubridge.core.bidding.rules;

import static org.gnubridge.core.bidding.Bid.*;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.PointCalculator;

public class Overcall1NT extends BiddingRule {

	private final PointCalculator pc;

	public Overcall1NT(Auctioneer a, Hand h) {
		super(a, h);
		pc = new PointCalculator(hand);
	}

	@Override
	protected boolean applies() {
		return auction.mayOvercall() && pc.getHighCardPoints() >= 16 && pc.getHighCardPoints() <= 18 && pc.isBalanced()
				&& haveStopperInEnemySuit();
	}

	@Override
	protected Bid prepareBid() {
		return ONE_NOTRUMP;
	}

}
