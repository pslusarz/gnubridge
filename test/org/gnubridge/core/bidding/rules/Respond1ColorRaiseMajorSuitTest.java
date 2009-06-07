package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.Pass;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Hearts;

import junit.framework.TestCase;

public class Respond1ColorRaiseMajorSuitTest extends TestCase {
	public void testOnlyAppliesToMajorSuit() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Respond1ColorRaiseMajorSuit rule = new Respond1ColorRaiseMajorSuit(a,
				new Hand("K,3,2", "K,5,4,3", "9,8,6", "5,4,3"));

		assertEquals(null, rule.getBid());
	}

	public void testOnlyAppliesToPartnersBidAt1Level() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(2, Hearts.i()));
		a.bid(new Pass());
		Respond1ColorRaiseMajorSuit rule = new Respond1ColorRaiseMajorSuit(a,
				new Hand("K,3,2", "K,5,4,3", "9,8,6", "5,4,3"));

		assertEquals(null, rule.getBid());
	}
}
