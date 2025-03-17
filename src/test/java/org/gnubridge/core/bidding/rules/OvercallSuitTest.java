package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;

import static org.gnubridge.core.bidding.Bid.*;

public class OvercallSuitTest extends AbstractBiddingRuleTest<OvercallSuit> {
    public static final Hand ONE_DIAMONDS_OVERCALL_CARDS = new Hand("7,8", "4,3","A,K,J,9,3,2","Q,5,4");
    public static final Hand ONE_HEARTS_OVERCALL_CARDS = new Hand("7,8", "A,K,J,9,3,2","4,3","Q,5,4");

    public void testOnlyAppliesToTrueOvercalls() {
        givenBidding(PASS);
        andPlayersCards(ONE_DIAMONDS_OVERCALL_CARDS);
        ruleShouldBid(null);

        givenBidding(ONE_CLUBS, PASS);
        andPlayersCards(ONE_DIAMONDS_OVERCALL_CARDS);
        ruleShouldBid(null);

        givenNoPriorBids();
        andPlayersCards(ONE_DIAMONDS_OVERCALL_CARDS);
        ruleShouldBid(null);
    }

    public void test1ColorAt10to12SuitOf6() {
        givenBidding(ONE_CLUBS);
        andPlayersCards(ONE_DIAMONDS_OVERCALL_CARDS);
        ruleShouldBid(ONE_DIAMONDS);
    }

    public void test1ColorAt10to12DecentSuitOf5() {
        givenBidding(ONE_CLUBS);
        andPlayersCards("7,8", "K,3,2", "Q,J,9,3,2", "A,5,4");
        ruleShouldBid(ONE_DIAMONDS);
    }

    public void testGoodSuitOf5ButLessThan10Points() {
        givenBidding(ONE_CLUBS);
        andPlayersCards("7,8", "4,3,2", "A,K,J,9,3", "6,5,4");
        ruleShouldBid(null);
    }

    public void test1ColorAt10to12PoorSuitOf5() {
        givenBidding(ONE_CLUBS);
        andPlayersCards("7,8", "A,3,2", "K,10,9,3,2", "Q,J,4");
        ruleShouldBid(null);
    }

    public void test1DifferentColorAt10to12() {
        givenBidding(ONE_CLUBS);
        andPlayersCards(ONE_HEARTS_OVERCALL_CARDS);
        ruleShouldBid(ONE_HEARTS);
    }

    public void testTwoPassesPrecedeRightHandOpponentsOpening() {
        givenBidding(PASS, PASS, ONE_CLUBS);
        andPlayersCards(ONE_DIAMONDS_OVERCALL_CARDS);
        ruleShouldBid(ONE_DIAMONDS);
    }

    public void testOnePassPrecedesRightHandOpponentsOpening() {
        givenBidding(PASS, ONE_CLUBS);
        andPlayersCards(ONE_DIAMONDS_OVERCALL_CARDS);
        ruleShouldBid(ONE_DIAMONDS);
    }

    public void testPickAMoreViableColor() {
        givenBidding(ONE_DIAMONDS);
        andPlayersCards("2", "A,K,J,9,3", "", "10,9,8,7,6,3,2");
        ruleShouldBid(ONE_HEARTS);
    }

    public void test13PointsCanBidOnPoor5() {
        givenBidding(ONE_CLUBS);
        andPlayersCards("7,8", "A,K,2", "K,J,9,3,2", "Q,5,4");
        ruleShouldBid(ONE_DIAMONDS);
    }

    public void test13PointsCanBidOnPoor5PickMoreViableColor() {
        givenBidding(ONE_DIAMONDS);
        andPlayersCards("A,10,7,8,2", "", "K,J,9,3,2", "Q,5,4");
        ruleShouldBid(ONE_SPADES);
    }

    public void test13PointsCanBid2LevelIfNecessary() {
        givenBidding(ONE_SPADES);
        andPlayersCards("7,8", "A,K", "K,J,9,4,3,2", "Q,5,4");
        ruleShouldBid(TWO_DIAMONDS);
    }

    public void test13PointsCanBid2LevelForGood5() {
        givenBidding(ONE_SPADES);
        andPlayersCards("7,8,2", "A,K", "K,Q,10,4,3", "Q,5,4");
        ruleShouldBid(TWO_DIAMONDS);
    }

    public void test13PointsCanotBid2LevelForDecent5() {
        givenBidding(ONE_SPADES);
        andPlayersCards("7,8,2", "A,K", "Q,J,10,4,3", "K,5,4");
        ruleShouldBid(null);
    }

    public void test16PointsCanBid2LevelForAny5LongSuit() {
        givenBidding(ONE_SPADES);
        andPlayersCards("K,8,2", "A,K", "K,J,9,4,3", "Q,5,4");
        ruleShouldBid(TWO_DIAMONDS);
    }

    public void test16PointsBidAtLowestLevelPossibleAny5LongSuit() {
        givenBidding(ONE_CLUBS);
        andPlayersCards("K,8,2", "A,K", "K,J,9,4,3", "Q,5,4");
        ruleShouldBid(ONE_DIAMONDS);
    }
}