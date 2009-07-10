package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.ResponseCalculator;

public class Rebid1ColorRaisePartner extends Rebid {

	public Rebid1ColorRaisePartner(Auctioneer a, Hand h) {
		super(a, h);
	}

	@Override
	protected boolean applies() {
		return super.applies() && response.getTrump().isSuit() && getTrumpCount() >= 4;
	}

	@Override
	protected Bid prepareBid() {
		ResponseCalculator calc = new ResponseCalculator(hand, response);
		if (calc.getCombinedPoints() >= 19) {
			return new Bid(4, response.getTrump());
		} else if (calc.getCombinedPoints() >= 16) {
			return new Bid(3, response.getTrump());
		} else {
			return new Bid(2, response.getTrump());
		}
	}

	private int getTrumpCount() {
		return hand.getColorLength(response.getTrump().asSuit());
	}

}
