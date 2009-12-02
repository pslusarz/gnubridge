package org.gnubridge.presentation.gui;

import org.gnubridge.core.Card;

public interface CardPlayedListener {
	public void playCard(Card c);

	public void displayPreviousTrick();

	public void newGame();
}
