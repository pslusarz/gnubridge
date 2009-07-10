package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.Call;

public abstract class Rebid extends BiddingRule {
	protected Bid response;
	protected Bid opening;

	public Rebid(Auctioneer a, Hand h) {
		super(a, h);
	}

	@Override
	protected boolean applies() {
		if (validOpeningAndResponse()) {
			response = auction.getPartnersLastCall().getBid();
			opening = auction.getPartnersCall(auction.getPartnersLastCall()).getBid();
		}
		return validOpeningAndResponse();
	}

	private boolean validOpeningAndResponse() {
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
	abstract protected Bid prepareBid();

}
