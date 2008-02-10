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
	public void testOpeningOneNTFirstCall() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,2", "A,Q,3",
				"A,8,6,5,3", "K,J,3"));
		assertEquals(new Bid(1, NoTrump.i()), ba.getBid());
	}

	public void testCannotOpenOneNTIfInsufficientHCP() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,2", "A,3",
				"A,8,6,5,3", "K,J,3"));
		assertFalse(new Bid(1, NoTrump.i()).equals(ba.getBid()));
	}

	public void testDoNotOpenOneNTOnImbalancedHand() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, new Hand("K", "A,Q,3",
				"A,8,6,5,3", "K,J,3,2"));
		assertFalse(new Bid(1, NoTrump.i()).equals(ba.getBid()));
		assertNotNull("have to bid something", ba.getBid());
	}

	public void testOpeningOneNTSecondCall() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,2", "A,Q,3",
				"A,8,6,5,3", "K,J,3"));
		assertEquals(new Bid(1, NoTrump.i()), ba.getBid());
	}

	public void testOpeningOneNTThirdCall() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,2", "A,Q,3",
				"A,8,6,5,3", "K,J,3"));
		assertEquals(new Bid(1, NoTrump.i()), ba.getBid());
	}

	public void testOpeningOneNTFourthCall() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Hearts.i()));
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,2", "A,Q,3",
				"A,8,6,5,3", "K,J,3"));
		assertEquals(new Bid(1, NoTrump.i()), ba.getBid());
	}

	public void testCannotOpenOneNTIfHigherBid() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(2, Clubs.i()));
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,2", "A,Q,3",
				"A,8,6,5,3", "K,J,3"));
		assertFalse(new Bid(1, NoTrump.i()).equals(ba.getBid()));
		assertNotNull("have to bid something", ba.getBid());
	}

	public void testOpenOneColor5ColorSuit() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,2", "A,3",
				"A,8,6,5,3", "5,4,3"));
		assertEquals(new Bid(1, Diamonds.i()), ba.getBid());
	}

	public void testCannotOpenOneColorIfHigherBid() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Hearts.i()));
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,2", "A,3",
				"A,8,6,5,3", "5,4,3"));
		assertFalse(new Bid(1, Diamonds.i()).equals(ba.getBid()));
		assertNotNull("have to bid something", ba.getBid());
	}

	public void testOpenOneColorTwo5ColorSuitsBidHigher() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,2", "9,8,7,5,3",
				"A,K,8,6,5", "2"));
		assertEquals(new Bid(1, Hearts.i()), ba.getBid());
	}

	public void testOpenOneColor6ColorSuit() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,2", "A,Q,7,5,3",
				"10,9,8,6,5,4"));
		assertEquals(new Bid(1, Diamonds.i()), ba.getBid());
	}

	public void testOpenOneColorNo5BidLongerMinor() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,Q,J,2", "Q,7,5,4",
				"10,9,8", "A,2"));
		assertEquals(new Bid(1, Diamonds.i()), ba.getBid());
		BiddingAgent triangulate = new BiddingAgent(a, new Hand("K,Q,J,2",
				"Q,7,5,4", "A,2", "10,9,8"));
		assertEquals(new Bid(1, Clubs.i()), triangulate.getBid());
	}

	public void testOpenOneColorNo5Minor4x4() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, new Hand("Q,9,8", "9,7",
				"5,4,3,2", "A,K,Q,J"));
		assertEquals(new Bid(1, Diamonds.i()), ba.getBid());
	}

	public void testOpenOneColorNo5Minor3x3() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, new Hand("K,J,2", "Q,7,5,4",
				"10,9,8", "A,K,10"));
		assertEquals(new Bid(1, Clubs.i()), ba.getBid());
		BiddingAgent triangulate = new BiddingAgent(a, new Hand("K,J,2",
				"Q,7,5,4", "A,K,10", "10,9,8"));
		assertEquals(new Bid(1, Diamonds.i()), triangulate.getBid());
	}

	public void testRP1() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, RPQuizzes.Basics.Lesson2.hand1());
		assertEquals(new Bid(1, NoTrump.i()), ba.getBid());
	}

	public void testRP2() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, RPQuizzes.Basics.Lesson2.hand2());
		assertEquals(new Pass(), ba.getBid());
	}

	public void testRP3() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, RPQuizzes.Basics.Lesson2.hand3());
		assertEquals(new Bid(1, Clubs.i()), ba.getBid());
	}

	public void testRP4() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, RPQuizzes.Basics.Lesson2.hand4());
		assertEquals(new Bid(1, Diamonds.i()), ba.getBid());
	}

	public void testRP5() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, RPQuizzes.Basics.Lesson2.hand5());
		assertEquals(new Bid(1, NoTrump.i()), ba.getBid());
	}

	public void testRP6() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, RPQuizzes.Basics.Lesson2.hand6());
		assertEquals(new Bid(1, Spades.i()), ba.getBid());
	}

	public void testRP7() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, RPQuizzes.Basics.Lesson2.hand7());
		assertEquals(new Bid(1, Clubs.i()), ba.getBid());
	}

	public void testRP8() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, RPQuizzes.Basics.Lesson2.hand8());
		assertEquals(new Bid(1, Diamonds.i()), ba.getBid());
	}

	public void testRP9() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, RPQuizzes.Basics.Lesson2.hand9());
		assertEquals(new Bid(1, Clubs.i()), ba.getBid());
	}

	public void testRP10() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, RPQuizzes.Basics.Lesson2.hand10());
		assertEquals(new Bid(1, Spades.i()), ba.getBid());
	}

	public void testRP11() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, RPQuizzes.Basics.Lesson2.hand11());
		assertEquals(new Bid(1, NoTrump.i()), ba.getBid());
	}

	public void testRP12() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, RPQuizzes.Basics.Lesson2.hand12());
		assertEquals(new Bid(1, Diamonds.i()), ba.getBid());
	}

}
