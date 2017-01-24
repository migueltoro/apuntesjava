package us.lsi.ag;

import us.lsi.ag.agchromosomes.BinaryChromosome2;


/**
 * @author Miguel Toro
 *
 * @param <S> El tipo de la solución del problema
 * 
 * 
 * <p> Un problema cuya solución puede ser modelada con variables binarias. Usa un cromomosoma de tipo BinaryChromosome2</p>
 */
public interface ProblemaAGBinary<S> extends ProblemaAG {
	
	/**
	 * @return Dimensión del cromosoma. Es el número de variables binarias del problema
	 */
	int getDimensionDelChromosoma();
	
	/**
	 * @param cr Un cromosoma
	 * @return La función de fitness del cromosoma
	 */
	
	Double fitnessFunction(BinaryChromosome2 cr);
	
	/**
	 * @param cr Un cromosoma
	 * @return La solución definida por el cromosoma
	 */
	S getSolucion(BinaryChromosome2 cr);
}
