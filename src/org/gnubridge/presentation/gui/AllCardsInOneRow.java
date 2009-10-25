package org.gnubridge.presentation.gui;

import org.gnubridge.core.Card;
import org.gnubridge.core.Direction;
import org.gnubridge.core.Game;
import org.gnubridge.core.Hand;

public class AllCardsInOneRow extends HandDisplay {

	public AllCardsInOneRow(Direction human, Direction player, Game game, CardPanelHost owner) {
		super(human, player, game, owner);
	}

	@Override
	public void display() {
		dispose(cards);
		int i = 0;
		Hand humanHand = new Hand(game.getPlayer(player).getHand());
		for (Card card : humanHand.getCardsHighToLow()) {
			CardPanel cardPanel = new CardPanel(card);
			cards.add(cardPanel);
			cardPanel.setPlayable(true);
			owner.addCard(cardPanel);
			cardPanel.setLocation(200 + OneColumnPerColor.CARD_OFFSET * i, owner.getTotalHeight()
					- CardPanel.IMAGE_HEIGHT - 35);
			i++;
		}
	}

}
