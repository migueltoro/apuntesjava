package us.lsi.ag.agchromosomes;

import java.util.List;

import us.lsi.ag.ProblemaAGBinario;

public interface IBinaryChromosome extends IChromosome<List<Integer>> {
	ProblemaAGBinario<?> getProblema();
}
