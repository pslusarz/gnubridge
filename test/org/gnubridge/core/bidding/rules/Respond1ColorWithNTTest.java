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
import org.gnubridge.core.deck.NoTrump;

public class Respond1ColorWithNTTest extends TestCase {
	public void test1NTIfHave6Points() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Hearts.i()));
		a.bid(new Pass());
		Respond1ColorWithNT rule = new Respond1ColorWithNT(a, new Hand("K,3,2", "5,4,3", "K,9,8,6", "5,4,3"));

		assertEquals(new Bid(1, NoTrump.i()), rule.getBid());
	}

	public void test1NTIfHave10Points() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		Respond1ColorWithNT rule = new Respond1ColorWithNT(a, new Hand("K,3,2", "K,J,4,3", "K,9,8,6", "5,4,3"));

		assertEquals(new Bid(1, NoTrump.i()), rule.getBid());
	}

	public void testdoesNotApplyAt11Points() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		Respond1ColorWithNT rule = new Respond1ColorWithNT(a, new Hand("K,J,3,2", "K,J,4,3", "K,9,8,6", "5,4,3"));

		assertEquals(null, rule.getBid());
	}

	public void testDoesNotApplyBelow6() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Respond1ColorWithNT rule = new Respond1ColorWithNT(a, new Hand("K,3,2", "5,4,3", "Q,9,8,6", "5,4,3"));

		assertEquals(null, rule.getBid());
	}

	public void testDoNotApply_1_3_5_Calculator() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Respond1ColorWithNT rule = new Respond1ColorWithNT(a, new Hand("10,5,4,3,2", "", "J,9,8,6", "5,4,3,2"));

		assertEquals(null, rule.getBid());
	}

	public void testDoNotCountDistributionalPoints() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Respond1ColorWithNT rule = new Respond1ColorWithNT(a, new Hand("10,5,4,3,2", "", "K,9,8,6", "5,4,3,2"));

		assertEquals(null, rule.getBid());
	}

	public void testRaise2WhenOver12AndBalanced() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Respond1ColorWithNT rule = new Respond1ColorWithNT(a, new Hand("10,5,4", "A,K", "K,9,8,6", "K,4,3,2"));

		assertEquals(new Bid(2, NoTrump.i()), rule.getBid());
	}

	public void testDoNotRaise2WhenUnbalanced() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Respond1ColorWithNT rule = new Respond1ColorWithNT(a, new Hand("K,10,5,4", "A", "K,9,8,6", "K,4,3,2"));

		assertEquals(null, rule.getBid());
	}

	public void testRaise3WhenOver16() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Respond1ColorWithNT rule = new Respond1ColorWithNT(a, new Hand("K,10,5", "A,K", "K,9,8,6", "K,J,3,2"));

		assertEquals(new Bid(3, NoTrump.i()), rule.getBid());
	}

	public void testDoNotRaise3WhenOver18() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Respond1ColorWithNT rule = new Respond1ColorWithNT(a, new Hand("K,10,5", "A,K", "K,9,8,6", "K,Q,J,2"));

		assertEquals(null, rule.getBid());
	}

}
