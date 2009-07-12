package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.ResponseCalculator;
import org.gnubridge.core.deck.Color;
import org.gnubridge.core.deck.NoTrump;

public class Respond1ColorWithNewSuit extends Response {

	private ResponseCalculator pc;
	private Color highestOver3;

	@Override
	protected boolean applies() {
		boolean result = false;
		if (super.applies() && partnerBid1Color()) {
			pc = new ResponseCalculator(hand, partnersOpeningBid);
			highestOver3 = findHighestColorWithFourOrMoreCards();
			if (pc.getCombinedPoints() >= 6 && highestOver3 != null) {
				result = true;
			}
		}
		return result;
	}

	public Respond1ColorWithNewSuit(Auctioneer a, Hand h) {
		super(a, h);
	}

	@Override
	protected Bid prepareBid() {
		Bid result = null;

		if (pc.getCombinedPoints() >= 17 && hand.getColorLength(highestOver3) >= 5) {
			int jump = partnersOpeningBid.getValue() + 1;
			result = new Bid(jump, highestOver3);
			result.makeGameForcing();
		} else {
			result = new Bid(1, highestOver3);
			if (!result.greaterThan(partnersOpeningBid) && pc.getCombinedPoints() >= 11) {
				result = new Bid(2, highestOver3);
			}
			result.makeForcing();
		}

		return result;
	}

	private boolean partnerBid1Color() {
		if (!NoTrump.i().equals(partnersOpeningBid.getTrump()) && 1 == partnersOpeningBid.getValue()) {
			return true;
		} else {
			return false;
		}
	}

	private Color findHighestColorWithFourOrMoreCards() {
		Color highestOver4 = null;
		for (Color color : Color.reverseList) {
			if (hand.getColorLength(color) >= 4 && strongerColorHasAtLeastAsMuchHighest(color, highestOver4)) {
				highestOver4 = color;
			}
		}
		return highestOver4;
	}

	private boolean strongerColorHasAtLeastAsMuchHighest(Color color, Color highestOver4) {
		return (highestOver4 == null || hand.getColorLength(color) >= hand.getColorLength(highestOver4));
	}

}
