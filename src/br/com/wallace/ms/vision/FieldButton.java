package br.com.wallace.ms.vision;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import br.com.wallace.ms.model.Field;
import br.com.wallace.ms.model.FieldEvent;
import br.com.wallace.ms.model.FieldObserver;

@SuppressWarnings("serial")
public class FieldButton extends JButton implements FieldObserver, MouseListener{

	private final Color BG_PATTERN = new Color(184, 184, 184);
	private final Color BG_MARKED = new Color(8, 179, 247);
	private final Color BG_EXPLODE = new Color(189, 66, 68);
	private final Color TXT_GREEN = new Color(0, 100, 0);
	
	private Field field;
	
	public FieldButton(Field field) {
		this.field = field;
		setBackground(BG_PATTERN);
		setOpaque(true);
		setBorder(BorderFactory.createBevelBorder(0));
		
		addMouseListener(this);
		field.registerObservers(this);
	}
	
	@Override
	public void eventOccurred(Field field, FieldEvent event) {
		switch (event) {
		case OPEN: 
			ApplyOpenStyle();
			break;
		case MARK:
			ApplyMarkupStyle();
			break;
		case EXPLODE:
			ApplyExposionStyle();
			break;
		default:
			ApplyPatternStyle();
		}
		SwingUtilities.invokeLater(() -> {
			repaint();
			validate();
		});
	}

	private void ApplyPatternStyle() {
		setBackground(BG_PATTERN);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");
	}

	private void ApplyExposionStyle() {
		setBackground(BG_EXPLODE);
		setForeground(Color.WHITE);
		setText("X");
	}

	private void ApplyMarkupStyle() {
		setBackground(BG_MARKED);
		setForeground(Color.BLACK);
		setText("M");
		//setIcon();
	}

	private void ApplyOpenStyle() {
		
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		if(field.isUndermine()) {
			setBackground(BG_EXPLODE);
			return;
		}
		setBackground(BG_PATTERN);
		
		switch (field.minesInTheNeighborhood()) {
		case 1: 	
			setForeground(TXT_GREEN);
			break;
		case 2: 	
			setForeground(Color.BLUE);
			break;
		case 3: 	
			setForeground(Color.YELLOW);
			break;
		case 4:
		case 5:
		case 6:
			setForeground(TXT_GREEN);
			break;
		default:
			setForeground(Color.PINK);
		}
		String valor = !field.neighborhoodSafe() ? field.minesInTheNeighborhood() + "" : "";
		setText(valor);
	}
	
	//Interface of the events of mouse
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {
			field.open();
		} else {
			field.toggleMarking();
		}
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
