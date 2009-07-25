package org.gnubridge.core.bidding.rules;

import junit.framework.TestCase;

import org.gnubridge.core.Hand;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.Pass;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Hearts;

public class RebidTo1ResponseTest extends TestCase {
	public void testOnlyApplyToRebidsFrom1LevelResponseAsPerLesson5() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Hearts.i()));
		a.bid(new Pass());
		a.bid(new Bid(2, Diamonds.i()));
		a.bid(new Pass());
		RebidToLevel1Response rule = new RebidToLevel1Response(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3")) {
			@Override
			protected Bid prepareBid() {
				throw new RuntimeException("should not try to prepare bid when partner responsed above level 1");
			}

		};
		assertEquals(null, rule.getBid());
	}
}
