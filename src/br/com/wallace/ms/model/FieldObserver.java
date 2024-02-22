package br.com.wallace.ms.model;

@FunctionalInterface
public interface FieldObserver {
	
	public void eventOccurred(Field field, FieldEvent event);
}
