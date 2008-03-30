package org.gnubridge.presentation.gui;

import java.awt.Container;

import javax.swing.JPanel;

public abstract class GBContainer {
	protected JPanel panel;
	protected final MainView owner;
	protected String message = "";
	
	

	public GBContainer(MainView owner) {

		this.owner = owner;
		panel = createDisplayPanel();
		
		
	}

	protected abstract JPanel createDisplayPanel();

	public void placeOn(Container parent) {
	  parent.add(panel);
	}

	

	public void display(String message) {
		this.message = message;
		panel.repaint();
		
	}



	

}
