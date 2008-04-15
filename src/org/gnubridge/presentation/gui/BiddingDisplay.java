package org.gnubridge.presentation.gui;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.gnubridge.core.Card;
import org.gnubridge.core.Direction;
import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Call;

public class BiddingDisplay extends GBContainer {

	private Auctioneer auction;
	private JButton playGameButton;
	private BiddingView parentView;

	public BiddingDisplay(BiddingView pv) {
		super(null);
		parentView = pv;
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(500, 500));
		addPlayGameButton();
	}

	private void addPlayGameButton() {
		playGameButton = new JButton("Play game...");
		playGameButton.setActionCommand("Play game...");
		playGameButton.addActionListener(parentView);
		panel.add(playGameButton);
		playGameButton.setLocation(175, getTopOfCards() - 125);
		playGameButton.setSize(125, 25);
		playGameButton.setVisible(false);

	}

	public void setCards(Hand h) {
		int i = 0;
		for (Card card : h.getCardsHighToLow()) {
			CardPanel cardPanel = new CardPanel(card);
			panel.add(cardPanel);
			cardPanel.setLocation(20 + 30 * i, getTopOfCards());
			panel.setComponentZOrder(cardPanel, 0);
			i++;
		}
	}

	private int getTopOfCards() {
		return (int) Math.round(panel.getPreferredSize().getHeight()
				- CardPanel.IMAGE_HEIGHT - 25);
	}

	public void setAuction(Auctioneer auction) {
		this.auction = auction;

	}

	public void auctionStateChanged() {
		if (auction.biddingFinished() && auction.getHighBid() != null) {
			playGameButton.setVisible(true);
		}
		panel.repaint();
	}

	@Override
	protected JPanel createDisplayPanel() {
		return new JPanel() {
			private static final long serialVersionUID = 2776807090785540403L;
			int colWidth = 80;

			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				int top = 15;
				int rowHeight = 15;
				g.drawString("Player 1", getColumnForDirection(0), top);
				g.drawString("Player 2", getColumnForDirection(1), top);
				g.drawString("Partner 1", getColumnForDirection(2), top);
				g.drawString("Partner 2", getColumnForDirection(3), top);
				top += rowHeight;
				for (Call call : auction.getCalls()) {
					g.drawString(call.getBid().toString(),
							getColumnForDirection(call.getDirection()
									.getValue()), top);
					if (call.getDirection().equals(
							Direction.instance(Direction.SOUTH))) {
						top += rowHeight;
					}
				}
				if (!auction.biddingFinished()) {
					g.drawString("?", getColumnForDirection(auction
							.getNextToBid().getValue()), top);
				}

				g.drawString(message, 5, getTopOfCards() - 20);
				auction.getLastCall();

			}

			private int getColumnForDirection(int direction) {
				int result = colWidth * 5;
				for (int i = direction; i <= Direction.SOUTH; i++) {
					result -= colWidth;
				}
				return result;
			}
		};
	}

}
