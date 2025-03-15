package org.gnubridge.core.bidding;

import static org.gnubridge.core.bidding.Bid.*;

import org.gnubridge.core.Hand;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.rules.BiddingRule;

import java.util.ArrayList;

public class BiddingAgentTest extends BiddingAgentTestCase {

	public void testOpeningOneNT() {
		givenNoPriorBids();
		andPlayersCards("K,2", "A,Q,3", "A,8,6,5,3", "K,J,3");
		expectPlayerToBid(ONE_NOTRUMP);
	}

	public void testMajorSuit1NTResponse() {
		givenBidding(ONE_NOTRUMP, PASS);
		andPlayersCards("9,8,7,6,2", "A,3", "6,5,3", "5,4,3");
		expectPlayerToBid(TWO_SPADES);
	}

	public void testOpenersResponseToMajorSuitResponseTo1NT() {
		givenBidding(ONE_NOTRUMP, PASS, THREE_HEARTS, PASS);
		andPlayersCards("K,2", "A,Q,3", "A,8,6,5,3", "K,J,3");
		expectPlayerToBid(FOUR_HEARTS);
	}

	public void testOpeningOneNTSequence() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent west = new BiddingAgent(a, new Hand("K,2", "A,Q,3", "A,8,6,5,3", "K,J,3"));
		assertEquals(ONE_NOTRUMP, west.getBid());
		a.bid(ONE_NOTRUMP);
		a.bid(PASS);
		BiddingAgent east = new BiddingAgent(a, new Hand("K,8,7,6", "A,3,2", "6,5,3", "Q,4,3"));
		assertEquals(TWO_NOTRUMP, east.getBid());
		a.bid(TWO_NOTRUMP);
		a.bid(PASS);
		assertEquals(THREE_NOTRUMP, west.getBid());
	}

	public void testOpenOneColor5ColorSuit() {
		givenNoPriorBids();
		andPlayersCards("K,2", "A,3", "A,8,6,5,3", "5,4,3");
		expectPlayerToBid(ONE_DIAMONDS);
	}

	public void testRespond1ColorWithNewSuit() {
		givenBidding(ONE_DIAMONDS, PASS);
		andPlayersCards("9,3", "K,5,4,3,2", "9,8", "Q,4,3,2");
		expectPlayerToBid(ONE_HEARTS);
	}

	public void testRespond1ColorRaisesMajorSuit() {
		givenBidding(ONE_SPADES, PASS);
		andPlayersCards("K,3,2", "K,5,4,3", "9,8,6", "5,4,3");
		expectPlayerToBid(TWO_SPADES);
	}

	public void testRespond1ColorRaisesMajorSuitSupercedesNewSuit() {
		givenBidding(ONE_HEARTS, PASS);
		andPlayersCards("K,10,7,6", "A,9,8,3", "A,8,6,4,2", "");
		expectPlayerToBid(THREE_HEARTS);
	}

	public void testRespond1ColorRaisesMinorSuit() {
		givenBidding(ONE_CLUBS, PASS);
		andPlayersCards("K,3,2", "5,4,3", "9,8,6", "K,5,4,3");
		expectPlayerToBid(TWO_CLUBS);
	}

	public void testRespond1ColorBidsNT() {
		//only way to keep form bidding new suit or raising partner was to have opponents bid
		givenBidding(ONE_CLUBS, ONE_DIAMONDS);
		andPlayersCards("K,3,2", "A,J,4", "K,8,6,3", "K,5,4");
		expectPlayerToBid(TWO_NOTRUMP);
	}

	public void testRebidRespondersColor() {
		givenBidding(ONE_CLUBS, PASS, ONE_DIAMONDS, TWO_CLUBS);
		andPlayersCards("J,8,6", "", "K,7,5,2", "A,K,J,10,9,2");
		expectPlayerToBid(THREE_DIAMONDS);
	}

	public void testRebidNewSuit() {
		givenBidding(ONE_CLUBS, PASS, ONE_DIAMONDS, PASS);
		andPlayersCards("J,5,4,2", "8,4", "A,K,9", "K,5,4,3");
		expectPlayerToBid(ONE_SPADES);
	}

	public void testRebidOriginalSuit() {
		givenBidding(ONE_HEARTS, PASS, ONE_SPADES, PASS);
		andPlayersCards("3,2", "A,K,5,4,3,2", "K,Q,J", "K,8");
		expectPlayerToBid(THREE_HEARTS);
	}

	public void testRebid1ColorWithNT() {
		givenBidding(ONE_SPADES, PASS, ONE_NOTRUMP, PASS);
		andPlayersCards("A,Q,4,3", "K,Q,J", "9,3,2", "A,K,5");
		expectPlayerToBid(THREE_NOTRUMP);
	}

	public void testOvercall1ColorWithOwnColor() {
		givenBidding(ONE_CLUBS);
		andPlayersCards("7,8", "4,3", "A,K,J,9,3,2", "Q,5,4");
	}

	public void testRespondToOvercall() {
		givenBidding(ONE_CLUBS, TWO_DIAMONDS, PASS);
		andPlayersCards("10,9,8,7", "K,3,2", "A,J,9", "9,5,4");
		expectPlayerToBid(THREE_DIAMONDS);
	}

	public void testOvercall1NT() {
		givenBidding(ONE_CLUBS);
		andPlayersCards("A,K,2", "A,Q,3", "8,6,5,3", "K,J,3");
		expectPlayerToBid(ONE_NOTRUMP);
	}

	public void testRespondOvercall1NT() {
		givenBidding(ONE_CLUBS, ONE_NOTRUMP, PASS);
		andPlayersCards("9,8,7,6,2", "A,3", "6,5,3", "Q,4,3");
		expectPlayerToBid(TWO_SPADES);
	}

	public void testHaveToBidSomething() {
		givenNoPriorBids();
		andPlayersCards("5,4,3,2", "5,4,3", "6,5,3", "5,4,3");
		expectPlayerToBid(PASS);
	}

	public void testRP2() {
		Auctioneer a = new Auctioneer(West.i());
		BiddingAgent ba = new BiddingAgent(a, RPQuizzes.Basics.Lesson2.hand2());
		assertEquals(PASS, ba.getBid());
	}

	public void testPreferRuleWithHigherScore() {
		Auctioneer a = new Auctioneer(West.i());
		ArrayList<BiddingRule> additionalRules = new ArrayList<>();
		additionalRules.add(always(SIX_NOTRUMP));
		additionalRules.add(always(SEVEN_NOTRUMP));
		additionalRules.add(always(FIVE_NOTRUMP));
		BiddingAgent ba = new BiddingAgent(a, RPQuizzes.Basics.Lesson2.hand2(), additionalRules);
		assertEquals(SEVEN_NOTRUMP, ba.getBid());
	}

	BiddingRule always(Bid bid) {
		Auctioneer dummyAuctioneer = new Auctioneer(West.i());
		Hand dummyHand = RPQuizzes.Basics.Lesson2.hand2();
		return new BiddingRule(dummyAuctioneer, dummyHand) {
			@Override
			protected Bid prepareBid() {
				return bid;
			}

			@Override
			protected boolean applies() {
				return true;
			}
		};
	}

}
