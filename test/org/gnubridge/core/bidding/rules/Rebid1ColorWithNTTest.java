package org.gnubridge.core.bidding.rules;

import junit.framework.TestCase;

import org.gnubridge.core.Hand;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.Pass;
import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.NoTrump;

public class Rebid1ColorWithNTTest extends TestCase {
	public void testBalancedAt1Level() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Rebid1ColorWithNT rule = new Rebid1ColorWithNT(a, new Hand("4,3,2", "K,Q,J,2", "9,8", "A,K,5,4"));
		assertEquals(new Bid(1, NoTrump.i()), rule.getBid());
	}

	public void testDoesNotApplyIfNotBalanced() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Rebid1ColorWithNT rule = new Rebid1ColorWithNT(a, new Hand("", "K,Q,J,3,2", "9,8,7", "A,K,5,4,3"));
		assertEquals(null, rule.getBid());
	}

	public void testDoNotApplyWhenBancedAt16To18() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Rebid1ColorWithNT rule = new Rebid1ColorWithNT(a, new Hand("A,3,2", "K,Q,J,2", "9,8", "A,K,5,4"));
		assertEquals(null, rule.getBid());
	}

	public void testBidAt2WhenBancedAt19To20() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Rebid1ColorWithNT rule = new Rebid1ColorWithNT(a, new Hand("A,Q,2", "K,Q,J,2", "9,8", "A,K,5,4"));
		assertEquals(new Bid(2, NoTrump.i()), rule.getBid());
	}

	public void testDoNotApplyWhenBancedOver20() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Rebid1ColorWithNT rule = new Rebid1ColorWithNT(a, new Hand("A,Q,2", "K,Q,J,2", "Q,8", "A,K,5,4"));
		assertEquals(null, rule.getBid());
	}

	public void testResponseIsNTTameWith16To18Points() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		Rebid1ColorWithNT rule = new Rebid1ColorWithNT(a, new Hand("A,4,3,2", "K,Q,J,2", "9", "A,K,5,4"));
		assertEquals(new Bid(2, NoTrump.i()), rule.getBid());
	}

	public void testResponseIsNTNotTameWith16To18Points() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		Rebid1ColorWithNT rule = new Rebid1ColorWithNT(a, new Hand("A,K,4,3,2", "Q,J", "9", "A,K,5,4,2"));
		assertEquals(null, rule.getBid());
	}

	public void testResponseIsNTTameWith19Points() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		Rebid1ColorWithNT rule = new Rebid1ColorWithNT(a, new Hand("A,Q,3,2", "K,Q,J,2", "9", "A,K,5,4"));
		assertEquals(new Bid(3, NoTrump.i()), rule.getBid());
	}

	public void testResponseIsNTNotTameWith19Points() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		Rebid1ColorWithNT rule = new Rebid1ColorWithNT(a, new Hand("A,Q,3,2", "K,Q,J,5,3,2", "9", "A,K"));
		assertEquals(null, rule.getBid());
	}

	public void testResponseIsNTBalancedWith19Points() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		Rebid1ColorWithNT rule = new Rebid1ColorWithNT(a, new Hand("A,Q,3,2", "K,Q,J,2", "9,4", "A,K,5"));
		assertEquals(new Bid(3, NoTrump.i()), rule.getBid());
	}

}
