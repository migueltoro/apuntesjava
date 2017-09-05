package us.lsi.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

public class ConsolaConVeto implements VetoableChangeListener {
	
	private static int nc = 1;
	private int id;
	
	ConsolaConVeto() {
		super();
		// TODO Auto-generated constructor stub
		id = nc;
		nc++;
	}

	@Override
	public void vetoableChange(PropertyChangeEvent  evt) throws PropertyVetoException {
		// TODO Auto-generated method stub
	
		String property = evt.getPropertyName();
		System.out.println(this+",  "+ property+", "+evt.getNewValue()+"  en  "+evt.getSource());
		if(property.equals("x")){
			Double x = (Double) evt.getNewValue();
			if(x < 0.){
				System.out.println("Veto sobre "+property+", "+evt.getNewValue()+"  en  "+evt.getSource());
				throw new PropertyVetoException(property, evt);
			} 
		}else if(property.equals("y")){
			Double y = (Double) evt.getNewValue();
			if(y < 100.) {
				System.out.println("Veto sobre "+property+", "+evt.getNewValue()+"  en  "+evt.getSource());
				throw new PropertyVetoException(property, evt);
			} 
		}else if(property.equals("distanciaAlOrigen")){
			Double d = (Double) evt.getNewValue();
			if(d < 10.) {
				System.out.println("Veto sobre "+property+", "+evt.getNewValue()+"  en  "+evt.getSource());
				throw new PropertyVetoException(property, evt);
			} 
		}
		
	}

	@Override
	public String toString() {
		return "ConsolaConVeto [id=" + id + "]";
	}

}
