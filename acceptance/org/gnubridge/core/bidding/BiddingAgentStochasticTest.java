package org.gnubridge.core.bidding;

import static org.gnubridge.core.bidding.Bid.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import junit.framework.TestCase;

import org.gnubridge.core.Deal;
import org.gnubridge.core.Hand;
import org.gnubridge.core.West;
import org.gnubridge.presentation.GameUtils;

public class BiddingAgentStochasticTest extends TestCase {
	Bid[] allBids = new Bid[] { PASS, DOUBLE, ONE_NOTRUMP, ONE_SPADES, ONE_HEARTS, ONE_DIAMONDS, ONE_CLUBS,
			TWO_NOTRUMP, TWO_SPADES, TWO_HEARTS, TWO_DIAMONDS, TWO_CLUBS, THREE_NOTRUMP, THREE_SPADES, THREE_HEARTS,
			THREE_DIAMONDS, THREE_CLUBS, FOUR_NOTRUMP, FOUR_SPADES, FOUR_HEARTS, FOUR_DIAMONDS, FOUR_CLUBS,
			FIVE_NOTRUMP, FIVE_SPADES, FIVE_HEARTS, FIVE_DIAMONDS, FIVE_CLUBS, SIX_NOTRUMP, SIX_SPADES, SIX_HEARTS,
			SIX_DIAMONDS, SIX_CLUBS, SEVEN_NOTRUMP, SEVEN_SPADES, SEVEN_HEARTS, SEVEN_DIAMONDS, SEVEN_CLUBS };

	public void testNoExceptionsGetThrown() {
		for (int i = 0; i < 10000; i++) {
			System.out.println(i + " auction: ******************************************");
			Auctioneer a = new Auctioneer(West.i());
			Deal g = new Deal(null);
			GameUtils.initializeRandom(g, 13);
			g.printHandsDebug();
			while (!a.biddingFinished()) {
				Hand hand = new Hand(g.getPlayer(a.getNextToBid()).getHand());
				BiddingAgent baUnderTest = new BiddingAgent(a, hand);
				System.out.println(" for player: " + a.getNextToBid());
				System.out.println(" the recommended bid is: " + baUnderTest.getBid());
				List<Bid> validBids = getValidBids(a);
				assertTrue("no valid bids, but auction not finished", validBids.size() > 0);
				int index = Math.abs(new Random().nextInt() % validBids.size());
				System.out.println("Bidding: " + validBids.get(index));
				a.bid(validBids.get(index));

			}
		}

	}

	private List<Bid> getValidBids(Auctioneer a) {
		List<Bid> result = new ArrayList<Bid>();
		for (Bid bid : allBids) {
			if (a.isValid(bid)) {
				result.add(bid);
			}
		}
		return result;
	}
}
