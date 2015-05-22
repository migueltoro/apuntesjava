package us.lsi.astar.puzzle;

import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.EdgeFactory;

import us.lsi.graphs.SimpleEdge;

public class PuzzleGraph3 extends PuzzleGraph1 {

	public static PuzzleGraph3 create(
			EdgeFactory<EstadoPuzzle, SimpleEdge<EstadoPuzzle>> edgeFactory) {
		return new PuzzleGraph3(edgeFactory);
	}

	private PuzzleGraph3(
			EdgeFactory<EstadoPuzzle, SimpleEdge<EstadoPuzzle>> edgeFactory) {
		super(edgeFactory);
	}

	@Override
	public double getWeightToEnd(EstadoPuzzle startVertex,EstadoPuzzle endVertex, Predicate<EstadoPuzzle> goal,
			Set<EstadoPuzzle> goalSet) {
		if(startVertex==null || goalSet==null)
			throw new IllegalArgumentException("El vértice inicial y el goalSet no pueden ser null");
		Double r = 0.;
		for(EstadoPuzzle e: goalSet){
			r = r+startVertex.getNumDiferentes(e);
		}	
		return r;
	}
}
