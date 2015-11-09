package us.lsi.ag;


import us.lsi.ag.agchromosomes.IBinaryChromosome;

/**
 * @author Miguel Toro
 *
 * @param <S> El tipo de la solcuión del problema
 * 
 * 
 * <p> Un problema cuya solución puede ser modelada con variables binarias. Usa un cromomosoma de tipo IBinaryChromosom</p>
 */
public interface ProblemaAGBinario<S> extends ProblemaAG {
	
	/**
	 * @return Dimensión del cromosoma. Es el número de variables binarias del problema
	 */
	int getDimensionDelChromosoma();
	
	/**
	 * @param cr Un cromosoma
	 * @return La función de fitness del cromosoma
	 */
	
	Double fitnessFunction(IBinaryChromosome cr);
	
	/**
	 * @param cr Un cromosoma
	 * @return La solución definida por el cromosoma
	 */
	S getSolucion(IBinaryChromosome cr);
}
