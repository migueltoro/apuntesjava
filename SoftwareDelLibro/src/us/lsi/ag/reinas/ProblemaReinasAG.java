package us.lsi.ag.reinas;

import java.util.List;





import us.lsi.ag.ProblemaAGIndex;
import us.lsi.ag.agchromosomes.IndexChromosome;
import us.lsi.bt.reinas.Reina;
import us.lsi.bt.reinas.TableroDeReinas;

public class ProblemaReinasAG implements ProblemaAGIndex<List<Reina>> {

	public static int numeroDeReinas = 8;
	
	public static ProblemaReinasAG create() {
		return new ProblemaReinasAG();
	}

	private ProblemaReinasAG() {
	}

	@Override
	public List<Reina> getSolucion(IndexChromosome chromosome) {
		List<Integer> ls = chromosome.decode();
		TableroDeReinas tr = TableroDeReinas.create(ls);
		return tr.getReinas();
	}

	@Override
	public Double fitnessFunction(IndexChromosome chromosome) {
		List<Integer> ls = chromosome.decode();
		TableroDeReinas tr = TableroDeReinas.create(ls);
		Double r = tr.getObjetivo();
		return -2000*r*r;
	}

	@Override
	public Integer getObjectsNumber() {
		return numeroDeReinas;
	}	
	
}
