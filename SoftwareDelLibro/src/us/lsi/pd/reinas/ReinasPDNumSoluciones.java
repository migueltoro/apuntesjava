package us.lsi.pd.reinas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.common.base.Preconditions;

import us.lsi.pd.AlgoritmoPD.Sp;
import us.lsi.pd.ProblemaPD;


public class ReinasPDNumSoluciones implements ProblemaPD<Integer, Integer> {

	public static int numeroDeReinas = 8;
	
	public static ReinasPDNumSoluciones create(Integer x, List<Integer> yOcupadas) {
		return new ReinasPDNumSoluciones(x, yOcupadas);
	}

	public static ReinasPDNumSoluciones create() {
		return new ReinasPDNumSoluciones(0, new ArrayList<>());
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
	
	private ReinasPDNumSoluciones(Integer x, List<Integer> yOcupadas) {
		super();
		this.x= x;
		this.yOcupadas = new ArrayList<Integer>(yOcupadas);
		calculaPropiedadesDerivadas();
	}

	@Override
	public us.lsi.pd.ProblemaPD.Tipo getTipo() {
		return Tipo.Otro;
	}
	
	@Override
	public int size() {
		return numeroDeReinas-this.x;
	}

	@Override
	public boolean esCasoBase() {
		return this.size() == 1;
	}

	@Override
	public Sp<Integer> getSolucionCasoBase() {
		Integer n = this.getAlternativas().size();
		return Sp.create(null, (double) n);
	}

	@Override
	public Sp<Integer> seleccionaAlternativa(List<Sp<Integer>> ls) {
		Double r = ls.stream().mapToDouble(sp->sp.propiedad).sum();
		return Sp.create(null, r);
	}

	@Override
	public ProblemaPD<Integer, Integer> getSubProblema(Integer a, int i) {
		Preconditions.checkArgument(i==0);
		List<Integer> ls = new ArrayList<>(this.yOcupadas);
		ls.add(a);
		return ReinasPDNumSoluciones.create(this.x+1,ls);
	}

	@Override
	public Sp<Integer> combinaSolucionesParciales(Integer a, List<Sp<Integer>> ls) {
		return Sp.create(a, ls.get(0).propiedad);
	}
	
	@Override
	public List<Integer> getAlternativas() {		
		Stream<Integer> s = IntStream
				.range(0, ReinasPDNumSoluciones.numeroDeReinas)
				.filter(y -> !this.yOcupadas.contains(y)
						&& !this.diagonalesPrincipalesOcupadas.contains(y-this.x)
						&& !this.diagonalesSecundariasOcupadas.contains(y+this.x)
						)
				.boxed();
		return s.collect(Collectors.toList());
	}

	@Override
	public int getNumeroSubProblemas(Integer a) {
		return 1;
	}

	@Override
	public Integer getSolucionReconstruida(Sp<Integer> sp) {
		return sp.propiedad.intValue();
	}

	@Override
	public Integer getSolucionReconstruida(Sp<Integer> sp, List<Integer> ls) {
		return sp.propiedad.intValue();
	}	

	@Override
	public Double getObjetivoEstimado(Integer a) {
		return 0.;
	}

	@Override
	public Double getObjetivo() {		
		return 0.;
	}
	
}
