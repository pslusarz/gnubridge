package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.ResponseCalculator;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Suit;

public class Respond1ColorWithNewSuit extends Response {

	private ResponseCalculator pc;
	private Suit highestOver3;

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
		if (pc.getCombinedPoints() >= 17 && hand.getSuitLength(highestOver3) >= 5) {
			result = new Bid(jumpPartnersBid(), highestOver3);
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

	private int jumpPartnersBid() {
		if (partnersOpeningBid.greaterThan(new Bid(partnersOpeningBid.getValue(), highestOver3))) {
			return partnersOpeningBid.getValue() + 2;
		} else {
			return partnersOpeningBid.getValue() + 1;
		}
	}

	private boolean partnerBid1Color() {
		if (!NoTrump.i().equals(partnersOpeningBid.getTrump()) && 1 == partnersOpeningBid.getValue()) {
			return true;
		} else {
			return false;
		}
	}

	private Suit findHighestColorWithFourOrMoreCards() {
		Suit highestOver4 = null;
		for (Suit color : Suit.reverseList) {
			if (hand.getSuitLength(color) >= 4 && strongerColorHasAtLeastAsMuchHighest(color, highestOver4)
					&& !color.equals(partnersOpeningBid.getTrump())) {
				highestOver4 = color;
			}
		}
		return highestOver4;
	}

	private boolean strongerColorHasAtLeastAsMuchHighest(Suit color, Suit highestOver4) {
		return (highestOver4 == null || hand.getSuitLength(color) >= hand.getSuitLength(highestOver4));
	}

}
