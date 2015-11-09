package us.lsi.astar.puzzle;

import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import us.lsi.graphs.SimpleEdge;

public class TestIteratorPuzzle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EstadoPuzzle p1 = EstadoPuzzle.create(1,2,3,4,0,5,6,7,8);
		Graph<EstadoPuzzle,SimpleEdge<EstadoPuzzle>> graph = 
				PuzzleGraph1.create(SimpleEdge.<EstadoPuzzle>create());
		Set<SimpleEdge<EstadoPuzzle>> ss = graph.edgesOf(p1);
		System.out.println("p1 = \n"+p1);
		for (SimpleEdge<EstadoPuzzle> e: ss){
			System.out.println(Graphs.getOppositeVertex(graph,e, p1));
		}
		
	}
	

}
