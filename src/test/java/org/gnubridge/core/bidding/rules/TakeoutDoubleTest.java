package org.gnubridge.core.bidding.rules;

import static org.gnubridge.core.Direction.*;
import static org.gnubridge.core.bidding.Bid.*;
import junit.framework.TestCase;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;

public class TakeoutDoubleTest extends TestCase {
	private Auctioneer auctioneer;
	private TakeoutDouble rule;

	private void givenNoPriorBids() {
		auctioneer = new Auctioneer(WEST);

	}

	private void givenBidding(Bid... bids) {
		givenNoPriorBids();
		for (Bid bid : bids) {
			auctioneer.bid(bid);
		}

	}

	private void ruleShouldBid(Bid bid) {
		assertEquals(bid, rule.getBid());

	}

	private void andPlayersCards(String... cardsBySuits) {
		rule = new TakeoutDouble(auctioneer, new Hand(cardsBySuits));

	}

	//TODO: work here next
	public void testTakeout() {
		givenBidding(ONE_CLUBS);
		andPlayersCards("A,K,2", "A,Q,3", "8,6,5,3", "K,J,3");
		ruleShouldBid(null);
	}

}
