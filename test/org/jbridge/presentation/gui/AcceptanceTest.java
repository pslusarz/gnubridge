package org.jbridge.presentation.gui;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import junit.framework.TestCase;

import org.gnubridge.core.Card;
import org.gnubridge.core.Game;
import org.gnubridge.core.Player;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.presentation.GameUtils;
import org.gnubridge.presentation.gui.GBController;
import org.gnubridge.presentation.gui.MainView;
import org.gnubridge.search.ConfigurableRuntimeSettings;
import org.gnubridge.search.ConfigurableRuntimeSettingsFactory;

public class AcceptanceTest extends TestCase {

	private static final int SEARCH_DEPTH_DURING_TEST = 4;
	private static final int TRICKS_PER_DEAL = 6;

	

	public void setUp() {
		ConfigurableRuntimeSettingsFactory.set(new TestingSettings(
				SEARCH_DEPTH_DURING_TEST));
	}

	public void testAutomatedBidding() {
		GBController mainController = makeController();
		Auctioneer auction = mainController.getBiddingController().getAuction();
		int initialBids = auction.getCalls().size();
		mainController.getBiddingController().placeBid(4, "NT");
		System.out.println(auction.getCalls());
		assertEquals(
				"Automated bidding was not performed after human placed bid",
				initialBids + 4, auction.getCalls().size());
	}

	public void testBiddingHighestEndsAuction() {
		preInitializeGameWithHumanToBidFirst();
		GBController mainController = makeController();
		Auctioneer auction = mainController.getBiddingController().getAuction();
		mainController.getBiddingController().placeBid(7, "NT");
		assertEquals("3 passes should follow 7NT bid", 4, auction.getCalls()
				.size());
		assertTrue("Auction not complete even though highest bid was placed",
				auction.biddingFinished());
	}

	public void testPlayGameEndToEndTakeNoTricks() throws InterruptedException,
			InvocationTargetException {
		preInitializeGameWithSingleColorSuits();
		GBController mainController = makeController();
		mainController.getBiddingController().placeBid(7, "NT");
		mainController.playGame();
		playGameToTheEnd(mainController);
		System.out.println("Game finished. Declarers took "
				+ mainController.getGameController().getGame().getTricksTaken(
						Player.NORTH_SOUTH) + " tricks.");
		assertEquals(0, mainController.getGameController().getGame()
				.getTricksTaken(Player.NORTH_SOUTH));
	}

	public void testPlayRandomGame() throws InterruptedException,
			InvocationTargetException {
		preInitializeRandomGame();
		GBController mainController = makeController();
		mainController.getBiddingController().placeBid(7, "NT");
		mainController.playGame();
		playGameToTheEnd(mainController);
		System.out.println("Game finished. Declarers took "
				+ mainController.getGameController().getGame().getTricksTaken(
						Player.NORTH_SOUTH) + " tricks.");
		assertEquals(TRICKS_PER_DEAL, mainController.getGameController()
				.getGame().getTricksTaken(Player.NORTH_SOUTH)
				+ mainController.getGameController().getGame().getTricksTaken(
						Player.WEST_EAST));
	}

	public void testPlayGameEndToEndTrumpAllTricks()
			throws InterruptedException, InvocationTargetException {
		preInitializeGameWithSingleColorSuits();
		GBController mainController = makeController();
		mainController.getBiddingController().placeBid(7, "Spades"); // TODO:
																		// what
																		// are
																		// valid
																		// names?
																		// hidden
																		// in
																		// biddingControls
																		// -
																		// force
																		// programmaticaly
		mainController.playGame();
		playGameToTheEnd(mainController);
		System.out.println("Game finished. Declarers took "
				+ mainController.getGameController().getGame().getTricksTaken(
						Player.NORTH_SOUTH) + " tricks.");
		assertEquals(TRICKS_PER_DEAL, mainController.getGameController()
				.getGame().getTricksTaken(Player.NORTH_SOUTH));
	}

	private void playGameToTheEnd(GBController mainController)
			throws InterruptedException {
		Game game = mainController.getGameController().getGame();
		int cardsPlayed = -1;
		while (!game.isDone()) {
			int previousCardsPlayed = cardsPlayed;
			cardsPlayed = game.getPlayedCards().getCardsHighToLow().size();
			assertTrue(
					"Expecting to play at least one card each time through the main loop",
					previousCardsPlayed < cardsPlayed);
			assertTrue("Game not done, but played cards " + cardsPlayed,
					cardsPlayed < 52);
			if (mainController.getGameController().humanHasMove()) {
				List<Card> possibleMoves = game.getNextToPlay()
						.getPossibleMoves(game.getCurrentTrick());
				System.out.println("Human about to play: "
						+ possibleMoves.get(0));
				mainController.getGameController().playCard(
						possibleMoves.get(0));
				continue;
			} else {
				boolean cardPlayed = false;
				for (int i = 0; i < 4000; i++) {
					Thread.sleep(100);
					if (i % 10 == 0) {
						System.out.print(".");
					}
					if (game.getPlayedCards().getCardsHighToLow().size() > cardsPlayed) {
						System.out.print(game.getCurrentTrick() + "\n");
						cardPlayed = true;
						break;
					}

				}
				assertTrue("No card was played in 400 seconds", cardPlayed);
			}
		}
		System.out.println("");
	}
	
	private void preInitializeGameWithHumanToBidFirst() {
		Game g = new Game(null);
		GameUtils.initializeRandom(g, TRICKS_PER_DEAL);
		g.setHumanPlayer(g.getWest());
		Game.setPreInitializedGame(g);
	}

	private void preInitializeGameWithSingleColorSuits() {
		Game g = new Game(null);
		GameUtils.initializeSingleColorSuits(g, TRICKS_PER_DEAL);
		g.setHumanPlayer(g.getWest());
		System.out.println("Human's hand: " + g.getWest().getHand());
		Game.setPreInitializedGame(g);
	}

	private void preInitializeRandomGame() {
		Game g = new Game(null);
		GameUtils.initializeRandom(g, TRICKS_PER_DEAL);
		g.setHumanPlayer(g.getNorth());
		System.out.println("West's hand: " + g.getWest().getHand());
		System.out.println("Human's hand: " + g.getNorth().getHand());
		System.out.println("East's hand: " + g.getEast().getHand());
		System.out.println("South's hand: " + g.getSouth().getHand());
		Game.setPreInitializedGame(g);
	}

	private GBController makeController() {
		MainView mw = new MainView("gnubridge");
		return new GBController(mw);
	}
	
	public class TestingSettings extends ConfigurableRuntimeSettings {

		private int depth = 1;

		public TestingSettings(int i) {
			depth = i;
		}

		@Override
		public int getSearchDepthRecommendation(Game game) {
			return depth;
		}

		@Override
		public long getMilisecondsToDisplayLastTrick() {
			return 0;
		}

	}
}
