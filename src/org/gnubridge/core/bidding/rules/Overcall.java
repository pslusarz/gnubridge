package org.gnubridge.core.bidding.rules;

import java.util.List;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.deck.Suit;

public class Overcall extends BiddingRule {

	public Overcall(Auctioneer a, Hand h) {
		super(a, h);
	}

	@Override
	protected boolean applies() {
		return this.auction.mayOvercall();
	}

	@Override
	protected Bid prepareBid() {
		List<Suit> goodSuits5AndLonger = hand.getSuitsWithAtLeastCards(6);
		//goodSuits5AndLonger.addAll(hand.getDecent5LengthSuits());
		if (hand.getLongestColorLength() >= 6) {
			return new Bid(1, hand.getLongestSuit());
		} else {
			return null;
		}
	}

}
