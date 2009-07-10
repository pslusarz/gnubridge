package org.gnubridge.core.bidding.rules;

import junit.framework.TestCase;

import org.gnubridge.core.Hand;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.Pass;
import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.Spades;

public class Rebid1ColorWithNewSuitTest extends TestCase {
	public void testShowUnbidSuitAt1Level() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Rebid1ColorWithNewSuit rule = new Rebid1ColorWithNewSuit(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3"));
		assertEquals(new Bid(1, Hearts.i()), rule.getBid());
	}

	public void testNoUnbidSuitWith4Cards() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Rebid1ColorWithNewSuit rule = new Rebid1ColorWithNewSuit(a, new Hand("3,2", "9,8", "K,Q,J,2", "A,K,5,4,3"));
		assertEquals(null, rule.getBid());
	}

	public void testShowUnbidSuitAt1LevelAnotherColor() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Rebid1ColorWithNewSuit rule = new Rebid1ColorWithNewSuit(a, new Hand("K,Q,J,2", "3,2", "9,8", "A,K,5,4,3"));
		assertEquals(new Bid(1, Spades.i()), rule.getBid());
	}

	public void testShowUnbidSuitEvenThoughRespondersSuitIsStronger() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Rebid1ColorWithNewSuit rule = new Rebid1ColorWithNewSuit(a, new Hand("J,5,4,2", "", "A,K,9,8", "A,K,5,4,3"));
		assertEquals(new Bid(1, Spades.i()), rule.getBid());
	}

	public void testLowestBidIsAt2LevelReverseBid() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Spades.i()));
		a.bid(new Pass());
		Rebid1ColorWithNewSuit rule = new Rebid1ColorWithNewSuit(a, new Hand("3,2", "A,K,Q,2", "9,8", "A,K,5,4,3"));
		assertEquals(new Bid(2, Hearts.i()), rule.getBid());
	}

	public void test1LevelIsNotAReverseBid() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Hearts.i()));
		a.bid(new Pass());
		Rebid1ColorWithNewSuit rule = new Rebid1ColorWithNewSuit(a, new Hand("K,Q,J,2", "9,8", "K,Q,J,3,2", "J,2"));
		assertEquals(new Bid(1, Spades.i()), rule.getBid());
	}

	public void testNeedAtLeast16ForReverseBid() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		a.bid(new Bid(2, Clubs.i()));
		a.bid(new Pass());
		Rebid1ColorWithNewSuit rule = new Rebid1ColorWithNewSuit(a, new Hand("K,Q,J,2", "9,8", "K,Q,J,3,2", "J,2"));
		assertEquals(null, rule.getBid());
	}

	public void testLowerRankedSuitThanOriginalIsNotAReverseBid() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Spades.i()));
		a.bid(new Pass());
		a.bid(new Bid(2, Clubs.i()));
		a.bid(new Pass());
		Rebid1ColorWithNewSuit rule = new Rebid1ColorWithNewSuit(a, new Hand("K,Q,J,2", "9,8", "K,Q,J,3,2", "J,2"));
		assertEquals(new Bid(2, Diamonds.i()), rule.getBid());
	}

	public void testJumpShiftTo2LevelAt19Points() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Rebid1ColorWithNewSuit rule = new Rebid1ColorWithNewSuit(a, new Hand("K,Q,J,2", "A,2", "Q,8", "A,K,5,4,3"));
		assertEquals(new Bid(2, Spades.i()), rule.getBid());
		assertTrue(rule.getBid().isGameForcing());
	}

	public void testLowestBidIsAt2LevelDoNotApplyToBalancedHand() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Spades.i()));
		a.bid(new Pass());
		Rebid1ColorWithNewSuit rule = new Rebid1ColorWithNewSuit(a, new Hand("3,2", "K,Q,J,2", "9,8,3", "A,K,5,4"));
		assertEquals(null, rule.getBid());
	}

}
