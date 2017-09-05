package us.lsi.graphs;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.GraphWalk;

public class GraphWalk2<V,E> extends GraphWalk<V, E> {

	public GraphWalk2(Graph<V, E> graph, List<V> vertexList, double weight) {
		super(graph, vertexList, weight);
	}

	public GraphWalk2(Graph<V, E> graph, V startVertex, V endVertex,
			List<E> edgeList, double weight) {
		super(graph, startVertex, endVertex, edgeList, weight);		
	}

	public GraphWalk2(Graph<V, E> graph, V startVertex, V endVertex,
			List<V> vertexList, List<E> edgeList, double weight) {
		super(graph, startVertex, endVertex, vertexList, edgeList, weight);		
	}

	public GraphWalk2(Graph<V, E> graph, List<V> vertexList) {
		super(graph, vertexList,0.);
	}
	@Override
	public double getWeight(){
		return super.getEdgeList().stream().mapToDouble(x->super.getGraph().getEdgeWeight(x)).sum();
	}
}
