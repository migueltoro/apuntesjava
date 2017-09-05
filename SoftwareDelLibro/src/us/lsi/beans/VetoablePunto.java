package us.lsi.beans;


import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

import us.lsi.geometria.Punto2D;

public class VetoablePunto extends Punto2D {
	
	final private VetoableChangeSupport vetoableProperty = new VetoableChangeSupport(this);
	
	private static int n = 1;
	public int id;
	
	VetoablePunto() {
		super();
		id = n;
		n++;
	}
	
	VetoablePunto(Double x, Double y) {
		super(x, y);
		id = n;
		n++;
	}
	
	
	
	public void addVetoableChangeListener(String arg0,
			VetoableChangeListener arg1) {
		vetoableProperty.addVetoableChangeListener(arg0, arg1);
	}

/*	public void addVetoableChangeListener(VetoableChangeListener arg0) {
		vetoableProperty.addVetoableChangeListener(arg0);
	}

*/	public void removeVetoableChangeListener(String arg0,
			VetoableChangeListener arg1) {
		vetoableProperty.removeVetoableChangeListener(arg0, arg1);
	}

/*	public void removeVetoableChangeListener(VetoableChangeListener arg0) {
		vetoableProperty.removeVetoableChangeListener(arg0);
	}
*/
	@Override
	public void setX(Double x) {
		Double oldX = getX();
		Double oldD = super.getDistanciaAlOrigen();
		super.setX(x);
		try {
			vetoableProperty.fireVetoableChange("x", oldX, x);
			vetoableProperty. fireVetoableChange("distanciaAlOrigen", oldD, super.getDistanciaAlOrigen());
		} catch (PropertyVetoException e) {
			System.out.println("Manteniendo x");
			super.setX(oldX);
		}
	}
	
	@Override
	public void setY(Double y) {
		// TODO Auto-generated method stub
		Double oldY = getY();
		Double oldD = super.getDistanciaAlOrigen();
		super.setY(y);
		try {
			vetoableProperty.fireVetoableChange("y", oldY, y);
			vetoableProperty.fireVetoableChange("distanciaAlOrigen", oldD, super.getDistanciaAlOrigen());
		} catch (PropertyVetoException e) {
			System.out.println("Manteniendo y");
			super.setY(oldY);
		}
	}
	

	@Override
	public String toString() {
		return "VetoablePunto [id = "+id+", getX()=" + getX() + ", getY()=" + getY() +", distancia ="+getDistanciaAlOrigen()+"]";
	}

	
	
}
