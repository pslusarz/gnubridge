package org.gnubridge.core.bidding.rules;

import static org.gnubridge.core.bidding.Bid.ONE_CLUBS;
import static org.gnubridge.core.bidding.Bid.ONE_DIAMONDS;
import static org.gnubridge.core.bidding.Bid.PASS;
import junit.framework.TestCase;

import org.gnubridge.core.Hand;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;

public class OvercallTest extends TestCase {
	public static final Hand ONE_DIAMONDS_OVERCALL_HAND = new Hand("7,8", "4,3", "A,K,J,9,3,2", "Q,5,4");

	public void testOnlyAppliesToTrueOvercalls() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(PASS);
		Overcall rule = new Overcall(a, ONE_DIAMONDS_OVERCALL_HAND);
		assertEquals(null, rule.getBid());

		Auctioneer b = new Auctioneer(West.i());
		b.bid(ONE_CLUBS);
		b.bid(PASS);
		rule = new Overcall(b, ONE_DIAMONDS_OVERCALL_HAND);
		assertEquals(null, rule.getBid());

		Auctioneer c = new Auctioneer(West.i());
		rule = new Overcall(c, ONE_DIAMONDS_OVERCALL_HAND);
		assertEquals(null, rule.getBid());
	}

	public void test1ColorAt10to12() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		Overcall rule = new Overcall(a, ONE_DIAMONDS_OVERCALL_HAND);
		assertEquals(ONE_DIAMONDS, rule.getBid());
	}

}
