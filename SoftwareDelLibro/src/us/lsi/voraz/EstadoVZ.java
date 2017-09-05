package us.lsi.voraz;

/**
*<p> Tipo mutable del estado de un problema que se quiere resolver por una técnica Voraz. 
 * Cada estado está asociado un problema, del conjunto de problemas considerado, más el camino, secuencia de alternativas, para llegar a él desde el problema inicial. </p>
 * 
 * <p>La documentación puede encontarse en el: <a href="../../../document/Tema15.pdf" target="_blank">Tema15</a></p>
*
*
* @param <E> Tipo del Estado 
* @param <S> Tipo de la solución del problema
* @param <A> Tipo de las alternativas
*/
public interface EstadoVZ<E extends EstadoVZ<E,S,A>,S,A> {	
	/**
	 * @param a - Alternativa elegida
	 * @return Siguiente estado si se escoge la alternativa a
	 */
	E next(A a);	
	/**
	 * @return La alternativa escogida en el estado actual. 
	 */
	A getAlternativa();
	/**
	 * @return Solución del problema calculada a partir del estado actual en el estado que cumple la condición de parada o null si no hay solución
	 */
	S getSolucion();
	/**
	 * La condición de parada establece cuando se parará el algoritmo. Esta condición se puede establecer a partir
	 * de la propiedades fijadas para el algoritmo: número de soluciones encontradas, número de iteraciones, etc.
	 * 
	 * @return Si se cumple la condición del parada del algortimo.
	 */
	boolean condicionDeParada();
}

