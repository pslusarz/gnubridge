package org.jbridge.presentation.gui;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;

public class MainWindow implements ActionListener {

	private JFrame theWindow;
	private BiddingDisplay biddingDisplay;
	private BiddingControls biddingControls;
	private int bidSize;
	private GBController controller;

	public MainWindow(String title) {
		theWindow = new JFrame(title);
		theWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel content = new JPanel();

		theWindow.setContentPane(content);
		int width = 700;
		int height = 500;
		theWindow.setBounds(new Rectangle(GraphicsEnvironment
				.getLocalGraphicsEnvironment().getCenterPoint().x
				- (width / 2), GraphicsEnvironment
				.getLocalGraphicsEnvironment().getCenterPoint().y
				- (height / 2), width, height));
		
		splitLeftRight(200, 300);
		controller = new GBController(this);
		theWindow.pack();
	}

	public void splitLeftRight(int i, int j) {
		biddingDisplay = new BiddingDisplay(this);
		biddingControls = new BiddingControls(this);
		JSplitPane content = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		theWindow.setContentPane(content);
		biddingDisplay.placeOn(theWindow);
		biddingControls.placeOn(theWindow);
		//theWindow.pack();
		content.setDividerLocation(700);

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JRadioButton) {
			bidSize = Integer.valueOf(e.getActionCommand()).intValue();
		} else {
			controller.placeBid(bidSize, e.getActionCommand());
		}

	}

	public BiddingDisplay getBiddingDisplay() {		
		return biddingDisplay;
	}

	public void show() {
		theWindow.setVisible(true);
		
	}
}
