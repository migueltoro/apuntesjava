package us.lsi.astar;

import java.util.*;
import java.util.function.Predicate;

import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.util.*;

/**
 * <p> Implementación del algoritmo A*. Adaptación de la clase AStarShortestPath en <p> <a href="http://jgrapht.org/javadoc/" target="_blank">JGrapht</a></p> 
 * 
 *
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 *
 * @author Miguel Toro
 */
public class AStarAlgorithm2<V, E> implements AStarAlgorithm<V, E> {
   
	private final AStarGraph<V, E> graph;

    // List of open nodes
    protected FibonacciHeap<V> openList;
    protected Map<V, FibonacciHeapNode<V>> vertexToHeapNodeMap;

    // List of closed nodes
    protected Set<V> closedList;

    // Mapping of nodes to their g-scores (g(x)).
    protected Map<V, Double> gScoreMap;

    // Predecessor map: mapping of a node to an edge that leads to its
    // predecessor on its shortest path towards the targetVertex
    protected Map<V, E> cameFrom;

    // Reference to the admissible heuristic
    // protected AStarAdmissibleHeuristic<V> admissibleHeuristic;

    // Counter which keeps track of the number of expanded nodes
    protected int numberOfExpandedNodes;
    
    private Predicate<V> goal = null;
    private V sourceVertex;
    private V targetVertex;
    private GraphPath<V,E> graphPath = null;

    /**
     * Create a new instance of the A* shortest path algorithm.
     * 
     * @param sourceVertex Source Vertex
     * @param targetVertex Target Vertex
     * @param graph the input graph
     */
    public AStarAlgorithm2(AStarGraph<V, E> graph, V sourceVertex, V targetVertex)
    {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null!");
        }
        if (!graph.containsVertex(sourceVertex) || !graph.containsVertex(targetVertex)) {
            throw new IllegalArgumentException(
              "Source or target vertex not contained in the graph!");
        }
        this.graph = graph;
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
        this.goal = null;
    }
    
    /**
     * Create a new instance of the A* shortest path algorithm.
     * 
     * @param graph the input graph
     * @param sourceVertex Source Vertex
     * @param goal Predicado que especifica el objetivo
     */
    public AStarAlgorithm2(AStarGraph<V, E> graph, V sourceVertex, Predicate<V> goal)
    {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null!");
        }
        if (!graph.containsVertex(sourceVertex)) {
            throw new IllegalArgumentException(
              "Source vertex not contained in the graph!");
        }
        if (goal == null) {
            throw new IllegalArgumentException("Goal cannot be null!");
        }
        this.graph = graph;
        this.sourceVertex = sourceVertex;
        this.targetVertex = null;
        this.goal = goal;
    }

    /**
     * Initializes the data structures
     *
     * @param admissibleHeuristic admissible heuristic
     */
//    private void initialize(AStarAdmissibleHeuristic<V> admissibleHeuristic)
    private void initialize()
    {
//        this.admissibleHeuristic = admissibleHeuristic;
        openList = new FibonacciHeap<>();
        vertexToHeapNodeMap = new HashMap<>();
        closedList = new HashSet<>();
        gScoreMap = new HashMap<>();
        cameFrom = new HashMap<>();
        numberOfExpandedNodes = 0;
    }

    /**
     * Calculates (and returns) the shortest path from the sourceVertex to the targetVertex.
     * @return The shortest path
     */
//   public GraphPath<V, E> getShortestPath(
//       V sourceVertex, V targetVertex, AStarAdmissibleHeuristic<V> admissibleHeuristic)
//   {
//        if (!graph.containsVertex(sourceVertex) || !graph.containsVertex(targetVertex)) {
//            throw new IllegalArgumentException(
//                "Source or target vertex not contained in the graph!");
//        }
//    public GraphPath<V, E> getShortestPath(V sourceVertex, V targetVertex){
//      this.initialize(admissibleHeuristic);
    public GraphPath<V, E> getShortestPath(){
    	this.initialize();
        gScoreMap.put(sourceVertex, graph.getVertexWeight(sourceVertex));
        FibonacciHeapNode<V> heapNode = new FibonacciHeapNode<>(sourceVertex);
        openList.insert(heapNode, graph.getVertexWeight(sourceVertex));
        vertexToHeapNodeMap.put(sourceVertex, heapNode);

        do {
            FibonacciHeapNode<V> currentNode = openList.removeMin();

			// Check whether we reached the target vertex
			if ((goal != null && goal.test(currentNode.getData()) || 
				 currentNode.getData().equals(targetVertex))
				) {
				// Build the path
				// return this.buildGraphPath(sourceVertex, targetVertex,
				// currentNode.getKey());
				E edgeBeforeCurrent = cameFrom.get(currentNode.getData());
				return this.buildGraphPath(sourceVertex, currentNode.getData(),		
						currentNode.getKey()+graph.getVertexWeight(currentNode.getData(), edgeBeforeCurrent, null));
			}

            // We haven't reached the target vertex yet; expand the node
            expandNode(currentNode, targetVertex);
            closedList.add(currentNode.getData());
        } while (!openList.isEmpty());

        // No path exists from sourceVertex to TargetVertex
        return null;
    }


	private void expandNode(FibonacciHeapNode<V> currentNode, V endVertex)
    {
        numberOfExpandedNodes++;

        Set<E> outgoingEdges = null;
        if (graph instanceof Graph) {
            outgoingEdges = graph.edgesOf(currentNode.getData());
        } else if (graph instanceof Graph) {
            outgoingEdges = graph.outgoingEdgesOf(currentNode.getData());
        }

        
        for (E edge : outgoingEdges) {
            V successor = Graphs.getOppositeVertex(graph, edge, currentNode.getData());
            if ((successor == currentNode.getData()) || closedList.contains(successor)) { // Ignore
                                                                                          // self-loops
                                                                                          // or
                                                                                          // nodes
                                                                                          // which
                                                                                          // have
                                                                                          // already
                                                                                          // been
                                                                                          // expanded
                continue;
            }
            
            E edgeBeforeCurrent = cameFrom.get(currentNode.getData());
            double gScore_current = gScoreMap.get(currentNode.getData());
            double tentativeGScore = gScore_current + 
            		                 graph.getEdgeWeight(edge)+
            		                 graph.getVertexWeight(successor)+
					                 graph.getVertexWeight(currentNode.getData(), edgeBeforeCurrent, edge);

            if (!vertexToHeapNodeMap.containsKey(successor)
                || (tentativeGScore < gScoreMap.get(successor)))
            {
                cameFrom.put(successor, edge);
                gScoreMap.put(successor, tentativeGScore);

                double fScore =
                    tentativeGScore + graph.getWeightToEnd(successor, endVertex);
                if (!vertexToHeapNodeMap.containsKey(successor)) {
                    FibonacciHeapNode<V> heapNode = new FibonacciHeapNode<>(successor);
                    openList.insert(heapNode, fScore);
                    vertexToHeapNodeMap.put(successor, heapNode);
                } else {
                    openList.decreaseKey(vertexToHeapNodeMap.get(successor), fScore);
                }
            }
        }
    }

    /**
     * Builds the graph path
     *
     * @param startVertex starting vertex of the path
     * @param targetVertex ending vertex of the path
     * @param pathLength length of the path
     *
     * @return the shortest path from startVertex to endVertex
     */
    private GraphPath<V, E> buildGraphPath(V startVertex, V targetVertex, double pathLength)
    {
        List<E> edgeList = new ArrayList<>();
        List<V> vertexList = new ArrayList<>();
        vertexList.add(targetVertex);

        V v = targetVertex;
      while (!v.equals(startVertex)) {  //cambiado por mí
//    while (v != startVertex) {
            edgeList.add(cameFrom.get(v));
            v = Graphs.getOppositeVertex(graph, cameFrom.get(v), v);
            vertexList.add(v);
        }
        Collections.reverse(edgeList);
        Collections.reverse(vertexList);
        return new GraphWalk<>(graph, startVertex, targetVertex,edgeList, pathLength);
    }

    /**
     * Returns how many nodes have been expanded in the A* search procedure in its last invocation.
     * A node is expanded if it is removed from the open list.
     *
     * @return number of expanded nodes
     */
    public int getNumberOfExpandedNodes()
    {
        return numberOfExpandedNodes;
    }
    
    @Override
    public GraphPath<V, E> getPath(){
    	if(graphPath==null)
    		graphPath = this.getShortestPath();
    	return graphPath;
    }
    
    @Override
	public List<V> getPathVertexList() {
		return getPath().getVertexList();
	}
}

// End AStarShortestPath.java
