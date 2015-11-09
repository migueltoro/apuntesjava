package us.lsi.ag;

import java.util.List;

import us.lsi.ag.agchromosomes.IRealChromosome;
import us.lsi.common.Par;

/**
 * @author Miguel Toro
 *
 * @param <S> Tipo de la solución
 * 
 * <p> Tipo adecuado para encontrar el máximo de una función de varias varaibles reales en un dominio dado </p>
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
	 * @param i El indice de una variable
	 * @return El límite inferior de la variable i
	 */
	default Double geLimiteInf(int i){
		return this.getLimites().get(i).p1;
	}
		
	/**
	 * @param i El indice de una variable
	 * @return El límite superior de la variable i
	 */
	default Double geLimiteSup(int i){
		return this.getLimites().get(i).p2;
	}
	
	/**
	 * @param chromosome Un cromosoma
	 * @return La función de fitness del cromosoma
	 */
	Double fitnessFunction(IRealChromosome chromosome);
	
	/**
	 * @param chromosome Es un cromosoma
	 * @return La solución definida por el cromosoma
	 */
	S getSolucion(IRealChromosome chromosome);
	
}
