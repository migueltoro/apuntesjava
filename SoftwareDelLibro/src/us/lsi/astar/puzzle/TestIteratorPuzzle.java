package us.lsi.astar.puzzle;

import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import us.lsi.graphs.SimpleEdge;

public class TestIteratorPuzzle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EstadoPuzzle1 p1 = EstadoPuzzle1.create(ProblemaPuzzle.create(1,2,3,4,0,5,6,7,8));
		Graph<EstadoPuzzle1,SimpleEdge<EstadoPuzzle1>> graph = 
				PuzzleGraph1.create(p1);
		Set<SimpleEdge<EstadoPuzzle1>> ss = graph.edgesOf(p1);
		System.out.println("p1 = \n"+p1);
		for (SimpleEdge<EstadoPuzzle1> e: ss){
			System.out.println(Graphs.getOppositeVertex(graph,e, p1));
		}
		
	}
	

}
