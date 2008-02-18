package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.Pass;
import org.gnubridge.core.bidding.PointCalculator;
import org.gnubridge.core.deck.Color;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Spades;

public class Respond1NTMajorSuit extends BiddingRule {

	private PointCalculator pc;

	public Respond1NTMajorSuit(Auctioneer a, Hand h) {
		super(a, h);
		pc = new PointCalculator(hand);
	}

	@Override
	protected Bid prepareBid() {
		Bid result = null;
		if (auction.getPartnersLastCall() != null && new Bid(1, NoTrump.i()).equals(auction.getPartnersLastCall().getBid())) {
			Color longer = Spades.i();
			if (hand.getColorLength(Spades.i()) < hand.getColorLength(Hearts
					.i())) {
				longer = Hearts.i();
			}
			if (hand.getColorLength(longer) < 5) {
			  if (pc.getHighCardPoints() <=7 ) {
				  result = new Pass();
			  } else if (pc.getHighCardPoints() <=9) {
				  result = new Bid(2, NoTrump.i());
			  } else if (pc.getHighCardPoints() <= 14) {
				  result = new Bid(3, NoTrump.i());
			  }
			} else if (pc.getCombinedPoints() <= 7) {
				if (hand.getColorLength(longer) >= 5) {
					result = new Bid(2, longer);
				}
			} else if (pc.getCombinedPoints() >= 10) {
				if (hand.getColorLength(longer) == 5) {
					result = new Bid(3, longer);
				} else if (hand.getColorLength(longer) >= 6) {
					result = new Bid(4, longer);
				}
			}
		}
		return result;
	}
}
