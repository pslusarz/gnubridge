package org.gnubridge.presentation.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class BiddingControls {
    private ButtonGroup radioButtons;
    private final BiddingViewImpl listener;
    protected JPanel panel;

    public BiddingControls(BiddingViewImpl view) {
        panel = createDisplayPanel();
        listener = view;
        addSelection("1", "2", "3", "4", "5", "6", "7");
        addButtons("Double", "Pass", "NT", "Spades", "Hearts", "Diamonds", "Clubs");
        addCheckButton("Hints");
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
        for (String selection : selections) {
            JRadioButton button = new JRadioButton("       " + selection);
            button.setActionCommand(selection);
            button.addActionListener(listener);
            radioButtons.add(button);
            panel.add(button);
        }

    }

    public void addButtons(String... buttons) {
        for (String name : buttons) {
            JButton button = new JButton(name);
            button.setActionCommand(name);
            button.addActionListener(listener);
            panel.add(button);
        }

    }

    public void addCheckButton(String... names) {
        for (String name : names) {
            JCheckBox button = new JCheckBox(name);
            button.setActionCommand(name);
            button.addActionListener(listener);
            panel.add(button);
        }


    }

    public void placeOn(Container parent) {
        parent.add(panel);
    }

}
