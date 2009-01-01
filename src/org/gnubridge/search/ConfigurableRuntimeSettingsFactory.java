package org.gnubridge.search;


public class ConfigurableRuntimeSettingsFactory {

	private static ConfigurableRuntimeSettings strategy;

	public static void set(ConfigurableRuntimeSettings strat) {
		strategy = strat;
	}

	public static ConfigurableRuntimeSettings get() {
		if (strategy == null) {
			strategy = new ProductionSettings();
		}
		return strategy;
	}

}
