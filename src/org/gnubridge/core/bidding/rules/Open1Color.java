package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.PointCalculator;
import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Color;
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

		Color highest = null;
		for (Color color : Color.list) {
			if (hand.getColorLength(color) >= 5) {
				if (highest == null) {
					highest = color;
				} else if (hand.getColorLength(color) > hand
						.getColorLength(highest)) {
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
		if (hand.getColorLength(Clubs.i()) > hand.getColorLength(Diamonds.i())) {
			result = Clubs.i();
		} else if (hand.getColorLength(Clubs.i()) == 3
				&& hand.getColorLength(Diamonds.i()) == 3) {
			if (pc.getHighCardPoints(hand.getColorHi2Low(Clubs.i())) > pc
					.getHighCardPoints(hand.getColorHi2Low(Diamonds.i()))) {
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
