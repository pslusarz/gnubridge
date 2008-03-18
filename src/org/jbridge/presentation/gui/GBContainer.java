package org.jbridge.presentation.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public abstract class GBContainer {
	protected JPanel panel;
	private final MainWindow owner;
	protected String message = "";
	private ButtonGroup radioButtons;
	private List<JPanel> subpanels;

	public GBContainer(MainWindow owner) {

		this.owner = owner;
		panel = createDisplayPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.setMinimumSize(new Dimension(100,200));
		panel.setPreferredSize(new Dimension(100,200));
		panel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		subpanels = new ArrayList<JPanel>();
	}

	protected abstract JPanel createDisplayPanel();

	public void placeOn(JFrame parent) {
	  parent.getContentPane().add(panel);
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

	public void display(String message) {
		this.message = message;
		panel.repaint();
		
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
		JPanel subpanel = new JPanel() {

			public void paintComponent(Graphics g) {
		         super.paintComponent(g);
		         
		      }
		};
		subpanel.setAlignmentY(JPanel.TOP_ALIGNMENT);
		subpanels.add(subpanel);
		panel.add(subpanel);
		return subpanel;
	}

}
