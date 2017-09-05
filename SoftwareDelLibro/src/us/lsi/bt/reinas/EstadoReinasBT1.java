package us.lsi.bt.reinas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.IntStream;







import us.lsi.bt.EstadoBT;
import us.lsi.common.Lists2;

public class EstadoReinasBT1 implements EstadoBT<SolucionReinas, Integer> {

	
	public static EstadoReinasBT1 create(Integer x, List<Integer> yOcupadas) {
		return new EstadoReinasBT1(x, yOcupadas);
	}

	public static EstadoReinasBT1 create() {
		return new EstadoReinasBT1();
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
	
	private EstadoReinasBT1() {
		super();
		this.x= 0;
		this.yOcupadas = new ArrayList<>();
		this.diagonalesPrincipalesOcupadas = new HashSet<>();
		this.diagonalesSecundariasOcupadas = new HashSet<>();
	}
	
	private EstadoReinasBT1(Integer x, List<Integer> yOcupadas) {
		super();
		this.x= x;
		this.yOcupadas = yOcupadas;
		calculaPropiedadesDerivadas();
	}	
	
	@Override
	public EstadoBT<SolucionReinas, Integer> getEstadoInicial() {	
		return EstadoReinasBT1.create();
	}

	
	@Override
	public Tipo getTipo() {
		return Tipo.AlgunasSoluciones;
	}
	
	@Override
	public int size() {
		return Reina.numeroDeReinas-this.x;
	}
	
	@Override
	public EstadoReinasBT1 avanza(Integer a) {
		this.yOcupadas.add(a);
		this.diagonalesPrincipalesOcupadas.add(a-this.x);
		this.diagonalesSecundariasOcupadas.add(a+this.x);
		this.x = this.x+1;
		return this;
	}

	@Override
	public EstadoReinasBT1 retrocede(Integer a) {
		Lists2.removeLast(this.yOcupadas);
		this.x = this.x-1;
		this.diagonalesPrincipalesOcupadas.remove(a-this.x);
		this.diagonalesSecundariasOcupadas.remove(a+this.x);
		return this;
	}

	@Override
	public List<Integer> getAlternativas() {		
		Stream<Integer> s = IntStream
				.range(0, Reina.numeroDeReinas)
				.filter(y -> !this.yOcupadas.contains(y)
						&& !this.diagonalesPrincipalesOcupadas.contains(y-this.x)
						&& !this.diagonalesSecundariasOcupadas.contains(y+this.x)
						)
				.boxed();
		return s.collect(Collectors.toList());
	}
	
	@Override
	public boolean esCasoBase() {
		return this.x==Reina.numeroDeReinas;
	}

	@Override
	public SolucionReinas getSolucion() {
		List<Reina> ls = new ArrayList<>();
		for(int x=0;x<Reina.numeroDeReinas;x++){
			ls.add(Reina.create(x, this.yOcupadas.get(x)));
		}
		return SolucionReinas.create(ls);
	}


	@Override
	public String toString() {
		return "EstadoReinasBT [\n x=" + x + "\n yOcupadas=" + yOcupadas
				+ "\n diagonalesPrincipalesOcupadas="
				+ diagonalesPrincipalesOcupadas
				+ "\n diagonalesSecundariasOcupadas="
				+ diagonalesSecundariasOcupadas
				+ "\n Alternativas= "
				+this.getAlternativas() + "]";
	}

	
}
