package org.gnubridge.presentation.gui;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.gnubridge.core.Card;
import org.gnubridge.core.Deal;
import org.gnubridge.core.Direction;
import org.gnubridge.core.North;
import org.gnubridge.core.South;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.search.DoubleDummySolver;
import org.gnubridge.search.ProductionSettings;
import org.jdesktop.swingworker.SwingWorker;

public class DealController implements CardPlayedListener {
	private static final int COMPUTER_PLAYER_IS_THINKING = 0;
	public static int MAX_SECONDS_TO_MOVE = 15;
	long start = -1;
	private static final int MILISECONDS_PER_SECOND = 1000;
	private static final long RIDICULOUSLY_LONG_WAIT_TIME = 100000000;
	private final long TIME_ALLOTED_PER_MOVE = MAX_SECONDS_TO_MOVE * MILISECONDS_PER_SECOND;

	public class Clock extends Thread {
		@Override
		public void run() {
			while (start >= COMPUTER_PLAYER_IS_THINKING) {
				view
						.displayTimeRemaining((int) (0.001 * (TIME_ALLOTED_PER_MOVE - (System.currentTimeMillis() - start))));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
			view.displayTimeRemaining(-1);
		}
	}

	public class SearchController extends SwingWorker<Void, String> {
		private static final int COMPUTER_PLAYER_FINISHED_THINKING = -1;
		Card bestMove;

		@Override
		protected Void doInBackground() throws Exception {
			start = System.currentTimeMillis();
			bestMove = findBestMoveAtDepth(1, RIDICULOUSLY_LONG_WAIT_TIME);
			for (int tricksSearchDepth = 2; tricksSearchDepth <= ProductionSettings.getSearchDepthRecommendation(game); tricksSearchDepth++) {
				long timePassedSinceStart = System.currentTimeMillis() - start;
				long timeRemaining = TIME_ALLOTED_PER_MOVE - timePassedSinceStart;
				if (!haveEnoughTimeToAttemptNextSearch(timeRemaining)) {
					System.out.println("// not enough time to attempt next search");
					break;
				}
				System.out.println("// now searching depth: " + tricksSearchDepth);
				try {
					bestMove = findBestMoveAtDepth(tricksSearchDepth, timeRemaining);
				} catch (TimeoutException e) {
					System.out.println("// could not complete full search of depth " + tricksSearchDepth
							+ ", current best: " + bestMove);
					break;
				}
			}
			start = COMPUTER_PLAYER_FINISHED_THINKING;
			return null;
		}

		private boolean haveEnoughTimeToAttemptNextSearch(long timeRemaining) {
			return timeRemaining > TIME_ALLOTED_PER_MOVE * 2 / 3;
		}

		private Card findBestMoveAtDepth(int tricksSearchDepth, long timeoutMs) throws InterruptedException,
				ExecutionException, TimeoutException {
			SearchWorker searchWorker = new SearchWorker(tricksSearchDepth);
			searchWorker.execute();
			return searchWorker.get(timeoutMs, TimeUnit.MILLISECONDS);
		}

		@Override
		public void done() {
			playCard(bestMove);
		}
	}

	public class SearchWorker extends SwingWorker<Card, String> {
		DoubleDummySolver search;
		private final int maxTricksSearchDepth;

		public SearchWorker(int maxTricksSearchDepth) {
			this.maxTricksSearchDepth = maxTricksSearchDepth;

		}

		@Override
		protected Card doInBackground() throws Exception {
			search = new DoubleDummySolver(game);
			search.setMaxTricks(maxTricksSearchDepth);
			search.search();
			return search.getBestMoves().get(0);
		}
	}

	public class PreviousTrickDisplayWorker extends SwingWorker<Void, String> {

		@Override
		protected Void doInBackground() throws Exception {
			view.displayPreviousTrick();
			return null;
		}

		@Override
		public void done() {
			try {
				Thread.sleep(ProductionSettings.getMilisecondsToDisplayLastTrick());
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			if (!game.isDone()) {
				view.displayCurrentTrick();
			}

		}
	}

	private final MainController parent;
	private final Deal game;
	private final Direction human;

	private final DealView view;
	private final ScoringTracker scoringTracker;
	private final Bid highBid;

	public DealController(MainController controller, Bid highBid, Deal g, Direction humanDir, DealView playView,
			ScoringTracker tracker) {
		parent = controller;
		this.highBid = highBid;
		game = g;
		this.scoringTracker = tracker;
		game.printHandsDebug();
		human = humanDir;
		view = playView;
		view.setListener(this);
		view.setGame(game, human);
		view.setContract(highBid);
		doAutomatedPlay();
		view.displayStartingScore(scoringTracker);
	}

	public Deal getGame() {
		return game;
	}

	public Direction getHuman() {
		return human;
	}

	public boolean humanHasMove() {
		Direction nextToMove = game.getNextToPlay().getDirection2();
		if (human.equals(nextToMove) || (human.equals(South.i()) && nextToMove.equals(North.i()))) {
			return true;
		} else {
			return false;
		}
	}

	public synchronized void playCard(Card c) {
		System.out.println("game.play(" + c.toDebugString() + ");");
		game.play(c);
		if (game.getCurrentTrick().getCards().size() == 0 && game.getPreviousTrick() != null) {
			displayPreviousTrick();
		} else {
			view.displayCurrentTrick();
		}
		if (game.isDone()) {
			scoringTracker.processFinishedGame(human.getValue(), highBid, game.getDeclarerTricksTaken());
			view.displayFinalScore(scoringTracker);
		} else {
			doAutomatedPlay();
		}
	}

	public void displayPreviousTrick() {
		PreviousTrickDisplayWorker tdw = new PreviousTrickDisplayWorker();
		tdw.execute();
		while (!tdw.isDone()) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

	}

	public void doAutomatedPlay() {
		if (humanHasMove()) {
			return;
		}

		new SearchController().execute();
		start = COMPUTER_PLAYER_IS_THINKING;
		new Clock().start();

	}

	@Override
	public void newGame() {
		view.hide();
		parent.newGame();
	}
}
