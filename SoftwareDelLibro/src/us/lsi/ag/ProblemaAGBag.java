package us.lsi.ag;

import java.util.List;

import com.google.common.base.Preconditions;

/**
 * @author Miguel Toro
 *
 * @param <S> Tipo de la solución
 */
public interface ProblemaAGBag<S> {	
	
	
	/**
	 * @return Numero de objetos distintos
	 */
	Integer getNumeroDeObjetos();
	
	/**
	 * @pre <code> 0 &le; index &lt; getObjetos().size() </code>
	 * @param index Indice en la lista de objetos disponibles
	 * @return La multiplicidad máxima del objeto de índice <code> index </code>
	 */
	
	default Integer getMultiplicidadMaxima(int index){
		Preconditions.checkElementIndex(index, this.getNumeroDeObjetos());
		return 1;
	}
	
	
	/**
	 * @param ls Lista de índices del cromosoma
	 * @pre ls.size() == getDimension()
	 * @return Fitness del cromosoma
	 */
	Double fitnessFunction(List<Integer> ls);
	
	/**
	 * @param chromosome Es un cromosoma
	 * @return La solución definida por el cromosoma
	 */
	S getSolucion(Cromosoma<Integer> chromosome);
}
