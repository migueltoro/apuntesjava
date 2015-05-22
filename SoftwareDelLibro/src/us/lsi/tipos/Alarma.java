package us.lsi.tipos;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Implementación de un tipo inmutable
 * 
 * @author Miguel Toro
 *
 */
public final class Alarma implements Comparable<Alarma>{

	public static Alarma create(Calendar fechaAlarma) {
		return new Alarma(fechaAlarma);
	}

	public static Alarma create(Alarma a) {
		return new Alarma(a);
	}

	private final Calendar fechaAlarma;	
	
	private Alarma(Calendar fechaAlarma) {
		checkFechaAlarma(fechaAlarma);
		this.fechaAlarma = (Calendar) fechaAlarma.clone();
	}

	private Alarma(Alarma a) {
		this.fechaAlarma = (Calendar) a.fechaAlarma.clone();
	}
	
	private void checkFechaAlarma(Calendar fechaAlarma) {
		Calendar ahora = Calendar.getInstance();
		if (fechaAlarma.before(ahora)) {
			throw new IllegalArgumentException(
					"Alarma.checkFechaAlarma:: La fecha de la alarma no puede ser anterior al momento actual");
		}
	}
	
	public Calendar getFechaAlarma(){
		return (Calendar) fechaAlarma.clone();
	}
	
	
	public String toString(){
		return 	   fechaAlarma.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, new Locale("ES"))+", "+
				   fechaAlarma.get(Calendar.DAY_OF_MONTH)+ " de " + 
		           fechaAlarma.getDisplayName(Calendar.MONTH,Calendar.LONG, new Locale("ES")) + " de " +
				   fechaAlarma.get(Calendar.YEAR)+ ", "+
		           fechaAlarma.get(Calendar.HOUR_OF_DAY)+":"+
				   fechaAlarma.get(Calendar.MINUTE)+ ":"+
				   fechaAlarma.get(Calendar.SECOND);
	}
	
	public boolean equals(Object o) {
		boolean res = false;

		if (o instanceof Alarma) {
			Alarma a = (Alarma) o;
			res = getFechaAlarma().equals(a.getFechaAlarma());
		}
		return res;
	}
	
	public int hashCode(){
		return getFechaAlarma().hashCode();
	}
	
	public int compareTo(Alarma a){
		return getFechaAlarma().compareTo(a.getFechaAlarma());
	}
	
	public static void main(String[] args) {
		Alarma a = Alarma.create(new GregorianCalendar(2014,2,1,2,14,15));
		System.out.println(a);
	}
}
