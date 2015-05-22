package us.lsi.sa.reinas;

import java.util.List;

import com.google.common.collect.Lists;

import us.lsi.bt.reinas.Reina;
import us.lsi.bt.reinas.TableroDeReinas;
import us.lsi.common.ParInteger;
import us.lsi.sa.ProblemaSA;



public class ProblemaReinasSA implements ProblemaSA<EstadoReinasSA,List<Reina>,ParInteger> {
	
	public static int numeroDeReinas = 8;
	
	@Override
	public EstadoReinasSA getEstadoInicial() {
		List<Integer> filasOcupadas = Lists.newArrayList();
		for (int i = 0; i < numeroDeReinas; i++) {
			filasOcupadas.add(i);
		}
		TableroDeReinas t = TableroDeReinas.create(filasOcupadas);
		return EstadoReinasSA.create(t);
	}
	
	public static ProblemaReinasSA create() {
		return new ProblemaReinasSA();
	}

	

	
}
