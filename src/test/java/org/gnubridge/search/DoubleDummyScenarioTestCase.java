package org.gnubridge.search;

import junit.framework.TestCase;

import org.gnubridge.core.Card;
import org.gnubridge.core.Deal;
import org.gnubridge.core.Direction;
import org.gnubridge.core.Hand;
import org.gnubridge.core.deck.NoTrump;
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
			deal = new Deal(NoTrump.i());
			solver = null;
		}

	}

	private void solve() {
		givenDeal();
		if (solver == null) {
			solver = new DoubleDummySolver(deal);
			solver.search();
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

	protected void shouldWinTricks(int expectedMaxPlayerTricks) {
		solve();
		assertEquals("tricks won by leading pair", expectedMaxPlayerTricks,
				solver.getRoot().getTricksTaken(solver.getRoot().getCurrentPair()));

	}

	protected void shouldPlay(Direction player, Card card) {
		solve();
		assertEquals(card, solver.getBestMoves().get(0));
	}

	protected void followedBy(Direction player, Card card) {
		deal.play(card);
	}

	protected void whenLeadBy(Direction player, Card card) {
		whenLeadBy(player);
		deal.play(card);

	}

}
