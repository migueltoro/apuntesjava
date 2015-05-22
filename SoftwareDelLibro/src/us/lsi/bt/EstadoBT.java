package us.lsi.bt;

import java.util.List;


/**
 * <p> Tipo mutable del estado de un problema que se quiere resolver por la técnica de Bactracking. 
 * Cada estado está asociado un problema, del conjunto de problemas considerado, más el camino, secuencia de alternativas, para llegar a él desde el problema inicial. </p>
 * 
 * <p>La documentación puede encontarse en el: <a href="../../../document/Tema15.pdf" target="_blank">Tema15</a></p>
 *
 * @author Miguel Toro
 *
 * @param <S> El tipo de la solución
 * @param <A> El tipo de la alternativa
 */
public interface EstadoBT<S, A> {
	/**
	 * @param a - alternativa escogida para cambiar el estado
	 */
	void avanza(A a);
	/**
	 * @param a - alternativa a eliminar para restaurar el estado anterior
	 */
	void retrocede(A a);
	/**
	 * @return Tamaño del problema al que está asociado el estado
	 */
	int size();
	/**
	 * @return Si el problema asociado al estado es final
	 */
	boolean isFinal();
	/**
	 * @return Las alternativas admisibles para el problema asociado
	 */
	List<A> getAlternativas();
	/**
	 * <p> Para poder calcular la solución del problema inicial en el estado final es muy recomendable que el estado disponga de una propiedad de tipo List&lt;A&gt; 
	 * que almacena se secuencia de alternativas tomadas. </p>.
	 * 
	 * @pre isFinal()
	 * @return Una solución del problema inicial y null si no hay solución para el problema inicial tras la secuencia de alternativas tomadas
	 * @throws IllegalStateException - Si se invoca el método en un estado no final
	 */
	S getSolucion();
	
	/**
	 * @pos Si no es posible calcularla  se devolverá MAX_VALUE si el problema es de minimización y MIN_VALUE si es de maximización. 
	 * @return Valor de propiedad objetivo del problema inicial asumiendo que estamos en el problema final.    
	 */
	Double getObjetivo();
	
	/**
	 * @pos 
	 * <ul>
	 * <li> Si el problema es de minimización el valor debe ser 
	 * una cota inferior del valor de la propiedad objetivo del problema inicial, 
	 * asumiendo que se ha alcanzado el problema actual y se  va a seguir la alternativa <code> a </code>.
	 * La alternativa puede ser descartada si <code> getObjetivoEstimado(a) &gt; =  AlgoritmoPD.getMejorValor() </code>,
	 * puesto que el valor objetivo de la solución obtnida estaría por encima del mejor valor obtenido hasta el momento.
	 * Es decir nos quedamos con las alternativas que cumplen 
	 * <code> getObjetivoEstimado(a) &lt;   AlgoritmoPD.getMejorValor() </code>.
	 * Donde <code> AlgoritmoPD.getMejorValor() </code> es el mejor valor encontrado por el algoritmo hasta ahora.
	 * <li> Si el problema original fuera de maximización el valor debe ser 
	 * una cota superior del valor de la propiedad objetivo del problema inicial, 
	 * asumiendo que se ha alcanzado el problema actual y se  va a seguir la alternativa <code> a </code>.
	 *  Es decir nos quedamos con las alternativas que cumplen 
	 * <code> getObjetivoEstimado(a) &gt;   AlgoritmoPD.getMejorValor() </code>.
	 * </ul>
	 * <p> Si no es posible estimar esa cota entonces devolverá MIN_VALUE si el problema es de minimización
	 *  y MAX_VALUE si es de maximización</p>
	 * @param a - Alternativa elegida
	 * @return El valor objetivo estimado si seguimos <code> a </code>
	 */
	Double getObjetivoEstimado(A a);
	
}
