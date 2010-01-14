package org.gnubridge.core.bidding.rules;

import static org.gnubridge.core.bidding.Bid.*;
import junit.framework.TestCase;

import org.gnubridge.core.Hand;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;

public class RespondOvercallSuitTest extends TestCase {
	public void testPassWithLessThan8Points() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("7,8", "K,3,2", "Q,J,9,3,2", "9,5,4"));
		assertEquals(null, rule.getBid());
	}

	public void testRaisePartnerWith8PointsAnd3Trumps() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("10,9,8,7", "K,3,2", "A,J,9", "9,5,4"));
		assertEquals(TWO_DIAMONDS, rule.getBid());
	}

	public void testDoNotRaiseIfDoNotHave3Trumps() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("10,9,8,7", "K,3,2", "A,9", "J,9,5,4"));
		assertEquals(null, rule.getBid());
	}

	public void testCountPointsAsDummy() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("10,9,8,7,6", "", "K,10,9,2", "10,9,5,4"));
		assertEquals(TWO_DIAMONDS, rule.getBid());
	}

	//TODO: PAUL - next test to pass, but first fix auctioneer test	
	//	public void testOnlyApplyToOvercallResponses() {
	//		Auctioneer a = new Auctioneer(West.i());
	//		a.bid(ONE_DIAMONDS);
	//		a.bid(PASS);
	//		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("10,9,8,7,6", "", "K,10,9,2", "10,9,5,4"));
	//		assertEquals(null, rule.getBid());
	//	}

	// interference from opponents - 3rd bid is not a pass

	//do not blow up if partner bid notrump

	//do not blow up if partner bid double
}
