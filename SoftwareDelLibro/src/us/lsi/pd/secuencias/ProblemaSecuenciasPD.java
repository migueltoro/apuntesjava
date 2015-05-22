package us.lsi.pd.secuencias;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import us.lsi.common.Par;
import us.lsi.pd.AlgoritmoPD.Sp;
import us.lsi.pd.ProblemaPD;
import us.lsi.pd.secuencias.Secuencia.Accion;

public class ProblemaSecuenciasPD implements ProblemaPD<List<Par<Accion,Integer>>, Accion> {

	public static ProblemaSecuenciasPD create(
			Secuencia secuencia, Double valorAcumulado) {
		ProblemaSecuenciasPD p = new ProblemaSecuenciasPD(secuencia,valorAcumulado);
		return p;
	}

	public static ProblemaSecuenciasPD create(String origen, String destino) {
		Secuencia s = Secuencia.create(origen);
		Secuencia.transformada = destino;
		Secuencia.tamTransformada = Secuencia.transformada.length();
		return new ProblemaSecuenciasPD(s, 0.);
	}
	
	private Double valorAcumulado;
	private Double valorSolucion = Double.MAX_VALUE;
	private Secuencia secuencia;
	 

	private ProblemaSecuenciasPD(Secuencia secuencia, Double valorAcumulado ) {
		super();
		this.valorAcumulado = valorAcumulado;
		this.secuencia = secuencia;
	}

	@Override
	public us.lsi.pd.ProblemaPD.Tipo getTipo() {
		return Tipo.Min;
	}

	@Override
	public int size() {
		return Secuencia.tamTransformada+secuencia.getTamCadena()-2*secuencia.getIndex();
	}

	@Override
	public boolean esCasoBase() {
		return size() ==0 ;
	}

	@Override
	public Sp<Accion> getSolucionCasoBase() {
		Sp<Accion> sp = Sp.create(Accion.Nada, 0.);
		this.valorSolucion = sp.propiedad;
		return sp;
	}

	@Override
	public Sp<Accion> seleccionaAlternativa(List<Sp<Accion>> ls) {
		Sp<Accion> sp = ls.stream().min(Comparator.naturalOrder()).orElse(null);
		this.valorSolucion = sp.propiedad;
		return sp;
	}

	@Override
	public ProblemaPD<List<Par<Accion,Integer>>, Accion> getSubProblema(Accion a, int np) {
		Preconditions.checkArgument(np==0);
		Secuencia s = secuencia.getNeighbor(a);
		SecuenciaEdge e = SecuenciaEdge.create(secuencia, s, a);
		Double valorAcumulado = this.valorAcumulado + e.getValor();
		return ProblemaSecuenciasPD.create(s, valorAcumulado);
	}
	
	@Override
	public Sp<Accion> combinaSolucionesParciales(Accion a, List<Sp<Accion>> ls) {
		Secuencia s = secuencia.getNeighbor(a);
		SecuenciaEdge e = SecuenciaEdge.create(secuencia, s, a);
		Double valor = ls.get(0).propiedad + e.getValor();
		Sp<Accion> sp = Sp.create(a, valor);
		return sp;
	}

	@Override
	public List<Accion> getAlternativas() {
		return secuencia.edgesOf().stream().map(x->x.getAccion()).collect(Collectors.toList());
	}

	@Override
	public int getNumeroSubProblemas(Accion a) {
		return  1;
	}

	@Override
	public List<Par<Accion,Integer>> getSolucionReconstruida(Sp<Accion> sp) {
		return Lists.newArrayList();
	}

	@Override
	public List<Par<Accion,Integer>> getSolucionReconstruida(Sp<Accion> sp, List<List<Par<Accion,Integer>>> ls) {
		List<Par<Accion,Integer>> lista = Lists.newArrayList();
		if(sp.alternativa!=Accion.Avanza)
			lista.add(Par.create(sp.alternativa,secuencia.getIndex()));
		lista.addAll(ls.get(0));
		return lista;
	}
	

	@Override
	public Double getObjetivoEstimado(Accion a) {	
		return valorAcumulado;
	}

	
	@Override
	public Double getObjetivo() {	
		return this.valorAcumulado+this.valorSolucion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((secuencia == null) ? 0 : secuencia.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProblemaSecuenciasPD))
			return false;
		ProblemaSecuenciasPD other = (ProblemaSecuenciasPD) obj;
		if (secuencia == null) {
			if (other.secuencia != null)
				return false;
		} else if (!secuencia.equals(other.secuencia))
			return false;
		return true;
	}

	

}
