package org.gnubridge.core.bidding.rules;

import static org.gnubridge.core.bidding.Bid.*;

public class Rebid1ColorRaisePartnerTest extends AbstractBiddingRuleTest<Rebid1ColorRaisePartner> {

    public void testRaiseThePartnerTo2() {
        givenBidding(ONE_CLUBS, PASS, ONE_HEARTS, PASS);
        andPlayersCards("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3");
        ruleShouldBid(TWO_HEARTS);
    }

    public void testRaiseThePartnerTo2AnotherColor() {
        givenBidding(ONE_CLUBS, PASS, ONE_SPADES, PASS);
        andPlayersCards("9,5,3,2", "A,Q", "9,8", "A,K,5,4,3");
        ruleShouldBid(TWO_SPADES);
    }

    public void testDoNotApplyIfLessThan4Trumps() {
        givenBidding(ONE_CLUBS, PASS, ONE_SPADES, PASS);
        andPlayersCards("9,5,3", "A,Q,2", "9,8", "A,K,5,4,3");
        ruleShouldBid(null);
    }

    public void testRaiseThePartnerTo3() {
        givenBidding(ONE_CLUBS, PASS, ONE_HEARTS, PASS);
        andPlayersCards("3,2", "K,Q,J,2", "K,8", "A,K,5,4,3");
        ruleShouldBid(THREE_HEARTS);
    }

    public void testRaiseThePartnerTo4() {
        givenBidding(ONE_CLUBS, PASS, ONE_HEARTS, PASS);
        andPlayersCards("3,2", "K,Q,J,2", "K,8", "A,K,J,4,3");
        ruleShouldBid(FOUR_HEARTS);
    }

    public void testUse5_3_1distributionalCount() {
        givenBidding(ONE_CLUBS, PASS, ONE_SPADES, PASS);
        andPlayersCards("Q,5,3,2", "Q,9,8,7", "", "A,K,5,4,3");
        ruleShouldBid(THREE_SPADES);
    }

    public void testDoNotApplyToNoTrumpResponse() {
        givenBidding(ONE_CLUBS, PASS, ONE_NOTRUMP, PASS);
        andPlayersCards("Q,5,3,2", "Q,9,8,7", "", "A,K,5,4,3");
        ruleShouldBid(null);
    }
}