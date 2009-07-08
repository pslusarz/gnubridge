package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.PointCalculator;
import org.gnubridge.core.deck.Color;

public class Rebid1ColorWithNewSuit extends Rebid {

	public Rebid1ColorWithNewSuit(Auctioneer a, Hand h) {
		super(a, h);

	}

	@Override
	protected Bid prepareBid() {
		PointCalculator calculator = new PointCalculator(hand);
		Color unbidSuit = getUnbidSuitWithAtLeast4Cards();
		int minimumBid = getMinimumBidInSuit(unbidSuit);
		if (calculator.getCombinedPoints() >= 19) {
			Bid bid = new Bid(minimumBid + 1, unbidSuit);
			bid.makeGameForcing();
			return bid;
		}
		if ((minimumBid == 2 && !calculator.isBalanced()) || minimumBid == 1) {
			return new Bid(minimumBid, unbidSuit);
		}

		return null;
	}

	private int getMinimumBidInSuit(Color suit) {
		if (auction.isValid(new Bid(1, suit))) {
			return 1;
		} else {
			return 2;
		}
	}

	private Color getUnbidSuitWithAtLeast4Cards() {
		for (Color color : Color.list) {
			if (hand.getColorLength(color) >= 4 && hasNotBeenBid(color)) {
				return color;
			}
		}
		return null;
	}

	private boolean hasNotBeenBid(Color suit) {
		return !suit.equals(response.getTrump()) && !suit.equals(opening.getTrump());
	}

}
