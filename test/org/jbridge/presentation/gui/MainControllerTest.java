package org.jbridge.presentation.gui;

import junit.framework.TestCase;

import org.gnubridge.presentation.gui.MainController;
import org.gnubridge.presentation.gui.ViewFactory;

public class MainControllerTest extends TestCase {
	public void testWhenBiddingStartsVulnerabilityIsSetOnBiddingView() {
		MockMainView mw = new MockMainView("gnubridge");
		ViewFactory.setMockMainView(mw);
		MainController mainController = new MainController();
		assertTrue(((MockBiddingView) mw.getBiddingView()).isVulnerabilitySet());
	}

	public void testWhenGameStartsScoreIsSetOnDealView() {
		MockMainView mw = new MockMainView("gnubridge");
		ViewFactory.setMockMainView(mw);
		MainController mainController = new MainController();
		mainController.getBiddingController().placeBid(7, "NT");
		mainController.playGame();
		assertTrue(((MockDealView) mw.getDealView()).isScoreSet());
	}
}
