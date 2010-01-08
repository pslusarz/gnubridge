package org.gnubridge.presentation.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;
import javax.swing.JSplitPane;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;

public class BiddingViewImpl implements ActionListener, BiddingView {

	private final MainView owner;
	private final BiddingDisplay biddingDisplay;
	private final JSplitPane pane;
	private BiddingController controller;
	private int bidSize = 1;

	public BiddingViewImpl(MainView o) {
		owner = o;
		biddingDisplay = new BiddingDisplay(this);
		pane = createBiddingPane();
	}

	/* (non-Javadoc)
	 * @see org.gnubridge.presentation.gui.BiddingView#setCards(org.gnubridge.core.Hand)
	 */
	public void setCards(Hand hand) {
		biddingDisplay.setCards(hand);
	}

	/* (non-Javadoc)
	 * @see org.gnubridge.presentation.gui.BiddingView#setAuction(org.gnubridge.core.bidding.Auctioneer)
	 */
	public void setAuction(Auctioneer auction) {
		biddingDisplay.setAuction(auction);

	}

	/* (non-Javadoc)
	 * @see org.gnubridge.presentation.gui.BiddingView#auctionStateChanged()
	 */
	public void auctionStateChanged() {
		biddingDisplay.auctionStateChanged();

	}

	/* (non-Javadoc)
	 * @see org.gnubridge.presentation.gui.BiddingView#display(java.lang.String)
	 */
	public void display(String msg) {
		biddingDisplay.display(msg);

	}

	private JSplitPane createBiddingPane() {
		BiddingControls biddingControls = new BiddingControls(this);
		JSplitPane content = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		biddingDisplay.placeOn(content);
		biddingControls.placeOn(content);
		content.setDividerLocation(500);
		return content;
	}

	/* (non-Javadoc)
	 * @see org.gnubridge.presentation.gui.BiddingView#hide()
	 */
	public void hide() {
		pane.setVisible(false);

	}

	/* (non-Javadoc)
	 * @see org.gnubridge.presentation.gui.BiddingView#show()
	 */
	public void show() {
		owner.setContent(pane);

	}

	/* (non-Javadoc)
	 * @see org.gnubridge.presentation.gui.BiddingView#setController(org.gnubridge.presentation.gui.BiddingController)
	 */
	public void setController(BiddingController c) {
		controller = c;

	}

	/* (non-Javadoc)
	 * @see org.gnubridge.presentation.gui.BiddingView#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JRadioButton) {
			bidSize = Integer.valueOf(e.getActionCommand()).intValue();
		} else if ("Play game...".equals(e.getActionCommand())) {
			controller.playGame();
		} else if ("newGame".equals(e.getActionCommand())) {
			controller.newGame();
		} else {
			controller.placeBid(bidSize, e.getActionCommand());
		}

	}
	
	public void setVulnerability (String vulnerabilityMessage) {
		biddingDisplay.setVulnerability(vulnerabilityMessage);
	}
	

	public void displayScore(String message) {
		biddingDisplay.displayScore(message);
	}
}
