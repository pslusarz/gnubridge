package org.gnubridge.presentation.gui;

import org.gnubridge.core.Player;

public class GBController {

	private final MainView view;
	private BiddingController biddingController;
	private GameController gameController;

	public GBController(MainView view) {
		this.view = view;
		setBiddingController(new BiddingController(view.getBiddingView(), this));
	}

	public void playGame() {
		setGameController(new GameController(this, getBiddingController().getAuction(), getBiddingController()
				.getCardHolder(), getBiddingController().allowHumanToPlayIfDummy(), view.getPlayView()));
	}

	public void gameFinished() {
		view.getPlayView().display(
				"GAME FINISHED. Contract was: " + getBiddingController().getAuction().getHighBid()
						+ ", declarers took " + getGameController().getGame().getTricksTaken(Player.NORTH_SOUTH)
						+ " tricks.");
	}

	public void setBiddingController(BiddingController biddingController) {
		this.biddingController = biddingController;
	}

	public BiddingController getBiddingController() {
		return biddingController;
	}

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}

	public GameController getGameController() {
		return gameController;
	}

}
