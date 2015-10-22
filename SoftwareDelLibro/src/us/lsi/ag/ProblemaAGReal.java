package us.lsi.ag;

import java.util.List;

import us.lsi.ag.agchromosomes.RealChromosome;
import us.lsi.common.Par;

/**
 * @author Miguel Toro
 *
 * @param <S> Tipo de la solución
 */
public interface ProblemaAGReal<S> extends ProblemaAG {
	
	/**
	 * @return Número de variables
	 */
	Integer getNumeroDeVariables(); 
	
	/**
	 * @return Límites para cada variable
	 */
	List<Par<Double,Double>> getLimites();
	
	/**
	 * @param ls Lista de valores de las variables
	 * @pre ls.size() == getNumeroDeVariables()
	 * @return Fitness del cromosoma
	 */
	Double fitnessFunction(List<Double> ls);
	
	/**
	 * @param chromosome Es un cromosoma
	 * @return La solución definida por el cromosoma
	 */
	S getSolucion(RealChromosome chromosome);
	
}
