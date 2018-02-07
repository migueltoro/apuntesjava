package us.lsi.ag.reinas;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import us.lsi.ag.IndexChromosome;
import us.lsi.ag.IndexProblemAG;
import us.lsi.bt.reinas.Reina;

public class ProblemaReinasAG implements IndexProblemAG<List<Reina>> {

public static int numeroDeReinas = 8;
	
	public static ProblemaReinasAG create() {
		return new ProblemaReinasAG();
	}

	private ProblemaReinasAG() {
	}

	@Override
	public List<Reina> getSolucion(IndexChromosome chromosome) {
		List<Integer> ls = chromosome.decode();
		List<Reina> r = Lists.newArrayList();
		for (int i = 0; i < ls.size(); i++) {
			r.add(Reina.create(i, ls.get(i)));
		}
		return r;
	}

	@Override
	public Double fitnessFunction(IndexChromosome chromosome) {
		List<Integer> ls = chromosome.decode();
		Set<Integer> dp = Sets.newHashSet();
		Set<Integer> ds = Sets.newHashSet();
		for (int i = 0; i < ls.size(); i++) {
			dp.add(ls.get(i)-i);
			ds.add(ls.get(i)+i);
		}
		Double r = 2.*ProblemaReinasAG.numeroDeReinas-dp.size()-ds.size();
		return -2000*r*r;
	}

	@Override
	public Integer getObjectsNumber() {
		return numeroDeReinas;
	}	
	

}
