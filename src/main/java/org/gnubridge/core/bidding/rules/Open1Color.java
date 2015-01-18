package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.PointCalculator;
import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Suit;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Trump;

public class Open1Color extends BiddingRule {

	private PointCalculator pc;

	public Open1Color(Auctioneer a, Hand h) {
		super(a, h);
		pc = new PointCalculator(hand);
	}

	@Override
	protected boolean applies() {
		return auction.isOpeningBid() && pc.getCombinedPoints() >= 13;
	}

	@Override
	protected Bid prepareBid() {
		Bid result = null;

		Suit highest = null;
		for (Suit color : Suit.list) {
			if (hand.getSuitLength(color) >= 5) {
				if (highest == null) {
					highest = color;
				} else if (hand.getSuitLength(color) > hand
						.getSuitLength(highest)) {
					highest = color;
				}
			}
		}
		if (highest != null) {
			result = new Bid(1, highest);
		} else {
			result = new Bid(1, getStrongerMinor());
		}
		return result;
	}

	private Trump getStrongerMinor() {
		Trump result = null;
		if (hand.getSuitLength(Clubs.i()) > hand.getSuitLength(Diamonds.i())) {
			result = Clubs.i();
		} else if (hand.getSuitLength(Clubs.i()) == 3
				&& hand.getSuitLength(Diamonds.i()) == 3) {
			if (pc.getHighCardPoints(hand.getSuitHi2Low(Clubs.i())) > pc
					.getHighCardPoints(hand.getSuitHi2Low(Diamonds.i()))) {
				result = Clubs.i();
			} else {
				result = Diamonds.i();
			}
		} else {
			result = Diamonds.i();
		}
		return result;
	}
}
