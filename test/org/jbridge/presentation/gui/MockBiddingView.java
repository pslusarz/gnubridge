package org.jbridge.presentation.gui;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.presentation.gui.BiddingController;
import org.gnubridge.presentation.gui.BiddingView;
import org.gnubridge.presentation.gui.ScoringTracker;

public class MockBiddingView implements BiddingView {

	private boolean vulnerabilitySet;

	public void auctionStateChanged() {
		// TODO Auto-generated method stub

	}

	public void display(String msg) {
		// TODO Auto-generated method stub

	}

	public void hide() {
		// TODO Auto-generated method stub

	}

	public void setAuction(Auctioneer auction) {
		// TODO Auto-generated method stub

	}

	public void setCards(Hand hand) {
		// TODO Auto-generated method stub

	}

	public void setController(BiddingController c) {
		// TODO Auto-generated method stub

	}

	public void show() {
		// TODO Auto-generated method stub

	}

	public boolean isVulnerabilitySet() {
		return vulnerabilitySet;
	}

	public void displayScore(ScoringTracker scoringTracker) {
		vulnerabilitySet = true;

	}
}
