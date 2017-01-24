package us.lsi.pd.tareasprocesadores;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.common.collect.Lists;

import us.lsi.common.Lists2;

public final class CargaDeProcesadores {

	public static CargaDeProcesadores create(Integer numeroDeProcesadores) {
		return new CargaDeProcesadores(numeroDeProcesadores);
	}

	private List<Double> cargaProcesadores;

	private CargaDeProcesadores(Integer numeroDeProcesadores) {
		super();
		this.cargaProcesadores = Lists2.nCopias(numeroDeProcesadores, 0.);
	}

	private CargaDeProcesadores(List<Double> cargaProcesadores) {
		super();
		this.cargaProcesadores = new ArrayList<>(cargaProcesadores);
	}

	public List<Double> getCargaProcesadores() {
		return new ArrayList<>(cargaProcesadores);
	}
	
	public CargaDeProcesadores addTareaAProcesador(int t, int p){
		List<Double>  ls  = Lists.newArrayList(this.cargaProcesadores);
		ls.set(p, ls.get(p)+Tarea.getDuracion(t));
		return new CargaDeProcesadores(ls);
	}
	
	public Double getTiempoDelMasCargado() {
		return this.cargaProcesadores.stream().max(Comparator.naturalOrder()).get();
	}

	@Override
	public String toString() {
		return cargaProcesadores+","+this.getTiempoDelMasCargado();
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
