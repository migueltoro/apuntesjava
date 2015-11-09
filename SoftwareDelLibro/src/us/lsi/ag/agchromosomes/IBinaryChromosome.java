package us.lsi.ag.agchromosomes;

import java.util.List;

import us.lsi.ag.ProblemaAGBinario;

/**
 * @author Miguel Toro
 * 
 * <p> Un cromosoma cuyas soluciones asociadas son listas de valores binarios. Es decir la instanciación del tipo &lt;T&gt; es 
 * List&lt;Integer&gt; donde los enteros son cero o uno. </p>
 * 
 * <p> Este es un cromosoma muy general adecuado para resolver problemas modelados con variables binarias. </p>
 * 
 *
 */
public interface IBinaryChromosome extends IChromosome<List<Integer>> {
	/**
	 * @return El problema a resolver
	 */
	ProblemaAGBinario<?> getProblema();
}
