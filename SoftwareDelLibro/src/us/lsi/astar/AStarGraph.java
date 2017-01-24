package us.lsi.astar;


import org.jgrapht.Graph;

public interface AStarGraph<V, E> extends Graph<V,E> {
	/**
	 * @param vertex es el vértice actual
	 * @return El peso de vertex
	 */
	default double getVertexWeight(V vertex) {
		return 0.;
	}
	/**
	 * @param vertex El vértice actual
	 * @param edgeIn Una arista entrante o incidente en el vértice actual. Es null en el vértice inicial.
	 * @param edgeOut Una arista saliente o incidente en el vértice actual. Ees null en el vértice final.
	 * @return El peso asociado al vértice suponiendo las dos aristas dadas. 
	 */
	default double getVertexWeight(V vertex, E edgeIn, E edgeOut) {
		return 0.;
	}
	
	/**
	 * @param actual El vértice actual
	 * @param endVertex El vértice destino. Este vértice puede ser null. 
	 * 
	 * @return Una cota inferior del peso del camino desde el vértice actual al destino, 
	 * o desde el vértice actual al conjunto de vértices descrito por un predicado objetivo que se especificará en el AStarAlgorithm.
	 * Debe cumplirse la distancia es cero si el vértice actual cumple el predicado objetivo
	 */
	default double getWeightToEnd(V actual, V endVertex) {
		return 0.;
	}
	
}
