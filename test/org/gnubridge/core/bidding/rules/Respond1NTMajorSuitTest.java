package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.BiddingAgent;
import org.gnubridge.core.bidding.Pass;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Spades;

import junit.framework.TestCase;

public class Respond1NTMajorSuitTest extends TestCase {
	Auctioneer a;	
  protected void setUp() {
	    a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
  }
	
  public void testSevenPoint() {
	  Respond1NTMajorSuit rule = new Respond1NTMajorSuit(a, new Hand("9,8,7,6,2", "A,3",
				"6,5,3", "Q,4,3"));
	  assertEquals(new Bid(2, Spades.i()), rule.getBid());
	  Respond1NTMajorSuit triangulate = new Respond1NTMajorSuit(a, new Hand("A,3", "9,8,7,6,2", 
			  "6,5,3", "Q,4,3"));
	  assertEquals(new Bid(2, Hearts.i()), triangulate.getBid());
  }
  
  public void testSevenPointColorTooShort() {
	  Respond1NTMajorSuit rule = new Respond1NTMajorSuit(a, new Hand("9,8,7,6", "A,3",
			  "7,6,5,3,2", "Q,4,3"));
	  assertNull(rule.getBid());
  } 
  
  public void testTenPoint() {
	  Respond1NTMajorSuit rule = new Respond1NTMajorSuit(a, new Hand("9,8,7,6,2", "A,3",
			  "K,5,3", "Q,4,3"));
	  assertEquals(new Bid(3, Spades.i()), rule.getBid());
	  Respond1NTMajorSuit triangulate = new Respond1NTMajorSuit(a, new Hand("A,3", "9,8,7,6,2", 
			  "K,5,3", "Q,4,3"));
	  assertEquals(new Bid(3, Hearts.i()), triangulate.getBid());
  }
  
  public void testTenPointColorTooShort() {
	  Respond1NTMajorSuit rule = new Respond1NTMajorSuit(a, new Hand("9,8,7,6", "A,3",
			  "K,6,5,3,2", "Q,4,3"));
	  assertNull(rule.getBid());
  }
  
  public void testDoNotFireWhenNotRespondingTo1NT() {
	  Auctioneer not1NT = new Auctioneer(West.i());
	  Respond1NTMajorSuit rule = new Respond1NTMajorSuit(not1NT, new Hand("9,8,7,6,2", "A,3",
				"6,5,3", "Q,4,3"));
	  assertNull(rule.getBid());
  }
  
}
