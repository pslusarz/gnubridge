package org.gnubridge.presentation.gui;

import org.gnubridge.core.Direction;
import org.gnubridge.core.Game;
import org.gnubridge.core.bidding.Bid;

public interface PlayView {

	public static final long DOUBLE_CLICK_DELAY_MS = 750;

	public abstract void setController(GameController c);

	public abstract void setGame(Game g, Direction human);

	public abstract void gameStateChanged();

	public abstract void show();

	public abstract void displayPreviousTrick();

	public abstract void displayCurrentTrick();

	public abstract void display(String message);

	public abstract int getTableBottom();

	public abstract void setContract(Bid contract);

}