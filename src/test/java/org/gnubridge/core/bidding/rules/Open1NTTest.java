package org.gnubridge.core.bidding.rules;

import static org.gnubridge.core.bidding.Bid.*;

import org.gnubridge.core.bidding.RPQuizzes;

public class Open1NTTest extends AbstractBiddingRuleTest<Open1NT> {

    public void testOpeningOneNTFirstCall() {
        givenNoPriorBids();
        andPlayersCards("K,2", "A,Q,3", "A,8,6,5,3", "K,J,3");
        ruleShouldBid(ONE_NOTRUMP);
    }

    public void testDoNotOpenOneNTWhenResponding() {
        givenBidding(ONE_CLUBS, PASS);
        andPlayersCards("K,2", "A,Q,3", "A,8,6,5,3", "K,J,3");
        ruleShouldBid(null);
    }

    public void testOpeningOneNTSecondCall() {
        givenBidding(PASS);
        andPlayersCards("K,2", "A,Q,3", "A,8,6,5,3", "K,J,3");
        ruleShouldBid(ONE_NOTRUMP);
    }

    public void testCannotOpenOneNTIfInsufficientHCP() {
        givenNoPriorBids();
        andPlayersCards("K,2", "A,3", "A,8,6,5,3", "K,J,3");
        ruleShouldBid(null);
    }

    public void testDoNotOpenOneNTOnImbalancedHand() {
        givenNoPriorBids();
        andPlayersCards("K", "A,Q,3", "A,8,6,5,3", "K,J,3,2");
        ruleShouldBid(null);
    }

    public void testCannotOpenOneNTIfHigherBid() {
        givenBidding(TWO_CLUBS);
        andPlayersCards("K,2", "A,Q,3", "A,8,6,5,3", "K,J,3");
        ruleShouldBid(null);
    }

    public void testRP1() {
        givenNoPriorBids();
        andPlayersCards(RPQuizzes.Basics.Lesson2.hand1());
        ruleShouldBid(ONE_NOTRUMP);
    }

    public void testRP5() {
        givenNoPriorBids();
        andPlayersCards(RPQuizzes.Basics.Lesson2.hand5());
        ruleShouldBid(ONE_NOTRUMP);
    }

    public void testRP11() {
        givenNoPriorBids();
        andPlayersCards(RPQuizzes.Basics.Lesson2.hand11());
        ruleShouldBid(ONE_NOTRUMP);
    }
}