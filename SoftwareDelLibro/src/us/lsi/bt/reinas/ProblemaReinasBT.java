package us.lsi.bt.reinas;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import us.lsi.bt.ProblemaBT;

public class ProblemaReinasBT implements ProblemaBT<List<Reina>,Integer> {
	
	public static int numeroDeReinas = 8;
	
	@Override
	public EstadoReinasBT getEstadoInicial() {
		return EstadoReinasBT.create();
	}
	
	public static ProblemaReinasBT create() { 
		return new ProblemaReinasBT();
	}
	
	@Override
	public Tipo getTipo() {
		return Tipo.Otro;
	}

	int getNumeroDeConflictos(List<Reina> ls){
		Set<Integer> diagonalesPrincipalesOcupadas = new HashSet<>();
		Set<Integer> diagonalesSecundariasOcupadas = new HashSet<>();
		for(Reina r:ls){
			diagonalesPrincipalesOcupadas.add(r.getY()-r.getX());
			diagonalesSecundariasOcupadas.add(r.getY()+r.getX());
		}
		return 2*ProblemaReinasBT.numeroDeReinas 
				- diagonalesPrincipalesOcupadas.size()
				-diagonalesSecundariasOcupadas.size();
	}
}
