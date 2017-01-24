package us.lsi.astar.puzzle;


import us.lsi.astar.AStarGraph;
import us.lsi.graphs.*;


public class PuzzleGraph1 extends UndirectedSimpleVirtualGraph<EstadoPuzzle, SimpleEdge<EstadoPuzzle>>
	implements AStarGraph<EstadoPuzzle, SimpleEdge<EstadoPuzzle>>{

	
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
