package org.gnubridge.core.bidding;

import static org.gnubridge.core.Direction.*;
import junit.framework.TestCase;

import org.gnubridge.core.Hand;

public abstract class BiddingAgentTestCase extends TestCase {
	Auctioneer auctioneer;
	BiddingAgent agent;

	protected void expectPlayerToBid(Bid bid) {
		assertEquals(bid, agent.getBid());

	}

	protected void andPlayersCards(String... cardsBySuits) {
		agent = new BiddingAgent(auctioneer, new Hand(cardsBySuits));

	}

	protected void givenPlayersCards(String... cardsBySuits) {
		givenNoPriorBids();
		agent = new BiddingAgent(auctioneer, new Hand(cardsBySuits));

	}

	protected void givenNoPriorBids() {
		auctioneer = new Auctioneer(WEST);

	}

	protected void givenBidding(Bid... bids) {
		givenNoPriorBids();
		for (Bid bid : bids) {
			auctioneer.bid(bid);
		}

	}

	protected void andGivenBidding(Bid... bids) {
		for (Bid bid : bids) {
			auctioneer.bid(bid);
		}

	}
}
