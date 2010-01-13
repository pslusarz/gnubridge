package org.gnubridge.presentation.gui;

import org.gnubridge.core.Deal;
import org.gnubridge.core.Direction;
import org.gnubridge.core.bidding.Bid;

public interface DealView {

	public static final long DOUBLE_CLICK_DELAY_MS = 750;

	public abstract void setListener(CardPlayedListener c);

	public abstract void setGame(Deal g, Direction human);

	//public abstract void gameStateChanged();

	public abstract void displayPreviousTrick();

	public abstract void displayCurrentTrick();

	public abstract void display(String message);

	public abstract void displayScore(String message);

	public abstract int getTableBottom();

	public abstract void setContract(Bid contract);

	public abstract void displayTimeRemaining(int i);

	public abstract void hide();

	public abstract void gameFinished();

}