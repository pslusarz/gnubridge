package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.ResponseCalculator;

public class Respond1ColorRaiseMinorSuit extends BiddingRule {

	private Bid partnersBid;
	private ResponseCalculator calculator;

	public Respond1ColorRaiseMinorSuit(Auctioneer a, Hand h) {
		super(a, h);
	}

	@Override
	protected boolean applies() {
		boolean result = false;
		if (auction.getPartnersLastCall() != null) {
			partnersBid = auction.getPartnersLastCall().getBid();
			if (!partnersBid.isPass()) {
				calculator = new ResponseCalculator(hand, partnersBid);
				if (partnersBid.getTrump().isMinorSuit() && partnersBid.getValue() == 1
						&& calculator.getCombinedPoints() >= 6
						&& hand.getColorLength(partnersBid.getTrump().asColor()) >= 4) {
					result = true;
				}
			}
		}
		return result;
	}

	@Override
	protected Bid prepareBid() {
		if (calculator.getCombinedPoints() >= 6 && calculator.getCombinedPoints() <= 10) {
			return new Bid(2, partnersBid.getTrump());
		} else if (calculator.getCombinedPoints() >= 13 && calculator.getCombinedPoints() <= 16) {
			return new Bid(3, partnersBid.getTrump());
		} else {
			return null;
		}
	}

}
