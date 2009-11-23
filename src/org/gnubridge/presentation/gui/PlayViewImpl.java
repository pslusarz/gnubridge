package org.gnubridge.presentation.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.gnubridge.core.Direction;
import org.gnubridge.core.Game;
import org.gnubridge.core.North;
import org.gnubridge.core.Player;
import org.gnubridge.core.bidding.Bid;

public class PlayViewImpl implements PlayView, CardPanelHost, ActionListener {

	private Game game;
	final int DHEIGHT = 700;
	private final int WIDTH = 800;

	private final Table table;
	private CardPlayedListener controller;
	private OneColumnPerColor dummy;

	private boolean cardPlayed = false;
	private AllCardsInOneRow humanHandDisplay;
	private Bid contract;
	protected JPanel panel;
	protected final MainView owner;
	protected String message = "";
	private JButton previousTrickButton;

	public PlayViewImpl(MainView owner) {
		this.owner = owner;
		panel = createDisplayPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(WIDTH, DHEIGHT));
		panel.setSize(new Dimension(WIDTH, DHEIGHT));
		table = new Table(DHEIGHT);
		createPreviousTrickButton();
		owner.setContent(panel);

	}

	private void createPreviousTrickButton() {
		URL imageURL = getClass().getResource("/b1fh.png");
		ImageIcon image = new ImageIcon(imageURL);
		final int IMAGE_WIDTH2 = 65;
		final int IMAGE_HEIGHT2 = 52;

		previousTrickButton = new JButton("Previous trick buttton", image);
		previousTrickButton.setToolTipText("Display previous trick");
		previousTrickButton.setLocation(table.getTopLeftX() + 4, table.getTopLeftY() + 4);
		previousTrickButton.setVerticalTextPosition(AbstractButton.CENTER);
		previousTrickButton.setHorizontalTextPosition(AbstractButton.LEADING);
		previousTrickButton.addActionListener(this);
		previousTrickButton.setActionCommand("displayPreviousTrick");
		previousTrickButton.setSize(IMAGE_WIDTH2, IMAGE_HEIGHT2);
		previousTrickButton.setEnabled(true);
		panel.add(previousTrickButton);

	}

	public void setListener(CardPlayedListener c) {
		controller = c;
	}

	public void setGame(Game g, Direction human) {
		game = g;
		table.setHumanDirection(human);

		humanHandDisplay = new AllCardsInOneRow(human, human, game, this);
		humanHandDisplay.display();
		dummy = new OneColumnPerColor(human, North.i(), game, this);
		displayDummyIfWestPlayed();
	}

	private void displayDummyIfWestPlayed() {
		if (game.getTricksPlayed() > 0 || game.getCurrentTrick().getHighestCard() != null) {

			dummy.display();
		}
	}

	protected void dockingCard(boolean isDocking) {
		if (cardPlayed != isDocking) {
			cardPlayed = isDocking;
			table.drawPromptArrow(panel.getGraphics(), game.getNextToPlay().getDirection2(), cardPlayed);
		}
	}

	public JPanel createDisplayPanel() {
		return new JPanel() {
			private static final long serialVersionUID = -8275738275275964573L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				table.draw(g);
				g.drawString("Contract (North/South): " + contract + "            " + message + " ", 20, DHEIGHT - 25);

				table.drawPromptArrow(g, game.getNextToPlay().getDirection2(), cardPlayed);
			}
		};
	}

	//	public void gameStateChanged() {
	//		displayCurrentTrick();
	//
	//	}

	public void displayCurrentTrick() {
		message = " Tricks played: " + game.getTricksPlayed() + "    North/South: "
				+ game.getTricksTaken(Player.NORTH_SOUTH) + " East/West: " + game.getTricksTaken(Player.WEST_EAST);
		table.displayTrick(game.getCurrentTrick(), panel);
		table.setDisplayingPreviousTrick(false);
		displayDummyIfWestPlayed();
		humanHandDisplay.display();
		panel.repaint();

	}

	class DaListener implements MouseListener, MouseMotionListener {

		private CardPanel theCard;
		private int startX = -1;
		private int startY = -1;
		private final Game theGame;
		long previousClick = -1000;

		public DaListener(CardPanel card, Game g) {
			theCard = card;
			theGame = g;
		}

		public void mouseClicked(MouseEvent arg0) {
			long now = System.currentTimeMillis();
			if (now - previousClick < DOUBLE_CLICK_DELAY_MS && theCard.isSelected()) {
				play();
			}
			previousClick = now;
		}

		public void mouseEntered(MouseEvent arg0) {
			if (!theCard.isDragged() && CardPanel.canSelect(theCard) && theGame.isLegalMove(theCard.getCard())) {
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
			theCard.setLocation(theCard.getX() + arg0.getX() - startX, theCard.getY() + arg0.getY() - startY);
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

	public void displayPreviousTrick() {
		table.setDisplayingPreviousTrick(true);
		table.displayTrick(game.getPreviousTrick(), panel);
		dummy.display();
		humanHandDisplay.display();
	}

	@Override
	public int getTableBottom() {
		return (int) (table.getDimensions().getY() + table.getDimensions().getHeight());
	}

	public void setContract(Bid contract) {
		this.contract = contract;

	}

	public void display(String message) {
		this.message = message;
		panel.repaint();

	}

	@Override
	public int getTotalHeight() {
		return DHEIGHT;
	}

	@Override
	public void addCard(CardPanel card) {
		if (card.isPlayable()) {
			DaListener listener = new DaListener(card, game);
			card.addMouseListener(listener);
			card.addMouseMotionListener(listener);
		}
		panel.add(card);
		panel.setComponentZOrder(card, 0);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (game.getPreviousTrick() != null) {
			controller.displayPreviousTrick();
		} else {
			System.out.println("Requested display of previous trick, but no previous trick exists");
		}

	}
}
