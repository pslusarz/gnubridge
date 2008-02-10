package org.gnubridge.core.bidding;

import junit.framework.TestCase;

import org.gnubridge.core.Hand;
import org.gnubridge.core.deck.Ace;
import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.Jack;
import org.gnubridge.core.deck.King;
import org.gnubridge.core.deck.Queen;
import org.gnubridge.core.deck.Spades;
import org.gnubridge.core.deck.Three;
import org.gnubridge.core.deck.Two;

public class PointCalculatorTest extends TestCase {
  public void testHighCardPoints() {
	  PointCalculator dh = new PointCalculator(new Hand(Ace.of(Diamonds.i()), Two.of(Spades.i()), King.of(Hearts.i()), Queen.of(Hearts.i()), Jack.of(Clubs.i())));
	  assertEquals(10, dh.getHighCardPoints());
  }
  
  public void testGetDistributionalPoints() {
	  PointCalculator dh = new PointCalculator(new Hand(Ace.of(Diamonds.i()), Two.of(Spades.i()), King.of(Hearts.i()), Three.of(Spades.i()), Queen.of(Hearts.i()), Jack.of(Hearts.i())));
	  assertEquals(6, dh.getDistributionalPoints());
  }
  
  public void testGetCombinedPointsFlawedColors() {
	  PointCalculator dh = new PointCalculator(new Hand(Ace.of(Diamonds.i()), King.of(Clubs.i()), Jack.of(Spades.i()), Queen.of(Hearts.i()), Three.of(Spades.i()), Jack.of(Hearts.i())));
	  assertEquals((4 + 2) + 3 + 1 + 3, dh.getCombinedPoints());
  }
  
  //A balanced hand contains no singleton or void and at most one doubleton 
  
  public void testBalancedHand() {
	  Hand h = new Hand ("A,K,4,3", "K,J,9,2", "6,2","Q,9,7" );
	  PointCalculator pc = new PointCalculator(h);
	  assertTrue(pc.isBalanced());
  }
  public void testImbalancedHandTwoDoubletons() {
	  Hand h = new Hand ("A,K,4,3", "K,J,9,7,2", "6,2","Q,9" );
	  PointCalculator pc = new PointCalculator(h);
	  assertFalse(pc.isBalanced());
  }
  
  public void testImbalancedHandSingleton() {
	  Hand h = new Hand ("A,K,4,3", "K,J,9,2", "6","Q,9,7,3" );
	  PointCalculator pc = new PointCalculator(h);
	  assertFalse(pc.isBalanced());
  }
  public void testImbalancedHandVoid() {
	  Hand h = new Hand ("A,K,Q,9,7,4,3", "K,J,9,2", "6,2" );
	  PointCalculator pc = new PointCalculator(h);
	  assertFalse(pc.isBalanced());
  }
  
  public void testRP1() {
	  Hand h = RPQuizzes.Basics.Lesson2.hand1(); 
	  PointCalculator pc = new PointCalculator(h);
	  assertEquals(16, pc.getCombinedPoints());
  }
  
  public void testRP2() {
	  Hand h = RPQuizzes.Basics.Lesson2.hand2();
	  PointCalculator pc = new PointCalculator(h);
	  assertEquals(12, pc.getCombinedPoints());
  }
  
  public void testRP3() {
	  Hand h = RPQuizzes.Basics.Lesson2.hand3(); 
	  PointCalculator pc = new PointCalculator(h);
	  assertEquals(14, pc.getCombinedPoints());
  }
  
  public void testRP4() {
	  Hand h = RPQuizzes.Basics.Lesson2.hand4();
	  PointCalculator pc = new PointCalculator(h);
	  assertEquals(15, pc.getCombinedPoints());
  }  
  
  public void testRP5() {
	  Hand h = RPQuizzes.Basics.Lesson2.hand5();
	  PointCalculator pc = new PointCalculator(h);
	  assertEquals(18, pc.getCombinedPoints());
	  assertEquals(17, pc.getHighCardPoints());
  }  
  
  public void testRP6() {
	  Hand h = RPQuizzes.Basics.Lesson2.hand6();
	  PointCalculator pc = new PointCalculator(h);
	  assertEquals(18, pc.getCombinedPoints());
  } 
  
  public void testRP7() {
	  Hand h = RPQuizzes.Basics.Lesson2.hand7();
	  PointCalculator pc = new PointCalculator(h);
	  assertEquals(20, pc.getCombinedPoints());
  }
  
  public void testRP8() {
	  Hand h = RPQuizzes.Basics.Lesson2.hand8();
	  PointCalculator pc = new PointCalculator(h);
	  assertEquals(16, pc.getCombinedPoints());
  } 
  
  public void testRP9() {
	  Hand h = RPQuizzes.Basics.Lesson2.hand9();
	  PointCalculator pc = new PointCalculator(h);
	  assertEquals(19, pc.getCombinedPoints());
  }
  
  public void testRP10() {
	  Hand h = RPQuizzes.Basics.Lesson2.hand10();
	  PointCalculator pc = new PointCalculator(h);
	  assertEquals(13, pc.getCombinedPoints());
  }
  
  public void testRP11() {
	  Hand h = RPQuizzes.Basics.Lesson2.hand11();
	  PointCalculator pc = new PointCalculator(h);
	  assertEquals(17, pc.getHighCardPoints());
	  assertEquals(18, pc.getCombinedPoints());
  }
  
  public void testRP12() {
	  Hand h = RPQuizzes.Basics.Lesson2.hand12();
	  PointCalculator pc = new PointCalculator(h);
	  assertEquals(14, pc.getCombinedPoints());
  } 
}
