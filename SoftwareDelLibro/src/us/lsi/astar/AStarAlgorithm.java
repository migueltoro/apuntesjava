package us.lsi.astar;

import java.util.List;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

/**
 * Interface y factoría de un algoritmo A*. 
 * 
 * @author Miguel Toro
 *
 * @param <V> Tipo de los vértices
 * @param <E> Tipo de las aristas
 */
public interface AStarAlgorithm<V, E> {
	
	
	public static <V,E> AStarAlgorithm<V,E> create(AStarGraph<V, E> graph,V startVertex, V endVertex){
		return new AStarAlgorithm2<>(graph,startVertex,endVertex);
	}
	
	public static <V,E> AStarAlgorithm<V,E> create(AStarGraph<V, E> graph,V startVertex, Predicate<V> goal){
		return new AStarAlgorithm2<>(graph,startVertex,goal);
	}
	
	public GraphPath<V, E> getPath();
	public List<V> getPathVertexList();
	
	
}
