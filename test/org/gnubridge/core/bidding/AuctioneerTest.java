package org.gnubridge.core.bidding;

import junit.framework.TestCase;

import org.gnubridge.core.East;
import org.gnubridge.core.North;
import org.gnubridge.core.South;
import org.gnubridge.core.West;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.NoTrump;

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
}
