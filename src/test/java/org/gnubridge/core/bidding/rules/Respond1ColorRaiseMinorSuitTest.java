package org.gnubridge.core.bidding.rules;

import static org.gnubridge.core.bidding.Bid.*;

public class Respond1ColorRaiseMinorSuitTest extends AbstractBiddingRuleTest<Respond1ColorRaiseMinorSuit> {

    public void testOnlyAppliesToMinorSuit() {
        givenBidding(ONE_HEARTS, PASS);
        andPlayersCards("K,3,2", "5,4,3", "K,9,8,6", "5,4,3");
        ruleShouldBid(null);
    }

    public void testOnlyAppliesToPartnersBidAt1Level() {
        givenBidding(TWO_DIAMONDS, PASS);
        andPlayersCards("K,3,2", "5,4,3", "K,9,8,6", "5,4,3");
        ruleShouldBid(null);
    }

    public void testRaiseThePartnerBy1() {
        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("K,3,2", "10,5,4", "K,8,6,5", "5,4,3");
        ruleShouldBid(TWO_DIAMONDS);
    }

    public void testRaiseThePartnerBy1AlsoOnClubs() {
        givenBidding(ONE_CLUBS, PASS);
        andPlayersCards("K,3,2", "10,5,4", "8,6,5", "K,5,4,3");
        ruleShouldBid(TWO_CLUBS);
    }

    public void testDoNotApplyIfLessThan6Points() {
        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("K,3,2", "9,5,4", "9,8,6,5", "5,4,3");
        ruleShouldBid(null);
    }

    public void testCountBonusDistributionalPoints() {
        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("J,4,3,2", "9,5,4,3,2", "6,5,4,3", "");
        ruleShouldBid(TWO_DIAMONDS);
    }

    public void testDoNotApplyIfLessThan4Trumps() {
        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("K,3,2", "K,5,4", "8,6,5", "5,4,3,2");
        ruleShouldBid(null);
    }

    /**
     * Pavlicek explicitly states this rule does not apply for 11 and 12 points
     */
    public void testRaiseThePartnerBy1DoNotApplyBetween11and12Points() {
        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("K,3,2", "K,5,4", "A,8,6,5", "J,4,3");
        ruleShouldBid(null);
    }

    public void testRaiseThePartnerBy2Over12Points() {
        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("K,3,2", "K,5,4", "A,8,6,5", "K,3,2");
        ruleShouldBid(THREE_DIAMONDS);
    }

    public void testRaiseThePartnerBy1DoNotApplyOver16Points() {
        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("K,3,2", "K,5,4", "A,8,6,5", "A,K,3");
        ruleShouldBid(null);
    }

    public void testCanHandlePartnersPassWithoutNPE() {
        givenBidding(PASS, PASS);
        andPlayersCards("K,3,2", "K,5,4", "A,8,6,5", "A,K,3");
        ruleShouldBid(null);
    }
}