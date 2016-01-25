package us.lsi.astar;

	
	
import java.util.*;
import java.util.function.Function;

import org.jgrapht.*;
import org.jgrapht.graph.*;

import us.lsi.algoritmos.Algoritmos;



/**
 * @author Miguel Toro
 *
 * @param <V> Tipo de los vértices
 * @param <E> Tipo de las aristas
 */
public final class AStarAlgorithm<V, E> {
	

	private GraphPath<V, E> path;
	private AStarIterator<V, E> iterator;
	private AStarGraph<V, E> graph;
	private V startVertex;
	private V endVertex;
	private Function<V,Double> goalDistance=null;
	private Set<V> goalSet=null;
	
	/**
	 * <p> El algoritmo para alcanzar el vértice destino </p>
	 * 
	 * @param graph Grafo 
	 * @param startVertex Vértice origen
	 * @param endVertex Vértice destino
	 * 
	 */
	public AStarAlgorithm(AStarGraph<V, E> graph,V startVertex, V endVertex) {
		this(graph, startVertex, endVertex, null, null,
				Double.POSITIVE_INFINITY);
	}

	/**
	 * <p> El algoritmo para alcanzae el primer vértice cuya distancia al objetivo es cero </p>
	 * 
	 * @param graph Grafo
	 * @param startVertex Vértice origen
	 * @param goalDistance Distancia a un objetivo
	 * 
	 */
	public AStarAlgorithm(AStarGraph<V, E> graph, V startVertex, Function<V,Double> goalDistance) {
		this(graph, startVertex, null, goalDistance, null,
				Double.POSITIVE_INFINITY);
	}
	/**
	 * <p> El algoritmo para alcanzar uno los vértices objetivo </p>
	 * 
	 * @param graph Grafo
	 * @param startVertex Vértice origen
	 * @param goalSet Conjunto de vértices objetivo 
	 * 
	 */
	public AStarAlgorithm(AStarGraph<V, E> graph, V startVertex, Set<V> goalSet) {
		this(graph, startVertex, null, null,goalSet,
				Double.POSITIVE_INFINITY);
	}

	private AStarAlgorithm(AStarGraph<V, E> graph, V startVertex, V endVertex, Function<V,Double> goalDistance, Set<V> goalSet, double radius) {

		
		this.graph=graph;
		this.startVertex = startVertex;
		this.endVertex = endVertex;
		this.goalDistance = goalDistance;
		this.goalSet =goalSet;
		
		
		if (endVertex!=null && !graph.containsVertex(endVertex)) {
			throw new IllegalArgumentException(
					"graph must contain the end vertex");
		}

		iterator = new AStarIterator<V, E>(graph, startVertex, endVertex, goalDistance, goalSet, radius);
		
		while (iterator.hasNext()) {		
			
			V vertex = iterator.next();
			
			
			if (this.goalDistance == null && this.goalSet == null && vertex.equals(this.endVertex)
					|| (this.goalDistance != null && this.goalDistance.apply(vertex)==0.)
					|| (this.goalSet != null && this.goalSet.contains(vertex))) {
				
				path = createEdgeList(graph, iterator, startVertex, vertex);
				return;
			}
			
			
		}

		path = null;
	}

	/**
	 * @return Devuelve una lista con las aristas del camino mínimo desde el origen al último vértice alcanzado
	 */
	public List<E> getPathEdgeList() {
		if (path == null) {
			return null;
		} else {
			return path.getEdgeList();
		}
	}
	/**
	 * @return Devuelve el camino mínimo desde el origen al último vértice alcanzado
	 */
	public GraphPath<V, E> getPath() {
		return path;
	}
	
	/**
	 * @param vertex Vértice destino
	 * @return Devuelve el camino mínimo desde el origen a vertex
	 */
	public GraphPath<V, E> getPath(V vertex) {
		return createEdgeList(graph,iterator,startVertex,vertex);
	}

	/**
	 * @return Devuelve el peso del camino mínimo desde el origen al último vértice alcanzado
	 */
	public double getPathLength() {
		if (path == null) {
			return Double.POSITIVE_INFINITY;
		} else {
			return path.getWeight();
		}
	}
	
	/**
	 * @return Devuelve el iterador que se ha creado para recorrer el grafo
	 */
	public AStarIterator<V, E> getIterator() {
		return iterator;
	}
	
	/**
	 * @param graph Grafo
	 * @param startVertex Vértice origen
	 * @param endVertex Vértice Destino
	 * @param <V> El tipo de los elementos vértices
	 * @param <E> El tipo de los elementos de las aristas
	 * @return Devuelve el camino mínimo desde el origen vértice destino
	 */
	public static <V, E> List<E> findPathBetween(
			AStarGraph<V, E> graph, V startVertex, V endVertex) {

		AStarAlgorithm<V, E> alg = Algoritmos.createAStar(graph, startVertex, endVertex);

		return alg.getPathEdgeList();
	}

	private GraphPath<V, E> createEdgeList(Graph<V, E> graph,
			AStarIterator<V, E> iter, V startVertex, V endVertex) {

		List<E> edgeList = new ArrayList<E>();

		V v = endVertex;

		while (true) {
			E edge = iter.getSpanningTreeEdge(v);

			if (edge == null) {
				break;
			}

			edgeList.add(edge);
			v = Graphs.getOppositeVertex(graph, edge, v);
		}

		Collections.reverse(edgeList);
		double pathLength = iter.getShortestPathLength(endVertex);
		return new GraphPathImpl<V, E>(graph, startVertex, endVertex, edgeList,
				pathLength);
	}
}

// End AStar.java

