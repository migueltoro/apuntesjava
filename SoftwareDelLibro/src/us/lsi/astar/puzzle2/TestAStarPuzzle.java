package us.lsi.astar.puzzle2;



import java.util.stream.Collectors;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.astar.AStarAlgorithm;
import us.lsi.astar.AStarGraph;
import us.lsi.common.ParInteger;
import us.lsi.graphs.AlternativeSimpleEdge;

public class TestAStarPuzzle {

	public static void main(String[] args) {
		
		EstadoPuzzle.numFilas = 3;
		EstadoPuzzle p1 = EstadoPuzzle.create(1,0,2,3,4,5,6,7,8);
//		EstadoPuzzle p2 = EstadoPuzzle.create(1,2,3,4,5,6,7,8,0);
//		EstadoPuzzle p3 = EstadoPuzzle.create(1,2,3,4,5,0,6,7,8);
//		EstadoPuzzle p4 = EstadoPuzzle.create(1,2,3,4,5,0,6,7,8);
		EstadoPuzzle p5 = EstadoPuzzle.create(1,2,3,4,0,5,6,7,8);
	
		AStarGraph<EstadoPuzzle,AlternativeSimpleEdge<ParInteger,EstadoPuzzle>> graph = PuzzleGraph1.create(p1,p5);
		AStarAlgorithm<EstadoPuzzle,AlternativeSimpleEdge<ParInteger,EstadoPuzzle>> d = Algoritmos.createAStar(graph, p1, p5);
		GraphPath<EstadoPuzzle,AlternativeSimpleEdge<ParInteger,EstadoPuzzle>> path = d.getPath();
		System.out.println(Graphs.getPathVertexList(path));
		System.out.println(Graphs.getPathVertexList(path).size());
		System.out.println(path.getEdgeList().stream().map(x->x.getAlternative()).collect(Collectors.toList()));
		System.out.println(path.getEdgeList().stream().map(x->x.getAlternative()).collect(Collectors.toList()).size());
/*		System.out.println(((PuzzleGraph3)graph).getNumVertexInEdgesOf());
		GraphPath<EstadoPuzzle,DefaultSimpleEdge<EstadoPuzzle>> path2 = d.getPath(p2);
		GraphPath<EstadoPuzzle,DefaultSimpleEdge<EstadoPuzzle>> path3 = d.getPath(p3);
		GraphPath<EstadoPuzzle,DefaultSimpleEdge<EstadoPuzzle>> path4 = d.getPath(p4);
		System.out.println(Graphs.getPathVertexList(path2));
		System.out.println(Graphs.getPathVertexList(path3));
		System.out.println(Graphs.getPathVertexList(path4));
*/	}

}
