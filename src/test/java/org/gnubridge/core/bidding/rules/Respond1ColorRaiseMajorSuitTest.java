package org.gnubridge.core.bidding.rules;

import static org.gnubridge.core.bidding.Bid.*;

public class Respond1ColorRaiseMajorSuitTest extends AbstractBiddingRuleTest<Respond1ColorRaiseMajorSuit> {

    public void testOnlyAppliesToMajorSuit() {
        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("K,3,2", "K,5,4,3", "9,8,6", "5,4,3");
        ruleShouldBid(null);
    }

    public void testOnlyAppliesToPartnersBidAt1Level() {
        givenBidding(TWO_HEARTS, PASS);
        andPlayersCards("K,3,2", "K,5,4,3", "9,8,6", "5,4,3");
        ruleShouldBid(null);
    }

    public void testRaiseThePartnerBy1() {
        givenBidding(ONE_HEARTS, PASS);
        andPlayersCards("K,3,2", "K,5,4", "9,8,6,5", "5,4,3");
        ruleShouldBid(TWO_HEARTS);
    }

    public void testRaiseThePartnerBy1AlsoOnSpades() {
        givenBidding(ONE_SPADES, PASS);
        andPlayersCards("K,3,2", "K,5,4", "9,8,6,5", "5,4,3");
        ruleShouldBid(TWO_SPADES);
    }

    public void testDoNotApplyIfLessThan6Points() {
        givenBidding(ONE_SPADES, PASS);
        andPlayersCards("K,3,2", "9,5,4", "9,8,6,5", "5,4,3");
        ruleShouldBid(null);
    }

    public void testCountBonusDistributionalPoints() {
        givenBidding(ONE_SPADES, PASS);
        andPlayersCards("J,4,3,2", "9,5,4", "9,8,6,5,4,3", "");
        ruleShouldBid(TWO_SPADES);
    }

    public void testDoNotApplyIfLessThan3Trumps() {
        givenBidding(ONE_HEARTS, PASS);
        andPlayersCards("K,3,2", "K,5", "9,8,6,5", "5,4,3,2");
        ruleShouldBid(null);
    }

    /**
     * Pavlicek explicitly states this rule does not apply for 11 and 12 points
     */
    public void testRaiseThePartnerBy1DoNotApplyBetween11and12Points() {
        givenBidding(ONE_HEARTS, PASS);
        andPlayersCards("K,3,2", "K,5,4", "A,8,6,5", "J,4,3");
        ruleShouldBid(null);
    }

    public void testRaiseThePartnerBy2Over12Points() {
        givenBidding(ONE_HEARTS, PASS);
        andPlayersCards("K,3,2", "K,5,4", "A,8,6,5", "K,3,2");
        ruleShouldBid(THREE_HEARTS);
    }

    public void testRaiseThePartnerBy1DoNotApplyOver16Points() {
        givenBidding(ONE_HEARTS, PASS);
        andPlayersCards("K,3,2", "K,5,4", "A,8,6,5", "A,K,3");
        ruleShouldBid(null);
    }

    public void testCanHandlePartnersPassWithoutNPE() {
        givenBidding(PASS, PASS);
        andPlayersCards("K,3,2", "K,5,4", "A,8,6,5", "A,K,3");
        ruleShouldBid(null);
    }
}