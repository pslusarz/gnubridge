package org.gnubridge.search;

import static org.gnubridge.core.deck.Trump.*;
import junit.framework.TestCase;

import org.gnubridge.core.Deal;
import org.gnubridge.core.Direction;
import org.gnubridge.core.Hand;
import org.gnubridge.core.deck.Trump;

public abstract class DoubleDummyScenarioTestCase extends TestCase {
	protected Deal deal;
	protected DoubleDummySolver solver;

	public void given(Direction playerDirection, String... suits) {
		givenDeal();
		deal.getPlayer(playerDirection).init(new Hand(suits).getCardsHighToLow());

	}

	private void givenDeal() {
		if (deal == null) {
			deal = new Deal(NOTRUMP);
		}

	}

	public void whenLeadBy(Direction player) {
		givenDeal();
		deal.setNextToPlay(player.getValue());
	}

	public void givenTrump(Trump trump) {
		givenDeal();
		deal.setTrump(trump);
	}

	public void thenTricksWon(int expectedMaxPlayerTricks) {
		solver = new DoubleDummySolver(deal);
		solver.search();
		assertEquals("tricks won by leading pair", expectedMaxPlayerTricks,
				solver.getRoot().getTricksTaken(solver.getRoot().getCurrentPair()));
	}

}
