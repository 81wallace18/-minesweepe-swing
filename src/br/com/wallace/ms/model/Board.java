package br.com.wallace.ms.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Board implements FieldObserver{
	
	private int lines;
	private int columns;
	private int mines;
	
	private final List<Field> fields = new ArrayList<>();
	private final List<Consumer<ResultEvent>> observers = new ArrayList<>();
	
	public Board(int lines, int columns, int mines) {
		this.lines = lines;
		this.columns = columns;
		this.mines = mines;
		
		generateFields();
		associateNeighbors();
		drawMines();
	}
	
	public void registerObservers(Consumer<ResultEvent> observer) {
		observers.add(observer);
	}
	
	private void notifyObervers(boolean result) {
		observers.stream()
			.forEach(o -> o.accept(new ResultEvent(result)));
	}
	
	public void open(int line, int column) {
		fields.parallelStream()
			.filter(c -> c.getLine() == line && c.getColumn() == column)
			.findFirst()
			.ifPresent(c -> c.open());
	}
	
	public void toggleMarking(int line, int column) {
		fields.parallelStream()
		.filter(c -> c.getLine() == line && c.getColumn() == column)
		.findFirst()
		.ifPresent(c -> c.toggleMarking());;
	}
	
	private void generateFields() {
		for (int l = 0; l < lines; l++) {
			for (int c = 0; c < columns; c++) {
				Field field = new Field(l, c);
				field.registerObservers(this);
				fields.add(field);
			}
		}
		
	}
	
	private void associateNeighbors() {
		for(Field f1 : fields) {
			for(Field f2 : fields) {
				f1.addNeighbors(f2);
			}
		}
	}
	
	private void drawMines() {
		long minesArmed = 0;
		Predicate<Field> undermine = c -> c.isUndermine();
		do {
			int randomVariable = (int) (Math.random() * fields.size());
			fields.get(randomVariable).undermine();;
			minesArmed = fields.stream().filter(undermine).count();
		} while(minesArmed < mines);
	}

	public boolean objectiveAchieved() {
		return fields.stream().allMatch(c -> c.objectiveAchieved());
	}
	
	public void restart() {
		fields.stream().forEach(c -> c.restart());
		drawMines();
	}
	
	@Override
	public void eventOccurred(Field field, FieldEvent event) {
		if(event == FieldEvent.EXPLODE) { 
			showMines();
			notifyObervers(false);
		} else if (objectiveAchieved()) {
			notifyObervers(true);
		}
	}
	
	private void showMines() {
		fields.stream()
		.filter(c -> c.isUndermine())
		.forEach(c -> c.setOpened(true));
	}
}
