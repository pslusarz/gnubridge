package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.Pass;
import org.gnubridge.core.bidding.PointCalculator;
import org.gnubridge.core.deck.Suit;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Spades;

public class Respond1NT extends Response {

	private final PointCalculator pc;

	public Respond1NT(Auctioneer a, Hand h) {
		super(a, h);
		pc = new PointCalculator(hand);
	}

	@Override
	protected Bid prepareBid() {
		Bid result = null;
		Suit longer = Spades.i();
		if (hand.getSuitLength(Spades.i()) < hand.getSuitLength(Hearts.i())) {
			longer = Hearts.i();
		}
		if (hand.getSuitLength(longer) < 5) {
			if (pc.getHighCardPoints() <= 7) {
				result = new Pass();
			} else if (pc.getHighCardPoints() <= 9) {
				result = new Bid(2, NoTrump.i());
			} else if (pc.getHighCardPoints() <= 14) {
				result = new Bid(3, NoTrump.i());
			}
		} else if (pc.getCombinedPoints() <= 7) {
			if (hand.getSuitLength(longer) >= 5) {
				result = new Bid(2, longer);
			}
		} else if (pc.getCombinedPoints() >= 10) {
			if (hand.getSuitLength(longer) == 5) {
				result = new Bid(3, longer);
			} else if (hand.getSuitLength(longer) >= 6) {
				result = new Bid(4, longer);
			}
		}
		return result;
	}

	@Override
	protected boolean applies() {
		return super.applies() && new Bid(1, NoTrump.i()).equals(partnersOpeningBid);
	}
}
