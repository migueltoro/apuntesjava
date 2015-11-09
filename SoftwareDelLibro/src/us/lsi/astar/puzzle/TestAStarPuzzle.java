package us.lsi.astar.puzzle;



import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;








import com.google.common.collect.Sets;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.astar.AStarAlgorithm;
import us.lsi.astar.AStarGraph;
import us.lsi.graphs.SimpleEdge;

public class TestAStarPuzzle {

	public static void main(String[] args) {
		
		EstadoPuzzle.numFilas = 3;
		EstadoPuzzle p1 = EstadoPuzzle.create(0,1,2,3,4,5,6,7,8);
		EstadoPuzzle p2 = EstadoPuzzle.create(1,2,3,4,5,6,7,8,0);
		EstadoPuzzle p3 = EstadoPuzzle.create(1,2,3,4,5,0,6,7,8);
/*		EstadoPuzzle p4 = EstadoPuzzle.create(1,2,3,4,5,0,6,7,8);
*/		
		AStarGraph<EstadoPuzzle,SimpleEdge<EstadoPuzzle>> graph = 
				PuzzleGraph3.create(
						SimpleEdge.<EstadoPuzzle>create());
		AStarAlgorithm<EstadoPuzzle,SimpleEdge<EstadoPuzzle>> d = 
				Algoritmos.createAStar(graph, p1, Sets.newHashSet(p2,p3));
		GraphPath<EstadoPuzzle,SimpleEdge<EstadoPuzzle>> path = d.getPath();
		System.out.println(Graphs.getPathVertexList(path));
		System.out.println(((PuzzleGraph3)graph).getNumVertexInEdgesOf());
/*		GraphPath<EstadoPuzzle,DefaultSimpleEdge<EstadoPuzzle>> path2 = d.getPath(p2);
		GraphPath<EstadoPuzzle,DefaultSimpleEdge<EstadoPuzzle>> path3 = d.getPath(p3);
		GraphPath<EstadoPuzzle,DefaultSimpleEdge<EstadoPuzzle>> path4 = d.getPath(p4);
		System.out.println(Graphs.getPathVertexList(path2));
		System.out.println(Graphs.getPathVertexList(path3));
		System.out.println(Graphs.getPathVertexList(path4));
*/	}

}
