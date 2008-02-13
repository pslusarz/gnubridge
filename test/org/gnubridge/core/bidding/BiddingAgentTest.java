package org.gnubridge.core.bidding;

import junit.framework.TestCase;

import org.gnubridge.core.Hand;
import org.gnubridge.core.West;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.NoTrump;

public class BiddingAgentTest extends TestCase {

	public void testOpeningOneNT() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,2", "A,Q,3",
				"A,8,6,5,3", "K,J,3"));
		assertEquals(new Bid(1, NoTrump.i()), ba.getBid());
	}


	public void testOpenOneColor5ColorSuit() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,2", "A,3",
				"A,8,6,5,3", "5,4,3"));
		assertEquals(new Bid(1, Diamonds.i()), ba.getBid());
	}

	public void testHaveToBidSomething() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, new Hand("5,4,3,2", "5,4,3",
				"6,5,3", "5,4,3"));
		assertEquals(new Pass(), ba.getBid());
	}

	public void testRP2() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, RPQuizzes.Basics.Lesson2.hand2());
		assertEquals(new Pass(), ba.getBid());
	}


}
