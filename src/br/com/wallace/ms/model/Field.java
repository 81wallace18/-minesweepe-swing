package br.com.wallace.ms.model;

import java.util.ArrayList;
import java.util.List;

public class Field {
	
	private final int line;
	private final int column;
	
	private boolean opened;
	private boolean undermine;
	private boolean marked;
	
	private List<Field> neighbors = new ArrayList<>();
	private List<FieldObserver> observers = new ArrayList<>();
	
	Field (int line, int column) {
		this.line = line;
		this.column = column;
	}
	
	public void registerObservers(FieldObserver observer) {
		observers.add(observer);
	}
	
	private void notifyObservers(FieldEvent event) {
		observers.stream()
			.forEach(o -> o.eventOccurred(this, event));
		
	}
	
	public boolean addNeighbors(Field neighbor) {
		boolean lineDifferent = line != neighbor.line;
		boolean columnDifferent = column != neighbor.column;
		boolean diagonal = lineDifferent && columnDifferent;
		
		int deltaLine = Math.abs(line - neighbor.line);
		int deltaColumn = Math.abs(column - neighbor.column);
		int deltaGeneral = deltaColumn + deltaLine;
		
		if(deltaGeneral == 1 && !diagonal) {
			neighbors.add(neighbor);
			return true;
		} else if (deltaGeneral == 2 && diagonal) {
			neighbors.add(neighbor); 
			return true;
		} else {
			return false;
		}
	}
	
	public void toggleMarking() {
		if(!opened) {
			marked = !marked;
			
			if(marked) {
				notifyObservers(FieldEvent.MARK);
			} else {
				notifyObservers(FieldEvent.UNMARK);
			}
		}
	}
	
	public boolean open() {
		if(!opened && !marked) {
			opened = true;
			
			if(undermine) {
				notifyObservers(FieldEvent.EXPLODE);
				return true;
			}
			
			setOpened(true);
						
			if(neighborhoodSafe()) {
				neighbors.forEach(v -> v.open());
			}
			return true;
		} else {
			return false;
		}
	}
	
	public boolean neighborhoodSafe() {
		return neighbors.stream().noneMatch(n -> n.undermine);
	}
	
	public boolean isMarked() {
		return marked;
	}
	
	public void undermine() {
		undermine = true;
	}
	
	public boolean isUndermine() {
		return undermine;
	}
	
	public boolean isOpened() {
		return opened;
	}
	
	void setOpened(boolean opened) {
		this.opened = opened;
		
		if(opened) {
			notifyObservers(FieldEvent.OPEN);
		}
	}
	
	public boolean isClosed() {
		return !isOpened();
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}
	
	boolean objectiveAchieved() {
		boolean unraveledVariable = !undermine && opened;
		boolean protectedVariable = undermine && marked;
		return protectedVariable || unraveledVariable;
	}
	
	public long minesInTheNeighborhood() {
		return neighbors.stream().filter(n -> n.undermine).count();
	}
	
	public void restart() {
		opened = false;
		undermine = false;
		marked = false;
	}
}
