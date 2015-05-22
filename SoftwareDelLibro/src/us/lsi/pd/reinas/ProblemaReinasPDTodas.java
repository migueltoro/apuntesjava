package us.lsi.pd.reinas;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import us.lsi.bt.reinas.Reina;
import us.lsi.bt.reinas.TableroDeReinas;
import us.lsi.pd.AlgoritmoPD.Sp;
import us.lsi.pd.ProblemaPD;


public class ProblemaReinasPDTodas implements ProblemaPD<Set<List<Reina>>, Integer> {

	public static int numeroDeReinas = 8;
	
	public static ProblemaReinasPDTodas create(TableroDeReinas tablero) {
		return new ProblemaReinasPDTodas(tablero);
	}

	public static ProblemaReinasPDTodas create() {
		return new ProblemaReinasPDTodas(TableroDeReinas.create());
	}
	
	private TableroDeReinas tablero;
	
	
	private ProblemaReinasPDTodas(TableroDeReinas tablero) {
		super();
		this.tablero = tablero;		
	}

	@Override
	public us.lsi.pd.ProblemaPD.Tipo getTipo() {
		return Tipo.Todas;
	}
	
	@Override
	public int size() {
		return numeroDeReinas-tablero.getNumDeReinas();
	}

	@Override
	public boolean esCasoBase() {
		return this.size() == 1;
	}

	@Override
	public Sp<Integer> getSolucionCasoBase() {
		return Sp.create(null, (double) tablero.getYDisponibles().size());
	}

	@Override
	public Sp<Integer> seleccionaAlternativa(List<Sp<Integer>> ls) {
		Double r = ls.stream().mapToDouble(sp->sp.propiedad).sum();
		return Sp.create(null, r);
	}

	@Override
	public ProblemaPD<Set<List<Reina>>, Integer> getSubProblema(Integer a, int i) {
		Preconditions.checkArgument(i==0);
		return ProblemaReinasPDTodas.create(this.tablero.add(a));
	}

	@Override
	public Sp<Integer> combinaSolucionesParciales(Integer a, List<Sp<Integer>> ls) {
		return Sp.create(a, ls.get(0).propiedad);
	}

	@Override
	public List<Integer> getAlternativas() {		
		return this.tablero.getYDisponibles();
	}

	@Override
	public int getNumeroSubProblemas(Integer a) {
		return 1;
	}

	@Override
	public Set<List<Reina>> getSolucionReconstruida(Sp<Integer> sp) {
		Set<List<Reina>> s = tablero.getYDisponibles()
				.stream()
				.<List<Reina>> map(y -> Lists.newArrayList(Reina.create(tablero.getX(), y)))
				.collect(Collectors.<List<Reina>> toSet());
		return s;
	}

	@Override
	public Set<List<Reina>> getSolucionReconstruida(Sp<Integer> sp, List<Set<List<Reina>>> ls) {
		Set<List<Reina>> r;	
		if (sp.alternativa != null) {
			ls.get(0).forEach(lr->lr.add(Reina.create(this.tablero.getX(), sp.alternativa)));
			r = ls.get(0);
		} else {
			r = Sets.newHashSet();	
			ls.stream().forEach(x->r.addAll(x));
		}			
		return r;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tablero == null) ? 0 : tablero.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProblemaReinasPDTodas))
			return false;
		ProblemaReinasPDTodas other = (ProblemaReinasPDTodas) obj;
		if (tablero == null) {
			if (other.tablero != null)
				return false;
		} else if (!tablero.equals(other.tablero))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return tablero.getX().toString();
	}

	@Override
	public Double getObjetivoEstimado(Integer a) {
		return this.tablero.getObjetivo();
	}

	@Override
	public Double getObjetivo() {
		return this.tablero.getObjetivo();
	}
	
}
