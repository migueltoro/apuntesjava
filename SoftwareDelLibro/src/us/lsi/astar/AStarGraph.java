package us.lsi.astar;

import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.Graph;

public interface AStarGraph<V, E> extends Graph<V,E> {
	/**
	 * @param vertex es el vértice actual
	 * @return El peso de vertex
	 */
	double getVertexWeight(V vertex);
	/**
	 * @param vertex El vértice actual
	 * @param edgeIn Una arista entrante o incidente en el vértice actual
	 * @param edgeOut Una arista saliente o incidente en el vértice actual
	 * @return el peso asociado al vértice suponiendo las dos aristas dadas dónde la primera puede ser null
	 */
	double getVertexWeight(V vertex, E edgeIn, E edgeOut);
	
	/**
	 * @param actual El vértice actual
	 * @param endVertex El vértice destino
	 * @param goal El predicado que debe cumplir el vértice destino
	 * @param goalSet Un conjunto de vértices destino
	 * @return Una cota inferior del peso del camino desde el vértice actual al destino, 
	 * o desde el vértice actual al vértice que cumple el predicado
	 * o de la suma de los pesos de los caminos mínimos del vértice actual a los objetivos
	 */
	double getWeightToEnd(V actual, V endVertex, Predicate<V> goal, Set<V> goalSet);
}
