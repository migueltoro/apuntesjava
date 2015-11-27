package us.lsi.astar.puzzle;

import java.util.Set;
import java.util.function.Function;

import org.jgrapht.EdgeFactory;









import us.lsi.astar.AStarGraph;
import us.lsi.graphs.*;


public class PuzzleGraph1 extends UndirectedSimpleVirtualGraph<EstadoPuzzle, SimpleEdge<EstadoPuzzle>>
	implements AStarGraph<EstadoPuzzle, SimpleEdge<EstadoPuzzle>>{

	
	public static PuzzleGraph1 create(
			EdgeFactory<EstadoPuzzle, SimpleEdge<EstadoPuzzle>> edgeFactory) {
		return new PuzzleGraph1(edgeFactory);
	}

	protected PuzzleGraph1(
			EdgeFactory<EstadoPuzzle, SimpleEdge<EstadoPuzzle>> edgeFactory) {
		super(edgeFactory);
	}

	@Override
	public double getVertexWeight(EstadoPuzzle vertex) {
		return 0;
	}

	@Override
	public double getVertexWeight(EstadoPuzzle vertex,
			SimpleEdge<EstadoPuzzle> edgeIn,
			SimpleEdge<EstadoPuzzle> edgeOut) {
		return 0;
	}

	@Override
	public double getWeightToEnd(EstadoPuzzle startVertex,EstadoPuzzle endVertex, Function<EstadoPuzzle,Double> goalDistance, 
			Set<EstadoPuzzle> goalSet) {
		if(startVertex==null || endVertex==null)
			throw new IllegalArgumentException("El vértice inicial y final no pueden ser null");
		return startVertex.getNumDiferentes(endVertex);
	}


}
