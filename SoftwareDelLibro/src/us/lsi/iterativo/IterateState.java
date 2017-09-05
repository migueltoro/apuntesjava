package us.lsi.iterate;

/**
 * @author Miguel Toro
 * 
 * <p> Tipo que representa la información esencial de un algoritmo iterativo o recursivo final </p>
 *
 * @param <R> Tipo del resultado del algoritmo iterativo
 * @param <E> Tipo que agrega las variables del estado del algoritmo
 */
public interface IterateState<R,E extends IterateState<R,E>> {	
	/**
	 * @return Siguiente estado
	 */
	E next();
	/**
	 * @return Valor devuelto por el algoritmo
	 */
	R ret();
	/**
	 * @return Verdadero mientras esté dentro del dominio
	 */
	boolean domain();
}
