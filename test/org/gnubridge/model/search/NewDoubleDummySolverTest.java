package org.gnubridge.model.search;

import static org.gnubridge.core.deck.Trump.*;
import junit.framework.TestCase;

import org.gnubridge.core.Deal;
import org.gnubridge.presentation.GameUtils;

public class NewDoubleDummySolverTest extends TestCase {
	public void testFirstTest() {
		Deal deal = new Deal(NOTRUMP);
		GameUtils.initializeRandom(deal, 2);
		NewDoubleDummySolver solver = new NewDoubleDummySolver(new RootNode(deal));
		//solver.evaluate();
	}
}
