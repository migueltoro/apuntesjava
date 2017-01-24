package us.lsi.ag.agchromosomes;

import java.util.List;

import us.lsi.ag.ProblemaAGIndex;

/**
 * @author Miguel Toro
 * 
 * <p> Un cromosoma adecuado para resolver problemas cuya solución es un Multiset o una lista, posiblemente con elementoss repetidos,
 * formados con elementos de un conjunto dado </p>
 *
 */
public interface IndexChromosome extends IChromosome<List<Integer>> {
	/**
	 * @return El problema a resolver
	 */
	ProblemaAGIndex<?> getProblem();
	
}
