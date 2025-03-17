package org.gnubridge.core.bidding.rules;

import static org.gnubridge.core.bidding.Bid.*;

public class Rebid1NTTest extends AbstractBiddingRuleTest<Rebid1NT> {

    public void testRaiseTo4IfAtLeast3CardsInColor() {
        givenBidding(ONE_NOTRUMP, PASS, THREE_HEARTS, PASS);
        andPlayersCards("K,2", "A,3,2", "A,Q,8,6", "K,J,5,3");
        ruleShouldBid(FOUR_HEARTS);

        givenBidding(ONE_NOTRUMP, PASS, THREE_SPADES, PASS);
        andPlayersCards("A,Q,3", "K,2", "A,8,6,3", "K,J,5,3");
        ruleShouldBid(FOUR_SPADES);
    }

    public void testRaiseTo3NTIfDoubletonInColor() {
        givenBidding(ONE_NOTRUMP, PASS, THREE_HEARTS, PASS);
        andPlayersCards("K,3,2", "A,3", "A,Q,8,6", "K,J,5,3");
        ruleShouldBid(THREE_NOTRUMP);
    }

    public void testDoNotRespondIfPartnerDidNotCallAMajorColor() {
        givenBidding(ONE_NOTRUMP, PASS, THREE_DIAMONDS, PASS);
        andPlayersCards("K,3,2", "A,3", "A,Q,8,6", "K,J,5,3");
        ruleShouldBid(null);
    }

    public void testPassIfPartnerCalled2InAMajorColor() {
        givenBidding(ONE_NOTRUMP, PASS, TWO_SPADES, PASS);
        andPlayersCards("K,3,2", "A,3", "A,Q,8,6", "K,J,5,3");
        ruleShouldBid(PASS);
    }

    public void test2NTInvitational16HCP() {
        givenBidding(ONE_NOTRUMP, PASS, TWO_NOTRUMP, PASS);
        andPlayersCards("K,3,2", "A,3", "A,J,8,6", "K,J,5,3");
        ruleShouldBid(PASS);
    }

    public void test2NTInvitational18HCP() {
        givenBidding(ONE_NOTRUMP, PASS, TWO_NOTRUMP, PASS);
        andPlayersCards("K,3,2", "A,3", "A,K,8,6", "K,J,5,3");
        ruleShouldBid(THREE_NOTRUMP);
    }

    public void test3NTSignoff() {
        givenBidding(ONE_NOTRUMP, PASS, THREE_NOTRUMP, PASS);
        andPlayersCards("K,3,2", "A,3", "A,K,8,6", "K,J,5,3");
        ruleShouldBid(PASS);
    }

    public void testDoNotRespondIfPartnersBidWasAnOpening() {
        givenBidding(THREE_SPADES, PASS);
        andPlayersCards("K,3,2", "A,3", "A,Q,8,6", "K,J,5,3");
        ruleShouldBid(null);
    }

    public void testDoNotRespondIfPartnersBidWasNotRespondingTo1NT() {
        givenBidding(ONE_SPADES, PASS, THREE_SPADES, PASS);
        andPlayersCards("K,3,2", "A,3", "A,Q,8,6", "K,J,5,3");
        ruleShouldBid(null);
    }

    public void testBugIdentifyingPartnersBidThrowsNPE() {
        givenBidding(ONE_NOTRUMP, PASS, PASS, PASS);
        andPlayersCards("A,K,5,2", "8,6,5,2", "A,Q,9", "A,7");
        ruleShouldBid(null);
    }
}