package org.jbridge.presentation.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import org.gnubridge.core.Card;
import org.gnubridge.core.Game;
import org.gnubridge.core.Hand;

public class PlayControls extends GBContainer {

	private Game game;

	public PlayControls(MainWindow owner) {
		super(owner);
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(700, 500));
	}

	public void setGame(Game g) {
		game = g;

		int i = 0;
		Hand dummyHand = new Hand(game.getNorth().getHand());
		for (Card card : dummyHand.getCardsHighToLow()) {
			CardPanel cardPanel = new CardPanel(card);
			cardPanel.addMouseListener(new DaListener(cardPanel));
			cardPanel.addMouseMotionListener(new DaListener(cardPanel));
			panel.add(cardPanel);
			cardPanel.setLocation(20 + 30 * i, 20);
			panel.setComponentZOrder(cardPanel, 0);
			i++;
		}
		panel.repaint();
	}

	@Override
	protected JPanel createDisplayPanel() {
		return new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawRect(5, 5, getWidth() - 10, getHeight() - 10);
				g.drawString("Trump: " + game.getTrump(), 20, 475);
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
