package org.gnubridge.core.bidding.rules;

import static org.gnubridge.core.bidding.Bid.*;
import junit.framework.TestCase;

import org.gnubridge.core.Hand;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;

public class RespondOvercallSuitTest extends TestCase {

	/**
	 *   Raise partners suit
	 */
	public void testBidAt2LevelWith8PointsAnd3Trumps() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("10,9,8,7", "K,3,2", "A,J,9", "9,5,4"));
		assertEquals(TWO_DIAMONDS, rule.getBid());
	}

	public void testRaisePartnerWith8PointsAnd3Trumps() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(TWO_DIAMONDS);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("10,9,8,7", "K,3,2", "A,J,9", "9,5,4"));
		assertEquals(THREE_DIAMONDS, rule.getBid());
	}

	public void testJumpRaisePartnerWith12To14PointsAnd3Trumps() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("A,9,8,7", "K,3,2", "A,J,9", "9,5,4"));
		assertEquals(THREE_DIAMONDS, rule.getBid());
	}

	public void testRaiseToGameWith15PointsAnd3TrumpsMajorSuit() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_HEARTS);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("A,K,8,7", "K,3,2", "A,J,9", "9,5,4"));
		assertEquals(FOUR_HEARTS, rule.getBid());
	}

	public void testRaiseTo4LevelWith15PointsAnd3TrumpsMinorSuit() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("A,K,8,7", "K,3,2", "A,J,9", "9,5,4"));
		assertEquals(FOUR_DIAMONDS, rule.getBid());
	}

	public void testReallyJumpRaisePartnerWith12To14PointsAnd3Trumps() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(TWO_DIAMONDS);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("A,9,8,7", "K,3,2", "A,J,9", "9,5,4"));
		assertEquals(FOUR_DIAMONDS, rule.getBid());
	}

	/**
	 *  bid unbid own suit
	 */

	public void testBidUnbidSuitAtTwoLevelWith8PointsAnd5Trumps() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_DIAMONDS);
		a.bid(TWO_HEARTS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("A,10,9,8,7", "K,3,2", "J,9", "9,5,4"));
		assertEquals(TWO_SPADES, rule.getBid());
	}

	public void testBidUnbidSuitAtCheapestLevelWith8PointsAnd5Trumps() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("A,10,9,8,7", "K,3,2", "J,9", "9,5,4"));
		assertEquals(ONE_SPADES, rule.getBid());
	}

	public void testJumpBidUnbidSuitWith12PointsAnd5Trumps() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("A,10,9,8,7", "K,3,2", "J,9", "A,5,4"));
		assertEquals(TWO_SPADES, rule.getBid());
	}

	public void testJumpHigherBidUnbidSuitWith12PointsAnd5Trumps() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(TWO_DIAMONDS);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("A,10,9,8,7", "K,3,2", "J,9", "A,5,4"));
		assertEquals(THREE_SPADES, rule.getBid());
	}

	public void testBidGameUnbidSuitWith15PointsAnd5Trumps() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("A,K,9,8,7", "K,3,2", "J,9", "A,5,4"));
		assertEquals(FOUR_SPADES, rule.getBid());
	}

	/**
	 *  respond no trump
	 */

	public void testBidNoTrumpOneLevelWith8PointsAndStopperInEnemySuit() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("A,10,9,8", "9,4,3,2", "J,9", "K,5,4"));
		assertEquals(ONE_NOTRUMP, rule.getBid());
	}

	public void testDoNotBidWithoutStopperInEnemySuit() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("A,10,9,8", "K,4,3,2", "J,9", "9,5,4"));
		assertEquals(null, rule.getBid());
	}

	public void testDoNotBidWithoutStopperInBothEnemySuits() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_DIAMONDS);
		a.bid(ONE_HEARTS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("A,K,9,8", "9,4,3,2", "10,9", "J,9,5,4"));
		assertEquals(null, rule.getBid());
	}

	public void testBidWithStoppersInBothEnemySuits() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_DIAMONDS);
		a.bid(ONE_HEARTS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("A,10,9,8", "K,4,3,2", "10,9", "J,9,5,4"));
		assertEquals(ONE_NOTRUMP, rule.getBid());
	}

	public void testBidNoTrumpCheapestLevelWith8PointsAndStopperInEnemySuit() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(TWO_DIAMONDS);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("A,10,9,8", "9,4,3,2", "J,9", "K,5,4"));
		assertEquals(TWO_NOTRUMP, rule.getBid());
	}

	public void testJumpBidNoTrumpWith12PointsAndStopperInEnemySuit() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("A,10,9,8", "A,4,3,2", "J,9", "K,5,4"));
		assertEquals(TWO_NOTRUMP, rule.getBid());
	}

	public void testJumpHigherBidNoTrumpWith12PointsAndStopperInEnemySuit() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(TWO_DIAMONDS);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("A,10,9,8", "A,4,3,2", "J,9", "K,5,4"));
		assertEquals(THREE_NOTRUMP, rule.getBid());
	}

	public void testBidGameNoTrumpWith15PointsAndStopperInEnemySuit() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("A,K,9,8", "A,4,3,2", "J,9", "K,5,4"));
		assertEquals(THREE_NOTRUMP, rule.getBid());
	}

	/**
	 *  border cases when rule should not apply
	 */

	public void testPassWithLessThan8Points() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("7,8", "K,3,2", "Q,J,9,3,2", "9,5,4"));
		assertEquals(null, rule.getBid());
	}

	public void testDoNotRaiseIfDoNotHave3Trumps() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("10,9,8,7", "K,3,2,4", "A,9", "J,9,5"));
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

	public void testOnlyApplyToOvercallResponses() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_DIAMONDS);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("10,9,8,7,6", "", "K,10,9,2", "10,9,5,4"));
		assertEquals(null, rule.getBid());
	}

	public void testOnlyApplyToSuitOvercallsNoTrump() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(ONE_NOTRUMP);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("10,9,8,7", "K,3,2", "A,J,9", "9,5,4"));
		assertEquals(null, rule.getBid());
	}

	public void testOnlyApplyToSuitOvercallsDouble() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(DOUBLE);
		a.bid(PASS);
		RespondOvercallSuit rule = new RespondOvercallSuit(a, new Hand("10,9,8,7", "K,3,2", "A,J,9", "9,5,4"));
		assertEquals(null, rule.getBid());
	}

}
