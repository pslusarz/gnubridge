package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.Pass;
import org.gnubridge.core.deck.Color;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Spades;

public class Opener1NTRespondsToPArtnersMajorSuitResponse extends BiddingRule {

	public Opener1NTRespondsToPArtnersMajorSuitResponse(Auctioneer a, Hand h) {
		super(a, h);
	}

	@Override
	protected Bid prepareBid() {
		Bid result = null;
		Bid partnersBid = auction.getPartnersBid();
		if (partnerWasRespondingToMy1NT()) {
			if (Spades.i().equals(partnersBid.getTrump())
					|| Hearts.i().equals(partnersBid.getTrump())) {
				if (partnersBid.getValue() == 2) {
					result = new Pass();
				} else {
					if (hand.getColorLength((Color) partnersBid.getTrump()) >= 3) {
						result = new Bid(4, partnersBid.getTrump());
					} else {
						result = new Bid(3, NoTrump.i());
					}
				}
			}
		}
		return result;
	}

	private boolean partnerWasRespondingToMy1NT() {
		Bid partnersBid = auction.getPartnersBid();
		return new Bid(1, NoTrump.i()).equals(auction.getPartnersBid(partnersBid));
	}

}
