package org.gnubridge.search;

import org.gnubridge.search.pruning.AlphaBeta;

public class SolverConfigurator {

	public static final SolverConfigurator Default = new SolverConfigurator();
	static {
		Default.setUseAlphaBetaPruning(true);
	}
	private boolean useAlphaBetaPruning;

	public boolean isUseAlphaBetaPruning() {
		return useAlphaBetaPruning;
	}

	public void setUseAlphaBetaPruning(boolean useAlphaBetaPruning) {
		this.useAlphaBetaPruning = useAlphaBetaPruning;

	}

	public void configure(DoubleDummySolver doubleDummySolver) {
		if (isUseAlphaBetaPruning()) {
			doubleDummySolver.addPostEvaluationPruningStrategy(new AlphaBeta());
		}

	}

}
