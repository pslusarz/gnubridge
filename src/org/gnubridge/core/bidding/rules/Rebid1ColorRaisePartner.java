package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.Call;
import org.gnubridge.core.bidding.ResponseCalculator;

public class Rebid1ColorRaisePartner extends BiddingRule {

	public Rebid1ColorRaisePartner(Auctioneer a, Hand h) {
		super(a, h);
	}

	@Override
	protected boolean applies() {
		Call responderCall = auction.getPartnersLastCall();
		if (responderCall != null && !responderCall.isPass()) {
			Call myOpeningBid = auction.getPartnersCall(responderCall);
			if (myOpeningBid != null && !myOpeningBid.isPass()) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected Bid prepareBid() {
		ResponseCalculator calc = new ResponseCalculator(hand, auction.getPartnersLastCall().getBid());
		if (calc.getCombinedPoints() >= 19) {
			return new Bid(4, auction.getPartnersLastCall().getTrump());
		} else if (calc.getCombinedPoints() >= 16) {
			return new Bid(3, auction.getPartnersLastCall().getTrump());
		} else {
			return new Bid(2, auction.getPartnersLastCall().getTrump());
		}
	}

}
