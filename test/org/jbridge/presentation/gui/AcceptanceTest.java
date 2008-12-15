package org.jbridge.presentation.gui;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import junit.framework.TestCase;

import org.gnubridge.core.Card;
import org.gnubridge.core.Game;
import org.gnubridge.core.Player;
import org.gnubridge.presentation.gui.GBController;
import org.gnubridge.presentation.gui.MainView;

public class AcceptanceTest extends TestCase {
	public void testPlayGameEndToEnd() throws InterruptedException,
			InvocationTargetException {
		MainView mw = new MainView("gnubridge");
		GBController mainController = new GBController(mw);
		mainController.getBiddingController().placeBid(7, "NT");
		assertTrue(mainController.getBiddingController().getAuction()
				.biddingFinished());
		System.out.println("High bid: "
				+ mainController.getBiddingController().getAuction()
						.getHighCall().getBid()
				+ " bid by "
				+ mainController.getBiddingController().getAuction()
						.getHighCall().getDirection());
		mainController.playGame();
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
				for (int i = 0; i < 400; i++) {
					Thread.sleep(1000);
					System.out.print(".");
					if (game.getPlayedCards().getCardsHighToLow().size() > cardsPlayed) {
						System.out.print(game.getCurrentTrick()+"\n");
						cardPlayed = true;
						break;
					}

				}
				assertTrue("No card was played in 400 seconds", cardPlayed);
			}
		}
		System.out.println("");
		System.out.println("Game finished. Declarers took "+mainController.getGameController().getGame().getTricksTaken(Player.NORTH_SOUTH)+ " tricks.");
	}
}
