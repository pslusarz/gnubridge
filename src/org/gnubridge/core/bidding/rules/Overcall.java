package org.gnubridge.core.bidding.rules;

import java.util.Collection;

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
			return firstValidBid( //
					bidSuit(1, hand.getSuitsWithAtLeastCards(6)), //
					bidSuit(1, hand.getDecent5LengthSuits()));
		} else if (calculator.getCombinedPoints() < 16) {
			return firstValidBid( //
					bidSuit(1, hand.getSuitsWithAtLeastCards(5)), //
					bidSuit(2, hand.getSuitsWithAtLeastCards(6)), //
					bidSuit(2, hand.getGood5LengthSuits()));
		} else if (calculator.getCombinedPoints() < 19) {
			return firstValidBid( //
					bidSuit(1, hand.getSuitsWithAtLeastCards(5)), //
					bidSuit(2, hand.getSuitsWithAtLeastCards(5)));
		}
		return result;
	}

	private Bid bidSuit(int bidLevel, Collection<Suit> suits) {
		for (Suit suit : suits) {
			if (auction.isValid(new Bid(bidLevel, suit))) {
				return new Bid(bidLevel, suit);
			}
		}
		return null;
	}

	private Bid firstValidBid(Bid... bids) {
		for (Bid bid : bids) {
			if (bid != null) {
				return bid;
			}
		}
		return null;
	}

}
