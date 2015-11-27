package us.lsi.astar.puzzle;

import java.util.Set;
import java.util.function.Function;

import org.jgrapht.EdgeFactory;

import us.lsi.graphs.SimpleEdge;

public class PuzzleGraph2 extends PuzzleGraph1 {

	public static PuzzleGraph2 create(
			EdgeFactory<EstadoPuzzle, SimpleEdge<EstadoPuzzle>> edgeFactory) {
		return new PuzzleGraph2(edgeFactory);
	}

	private PuzzleGraph2(EdgeFactory<EstadoPuzzle, SimpleEdge<EstadoPuzzle>> edgeFactory) {
		super(edgeFactory);
	}

	@Override
	public double getWeightToEnd(EstadoPuzzle actual,EstadoPuzzle endVertex, Function<EstadoPuzzle,Double> goalDistance,
			Set<EstadoPuzzle> goalSet) {
		if(actual==null || goalDistance==null)
			throw new IllegalArgumentException("El vértice inicial y el goal no pueden ser null");
		return goalDistance.apply(actual);
	}
}
