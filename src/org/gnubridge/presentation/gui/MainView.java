package org.gnubridge.presentation.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;

import org.gnubridge.core.Card;
import org.gnubridge.core.Direction;
import org.gnubridge.core.Game;
import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;

public class MainView {

	private JFrame theWindow;
	private GBController controller;
	PlayView playView;
	private BiddingView biddingView;

	public MainView(String title) {
		theWindow = new JFrame(title);
		theWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theWindow.setSize(600, 500);
	}

	public void setContent(Container pane) {
		theWindow.setContentPane(pane);
		theWindow.pack();
		center();
	}

	private void center() {
		theWindow.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getCenterPoint().x
				- (theWindow.getWidth() / 2), GraphicsEnvironment
				.getLocalGraphicsEnvironment().getCenterPoint().y
				- (theWindow.getHeight() / 2));
	}

	public void show() {
		theWindow.setVisible(true);

	}

	public void playCard(Card card) {
		controller.playCard(card);
	}

	public void setController(GBController c) {
		controller = c;

	}

	public BiddingView getBiddingView() {
		if (biddingView == null) {
			biddingView = new BiddingView(this);
			biddingView.show();
		}
		return biddingView;
	}

	public PlayView getPlayView() {
		if (playView == null) {
			if (biddingView != null) {
				biddingView.hide();
			}
			playView = new PlayView(this);
			playView.show();
		}
		return playView;
	}
}
