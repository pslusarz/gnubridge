package org.gnubridge.core.bidding;

import junit.framework.TestCase;

import org.gnubridge.core.Hand;
import org.gnubridge.core.West;
import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Spades;

public class BiddingAgentTest extends TestCase {

	public void testOpeningOneNT() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,2", "A,Q,3", "A,8,6,5,3", "K,J,3"));
		assertEquals(new Bid(1, NoTrump.i()), ba.getBid());
	}

	public void testMajorSuit1NTResponse() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		BiddingAgent ba = new BiddingAgent(a, new Hand("9,8,7,6,2", "A,3", "6,5,3", "5,4,3"));
		assertEquals(new Bid(2, Spades.i()), ba.getBid());
	}

	public void testOpenersResponseToMajorSuitResponseTo1NT() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		a.bid(new Bid(3, Hearts.i()));
		a.bid(new Pass());
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,2", "A,Q,3", "A,8,6,5,3", "K,J,3"));
		assertEquals(new Bid(4, Hearts.i()), ba.getBid());
	}

	public void testOpeningOneNTSequence() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent west = new BiddingAgent(a, new Hand("K,2", "A,Q,3", "A,8,6,5,3", "K,J,3"));
		assertEquals(new Bid(1, NoTrump.i()), west.getBid());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		BiddingAgent east = new BiddingAgent(a, new Hand("K,8,7,6", "A,3,2", "6,5,3", "Q,4,3"));
		assertEquals(new Bid(2, NoTrump.i()), east.getBid());
		a.bid(new Bid(2, NoTrump.i()));
		a.bid(new Pass());
		assertEquals(new Bid(3, NoTrump.i()), west.getBid());
	}

	public void testOpenOneColor5ColorSuit() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,2", "A,3", "A,8,6,5,3", "5,4,3"));
		assertEquals(new Bid(1, Diamonds.i()), ba.getBid());
	}

	public void testRespond1ColorWithNewSuit() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,3,2", "K,5,4,3", "9,8,6", "5,4,3"));
		assertEquals(new Bid(1, Hearts.i()), ba.getBid());
	}

	public void testRespond1ColorRaisesMajorSuit() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Spades.i()));
		a.bid(new Pass());
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,3,2", "K,5,4,3", "9,8,6", "5,4,3"));
		assertEquals(new Bid(2, Spades.i()), ba.getBid());
	}

	public void testRespond1ColorRaisesMinorSuit() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,3,2", "5,4,3", "9,8,6", "K,5,4,3"));
		assertEquals(new Bid(2, Clubs.i()), ba.getBid());
	}

	public void testRespond1ColorBidsNT() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Bid(1, Diamonds.i())); //only way to keep form bidding new suit or raising partner was to have opponents bid
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,3,2", "A,J,4", "K,8,6,3", "K,5,4"));
		assertEquals(new Bid(2, NoTrump.i()), ba.getBid());
	}

	public void testHaveToBidSomething() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, new Hand("5,4,3,2", "5,4,3", "6,5,3", "5,4,3"));
		assertEquals(new Pass(), ba.getBid());
	}

	public void testRP2() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, RPQuizzes.Basics.Lesson2.hand2());
		assertEquals(new Pass(), ba.getBid());
	}

}
