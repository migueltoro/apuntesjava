package us.lsi.graphs;

import java.util.Set;

/**
 * El tipo representa un vértice de un grafo virtual no dirigido
 * 
 * @author Miguel Toro
 *
 * @param <V> Tipo de los vértices
 * @param <E> Tipo de las aristas
 */
public interface VirtualVertex<V extends VirtualVertex<V,E>, E> {
	/**
	 * @return Conjunto de los vértices vecinos
	 */
	Set<V> getNeighborListOf();
	/**
	 * @return Conjunto de las arista hacia los vértices vecinos
	 */
	Set<E> edgesOf();
	/**
	 * @param e Vértice que se pregunta si es vecino
	 * @return Si el vértice es vecino
	 */
	boolean isNeighbor(V e);
}
