package org.gnubridge.core.bidding;

import static org.gnubridge.core.Direction.*;
import static org.gnubridge.core.bidding.Bid.ONE_NOTRUMP;
import static org.gnubridge.core.bidding.Bid.*;
import junit.framework.TestCase;

import org.gnubridge.core.Direction;

public class AuctioneerTest extends TestCase {

	public void testFirstToBid() {
		Auctioneer a = new Auctioneer(WEST);
		assertEquals(WEST, a.getNextToBid());
		Auctioneer b = new Auctioneer(SOUTH);
		assertEquals(SOUTH, b.getNextToBid());
	}

	public void testBiddingMovesClockwise() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(ONE_NOTRUMP);
		assertEquals(NORTH, a.getNextToBid());
		a.bid(PASS);
		assertEquals(EAST, a.getNextToBid());
		a.bid(PASS);
		assertEquals(SOUTH, a.getNextToBid());
		a.bid(TWO_NOTRUMP);
		assertEquals(WEST, a.getNextToBid());
	}

	public void testEndBiddingContract() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(ONE_NOTRUMP);
		a.bid(PASS);
		a.bid(TWO_HEARTS);
		a.bid(PASS);
		a.bid(PASS);
		assertFalse(a.biddingFinished());
		a.bid(PASS);
		assertTrue(a.biddingFinished());
		assertEquals(TWO_HEARTS, a.getHighBid());
	}

	public void testEndBiddingDoubledContract() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(ONE_NOTRUMP);
		a.bid(PASS);
		a.bid(PASS);
		a.bid(DOUBLE);
		a.bid(PASS);
		assertFalse(a.biddingFinished());
		a.bid(PASS);
		assertFalse(a.biddingFinished());
		a.bid(PASS);
		assertTrue(a.biddingFinished());
	}

	public void testEndBiddingNoContract() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(PASS);
		a.bid(PASS);
		a.bid(PASS);
		assertFalse(a.biddingFinished());
		a.bid(PASS);
		assertTrue(a.biddingFinished());
		assertEquals(null, a.getHighBid());
	}

	public void testIsOpeningBid() {
		Auctioneer a = new Auctioneer(WEST);
		assertTrue(a.isOpeningBid());
		a.bid(PASS);
		assertTrue(a.isOpeningBid());
		a.bid(PASS);
		assertTrue(a.isOpeningBid());
		a.bid(PASS);
		assertTrue(a.isOpeningBid());
		a.bid(TWO_CLUBS);
		assertFalse("West already passed on 1st round, so it is not an opening bid", a.isOpeningBid());
	}

	public void testNoOpeningBidIfBid1() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(ONE_SPADES);
		assertFalse(a.isOpeningBid());
	}

	public void testNoOpeningBidIfBid2() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(PASS);
		a.bid(ONE_SPADES);
		assertFalse(a.isOpeningBid());
	}

	public void testNoOpeningBidIfBid3() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(PASS);
		a.bid(PASS);
		a.bid(ONE_SPADES);
		assertFalse(a.isOpeningBid());
	}

	public void testCanTraversePairsHistoryWithGetPartnersBid() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(ONE_CLUBS);
		a.bid(PASS);
		a.bid(ONE_DIAMONDS);
		a.bid(ONE_HEARTS);
		a.bid(PASS);
		a.bid(PASS);
		a.bid(ONE_SPADES);
		a.bid(ONE_NOTRUMP);
		Call c8 = a.getLastCall();
		assertEquals(ONE_NOTRUMP, c8.getBid());
		Call c6 = a.getPartnersCall(c8);
		assertNotNull(c6);
		assertEquals(PASS, c6.getBid());
		Call c4 = a.getPartnersCall(c6);
		assertEquals(ONE_HEARTS, c4.getBid());
		Call c2 = a.getPartnersCall(c4);
		assertEquals(PASS, c2.getBid());
		assertNull(a.getPartnersCall(c2));

		Call c7 = a.getPartnersLastCall();
		assertEquals(ONE_SPADES, c7.getBid());
	}

	public void testIsValidAnyBidValidAtStart() {
		Auctioneer a = new Auctioneer(WEST);
		assertTrue(a.isValid(PASS));
		assertTrue(a.isValid(ONE_NOTRUMP));
		assertTrue(a.isValid(SEVEN_DIAMONDS));
	}

	public void testIsValidPassAlwaysValid() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(ONE_NOTRUMP);
		assertTrue(a.isValid(PASS));
	}

	public void testIsValidOnlyHigherThanCurrent() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(ONE_NOTRUMP);
		assertFalse(a.isValid(ONE_CLUBS));
		assertFalse(a.isValid(ONE_NOTRUMP));
		assertTrue(a.isValid(TWO_CLUBS));
	}

	public void testIsValidDoubleOkAfterOpponentBids() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(ONE_NOTRUMP);
		assertTrue(a.isValid(DOUBLE));
	}

	public void testIsValidDoubleIsNotAValidResponseToAnotherDouble() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(ONE_NOTRUMP);
		a.bid(DOUBLE);
		assertFalse(a.isValid(DOUBLE));
	}

	public void testIsValidMayNotDoubleYourOwnDouble() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(ONE_NOTRUMP);
		a.bid(DOUBLE);
		a.bid(PASS);
		assertFalse(a.isValid(DOUBLE));
	}

	public void testIsValidMayNotDoublePartner() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(ONE_NOTRUMP);
		a.bid(PASS);
		assertFalse(a.isValid(DOUBLE));
	}

	public void testIsValidNotOkToOpenWithDouble() {
		Auctioneer a = new Auctioneer(WEST);
		assertFalse(a.isValid(DOUBLE));
		a.bid(PASS);
		assertFalse(a.isValid(DOUBLE));
		a.bid(PASS);
		assertFalse(a.isValid(DOUBLE));
		a.bid(PASS);
		assertFalse(a.isValid(DOUBLE));
	}

	public void testDoublingAffectsTheHighBid() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(ONE_NOTRUMP);
		assertFalse(a.getHighBid().isDoubled());
		a.bid(DOUBLE);
		assertTrue(a.getHighBid().isDoubled());
	}

	public void testDoublingDoesNotChangeTheHighBidValue() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(ONE_NOTRUMP);
		a.bid(DOUBLE);
		assertEquals(ONE_NOTRUMP, a.getHighBid());
	}

	public void testDoublingDoesNotChangeTheHighCallPlayer() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(ONE_NOTRUMP);
		a.bid(DOUBLE);
		assertEquals(WEST, a.getHighCall().getDirection());
	}

	public void testGetDummyNullIfAuctionNotFinished() {
		Auctioneer a = new Auctioneer(WEST);
		assertNull(a.getDummy());
		a.bid(ONE_NOTRUMP);
		assertEquals(null, a.getDummy());
	}

	public void testGetDummyNullIfNoContract() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(PASS);
		a.bid(PASS);
		a.bid(PASS);
		a.bid(PASS);
		assertEquals(null, a.getDummy());
	}

	public void testGetDummySimpleContract() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(PASS);
		a.bid(ONE_NOTRUMP);
		a.bid(PASS);
		a.bid(PASS);
		a.bid(PASS);
		assertEquals(SOUTH, a.getDummy());
	}

	public void testGetDummySimpleDoubledContract() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(PASS);
		a.bid(ONE_NOTRUMP);
		a.bid(DOUBLE);
		a.bid(PASS);
		a.bid(PASS);
		a.bid(PASS);
		assertEquals(SOUTH, a.getDummy());
	}

	public void testGetDummyOverbidContract() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(ONE_NOTRUMP);
		a.bid(TWO_NOTRUMP);
		a.bid(PASS);
		a.bid(PASS);
		a.bid(PASS);
		assertEquals(SOUTH, a.getDummy());
	}

	public void testGetDummyRaisedPartnersContract() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(ONE_NOTRUMP);
		a.bid(PASS);
		a.bid(TWO_NOTRUMP);
		a.bid(PASS);
		a.bid(PASS);
		a.bid(PASS);
		assertEquals(EAST, a.getDummy());
	}

	public void testGetDummyDoubledInitialBid() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(ONE_NOTRUMP);
		a.bid(DOUBLE);
		a.bid(TWO_DIAMONDS);
		a.bid(PASS);
		a.bid(PASS);
		a.bid(PASS);
		assertEquals(WEST, a.getDummy());
	}

	public void testGetDummyTwoRoundContract() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(ONE_NOTRUMP);
		a.bid(PASS);
		a.bid(TWO_DIAMONDS);
		a.bid(PASS);
		a.bid(THREE_DIAMONDS);
		a.bid(PASS);
		a.bid(THREE_NOTRUMP);
		a.bid(PASS);
		a.bid(PASS);
		a.bid(PASS);
		assertEquals(EAST, a.getDummy());
	}

	public void testGetDummyDirectionOffset() {
		Auctioneer a = new Auctioneer(WEST) {
			@Override
			public Direction getDummy() {
				return WEST;
			}
		};
		assertEquals(WEST, a.getDummyOffsetDirection(SOUTH));
		assertEquals(NORTH, a.getDummyOffsetDirection(WEST));
		assertEquals(EAST, a.getDummyOffsetDirection(NORTH));
		assertEquals(SOUTH, a.getDummyOffsetDirection(EAST));

	}

	public void testIsOvercall() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(ONE_DIAMONDS);
		a.bid(ONE_SPADES);
		assertTrue(a.isOvercall(ONE_SPADES));
		assertFalse(a.isOvercall(ONE_DIAMONDS));
	}

	public void testIsOvercall2() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(ONE_DIAMONDS);
		a.bid(ONE_SPADES);
		a.bid(TWO_DIAMONDS);
		assertTrue(a.isOvercall(ONE_SPADES));
		assertFalse(a.isOvercall(TWO_DIAMONDS));
	}

	public void testIsOvercall3() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(ONE_DIAMONDS);
		a.bid(ONE_SPADES);
		a.bid(PASS);
		assertTrue(a.isOvercall(ONE_SPADES));
	}

	public void testIsNotAnOvercall() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(ONE_SPADES);
		assertFalse(a.isOvercall(ONE_SPADES));
	}

	public void testIsNotAnOvercall2() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(PASS);
		a.bid(ONE_SPADES);
		assertFalse(a.isOvercall(ONE_SPADES));
	}

	public void testPassIsNotAnOvercall() {
		Auctioneer a = new Auctioneer(WEST);
		a.bid(ONE_SPADES);
		a.bid(PASS);
		assertFalse(a.isOvercall(PASS));
	}

}
