package org.gnubridge.core.bidding.rules;

import static org.gnubridge.core.bidding.Bid.*;
import junit.framework.TestCase;

import org.gnubridge.core.Hand;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;

public class OvercallSuitTest extends TestCase {
	public static final Hand ONE_DIAMONDS_OVERCALL_HAND = new Hand("7,8", "4,3", "A,K,J,9,3,2", "Q,5,4");
	public static final Hand ONE_HEARTS_OVERCALL_HAND = new Hand("7,8", "A,K,J,9,3,2", "4,3", "Q,5,4");

	public void testOnlyAppliesToTrueOvercalls() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(PASS);
		OvercallSuit rule = new OvercallSuit(a, ONE_DIAMONDS_OVERCALL_HAND);
		assertEquals(null, rule.getBid());

		Auctioneer b = new Auctioneer(West.i());
		b.bid(ONE_CLUBS);
		b.bid(PASS);
		rule = new OvercallSuit(b, ONE_DIAMONDS_OVERCALL_HAND);
		assertEquals(null, rule.getBid());

		Auctioneer c = new Auctioneer(West.i());
		rule = new OvercallSuit(c, ONE_DIAMONDS_OVERCALL_HAND);
		assertEquals(null, rule.getBid());
	}

	public void test1ColorAt10to12SuitOf6() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		OvercallSuit rule = new OvercallSuit(a, ONE_DIAMONDS_OVERCALL_HAND);
		assertEquals(ONE_DIAMONDS, rule.getBid());
	}

	public void test1ColorAt10to12DecentSuitOf5() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		OvercallSuit rule = new OvercallSuit(a, new Hand("7,8", "K,3,2", "Q,J,9,3,2", "A,5,4"));
		assertEquals(ONE_DIAMONDS, rule.getBid());
	}

	public void testGoodSuitOf5ButLessThan10Points() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		OvercallSuit rule = new OvercallSuit(a, new Hand("7,8", "4,3,2", "A,K,J,9,3", "6,5,4"));
		assertEquals(null, rule.getBid());
	}

	public void test1ColorAt10to12PoorSuitOf5() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		OvercallSuit rule = new OvercallSuit(a, new Hand("7,8", "A,3,2", "K,10,9,3,2", "Q,J,4"));
		assertEquals(null, rule.getBid());
	}

	public void test1DifferentColorAt10to12() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		OvercallSuit rule = new OvercallSuit(a, ONE_HEARTS_OVERCALL_HAND);
		assertEquals(ONE_HEARTS, rule.getBid());
	}

	public void testTwoPassesPrecedeRightHandOpponentsOpening() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(PASS);
		a.bid(PASS);
		a.bid(ONE_CLUBS);
		OvercallSuit rule = new OvercallSuit(a, ONE_DIAMONDS_OVERCALL_HAND);
		assertEquals(ONE_DIAMONDS, rule.getBid());
	}

	public void testOnePassPrecedesRightHandOpponentsOpening() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(PASS);
		a.bid(ONE_CLUBS);
		OvercallSuit rule = new OvercallSuit(a, ONE_DIAMONDS_OVERCALL_HAND);
		assertEquals(ONE_DIAMONDS, rule.getBid());
	}

	public void testPickAMoreViableColor() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_DIAMONDS);
		OvercallSuit rule = new OvercallSuit(a, new Hand("2", "A,K,J,9,3", "", "10,9,8,7,6,3,2"));
		assertEquals(ONE_HEARTS, rule.getBid());
	}

	public void test13PointsCanBidOnPoor5() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		OvercallSuit rule = new OvercallSuit(a, new Hand("7,8", "A,K,2", "K,J,9,3,2", "Q,5,4"));
		assertEquals(ONE_DIAMONDS, rule.getBid());
	}

	public void test13PointsCanBidOnPoor5PickMoreViableColor() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_DIAMONDS);
		OvercallSuit rule = new OvercallSuit(a, new Hand("A,10,7,8,2", "", "K,J,9,3,2", "Q,5,4"));
		assertEquals(ONE_SPADES, rule.getBid());
	}

	public void test13PointsCanBid2LevelIfNecessary() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_SPADES);
		OvercallSuit rule = new OvercallSuit(a, new Hand("7,8", "A,K", "K,J,9,4,3,2", "Q,5,4"));
		assertEquals(TWO_DIAMONDS, rule.getBid());
	}

	public void test13PointsCanBid2LevelForGood5() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_SPADES);
		OvercallSuit rule = new OvercallSuit(a, new Hand("7,8,2", "A,K", "K,Q,10,4,3", "Q,5,4"));
		assertEquals(TWO_DIAMONDS, rule.getBid());
	}

	public void test13PointsCanotBid2LevelForDecent5() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_SPADES);
		OvercallSuit rule = new OvercallSuit(a, new Hand("7,8,2", "A,K", "Q,J,10,4,3", "K,5,4"));
		assertEquals(null, rule.getBid());
	}

	public void test16PointsCanBid2LevelForAny5LongSuit() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_SPADES);
		OvercallSuit rule = new OvercallSuit(a, new Hand("K,8,2", "A,K", "K,J,9,4,3", "Q,5,4"));
		assertEquals(TWO_DIAMONDS, rule.getBid());
	}

	public void test16PointsBidAtLowestLevelPossibleAny5LongSuit() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		OvercallSuit rule = new OvercallSuit(a, new Hand("K,8,2", "A,K", "K,J,9,4,3", "Q,5,4"));
		assertEquals(ONE_DIAMONDS, rule.getBid());
	}

}
