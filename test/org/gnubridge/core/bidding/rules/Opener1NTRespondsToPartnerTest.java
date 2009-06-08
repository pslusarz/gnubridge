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

public class Opener1NTRespondsToPartnerTest extends TestCase {
	public void testRaiseTo4IfAtLeast3CardsInColor() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		a.bid(new Bid(3, Hearts.i()));
		a.bid(new Pass());
		Opener1NTRespondsToPartner rule = new Opener1NTRespondsToPartner(
				a, new Hand("K,2", "A,3,2", "A,Q,8,6", "K,J,5,3"));
		assertEquals(new Bid(4, Hearts.i()), rule.getBid());

		Auctioneer a2 = new Auctioneer(West.i());
		a2.bid(new Bid(1, NoTrump.i()));
		a2.bid(new Pass());
		a2.bid(new Bid(3, Spades.i()));
		a2.bid(new Pass());
		Opener1NTRespondsToPartner triangulate = new Opener1NTRespondsToPartner(
				a2, new Hand("A,Q,3", "K,2", "A,8,6,3", "K,J,5,3"));
		assertEquals(new Bid(4, Spades.i()), triangulate.getBid());
	}

	public void testRaiseTo3NTIfDoubletonInColor() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		a.bid(new Bid(3, Hearts.i()));
		a.bid(new Pass());
		Opener1NTRespondsToPartner rule = new Opener1NTRespondsToPartner(
				a, new Hand("K,3,2", "A,3", "A,Q,8,6", "K,J,5,3"));
		assertEquals(new Bid(3, NoTrump.i()), rule.getBid());
	}
	public void testDoNotRespondIfPartnerDidNotCallAMajorColor() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		a.bid(new Bid(3, Diamonds.i()));
		a.bid(new Pass());
		Opener1NTRespondsToPartner rule = new Opener1NTRespondsToPartner(
				a, new Hand("K,3,2", "A,3", "A,Q,8,6", "K,J,5,3"));
		assertNull(rule.getBid());
	}
	public void testPassIfPartnerCalled2InAMajorColor() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		a.bid(new Bid(2, Spades.i()));
		a.bid(new Pass());
		Opener1NTRespondsToPartner rule = new Opener1NTRespondsToPartner(
				a, new Hand("K,3,2", "A,3", "A,Q,8,6", "K,J,5,3"));
		assertEquals(new Pass(), rule.getBid());
	}
	public void test2NTInvitational16HCP() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		a.bid(new Bid(2, NoTrump.i()));
		a.bid(new Pass());
		Opener1NTRespondsToPartner rule = new Opener1NTRespondsToPartner(
				a, new Hand("K,3,2", "A,3", "A,J,8,6", "K,J,5,3"));
		assertEquals(new Pass(), rule.getBid());
	}
	public void test2NTInvitational18HCP() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		a.bid(new Bid(2, NoTrump.i()));
		a.bid(new Pass());
		Opener1NTRespondsToPartner rule = new Opener1NTRespondsToPartner(
				a, new Hand("K,3,2", "A,3", "A,K,8,6", "K,J,5,3"));
		assertEquals(new Bid(3, NoTrump.i()), rule.getBid());
	}
	public void test3NTSignoff() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		a.bid(new Bid(3, NoTrump.i()));
		a.bid(new Pass());
		Opener1NTRespondsToPartner rule = new Opener1NTRespondsToPartner(
				a, new Hand("K,3,2", "A,3", "A,K,8,6", "K,J,5,3"));
		assertEquals(new Pass(), rule.getBid());
	}
	public void testDoNotRespondIfPartnersBidWasAnOpening() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(3, Spades.i()));
		a.bid(new Pass());
		Opener1NTRespondsToPartner rule = new Opener1NTRespondsToPartner(
				a, new Hand("K,3,2", "A,3", "A,Q,8,6", "K,J,5,3"));
		assertNull(rule.getBid());
	}
	public void testDoNotRespondIfPartnersBidWasNotRespondingTo1NT() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Spades.i()));
		a.bid(new Pass());
		a.bid(new Bid(3, Spades.i()));
		a.bid(new Pass());
		Opener1NTRespondsToPartner rule = new Opener1NTRespondsToPartner(
				a, new Hand("K,3,2", "A,3", "A,Q,8,6", "K,J,5,3"));
		assertEquals(null, rule.getBid());
	}
	
	public void testBugIdentifyingPartnersBidThrowsNPE() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		a.bid(new Pass());
		a.bid(new Pass());
		Opener1NTRespondsToPartner rule = new Opener1NTRespondsToPartner(
				a, new Hand("A,K,5,2", "8,6,5,2", "A,Q,9", "A,7"));	
		assertEquals(null, rule.getBid());
	}

}
