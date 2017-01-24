package us.lsi.ag.agchromosomes;

import java.util.List;

import us.lsi.ag.ProblemaAGBinary;

/**
 * @author Miguel Toro
 * 
 * <p> 
 * Un cromosoma cuya valor decodificado es una lista de ceros y unos del tamaño especificado en el problema.  </p>
 *
 */

public interface IBinaryChromosome extends IChromosome<List<Integer>> {

	public ProblemaAGBinary<?> getProblema();
}
