package us.lsi.ag.agchromosomes;

import org.apache.commons.math3.genetics.Chromosome;

/**
 * @author Miguel Toro
 *
 * @param <T> El tipo de la solución asociada al cromosoma
 * 
 * <p> El tipo de un cromosoma </p>
 */
public interface IChromosome<T> {
	/**
	 * @return Un valor de tipo T asociado al cromosoma
	 */
	T decode();
	
	/**
	 * @return El valor de fitness del cromosoma
	 */
	double fitness();
	
	/**
	 * @return La vista de tipo Chromosome de Apache.
	 */
	Chromosome asChromosome();
}
