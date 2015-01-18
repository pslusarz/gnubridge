package org.gnubridge.presentation.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class BiddingControls {
	private ButtonGroup radioButtons;
	private final List<JPanel> subpanels;
	private final BiddingViewImpl listener;
	protected JPanel panel;

	public BiddingControls(BiddingViewImpl view) {
		panel = createDisplayPanel();
		listener = view;
		subpanels = new ArrayList<JPanel>();
		addSelection("1", "2", "3", "4", "5", "6", "7");
		addButtons("Double", "Pass", "NT", "Spades", "Hearts", "Diamonds", "Clubs");
	}

	protected JPanel createDisplayPanel() {
		JPanel result = new JPanel();
		result.setLayout(new BoxLayout(result, BoxLayout.Y_AXIS));
		result.setMinimumSize(new Dimension(100, 200));
		result.setPreferredSize(new Dimension(100, 200));
		result.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		return result;
	}

	public void addSelection(String... selections) {
		radioButtons = new ButtonGroup();
		JPanel subpanel = createSubpanel();
		for (String selection : selections) {
			JRadioButton button = new JRadioButton("       " + selection);
			button.setActionCommand(selection);
			button.addActionListener(listener);
			radioButtons.add(button);
			subpanel.add(button);
		}

	}

	public void addButtons(String... buttons) {
		JPanel subpanel = createSubpanel();
		for (String name : buttons) {
			JButton button = new JButton(name);
			button.setActionCommand(name);
			button.addActionListener(listener);
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

	public void placeOn(Container parent) {
		parent.add(panel);
	}

}
