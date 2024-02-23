package br.com.wallace.ms.vision;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import br.com.wallace.ms.model.Field;
import br.com.wallace.ms.model.FieldEvent;
import br.com.wallace.ms.model.FieldObserver;

@SuppressWarnings("serial")
public class FieldButton extends JButton implements FieldObserver{

	private final Color BG_PATTERN = new Color(184, 184, 184);
	private final Color BG_MARKED = new Color(8, 179, 247);
	private final Color BG_EXPLODE = new Color(189, 66, 68);
	private final Color TXT_GREEN = new Color(0, 100, 0);
	
	private Field field;
	
	public FieldButton(Field field) {
		this.field = field;
		setBackground(BG_PATTERN);
		setBorder(BorderFactory.createBevelBorder(0));
		
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
	}

	private void ApplyPatternStyle() {
		// TODO Auto-generated method stub
		
	}

	private void ApplyExposionStyle() {
		// TODO Auto-generated method stub
		
	}

	private void ApplyMarkupStyle() {
		// TODO Auto-generated method stub
		
	}

	private void ApplyOpenStyle() {
		
	}
}
