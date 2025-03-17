package org.gnubridge.core.bidding.rules;

	import static org.gnubridge.core.bidding.Bid.*;

	public class RebidOriginalSuitTest extends AbstractBiddingRuleTest<Rebid1ColorOriginalSuit> {

	    public void testRebidAt2Level() {
	        givenBidding(ONE_CLUBS, PASS, ONE_DIAMONDS, PASS);
	        andPlayersCards("3,2", "K,Q,J", "9,8", "A,K,5,4,3,2");
	        ruleShouldBid(TWO_CLUBS);
	    }

	    public void testRebidAt3Level() {
	        givenBidding(ONE_HEARTS, PASS, ONE_SPADES, PASS);
	        andPlayersCards("3,2", "A,K,5,4,3,2", "K,Q,J", "K,8");
	        ruleShouldBid(THREE_HEARTS);
	    }

	    public void testRebidAt4Level() {
	        givenBidding(ONE_HEARTS, PASS, ONE_SPADES, PASS);
	        andPlayersCards("K,2", "A,K,J,5,4,3,2", "K,Q", "K,8");
	        ruleShouldBid(FOUR_HEARTS);
	    }

	    public void testDoNotRebidIfLessThan6Cards() {
	        givenBidding(ONE_CLUBS, PASS, ONE_DIAMONDS, PASS);
	        andPlayersCards("3,2", "K,Q,J", "9,8,2", "A,K,5,4,3");
	        ruleShouldBid(null);
	    }

	    public void testDoNotRebidNT() {
	        givenBidding(ONE_NOTRUMP, PASS, ONE_DIAMONDS, PASS);
	        andPlayersCards("3,2", "K,Q,J", "9,8", "A,K,5,4,3,2");
	        ruleShouldBid(null);
	    }
	}