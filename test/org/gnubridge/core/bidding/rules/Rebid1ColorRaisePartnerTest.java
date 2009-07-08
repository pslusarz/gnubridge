package org.gnubridge.core.bidding.rules;

import junit.framework.TestCase;

import org.gnubridge.core.Hand;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.Pass;
import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.Spades;

public class Rebid1ColorRaisePartnerTest extends TestCase {
	public void testRaiseThePartnerTo2() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Hearts.i()));
		a.bid(new Pass());
		Rebid1ColorRaisePartner rule = new Rebid1ColorRaisePartner(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3"));

		assertEquals(new Bid(2, Hearts.i()), rule.getBid());
	}

	public void testRaiseThePartnerTo2AnotherColor() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Spades.i()));
		a.bid(new Pass());
		Rebid1ColorRaisePartner rule = new Rebid1ColorRaisePartner(a, new Hand("9,5,3,2", "A,Q", "9,8", "A,K,5,4,3"));

		assertEquals(new Bid(2, Spades.i()), rule.getBid());
	}

	public void testRaiseThePartnerTo3() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(2, Hearts.i()));
		a.bid(new Pass());
		Rebid1ColorRaisePartner rule = new Rebid1ColorRaisePartner(a, new Hand("3,2", "K,Q,J,2", "K,8", "A,K,5,4,3"));

		assertEquals(new Bid(3, Hearts.i()), rule.getBid());
	}

	public void testRaiseThePartnerTo4() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(2, Hearts.i()));
		a.bid(new Pass());
		Rebid1ColorRaisePartner rule = new Rebid1ColorRaisePartner(a, new Hand("3,2", "K,Q,J,2", "K,8", "A,K,J,4,3"));

		assertEquals(new Bid(4, Hearts.i()), rule.getBid());
	}

	public void testUse5_3_1distributionalCount() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Spades.i()));
		a.bid(new Pass());
		Rebid1ColorRaisePartner rule = new Rebid1ColorRaisePartner(a, new Hand("Q,5,3,2", "Q,9,8,7", "", "A,K,5,4,3"));

		assertEquals(new Bid(3, Spades.i()), rule.getBid());
	}

	public void testDoNotApplyToNoTrumpResponse() {
		fail("todo");
	}

}
