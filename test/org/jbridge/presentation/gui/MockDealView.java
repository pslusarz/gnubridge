package org.jbridge.presentation.gui;

import org.gnubridge.core.Deal;
import org.gnubridge.core.Direction;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.presentation.gui.CardPlayedListener;
import org.gnubridge.presentation.gui.DealView;
import org.gnubridge.presentation.gui.ScoringTracker;

public class MockDealView implements DealView {

	private ScoringTracker scoringTracker;
	private boolean startingScoreSet;
	private boolean finalScoreSet;

	@Override
	public void display(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayCurrentTrick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayPreviousTrick() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getTableBottom() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setContract(Bid contract) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setListener(CardPlayedListener c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setGame(Deal g, Direction human) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayTimeRemaining(int i) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	public boolean isStartingScoreSet() {
		return startingScoreSet;
	}

	public boolean isFinalScoreSet() {
		return finalScoreSet;
	}

	@Override
	public void displayStartingScore(ScoringTracker scoringTracker) {
		this.scoringTracker = scoringTracker;
		this.startingScoreSet = true;
	}

	@Override
	public void displayFinalScore(ScoringTracker scoringTracker) {
		this.scoringTracker = scoringTracker;
		this.finalScoreSet = true;
	}

	public ScoringTracker getScoringTracker() {
		return scoringTracker;
	}

}
