package org.jbridge.presentation.gui;

import java.awt.Container;

import org.gnubridge.presentation.gui.BiddingView;
import org.gnubridge.presentation.gui.DealView;
import org.gnubridge.presentation.gui.MainView;

public class MockMainView implements MainView {

	private BiddingView biddingView;
	private MockDealView dealView;

	public MockMainView(String string) {
	}

	public BiddingView getBiddingView() {
		if (biddingView == null) {
			biddingView = new MockBiddingView();
		}
		return biddingView;
	}

	public DealView getDealView() {
		if (dealView == null) {
			dealView = new MockDealView();
		}
		return dealView;
	}

	public void setContent(Container pane) {
		// TODO Auto-generated method stub

	}

	public void show() {
		// TODO Auto-generated method stub

	}

	public void hide() {
		// TODO Auto-generated method stub

	}

	public void setBiddingView(BiddingView view) {
		biddingView = view;

	}

}
