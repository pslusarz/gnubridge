package org.gnubridge.core.bidding.rules;

import static org.gnubridge.core.bidding.Bid.*;

public class Rebid1ColorWithNTTest extends AbstractBiddingRuleTest<Rebid1ColorWithNT> {

    public void testBalancedAt1Level() {
        givenBidding(ONE_CLUBS, PASS, ONE_DIAMONDS, PASS);
        andPlayersCards("4,3,2", "K,Q,J,2", "9,8", "A,K,5,4");
        ruleShouldBid(ONE_NOTRUMP);
    }

    public void testDoesNotApplyIfNotBalanced() {
        givenBidding(ONE_CLUBS, PASS, ONE_DIAMONDS, PASS);
        andPlayersCards("", "K,Q,J,3,2", "9,8,7", "A,K,5,4,3");
        ruleShouldBid(null);
    }

    public void testDoNotApplyWhenBancedAt16To18() {
        givenBidding(ONE_CLUBS, PASS, ONE_DIAMONDS, PASS);
        andPlayersCards("A,3,2", "K,Q,J,2", "9,8", "A,K,5,4");
        ruleShouldBid(null);
    }

    public void testBidAt2WhenBancedAt19To20() {
        givenBidding(ONE_CLUBS, PASS, ONE_DIAMONDS, PASS);
        andPlayersCards("A,Q,2", "K,Q,J,2", "9,8", "A,K,5,4");
        ruleShouldBid(TWO_NOTRUMP);
    }

    public void testDoNotApplyWhenBancedOver20() {
        givenBidding(ONE_CLUBS, PASS, ONE_DIAMONDS, PASS);
        andPlayersCards("A,Q,2", "K,Q,J,2", "Q,8", "A,K,5,4");
        ruleShouldBid(null);
    }

    public void testResponseIsNTTameWith16To18Points() {
        givenBidding(ONE_CLUBS, PASS, ONE_NOTRUMP, PASS);
        andPlayersCards("A,4,3,2", "K,Q,J,2", "9", "A,K,5,4");
        ruleShouldBid(TWO_NOTRUMP);
    }

    public void testResponseIsNTNotTameWith16To18Points() {
        givenBidding(ONE_CLUBS, PASS, ONE_NOTRUMP, PASS);
        andPlayersCards("A,K,4,3,2", "Q,J", "9", "A,K,5,4,2");
        ruleShouldBid(null);
    }

    public void testResponseIsNTTameWith19Points() {
        givenBidding(ONE_CLUBS, PASS, ONE_NOTRUMP, PASS);
        andPlayersCards("A,Q,3,2", "K,Q,J,2", "9", "A,K,5,4");
        ruleShouldBid(THREE_NOTRUMP);
    }

    public void testResponseIsNTNotTameWith19Points() {
        givenBidding(ONE_CLUBS, PASS, ONE_NOTRUMP, PASS);
        andPlayersCards("A,Q,3,2", "K,Q,J,5,3,2", "9", "A,K");
        ruleShouldBid(null);
    }

    public void testResponseIsNTBalancedWith19Points() {
        givenBidding(ONE_CLUBS, PASS, ONE_NOTRUMP, PASS);
        andPlayersCards("A,Q,3,2", "K,Q,J,2", "9,4", "A,K,5");
        ruleShouldBid(THREE_NOTRUMP);
    }
}