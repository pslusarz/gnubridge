package org.gnubridge.presentation.gui;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
import org.gnubridge.search.ConfigurableRuntimeSettingsFactory;

public class GameController {
	public static int MAX_SECONDS_TO_MOVE = 45;
	public class SearchController extends SwingWorker<Void, String> {
		private static final int MILISECONDS_PER_SECOND = 1000;
		private static final long RIDICULOUSLY_LONG_WAIT_TIME = 100000000;
		private final long TIME_ALLOTED_PER_MOVE = MAX_SECONDS_TO_MOVE * MILISECONDS_PER_SECOND;
		Card bestMove;

		@Override
		protected Void doInBackground() throws Exception {
			long start = System.currentTimeMillis();			
			bestMove = findBestMoveAtDepth(1, RIDICULOUSLY_LONG_WAIT_TIME); 		
			for (int tricksSearchDepth = 2; tricksSearchDepth <= ConfigurableRuntimeSettingsFactory
					.get().getSearchDepthRecommendation(game); tricksSearchDepth++) {
				long timePassedSinceStart = System.currentTimeMillis() - start;
				long timeRemaining = TIME_ALLOTED_PER_MOVE - timePassedSinceStart;
				if (!haveEnoughTimeToAttemptNextSearch(timeRemaining)) {
					System.out.println("// not enough time to attempt next search");
					break;
				}
				System.out.println("// now searching depth: "+tricksSearchDepth);
				try {
					bestMove = findBestMoveAtDepth(tricksSearchDepth, timeRemaining);
				} catch (TimeoutException e) {
					System.out.println("// could not complete full search of depth "+tricksSearchDepth+", current best: "+bestMove);
					break;
				}
			}
			return null;
		}

		private boolean haveEnoughTimeToAttemptNextSearch(long timeRemaining) {
			return timeRemaining > TIME_ALLOTED_PER_MOVE * 2 / 3;
		}

		private Card findBestMoveAtDepth(int tricksSearchDepth, long timeoutMs) throws InterruptedException, ExecutionException, TimeoutException {
			SearchWorker searchWorker = new SearchWorker(tricksSearchDepth);
			searchWorker.execute();
			return searchWorker.get(timeoutMs,TimeUnit.MILLISECONDS);
		}

		@Override
		public void done() {
			playCard(bestMove);
		}
	}

	public class SearchWorker extends SwingWorker<Card, String> {
		Search search;
		private final int maxTricksSearchDepth;

		public SearchWorker(int maxTricksSearchDepth) {
			this.maxTricksSearchDepth = maxTricksSearchDepth;

		}

		@Override
		protected Card doInBackground() throws Exception {
			search = new Search(game);
			search.setMaxTricks(maxTricksSearchDepth);
			search.search();
			return search.getBestMoves().get(0);
		}

		// @Override
		// public void done() {
		// playCard(search.getBestMoves().get(0));
		// }
	}

	public class TrickDisplayWorker extends SwingWorker<Void, String> {
		boolean previousTrickDisplayed = false;

		@Override
		protected Void doInBackground() throws Exception {
			if (game.getCurrentTrick().getCards().size() == 0
					&& game.getPreviousTrick() != null) {
				view.displayPreviousTrick();
				previousTrickDisplayed = true;
			}
			return null;
		}

		@Override
		public void done() {
			if (previousTrickDisplayed) {
				try {
					Thread.sleep(ConfigurableRuntimeSettingsFactory.get()
							.getMilisecondsToDisplayLastTrick());
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				view.displayCurrentTrick();
			}
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
		game.printHandsDebug();
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

	public synchronized void playCard(Card c) {
		System.out.println("game.play(" + c.toDebugString() + ");");
		game.play(c);
		TrickDisplayWorker tdw = new TrickDisplayWorker();
		tdw.execute();
		while (!tdw.isDone()) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
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

		new SearchController().execute();

	}

}
