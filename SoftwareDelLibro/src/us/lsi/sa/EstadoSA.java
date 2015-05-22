package us.lsi.sa;

/**
 * 
 * <p>La documentación puede encontarse en el: <a href="../../../document/Tema16.pdf" target="_blank">Tema16</a></p>
 * 
 * @author Miguel Toro
 *
 * @param <E> Tipo del Estado 
 * @param <S> Tipo de la solución del problema
 * @param <A> Tipo de las alternativas
 */
public interface EstadoSA<E extends EstadoSA<E,S,A>,S,A> {	
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
	 * @return Solución del problema calculada a partir del estado actual
	 */
	S getSolucion();
	/**
	 * La condición de parada establece cuando se parará el algortimo. Esta condición se puede establecer a partir
	 * de la propiedades fijadas para el algortimo: número de soluciones encontradas, número de iteraciones, 
	 * valor de la función objetivo, etc.
	 * 
	 * @return Si se cumple la condición del parada del algortimo.
	 */
	boolean condicionDeParada();
	/**
	 * @return Valor de la propiedad a minimizar
	 */
	double getObjetivo(); 
	/**
	 * @return Una copia del estado 
	 */
	E copia();
	/**
	 * @param s En un valor del tipo S
	 * @return Si s es una solcuión del problema
	 */
	boolean esSolucion(S s);
}
