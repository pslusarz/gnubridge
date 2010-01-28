package org.jbridge.presentation.gui;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.presentation.gui.BiddingController;
import org.gnubridge.presentation.gui.BiddingView;
import org.gnubridge.presentation.gui.ScoringTracker;

public class MockBiddingView implements BiddingView {

	private boolean vulnerabilitySet;

	@Override
	public void auctionStateChanged() {
		// TODO Auto-generated method stub

	}

	@Override
	public void display(String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAuction(Auctioneer auction) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCards(Hand hand) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setController(BiddingController c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	public boolean isVulnerabilitySet() {
		return vulnerabilitySet;
	}

	@Override
	public void displayScore(ScoringTracker scoringTracker) {
		vulnerabilitySet = true;

	}
}
