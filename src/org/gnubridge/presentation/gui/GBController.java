package org.gnubridge.presentation.gui;

import org.gnubridge.core.East;
import org.gnubridge.core.Game;
import org.gnubridge.core.North;
import org.gnubridge.core.Player;
import org.gnubridge.core.South;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;

public class GBController {

	private final MainView view;
	private BiddingController biddingController;
	private GameController gameController;

	public GBController(MainView view) {
		this.view = view;
		setBiddingController(new BiddingController(view.getBiddingView(), this));
	}

	public void playGame() {
		setGameController(new GameController(this, getBiddingController().getAuction().getHighBid(),
				repositionHandsSoThatSouthIsDeclarer(getBiddingController().getAuction(), getBiddingController()
						.getCardHolder()), getBiddingController().allowHumanToPlayIfDummy(), view.getPlayView()));
	}

	private Game repositionHandsSoThatSouthIsDeclarer(Auctioneer a, Game cardHolder) {
		Game result = new Game(a.getHighBid().getTrump());
		result.getPlayer(a.getDummyOffsetDirection(North.i())).init(cardHolder.getPlayer(North.i()).getHand());
		result.getPlayer(a.getDummyOffsetDirection(East.i())).init(cardHolder.getPlayer(East.i()).getHand());
		result.getPlayer(a.getDummyOffsetDirection(South.i())).init(cardHolder.getPlayer(South.i()).getHand());
		result.getPlayer(a.getDummyOffsetDirection(West.i())).init(cardHolder.getPlayer(West.i()).getHand());
		result.setNextToPlay(West.i().getValue());
		return result;
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
