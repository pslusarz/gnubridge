package org.gnubridge.core.bidding.rules;

import java.util.List;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.PointCalculator;
import org.gnubridge.core.deck.Suit;

public class Overcall extends BiddingRule {

	private final PointCalculator calculator;

	public Overcall(Auctioneer a, Hand h) {
		super(a, h);
		calculator = new PointCalculator(hand);
	}

	@Override
	protected boolean applies() {
		return this.auction.mayOvercall() && calculator.getCombinedPoints() >= 10;
	}

	@Override
	protected Bid prepareBid() {
		Bid result = null;
		if (calculator.getCombinedPoints() < 13) {
			result = getValidBidFor6LongSuitOrDecent5(1);
		} else if (calculator.getCombinedPoints() < 16) {
			result = getValidBidForSuit5AndLonger(1);
			if (result == null) {
				result = getValidBidFor6LongSuitOrDecent5(2);
			}
		} else if (calculator.getCombinedPoints() < 19) {
			result = getValidBidForSuit5AndLonger(1);
			if (result == null) {
				result = getValidBidForSuit5AndLonger(2);
			}
		}
		return result;
	}

	private Bid getValidBidForSuit5AndLonger(int maxBidLevelAllowed) {
		List<Suit> suits5AndLonger = hand.getSuitsWithAtLeastCards(5);
		if (suits5AndLonger.size() > 0 && auction.isValid(new Bid(maxBidLevelAllowed, suits5AndLonger.get(0)))) {
			return new Bid(maxBidLevelAllowed, suits5AndLonger.get(0));
		}
		return null;
	}

	private Bid getValidBidFor6LongSuitOrDecent5(int maxBidLevelAllowed) {
		List<Suit> goodSuits5AndLonger = hand.getSuitsWithAtLeastCards(6);
		goodSuits5AndLonger.addAll(hand.getDecent5LengthSuits());
		for (Suit suit : goodSuits5AndLonger) {
			if (auction.isValid(new Bid(maxBidLevelAllowed, suit))) {
				return new Bid(maxBidLevelAllowed, suit);
			}
		}
		return null;
	}

}
