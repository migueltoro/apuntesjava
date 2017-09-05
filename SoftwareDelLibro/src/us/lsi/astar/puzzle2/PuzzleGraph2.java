package us.lsi.astar.puzzle2;



import us.lsi.astar.AStarGraph;
import us.lsi.common.PairInteger;
import us.lsi.graphs.*;


public class PuzzleGraph2 extends 
	UndirectedSimpleVirtualGraph<EstadoPuzzle2, ActionSimpleEdge<PairInteger,EstadoPuzzle2>>
	implements AStarGraph<EstadoPuzzle2, ActionSimpleEdge<PairInteger,EstadoPuzzle2>>{

	
	public static PuzzleGraph2 create(EstadoPuzzle2... v) {
		return new PuzzleGraph2(v);
	}
	
	protected PuzzleGraph2(EstadoPuzzle2... v) {
		super(v);
	}

	@Override
	public double getWeightToEnd(EstadoPuzzle2 startVertex,EstadoPuzzle2 endVertex) {
		if(startVertex==null || endVertex==null)
			throw new IllegalArgumentException("El vértice inicial y final no pueden ser null");
		return startVertex.getNumDiferentes(endVertex);
	}


}
