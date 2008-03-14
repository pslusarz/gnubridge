package org.jbridge.presentation.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.SpringLayout;

import org.gnubridge.core.bidding.Bid;

public class MainWindow implements WindowListener, ActionListener {

	private boolean closed;
	private JFrame theWindow;
	private GBContainer left;
	private GBContainer right;
	private int bidSize;
	private GBController controller;

	
	public MainWindow(String title) {
		theWindow = new JFrame(title);
		theWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel content = new JPanel();
	    
	    theWindow.setContentPane(content);
	    int width = 700;
	    int height = 500;
		theWindow.setBounds(new Rectangle(GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().x - (width /2), 
	    		GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().y - (height/2), width, height));
	    theWindow.addWindowListener(this);
	    controller = new GBController(this);
	    
	    

		
	}


	public void showBlocking() {
		closed = false;
		theWindow.setVisible(true);
		while (!closed) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		
	}




	public void splitLeftRight(int i, int j) {
		left = new GBContainer(this);
		right = new GBContainer(this);
		theWindow.setContentPane(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT));
		//theWindow.getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));//new BoxLayout(theWindow.getContentPane(),BoxLayout.PAGE_AXIS));
		left.placeOn(theWindow);
		right.placeOn(theWindow);

		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		
	}
	
	
	@Override
	public void windowClosed(WindowEvent e) {
		closed = true;
		
	}
	
	
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	public GBContainer right() {
		return right;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
	  if (e.getSource() instanceof JRadioButton) {
		  bidSize = Integer.valueOf(e.getActionCommand()).intValue();
	  } else {
	    controller.placeBid(bidSize, e.getActionCommand());
	  }
		
	}


	public GBContainer left() {
		return left;
	}
}
