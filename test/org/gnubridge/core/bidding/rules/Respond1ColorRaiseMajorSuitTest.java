package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.Pass;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.Spades;

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
	
	public void testRaiseThePartnerBy1() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Hearts.i()));
		a.bid(new Pass());
		Respond1ColorRaiseMajorSuit rule = new Respond1ColorRaiseMajorSuit(a,
				new Hand("K,3,2", "K,5,4", "9,8,6,5", "5,4,3"));
		
		assertEquals(new Bid(2, Hearts.i()), rule.getBid());
	}
	public void testRaiseThePartnerBy1AlsoOnSpades() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Spades.i()));
		a.bid(new Pass());
		Respond1ColorRaiseMajorSuit rule = new Respond1ColorRaiseMajorSuit(a,
				new Hand("K,3,2", "K,5,4", "9,8,6,5", "5,4,3"));
		
		assertEquals(new Bid(2, Spades.i()), rule.getBid());
	}
	
	public void testDoNotApplyIfLessThan6Points() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Spades.i()));
		a.bid(new Pass());
		Respond1ColorRaiseMajorSuit rule = new Respond1ColorRaiseMajorSuit(a,
				new Hand("K,3,2", "9,5,4", "9,8,6,5", "5,4,3"));
		
		assertEquals(null, rule.getBid());
	}
	
	public void testCountBonusDistributionalPoints() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Spades.i()));
		a.bid(new Pass());
		Respond1ColorRaiseMajorSuit rule = new Respond1ColorRaiseMajorSuit(a,
				new Hand("J,4,3,2", "9,5,4", "9,8,6,5,4,3", ""));
		
		assertEquals(new Bid(2, Spades.i()), rule.getBid());
	}
	
	public void testDoNotApplyIfLessThan3Trumps() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Hearts.i()));
		a.bid(new Pass());
		Respond1ColorRaiseMajorSuit rule = new Respond1ColorRaiseMajorSuit(a,
				new Hand("K,3,2", "K,5", "9,8,6,5", "5,4,3,2"));
		
		assertEquals(null, rule.getBid());
	}
	
	/**
	 *   Pavlicek explicitly states this rule does not apply for 11 and 12 points
	 */
	
	public void testRaiseThePartnerBy1DoNotApplyBetween11and12Points() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Hearts.i()));
		a.bid(new Pass());
		Respond1ColorRaiseMajorSuit rule = new Respond1ColorRaiseMajorSuit(a,
				new Hand("K,3,2", "K,5,4", "A,8,6,5", "J,4,3"));
		
		assertEquals(null, rule.getBid());
	}
	
	public void testRaiseThePartnerBy2Over12Points() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Hearts.i()));
		a.bid(new Pass());
		Respond1ColorRaiseMajorSuit rule = new Respond1ColorRaiseMajorSuit(a,
				new Hand("K,3,2", "K,5,4", "A,8,6,5", "K,3,2"));
		
		assertEquals(new Bid(3, Hearts.i()), rule.getBid());
	}
	public void testRaiseThePartnerBy1DoNotApplyOver16Points() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Hearts.i()));
		a.bid(new Pass());
		Respond1ColorRaiseMajorSuit rule = new Respond1ColorRaiseMajorSuit(a,
				new Hand("K,3,2", "K,5,4", "A,8,6,5", "A,K,3"));
		
		assertEquals(null, rule.getBid());
	}
	public void testCanHandlePartnersPassWithoutNPE() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Pass());
		a.bid(new Pass());
		Respond1ColorRaiseMajorSuit rule = new Respond1ColorRaiseMajorSuit(a,
				new Hand("K,3,2", "K,5,4", "A,8,6,5", "A,K,3"));
		
		assertEquals(null, rule.getBid());
	}
	
}
