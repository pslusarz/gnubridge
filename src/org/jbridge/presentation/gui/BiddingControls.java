package org.jbridge.presentation.gui;

import javax.swing.JPanel;

public class BiddingControls extends GBContainer {

	public BiddingControls(MainWindow owner) {
		super(owner);
		addSelection("1", "2", "3", "4", "5", "6", "7");
		addButtons("Pass", "NT", "Spades", "Hearts", "Diamonds", "Clubs");
	}

	@Override
	protected JPanel createDisplayPanel() {
		return new JPanel();
	}

}
