package org.jbridge.presentation.gui;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.gnubridge.core.Game;
import org.gnubridge.core.South;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.presentation.GameUtils;
import org.gnubridge.presentation.gui.MainViewImpl;

public class AnalysisTool {
	public static void main(String[] args) throws InterruptedException, InvocationTargetException {

		buildGui();

	}

	private static void buildGui() throws InterruptedException, InvocationTargetException {
		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				Game g = createSampleGame();
				MainViewImpl mainView = new MainViewImpl("Gnubridge Analysis Mode");
				AnalysisView pv = new AnalysisView(mainView);
				pv.show();
				mainView.show();
				pv.setGame(g, South.i());
				pv.setContract(new Bid(1, g.getTrump()));
				pv.setListener(new MockCardPlayedListener());
				pv.displayCurrentTrick();

			}
		});
	}

	private static Game createSampleGame() {
		Game g = new Game(NoTrump.i());
		GameUtils.initializeRandom(g, 13);
		g.setHumanPlayer(g.getSouth());
		return g;
	}
}
