package org.jbridge.presentation.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class BiddingControls extends GBContainer {
	private ButtonGroup radioButtons;
	private List<JPanel> subpanels;
	
	public BiddingControls(MainWindow owner) {
		super(owner);
		subpanels = new ArrayList<JPanel>();
		addSelection("1", "2", "3", "4", "5", "6", "7");
		addButtons("Pass", "NT", "Spades", "Hearts", "Diamonds", "Clubs");
	}

	@Override
	protected JPanel createDisplayPanel() {
		JPanel result = new JPanel();
		result.setLayout(new BoxLayout(result,BoxLayout.Y_AXIS));
		result.setMinimumSize(new Dimension(100,200));
		result.setPreferredSize(new Dimension(100,200));
		result.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		return result;
	}
	
	public void addSelection(String... selections) {
		radioButtons = new ButtonGroup();
		JPanel subpanel = createSubpanel();
		for (String selection : selections) {
			JRadioButton button = new JRadioButton("       "+selection);
			button.setActionCommand(selection);
			button.addActionListener(owner);		
			radioButtons.add(button);
			subpanel.add(button);
		}
		
	}
	
	public void addButtons(String... buttons) {
		JPanel subpanel = createSubpanel();
		for (String name : buttons) {
			JButton button = new JButton(name);
			button.setActionCommand(name);
			button.addActionListener(owner);		
			subpanel.add(button);
		}
		
	}
	
	private JPanel createSubpanel() {
		JPanel subpanel = new JPanel();
		subpanel.setAlignmentY(JPanel.TOP_ALIGNMENT);
		subpanels.add(subpanel);
		panel.add(subpanel);
		return subpanel;
	}

}
