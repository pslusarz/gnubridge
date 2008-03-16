package org.gnubridge.core.bidding;

import junit.framework.TestCase;

import org.gnubridge.core.East;
import org.gnubridge.core.North;
import org.gnubridge.core.South;
import org.gnubridge.core.West;
import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Spades;

public class AuctioneerTest extends TestCase {
	public void testFirstToBid() {
		Auctioneer a = new Auctioneer(West.i());
		assertEquals(West.i(), a.getNextToBid());
		Auctioneer b = new Auctioneer(South.i());
		assertEquals(South.i(), b.getNextToBid());
	}
	
	public void testBiddingMovesClockwise() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		assertEquals(North.i(), a.getNextToBid());
		a.bid(new Pass());
		assertEquals(East.i(), a.getNextToBid());
		a.bid(new Pass());
		assertEquals(South.i(), a.getNextToBid());
		a.bid(new Bid(2, NoTrump.i()));
		assertEquals(West.i(), a.getNextToBid());	
	}
	
	public void testEndBiddingContract() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		a.bid(new Bid(2, Hearts.i()));
		a.bid(new Pass());
		a.bid(new Pass());
		assertFalse(a.biddingFinished());
		a.bid(new Pass());
		assertTrue(a.biddingFinished());
		assertEquals(new Bid(2, Hearts.i()), a.getHighBid());
	}

	public void testEndBiddingNoContract() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Pass());
		a.bid(new Pass());
		a.bid(new Pass());
		assertFalse(a.biddingFinished());
		a.bid(new Pass());
		assertTrue(a.biddingFinished());
		assertEquals(null, a.getHighBid());
	}
	
	public void testIsOpeningBid() {
		Auctioneer a = new Auctioneer(West.i());
		assertTrue(a.isOpeningBid());
		a.bid(new Pass());
		assertTrue(a.isOpeningBid());
		a.bid(new Bid(1, NoTrump.i()));
		assertTrue(a.isOpeningBid());
		a.bid(new Pass());
		assertFalse("South responding to partners 1NT is not an opening bid", a.isOpeningBid());
		a.bid(new Bid(2, Clubs.i()));
		assertFalse("West already passed on 1st round, so it is not an opening bid", a.isOpeningBid());	
	}
	
	public void testCanTraversePairsHistoryWithGetPartnersBid() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Bid(1, Hearts.i()));
		a.bid(new Pass());
		a.bid(new Pass());
		a.bid(new Bid(1, Spades.i()));
		a.bid(new Bid(1, NoTrump.i()));
		Call c8 = a.getLastCall();
		assertEquals(new Bid(1, NoTrump.i()), c8.getBid());
		Call c6 = a.getPartnersCall(c8);
		assertNotNull(c6);
		assertEquals(new Pass(), c6.getBid());
		Call c4 = a.getPartnersCall(c6);
		assertEquals(new Bid(1, Hearts.i()), c4.getBid());
		Call c2 = a.getPartnersCall(c4);
		assertEquals(new Pass(), c2.getBid());
		assertNull(a.getPartnersCall(c2));
		
		Call c7 = a.getPartnersLastCall();
		assertEquals(new Bid(1, Spades.i()), c7.getBid());
	}
	
	/**
	 * TODO: test isValid()
	 */
}
