package org.jbridge.presentation.gui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.InvocationTargetException;

import junit.framework.TestCase;

public class MainWindowTest extends TestCase implements WindowListener {
	private boolean isClosed;

	public void testBuildBiddingGui() {
		MainWindow mw = new MainWindow("gnubridge");
		mw.splitLeftRight(200, 300);
		mw.right().addSelection("1", "2", "3", "4", "5", "6", "7");
		mw.right().addButtons("No Trump", "Spades", "Hearts", "Diamonds", "Clubs");
		//mw.left().addButtons("DO");
//		mw.whenButtonClicked().setAction(new Action() {
//			public void execute() {
//				mainWindow.left().display("Your bid: "+mainWindow.getSelection("Value")+" "+buttonClicked.getName());
//			}
//		});
		mw.showBlocking();
		assertTrue(true);
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		isClosed = true;

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

}
