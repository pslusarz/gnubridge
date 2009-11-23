package org.jbridge.presentation.gui;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.gnubridge.core.Direction;
import org.gnubridge.core.Game;
import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.Pass;
import org.gnubridge.core.deck.Ace;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Four;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Ten;
import org.gnubridge.presentation.gui.GBController;
import org.gnubridge.presentation.gui.MainView;
import org.gnubridge.presentation.gui.MainViewImpl;

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
				Game.setPreInitializedGame(constructGame());
				MainView mw = new MainViewImpl("gnubridge");
				GBController controller = new GBController(mw);
				controller.getBiddingController().getAuction().bid(new Bid(1, NoTrump.i()));
				controller.getBiddingController().getAuction().bid(new Pass());
				controller.getBiddingController().getAuction().bid(new Pass());
				controller.getBiddingController().getAuction().bid(new Pass());
				controller.getBiddingController().playGame();
				mw.show();
			}

		});

	}

	private static Game constructGame() {
		Game game = new Game(NoTrump.i());
		game.getWest().init(new Hand("", "8,4", "2", ""));//E: AH, W: 4H???
		game.getNorth().init(new Hand("3", "", "5,4", ""));
		game.getEast().init(new Hand("", "A,6,5", "", ""));
		game.getSouth().init(new Hand("", "10", "3", "3"));
		game.setNextToPlay(Direction.EAST);
		game.play(Ace.of(Hearts.i()));
		game.play(Ten.of(Hearts.i()));
		game.play(Four.of(Hearts.i()));
		game.play(Four.of(Diamonds.i()));
		System.out.println("FGFGFG Previous trick: " + game.getPreviousTrick());
		return game;
	}
}
