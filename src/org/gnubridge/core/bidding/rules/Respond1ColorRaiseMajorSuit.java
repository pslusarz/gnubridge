package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Trump;

public class Respond1ColorRaiseMajorSuit extends BiddingRule {

	private Bid partnersBid;

	public Respond1ColorRaiseMajorSuit(Auctioneer a, Hand h) {
		super(a, h);
	}

	@Override
	protected boolean applies() {
		boolean result = false;
		if (auction.getPartnersLastCall() != null) {
			partnersBid = auction.getPartnersLastCall().getBid();
			
			if (partnersBid.getTrump().isMajorSuit() && partnersBid.getValue() == 1) {
				result = true;
			}
		}
		return result;
	}
	
	@Override
	protected Bid prepareBid() {
		return new Bid(7, NoTrump.i());
	}

}
