package org.jbridge.presentation.gui;

public class BiddingControls extends GBContainer {

	public BiddingControls(MainWindow owner) {
		super(owner);
		addSelection("1", "2", "3", "4", "5", "6", "7");
		addButtons("No Trump", "Spades", "Hearts", "Diamonds", "Clubs");
	}

}
