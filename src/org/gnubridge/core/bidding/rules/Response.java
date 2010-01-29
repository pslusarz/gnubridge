package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.Call;

public abstract class Response extends BiddingRule {

	protected Bid partnersOpeningBid;

	public Response(Auctioneer a, Hand h) {
		super(a, h);
	}

	@Override
	protected boolean applies() {
		Call partnersCall = auction.getPartnersLastCall();
		if (partnersCall != null && partnersCall.getBid().hasTrump()) {
			partnersOpeningBid = partnersCall.getBid();
			Call myOpeningCall = auction.getPartnersCall(partnersCall);
			if (myOpeningCall == null || myOpeningCall.isPass()) {
				return true;
			}
		}
		return false;
	}

	@Override
	abstract protected Bid prepareBid();

}
