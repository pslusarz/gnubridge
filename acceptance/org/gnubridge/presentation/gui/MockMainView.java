package org.gnubridge.presentation.gui;

import java.awt.Container;

public class MockMainView implements MainView {

	public MockMainView(String string) {
	}

	@Override
	public BiddingView getBiddingView() {
		return new MockBiddingView();
	}

	@Override
	public PlayView getPlayView() {
		return new MockPlayView();
	}

	@Override
	public void setContent(Container pane) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

}
