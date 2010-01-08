package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.ResponseCalculator;

public class Respond1ColorRaiseMajorSuit extends Response {

	private ResponseCalculator calculator;

	public Respond1ColorRaiseMajorSuit(Auctioneer a, Hand h) {
		super(a, h);
	}

	@Override
	protected boolean applies() {
		boolean result = false;
		if (super.applies()) {
			calculator = new ResponseCalculator(hand, partnersOpeningBid);
			if (partnersOpeningBid.getTrump().isMajorSuit() && partnersOpeningBid.getValue() == 1
					&& calculator.getCombinedPoints() >= 6
					&& hand.getSuitLength(partnersOpeningBid.getTrump().asSuit()) >= 3) {
				result = true;
			}
		}

		return result;
	}

	@Override
	protected Bid prepareBid() {
		if (calculator.getCombinedPoints() >= 6 && calculator.getCombinedPoints() <= 10) {
			return new Bid(2, partnersOpeningBid.getTrump());
		} else if (calculator.getCombinedPoints() >= 13 && calculator.getCombinedPoints() <= 16) {
			return new Bid(3, partnersOpeningBid.getTrump());
		} else {
			return null;
		}
	}

}
