package org.gnubridge.presentation.gui;

import java.util.ArrayList;
import java.util.List;

import org.gnubridge.core.Direction;
import org.gnubridge.core.Deal;

public abstract class HandDisplay {
	protected final Direction human;
	protected final Direction player;
	protected final Deal game;
	protected final CardPanelHost owner;
	List<CardPanel> cards;

	public HandDisplay(Direction human, Direction player, Deal game, CardPanelHost owner) {
		this.human = human;
		this.player = player;
		this.game = game;
		this.owner = owner;
		cards = new ArrayList<CardPanel>();
	}

	public abstract void display();

	protected void dispose(List<CardPanel> trash) {
		for (CardPanel card : trash) {
			card.dispose();
		}
		trash.clear();

	}
}
