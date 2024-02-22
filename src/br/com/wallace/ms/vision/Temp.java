package br.com.wallace.ms.vision;

import br.com.wallace.ms.model.Board;

public class Temp {
	public static void main(String[] args) {
		
		Board board = new Board(3, 3, 9);
		
		board.registerObservers(e -> {
			if(e.isWin()) {
				System.out.println("You Win!!");
			} else {
				System.out.println("Game Over!!");
			}
		});
		
		board.toggleMarking(0, 0);
		board.toggleMarking(0, 1);
		board.toggleMarking(0, 2);
		board.toggleMarking(1, 0);
		board.toggleMarking(1, 1);
		board.toggleMarking(1, 2);
		board.toggleMarking(2, 0);
		board.toggleMarking(2, 1);
		board.toggleMarking(2, 2);
	}
}
