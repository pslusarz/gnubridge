package org.jbridge.presentation.gui;

import java.awt.Dimension;

import org.gnubridge.core.Card;
import org.gnubridge.core.Hand;

public class BiddingDisplay extends GBContainer {

	public BiddingDisplay(MainWindow owner) {
		super(owner);
		panel.setLayout(null);
		//panel.setMinimumSize(new Dimension(400, 300));
		//panel.setMaximumSize(new Dimension(600, 500));
		//panel.setPreferredSize(new Dimension(500, 500));
		panel.setPreferredSize(new Dimension(500, 500));
	}
	
	public void addCards(Hand h) {
	   int i = 0;
       for (Card card : h.getCardsHighToLow()) {
         CardPanel cardPanel = new CardPanel(card);
         panel.add(cardPanel);
         cardPanel.setLocation(20+30*i,  (int)Math.round(panel.getPreferredSize().getHeight()-CardPanel.IMAGE_HEIGHT-25));
         panel.setComponentZOrder(cardPanel, 0);
         i++;
       }
	}

}
