package us.lsi.ag.agchromosomes;

import java.util.List;

import us.lsi.ag.ProblemaAGIndex;

public interface IndexChromosome extends IChromosome<List<Integer>> {
	ProblemaAGIndex<?> getProblem();
	Integer getObjectsNumber();
	Integer getMax(int i);
}
