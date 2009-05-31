package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.Call;
import org.gnubridge.core.bidding.PointCalculator;
import org.gnubridge.core.bidding.ResponseCalculator;
import org.gnubridge.core.deck.Color;
import org.gnubridge.core.deck.NoTrump;

public class Respond1Color extends BiddingRule {

	private PointCalculator pc;

	public Respond1Color(Auctioneer a, Hand h) {
		super(a, h);
	}

	@Override
	protected Bid prepareBid() {
		if (!partnerBid1Color()) {
			return null;
		}
		Bid result = null;
		pc = new ResponseCalculator(hand, auction.getPartnersLastCall().getBid());
		if (pc.getCombinedPoints() >= 6) {
			Color highestOver4 = findHighestColorWithFourOrMoreCards();

			if (highestOver4 != null) {
				if (pc.getCombinedPoints() >= 17
						&& hand.getColorLength(highestOver4) >= 5) {
					int jump = auction.getPartnersLastCall().getBid()
							.getValue() + 1;
					result = new Bid(jump, highestOver4);
				} else {
					result = new Bid(1, highestOver4);
					if (!result.greaterThan(auction.getPartnersLastCall()
							.getBid())
							&& pc.getCombinedPoints() >= 11) {
						result = new Bid(2, highestOver4);
					}
				}
			}
		}
		return result;
	}

	private boolean partnerBid1Color() {
		Call partners = auction.getPartnersLastCall();
		if (partners != null
				&& !NoTrump.i().equals(partners.getBid().getTrump())
				&& 1 == partners.getBid().getValue()) {
			return true;
		} else {
			return false;
		}
	}

	private Color findHighestColorWithFourOrMoreCards() {
		Color highestOver4 = null;
		for (Color color : Color.reverseList) {
			if (hand.getColorLength(color) >= 4
					&& strongerColorHasAtLeastAsMuchHighest(color, highestOver4)) {
				highestOver4 = color;
			}
		}
		return highestOver4;
	}

	private boolean strongerColorHasAtLeastAsMuchHighest(Color color,
			Color highestOver4) {
		return (highestOver4 == null || hand.getColorLength(color) >= hand
				.getColorLength(highestOver4));
	}

}
