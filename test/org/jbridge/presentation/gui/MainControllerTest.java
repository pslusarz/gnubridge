package org.jbridge.presentation.gui;

import junit.framework.TestCase;

import org.gnubridge.core.bidding.UsThemVulnerability;
import org.gnubridge.presentation.gui.MainController;
import org.gnubridge.presentation.gui.ScoringTracker;
import org.gnubridge.presentation.gui.ViewFactory;

public class MainControllerTest extends TestCase {
	public void testWhenBiddingStartsVulnerabilityIsSetOnBiddingView() {
		MockMainView mw = new MockMainView("gnubridge");
		ViewFactory.setMockMainView(mw);
		new MainController();
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

	public void testWhenGameStartsVulnerabilityOnScoringTrackerIsReset() {
		MockScoringTracker mockTracker = new MockScoringTracker();
		ScoringTracker.setInstance(mockTracker);
		new MainController();
		assertNotNull((mockTracker.getUsThemVulnerability()));
	}

	public void testNewGameResetsVulnerabilityOnScoringTrackerIsReset() {
		MockScoringTracker mockTracker = new MockScoringTracker();
		ScoringTracker.setInstance(mockTracker);
		MainController mainController = new MainController();
		UsThemVulnerability initialVulnerability = mockTracker.getUsThemVulnerability();
		mainController.newGame();
		assertNotSame(initialVulnerability, mockTracker.getUsThemVulnerability());
	}
}
