package us.lsi.astar;

	
	
import java.util.*;
import java.util.function.Predicate;

import org.jgrapht.*;
import org.jgrapht.graph.*;



/**
 * <p> Implementación del algoritmo A*. Adaptación de la clase DijkstraShortestPath en <p> <a href="http://jgrapht.org/javadoc/" target="_blank">JGrapht</a></p>
 * 
 * @author Miguel Toro
 *
 * @param <V> Tipo de los vértices
 * @param <E> Tipo de las aristas
 */
public final class AStarAlgorithm1<V, E> implements AStarAlgorithm<V, E> {
	

	private GraphPath<V, E> path;
	private AStarIterator<V, E> iterator;
	private AStarGraph<V, E> graph;
	private V startVertex;
	private V endVertex;
	private Predicate<V> goal=null;
	
	/**
	 * <p> El algoritmo para alcanzar el vértice destino </p>
	 * 
	 * @param graph Grafo 
	 * @param startVertex Vértice origen
	 * @param endVertex Vértice destino
	 * 
	 */
	public AStarAlgorithm1(AStarGraph<V, E> graph,V startVertex, V endVertex) {
		this(graph, startVertex, endVertex, null,
				Double.POSITIVE_INFINITY);
	}

	/**
	 * <p> El algoritmo para alcanzae el primer vértice cuya distancia al objetivo es cero </p>
	 * 
	 * @param graph Grafo
	 * @param startVertex Vértice origen
	 * @param goal Predicado que indica las condiciones de un vértice final
	 * 
	 */
	public AStarAlgorithm1(AStarGraph<V, E> graph, V startVertex, Predicate<V> goal) {
		this(graph, startVertex, null, goal,
				Double.POSITIVE_INFINITY);
	}

	private AStarAlgorithm1(AStarGraph<V, E> graph, V startVertex, V endVertex, Predicate<V> goal, double radius) {

		
		this.graph=graph;
		this.startVertex = startVertex;
		this.endVertex = endVertex;
		this.goal = goal;
		
		
		if (endVertex!=null && !graph.containsVertex(endVertex)) {
			throw new IllegalArgumentException(
					"graph must contain the end vertex");
		}

		iterator = new AStarIterator<V, E>(graph, startVertex, endVertex, radius);
		
		while (iterator.hasNext()) {		
			
			V vertex = iterator.next();
			
			
			if (this.endVertex != null && vertex.equals(this.endVertex)
			    || (this.goal != null && this.goal.test(vertex))) {
				
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

		AStarAlgorithm1<V, E> alg = new AStarAlgorithm1<>(graph, startVertex, endVertex);

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
		return new GraphWalk<V, E>(graph, startVertex, endVertex, edgeList,
				pathLength);
	}

	@Override
	public List<V> getPathVertexList() {
		return getPath().getVertexList();
	}
}

// End AStar.java

