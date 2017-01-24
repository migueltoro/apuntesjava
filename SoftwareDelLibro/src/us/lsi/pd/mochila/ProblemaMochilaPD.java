package us.lsi.pd.mochila;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import us.lsi.pd.AlgoritmoPD.Sp;
import us.lsi.pd.ProblemaPD;

public class ProblemaMochilaPD implements ProblemaPD<Multiset<ObjetoMochila>, Integer> {
	
	private static ProblemaMochilaPD problemaInicial;
	private static Integer capacidadInicial;
	private int index;
	private Double valorSolucion = Double.MIN_VALUE;
	
	public static List<Integer> multiplicidadesMaximas;
	public static List<ObjetoMochila> objetos;
	public static Integer numeroDeObjetos;
	
	
	public static ProblemaMochilaPD create(String fichero, Integer c) {	
		ProblemaMochila.leeObjetosDisponibles(fichero);
		ProblemaMochilaPD.objetos = ProblemaMochila.getObjetosDisponibles();
		ProblemaMochilaPD.numeroDeObjetos = ProblemaMochilaPD.objetos.size();
		ProblemaMochilaPD.multiplicidadesMaximas = ProblemaMochila.getObjetosDisponibles()
				.stream().mapToInt(x->x.getNumMaxDeUnidades())
				.boxed()
				.collect(Collectors.toList());			
		ProblemaMochilaPD.capacidadInicial = c;
		ProblemaMochilaPD.problemaInicial = new ProblemaMochilaPD(0, 0, 0.);
		return ProblemaMochilaPD.problemaInicial;
	}
	
	public static ProblemaMochilaPD create(int index,int pesoAcumulado,double valorAcumulado) {
		ProblemaMochilaPD p = new ProblemaMochilaPD(index, pesoAcumulado,valorAcumulado);
		return p;
	}
	
	private Integer pesoAcumulado;
	private Integer capacidadRestante;
	private Double valorAcumulado;
	
	
	private ProblemaMochilaPD(int index, int pesoAcumulado, double valorAcumulado) {
		this.index = index;
		this.pesoAcumulado = pesoAcumulado;
		this.capacidadRestante = capacidadInicial-this.pesoAcumulado;
		this.valorAcumulado = valorAcumulado;
	}	
	
	private Boolean constraints(Integer a) {
		return this.pesoAcumulado+a*objetos.get(index).getPeso() <= capacidadInicial;
	}	
	
	@Override
	public Tipo getTipo(){
		return Tipo.Max;
	}
	
	@Override
	public int size() {
		return ProblemaMochilaPD.numeroDeObjetos-index+1;
	}
	
	@Override
	public List<Integer> getAlternativas() {
		List<Integer> ls = IntStream.rangeClosed(0, ProblemaMochilaPD.multiplicidadesMaximas.get(this.index))
				.filter(x->this.constraints(x))
				.boxed()
				.collect(Collectors.toList());
		Collections.reverse(ls);
		return ls;
	}
	
	@Override
	public boolean esCasoBase() {
		return this.capacidadRestante == 0 ||  index == ProblemaMochilaPD.numeroDeObjetos-1;
	}
	
	@Override
	public Sp<Integer> getSolucionCasoBase() {
		Integer peso = objetos.get(index).getPeso();
		int num = Math.min(capacidadRestante/peso,objetos.get(index).getNumMaxDeUnidades()) ;
		Double val = (double) num*objetos.get(index).getValor();
		valorSolucion = val;
		return Sp.create(num, val);
	}

	@Override
	public ProblemaPD<Multiset<ObjetoMochila>, Integer> getSubProblema(Integer a, int np) {
		Preconditions.checkArgument(np==0);
		Integer pesoAcumulado = this.pesoAcumulado+a*objetos.get(index).getPeso();
		Double valorAcumulado = this.valorAcumulado+a*objetos.get(index).getValor();
		return ProblemaMochilaPD.create(index+1,pesoAcumulado,valorAcumulado);
	}

	@Override
	public Sp<Integer> combinaSolucionesParciales(Integer a, List<Sp<Integer>> ls) {
		Sp<Integer> r = ls.get(0);
		Double valor = a*objetos.get(index).getValor()+r.propiedad;
		return Sp.create(a, valor);
	}
	
	@Override
	public Sp<Integer> seleccionaAlternativa(List<Sp<Integer>> ls) {
		Sp<Integer> r =ls.stream().filter(x -> x.propiedad != null).max(Comparator.naturalOrder()).orElse(null);
		valorSolucion = r!=null ? r.propiedad : Double.MIN_VALUE;
		return r;
	}
	
	@Override
	public int getNumeroSubProblemas(Integer a) {
		return 1;
	}

	@Override
	public Multiset<ObjetoMochila> getSolucionReconstruida(Sp<Integer> sp) {
		Multiset<ObjetoMochila> m = HashMultiset.create();
		m.add(ProblemaMochilaPD.objetos.get(this.index), sp.alternativa);
		return m;
	}

	@Override
	public Multiset<ObjetoMochila> getSolucionReconstruida(Sp<Integer> sp, List<Multiset<ObjetoMochila>> ls) {
		Multiset<ObjetoMochila> m = ls.get(0);
		m.add(ProblemaMochilaPD.objetos.get(this.index), sp.alternativa);
		return m;
	}

	
	@Override
	public Double getObjetivoEstimado(Integer a) {	
		return this.valorAcumulado+this.getCotaSuperiorValorEstimado(a);
	}

	@Override
	public Double getObjetivo() {
		return this.valorAcumulado+this.valorSolucion;
	}

	
	/**
	 * @pre a está contenida en getAlternativas()
	 * @param a Una alternativa de this
	 * @return Una cota superior del valor de la solución del problema this si se escoge la alternativa a
	 */
	public Integer getCotaSuperiorValorEstimado(Integer a){
		Double r = 0.;
		Double capacidadRestante = (double) (capacidadInicial-pesoAcumulado);
		Double nu =(double) a;
		int ind = this.index;
		while(true) {
			r = r+nu*objetos.get(ind).getValor();
			capacidadRestante = capacidadRestante-nu*objetos.get(ind).getPeso();
			ind++;
			if(ind >= objetos.size() || capacidadRestante <= 0.) break;
			nu = Math.min(multiplicidadesMaximas.get(ind),capacidadRestante/objetos.get(ind).getPeso());			
		} 
		return (int)Math.ceil(r);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((capacidadRestante == null) ? 0 : capacidadRestante
						.hashCode());
		result = prime * result + index;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProblemaMochilaPD))
			return false;
		ProblemaMochilaPD other = (ProblemaMochilaPD) obj;
		if (capacidadRestante == null) {
			if (other.capacidadRestante != null)
				return false;
		} else if (!capacidadRestante.equals(other.capacidadRestante))
			return false;
		if (index != other.index)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "(" + index + ", "
				+ capacidadRestante + ")";
	}


	
	
	
}
