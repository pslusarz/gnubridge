package org.gnubridge.presentation.gui;

import java.util.ArrayList;
import java.util.List;

import org.gnubridge.core.Direction;
import org.gnubridge.core.Game;

public abstract class HandDisplay {
	protected final Direction human;
	protected final Direction player;
	protected final Game game;
	protected final PlayView owner;
	List<CardPanel> cards;
	
	public HandDisplay(Direction human, Direction player, Game game, PlayView owner) {
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
