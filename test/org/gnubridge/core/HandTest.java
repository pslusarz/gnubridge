package org.gnubridge.core;

import java.util.List;

import org.gnubridge.core.deck.Ace;
import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.King;
import org.gnubridge.core.deck.Three;
import org.gnubridge.core.deck.Two;

import junit.framework.TestCase;

public class HandTest extends TestCase {
  
  public void testGetColorLength() {
	Hand h = new Hand("", "", "4,3,2", "A,K");
	assertEquals(0, h.getColorLength(Hearts.i()));
	assertEquals(3, h.getColorLength(Diamonds.i()));
  }
  
  public void testGetColor() {
	  Hand h = new Hand(King.of(Hearts.i()), Two.of(Diamonds.i()), Ace.of(Diamonds.i()), Three.of(Clubs.i()), Three.of(Diamonds.i()));
	  List<Card> actual = h.getColorHi2Low(Diamonds.i());
	  assertEquals(3, actual.size());
	  assertEquals(Ace.of(Diamonds.i()), actual.get(0));
	  assertEquals(Three.of(Diamonds.i()), actual.get(1));
	  assertEquals(Two.of(Diamonds.i()), actual.get(2));
  }
}
