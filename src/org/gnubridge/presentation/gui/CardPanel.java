package org.gnubridge.presentation.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.gnubridge.core.Card;

public class CardPanel extends JPanel {

	public static final int IMAGE_WIDTH = 70;
	public static final int IMAGE_HEIGHT = 120;
	private static CardPanel selectedCard;
	private Card card;
	private boolean selected;
	private boolean played;
	private int originalX;
	private int originalY;
	Image image;
	private boolean disposed = false;
	private boolean high;

	public CardPanel(Card card) {
		this.card = card;
		setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
		URL imageURL = CardPanel.class.getResource("/"+card.toString().replaceAll(" ", "-") + ".png");
        image = new ImageIcon(imageURL).getImage();
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
		if (played) {
			g.setColor(Color.GREEN);
		} else if (selected) {
			g.setColor(Color.YELLOW);
		} else if (high) {
			g.setColor(Color.BLUE);
		}
		if (selected || played || high) {
			g.drawRect(0, 0, IMAGE_WIDTH - 1, IMAGE_HEIGHT - 1);
		}
	}

	public Card getCard() {
		return card;
	}

	public void setSelected(boolean b) {
		if (b && !selected) {
			selectedCard = this;
			getParent().repaint();
		} else if (!b && selected) {
			selectedCard = null;
			getParent().repaint();
		}
		selected = b;
		if (selected) {
			originalX = getX();
			originalY = getY();
		} else {
			setLocation(originalX, originalY);
		}

	}

	public boolean isSelected() {
		return selected;
	}

	public boolean isPlayed() {
		return played;
	}

	public static boolean canSelect(CardPanel theCard) {
		if (selectedCard == null || selectedCard.equals(theCard)) {
			return true;
		} else {
			return false;
		}
	}

	public void setPlayed(boolean b) {
		played = b;

	}

	public void dispose() {
		if (!disposed) {
			setSelected(false);
			setVisible(false);
			getParent().remove(this);
			disposed = true;
		}
	}

	public void setHighestInTrick() {
		high = true;
		
	}

}
