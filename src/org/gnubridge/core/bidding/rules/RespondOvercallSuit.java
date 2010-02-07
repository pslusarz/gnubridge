package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.PointCalculator;
import org.gnubridge.core.bidding.ResponseCalculator;

public class RespondOvercallSuit extends Response {

	public RespondOvercallSuit(Auctioneer a, Hand h) {
		super(a, h);
	}

	@Override
	protected boolean applies() {
		return super.applies() && auction.isOvercall(partnersOpeningBid) && partnersOpeningBid.getTrump().isSuit();
	}

	@Override
	protected Bid prepareBid() {
		PointCalculator calculator = new ResponseCalculator(hand, partnersOpeningBid);
		if (calculator.getCombinedPoints() >= 8 && hand.getSuitLength(partnersOpeningBid.getTrump().asSuit()) >= 3) {
			return new Bid(2, partnersOpeningBid.getTrump());
		}
		return null;
	}

}
