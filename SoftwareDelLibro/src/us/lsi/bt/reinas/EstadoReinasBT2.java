package us.lsi.bt.reinas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;




import us.lsi.bt.EstadoBT;
import us.lsi.common.Lists2;

public class EstadoReinasBT2 implements EstadoBT<SolucionReinas, Integer> {

	
	public static EstadoReinasBT2 create(Integer x, List<Integer> yOcupadas) {
		return new EstadoReinasBT2(x, yOcupadas);
	}

	public static EstadoReinasBT2 create() {
		return new EstadoReinasBT2();
	}
	
	private final Integer x;
	private final List<Integer> yOcupadas;
	private final Set<Integer> diagonalesPrincipalesOcupadas;
	private final Set<Integer> diagonalesSecundariasOcupadas;
	
	private void calculaPropiedadesDerivadas(){
		for(int x=0; x < this.yOcupadas.size();x++){
			this.diagonalesPrincipalesOcupadas.add(this.yOcupadas.get(x)-x);
			this.diagonalesSecundariasOcupadas.add(this.yOcupadas.get(x)+x);
		}
	}
	
	private EstadoReinasBT2() {
		super();
		this.x= 0;
		this.yOcupadas = new ArrayList<>();
		this.diagonalesPrincipalesOcupadas = new HashSet<>();
		this.diagonalesSecundariasOcupadas = new HashSet<>();
	}
	
	private EstadoReinasBT2(Integer x, List<Integer> yOcupadas) {
		super();
		this.x= x;
		this.yOcupadas = yOcupadas;
		this.diagonalesPrincipalesOcupadas = new HashSet<>();
		this.diagonalesSecundariasOcupadas = new HashSet<>();
		calculaPropiedadesDerivadas();
	}	
	
	@Override
	public EstadoBT<SolucionReinas, Integer> getEstadoInicial() {	
		return EstadoReinasBT2.create();
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
	public EstadoReinasBT2 avanza(Integer a) {
		Integer x = this.x+1;
		List<Integer> yOcupadas = new ArrayList<>(this.yOcupadas);
		yOcupadas.add(a);
		return EstadoReinasBT2.create(x, yOcupadas);
	}

	@Override
	public EstadoReinasBT2 retrocede(Integer a) {
		Integer x = this.x-1;
		List<Integer> yOcupadas = new ArrayList<>(this.yOcupadas);
		Lists2.removeLast(yOcupadas);
		return EstadoReinasBT2.create(x, yOcupadas);
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

