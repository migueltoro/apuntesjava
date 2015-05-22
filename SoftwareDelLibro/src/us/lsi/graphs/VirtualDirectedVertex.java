package us.lsi.graphs;

import java.util.Set;

/**
 * El tipo representa un vértice de un grafo virtual dirigido
 * 
 * @author Miguel Toro
 *
 * @param <V> Tipo de los vértices
 * @param <E> Tipo de las aristas
 * 
 */

public interface VirtualDirectedVertex<V extends VirtualDirectedVertex<V,E>,E> extends VirtualVertex<V,E> {
	/**
	 * @return Conjunto de vecinos entrantes
	 */
	Set<V> getPredecessorListOfListOf();
	/**
	 * @return Conjunto de aristas entrantes
	 */
	Set<E> incomingEdgesOf();
	/**
	 * @return Conjunjto de vecincos salientes
	 */
	Set<V> getSuccessorListOfListOf();
	/**
	 * @return Conjunto de aristas salientes
	 */
	Set<E> outgoingEdgesOf();
}
