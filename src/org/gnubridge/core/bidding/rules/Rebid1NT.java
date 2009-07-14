package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.Pass;
import org.gnubridge.core.bidding.PointCalculator;
import org.gnubridge.core.deck.Color;
import org.gnubridge.core.deck.NoTrump;

public class Rebid1NT extends Rebid {

	public Rebid1NT(Auctioneer a, Hand h) {
		super(a, h);
	}

	@Override
	protected Bid prepareBid() {
		Bid result = null;

		Bid partnersBid = auction.getPartnersLastCall().getBid();
		if (partnersBid.getTrump().isMajorSuit()) {
			if (partnersBid.getValue() == 2) {
				result = new Pass();
			} else {
				if (hand.getColorLength((Color) partnersBid.getTrump()) >= 3) {
					result = new Bid(4, partnersBid.getTrump());
				} else {
					result = new Bid(3, NoTrump.i());
				}
			}
		} else if (NoTrump.i().equals(partnersBid.getTrump())) {
			if (partnersBid.getValue() == 2) {
				PointCalculator pc = new PointCalculator(hand);
				if (pc.getHighCardPoints() == 16) {
					result = new Pass();
				} else {
					result = new Bid(3, NoTrump.i());
				}
			} else if (partnersBid.getValue() == 3) {
				result = new Pass();
			}
		}

		return result;
	}

	private boolean partnerWasRespondingToMy1NT() {
		return super.applies() && new Bid(1, NoTrump.i()).equals(opening);
	}

	@Override
	protected boolean applies() {
		return partnerWasRespondingToMy1NT();
	}

}
