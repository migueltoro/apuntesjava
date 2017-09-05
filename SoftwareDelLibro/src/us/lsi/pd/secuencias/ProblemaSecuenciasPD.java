package us.lsi.pd.secuencias;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

import us.lsi.common.Tuple2;
import us.lsi.pd.AlgoritmoPD.Sp;
import us.lsi.pd.ProblemaPD;
import us.lsi.pd.ProblemaPDR;
import us.lsi.pd.secuencias.Secuencia.Accion;

public class ProblemaSecuenciasPD implements ProblemaPDR<SolucionSecuencias, Accion> {

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
	public Sp<Accion> getSolucionParcialCasoBase() {
		return Sp.create(Accion.Nada, 0.);
	}

	@Override
	public Sp<Accion> getSolucionParcial(List<Sp<Accion>> ls) {
		return ls.stream().min(Comparator.naturalOrder()).orElse(null);
	}

	@Override
	public ProblemaPD<SolucionSecuencias, Accion> getSubProblema(Accion a) {
		Secuencia s = secuencia.getNeighbor(a);
		SecuenciaEdge e = SecuenciaEdge.create(secuencia, s, a);
		Double valorAcumulado = this.valorAcumulado + e.getValor();
		return ProblemaSecuenciasPD.create(s, valorAcumulado);
	}
	
	@Override
	public Sp<Accion> getSolucionParcialPorAlternativa(Accion a, Sp<Accion> r) {
		Secuencia s = secuencia.getNeighbor(a);
		SecuenciaEdge e = SecuenciaEdge.create(secuencia, s, a);
		Double valor = r.propiedad + e.getValor();
		Sp<Accion> sp = Sp.create(a, valor);
		return sp;
	}

	@Override
	public List<Accion> getAlternativas() {
		return secuencia.edgesOf().stream().map(x->x.getAccion()).collect(Collectors.toList());
	}

	@Override
	public SolucionSecuencias getSolucionReconstruidaCasoBase(Sp<Accion> sp) {
		return SolucionSecuencias.create();
	}
	
	@Override
	public SolucionSecuencias getSolucionReconstruidaCasoRecursivo(Sp<Accion> sp, SolucionSecuencias s) {
		if(sp.alternativa!=Accion.Avanza)
			s.addFirst(Tuple2.create(sp.alternativa,secuencia.getIndex()));
		return s;
	}
	

	@Override
	public Double getObjetivoEstimado(Accion a) {	
		return this.valorAcumulado+0.;
	}

	
	@Override
	public Double getObjetivo() {
		Double r = null;
		if (esCasoBase()) {
			r = this.valorAcumulado;
		}
		return r;
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
