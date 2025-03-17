package org.gnubridge.core.bidding.rules;

import static org.gnubridge.core.Direction.*;
import static org.gnubridge.core.bidding.Bid.*;
import junit.framework.TestCase;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;

public class TakeoutDoubleTest extends AbstractBiddingRuleTest<TakeoutDouble> {

	public void testTakeout() {
		givenBidding(ONE_CLUBS);
		andPlayersCards("A,K,2", "A,Q,3", "8,6,5,3", "K,J,3");
		ruleShouldBid(DOUBLE);
	}

	public void testTakeoutNotEnoughCardsInUnbuidSuit() {
		givenBidding(ONE_CLUBS);
		andPlayersCards("A,K,2", "A,Q,3", "8,6", "K,J,5,3,2");
		ruleShouldBid(null);
	}

	public void testTakeoutEnoughCardsInUnbuidSuit() {
		givenBidding(ONE_CLUBS);
		andPlayersCards("A,K,2", "A,Q,3", "8,6,5,3,2", "K,J");
		ruleShouldBid(DOUBLE);
	}

	public void testTakeoutNotEnoughPoints() {
		givenBidding(ONE_CLUBS);
		andPlayersCards("10,8,2", "A,Q,3", "8,6,5,3,2", "K,J");
		ruleShouldBid(null);
	}

	public void testTakeoutNotEnoughCardsInUnbuidSuitBut19Points() {
		givenBidding(ONE_CLUBS);
		andPlayersCards("A,K,2", "A,Q,3", "8,6", "A,K,J,5,3");
		ruleShouldBid(DOUBLE);
	}

}
