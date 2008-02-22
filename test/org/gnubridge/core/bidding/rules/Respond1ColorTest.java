package org.gnubridge.core.bidding.rules;

import junit.framework.TestCase;

import org.gnubridge.core.Hand;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.Pass;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Hearts;

public class Respond1ColorTest extends TestCase {
	public void testRespond1Color() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Respond1Color rule = new Respond1Color(a, new Hand("K,3,2", "K,5,4,3",
				"9,8,6", "5,4,3"));
		assertEquals(new Bid(1, Hearts.i()), rule.getBid());
	}
}
