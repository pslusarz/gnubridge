package org.gnubridge.core.bidding.rules;

import junit.framework.TestCase;

import org.gnubridge.core.Hand;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.Pass;
import org.gnubridge.core.bidding.RPQuizzes;
import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.Spades;

public class Open1ColorTest extends TestCase {
	public void testOpenOneColor5ColorSuit() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingRule rule = new Open1Color(a, new Hand("K,2", "A,3",
				"A,8,6,5,3", "5,4,3"));
		assertEquals(new Bid(1, Diamonds.i()), rule.getBid());
	}
	public void testCannotOpenOneColorIfResponding() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		BiddingRule rule = new Open1Color(a, new Hand("K,2", "A,3",
				"A,8,6,5,3", "5,4,3"));
		assertNull(rule.getBid());
	}

	public void testCannotOpenOneColorIfHigherBid() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Hearts.i()));
		BiddingRule rule = new Open1Color(a, new Hand("K,2", "A,3",
				"A,8,6,5,3", "5,4,3"));
		assertNull(rule.getBid());
	}

	public void testOpenOneColorTwo5ColorSuitsBidHigher() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingRule rule = new Open1Color(a, new Hand("K,2", "9,8,7,5,3",
				"A,K,8,6,5", "2"));
		assertEquals(new Bid(1, Hearts.i()), rule.getBid());
	}

	public void testOpenOneColor6ColorSuit() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingRule rule = new Open1Color(a, new Hand("K,2", "A,Q,7,5,3",
				"10,9,8,6,5,4"));
		assertEquals(new Bid(1, Diamonds.i()), rule.getBid());
	}

	public void testOpenOneColorNo5BidLongerMinor() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingRule rule = new Open1Color(a, new Hand("K,Q,J,2", "Q,7,5,4",
				"10,9,8", "A,2"));
		assertEquals(new Bid(1, Diamonds.i()), rule.getBid());
		BiddingRule triangulate = new Open1Color(a, new Hand("K,Q,J,2",
				"Q,7,5,4", "A,2", "10,9,8"));
		assertEquals(new Bid(1, Clubs.i()), triangulate.getBid());
	}

	public void testOpenOneColorNo5Minor4x4() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingRule rule = new Open1Color(a, new Hand("Q,9,8", "9,7",
				"5,4,3,2", "A,K,Q,J"));
		assertEquals(new Bid(1, Diamonds.i()), rule.getBid());
	}

	public void testOpenOneColorNo5Minor3x3() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingRule rule = new Open1Color(a, new Hand("K,J,2", "Q,7,5,4",
				"10,9,8", "A,K,10"));
		assertEquals(new Bid(1, Clubs.i()), rule.getBid());
		BiddingRule triangulate = new Open1Color(a, new Hand("K,J,2",
				"Q,7,5,4", "A,K,10", "10,9,8"));
		assertEquals(new Bid(1, Diamonds.i()), triangulate.getBid());
	}

	public void testRP2() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingRule rule = new Open1Color(a, RPQuizzes.Basics.Lesson2.hand2());
		assertNull( rule.getBid());
	}

	public void testRP3() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingRule rule = new Open1Color(a, RPQuizzes.Basics.Lesson2.hand3());
		assertEquals(new Bid(1, Clubs.i()), rule.getBid());
	}

	public void testRP4() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingRule rule = new Open1Color(a, RPQuizzes.Basics.Lesson2.hand4());
		assertEquals(new Bid(1, Diamonds.i()), rule.getBid());
	}


	public void testRP6() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingRule rule = new Open1Color(a, RPQuizzes.Basics.Lesson2.hand6());
		assertEquals(new Bid(1, Spades.i()), rule.getBid());
	}

	public void testRP7() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingRule rule = new Open1Color(a, RPQuizzes.Basics.Lesson2.hand7());
		assertEquals(new Bid(1, Clubs.i()), rule.getBid());
	}

	public void testRP8() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingRule rule = new Open1Color(a, RPQuizzes.Basics.Lesson2.hand8());
		assertEquals(new Bid(1, Diamonds.i()), rule.getBid());
	}

	public void testRP9() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingRule rule = new Open1Color(a, RPQuizzes.Basics.Lesson2.hand9());
		assertEquals(new Bid(1, Clubs.i()), rule.getBid());
	}

	public void testRP10() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingRule rule = new Open1Color(a, RPQuizzes.Basics.Lesson2.hand10());
		assertEquals(new Bid(1, Spades.i()), rule.getBid());
	}


	public void testRP12() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingRule rule = new Open1Color(a, RPQuizzes.Basics.Lesson2.hand12());
		assertEquals(new Bid(1, Diamonds.i()), rule.getBid());
	}
}
