package org.gnubridge.presentation.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.gnubridge.core.Card;
import org.gnubridge.core.Direction;
import org.gnubridge.core.East;
import org.gnubridge.core.North;
import org.gnubridge.core.South;
import org.gnubridge.core.Trick;
import org.gnubridge.core.West;

public class Table {
	private final Rectangle dimensions;
	private Rectangle promptArrowDimensions;
	List<CardPanel> currentTrickCards;
	private boolean displayingPreviousTrick;
	private Direction human;
	private final int HEIGHT = CardPanel.IMAGE_HEIGHT * 2 + 2;
	private int timeRemaining = -1;
	private boolean promptArrowHighlighted = false;

	public Table(int parentHeight) {
		dimensions = new Rectangle(290, parentHeight - CardPanel.IMAGE_HEIGHT - 35 - 5 - HEIGHT, 222, HEIGHT);
		currentTrickCards = new ArrayList<CardPanel>();

	}

	public void draw(Graphics g) {
		g.drawRect((int) dimensions.getX(), (int) dimensions.getY(), (int) dimensions.getWidth(), (int) dimensions
				.getHeight());
	}

	public Point getExpectedSlot(Direction d) {
		Direction slot = new HumanAlwaysOnBottom(human).mapRelativeTo(d);

		if (slot.equals(South.i())) {
			return new Point((int) (dimensions.getX() + dimensions.getWidth() / 2 - CardPanel.IMAGE_WIDTH / 2),
					(int) (dimensions.getY() + dimensions.getHeight() - CardPanel.IMAGE_HEIGHT));
		} else if (slot.equals(West.i())) {
			return new Point((int) dimensions.getX(),
					(int) (dimensions.getY() + dimensions.getHeight() / 2 - CardPanel.IMAGE_HEIGHT / 2));
		} else if (slot.equals(North.i())) {
			return new Point((int) (dimensions.getX() + dimensions.getWidth() / 2 - CardPanel.IMAGE_WIDTH / 2),
					(int) dimensions.getY());
		} else if (slot.equals(East.i())) {
			return new Point((int) (dimensions.getX() + dimensions.getWidth() - CardPanel.IMAGE_WIDTH),
					(int) (dimensions.getY() + dimensions.getHeight() / 2 - CardPanel.IMAGE_HEIGHT / 2));
		}
		return null;
	}

	public void drawPromptArrow(Graphics g, Direction whereIsNextPlayer, boolean isCardBeingPlayed) {
		Color originalColor;
		if (currentTrickCards.size() < 4) {
			Point topLeft = getExpectedSlot(whereIsNextPlayer);
			if (timeRemaining > -1) {
				promptArrowDimensions = new Rectangle(topLeft, new Dimension(CardPanel.IMAGE_WIDTH,
						CardPanel.IMAGE_HEIGHT));
			} else {
				promptArrowDimensions = null;
				setHighlightPromptArrow(false);
			}
			g.setColor(java.awt.Color.WHITE);
			g.fillRect((int) topLeft.getX() + 1, (int) topLeft.getY() + 1, CardPanel.IMAGE_WIDTH - 2,
					CardPanel.IMAGE_HEIGHT - 2);
			if (isCardBeingPlayed) {
				g.setColor(java.awt.Color.GREEN);
			} else {
				g.setColor(java.awt.Color.YELLOW);
			}
			g.drawRect((int) topLeft.getX() + 1, (int) topLeft.getY() + 1, CardPanel.IMAGE_WIDTH - 2,
					CardPanel.IMAGE_HEIGHT - 2);
			if (isPromptArrowHighlighted()) {
				g.drawRect((int) topLeft.getX() + 2, (int) topLeft.getY() + 2, CardPanel.IMAGE_WIDTH - 4,
						CardPanel.IMAGE_HEIGHT - 4);
				g.drawRect((int) topLeft.getX() + 3, (int) topLeft.getY() + 3, CardPanel.IMAGE_WIDTH - 6,
						CardPanel.IMAGE_HEIGHT - 6);

			}
			g.setColor(java.awt.Color.BLACK);
			Font originalFont = g.getFont();
			originalColor = g.getColor();
			try {
				g.setFont(new Font(originalFont.getName(), Font.BOLD, originalFont.getSize() + 8));
				g.drawString(whereIsNextPlayer.toString(), (int) topLeft.getX() + 8, (int) topLeft.getY() + 30);
				g.setFont(new Font(originalFont.getName(), Font.BOLD, originalFont.getSize() + 30));
				g.drawString("?", (int) topLeft.getX() + CardPanel.IMAGE_WIDTH / 2 - 10, (int) topLeft.getY()
						+ CardPanel.IMAGE_HEIGHT / 2 + 15);
				if (timeRemaining > -1) {
					g.setFont(new Font(originalFont.getName(), Font.BOLD, originalFont.getSize() + 8));
					if (isPromptArrowHighlighted()) {
						g.setColor(Color.RED);
					}
					g.drawString("" + timeRemaining, (int) topLeft.getX() + CardPanel.IMAGE_WIDTH / 2 - 10,
							(int) topLeft.getY() + CardPanel.IMAGE_HEIGHT / 2 + 15 + 30);

				}
			} finally {
				g.setFont(originalFont);
				g.setColor(originalColor);
			}

		}
	}

	public boolean contains(CardPanel card) {
		return dimensions.contains(card.getBounds().getCenterX(), card.getBounds().getCenterY());
	}

	public void setDisplayingPreviousTrick(boolean b) {
		displayingPreviousTrick = b;

	}

	public boolean isDisplayingPreviousTrick() {
		return displayingPreviousTrick;
	}

	public void setHumanDirection(Direction h) {
		human = h;
	}

	public void displayTrick(Trick trick, Container thePanel) {
		dispose(currentTrickCards);
		for (Card card : trick.getCards()) {
			CardPanel cardPanel = new CardPanel(card);
			if (card.equals(trick.getHighestCard()) && displayingPreviousTrick) {
				cardPanel.setHighestInTrick();
			}
			currentTrickCards.add(cardPanel);
			cardPanel.setLocation(getExpectedSlot(trick.whoPlayed(card).getDirection2()));
			thePanel.add(cardPanel);
		}
		thePanel.repaint();

	}

	private void dispose(List<CardPanel> trash) {
		for (CardPanel card : trash) {
			card.dispose();
		}
		trash.clear();

	}

	public Rectangle getDimensions() {
		return dimensions;
	}

	public int getTopLeftX() {
		return (int) dimensions.getX();
	}

	public int getTopLeftY() {
		return (int) dimensions.getY();
	}

	public void setTimeRemaining(int i) {
		timeRemaining = i;

	}

	public boolean isPromptArrowHighlighted() {
		return promptArrowHighlighted;
	}

	public void setHighlightPromptArrow(boolean b) {
		promptArrowHighlighted = b;
	}

	public boolean isWithinPromptArrow(Point point) {
		return promptArrowDimensions != null && promptArrowDimensions.contains(point);
	}

}
