package org.gnubridge.presentation.gui;

import junit.framework.TestCase;

import org.gnubridge.core.Deal;
import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.BiddingAgent;
import org.gnubridge.core.bidding.Pass;
import org.gnubridge.presentation.GameUtils;
import org.jbridge.presentation.gui.MockMainView;

import static org.gnubridge.core.bidding.Bid.*;

public class BiddingAcceptanceTest extends TestCase {
	private static final int TRICKS_PER_DEAL = 6;

	public void testAutomatedBidding() {
		MainController mainController = makeController();
		Auctioneer auction = mainController.getBiddingController().getAuction();
		System.out.println(" ***** Automated bidding on a random game *****");
		mainController.getBiddingController().getCardHolder().printHandsDebug();
		System.out.println("  Initial Calls: " + auction.getCalls());
		int initialBids = auction.getCalls().size();
		mainController.getBiddingController().placeBid(4, "NT");
		System.out.println("  Calls after human bid: " + auction.getCalls());
		assertEquals("Automated bidding was not performed after human placed bid", initialBids + 4, auction.getCalls()
				.size());
	}

	public void testBiddingStochastically() {
		for (int i = 0; i < 100; i++) {
			MainController mainController = makeController();
			Auctioneer auction = mainController.getBiddingController().getAuction();
			System.out.println(" ** ( " + i + " )*** Automated bidding on a random game *****");
			mainController.getBiddingController().getCardHolder().printHandsDebug();
			Hand humanHand = new Hand(mainController.getBiddingController().getHuman().getHand());
			BiddingAgent humanAgent = new BiddingAgent(auction, humanHand);
			System.out.println("  Initial Calls: " + auction.getCalls());

			while (!auction.biddingFinished()) {

				Bid humanBid = humanAgent.getBid();
				System.out.println("  human about to bid: " + humanBid);
				if (PASS.equals(humanBid)) {
					mainController.getBiddingController().placeBid(-1, "PASS");
				} else if (DOUBLE.equals(humanBid)) {
					mainController.getBiddingController().placeBid(-1, "DOUBLE");
				} else if (REDOUBLE.equals(humanBid)) {
					mainController.getBiddingController().placeBid(-1, "REDOUBLE");
				}
				else {
					mainController.getBiddingController().placeBid(humanBid.getValue(), humanBid.getTrump().toString());
				}
				System.out.println("  Calls after human bid: " + auction.getCalls());
			}
		}
	}

	public void testBiddingHighestEndsAuction() {
		preInitializeGameWithHumanToBidFirst();
		MainController mainController = makeController();
		Auctioneer auction = mainController.getBiddingController().getAuction();
		mainController.getBiddingController().placeBid(7, "NT");
		assertEquals("3 passes should follow 7NT bid", 4, auction.getCalls().size());
		assertTrue("Auction not complete even though highest bid was placed", auction.biddingFinished());
	}

	private void preInitializeGameWithHumanToBidFirst() {
		Deal g = new Deal(null);
		GameUtils.initializeRandom(g, TRICKS_PER_DEAL);
		g.setHumanPlayer(g.getWest());
		Deal.setPreInitializedGame(g);
	}

	private MainController makeController() {
		MainView mw = new MockMainView("gnubridge");
		ViewFactory.setMockMainView(mw);
		return new MainController();
	}
}
