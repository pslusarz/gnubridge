package org.jbridge.presentation.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import org.gnubridge.core.Card;
import org.gnubridge.core.Direction;
import org.gnubridge.core.East;
import org.gnubridge.core.Game;
import org.gnubridge.core.Hand;
import org.gnubridge.core.North;
import org.gnubridge.core.South;
import org.gnubridge.core.West;
import org.gnubridge.core.deck.Color;

public class PlayControls extends GBContainer {

	private Game game;
	private final int DHEIGHT = 600;
	private final int WIDTH = 800;
	private final int CARD_OFFSET = 30;

	public PlayControls(MainWindow owner) {
		super(owner);
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(WIDTH, DHEIGHT));
	}

	public void setGame(Game g, Direction human) {
		game = g;

		int i = 0;
		Hand humanHand = new Hand(game.getPlayer(human).getHand());
		for (Card card : humanHand.getCardsHighToLow()) {
			CardPanel cardPanel = new CardPanel(card);
			cardPanel.addMouseListener(new DaListener(cardPanel));
			cardPanel.addMouseMotionListener(new DaListener(cardPanel));
			panel.add(cardPanel);
			System.out.println("panel height: " + panel.getHeight());
			cardPanel.setLocation(75 + CARD_OFFSET * i, DHEIGHT
					- CardPanel.IMAGE_HEIGHT - 35);
			panel.setComponentZOrder(cardPanel, 0);
			i++;
		}

		Hand dummyHand = new Hand(game.getPlayer(North.i()).getHand());
		Point dummyUpperLeft = determineDummyPos(human, dummyHand
				.getLongestColorLength());
		for (Color color : Color.list) {
			int j = 0;
			for (Card card : dummyHand.getColorHi2Low(color)) {
				CardPanel cardPanel = new CardPanel(card);
				if (human.equals(South.i())) {
					cardPanel.addMouseListener(new DaListener(cardPanel));
					cardPanel.addMouseMotionListener(new DaListener(cardPanel));
				}
				panel.add(cardPanel);
				cardPanel.setLocation((int) dummyUpperLeft.getX(),
						(int) dummyUpperLeft.getY() + CARD_OFFSET * j);
				panel.setComponentZOrder(cardPanel, 0);
				j++;
			}
			dummyUpperLeft.setLocation(dummyUpperLeft.getX()
					+ CardPanel.IMAGE_WIDTH + 5, dummyUpperLeft.getY());
		}
		panel.repaint();
	}

	private Point determineDummyPos(Direction human, int longestColorLength) {
		if (South.i().equals(human)) {
			return new Point(175, 10);
		} else if (West.i().equals(human)) {
			return new Point(30, 150);
		} else if (East.i().equals(human)) {
			return new Point(600, 150);
		}
		throw new RuntimeException("human should never have to play as dummy");
	}

	@Override
	protected JPanel createDisplayPanel() {
		return new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				//g.drawRect(5, 5, getWidth() - 10, getHeight() - 10);
				g.drawString("Trump: " + game.getTrump(), 20, DHEIGHT - 25);
			}
		};
	}

}

class DaListener implements MouseListener, MouseMotionListener {

	private JPanel theCard;
	private boolean dragging;
	private int startX = -1;
	private int startY = -1;

	public DaListener(CardPanel card) {
		theCard = card;
	}

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {

	}

	public void mouseReleased(MouseEvent arg0) {
		dragging = false;
	}

	public void mouseDragged(MouseEvent arg0) {
		if (!dragging) {
			startX = arg0.getX();
			startY = arg0.getY();
		}
		theCard.setLocation(theCard.getX() + arg0.getX() - startX, theCard
				.getY()
				+ arg0.getY() - startY);
		theCard.repaint();
		dragging = true;
	}

	public void mouseMoved(MouseEvent arg0) {

	}

}
