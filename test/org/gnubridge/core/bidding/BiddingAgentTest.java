package org.gnubridge.core.bidding;

import static org.gnubridge.core.bidding.Bid.*;
import junit.framework.TestCase;

import org.gnubridge.core.Hand;
import org.gnubridge.core.West;

public class BiddingAgentTest extends TestCase {

	public void testOpeningOneNT() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,2", "A,Q,3", "A,8,6,5,3", "K,J,3"));
		assertEquals(ONE_NOTRUMP, ba.getBid());
	}

	public void testMajorSuit1NTResponse() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_NOTRUMP);
		a.bid(PASS);
		BiddingAgent ba = new BiddingAgent(a, new Hand("9,8,7,6,2", "A,3", "6,5,3", "5,4,3"));
		assertEquals(TWO_SPADES, ba.getBid());
	}

	public void testOpenersResponseToMajorSuitResponseTo1NT() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_NOTRUMP);
		a.bid(PASS);
		a.bid(THREE_HEARTS);
		a.bid(PASS);
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,2", "A,Q,3", "A,8,6,5,3", "K,J,3"));
		assertEquals(FOUR_HEARTS, ba.getBid());
	}

	public void testOpeningOneNTSequence() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent west = new BiddingAgent(a, new Hand("K,2", "A,Q,3", "A,8,6,5,3", "K,J,3"));
		assertEquals(ONE_NOTRUMP, west.getBid());
		a.bid(ONE_NOTRUMP);
		a.bid(PASS);
		BiddingAgent east = new BiddingAgent(a, new Hand("K,8,7,6", "A,3,2", "6,5,3", "Q,4,3"));
		assertEquals(TWO_NOTRUMP, east.getBid());
		a.bid(TWO_NOTRUMP);
		a.bid(PASS);
		assertEquals(THREE_NOTRUMP, west.getBid());
	}

	public void testOpenOneColor5ColorSuit() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,2", "A,3", "A,8,6,5,3", "5,4,3"));
		assertEquals(ONE_DIAMONDS, ba.getBid());
	}

	public void testRespond1ColorWithNewSuit() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,3", "K,5,4,3,2", "9,8", "5,4,3,2"));
		assertEquals(ONE_HEARTS, ba.getBid());
	}

	public void testRespond1ColorRaisesMajorSuit() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_SPADES);
		a.bid(PASS);
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,3,2", "K,5,4,3", "9,8,6", "5,4,3"));
		assertEquals(TWO_SPADES, ba.getBid());
	}

	public void testRespond1ColorRaisesMinorSuit() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(PASS);
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,3,2", "5,4,3", "9,8,6", "K,5,4,3"));
		assertEquals(TWO_CLUBS, ba.getBid());
	}

	public void testRespond1ColorBidsNT() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_DIAMONDS); //only way to keep form bidding new suit or raising partner was to have opponents bid
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,3,2", "A,J,4", "K,8,6,3", "K,5,4"));
		assertEquals(TWO_NOTRUMP, ba.getBid());
	}

	public void testRebidRespondersColor() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(PASS);
		a.bid(ONE_DIAMONDS);
		a.bid(TWO_CLUBS);
		BiddingAgent ba = new BiddingAgent(a, new Hand("J,8,6", "", "K,7,5,2", "A,K,J,10,9,2"));

		assertEquals(THREE_DIAMONDS, ba.getBid());
	}

	public void testRebidNewSuit() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(PASS);
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);
		BiddingAgent ba = new BiddingAgent(a, new Hand("J,5,4,2", "8,4", "A,K,9", "K,5,4,3"));
		assertEquals("add rebid new suit rule to make this pass", ONE_SPADES, ba.getBid());
	}

	public void testRebidOriginalSuit() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_HEARTS);
		a.bid(PASS);
		a.bid(ONE_SPADES);
		a.bid(PASS);
		BiddingAgent ba = new BiddingAgent(a, new Hand("3,2", "A,K,5,4,3,2", "K,Q,J", "K,8"));
		assertEquals(THREE_HEARTS, ba.getBid());
	}

	public void testRebid1ColorWithNT() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_SPADES);
		a.bid(PASS);
		a.bid(ONE_NOTRUMP);
		a.bid(PASS);
		BiddingAgent ba = new BiddingAgent(a, new Hand("A,Q,4,3", "K,Q,J", "9,3,2", "A,K,5"));
		assertEquals(THREE_NOTRUMP, ba.getBid());
	}

	public void testOvercall1ColorWithOwnColor() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		BiddingAgent ba = new BiddingAgent(a, new Hand("7,8", "4,3", "A,K,J,9,3,2", "Q,5,4"));
		assertEquals(ONE_DIAMONDS, ba.getBid());
	}

	public void testRespondToOvercall() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(TWO_DIAMONDS);
		a.bid(PASS);
		BiddingAgent ba = new BiddingAgent(a, new Hand("10,9,8,7", "K,3,2", "A,J,9", "9,5,4"));
		assertEquals(THREE_DIAMONDS, ba.getBid());
	}

	public void testOvercall1NT() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		BiddingAgent ba = new BiddingAgent(a, new Hand("A,K,2", "A,Q,3", "8,6,5,3", "K,J,3"));
		assertEquals(ONE_NOTRUMP, ba.getBid());
	}

	public void testRespondOvercall1NT() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_NOTRUMP);
		a.bid(PASS);
		BiddingAgent ba = new BiddingAgent(a, new Hand("9,8,7,6,2", "A,3", "6,5,3", "Q,4,3"));
		assertEquals(TWO_SPADES, ba.getBid());
	}

	public void testHaveToBidSomething() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, new Hand("5,4,3,2", "5,4,3", "6,5,3", "5,4,3"));
		assertEquals(PASS, ba.getBid());
	}

	public void testRP2() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, RPQuizzes.Basics.Lesson2.hand2());
		assertEquals(PASS, ba.getBid());
	}

}
