package org.gnubridge.presentation.gui;

import org.gnubridge.core.Deal;
import org.gnubridge.core.East;
import org.gnubridge.core.North;
import org.gnubridge.core.Player;
import org.gnubridge.core.South;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;

public class MainController {

	private MainView view;
	private BiddingController biddingController;
	private DealController gameController;

	private final ScoringTracker scoringTracker;

	public MainController() {
		scoringTracker = new ScoringTracker();
		newGame();
	}

	public void playGame() {
		setGameController(new DealController(this, getBiddingController().getAuction().getHighBid(),
				repositionHandsSoThatSouthIsDeclarer(getBiddingController().getAuction(), getBiddingController()
						.getCardHolder()), getBiddingController().allowHumanToPlayIfDummy(), view.getDealView(),
				scoringTracker));

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

	public void gameFinished() {
		int declarerTricksTaken = getGameController().getGame().getTricksTaken(Player.NORTH_SOUTH);

		scoringTracker.processFinishedGame(gameController.getHuman().getValue(), getBiddingController().getAuction()
				.getHighBid(), declarerTricksTaken);

	}

	public void setBiddingController(BiddingController biddingController) {
		this.biddingController = biddingController;
	}

	public BiddingController getBiddingController() {
		return biddingController;
	}

	public void setGameController(DealController gameController) {
		this.gameController = gameController;
	}

	public DealController getGameController() {
		return gameController;
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
		setBiddingController(new BiddingController(view.getBiddingView(), this, scoringTracker));
		view.show();

	}

}
