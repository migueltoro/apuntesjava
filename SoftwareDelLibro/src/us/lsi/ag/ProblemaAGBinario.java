package us.lsi.ag;

import java.util.List;

import us.lsi.ag.agchromosomes.IBinaryChromosome;

public interface ProblemaAGBinario<S> extends ProblemaAG {
	
	int getDimensionDelChromosoma();
	/**
	 * @param ls Lista de índices del cromosoma
	 * @pre ls.size() == getDimension()
	 * @return Fitness del cromosoma
	 */
	Double fitnessFunction(List<Integer> ls);
	
	/**
	 * @param cr Un cromosoma que representa indices de objetos
	 * @return La solución definida por el cromosoma
	 */
	S getSolucion(IBinaryChromosome cr);
}
