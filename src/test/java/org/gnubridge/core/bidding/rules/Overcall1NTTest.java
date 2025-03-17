package org.gnubridge.core.bidding.rules;

import static org.gnubridge.core.bidding.Bid.*;

public class Overcall1NTTest extends AbstractBiddingRuleTest<Overcall1NT> {

    public void testMakeOvercallWith16to18HCPBalancedHandAndStopperInEnemySuit() {
        givenBidding(ONE_CLUBS);
        andPlayersCards("K,2", "A,Q,3", "A,8,6,5,3", "K,J,3");
        ruleShouldBid(ONE_NOTRUMP);
    }

    public void testDoNotApplyIfNoOvercall() {
        givenBidding(PASS);
        andPlayersCards("K,2", "A,Q,3", "A,8,6,5,3", "K,J,3");
        ruleShouldBid(null);
    }

    public void testDoNotApplyIfLessThan16HCP() {
        givenBidding(ONE_CLUBS);
        andPlayersCards("7,2", "A,Q,3", "A,8,6,5,3", "K,J,3");
        ruleShouldBid(null);
    }

    public void testDoNotApplyIfMoreThan18HCP() {
        givenBidding(ONE_CLUBS);
        andPlayersCards("A,2", "A,Q,3", "A,K,6,5,3", "K,J,3");
        ruleShouldBid(null);
    }

    public void testDoNotApplyIfImbalanced() {
        givenBidding(ONE_CLUBS);
        andPlayersCards("2", "A,K,Q,3", "A,8,6,5,3", "K,J,3");
        ruleShouldBid(null);
    }

    public void testDoNotApplyIfNoStopperInEnemySuit() {
        givenBidding(ONE_CLUBS);
        andPlayersCards("K,2", "A,Q,3", "A,K,8,6,5,3", "J,10,3");
        ruleShouldBid(null);
    }
}