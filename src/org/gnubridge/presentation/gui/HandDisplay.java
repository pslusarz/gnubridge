package org.gnubridge.presentation.gui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.gnubridge.core.Card;
import org.gnubridge.core.Direction;
import org.gnubridge.core.East;
import org.gnubridge.core.Game;
import org.gnubridge.core.Hand;
import org.gnubridge.core.South;
import org.gnubridge.core.West;
import org.gnubridge.core.deck.Color;

public class HandDisplay {

	private final Direction human;
	private final Direction player;
	private final Game game;
	private final PlayView owner;
	List<CardPanel> cards;
	final static int CARD_OFFSET = 30;
	
	public HandDisplay(Direction human, Direction player, Game game, PlayView owner) {
		this.human = human;
		this.player = player;
		this.game = game;
		this.owner = owner;
		cards = new ArrayList<CardPanel>();
	}

	public void display() {
			dispose(cards);
			Hand hand = new Hand(game.getPlayer(player).getHand());
			Point dummyUpperLeft = determineDummyPos(human, hand
					.getLongestColorLength());
			for (Color color : Color.list) {
				int j = 0;
				for (Card card : hand.getColorHi2Low(color)) {
					CardPanel cardPanel = new CardPanel(card);
					cards.add(cardPanel);
	        		if (human.equals(South.i())) {
	        			cardPanel.setPlayable(true);
	        		}
	        		owner.addCard(cardPanel);
					cardPanel.setLocation((int) dummyUpperLeft.getX(),
							(int) dummyUpperLeft.getY() + CARD_OFFSET * j);
					j++;
				}
				dummyUpperLeft.setLocation(dummyUpperLeft.getX()
						+ CardPanel.IMAGE_WIDTH + 2, dummyUpperLeft.getY());
			}

	}
	
	private Point determineDummyPos(Direction human, int longestColorLength) {
		if (South.i().equals(human)) {
			return new Point(235, 5);
		} else if (West.i().equals(human)) {
			return new Point(3, owner.DHEIGHT - 500);
		} else if (East.i().equals(human)) {
			return new Point(512, owner.DHEIGHT - 500);
		}
		throw new RuntimeException("human should never have to play as dummy");
	}
	
	private void dispose(List<CardPanel> trash) {
		for (CardPanel card : trash) {
			card.dispose();
		}
		trash.clear();

	}

}
