package org.gnubridge.core.bidding;

import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Spades;

import junit.framework.TestCase;

public class BidTest extends TestCase {
	public void testGreaterThanFalseIfSame() {
		assertFalse(new Pass().greaterThan(new Pass()));
		assertFalse(new Bid(1, NoTrump.i())
				.greaterThan(new Bid(1, NoTrump.i())));
	}

	public void testGreaterThanColorPrecedence() {
		assertTrue(new Pass().greaterThan(null));
		assertTrue(new Bid(1, Clubs.i()).greaterThan(new Pass()));
		assertTrue(new Bid(1, Diamonds.i()).greaterThan(new Bid(1, Clubs.i())));
		assertTrue(new Bid(1, Hearts.i()).greaterThan(new Bid(1, Diamonds.i())));
		assertTrue(new Bid(1, Spades.i()).greaterThan(new Bid(1, Hearts.i())));
		assertTrue(new Bid(1, NoTrump.i()).greaterThan(new Bid(1, Spades.i())));
	}
	
	public void testHigherValue() {
		assertTrue(new Bid(2, Clubs.i()).greaterThan(new Bid(1, Clubs.i())));
		
	}
	
	public void testSymmetricColor() {
		assertTrue(new Bid(1, Clubs.i()).greaterThan(new Pass()));
		assertFalse(new Pass().greaterThan(new Bid(1, Clubs.i())));
	}
	
	public void testSymmetricValue() {
		assertTrue(new Bid(2, Clubs.i()).greaterThan(new Bid(1, Clubs.i())));
		assertFalse(new Bid(1, Clubs.i()).greaterThan(new Bid(2, Clubs.i())));
		
		assertTrue(new Bid(2, Clubs.i()).greaterThan(new Bid(1, NoTrump.i())));
		assertFalse(new Bid(1, NoTrump.i()).greaterThan(new Bid(2, Clubs.i())));
	}
	
	public void testTransitive() {
		assertTrue(new Bid(1, Clubs.i()).greaterThan(new Pass()));
		assertTrue(new Bid(1, Diamonds.i()).greaterThan(new Bid(1, Clubs.i())));
		assertTrue(new Bid(1, Diamonds.i()).greaterThan(new Pass()));
	}
	
/**
 * TODO: test makeBid
 * TODO: test Trump.instance()
 */

}
