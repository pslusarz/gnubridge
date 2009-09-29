package org.gnubridge.presentation.gui;

import java.awt.Container;

public interface MainView {

	public abstract void setContent(Container pane);

	public abstract void show();

	public abstract BiddingView getBiddingView();

	public abstract PlayView getPlayView();

}