package org.gnubridge.core.bidding;

import org.gnubridge.core.Hand;
import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Color;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Trump;

public class BiddingAgent {

	private Auctioneer auction;
	private Hand hand;
	private PointCalculator pc;

	public BiddingAgent(Auctioneer a, Hand h) {
		auction = a;
		hand = h;
		pc = new PointCalculator(hand);
	}

	public Bid getBid() {
		Bid selectedBid = new Pass();
		if (pc.getHighCardPoints() >= 16 && pc.getHighCardPoints() <= 18
				&& pc.isBalanced()) {
			selectedBid = new Bid(1, NoTrump.i());
		} else if (pc.getCombinedPoints() >= 13) {
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
				selectedBid = new Bid(1, highest);
			} else {
				selectedBid = new Bid(1, getStrongerMinor());
			}
		}
		if (selectedBid.greaterThan(auction.getHighBid())) {
			return selectedBid;
		} else {
			return new Pass();
		}
	}

	private Trump getStrongerMinor() {
		Trump result = null;
		if (hand.getColorLength(Clubs.i()) > hand.getColorLength(Diamonds.i())) {
			result = Clubs.i();
		} else if (hand.getColorLength(Clubs.i()) == 3
				&& hand.getColorLength(Diamonds.i()) == 3) {
			if (pc.getHighCardPoints(hand.getColorHi2Low(Clubs.i())) > pc
					.getHighCardPoints(hand.getColorHi2Low(Diamonds.i()))) {
				result =  Clubs.i();
			} else {
				result =  Diamonds.i();
			}
		} else {
			result =  Diamonds.i();
		}
		return result;
	}

}
