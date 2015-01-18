package org.gnubridge.core.bidding.rules;

import static org.gnubridge.core.bidding.Bid.*;
import junit.framework.TestCase;

import org.gnubridge.core.Hand;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;

public class Respond1NTTest extends TestCase {
	Auctioneer a;

	@Override
	protected void setUp() {
		a = new Auctioneer(West.i());
		a.bid(ONE_NOTRUMP);
		a.bid(PASS);
	}

	public void testSevenPoint() {
		Respond1NT rule = new Respond1NT(a, new Hand("9,8,7,6,2", "A,3", "6,5,3", "Q,4,3"));
		assertEquals(TWO_SPADES, rule.getBid());
		Respond1NT triangulate = new Respond1NT(a, new Hand("A,3", "9,8,7,6,2", "6,5,3", "Q,4,3"));
		assertEquals(TWO_HEARTS, triangulate.getBid());
	}

	public void testTenPoint() {
		Respond1NT rule = new Respond1NT(a, new Hand("9,8,7,6,2", "A,3", "K,5,3", "Q,4,3"));
		assertEquals(THREE_SPADES, rule.getBid());
		Respond1NT triangulate = new Respond1NT(a, new Hand("A,3", "9,8,7,6,2", "K,5,3", "Q,4,3"));
		assertEquals(THREE_HEARTS, triangulate.getBid());
	}

	public void testDoNotFireWhenNotRespondingTo1NT() {
		Auctioneer not1NT = new Auctioneer(West.i());
		Respond1NT rule = new Respond1NT(not1NT, new Hand("9,8,7,6,2", "A,3", "6,5,3", "Q,4,3"));
		assertNull(rule.getBid());
	}

	public void testFourOfColor() {
		Respond1NT rule = new Respond1NT(a, new Hand("10,9,8,7,6,2", "A,3", "K,5,3", "Q,4,3"));
		assertEquals(FOUR_SPADES, rule.getBid());
		Respond1NT triangulate = new Respond1NT(a, new Hand("A,3", "10,9,8,7,6,2", "K,5,3", "Q,4,3"));
		assertEquals(FOUR_HEARTS, triangulate.getBid());
	}

	public void testSevenPointUnder5CardsMajor() {
		Respond1NT rule = new Respond1NT(a, new Hand("9,8,7,6", "A,3,2", "6,5,3", "K,4,3"));
		assertEquals(PASS, rule.getBid());
	}

	public void test8To9PointUnder5CardsMajor() {
		Respond1NT rule = new Respond1NT(a, new Hand("K,8,7,6", "A,3,2", "6,5,3", "Q,4,3"));
		assertEquals(TWO_NOTRUMP, rule.getBid());
	}

	public void testUpTo14PointsUnder5CardsMajor() {
		Respond1NT rule = new Respond1NT(a, new Hand("K,8,7,6", "A,3,2", "A,J,3", "Q,4,3"));
		assertEquals(THREE_NOTRUMP, rule.getBid());
	}

	public void testAlsoAppliesToOvercalls() {
		a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_NOTRUMP);
		a.bid(PASS);
		Respond1NT rule = new Respond1NT(a, new Hand("9,8,7,6,2", "A,3", "6,5,3", "Q,4,3"));
		assertEquals(TWO_SPADES, rule.getBid());
		Respond1NT triangulate = new Respond1NT(a, new Hand("A,3", "9,8,7,6,2", "6,5,3", "Q,4,3"));
		assertEquals(TWO_HEARTS, triangulate.getBid());
	}

}
