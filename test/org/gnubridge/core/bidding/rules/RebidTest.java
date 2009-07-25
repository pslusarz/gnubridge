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

public class RebidTest extends TestCase {
	public void testOnlyApplyToRebids() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Hearts.i()));
		a.bid(new Pass());
		Rebid rule = new Rebid(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3")) {
			@Override
			protected Bid prepareBid() {
				throw new RuntimeException("should not try to prepare bid when it's not a rebid situation");
			}

		};
		assertEquals(null, rule.getBid());
	}

	public void testPrepareBidInRebidSituation() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());

		Rebid rule = new Rebid(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3")) {
			@Override
			protected Bid prepareBid() {
				return new Bid(7, NoTrump.i());
			}

		};
		assertEquals(new Bid(7, NoTrump.i()), rule.getBid());
	}

	public void testDoNotThrowNPEPartnerPass() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Pass());
		a.bid(new Pass());
		Rebid rule = new Rebid(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3")) {
			@Override
			protected Bid prepareBid() {
				return null;
			}

		};
		assertEquals(null, rule.getBid());
	}

	public void testDoNotThrowNPEPartnerDidNotBid() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Pass());
		Rebid rule = new Rebid(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3")) {
			@Override
			protected Bid prepareBid() {
				return null;
			}

		};
		assertEquals(null, rule.getBid());
	}
}
