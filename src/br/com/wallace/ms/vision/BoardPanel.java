package br.com.wallace.ms.vision;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.wallace.ms.model.Board;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel {

	public BoardPanel(Board board) {
		
		setLayout(new GridLayout(board.getLines(), board.getColumns()));
		
		board.MyForEach(f -> add(new FieldButton(f)));
		
		board.registerObservers(e -> {
			
			SwingUtilities.invokeLater(() -> {
				if(e.isWin()) {
					JOptionPane.showMessageDialog(this, "Win :)");
				} else {
					JOptionPane.showMessageDialog(this, "Lose :(");
				
				}
				
				board.restart();
			});
		});
	}
}
