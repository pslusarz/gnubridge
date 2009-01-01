package org.gnubridge.search;

import org.gnubridge.core.Game;

public abstract class ConfigurableRuntimeSettings {

	public abstract int getSearchDepthRecommendation(Game game);

	public abstract long getMilisecondsToDisplayLastTrick();

}
