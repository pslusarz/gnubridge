package org.jbridge.presentation.gui;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.gnubridge.core.Direction;
import org.gnubridge.core.Deal;
import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.Pass;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.presentation.gui.MainController;

/**
 * Use this class to manually verify the play screen under various conditions
 */

public class PlayVerificationTool {
	public static void main(String[] args) throws InterruptedException, InvocationTargetException {

		buildGui();

	}

	private static void buildGui() throws InterruptedException, InvocationTargetException {
		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				Deal.setPreInitializedGame(constructGame());
				MainController controller = new MainController();
				controller.getBiddingController().getAuction().bid(new Bid(1, NoTrump.i()));
				controller.getBiddingController().getAuction().bid(new Pass());
				controller.getBiddingController().getAuction().bid(new Pass());
				controller.getBiddingController().getAuction().bid(new Pass());
				controller.getBiddingController().playGame();
			}

		});

	}

	private static Deal constructGame() {
		Deal game = new Deal(NoTrump.i());
		game.getWest().init(new Hand("", "8", "", ""));
		game.getNorth().init(new Hand("3", "", "", ""));
		game.getEast().init(new Hand("A", "", "", ""));
		game.getSouth().init(new Hand("", "", "", "3"));
		game.setNextToPlay(Direction.EAST_DEPRECATED);
		return game;
	}
}
