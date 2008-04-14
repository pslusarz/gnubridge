package org.gnubridge.presentation.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.gnubridge.core.Card;
import org.gnubridge.core.Direction;
import org.gnubridge.core.East;
import org.gnubridge.core.Game;
import org.gnubridge.core.Hand;
import org.gnubridge.core.North;
import org.gnubridge.core.Player;
import org.gnubridge.core.South;
import org.gnubridge.core.Trick;
import org.gnubridge.core.West;
import org.gnubridge.core.deck.Color;

public class PlayView extends GBContainer {

	private Game game;
	final int DHEIGHT = 700;
	private final int WIDTH = 800;
	
	private Table table;
	private GameController controller;
	private HandDisplay dummy;
	
	private boolean cardPlayed = false;

	public PlayView(MainView owner) {
		super(owner);
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(WIDTH, DHEIGHT));
		panel.setSize(new Dimension(WIDTH, DHEIGHT));
		table = new Table(DHEIGHT);
		
	}

	public void setController(GameController c) {
		controller = c;
	}

	private Container createPlayPane() {
		Container result = new JPanel();
		result.setPreferredSize(new Dimension(WIDTH, DHEIGHT));
		placeOn(result);
		return result;
	}

	public void setGame(Game g, Direction human) {
		game = g;
		table.setHumanDirection(human);
		int i = 0;
		Hand humanHand = new Hand(game.getPlayer(human).getHand());
		for (Card card : humanHand.getCardsHighToLow()) {
			CardPanel cardPanel = new CardPanel(card);
			DaListener listener = new DaListener(cardPanel, game);
			cardPanel.addMouseListener(listener);
			cardPanel.addMouseMotionListener(listener);
			panel.add(cardPanel);
			cardPanel.setLocation(200 + HandDisplay.CARD_OFFSET * i, DHEIGHT
					- CardPanel.IMAGE_HEIGHT - 35);
			panel.setComponentZOrder(cardPanel, 0);
			i++;
		}
        dummy = new HandDisplay(human, North.i(), game, this);
        dummy.display();
	}



	void addCard(CardPanel card) {
        if (card.isPlayable()) {
        	DaListener listener = new DaListener(card, game);
			card.addMouseListener(listener);
			card.addMouseMotionListener(listener);
        }
		panel.add(card);
		panel.setComponentZOrder(card, 0);
		
	}

	protected void dockingCard(boolean isDocking) {
		if (cardPlayed != isDocking) {
			cardPlayed = isDocking;
			table.drawPromptArrow(panel.getGraphics(), game.getNextToPlay().getDirection2(), cardPlayed);
		}
	}



	@Override
	protected JPanel createDisplayPanel() {
		return new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				table.draw(g);
				g.drawString("Trump: " + game.getTrump() + "            " + message
						+ " ", 20, DHEIGHT - 25);
				
				table.drawPromptArrow(g, game.getNextToPlay().getDirection2(), cardPlayed);
			}
		};
	}





	public void gameStateChanged() {
		if (table.isDisplayingPreviousTrick()) {
			return;
		}
		displayCurrentTrick();
		dummy.display();

	}

	void displayCurrentTrick() {
		message = " Tricks taken North/South: "
			+ game.getTricksTaken(Player.NORTH_SOUTH) + " out of "
			+ game.getTricksPlayed();
		table.displayTrick(game.getCurrentTrick(), panel);
		table.setDisplayingPreviousTrick(false);

	}


	class DaListener implements MouseListener, MouseMotionListener {

		private CardPanel theCard;
		private boolean dragging;
		private int startX = -1;
		private int startY = -1;
		private Game theGame;

		public DaListener(CardPanel card, Game g) {
			theCard = card;
			theGame = g;
		}

		public void mouseClicked(MouseEvent arg0) {
		}

		public void mouseEntered(MouseEvent arg0) {
			if (!dragging && CardPanel.canSelect(theCard)
					&& theGame.isLegalMove(theCard.getCard())) {
				theCard.setSelected(true);
			}
		}

		public void mouseExited(MouseEvent arg0) {
			if (!dragging) {
				mouseReleased(arg0);
			}
		}

		public void mousePressed(MouseEvent arg0) {

		}

		public void mouseReleased(MouseEvent arg0) {
			dragging = false;
			if (!theCard.isPlayed() && theCard.isSelected()) {
				theCard.setSelected(false);
			} else if (theCard.isPlayed()) {
				theCard.dispose();
				controller.playCard(theCard.getCard());
				theCard = null;
				dockingCard(false);

			}
		}

		public void mouseDragged(MouseEvent arg0) {
			if (!theCard.isSelected()) {
				return;
			}
			if (!dragging) {
				startX = arg0.getX();
				startY = arg0.getY();
			}
			theCard.setLocation(theCard.getX() + arg0.getX() - startX, theCard
					.getY()
					+ arg0.getY() - startY);
			if (table.contains(theCard)) {
				theCard.setPlayed(true);
				dockingCard(true);
			} else {
				theCard.setPlayed(false);
				dockingCard(false);
			}
			theCard.repaint();
			dragging = true;
		}

		public void mouseMoved(MouseEvent arg0) {

		}

	}

	public void show() {
		owner.setContent(createPlayPane());
	}

	public void displayPreviousTrick() {
		table.setDisplayingPreviousTrick(true);
		table.displayTrick(game.getPreviousTrick(), panel);
		
	}
}
