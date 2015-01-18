package org.gnubridge.core.bidding.rules;

import java.util.Set;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.deck.Trump;

public abstract class BiddingRule {
	protected Auctioneer auction;
	protected Hand hand;

	public BiddingRule(Auctioneer a, Hand h) {
		auction = a;
		hand = h;
	}

	protected abstract Bid prepareBid();

	public Bid getBid() {
		if (!applies()) {
			return null;
		}
		Bid candidate = prepareBid();
		if (auction == null || auction.isValid(candidate)) {
			return candidate;
		} else {
			return null;
		}
	}

	protected boolean haveStopperInEnemySuit() {
		Set<Trump> enemyTrumps = auction.getEnemyTrumps();
		for (Trump trump : enemyTrumps) {
			if (trump.isNoTrump()) {
				return false;
			}
			if (!hand.haveStopper(trump.asSuit())) {
				return false;
			}

		}
		return true;
	}

	protected abstract boolean applies();

}
