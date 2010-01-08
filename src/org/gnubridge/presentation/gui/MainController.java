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
						.getCardHolder()), getBiddingController().allowHumanToPlayIfDummy(), view.getPlayView()));
		view.getPlayView().displayScore(
				"Us: " + scoringTracker.getRunningHumanScore() + ", Them: "
						+ scoringTracker.getRunningComputerScore());
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

		view.getPlayView().display(
				"GAME FINISHED. Contract was: " + getBiddingController().getAuction().getHighBid()
						+ ", declarers took " + declarerTricksTaken + " tricks.");

		view.getPlayView().displayScore(
				"North/South: +" + scoringTracker.getLatestDeclarerScoreChange() + " points, East/West: +"
						+ scoringTracker.getLatestDefenderScoreChange() + " points (Human: "
						+ scoringTracker.getRunningHumanScore() + ", " + "Computer: "
						+ scoringTracker.getRunningComputerScore() + ")");
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

		view.getBiddingView().setVulnerability(scoringTracker.nextRound());

		setBiddingController(new BiddingController(view.getBiddingView(), this));
		view.show();
		view.getBiddingView().displayScore(
				"Us: " + scoringTracker.getRunningHumanScore() + ", Them: " + scoringTracker.getRunningComputerScore());
	}

}
