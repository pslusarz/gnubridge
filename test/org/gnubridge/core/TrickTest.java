package org.gnubridge.core;

import junit.framework.TestCase;

import org.gnubridge.core.deck.Ace;
import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Four;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Spades;
import org.gnubridge.core.deck.Three;
import org.gnubridge.core.deck.Two;

public class TrickTest extends TestCase {
	Trick tr;
	protected void setUp() {
		tr = new Trick(NoTrump.i());
	}
	public void testGetHighestCardSameColor() {
		tr.addCard(Four.of(Spades.i()));
		tr.addCard(Two.of(Spades.i()));
		Card expected = Ace.of(Spades.i());
		tr.addCard(expected);
		tr.addCard(Three.of(Spades.i()));
		assertEquals(expected, tr.getHighestCard());
	}

	public void testGetHighestUnmatchedColor() {
		tr.addCard(Two.of(Spades.i()));
		tr.addCard(Ace.of(Hearts.i()));
		tr.addCard(Three.of(Spades.i()));
		Card expected = Four.of(Spades.i());
		tr.addCard(expected);		
		assertEquals(expected, tr.getHighestCard());
	}

	public void testGetHighestCardTrump() {
		tr.setTrump(Clubs.i());
		tr.addCard(Four.of(Spades.i()));
		Card expected = Two.of(Clubs.i());
		tr.addCard(expected);
		tr.addCard(Ace.of(Spades.i()));
		tr.addCard(Three.of(Spades.i()));
		assertEquals(expected, tr.getHighestCard());
	}
	
	public void testDuplicateTrump() {
	  Trick original = new Trick(Spades.i());
	  Trick clone = original.duplicate();
	  assertEquals(Spades.i(), clone.getTrump());
	  Trick original2 = new Trick(Clubs.i());
	  Trick clone2 = original2.duplicate();
	  assertEquals(Clubs.i(), clone2.getTrump());
	}
	
	public void testGetHighestTrumpMoreThanOneTrump() {
		tr.setTrump(Spades.i());
		tr.addCard(Two.of(Spades.i()));
		Card expected = Four.of(Spades.i());
		tr.addCard(expected);
		tr.addCard(Ace.of(Clubs.i()));
		tr.addCard(Three.of(Diamonds.i()));
		assertEquals(expected, tr.getHighestCard());
	}
	public void testGetHighestTrumpMoreThanOneTrump2() {
		tr.setTrump(Spades.i());
		Card expected = Four.of(Spades.i());
		tr.addCard(expected);
		tr.addCard(Two.of(Spades.i()));
		tr.addCard(Ace.of(Clubs.i()));
		tr.addCard(Three.of(Diamonds.i()));
		assertEquals(expected, tr.getHighestCard());
	}
}
