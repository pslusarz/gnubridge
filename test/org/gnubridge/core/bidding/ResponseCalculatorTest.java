package org.gnubridge.core.bidding;

import org.gnubridge.core.Hand;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.Spades;

import junit.framework.TestCase;

public class ResponseCalculatorTest extends TestCase {
	  public void testSingleton() {
		  Hand h = new Hand ("6,5,4,3", "10,9,8,7,2", "7,6,2","9" );
		  PointCalculator pc = new ResponseCalculator(h, new Bid(1, Spades.i()));
		  assertEquals(3, pc.getCombinedPoints());
	  }
	  public void testOnlyApplyIfHaveAtLeast4InPartnersColor() {
		  Hand h = new Hand ("6,5,4", "10,9,8,7,2", "7,6,3,2","9" );
		  PointCalculator pc = new ResponseCalculator(h, new Bid(1, Spades.i()));
		  assertEquals(2, pc.getCombinedPoints());
	  }
	  public void testVoid() {
		  Hand h = new Hand ("", "10,9,8,7,2", "7,6,2","10,9,8,7,6" );
		  PointCalculator pc = new ResponseCalculator(h, new Bid(1, Hearts.i()));
		  assertEquals(5, pc.getCombinedPoints());
	  }
	  public void testDoNotCountDistributionalPointsInPartnersColor() {
		  Hand h = new Hand ("", "10,9,8,7,2", "7,6,2","10,9,8,7,6" );
		  PointCalculator pc = new ResponseCalculator(h, new Bid(1, Spades.i()));
		  assertEquals(0, pc.getCombinedPoints());
	  }
}
