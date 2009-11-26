package org.jbridge.presentation.gui;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.gnubridge.core.Direction;
import org.gnubridge.core.Game;
import org.gnubridge.core.Hand;
import org.gnubridge.core.South;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.deck.Ace;
import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Eight;
import org.gnubridge.core.deck.Five;
import org.gnubridge.core.deck.Four;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.Jack;
import org.gnubridge.core.deck.Nine;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Queen;
import org.gnubridge.core.deck.Seven;
import org.gnubridge.core.deck.Six;
import org.gnubridge.core.deck.Spades;
import org.gnubridge.core.deck.Ten;
import org.gnubridge.core.deck.Three;
import org.gnubridge.core.deck.Two;
import org.gnubridge.presentation.GameUtils;
import org.gnubridge.presentation.gui.MainViewImpl;

public class AnalysisTool {
	public static void main(String[] args) throws InterruptedException, InvocationTargetException {

		buildGui();

	}

	private static void buildGui() throws InterruptedException, InvocationTargetException {
		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				Game g = buildAnotherGame();
				MainViewImpl mainView = new MainViewImpl("GNUBridge Analysis Mode");
				AnalysisView pv = new AnalysisView(mainView);
				pv.show();
				mainView.show();
				pv.setGame(g, South.i());
				pv.setContract(new Bid(1, NoTrump.i()));
				pv.setListener(new MockCardPlayedListener());
				pv.displayCurrentTrick();

			}
		});
	}

	@SuppressWarnings("unused")
	private static Game createCounterexample() {
		Game game = new Game(NoTrump.i());
		game.getWest().init(new Hand("", "8,4", "2", ""));//E: AH, W: 4H???
		game.getNorth().init(new Hand("3", "", "5,4", ""));
		game.getEast().init(new Hand("", "A,6,5", "", ""));
		game.getSouth().init(new Hand("", "10", "3", "3"));
		game.setNextToPlay(Direction.EAST);
		game.play(Ace.of(Hearts.i()));
		game.play(Ten.of(Hearts.i()));
		return game;
	}

	private static Game buildAnotherGame() {
		Game game = new Game(null);
		game.getWest().init(Two.of(Diamonds.i()), Six.of(Spades.i()), Jack.of(Diamonds.i()), Eight.of(Hearts.i()),
				Three.of(Clubs.i()));
		game.getNorth().init(Five.of(Diamonds.i()), Eight.of(Diamonds.i()), Eight.of(Clubs.i()), Ace.of(Diamonds.i()),
				Four.of(Clubs.i()));
		game.getEast().init(Queen.of(Diamonds.i()), Nine.of(Diamonds.i()), Ace.of(Clubs.i()), Queen.of(Hearts.i()),
				Six.of(Clubs.i()));
		game.getSouth().init(Seven.of(Hearts.i()), Seven.of(Diamonds.i()), Nine.of(Spades.i()), Six.of(Hearts.i()),
				Ten.of(Spades.i()));
		game.setNextToPlay(Direction.WEST);
		game.setTrump(NoTrump.i());
		game.play(Two.of(Diamonds.i()));
		return game;
	}

	@SuppressWarnings("unused")
	private static Game createSampleGame() {
		Game g = new Game(null);
		GameUtils.initializeRandom(g, 13);
		g.setHumanPlayer(g.getSouth());
		return g;
	}
}
