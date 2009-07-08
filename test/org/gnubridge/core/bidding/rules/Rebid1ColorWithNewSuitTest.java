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
	public void testRaiseThePartnerTo2() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Rebid1ColorWithNewSuit rule = new Rebid1ColorWithNewSuit(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3"));
		assertEquals(new Bid(1, Hearts.i()), rule.getBid());
	}

	public void testRaiseThePartnerTo2AnotherColor() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Rebid1ColorWithNewSuit rule = new Rebid1ColorWithNewSuit(a, new Hand("K,Q,J,2", "3,2", "9,8", "A,K,5,4,3"));
		assertEquals(new Bid(1, Spades.i()), rule.getBid());
	}

	public void testLowestBidIsAt2Level() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Spades.i()));
		a.bid(new Pass());
		Rebid1ColorWithNewSuit rule = new Rebid1ColorWithNewSuit(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3"));
		assertEquals(new Bid(2, Hearts.i()), rule.getBid());
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

	public void testShowUnbidSuit() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Rebid1ColorWithNewSuit rule = new Rebid1ColorWithNewSuit(a, new Hand("J,5,4,2", "", "A,K,9,8", "A,K,5,4,3"));
		assertEquals(new Bid(1, Spades.i()), rule.getBid());
	}

}
