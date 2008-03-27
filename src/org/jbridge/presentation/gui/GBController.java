package org.jbridge.presentation.gui;

import javax.swing.SwingWorker;

import org.gnubridge.core.Card;
import org.gnubridge.core.Direction;
import org.gnubridge.core.East;
import org.gnubridge.core.Game;
import org.gnubridge.core.North;
import org.gnubridge.core.South;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.search.Search;

public class GBController {

//	public class SearchWorker extends SwingWorker<Void, String> {
//		int seconds = 0;
//
//		@Override
//		protected Void doInBackground() throws Exception {
//			for (; seconds < 30; seconds++) {
//				Thread.sleep(1000);
//				publish("Seconds: " + seconds);
//			}
//			return null;
//		}
//
//		@Override
//		protected void process(List<String> messages) {
//			view.display(messages.get(messages.size() - 1));
//		}
//
//		@Override
//		public void done() {
//			view.display("Search finished.");
//		}
//
//	}
	public class SearchWorker extends SwingWorker<Void, String> {
		Search search;
		@Override
		protected Void doInBackground() throws Exception {
			search = new Search(game);
			search.setMaxTricks(2);
			search.search();
			return null;
		}
		
		@Override
		public void done() {
			playCard(search.getBestMoves().get(0));
		}
		
	}

	private MainWindow view;
	private Game game;
	private Direction humanDirection;
	private BiddingController biddingController;

	public GBController(MainWindow view) {
		this.view = view;
		view.setController(this);
		biddingController = new BiddingController(view.getBiddingView());
	}





	public void playGame() {
		game = makeGame(biddingController.getAuction(), biddingController.getCardHolder());
		humanDirection = allowHumanToPlayIfDummy();
		view.setGame(game, humanDirection);
		doAutomatedPlay();
	}
	
	public void playCard(Card c) {
	  game.play(c);
	  view.gameStateChanged();
	  doAutomatedPlay();
	}

	private void doAutomatedPlay() {
		if (humanHasMove(humanDirection, game)) {
			return;
		}
		SearchWorker w = new SearchWorker();
		w.execute();

	}

	private boolean humanHasMove(Direction player, Game g) {
		Direction nextToMove = g.getNextToPlay().getDirection2();
		if (player.equals(nextToMove)
				|| (player.equals(South.i()) && nextToMove.equals(North.i()))) {
			return true;
		} else {
			return false;
		}
	}

	private Direction allowHumanToPlayIfDummy() {
		Direction newHuman = biddingController.getAuction().getDummyOffsetDirection(biddingController.getHuman()
				.getDirection2());
		if (North.i().equals(newHuman)) {
			newHuman = South.i();
		}
		return newHuman;
	}

	private Game makeGame(Auctioneer a, Game cardHolder) {
		Game result = new Game(a.getHighBid().getTrump());

		result.getPlayer(a.getDummyOffsetDirection(North.i())).init(
				cardHolder.getPlayer(North.i()).getHand());
		result.getPlayer(a.getDummyOffsetDirection(East.i())).init(
				cardHolder.getPlayer(East.i()).getHand());
		result.getPlayer(a.getDummyOffsetDirection(South.i())).init(
				cardHolder.getPlayer(South.i()).getHand());
		result.getPlayer(a.getDummyOffsetDirection(West.i())).init(
				cardHolder.getPlayer(West.i()).getHand());
		result.setNextToPlay(West.i().getValue());
		return result;
	}


}
