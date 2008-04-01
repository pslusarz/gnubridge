package org.gnubridge.presentation.gui;

import java.awt.Container;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import org.gnubridge.core.Card;

public class MainView {

	private JFrame theWindow;
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
