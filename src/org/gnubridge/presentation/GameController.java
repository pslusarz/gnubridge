package org.gnubridge.presentation;

import org.gnubridge.core.Game;
import org.gnubridge.core.Player;
import org.gnubridge.core.deck.NoTrump;

public class GameController {
	private Game game;

	//private TextDisplay display;

	Player human;

	public GameController() {
		game = new Game(NoTrump.i());
		GameUtils.initializeRandom(game, 13);
		//display = new TextDisplay();
	}

//	public void run() {
//		display.showHand(game.getPlayer(Player.NORTH));
//
//		Player human = display.selectPlayer();
//		for (int i = Player.WEST; i <= Player.SOUTH; i++) {
//			if (i == human.getDirection()) {
//				game.getPlayer(human.getDirection()).setMoveStrategy(
//						new Human(display, g));
//			} else {
//				game.getPlayer(human.getDirection()).setMoveStrategy(
//						new AlphaBeta(g));
//			}
//		}
//
//		while (!game.isDone()) {
//			playOneTrick();
//		}
//
//	}
//
//	private void playOneTrick() {
//		for (int i = 0; i < 4; i++) {
//			Card move = game.getNextToPlay().getMove();
//			display.makeMove(game.getNextToPlay(), move);
//			game.doNextCard(move);
//			
//		}
//		display.showScore(g);
//
//	}

}
