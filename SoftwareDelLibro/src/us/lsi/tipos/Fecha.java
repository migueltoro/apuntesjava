package us.lsi.tipos;

import java.text.*;
import java.util.*;


/**
 * Implementación del tipo inmutable Fecha reutilizando por composición 
 * el tipo GregorianCalendar
 * 
 * @author Miguel Toro
 *
 */
public final class Fecha implements Comparable<Fecha> {
	
	
	public static Fecha create(Integer d, Integer m, Integer a) {		
		return new Fecha(d, m, a);
	}

	public static Fecha create() {
		return new Fecha();
	}
	
	public static Fecha create(String s) {
		return new Fecha(s);
	}
	
	public static Fecha create(Fecha f) {
		return new Fecha(f.getDiaMes(), f.getMes(), f.getAño());
	}
	
	private final GregorianCalendar calendar;
		
	
	public boolean esValida(Integer d, Integer m, Integer a){
		boolean r = true;
		try{
			Calendar c = new GregorianCalendar(a,m-1,d);
			c.setLenient(false);
			c.getTime();
		}
		catch(IllegalArgumentException e){
			r = false;
		}
		return r;
	}
	
	private Fecha(Integer d, Integer m, Integer a) {
		super();
		if(!esValida(d, m, a)) throw new IllegalArgumentException("Fecha Inválida");
		this.calendar = new GregorianCalendar(a,m-1,d);	
	}

	private Fecha() {
		super();
		this.calendar = new GregorianCalendar();
	}
	
	private Fecha(GregorianCalendar g) {
		super();
		this.calendar = g;
	}
	
	 private Fecha(String s) { 	
		 DateFormat formatter =  new SimpleDateFormat("dd/MM/yy");	
		 formatter.setLenient(false);
		 this.calendar = new GregorianCalendar();
		 try {
			 Date date = (Date)formatter.parse(s); 						
			 this.calendar.setTime(date);	
		} catch (Exception e) {
			throw new IllegalArgumentException("Formato de fecha inválido o fecha no válida");
		}
	}
	
    public Integer getDiaSemana(){
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
    
	public Integer getDiaMes(){
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	public Integer getDiaAño(){
		return calendar.get(Calendar.DAY_OF_YEAR);
	}
	
	public Integer getMes(){
		return calendar.get(Calendar.MONTH);
	}
	
	public Integer getAño(){
		return calendar.get(Calendar.YEAR);
	}	
	
	public String getMesCadena(){
		return  calendar.getDisplayName(Calendar.MONTH,Calendar.LONG, new Locale("ES"));
	}
	
	public String getDiaSemanaCadena(){
			return calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, new Locale("ES"));
	}
	
	public Fecha suma(Integer a){
		GregorianCalendar ncalendar = (GregorianCalendar)calendar.clone();
		ncalendar.add(Calendar.DAY_OF_MONTH, a);
		return new Fecha(ncalendar);
	}
	
	public Fecha resta(Integer a){
		return suma(-a);
	}
	
	private static long millisOfDay = 1000*24*60*60;
	
	public Integer resta(Fecha f){
		long r;
		long dif = this.calendar.getTimeInMillis()-f.calendar.getTimeInMillis();
		r =  dif/millisOfDay;
		return (int) r;
	}

	public boolean esBisiesto(){
		return this.calendar.isLeapYear(getAño());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((calendar == null) ? 0 : calendar.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fecha other = (Fecha) obj;
		if (calendar == null) {
			if (other.calendar != null)
				return false;
		} else if (!calendar.equals(other.calendar))
			return false;
		return true;
	}

	
	@Override
	public String toString() {
		return getDiaSemanaCadena()+" a "+getDiaMes()+" de "+getMesCadena()+" de "+getAño();
	}
	
	@Override
	public int compareTo(Fecha f) {
		return this.calendar.compareTo(f.calendar);
	}

}
