package org.gnubridge.presentation.gui;

public class ViewFactory {

	private static MainView mw;

	public static MainView getMainView() {
		if (mw != null) {
			return mw;
		} else {
			return new MainViewImpl("gnubridge");
		}
	}

	public static void setMockMainView(MainView mw) {
		ViewFactory.mw = mw;

	}

}
