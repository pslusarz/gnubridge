package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.bidding.Bid;

import static org.gnubridge.core.bidding.Bid.*;

public class Respond1ColorWithNewSuitTest extends AbstractBiddingRuleTest<Respond1ColorWithNewSuit> {

    public void testRespond1ColorWithHeartsAsBestSuit() {
        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("K,3,2", "K,5,4,3", "9,8,6", "5,4,3");
        ruleShouldBid(ONE_HEARTS);
    }

    public void testRespond1ColorWithSpadesAsBestSuit() {
        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("K,4,3,2", "K,5,4", "9,8,6", "5,4,3");
        Bid bid = ruleShouldBid(ONE_SPADES);
        assertTrue(bid.isForcing());
        assertFalse(bid.isGameForcing());
    }

    public void testRespond1ColorBonusOnDistributionalPoints() {
        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("K,3,2", "10,5,4,3,2", "9,8,6,5", "5");
        ruleShouldBid(ONE_HEARTS);
    }

    public void testRespond1ColorBestSuit() {
        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("K,5,3,2", "K,5,4,3", "9,8,6", "5,4");
        ruleShouldBid(ONE_SPADES);

        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("K,5,3,2", "K,5,4,3,2", "9,8", "5,4");
        ruleShouldBid(ONE_HEARTS);
    }

    public void testRespond1Color11Points() {
        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("K,5,3,2", "K,5,4,3", "A,8,6", "5,4");
        ruleShouldBid(ONE_SPADES);
    }

    public void testRespond2Color11Points() {
        givenBidding(ONE_SPADES, PASS);
        andPlayersCards("K,3,2", "K,5,4,3", "A,8,6,3", "5,4");
        ruleShouldBid(TWO_HEARTS);
    }

    public void testJumpSuit17Points5Suit() {
        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("K,3,2", "A,K,5,4,3", "A,Q,6,3", "5,4");
        Bid bid = ruleShouldBid(TWO_HEARTS);
        assertTrue(bid.isGameForcing());
    }

    public void testJumpHigherSuit() {
        givenBidding(ONE_HEARTS, PASS);
        andPlayersCards("7,2", "A,8,2", "A,K,J,5,4", "A,J,5");
        Bid bid = ruleShouldBid(THREE_DIAMONDS);
        assertTrue(bid.isGameForcing());
    }

    public void testDoNotJumpSuitIfNotRespondingTo1Color() {
        givenBidding(ONE_NOTRUMP, PASS);
        andPlayersCards("K,3,2", "A,K,5,4,3", "A,Q,6,3", "5,4");
        ruleShouldBid(null);
    }

    public void testDoNotJumpSuitIfRespondingTo2Color() {
        givenBidding(TWO_DIAMONDS, PASS);
        andPlayersCards("K,3,2", "A,K,5,4,3", "A,Q,6,3", "5,4");
        ruleShouldBid(null);
    }

    public void testDoNotBidPartnersColor() {
        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("Q,J,3,2", "9,6", "K,J,9,7,5", "A,2");
        ruleShouldBid(ONE_SPADES);
    }

    public void testOKToBidColorLowerThanPartnersColor() {
        givenBidding(ONE_DIAMONDS, PASS);
        andPlayersCards("A,J,9", "K,10,7", "K,Q,5", "A,9,6,2");
        ruleShouldBid(TWO_CLUBS);
    }
}