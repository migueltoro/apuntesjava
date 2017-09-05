package us.lsi.dyv;

import java.util.List;


/**
 * @author Miguel Toro
 *
 * @param <S> El tipo de la solución
 * @param <E> El tipo de las soluciones intermedias
 */
public interface ProblemaDyV<S,E> {	
	/**
	 * @constraint El tamaño de un problema debe ser mayor que el de cada uno de sus subproblemas
	 * @return El tamaño de un problema
	 */
	int size();
	/**
	 * @return Si el problema es un caso base
	 */
	boolean esCasoBase();
	/**
	 * @return La solución del caso base
	 */
	E getSolucionCasoBase();
	/**
	 * @constraint Si todos los valores en ls son null la solución devuelta es null
	 * @pre ls!=null
	 * @param ls - Soluciones de los subproblemas alcanzados tras tomar las diferentes alterantivas
	 * @return La solución del problema
	 */
	E combina(List<E> ls);
	/**
	 * @pre 0 &lt; = i &lt; getNumeroSubProblemas(a)
	 * @param i - Número del subproblema
	 * @return Subproblema
	 */
	ProblemaDyV<S,E> getSubProblema(int i);
	/**
	 * @return Número de subproblemas
	 */
	int getNumeroDeSubProblemas();	
	/**
	 * @param e - Solución parcial
	 * @return Solución construida a partir de la solución parcial
	 */
	S getSolucion(E e);
}
