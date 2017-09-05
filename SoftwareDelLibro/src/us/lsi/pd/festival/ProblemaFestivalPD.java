package us.lsi.pd.festival;

import java.util.*;
import java.util.stream.*;

import us.lsi.pd.AlgoritmoPD.Sp;
import us.lsi.pd.ProblemaPD;
import us.lsi.pd.ProblemaPDR;

public class ProblemaFestivalPD implements ProblemaPDR<Festival, Grupo> {

	private int indexDiaYHora;
	private double presupuestoRestante;
	private int nNuevo = 0;
	private int nCerca = 0;
	private int votosAcum;
	
	
	public static ProblemaFestivalPD create(int indexDiaYHora, double presupuesto, 
			int nNuevo, int nCerca, int votosAcum) {
		return new ProblemaFestivalPD(indexDiaYHora, presupuesto, nNuevo, nCerca,votosAcum);
	}
	
	public static ProblemaFestivalPD create() {
		return new ProblemaFestivalPD();
	}

	private ProblemaFestivalPD(int indexDiaYHora, double presupuesto,int nNuevo, int nCerca, int votosAcum) {
		super();
		this.indexDiaYHora = indexDiaYHora;
		this.presupuestoRestante = presupuesto;
		this.nNuevo = nNuevo;
		this.nCerca = nCerca;
		this.votosAcum = votosAcum;		
	}

	
	private ProblemaFestivalPD() {
		super();
		this.indexDiaYHora = 0;
		this.presupuestoRestante = ProblemaFestival.presupuestoTotal;
		this.nNuevo = 0;
		this.nCerca = 0;
		this.votosAcum = 0;
	}
	
	@Override
	public us.lsi.pd.ProblemaPD.Tipo getTipo() {
		return Tipo.Max;
	}

	@Override
	public int size() {
		return indexDiaYHora;
	}

	@Override
	public boolean esCasoBase() {
		return this.indexDiaYHora == ProblemaFestival.gruposPorFranja.size();
	}

	@Override
	public Sp<Grupo> getSolucionParcialCasoBase() {
		Sp<Grupo> r = null;
		if (this.nCerca <= 2 && this.nNuevo >= 2){
			r =  Sp.create(null, 0.0);
		}
		return r;
	}

	@Override
	public List<Grupo> getAlternativas() {
		return ProblemaFestival.gruposPorFranja.get(indexDiaYHora).stream()
				.filter(x->x.getPrecio() <= this.presupuestoRestante && this.nCerca <= 2)
				.collect(Collectors.toList());
	}

	@Override
	public ProblemaPD<Festival, Grupo> getSubProblema(Grupo a) {
		return ProblemaFestivalPD.create(this.indexDiaYHora + 1, 
				this.presupuestoRestante - a.getPrecio(),
				a.esNuevo()?this.nNuevo + 1: this.nNuevo,
				a.esCerca()?this.nCerca + 1:this.nCerca, this.votosAcum +a.getVotos());
	}

	@Override
	public Sp<Grupo> getSolucionParcialPorAlternativa(Grupo a, Sp<Grupo> sp) {
		return Sp.create(a, sp.propiedad + a.getVotos());
	}

	@Override
	public Sp<Grupo> getSolucionParcial(List<Sp<Grupo>> ls) {
		return ls.stream().max(Comparator.naturalOrder()).orElse(null);
	}

	@Override
	public Festival getSolucionReconstruidaCasoBase(Sp<Grupo> sp) {
		return Festival.create();
	}

	@Override
	public Festival getSolucionReconstruidaCasoRecursivo(Sp<Grupo> sp, Festival s) {
		s.add(sp.alternativa);
		return s;
	}

	@Override
	public Double getObjetivoEstimado(Grupo a) {
		return (double) this.votosAcum+this.cota(a);
	}

	@Override
	public Double getObjetivo() {
		Double r = null;
		if (this.esCasoBase()) {
			r = (double) this.votosAcum;
		}
		return r;
	}
	
	/**
	 * La cota superior considerando para cada grupo restante el que tiene mayor numero de votos en cada grupo
	 * 
	 * 
	 * @param a La alternativa elegida
	 * @return Un cota superior del objetivo para este problema
	 */
	private Integer cota(Grupo a){
		Integer n1 = this.indexDiaYHora+1;
		Integer r =	IntStream.range(n1, ProblemaFestival.gruposPorFranja.size())
				.boxed()
				.mapToInt(x->ProblemaFestival.gruposPorFranja
						.get(x)
						.stream()
						.map(y->y.getVotos())
						.max(Comparator.naturalOrder())
						.orElse(Integer.MAX_VALUE))
				.sum();
		Integer ct = a.getVotos()+r;
		return ct;
	}

	@Override
	public String toString() {
		return "P[" + this.indexDiaYHora + ", " + this.presupuestoRestante + ", " + this.nNuevo + ", " + this.nCerca + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nCerca;
		result = prime * result + nNuevo;
		long temp;
		temp = Double.doubleToLongBits(presupuestoRestante);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + indexDiaYHora;
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
		ProblemaFestivalPD other = (ProblemaFestivalPD) obj;
		if (nCerca != other.nCerca)
			return false;
		if (nNuevo != other.nNuevo)
			return false;
		if (Double.doubleToLongBits(presupuestoRestante) != Double
				.doubleToLongBits(other.presupuestoRestante))
			return false;
		if (indexDiaYHora != other.indexDiaYHora)
			return false;
		return true;
	}

}

