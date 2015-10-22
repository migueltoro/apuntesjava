package us.lsi.ag;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.base.Preconditions;

import us.lsi.ag.agchromosomes.IndexChromosome;
import us.lsi.common.Lists2;
/**
 * @author Miguel Toro
 *
 * @param <S> Tipo de la solución
 */
public interface ProblemaAGIndex<S> extends ProblemaAG {	
	
	
	/**
	 * @return Numero de objetos distintos
	 */
	Integer getObjectsNumber();
	
	/**
	 * @pre <code> 0 &le; index &lt; getObjetos().size() </code>
	 * @param index Indice en la lista de objetos disponibles
	 * @return La multiplicidad máxima del objeto de índice <code> index </code>
	 */
	
	default Integer getMax(int index){
		Preconditions.checkElementIndex(index, this.getObjectsNumber());
		return 1;
	}
	
	
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
	S getSolucion(IndexChromosome cr);
	
	
	
    default List<Integer> getNormalSequence() {
		return IntStream.range(0,getObjectsNumber())
				.boxed()
				.map(x->Lists2.nCopias(getMax(x),x).stream())
				.flatMap(x->x)
				.collect(Collectors.toList());
	}
}
