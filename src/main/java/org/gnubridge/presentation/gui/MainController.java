package org.gnubridge.presentation.gui;

import java.util.Random;

import org.gnubridge.core.Deal;
import org.gnubridge.core.East;
import org.gnubridge.core.North;
import org.gnubridge.core.South;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.UsThemVulnerability;

public class MainController {

	private MainView view;
	private BiddingController biddingController;
	private DealController dealController;

	private final ScoringTracker scoringTracker;

	public MainController() {
		scoringTracker = ScoringTracker.getInstance();
		newGame();
	}

	public void playGame() {
		this.dealController = new DealController(this, getBiddingController().getAuction().getHighBid(),
				repositionHandsSoThatSouthIsDeclarer(getBiddingController().getAuction(), getBiddingController()
						.getCardHolder()), getBiddingController().allowHumanToPlayIfDummy(), view.getDealView(),
				scoringTracker);

	}

	private Deal repositionHandsSoThatSouthIsDeclarer(Auctioneer a, Deal cardHolder) {
		Deal result = new Deal(a.getHighBid().getTrump());
		result.getPlayer(a.getDummyOffsetDirection(North.i())).init(cardHolder.getPlayer(North.i()).getHand());
		result.getPlayer(a.getDummyOffsetDirection(East.i())).init(cardHolder.getPlayer(East.i()).getHand());
		result.getPlayer(a.getDummyOffsetDirection(South.i())).init(cardHolder.getPlayer(South.i()).getHand());
		result.getPlayer(a.getDummyOffsetDirection(West.i())).init(cardHolder.getPlayer(West.i()).getHand());
		result.setNextToPlay(West.i().getValue());
		return result;
	}

	public BiddingController getBiddingController() {
		return biddingController;
	}

	public DealController getGameController() {
		return dealController;
	}

	public int getRunningHumanScore() {
		return scoringTracker.getRunningHumanScore();
	}

	public int getRunningComputerScore() {
		return scoringTracker.getRunningComputerScore();
	}

	public void newGame() {
		if (view != null) {
			view.hide();
		}
		this.view = ViewFactory.getMainView();
		scoringTracker.setUsThemVulnerability(new UsThemVulnerability(new Random().nextBoolean(), new Random()
				.nextBoolean()));
		this.biddingController = new BiddingController(view.getBiddingView(), this, scoringTracker);
		view.show();

	}

	public void donate() {
		String donationUrl = "https://www.paypal.com/donate?campaign_id=9GACWA5V45JQC";
		try {
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(donationUrl));
		} catch (java.io.IOException e) {
			System.err.println("Failed to open browser: " + e.getMessage());
		}
	}

}
