package org.gnubridge.search;

import org.gnubridge.core.Game;

public class ProductionSettings extends ConfigurableRuntimeSettings {

	@Override
	public int getSearchDepthRecommendation(Game game) {
		int result = 13;
//
//		if (game.getTricksPlayed() < 6) {
//			result = 3;
//		} else if (game.getTricksPlayed() < 8) {
//			result = 4;
//		} else {
//			result = 6;
//		}
		return result;
	}

	@Override
	public long getMilisecondsToDisplayLastTrick() {
		return 3000;
	}

}
