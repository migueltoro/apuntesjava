package us.lsi.ag;

/**
 * @author Miguel Toro
 *
 * @param <E> El tipo de los elementos del cromosoma. El cromosoma es del tipo List&lt;E&gt;
 * @param <S> El tipo de solución del problema
 */
public interface ValuesInRangeProblemAG<E,S> extends ProblemAG {

	/**
	 * @return Número de variables.
	 */
	Integer getVariableNumber();
	
	/**
	 * @pre 0 &le; i &lt; 
	 * @param i Un entero getVariableNumber()
	 * @return El máximo valor del rango de valores de la variable i
	 */
	E getMax(Integer i);
	/**
	 * @pre 0 &le; i &lt; 
	 * @param i Un entero getVariableNumber()
	 * @return El mínimo valor del rango de valores de la variable i
	 */
	E getMin(Integer i);
	
	/**
	 * @param cr Un cromosoma
	 * @return La función de fitness del cromosoma
	 */
	
	Double fitnessFunction(ValuesInRangeChromosome<E> cr);
	
	/**
	 * @param cr Un cromosoma
	 * @return La solución definida por el cromosoma
	 */
	S getSolucion(ValuesInRangeChromosome<E> cr);
}
