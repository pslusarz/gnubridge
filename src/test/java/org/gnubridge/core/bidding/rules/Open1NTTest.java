package org.gnubridge.core.bidding.rules;

import static org.gnubridge.core.bidding.Bid.*;
import junit.framework.TestCase;

import org.gnubridge.core.Hand;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.RPQuizzes;
import org.gnubridge.core.deck.Clubs;

public class Open1NTTest extends TestCase {
	public void testOpeningOneNTFirstCall() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingRule rule = new Open1NT(a, new Hand("K,2", "A,Q,3", "A,8,6,5,3", "K,J,3"));
		assertEquals(ONE_NOTRUMP, rule.getBid());
	}

	public void testDoNotOpenOneNTWhenResponding() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(PASS);
		BiddingRule rule = new Open1NT(a, new Hand("K,2", "A,Q,3", "A,8,6,5,3", "K,J,3"));
		assertEquals(null, rule.getBid());
	}

	public void testOpeningOneNTSecondCall() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(PASS);
		BiddingRule rule = new Open1NT(a, new Hand("K,2", "A,Q,3", "A,8,6,5,3", "K,J,3"));
		assertEquals(ONE_NOTRUMP, rule.getBid());
	}

	public void testCannotOpenOneNTIfInsufficientHCP() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingRule rule = new Open1NT(a, new Hand("K,2", "A,3", "A,8,6,5,3", "K,J,3"));
		assertEquals(null, rule.getBid());
	}

	public void testDoNotOpenOneNTOnImbalancedHand() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingRule rule = new Open1NT(a, new Hand("K", "A,Q,3", "A,8,6,5,3", "K,J,3,2"));
		assertEquals(null, rule.getBid());
	}

	public void testCannotOpenOneNTIfHigherBid() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(2, Clubs.i()));
		BiddingRule rule = new Open1NT(a, new Hand("K,2", "A,Q,3", "A,8,6,5,3", "K,J,3"));
		assertEquals(null, rule.getBid());
	}

	public void testRP1() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingRule rule = new Open1NT(a, RPQuizzes.Basics.Lesson2.hand1());
		assertEquals(ONE_NOTRUMP, rule.getBid());
	}

	public void testRP5() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingRule rule = new Open1NT(a, RPQuizzes.Basics.Lesson2.hand5());
		assertEquals(ONE_NOTRUMP, rule.getBid());
	}

	public void testRP11() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingRule rule = new Open1NT(a, RPQuizzes.Basics.Lesson2.hand11());
		assertEquals(ONE_NOTRUMP, rule.getBid());
	}

}
