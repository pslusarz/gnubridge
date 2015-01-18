package org.gnubridge.core;

import junit.framework.TestCase;

import org.gnubridge.core.deck.Ace;
import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Five;
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
		tr.addCard(Four.of(Spades.i()), null);
		tr.addCard(Two.of(Spades.i()), null);
		Card expected = Ace.of(Spades.i());
		tr.addCard(expected, null);
		tr.addCard(Three.of(Spades.i()), null);
		assertEquals(expected, tr.getHighestCard());
	}

	public void testGetHighestUnmatchedColor() {
		tr.addCard(Two.of(Spades.i()), null);
		tr.addCard(Ace.of(Hearts.i()), null);
		tr.addCard(Three.of(Spades.i()), null);
		Card expected = Four.of(Spades.i());
		tr.addCard(expected, null);		
		assertEquals(expected, tr.getHighestCard());
	}

	public void testGetHighestCardTrump() {
		tr.setTrump(Clubs.i());
		tr.addCard(Four.of(Spades.i()), null);
		Card expected = Two.of(Clubs.i());
		tr.addCard(expected, null);
		tr.addCard(Ace.of(Spades.i()), null);
		tr.addCard(Three.of(Spades.i()), null);
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
		tr.addCard(Two.of(Spades.i()), null);
		Card expected = Four.of(Spades.i());
		tr.addCard(expected, null);
		tr.addCard(Ace.of(Clubs.i()), null);
		tr.addCard(Three.of(Diamonds.i()), null);
		assertEquals(expected, tr.getHighestCard());
	}
	public void testGetHighestTrumpMoreThanOneTrump2() {
		tr.setTrump(Spades.i());
		Card expected = Four.of(Spades.i());
		tr.addCard(expected, null);
		tr.addCard(Two.of(Spades.i()), null);
		tr.addCard(Ace.of(Clubs.i()), null);
		tr.addCard(Three.of(Diamonds.i()), null);
		assertEquals(expected, tr.getHighestCard());
	}
	
	public void testWhoPlayedOneCard() {
		Player p = new Player(East.i());
		Card card = Four.of(Spades.i());
		tr.addCard(card, p);
		assertEquals(p, tr.whoPlayed(card));
	}
	public void testWhoPlayedTwoCards() {
		Player p = new Player(East.i());
		Player p2 = new Player(North.i());
		Card card = Four.of(Spades.i());
		Card card2 = Five.of(Spades.i());
		tr.addCard(card, p);
		tr.addCard(card2, p2);
		assertEquals(p, tr.whoPlayed(card));
		assertEquals(p2, tr.whoPlayed(card2));
	}
}
