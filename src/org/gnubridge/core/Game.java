package org.gnubridge.core;

import java.util.ArrayList;
import java.util.List;

import org.gnubridge.core.deck.Trump;


public class Game {

	private static final int NO_FORCED_MOVE = -1;

	private Player[] players;

	int nextToPlay;

	Trick currentTrick;

	protected boolean done;

	private Trump trump;

	private int tricksPlayed;

	public Game(Trump trump) {
		players = new Player[4];
		for (int i = Direction.WEST; i <= Direction.SOUTH; i++) {
			players[i] = new Player(i);
		}
		nextToPlay = Direction.WEST;
		this.trump = trump;
		currentTrick = new Trick(this.getTrump());
		tricksPlayed = 0;
		done = false;
	}

	public Player getPlayer(int i) {
		return players[i];
	}
	
	public Player getWest() {
		return players[Direction.WEST];
	}
	public Player getNorth() {
		return players[Direction.NORTH];
	}
	public Player getEast() {
		return players[Direction.EAST];
	}
	public Player getSouth() {
		return players[Direction.SOUTH];
	}

	public void setPlayer(int i, Player p) {
		players[i] = p;

	}
	
	public void doNextCard() {
		doNextCard(NO_FORCED_MOVE);
	}

	public void doNextCard(int forcedMoveIndex) {		
		Card card;
		
		if (forcedMoveIndex == NO_FORCED_MOVE) {
			card = players[nextToPlay].play(currentTrick);
		} else {
			card = players[nextToPlay].play(currentTrick, forcedMoveIndex);
		}
		currentTrick.addCard(card);
		if (currentTrick.isDone()) {
			int winner = getWinnerIndex(currentTrick);
			nextToPlay = winner;
			players[winner].addTrickTaken(currentTrick);

			currentTrick = new Trick(this.getTrump());
			tricksPlayed++;
		} else {
			nextToPlay = (nextToPlay + 1) % players.length;
		}
		if (players[nextToPlay].getUnplayedCardsCount() == 0) {
			this.done = true;
		}

	}

	public int getWinnerIndex(Trick trick) {
		for (int i = 0; i < players.length; i++) {
            if (players[i].hasPlayedCard(trick.getHighestCard())) {
          	  return i;
            }
			}
		throw new RuntimeException("Cannot find winning player for trick: "+trick);
	}

	public boolean isDone() {
		return this.done;
	}

	public Player getNextToPlay() {
		return players[nextToPlay];
	}

	public Game duplicate() {
		Game result = new Game(getTrump());
		for (int i = Direction.WEST; i <= Direction.SOUTH; i++) {
			result.getPlayer(i).init(this.getPlayer(i));
		}
		result.nextToPlay = nextToPlay;
		result.setCurrentTrick(currentTrick.duplicate());
		return result;
	}

	private void setCurrentTrick(Trick trick) {
		currentTrick = trick;
		
	}

	public void playMoves(List<Integer> moves) {
		for (int move:moves) {
			doNextCard(move);	
		}		
	}

	public Trick getCurrentTrick() {
		return currentTrick;
	}

	public void setNextToPlay(int direction) {
		this.nextToPlay = direction;
		
	}

	public int getTricksTaken(int pair) {
		switch (pair) {
		case Player.WEST_EAST:
			return getPlayer(Direction.WEST).countTricksTaken()+ getPlayer(Direction.EAST).countTricksTaken();			
		case Player.NORTH_SOUTH:
			return getPlayer(Direction.NORTH).countTricksTaken()+ getPlayer(Direction.SOUTH).countTricksTaken();
		default:
			throw new RuntimeException("Unknown pair: "+pair);
		}

	}

	public boolean oneTrickLeft() {
		return (getCurrentTrick().getHighestCard() == null && getNextToPlay().getHand().size() == 1); 
	}

	public void printHands() {
		for (Player player : players) {
			System.out.println(player+": "+player.getHand());
		}
	}

	public void setTrump(Trump d) {
		this.trump = d;
		
	}

	public Trump getTrump() {
		return trump;
	}

	public int getTricksPlayed() {
		return tricksPlayed;
	}

	public List<Player> getPlayers() {
		List<Player> result = new ArrayList<Player>();
		for (int i = 0; i< players.length ; i++) {
		result.add(players[i]);
		}
		return result;
	}

	public Player getPlayer(Direction d) {
		return getPlayer(d.getValue());
	}

	public boolean isLegalMove(Card card) {
		 return getNextToPlay().getPossibleMoves(currentTrick).contains(card);
	}

	public void play(Card c) {
		List<Card> possibleMoves = getNextToPlay().getPossibleMoves(currentTrick);
		doNextCard(possibleMoves.indexOf(c));
	}


}
