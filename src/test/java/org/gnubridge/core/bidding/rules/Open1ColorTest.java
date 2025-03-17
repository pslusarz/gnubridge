package org.gnubridge.core.bidding.rules;

import static org.gnubridge.core.bidding.Bid.*;

import org.gnubridge.core.bidding.RPQuizzes;


public class Open1ColorTest extends AbstractBiddingRuleTest<Open1Color> {

    public void testOpenOneColor5ColorSuit() {
        givenNoPriorBids();
        andPlayersCards("K,2", "A,3", "A,8,6,5,3", "5,4,3");
        ruleShouldBid(ONE_DIAMONDS);
    }

    public void testCannotOpenOneColorIfResponding() {
        givenBidding(ONE_CLUBS, PASS);
        andPlayersCards("K,2", "A,3", "A,8,6,5,3", "5,4,3");
        ruleShouldBid(null);
    }

    public void testCannotOpenOneColorIfHigherBid() {
        givenBidding(ONE_HEARTS);
        andPlayersCards("K,2", "A,3", "A,8,6,5,3", "5,4,3");
        ruleShouldBid(null);
    }

    public void testOpenOneColorTwo5ColorSuitsBidHigher() {
        givenNoPriorBids();
        andPlayersCards("K,2", "9,8,7,5,3", "A,K,8,6,5", "2");
        ruleShouldBid(ONE_HEARTS);
    }

    public void testOpenOneColor6ColorSuit() {
        givenNoPriorBids();
        andPlayersCards("K,2", "A,Q,7,5,3", "10,9,8,6,5,4");
        ruleShouldBid(ONE_DIAMONDS);
    }

    public void testOpenOneColorNo5BidLongerMinor() {
        givenNoPriorBids();
        andPlayersCards("K,Q,J,2", "Q,7,5,4", "10,9,8", "A,2");
        ruleShouldBid(ONE_DIAMONDS);
    }

    public void testTriangulateOpenOneColorNo5BidLongerMinor() {
        givenNoPriorBids();
        andPlayersCards("K,Q,J,2", "Q,7,5,4", "A,2", "10,9,8");
        ruleShouldBid(ONE_CLUBS);
    }

    public void testOpenOneColorNo5Minor4x4() {
        givenNoPriorBids();
        andPlayersCards("Q,9,8", "9,7", "5,4,3,2", "A,K,Q,J");
        ruleShouldBid(ONE_DIAMONDS);
    }

    public void testOpenOneColorNo5Minor3x3() {
        givenNoPriorBids();
        andPlayersCards("K,J,2", "Q,7,5,4", "10,9,8", "A,K,10");
        ruleShouldBid(ONE_CLUBS);
    }

    public void testTriangulateOpenOneColorNo5Minor3x3() {
        givenNoPriorBids();
        andPlayersCards("K,J,2", "Q,7,5,4", "A,K,10", "10,9,8");
        ruleShouldBid(ONE_DIAMONDS);
    }

    public void testRP2() {
        givenNoPriorBids();
        andPlayersCards(RPQuizzes.Basics.Lesson2.hand2());
        ruleShouldBid(null);
    }

    public void testRP3() {
        givenNoPriorBids();
        andPlayersCards(RPQuizzes.Basics.Lesson2.hand3());
        ruleShouldBid(ONE_CLUBS);
    }

    public void testRP4() {
        givenNoPriorBids();
        andPlayersCards(RPQuizzes.Basics.Lesson2.hand4());
        ruleShouldBid(ONE_DIAMONDS);
    }

    public void testRP6() {
        givenNoPriorBids();
        andPlayersCards(RPQuizzes.Basics.Lesson2.hand6());
        ruleShouldBid(ONE_SPADES);
    }

    public void testRP7() {
        givenNoPriorBids();
        andPlayersCards(RPQuizzes.Basics.Lesson2.hand7());
        ruleShouldBid(ONE_CLUBS);
    }

    public void testRP8() {
        givenNoPriorBids();
        andPlayersCards(RPQuizzes.Basics.Lesson2.hand8());
        ruleShouldBid(ONE_DIAMONDS);
    }

    public void testRP9() {
        givenNoPriorBids();
        andPlayersCards(RPQuizzes.Basics.Lesson2.hand9());
        ruleShouldBid(ONE_CLUBS);
    }

    public void testRP10() {
        givenNoPriorBids();
        andPlayersCards(RPQuizzes.Basics.Lesson2.hand10());
        ruleShouldBid(ONE_SPADES);
    }

    public void testRP12() {
        givenNoPriorBids();
        andPlayersCards(RPQuizzes.Basics.Lesson2.hand12());
        ruleShouldBid(ONE_DIAMONDS);
    }
}