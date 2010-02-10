package org.gnubridge.core.bidding.rules;

import static org.gnubridge.core.bidding.Bid.*;
import junit.framework.TestCase;

import org.gnubridge.core.Hand;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.deck.NoTrump;

public class RebidTest extends TestCase {
	public void testOnlyApplyToRebids() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_HEARTS);
		a.bid(PASS);
		Rebid rule = new Rebid(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3")) {
			@Override
			protected Bid prepareBid() {
				throw new RuntimeException("should not try to prepare bid when it's not a rebid situation");
			}

		};
		assertEquals(null, rule.getBid());
	}

	public void testPrepareBidInRebidSituation() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(PASS);
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);

		Rebid rule = new Rebid(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3")) {
			@Override
			protected Bid prepareBid() {
				return new Bid(7, NoTrump.i());
			}

		};
		assertEquals(new Bid(7, NoTrump.i()), rule.getBid());
	}

	public void testPrepareBidInRebidSituationOpponent1Doubles() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(DOUBLE);
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);

		Rebid rule = new Rebid(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3")) {
			@Override
			protected Bid prepareBid() {
				return new Bid(7, NoTrump.i());
			}

		};
		assertEquals(new Bid(7, NoTrump.i()), rule.getBid());
	}

	public void testPrepareBidInRebidSituationOpponent2Doubles() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(PASS);
		a.bid(ONE_DIAMONDS);
		a.bid(DOUBLE);

		Rebid rule = new Rebid(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3")) {
			@Override
			protected Bid prepareBid() {
				return new Bid(7, NoTrump.i());
			}

		};
		assertEquals(new Bid(7, NoTrump.i()), rule.getBid());
	}

	public void testPrepareBidInRebidSituation2() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(PASS);
		a.bid(ONE_CLUBS);
		a.bid(PASS);
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);

		Rebid rule = new Rebid(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3")) {
			@Override
			protected Bid prepareBid() {
				return new Bid(7, NoTrump.i());
			}

		};
		assertEquals(new Bid(7, NoTrump.i()), rule.getBid());
	}

	public void testPrepareBidInRebidSituation3() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(PASS);
		a.bid(PASS);
		a.bid(ONE_CLUBS);
		a.bid(PASS);
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);

		Rebid rule = new Rebid(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3")) {
			@Override
			protected Bid prepareBid() {
				return new Bid(7, NoTrump.i());
			}

		};
		assertEquals(new Bid(7, NoTrump.i()), rule.getBid());
	}

	public void testPrepareBidInRebidSituation4() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(PASS);
		a.bid(PASS);
		a.bid(PASS);
		a.bid(ONE_CLUBS);
		a.bid(PASS);
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);

		Rebid rule = new Rebid(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3")) {
			@Override
			protected Bid prepareBid() {
				return new Bid(7, NoTrump.i());
			}

		};
		assertEquals(new Bid(7, NoTrump.i()), rule.getBid());
	}

	public void testDoNotThrowNPEPartnerPass() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(PASS);
		a.bid(PASS);
		Rebid rule = new Rebid(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3")) {
			@Override
			protected Bid prepareBid() {
				return null;
			}

		};
		assertEquals(null, rule.getBid());
	}

	public void testDoNotThrowNPEPartnerDidNotBid() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(PASS);
		Rebid rule = new Rebid(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3")) {
			@Override
			protected Bid prepareBid() {
				return null;
			}

		};
		assertEquals(null, rule.getBid());
	}

	public void testDoNotApplyWhenPartnerDoubled() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(PASS);
		a.bid(DOUBLE);
		a.bid(PASS);
		Rebid rule = new Rebid(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3")) {
			@Override
			protected Bid prepareBid() {
				throw new RuntimeException("should not try to prepare bid when it's not a rebid situation");
			}

		};
		assertEquals(null, rule.getBid());
	}

	public void testDoNotApplyWhenIDoubled() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(DOUBLE);
		a.bid(PASS);
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);
		Rebid rule = new Rebid(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3")) {
			@Override
			protected Bid prepareBid() {
				throw new RuntimeException("should not try to prepare bid when it's not a rebid situation");
			}

		};
		assertEquals(null, rule.getBid());
	}

	public void testDoNotApplyWhenIOvercalled() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);
		a.bid(ONE_HEARTS);
		a.bid(PASS);
		Rebid rule = new Rebid(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3")) {
			@Override
			protected Bid prepareBid() {
				throw new RuntimeException("should not try to prepare bid when it's not a rebid situation");
			}

		};
		assertEquals(null, rule.getBid());
	}
}
