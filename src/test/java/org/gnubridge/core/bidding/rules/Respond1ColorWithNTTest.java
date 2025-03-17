package org.gnubridge.core.bidding.rules;

import static org.gnubridge.core.bidding.Bid.*;

public class Respond1ColorWithNTTest extends AbstractBiddingRuleTest<Respond1ColorWithNT> {

    public void test1NTIfHave6Points() {
        givenBidding(ONE_HEARTS, PASS);
        andPlayersCards("K,3,2", "5,4,3", "K,9,8,6", "5,4,3");
        ruleShouldBid(ONE_NOTRUMP);
    }

    public void test1NTIfHave10Points() {
        givenBidding(ONE_CLUBS, PASS);
        andPlayersCards("K,3,2", "K,J,4,3", "K,9,8,6", "5,4,3");
        ruleShouldBid(ONE_NOTRUMP);
    }

    public void testdoesNotApplyAt11Points() {
        givenBidding(ONE_CLUBS, PASS);
        andPlayersCards("K,J,3,2", "K,J,4,3", "K,9,8,6", "5,4,3");
        ruleShouldBid(null);
    }

    public void testDoesNotApplyBelow6() {
        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("K,3,2", "5,4,3", "Q,9,8,6", "5,4,3");
        ruleShouldBid(null);
    }

    public void testDoNotApply_1_3_5_Calculator() {
        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("10,5,4,3,2", "", "J,9,8,6", "5,4,3,2");
        ruleShouldBid(null);
    }

    public void testDoNotCountDistributionalPoints() {
        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("10,5,4,3,2", "", "K,9,8,6", "5,4,3,2");
        ruleShouldBid(null);
    }

    public void testRaise2WhenOver12AndBalanced() {
        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("10,5,4", "A,K", "K,9,8,6", "K,4,3,2");
        ruleShouldBid(TWO_NOTRUMP);
    }

    public void testDoNotRaise2WhenUnbalanced() {
        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("K,10,5,4", "A", "K,9,8,6", "K,4,3,2");
        ruleShouldBid(null);
    }

    public void testRaise3WhenOver16() {
        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("K,10,5", "A,K", "K,9,8,6", "K,J,3,2");
        ruleShouldBid(THREE_NOTRUMP);
    }

    public void testDoNotRaise3WhenOver18() {
        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("K,10,5", "A,K", "K,9,8,6", "K,Q,J,2");
        ruleShouldBid(null);
    }
}