package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.PointCalculator;
import org.gnubridge.core.deck.NoTrump;

public class Respond1ColorWithNT extends Response {

	private PointCalculator calculator;

	public Respond1ColorWithNT(Auctioneer a, Hand h) {
		super(a, h);
	}

	@Override
	protected boolean applies() {
		boolean result = false;
		if (super.applies()) {
			calculator = new PointCalculator(hand);
			if (partnersOpeningBid.getTrump().isSuit() && partnersOpeningBid.getValue() == 1
					&& calculator.getHighCardPoints() >= 6) {
				result = true;
			}
		}
		return result;
	}

	@Override
	protected Bid prepareBid() {
		if (calculator.getHighCardPoints() <= 10) {
			return new Bid(1, NoTrump.i());
		} else if (calculator.getHighCardPoints() >= 13 && calculator.getHighCardPoints() < 17
				&& calculator.isBalanced()) {
			return new Bid(2, NoTrump.i());
		} else if (calculator.getHighCardPoints() >= 17 && calculator.getHighCardPoints() < 19
				&& calculator.isBalanced()) {
			return new Bid(3, NoTrump.i());
		} else {
			return null;
		}
	}

}
