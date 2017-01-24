package us.lsi.ag;

import java.util.List;

import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.MutationPolicy;

import us.lsi.ag.agchromosomes.ListIntegerChromosome;

/**
 * @author Miguel Toro
 *
 * @param <S>
 * 
 * <p> Es un cromosoma general para trabajar con problemas cuyas soluciones pueden ser modeladas por listas de enteros.</p>
 */
public interface ProblemaAGListInteger<S> extends ProblemaAG {

	/**
	 * @return La dimensión de la lista de enteros
	 */
	int getDimension();

	/**
	 * @param listIntegerChromosome Un cromosoma
	 * @return la función de fitness
	 */
	double fitnessFunction(ListIntegerChromosome listIntegerChromosome);

	/**
	 * @return Una lista aleatoria de enteros de tamaño getDimensión() que cumpla los requisitos del problema 
	 */
	List<Integer> getRandomList();
	
	/**
	 * @return El operador de Mutación usado
	 */
	MutationPolicy getMutationPolicy();

	/**
	 * @return El operador de mezcla (crossover)
	 */
	CrossoverPolicy getCrossoverPolicy();
	
	/**
	 * @param c Un cromosoma
	 * @return La solución asociada
	 */
	S getSolucion(ListIntegerChromosome c);
}
