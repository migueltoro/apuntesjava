package us.lsi.ag.agchromosomes;

import java.util.List;
import us.lsi.ag.ProblemaAGListInteger;


/**
 * @author Miguel Toro
 * 
 * <p> 
 * Un cromosoma cuya valor decodificado es una lista de enteros del tamaño especificado en el problema.  </p>
 *
 */
public interface IListIntegerChromosome extends IChromosome<List<Integer>> {

	public ProblemaAGListInteger<?> getProblema();
}
