package org.jbridge.presentation.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;
import javax.swing.JSplitPane;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;

public class BiddingView implements ActionListener{

	private MainWindow owner;
	private BiddingDisplay biddingDisplay;
	private JSplitPane pane;
	private BiddingController controller;
	private int bidSize = 1;

	public BiddingView(MainWindow o) {
		owner = o;
		biddingDisplay = new BiddingDisplay(this);
		pane = createBiddingPane();
	}

	public BiddingDisplay getBiddingDisplay() {
		return biddingDisplay;
	}

	public void setCards(Hand hand) {
		biddingDisplay.setCards(hand);
	}

	public void setAuction(Auctioneer auction) {
		biddingDisplay.setAuction(auction);
		
	}

	public void auctionStateChanged() {
		biddingDisplay.auctionStateChanged();
		
	}

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


	public void hide() {
		pane.setVisible(false);
		
	}

	public void show() {
		owner.setContent(pane);
		
	}

	public void setController(BiddingController c) {
		controller = c;
		
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JRadioButton) {
			bidSize  = Integer.valueOf(e.getActionCommand()).intValue();
		} else if ("Play game...".equals(e.getActionCommand())) {
			controller.playGame();
		} else {
			controller.placeBid(bidSize, e.getActionCommand());
		}

	}

}
