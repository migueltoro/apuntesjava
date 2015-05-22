package us.lsi.ag.reinas;

import java.util.List;



import us.lsi.ag.Cromosoma;
import us.lsi.ag.ProblemaAGBag;
import us.lsi.bt.reinas.Reina;
import us.lsi.bt.reinas.TableroDeReinas;

public class ProblemaReinasAG implements ProblemaAGBag<List<Reina>> {

	public static int numeroDeReinas = 8;
	
	public static ProblemaReinasAG create() {
		return new ProblemaReinasAG();
	}

	private ProblemaReinasAG() {
	}

	@Override
	public List<Reina> getSolucion(Cromosoma<Integer> chromosome) {
		List<Integer> ls = chromosome.decode();
		TableroDeReinas tr = TableroDeReinas.create(ls);
		return tr.getReinas();
	}

	@Override
	public Double fitnessFunction(List<Integer> ls) {
		TableroDeReinas tr = TableroDeReinas.create(ls);
		Double r = tr.getObjetivo();
		return -2000*r*r;
	}

	@Override
	public Integer getNumeroDeObjetos() {
		return numeroDeReinas;
	}	
	
	
}
