package org.gnubridge.presentation.gui;

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

public class GameController {
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

	private GBController parent;
	private Game game;
	private Direction human;
	private PlayView view;

	public GameController(GBController controller, Auctioneer auctioneer,
			Game cardHolder, Direction humanDir, PlayView playView) {
		parent = controller;
		game = makeGame(auctioneer, cardHolder);
		human = humanDir;
		view = playView;
		view.setController(this);
		view.setGame(game, human);
		doAutomatedPlay();
	}

	public Game getGame() {
		return game;
	}

	public Direction getHuman() {
		return human;
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

	public boolean humanHasMove() {
		Direction nextToMove = game.getNextToPlay().getDirection2();
		if (human.equals(nextToMove)
				|| (human.equals(South.i()) && nextToMove.equals(North.i()))) {
			return true;
		} else {
			return false;
		}
	}

	public void playCard(Card c) {
		game.play(c);
		if (game.isDone()) {
			parent.gameFinished();
		} else {
			view.gameStateChanged();
			doAutomatedPlay();
		}
	}

	public void doAutomatedPlay() {
		if (humanHasMove()) {
			return;
		}
		SearchWorker w = new SearchWorker();
		w.execute();

	}

}
