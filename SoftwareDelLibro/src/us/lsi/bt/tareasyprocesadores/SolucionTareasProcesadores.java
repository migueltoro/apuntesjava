package us.lsi.bt.tareasyprocesadores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.lsi.bt.SolucionBT;
import us.lsi.pd.tareasprocesadores.Tarea;

public class SolucionTareasProcesadores implements SolucionBT {

	public static SolucionTareasProcesadores create(
			Map<Integer, List<Tarea>> asignacion, Double objetivo) {
		return new SolucionTareasProcesadores(asignacion, objetivo);
	}
	
	private Map<Integer,List<Tarea>> asignacion;
	private Double objetivo;

	private SolucionTareasProcesadores(Map<Integer, List<Tarea>> asignacion,
			Double objetivo) {
		super();
		this.asignacion = new HashMap<>(asignacion);
		this.objetivo = objetivo;
	}

	@Override
	public Double getObjetivo() {
		return objetivo;
	}

	public Map<Integer, List<Tarea>> getAsignacion() {
		return asignacion;
	}


	@Override
	public String toString() {
		return this.objetivo + ","+this.asignacion;
	}
	
	
}
