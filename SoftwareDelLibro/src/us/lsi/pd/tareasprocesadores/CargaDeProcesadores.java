package us.lsi.pd.tareasprocesadores;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;

import us.lsi.common.Lists2;

public final class CargaDeProcesadores {

	public static CargaDeProcesadores create(Integer numeroDeProcesadores) {
		return new CargaDeProcesadores(numeroDeProcesadores);
	}

	private List<Double> cargaProcesadores;
	private Map<Integer,List<Tarea>> asignacion;
	private Integer np;

	private CargaDeProcesadores(Integer np) {
		super();
		this.np= np;
		this.cargaProcesadores = Lists2.nCopias(np, 0.);
		this.asignacion = new HashMap<>();
		IntStream.range(0, np).boxed().forEach(p->this.asignacion.put(p, Lists.newArrayList()));
	}

	private CargaDeProcesadores(Integer np, List<Double> cargaProcesadores, Map<Integer,List<Tarea>> asignacion) {
		super();
		this.np = np;
		this.cargaProcesadores = cargaProcesadores;
		this.asignacion = asignacion;
	}

	public List<Double> getCargaProcesadores() {
		return new ArrayList<>(cargaProcesadores);
	}
	
	public CargaDeProcesadores addTareaAProcesador(Integer p, Integer t){
		List<Double>  ls  = Lists.newArrayList(this.cargaProcesadores);
		ls.set(p, ls.get(p)+Tarea.getDuracion(t));
		Map<Integer,List<Tarea>> asignacion = new HashMap<>();
		IntStream.range(0, this.np).boxed()
			.forEach(x->asignacion.put(x, Lists.newArrayList(this.asignacion.get(x))));
		asignacion.get(p).add(Tarea.getTarea(t));
		return new CargaDeProcesadores(this.np,ls, asignacion);
	}
	
	public CargaDeProcesadores removeTareaAProcesador(Integer p, Integer t){
		List<Double>  ls  = Lists.newArrayList(this.cargaProcesadores);
		ls.set(p, ls.get(p)-Tarea.getDuracion(t));
		Map<Integer,List<Tarea>> asignacion = new HashMap<>();
		IntStream.range(0, this.np).boxed()
			.forEach(x->asignacion.put(x, Lists.newArrayList(this.asignacion.get(x))));
		asignacion.get(p).remove(Tarea.getTarea(t));
		return new CargaDeProcesadores(this.np,ls, asignacion);
	}
	
	public Map<Integer, List<Tarea>> getAsignacion() {
		return asignacion;
	}

	public Double getTiempoDelMasCargado() {
		return this.cargaProcesadores.stream().max(Comparator.naturalOrder()).get();
	}

	@Override
	public String toString() {
		return cargaProcesadores+","+this.getTiempoDelMasCargado()+","+asignacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((cargaProcesadores == null) ? 0 : cargaProcesadores
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CargaDeProcesadores))
			return false;
		CargaDeProcesadores other = (CargaDeProcesadores) obj;
		if (cargaProcesadores == null) {
			if (other.cargaProcesadores != null)
				return false;
		} else if (!cargaProcesadores.equals(other.cargaProcesadores))
			return false;
		return true;
	}	
}
