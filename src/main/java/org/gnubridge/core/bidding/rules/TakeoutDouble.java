package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.PointCalculator;
import org.gnubridge.core.deck.*;

import java.util.*;

import static org.gnubridge.core.bidding.Bid.DOUBLE;

public class TakeoutDouble extends BiddingRule {

	private final PointCalculator calculator;

	public TakeoutDouble(Auctioneer a, Hand h) {
		super(a, h);
		calculator = new PointCalculator(hand);
	}

	@Override
	protected boolean applies() {
		int points = calculator.getCombinedPoints();
		return auction.mayOvercall() && (
				(13 <= points && points <= 18 && haveAtLeastCardsInAllUnbidSuits(3, auction.getEnemyTrumps())) || (points > 19));
	}

	@Override
	protected Bid prepareBid() {
		return DOUBLE;
	}

	private boolean haveAtLeastCardsInAllUnbidSuits(int minimum, Set<Trump> opponentBidSuit) {
		List<Suit> allSuits = new ArrayList(Arrays.asList(Suit.list));

		// Remove suits that opponents have bid
		for (Trump trump : opponentBidSuit) {
			if (trump.isSuit()) {
				allSuits.remove(trump.asSuit());
			}
		}

		// Check if we have at least 'minimum' cards in each unbid suit
		for (Suit suit : allSuits) {
			if (hand.getSuitLength(suit) < minimum) {
				return false;
			}
		}

		return true;

	}

}
