package org.gnubridge.core.bidding.rules;

import static org.gnubridge.core.bidding.Bid.*;

public class RespondOvercallSuitTest extends AbstractBiddingRuleTest<RespondOvercallSuit> {

    /**
     * Raise partners suit
     */
    public void testBidAt2LevelWith8PointsAnd3Trumps() {
        givenBidding(ONE_CLUBS, ONE_DIAMONDS, PASS);
        andPlayersCards("10,9,8,7", "K,3,2", "A,J,9", "9,5,4");
        ruleShouldBid(TWO_DIAMONDS);
    }

    public void testRaisePartnerWith8PointsAnd3Trumps() {
        givenBidding(ONE_CLUBS, TWO_DIAMONDS, PASS);
        andPlayersCards("10,9,8,7", "K,3,2", "A,J,9", "9,5,4");
        ruleShouldBid(THREE_DIAMONDS);
    }

    public void testJumpRaisePartnerWith12To14PointsAnd3Trumps() {
        givenBidding(ONE_CLUBS, ONE_DIAMONDS, PASS);
        andPlayersCards("A,9,8,7", "K,3,2", "A,J,9", "9,5,4");
        ruleShouldBid(THREE_DIAMONDS);
    }

    public void testRaiseToGameWith15PointsAnd3TrumpsMajorSuit() {
        givenBidding(ONE_CLUBS, ONE_HEARTS, PASS);
        andPlayersCards("A,K,8,7", "K,3,2", "A,J,9", "9,5,4");
        ruleShouldBid(FOUR_HEARTS);
    }

    public void testRaiseTo4LevelWith15PointsAnd3TrumpsMinorSuit() {
        givenBidding(ONE_CLUBS, ONE_DIAMONDS, PASS);
        andPlayersCards("A,K,8,7", "K,3,2", "A,J,9", "9,5,4");
        ruleShouldBid(FOUR_DIAMONDS);
    }

    public void testReallyJumpRaisePartnerWith12To14PointsAnd3Trumps() {
        givenBidding(ONE_CLUBS, TWO_DIAMONDS, PASS);
        andPlayersCards("A,9,8,7", "K,3,2", "A,J,9", "9,5,4");
        ruleShouldBid(FOUR_DIAMONDS);
    }

    /**
     * bid unbid own suit
     */
    public void testBidUnbidSuitAtTwoLevelWith8PointsAnd5Trumps() {
        givenBidding(ONE_CLUBS, ONE_DIAMONDS, TWO_HEARTS);
        andPlayersCards("A,10,9,8,7", "K,3,2", "J,9", "9,5,4");
        ruleShouldBid(TWO_SPADES);
    }

    public void testBidUnbidSuitAtCheapestLevelWith8PointsAnd5Trumps() {
        givenBidding(ONE_CLUBS, ONE_DIAMONDS, PASS);
        andPlayersCards("A,10,9,8,7", "K,3,2", "J,9", "9,5,4");
        ruleShouldBid(ONE_SPADES);
    }

    public void testJumpBidUnbidSuitWith12PointsAnd5Trumps() {
        givenBidding(ONE_CLUBS, ONE_DIAMONDS, PASS);
        andPlayersCards("A,10,9,8,7", "K,3,2", "J,9", "A,5,4");
        ruleShouldBid(TWO_SPADES);
    }

    public void testJumpHigherBidUnbidSuitWith12PointsAnd5Trumps() {
        givenBidding(ONE_CLUBS, TWO_DIAMONDS, PASS);
        andPlayersCards("A,10,9,8,7", "K,3,2", "J,9", "A,5,4");
        ruleShouldBid(THREE_SPADES);
    }

    public void testBidGameUnbidSuitWith15PointsAnd5Trumps() {
        givenBidding(ONE_CLUBS, ONE_DIAMONDS, PASS);
        andPlayersCards("A,K,9,8,7", "K,3,2", "J,9", "A,5,4");
        ruleShouldBid(FOUR_SPADES);
    }

    /**
     * respond no trump
     */
    public void testBidNoTrumpOneLevelWith8PointsAndStopperInEnemySuit() {
        givenBidding(ONE_CLUBS, ONE_DIAMONDS, PASS);
        andPlayersCards("A,10,9,8", "9,4,3,2", "J,9", "K,5,4");
        ruleShouldBid(ONE_NOTRUMP);
    }

    public void testDoNotBidWithoutStopperInEnemySuit() {
        givenBidding(ONE_CLUBS, ONE_DIAMONDS, PASS);
        andPlayersCards("A,10,9,8", "K,4,3,2", "J,9", "9,5,4");
        ruleShouldBid(null);
    }

    public void testDoNotBidWithoutStopperInBothEnemySuits() {
        givenBidding(ONE_CLUBS, ONE_DIAMONDS, ONE_HEARTS);
        andPlayersCards("A,K,9,8", "9,4,3,2", "10,9", "J,9,5,4");
        ruleShouldBid(null);
    }

    public void testBidWithStoppersInBothEnemySuits() {
        givenBidding(ONE_CLUBS, ONE_DIAMONDS, ONE_HEARTS);
        andPlayersCards("A,10,9,8", "K,4,3,2", "10,9", "J,9,5,4");
        ruleShouldBid(ONE_NOTRUMP);
    }

    public void testBidNoTrumpCheapestLevelWith8PointsAndStopperInEnemySuit() {
        givenBidding(ONE_CLUBS, TWO_DIAMONDS, PASS);
        andPlayersCards("A,10,9,8", "9,4,3,2", "J,9", "K,5,4");
        ruleShouldBid(TWO_NOTRUMP);
    }

    public void testJumpBidNoTrumpWith12PointsAndStopperInEnemySuit() {
        givenBidding(ONE_CLUBS, ONE_DIAMONDS, PASS);
        andPlayersCards("A,10,9,8", "A,4,3,2", "J,9", "K,5,4");
        ruleShouldBid(TWO_NOTRUMP);
    }

    public void testJumpHigherBidNoTrumpWith12PointsAndStopperInEnemySuit() {
        givenBidding(ONE_CLUBS, TWO_DIAMONDS, PASS);
        andPlayersCards("A,10,9,8", "A,4,3,2", "J,9", "K,5,4");
        ruleShouldBid(THREE_NOTRUMP);
    }

    public void testBidGameNoTrumpWith15PointsAndStopperInEnemySuit() {
        givenBidding(ONE_CLUBS, ONE_DIAMONDS, PASS);
        andPlayersCards("A,K,9,8", "A,4,3,2", "J,9", "K,5,4");
        ruleShouldBid(THREE_NOTRUMP);
    }

    /**
     * border cases when rule should not apply
     */
    public void testPassWithLessThan8Points() {
        givenBidding(ONE_CLUBS, ONE_DIAMONDS, PASS);
        andPlayersCards("7,8", "K,3,2", "Q,J,9,3,2", "9,5,4");
        ruleShouldBid(null);
    }

    public void testDoNotRaiseIfDoNotHave3Trumps() {
        givenBidding(ONE_CLUBS, ONE_DIAMONDS, PASS);
        andPlayersCards("10,9,8,7", "K,3,2,4", "A,9", "J,9,5");
        ruleShouldBid(null);
    }

    public void testCountPointsAsDummy() {
        givenBidding(ONE_CLUBS, ONE_DIAMONDS, PASS);
        andPlayersCards("10,9,8,7,6", "", "K,10,9,2", "10,9,5,4");
        ruleShouldBid(TWO_DIAMONDS);
    }

    public void testOnlyApplyToOvercallResponses() {
        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("10,9,8,7,6", "", "K,10,9,2", "10,9,5,4");
        ruleShouldBid(null);
    }

    public void testOnlyApplyToSuitOvercallsNoTrump() {
        givenBidding(ONE_CLUBS, ONE_NOTRUMP, PASS);
        andPlayersCards("10,9,8,7", "K,3,2", "A,J,9", "9,5,4");
        ruleShouldBid(null);
    }

    public void testOnlyApplyToSuitOvercallsDouble() {
        givenBidding(ONE_CLUBS, DOUBLE, PASS);
        andPlayersCards("10,9,8,7", "K,3,2", "A,J,9", "9,5,4");
        ruleShouldBid(null);
    }
}