package br.com.wallace.ms.vision;

import java.awt.GridLayout;

import javax.swing.JPanel;

import br.com.wallace.ms.model.Board;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel{
	
	public BoardPanel(Board board) {
		
		setLayout(new GridLayout(board.getLines(), board.getColumns()));
		
		board.MyForEach(f -> add(new FieldButton(f)));
		
		board.registerObservers(e -> {
			// TODO mostrar resultado pro usuário
		});
	}
}
