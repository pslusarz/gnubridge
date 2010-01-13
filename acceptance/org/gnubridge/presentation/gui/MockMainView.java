package org.gnubridge.presentation.gui;

import java.awt.Container;

public class MockMainView implements MainView {

	private BiddingView biddingView;
	private MockDealView dealView;

	public MockMainView(String string) {
	}

	@Override
	public BiddingView getBiddingView() {
		if (biddingView == null) {
			biddingView = new MockBiddingView();
		}
		return biddingView;
	}

	@Override
	public DealView getDealView() {
		if (dealView == null) {
			dealView = new MockDealView();
		}
		return dealView;
	}

	@Override
	public void setContent(Container pane) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	public void setBiddingView(BiddingView view) {
		biddingView = view;

	}

}
