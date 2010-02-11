package org.gnubridge.core.bidding;

import static org.gnubridge.core.Direction.*;
import static org.gnubridge.core.bidding.Bid.*;
import junit.framework.TestCase;

import org.gnubridge.core.Hand;

public class BiddingAgentBehaviorTest extends TestCase {
	Auctioneer auctioneer;
	BiddingAgent agent;

	private void expectPlayerToBid(Bid bid) {
		assertEquals(bid, agent.getBid());

	}

	private void andPlayersCards(String... cardsInSuits) {
		agent = new BiddingAgent(auctioneer, new Hand(cardsInSuits));

	}

	private void givenBidding(Bid... bids) {
		auctioneer = new Auctioneer(WEST);
		for (Bid bid : bids) {
			auctioneer.bid(bid);
		}

	}

	public void testQuiz4Question4() {
		givenBidding(ONE_HEARTS, PASS);
		andPlayersCards("K,10,7,6", "A,9,8,3", "A,8,6,4,2", "");
		expectPlayerToBid(THREE_HEARTS);
	}

}
