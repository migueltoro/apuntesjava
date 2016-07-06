package us.lsi.bt.reinas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.IntStream;



import us.lsi.bt.EstadoBT;

public class EstadoReinasBT implements EstadoBT<List<Reina>, Integer> {

	
	public static EstadoReinasBT create(Integer x, List<Integer> yOcupadas) {
		return new EstadoReinasBT(x, yOcupadas);
	}

	public static EstadoReinasBT create() {
		return new EstadoReinasBT(0, new ArrayList<>());
	}
	
	private Integer x;
	private List<Integer> yOcupadas;
	private Set<Integer> diagonalesPrincipalesOcupadas;
	private Set<Integer> diagonalesSecundariasOcupadas;
	
	private void calculaPropiedadesDerivadas(){
		this.diagonalesPrincipalesOcupadas = new HashSet<>();
		this.diagonalesSecundariasOcupadas = new HashSet<>();
		for(int x=0; x < this.yOcupadas.size();x++){
			this.diagonalesPrincipalesOcupadas.add(this.yOcupadas.get(x)-x);
			this.diagonalesSecundariasOcupadas.add(this.yOcupadas.get(x)+x);
		}
	}
	
	private EstadoReinasBT(Integer x, List<Integer> yOcupadas) {
		super();
		this.x= x;
		this.yOcupadas = new ArrayList<Integer>(yOcupadas);
		calculaPropiedadesDerivadas();
	}	
	
	@Override
	public int size() {
		return ProblemaReinasBT.numeroDeReinas-this.x;
	}
	
	@Override
	public void avanza(Integer a) {
		this.x = this.x +1;
		this.yOcupadas.add(a);
		calculaPropiedadesDerivadas();
	}

	@Override
	public void retrocede(Integer a) {
		this.x = this.x -1;
		int n = this.yOcupadas.size()-1;
		this.yOcupadas.remove(n);
		calculaPropiedadesDerivadas();
	}

	@Override
	public List<Integer> getAlternativas() {		
		Stream<Integer> s = IntStream
				.range(0, ProblemaReinasBT.numeroDeReinas)
				.filter(y -> !this.yOcupadas.contains(y)
						&& !this.diagonalesPrincipalesOcupadas.contains(y-this.x)
						&& !this.diagonalesSecundariasOcupadas.contains(y+this.x)
						)
				.boxed();
		return s.collect(Collectors.toList());
	}
	
	@Override
	public boolean isFinal() {
		return this.x==ProblemaReinasBT.numeroDeReinas;
	}

	@Override
	public List<Reina> getSolucion() {
		List<Reina> ls = new ArrayList<>();
		for(int x=0;x<ProblemaReinasBT.numeroDeReinas;x++){
			ls.add(Reina.create(x, this.yOcupadas.get(x)));
		}
		return ls;
	}

	@Override
	public Double getObjetivo() {
		return Double.MIN_VALUE;
	}

	@Override
	public Double getObjetivoEstimado(Integer a) {
		return Double.MAX_VALUE;
	}

	@Override
	public String toString() {
		return "EstadoReinasBT [x=" + x + ", yOcupadas=" + yOcupadas
				+ ", diagonalesPrincipalesOcupadas="
				+ diagonalesPrincipalesOcupadas
				+ ", diagonalesSecundariasOcupadas="
				+ diagonalesSecundariasOcupadas + "]";
	}

	
}
