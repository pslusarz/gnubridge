package org.gnubridge.core.bidding.rules;

import junit.framework.TestCase;

import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.Pass;
import org.gnubridge.core.deck.NoTrump;

public class BiddingRuleTest extends TestCase {
  public void testDoNotBidIfSubclassRecommendsBidBelowCurrentHigh() {
	Auctioneer a = new Auctioneer(West.i());
	a.bid(new Bid(2, NoTrump.i()));
	BiddingRule always1NT = new BiddingRule(a, null) {
		  protected Bid prepareBid() {
			  return new Bid(1, NoTrump.i());
		  }
	  };
	assertNull(always1NT.getBid());
  }
  public void testDoNotBidIfSubclassRecommendsBidEqualCurrentHigh() {
	  Auctioneer a = new Auctioneer(West.i());
	  a.bid(new Bid(1, NoTrump.i()));
	  BiddingRule always1NT = new BiddingRule(a, null) {
		  protected Bid prepareBid() {
			  return new Bid(1, NoTrump.i());
		  }
	  };
	  assertNull(always1NT.getBid());
  }
  public void testAllowSubclassToRecommendPass() {
	  Auctioneer a = new Auctioneer(West.i());
	  a.bid(new Bid(1, NoTrump.i()));
	  BiddingRule alwaysPass = new BiddingRule(a, null) {
		  protected Bid prepareBid() {
			  return new Pass();
		  }
	  };
	  assertEquals(new Pass(), alwaysPass.getBid());
  }
}
