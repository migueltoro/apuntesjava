package us.lsi.astar.puzzle2;



import us.lsi.astar.AStarGraph;
import us.lsi.common.ParInteger;
import us.lsi.graphs.*;


public class PuzzleGraph1 extends 
	SimpleVirtualGraph<EstadoPuzzle, AlternativeSimpleEdge<ParInteger,EstadoPuzzle>>
	implements AStarGraph<EstadoPuzzle, AlternativeSimpleEdge<ParInteger,EstadoPuzzle>>{

	
	public static PuzzleGraph1 create(EstadoPuzzle... v) {
		return new PuzzleGraph1(v);
	}
	
	protected PuzzleGraph1(EstadoPuzzle... v) {
		super(v);
	}

	@Override
	public double getWeightToEnd(EstadoPuzzle startVertex,EstadoPuzzle endVertex) {
		if(startVertex==null || endVertex==null)
			throw new IllegalArgumentException("El vértice inicial y final no pueden ser null");
		return startVertex.getNumDiferentes(endVertex);
	}


}
