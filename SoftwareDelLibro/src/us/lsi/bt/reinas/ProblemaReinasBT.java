package us.lsi.bt.reinas;

import java.util.List;

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
		return Tipo.Todas;
	}

}
