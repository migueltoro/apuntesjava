package us.lsi.beans;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


import us.lsi.geometria.Punto2D;

public class ListenablePunto extends Punto2D {
	
	final private PropertyChangeSupport boundProperty = new PropertyChangeSupport(this);
	private static int n = 1;
	public int id;
	
	ListenablePunto() {
		super();
		id = n;
		n++;
	}
	
	ListenablePunto(Double x, Double y) {
		super(x, y);
		id = n;
		n++;
	}
	
	public void addPropertyChangeListener(String property,PropertyChangeListener listener) {
		boundProperty.addPropertyChangeListener(property, listener);
	}
	
	public void removePropertyChangeListener(String property,PropertyChangeListener listener) {
		boundProperty.removePropertyChangeListener(property, listener);
	}

	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		boundProperty.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		boundProperty.removePropertyChangeListener(listener);
	}

	@Override
	public void setX(Double x) {
		Double oldX = getX();
		Double oldD = super.getDistanciaAlOrigen();
		super.setX(x);
		boundProperty.firePropertyChange("x", oldX, x);
		boundProperty.firePropertyChange("distanciaAlOrigen", oldD, super.getDistanciaAlOrigen());
	}
	
	@Override
	public void setY(Double y) {
		// TODO Auto-generated method stub
		Double oldY = getY();
		Double oldD = super.getDistanciaAlOrigen();
		super.setY(y);
		boundProperty.firePropertyChange("y", oldY, y);
		boundProperty.firePropertyChange("distanciaAlOrigen", oldD, super.getDistanciaAlOrigen());
	}
	

	@Override
	public String toString() {
		return "ListenablePunto [id = "+id+", getX()=" + getX() + ", getY()=" + getY() + "]";
	}

	
}
