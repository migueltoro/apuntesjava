package us.lsi.pd.tareasprocesadores;

import java.util.List;
import java.util.stream.Collectors;

import us.lsi.stream.Stream2;

public class Tarea {

	public static List<Tarea> tareas;
	
	public static void leeTareas(String fichero){
		tareas = Stream2.fromFile(fichero)
				.map(s ->Tarea.create(s))
				.collect(Collectors.toList());
	}
	
	public static Tarea create(String s) {
		return new Tarea(s);
	}

	public static Tarea create(Integer id, Double duracion) {
		return new Tarea(id, duracion);
	}

	private Double duracion;
	private Integer id;
	private Tarea(Integer id, Double duracion) {
		super();
		this.duracion = duracion;
		this.id = id;
	}
	private Tarea(String s) {
		super();
		String[] elementos = s.split(",");		
		this.id = new Integer(elementos[0].trim());
		this.duracion = new Double(elementos[1].trim());;
	}
	public Double getDuracion() {
		return duracion;
	}
	
	public static Double getDuracion(int i) {
		return Tarea.tareas.get(i).getDuracion();
	}
	
	public Integer getId() {
		return id;
	}
	
	public static Tarea getTarea(int i) {
		return Tarea.tareas.get(i);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((duracion == null) ? 0 : duracion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Tarea))
			return false;
		Tarea other = (Tarea) obj;
		if (duracion == null) {
			if (other.duracion != null)
				return false;
		} else if (!duracion.equals(other.duracion))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "("+id + "," + duracion + ")";
	}
	
	

}
