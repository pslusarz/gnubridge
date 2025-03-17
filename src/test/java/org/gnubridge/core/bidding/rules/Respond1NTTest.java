package org.gnubridge.core.bidding.rules;

import static org.gnubridge.core.bidding.Bid.*;

public class Respond1NTTest extends AbstractBiddingRuleTest<Respond1NT> {

    public void testSevenPointSpades() {
        givenBidding(ONE_NOTRUMP, PASS);
        andPlayersCards("9,8,7,6,2", "A,3", "6,5,3", "Q,4,3");
        ruleShouldBid(TWO_SPADES);
    }

    public void testSevenPointHearts() {
        givenBidding(ONE_NOTRUMP, PASS);
        andPlayersCards("A,3", "9,8,7,6,2", "6,5,3", "Q,4,3");
        ruleShouldBid(TWO_HEARTS);
    }

    public void testTenPointSpades() {
        givenBidding(ONE_NOTRUMP, PASS);
        andPlayersCards("9,8,7,6,2", "A,3", "K,5,3", "Q,4,3");
        ruleShouldBid(THREE_SPADES);
    }

    public void testTenPointHearts() {
        givenBidding(ONE_NOTRUMP, PASS);
        andPlayersCards("A,3", "9,8,7,6,2", "K,5,3", "Q,4,3");
        ruleShouldBid(THREE_HEARTS);
    }

    public void testDoNotFireWhenNotRespondingTo1NT() {
        givenBidding();
        andPlayersCards("9,8,7,6,2", "A,3", "6,5,3", "Q,4,3");
        ruleShouldBid(null);
    }

    public void testFourOfSpades() {
        givenBidding(ONE_NOTRUMP, PASS);
        andPlayersCards("10,9,8,7,6,2", "A,3", "K,5,3", "Q,4,3");
        ruleShouldBid(FOUR_SPADES);
    }

    public void testFourOfHearts() {
        givenBidding(ONE_NOTRUMP, PASS);
        andPlayersCards("A,3", "10,9,8,7,6,2", "K,5,3", "Q,4,3");
        ruleShouldBid(FOUR_HEARTS);
    }

    public void testSevenPointUnder5CardsMajor() {
        givenBidding(ONE_NOTRUMP, PASS);
        andPlayersCards("9,8,7,6", "A,3,2", "6,5,3", "K,4,3");
        ruleShouldBid(PASS);
    }

    public void test8To9PointUnder5CardsMajor() {
        givenBidding(ONE_NOTRUMP, PASS);
        andPlayersCards("K,8,7,6", "A,3,2", "6,5,3", "Q,4,3");
        ruleShouldBid(TWO_NOTRUMP);
    }

    public void testUpTo14PointsUnder5CardsMajor() {
        givenBidding(ONE_NOTRUMP, PASS);
        andPlayersCards("K,8,7,6", "A,3,2", "A,J,3", "Q,4,3");
        ruleShouldBid(THREE_NOTRUMP);
    }

    public void testAlsoAppliesToOvercalls() {
        givenBidding(ONE_CLUBS, ONE_NOTRUMP, PASS);
        andPlayersCards("9,8,7,6,2", "A,3", "6,5,3", "Q,4,3");
        ruleShouldBid(TWO_SPADES);
    }

    public void testAlsoAppliesToOvercallsWithHearts() {
        givenBidding(ONE_CLUBS, ONE_NOTRUMP, PASS);
        andPlayersCards("A,3", "9,8,7,6,2", "6,5,3", "Q,4,3");
        ruleShouldBid(TWO_HEARTS);
    }
}