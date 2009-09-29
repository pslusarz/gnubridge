package org.gnubridge.presentation.gui;

import junit.framework.TestCase;

import org.gnubridge.core.Game;
import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.BiddingAgent;
import org.gnubridge.core.bidding.Pass;
import org.gnubridge.presentation.GameUtils;

public class BiddingAcceptanceTest extends TestCase {
	private static final int TRICKS_PER_DEAL = 6;

	public void testAutomatedBidding() {
		GBController mainController = makeController();
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
			GBController mainController = makeController();
			Auctioneer auction = mainController.getBiddingController().getAuction();
			System.out.println(" ** ( " + i + " )*** Automated bidding on a random game *****");
			mainController.getBiddingController().getCardHolder().printHandsDebug();
			Hand humanHand = new Hand(mainController.getBiddingController().getHuman().getHand());
			BiddingAgent humanAgent = new BiddingAgent(auction, humanHand);
			System.out.println("  Initial Calls: " + auction.getCalls());

			while (!auction.biddingFinished()) {

				Bid humanBid = humanAgent.getBid();
				System.out.println("  human about to bid: " + humanBid);
				if (new Pass().equals(humanBid)) {
					mainController.getBiddingController().placeBid(-1, "PASS");
				} else {
					mainController.getBiddingController().placeBid(humanBid.getValue(), humanBid.getTrump().toString());
				}
				System.out.println("  Calls after human bid: " + auction.getCalls());
			}
		}
	}

	public void testBiddingHighestEndsAuction() {
		preInitializeGameWithHumanToBidFirst();
		GBController mainController = makeController();
		Auctioneer auction = mainController.getBiddingController().getAuction();
		mainController.getBiddingController().placeBid(7, "NT");
		assertEquals("3 passes should follow 7NT bid", 4, auction.getCalls().size());
		assertTrue("Auction not complete even though highest bid was placed", auction.biddingFinished());
	}

	private void preInitializeGameWithHumanToBidFirst() {
		Game g = new Game(null);
		GameUtils.initializeRandom(g, TRICKS_PER_DEAL);
		g.setHumanPlayer(g.getWest());
		Game.setPreInitializedGame(g);
	}

	private GBController makeController() {
		MainView mw = new MockMainView("gnubridge");
		return new GBController(mw);
	}
}
