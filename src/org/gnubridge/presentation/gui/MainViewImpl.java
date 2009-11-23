package org.gnubridge.presentation.gui;

import java.awt.Container;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class MainViewImpl implements MainView {

	private final JFrame theWindow;
	PlayView playView;
	private BiddingView biddingView;

	public MainViewImpl(String title) {
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
		theWindow.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().x
				- (theWindow.getWidth() / 2), GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().y
				- (theWindow.getHeight() / 2));
	}

	public void show() {
		theWindow.setVisible(true);

	}

	public BiddingView getBiddingView() {
		if (biddingView == null) {
			biddingView = new BiddingViewImpl(this);
		}
		return biddingView;
	}

	public PlayView getPlayView() {
		if (playView == null) {
			playView = new PlayViewImpl(this);
		}
		return playView;
	}
}
