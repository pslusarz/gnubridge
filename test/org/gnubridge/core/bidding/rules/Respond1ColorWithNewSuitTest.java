package org.gnubridge.core.bidding.rules;

import junit.framework.TestCase;

import org.gnubridge.core.Hand;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.Pass;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Spades;

public class Respond1ColorWithNewSuitTest extends TestCase {
	public void testRespond1Color() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Respond1ColorWithNewSuit rule = new Respond1ColorWithNewSuit(a, new Hand("K,3,2", "K,5,4,3",
				"9,8,6", "5,4,3"));
		assertEquals(new Bid(1, Hearts.i()), rule.getBid());
		Respond1ColorWithNewSuit triangulate = new Respond1ColorWithNewSuit(a, new Hand("K,4,3,2", "K,5,4",
				"9,8,6", "5,4,3"));
		assertEquals(new Bid(1, Spades.i()), triangulate.getBid());
	}
	public void testRespond1ColorBonusOnDistributionalPoints() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Respond1ColorWithNewSuit rule = new Respond1ColorWithNewSuit(a, new Hand("K,3,2", "10,5,4,3,2",
				"9,8,6,5", "5"));
		assertEquals(new Bid(1, Hearts.i()), rule.getBid());
	}
	public void testRespond1ColorBestSuit() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Respond1ColorWithNewSuit rule = new Respond1ColorWithNewSuit(a, new Hand("K,5,3,2", "K,5,4,3",
				"9,8,6", "5,4"));
		assertEquals(new Bid(1, Spades.i()), rule.getBid());
		Respond1ColorWithNewSuit rule2 = new Respond1ColorWithNewSuit(a, new Hand("K,5,3,2", "K,5,4,3,2",
				"9,8", "5,4"));
		assertEquals(new Bid(1, Hearts.i()), rule2.getBid());
	}
	public void testRespond1Color11Points() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Respond1ColorWithNewSuit rule = new Respond1ColorWithNewSuit(a, new Hand("K,5,3,2", "K,5,4,3",
				"A,8,6", "5,4"));
		assertEquals(new Bid(1, Spades.i()), rule.getBid());
		
	}
	public void testRespond2Color11Points() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Spades.i()));
		a.bid(new Pass());
		Respond1ColorWithNewSuit rule = new Respond1ColorWithNewSuit(a, new Hand("K,3,2", "K,5,4,3",
				"A,8,6,3", "5,4"));
		assertEquals(new Bid(2, Hearts.i()), rule.getBid());
	}
	public void testJumpSuit17Points5Suit() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Respond1ColorWithNewSuit rule = new Respond1ColorWithNewSuit(a, new Hand("K,3,2", "A,K,5,4,3",
				"A,Q,6,3", "5,4"));
		assertEquals(new Bid(2, Hearts.i()), rule.getBid());
	}
	public void testDoNotJumpSuitIfNotRespondingTo1Color() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		Respond1ColorWithNewSuit rule = new Respond1ColorWithNewSuit(a, new Hand("K,3,2", "A,K,5,4,3",
				"A,Q,6,3", "5,4"));
		assertEquals(null, rule.getBid());
	}
	public void testDoNotJumpSuitIfRespondingTo2Color() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(2, Diamonds.i()));
		a.bid(new Pass());
		Respond1ColorWithNewSuit rule = new Respond1ColorWithNewSuit(a, new Hand("K,3,2", "A,K,5,4,3",
				"A,Q,6,3", "5,4"));
		assertEquals(null, rule.getBid());
	}
}
