package org.gnubridge.search;

import org.gnubridge.core.Deal;

public class ProductionSettings  {
    public static final int DEFAULT_MILISECONDS_TO_DISPLAY_LAST_TRICK = 4000;
	private static int milisecondsToDisplayLastTrick = DEFAULT_MILISECONDS_TO_DISPLAY_LAST_TRICK;

	public static void setMilisecondsToDisplayLastTrick(int value) {
		milisecondsToDisplayLastTrick  = value;
	}
	
	public static int getSearchDepthRecommendation(Deal game) {
		return 13 - game.getTricksPlayed();
	}

	public static long getMilisecondsToDisplayLastTrick() {
		return milisecondsToDisplayLastTrick;
	}

}
