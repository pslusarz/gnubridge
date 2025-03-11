package org.gnubridge.presentation.gui;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) throws InterruptedException, InvocationTargetException {

		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				new MainController();
			}
		});

	}

}
