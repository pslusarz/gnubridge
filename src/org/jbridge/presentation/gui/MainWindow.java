package org.jbridge.presentation.gui;

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

public class MainWindow implements ActionListener {

	private JFrame theWindow;
	private GBController controller;
	private Container playPane;
	PlayControls playControls;
	private GBContainer currentDisplay;
	private BiddingView biddingView;

	public MainWindow(String title) {
		theWindow = new JFrame(title);
		theWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theWindow.setSize(600, 500);
		biddingView = new BiddingView(this);
		biddingView.show();		
	}

	public void setContent(Container pane) {
		theWindow.setContentPane(pane);
		theWindow.pack();
		center();		
	}
	
	private void center() {
		theWindow.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().x
				- (theWindow.getWidth() / 2), 
				GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().y
				- (theWindow.getHeight() / 2));
		
	}

	public void actionPerformed(ActionEvent e) {
		if ("Play game...".equals(e.getActionCommand())) {
			controller.playGame();
		}

	}

	public void show() {
		theWindow.setVisible(true);
		
	}

	
	public void display (String msg) {
		currentDisplay.display(msg);
	}

	public void setGame(Game game, Direction human) {
		biddingView.hide();
		playControls = new PlayControls(this);
		playControls.setGame(game, human);
		currentDisplay = playControls;
		playPane = createPlayPane(playControls);		
		setContent(playPane);		
	}

	private Container createPlayPane(PlayControls pc) {
		Container result = new JPanel();
		result.setPreferredSize(new Dimension(800, 750));
		
		pc.placeOn(result);
		return result;
	}


	public void gameStateChanged() {
		playControls.gameStateChanged();
		
	}

	public void playCard(Card card) {
		controller.playCard(card);
	}

	public void setController(GBController c) {
		controller = c;
		
	}

	public BiddingView getBiddingView() {
		return biddingView;
	}
}
