package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.bidding.Bid;

import static org.gnubridge.core.bidding.Bid.*;

public class Rebid1ColorWithNewSuitTest extends AbstractBiddingRuleTest<Rebid1ColorWithNewSuit> {

    public void testShowUnbidSuitAt1Level() {
        givenBidding(ONE_CLUBS, PASS, ONE_DIAMONDS, PASS);
        andPlayersCards("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3");
        ruleShouldBid(ONE_HEARTS);
    }

    public void testNoUnbidSuitWith4Cards() {
        givenBidding(ONE_CLUBS, PASS, ONE_DIAMONDS, PASS);
        andPlayersCards("3,2", "9,8", "K,Q,J,2", "A,K,5,4,3");
        ruleShouldBid(null);
    }

    public void testShowUnbidSuitAt1LevelAnotherColor() {
        givenBidding(ONE_CLUBS, PASS, ONE_DIAMONDS, PASS);
        andPlayersCards("K,Q,J,2", "3,2", "9,8", "A,K,5,4,3");
        ruleShouldBid(ONE_SPADES);
    }

    public void testShowUnbidSuitEvenThoughRespondersSuitIsStronger() {
        givenBidding(ONE_CLUBS, PASS, ONE_DIAMONDS, PASS);
        andPlayersCards("J,5,4,2", "", "A,K,9,8", "A,K,5,4,3");
        ruleShouldBid(ONE_SPADES);
    }

    public void testLowestBidIsAt2LevelReverseBid() {
        givenBidding(ONE_CLUBS, PASS, ONE_SPADES, PASS);
        andPlayersCards("3,2", "A,K,Q,2", "9,8", "A,K,5,4,3");
        ruleShouldBid(TWO_HEARTS);
    }

    public void test1LevelIsNotAReverseBid() {
        givenBidding(ONE_DIAMONDS, PASS, ONE_HEARTS, PASS);
        andPlayersCards("K,Q,J,2", "9,8", "K,Q,J,3,2", "J,2");
        ruleShouldBid(ONE_SPADES);
    }

    public void testNeedAtLeast16ForReverseBid() {
        givenBidding(ONE_DIAMONDS, PASS, TWO_CLUBS, PASS);
        andPlayersCards("K,Q,J,2", "9,8", "K,Q,J,3,2", "J,2");
        ruleShouldBid(null);
    }

    public void testLowerRankedSuitThanOriginalIsNotAReverseBid() {
        givenBidding(ONE_HEARTS, PASS, ONE_SPADES, PASS);
        andPlayersCards("9,8", "K,Q,J,2", "K,Q,J,3,2", "J,2");
        ruleShouldBid(TWO_DIAMONDS);
    }

    public void testJumpShiftTo2LevelAt19Points() {
        givenBidding(ONE_CLUBS, PASS, ONE_DIAMONDS, PASS);
        andPlayersCards("K,Q,J,2", "A,2", "Q,8", "A,K,5,4,3");
        Bid expectedBid = ruleShouldBid(TWO_SPADES);
        assertTrue(expectedBid.isGameForcing());
    }

    public void testLowestBidIsAt2LevelDoNotApplyToBalancedHand() {
        givenBidding(ONE_CLUBS, PASS, ONE_SPADES, PASS);
        andPlayersCards("3,2", "K,Q,J,2", "9,8,3", "A,K,5,4");
        ruleShouldBid(null);
    }
}