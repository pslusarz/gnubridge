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
		assertFalse("precondition", ((MockBiddingView) mw.getBiddingView()).isVulnerabilitySet());
		new MainController();
		assertTrue(((MockBiddingView) mw.getBiddingView()).isVulnerabilitySet());
	}

	public void testWhenGameStartsScoreIsSetOnDealView() {
		MockMainView mw = new MockMainView("gnubridge");
		ViewFactory.setMockMainView(mw);
		MainController mainController = new MainController();
		mainController.getBiddingController().placeBid(7, "NT");
		assertFalse("precondition", ((MockDealView) mw.getDealView()).isScoreSet());
		mainController.playGame();
		assertTrue(((MockDealView) mw.getDealView()).isScoreSet());
	}

	public void testWhenGameStartsVulnerabilityOnScoringTrackerIsReset() {
		MockScoringTracker mockTracker = new MockScoringTracker();
		ScoringTracker.setInstance(mockTracker);
		assertNull("precondition", mockTracker.getUsThemVulnerability());
		new MainController();
		assertNotNull((mockTracker.getUsThemVulnerability()));
	}

	public void testNewGameResetsVulnerabilityOnScoringTrackerIsReset() {
		MockScoringTracker mockTracker = new MockScoringTracker();
		ScoringTracker.setInstance(mockTracker);
		MainController mainController = new MainController();
		UsThemVulnerability initialVulnerability = mockTracker.getUsThemVulnerability();
		assertSame("precondition", initialVulnerability, mockTracker.getUsThemVulnerability());
		mainController.newGame();
		assertNotSame(initialVulnerability, mockTracker.getUsThemVulnerability());
	}
}
