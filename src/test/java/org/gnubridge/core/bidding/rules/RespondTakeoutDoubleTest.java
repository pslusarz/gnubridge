package org.gnubridge.core.bidding.rules;

import junit.framework.TestCase;

import static org.gnubridge.core.bidding.Bid.*;

public class RespondTakeoutDoubleTest extends AbstractBiddingRuleTest<RespondTakeoutDouble> {
    public void testDoesNotApplyWhenDoubleIsNotTakeout() {
        givenBidding(ONE_CLUBS, DOUBLE, PASS, TWO_CLUBS, DOUBLE, PASS);
        andPlayersCards("A,K,10", "10,9, 8, 7", "5, 4, 3, 2", "10,8");
        ruleShouldBid(null);
    }
    public void testDoesNotApplyWhenOpponentResponderDidNotPass() {
        givenBidding(ONE_CLUBS, DOUBLE, ONE_DIAMONDS);
        andPlayersCards("A,K,10", "10,9, 8, 7", "5, 4, 3, 2", "10,8");
        ruleShouldBid(null);
    }

    public void testBidCheapestLongestSuitUnder10Points() {
        givenBidding(ONE_CLUBS, DOUBLE, PASS);
        andPlayersCards("3,2", "9,8,7,6", "9,8,7,6,5", "8,7");
        ruleShouldBid(ONE_DIAMONDS);
    }

    public void testDoNotBidOpponentTrump() {
        givenBidding(ONE_DIAMONDS, DOUBLE, PASS);
        andPlayersCards("3,2", "9,8,7,6", "9,8,7,6,5", "8,7");
        ruleShouldBid(ONE_HEARTS);
    }

    public void testHaveToBidAt2LevelEvenWithUnder10Points() {
        givenBidding(ONE_SPADES, DOUBLE, PASS);
        andPlayersCards("3,2", "9,8,7,6", "9,8,7,6,5", "8,7");
        ruleShouldBid(TWO_DIAMONDS);
    }

    public void testPreferMajorSuit() {
        givenBidding(ONE_SPADES, DOUBLE, PASS);
        andPlayersCards("3,2", "9,8,7,6,5", "9,8,7,6,5", "8");
        ruleShouldBid(TWO_HEARTS);
    }

    public void testJumpSuitOver9Points() {
        givenBidding(ONE_CLUBS, DOUBLE, PASS);
        andPlayersCards("3,2", "J,8,7,6", "A,K,Q,6,5", "8,7");
        ruleShouldBid(TWO_DIAMONDS);
    }

    public void testBidGameAt13Points() {
        givenBidding(ONE_CLUBS, DOUBLE, PASS);
        andPlayersCards("3", "A,Q,8,7,6", "A,K,10,6,5", "8,7");
        ruleShouldBid(FOUR_HEARTS);
    }


}
