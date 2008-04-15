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

	public static final long DOUBLE_CLICK_DELAY_MS = 750;
	private Game game;
	final int DHEIGHT = 700;
	private final int WIDTH = 800;

	private Table table;
	private GameController controller;
	private OneColumnPerColor dummy;

	private boolean cardPlayed = false;
	private AllCardsInOneRow humanHandDisplay;

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
		
		humanHandDisplay = new AllCardsInOneRow(human, human, game, this);
		humanHandDisplay.display();
		dummy = new OneColumnPerColor(human, North.i(), game, this);
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
			table.drawPromptArrow(panel.getGraphics(), game.getNextToPlay()
					.getDirection2(), cardPlayed);
		}
	}

	@Override
	protected JPanel createDisplayPanel() {
		return new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				table.draw(g);
				g.drawString("Trump: " + game.getTrump() + "            "
						+ message + " ", 20, DHEIGHT - 25);

				table.drawPromptArrow(g, game.getNextToPlay().getDirection2(),
						cardPlayed);
			}
		};
	}

	public void gameStateChanged() {
		if (table.isDisplayingPreviousTrick()) {
			return;
		}
		displayCurrentTrick();

	}

	void displayCurrentTrick() {
		message = " Tricks taken North/South: "
				+ game.getTricksTaken(Player.NORTH_SOUTH) + " out of "
				+ game.getTricksPlayed();
		table.displayTrick(game.getCurrentTrick(), panel);
		table.setDisplayingPreviousTrick(false);
		dummy.display();
		humanHandDisplay.display();

	}

	class DaListener implements MouseListener, MouseMotionListener {

		private CardPanel theCard;
		private int startX = -1;
		private int startY = -1;
		private Game theGame;
		long previousClick = -1000;

		public DaListener(CardPanel card, Game g) {
			theCard = card;
			theGame = g;
		}

		public void mouseClicked(MouseEvent arg0) {
			long now = System.currentTimeMillis();
			if (now - previousClick < DOUBLE_CLICK_DELAY_MS
					&& theCard.isSelected()) {
				play();
			}
			previousClick = now;
		}

		public void mouseEntered(MouseEvent arg0) {
			if (!theCard.isDragged() && CardPanel.canSelect(theCard)
					&& theGame.isLegalMove(theCard.getCard())) {
				theCard.setSelected(true);
			}
		}

		public void mouseExited(MouseEvent arg0) {
			if (theCard.isSelected()) {
				theCard.setSelected(false);
			}
		}

		public void mousePressed(MouseEvent arg0) {

		}

		public void mouseReleased(MouseEvent arg0) {
			if (!theCard.isDragged()) {
				return;
			}
			theCard.stopDragging();
			if (!theCard.isPlayed() && theCard.isSelected()) {
				theCard.setSelected(false);
			} else if (theCard.isPlayed()) {
				play();

			}
		}

		private void play() {
			theCard.dispose();
			controller.playCard(theCard.getCard());
			theCard = null;
			dockingCard(false);
		}

		public void mouseDragged(MouseEvent arg0) {
			if (!theCard.isSelected()) {
				return;
			}
			if (!theCard.isDragged()) {
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
			theCard.startDragging();
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
		dummy.display();
		humanHandDisplay.display();

	}

	public int getTableBottom() {
		return (int) (table.getDimensions().getY()+table.getDimensions().getHeight());
	}
}
