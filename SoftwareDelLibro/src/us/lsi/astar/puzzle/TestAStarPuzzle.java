package us.lsi.astar.puzzle;





import org.jgrapht.GraphPath;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.astar.AStarAlgorithm;
import us.lsi.astar.AStarGraph;
import us.lsi.graphs.SimpleEdge;

public class TestAStarPuzzle {

	public static void main(String[] args) {
		
		ProblemaPuzzle.numFilas = 3;
		EstadoPuzzle1 p1 = EstadoPuzzle1.create(0,1,2,3,4,5,6,7,8);
//		EstadoPuzzle p2 = EstadoPuzzle.create(1,2,3,4,5,6,7,8,0);
//		EstadoPuzzle p3 = EstadoPuzzle.create(1,2,3,4,5,0,6,7,8);
//		EstadoPuzzle p4 = EstadoPuzzle.create(1,2,3,4,5,0,6,7,8);
		EstadoPuzzle1 p5 = EstadoPuzzle1.create(1,2,3,4,0,5,6,7,8);
	
		AStarGraph<EstadoPuzzle1,SimpleEdge<EstadoPuzzle1>> graph = 
				PuzzleGraph1.create(p1,p5);
		AStarAlgorithm<EstadoPuzzle1,SimpleEdge<EstadoPuzzle1>> d = Algoritmos.createAStar(graph, p1,p5);
		GraphPath<EstadoPuzzle1,SimpleEdge<EstadoPuzzle1>> path = d.getPath();
		System.out.println(path.getWeight());
		System.out.println(path.getVertexList());
//		System.out.println(path.getVertexList().size());
//		System.out.println(path.getEdgeList().stream().collect(Collectors.toList()));
//		System.out.println(path.getEdgeList().stream().collect(Collectors.toList()).size());
/*		System.out.println(((PuzzleGraph3)graph).getNumVertexInEdgesOf());
		GraphPath<EstadoPuzzle,DefaultSimpleEdge<EstadoPuzzle>> path2 = d.getPath(p2);
		GraphPath<EstadoPuzzle,DefaultSimpleEdge<EstadoPuzzle>> path3 = d.getPath(p3);
		GraphPath<EstadoPuzzle,DefaultSimpleEdge<EstadoPuzzle>> path4 = d.getPath(p4);
		System.out.println(Graphs.getPathVertexList(path2));
		System.out.println(Graphs.getPathVertexList(path3));
		System.out.println(Graphs.getPathVertexList(path4));
*/	}

}
