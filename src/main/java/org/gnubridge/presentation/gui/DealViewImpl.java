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

import org.gnubridge.core.Deal;
import org.gnubridge.core.Direction;
import org.gnubridge.core.North;
import org.gnubridge.core.Player;
import org.gnubridge.core.bidding.Bid;

public class DealViewImpl implements DealView, CardPanelHost, ActionListener {

	private Deal game;
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
	protected String scoreMessage = "";
	private JButton previousTrickButton;
	private JButton newGameButton;
	private JButton donateButton;

	public DealViewImpl(MainView owner) {
		this.owner = owner;
		panel = createDisplayPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(WIDTH, DHEIGHT));
		panel.setSize(new Dimension(WIDTH, DHEIGHT));
		ArrowPromptMouseListener listener = new ArrowPromptMouseListener();
		panel.addMouseListener(listener);
		panel.addMouseMotionListener(listener);
		table = new Table(DHEIGHT);
		createPreviousTrickButton();
		owner.setContent(panel);
		addNewGameButton();
		addDonateButton();

	}

	private void addNewGameButton() {
		newGameButton = new JButton("New Game...");
		newGameButton.setActionCommand("newGame");
		newGameButton.addActionListener(this);
		panel.add(newGameButton);
		int height = 25;
		int width = 125;
		newGameButton.setLocation(4, 10);
		newGameButton.setSize(width, height);
		newGameButton.setVisible(true);
	}

	private void addDonateButton() {
		int height = 25;
		int width = 125;
		donateButton = new JButton("Donate");
		donateButton.setActionCommand("donate");
		donateButton.addActionListener(this);
		panel.add(donateButton);
		donateButton.setLocation(4, 10 + height + 2);
		donateButton.setSize(width, height);
		donateButton.setVisible(true);

	}

	private void createPreviousTrickButton() {
		URL imageURL = getClass().getResource("/images/cards/jfitz/b1fh.png");
		final int IMAGE_WIDTH2 = 65;
		final int IMAGE_HEIGHT2 = 52;
		ImageIcon image = new ImageIcon(new ImageIcon(imageURL).getImage().getScaledInstance(IMAGE_WIDTH2,
				IMAGE_HEIGHT2, java.awt.Image.SCALE_DEFAULT));

		previousTrickButton = new JButton(null, image);
		previousTrickButton.setToolTipText("Display previous trick");
		previousTrickButton.setLocation(table.getTopLeftX() + 4, table.getTopLeftY() + 4);
		previousTrickButton.setVerticalTextPosition(AbstractButton.CENTER);
		previousTrickButton.setHorizontalTextPosition(AbstractButton.LEADING);
		previousTrickButton.addActionListener(this);
		previousTrickButton.setActionCommand("displayPreviousTrick");
		previousTrickButton.setSize(IMAGE_WIDTH2, IMAGE_HEIGHT2);
		previousTrickButton.setBorderPainted(false);
		previousTrickButton.setEnabled(true);
		panel.add(previousTrickButton);

	}

	public void setListener(CardPlayedListener c) {
		controller = c;
	}

	public void setGame(Deal g, Direction human) {
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
				g.drawString("Contract (North/South): " + contract.longDescription() + "            " + message + " ",
						20, DHEIGHT - 25);
				g.drawString(scoreMessage, 20, DHEIGHT - 13);
				table.drawPromptArrow(g, game.getNextToPlay().getDirection2(), cardPlayed);
			}
		};
	}

	public void displayCurrentTrick() {
		message = " Tricks played: " + game.getTricksPlayed() + "    North/South: "
				+ game.getTricksTaken(Player.NORTH_SOUTH) + " East/West: " + game.getTricksTaken(Player.WEST_EAST);
		table.displayTrick(game.getCurrentTrick(), panel);
		table.setDisplayingPreviousTrick(false);
		displayDummyIfWestPlayed();
		humanHandDisplay.display();
		panel.repaint();

	}

	class CardOnTheTableMouseListener implements MouseListener, MouseMotionListener {

		private CardPanel theCard;
		private int startX = -1;
		private int startY = -1;
		private final Deal theGame;

		public CardOnTheTableMouseListener(CardPanel card, Deal g) {
			theCard = card;
			theGame = g;
		}

		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() > 1 && theCard.isSelected()) {
				play();
			}
		}

		public void mouseEntered(MouseEvent arg0) {
			if (!theCard.isDragged() && CardPanel.canSelect(theCard) && theGame.isLegalMove(theCard.getCard())) {
				theCard.setSelected(true);
			}
		}

		public void mouseExited(MouseEvent arg0) {
			if (theCard.isSelected() && (arg0.getClickCount() == 0)) {
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
			if (!theCard.isSelected() || (arg0.getClickCount() > 1)) {
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

	public int getTotalHeight() {
		return DHEIGHT;
	}

	public void addCard(CardPanel card) {
		if (card.isPlayable()) {
			CardOnTheTableMouseListener listener = new CardOnTheTableMouseListener(card, game);
			card.addMouseListener(listener);
			card.addMouseMotionListener(listener);
		}
		panel.add(card);
		panel.setComponentZOrder(card, 0);

	}

	public void actionPerformed(ActionEvent e) {
		if ("displayPreviousTrick".equals(e.getActionCommand())) {
			if (game.getPreviousTrick() != null) {
				controller.displayPreviousTrick();
			} else {
				System.out.println("Requested display of previous trick, but no previous trick exists");

			}
		} else if ("newGame".equals(e.getActionCommand())) {
			newGameButton.setEnabled(false);
			controller.newGame();
		} else if ("donate".equals(e.getActionCommand())) {
			controller.donate();
		}

	}

	public void displayTimeRemaining(int i) {
		table.setTimeRemaining(i);
		panel.repaint();

	}

	public void hide() {
		//panel.removeAll();
		panel.setVisible(false);
	}

	public void displayStartingScore(ScoringTracker scoringTracker) {
		this.scoreMessage = "Us: " + scoringTracker.getRunningHumanScore() + ", Them: "
				+ scoringTracker.getRunningComputerScore();
		panel.repaint();
	}

	public void displayFinalScore(ScoringTracker scoringTracker) {
		display("GAME FINISHED. Contract was: " + contract + ", declarers took " + game.getDeclarerTricksTaken()
				+ " tricks.");
		this.scoreMessage = "North/South: +" + scoringTracker.getLatestDeclarerScoreChange() + " points, East/West: +"
				+ scoringTracker.getLatestDefenderScoreChange() + " points (Us: "
				+ scoringTracker.getRunningHumanScore() + ", " + "Them: " + scoringTracker.getRunningComputerScore()
				+ ")";
		panel.repaint();

	}

	class ArrowPromptMouseListener implements MouseListener, MouseMotionListener {

		public void mouseClicked(MouseEvent e) {
			if (table.isPromptArrowHighlighted()) {
				controller.forceMove();
			}

		}

		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mouseMoved(MouseEvent e) {
			if (table.isWithinPromptArrow(e.getPoint()) && !table.isPromptArrowHighlighted()) {
				table.setHighlightPromptArrow(true);
				panel.repaint();
			} else if (table.isPromptArrowHighlighted() && !table.isWithinPromptArrow(e.getPoint())) {
				table.setHighlightPromptArrow(false);
				panel.repaint();
			}

		}
	}
}
