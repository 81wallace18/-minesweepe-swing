package br.com.wallace.ms.vision;

import javax.swing.JFrame;

import br.com.wallace.ms.model.Board;

@SuppressWarnings("serial")
public class MainScreen extends JFrame{

	public MainScreen() {
		Board board = new Board(16, 30, 50);
		BoardPanel boardPanel = new BoardPanel(board);
		add(boardPanel);
		
		setTitle("Mine Sweeper");
		setSize(690, 438);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainScreen();
	}
}
