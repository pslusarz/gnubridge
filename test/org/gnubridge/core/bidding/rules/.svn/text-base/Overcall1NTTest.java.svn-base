package org.gnubridge.core.bidding.rules;

import static org.gnubridge.core.bidding.Bid.*;
import junit.framework.TestCase;

import org.gnubridge.core.Hand;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;

public class Overcall1NTTest extends TestCase {
	public void testMakeOvercallWith16to18HCPBalancedHandAndStopperInEnemySuit() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		Overcall1NT rule = new Overcall1NT(a, new Hand("K,2", "A,Q,3", "A,8,6,5,3", "K,J,3"));
		assertEquals(ONE_NOTRUMP, rule.getBid());
	}

	public void testDoNotApplyIfNoOvercall() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(PASS);
		Overcall1NT rule = new Overcall1NT(a, new Hand("K,2", "A,Q,3", "A,8,6,5,3", "K,J,3"));
		assertEquals(null, rule.getBid());
	}

	public void testDoNotApplyIfLessThan16HCP() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		Overcall1NT rule = new Overcall1NT(a, new Hand("7,2", "A,Q,3", "A,8,6,5,3", "K,J,3"));
		assertEquals(null, rule.getBid());
	}

	public void testDoNotApplyIfMoreThan18HCP() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		Overcall1NT rule = new Overcall1NT(a, new Hand("A,2", "A,Q,3", "A,K,6,5,3", "K,J,3"));
		assertEquals(null, rule.getBid());
	}

	public void testDoNotApplyIfImbalanced() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		Overcall1NT rule = new Overcall1NT(a, new Hand("2", "A,K,Q,3", "A,8,6,5,3", "K,J,3"));
		assertEquals(null, rule.getBid());
	}

	public void testDoNotApplyIfNoStopperInEnemySuit() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		Overcall1NT rule = new Overcall1NT(a, new Hand("K,2", "A,Q,3", "A,K,8,6,5,3", "J,10,3"));
		assertEquals(null, rule.getBid());
	}
}
