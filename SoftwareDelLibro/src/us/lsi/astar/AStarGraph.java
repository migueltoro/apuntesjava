package us.lsi.astar;

import java.util.Set;
import java.util.function.Function;

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
	 * @param goalDistance Un función que mide la distancia del vértice actual a un objetivo. Debe cumplirse que si se alcanza el objetivo la distancia es cero
	 * @param goalSet Un conjunto de vértices destino
	 * @return Una cota inferior del peso del camino desde el vértice actual al destino, 
	 * o desde el vértice actual al objetivo
	 * o del mínimo del vértice actual a los objetivos
	 */
	double getWeightToEnd(V actual, V endVertex, Function<V,Double> goalDistance, Set<V> goalSet);
}
