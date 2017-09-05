package us.lsi.astar.puzzle;


import us.lsi.astar.AStarGraph;
import us.lsi.graphs.*;


public class PuzzleGraph1 extends UndirectedSimpleVirtualGraph<EstadoPuzzle1, SimpleEdge<EstadoPuzzle1>>
	implements AStarGraph<EstadoPuzzle1, SimpleEdge<EstadoPuzzle1>>{

	
	public static PuzzleGraph1 create(EstadoPuzzle1... v) {
		return new PuzzleGraph1(v);
	}

	protected PuzzleGraph1(EstadoPuzzle1... v) {
		super(v);
	}

	

	@Override
	public double getWeightToEnd(EstadoPuzzle1 startVertex,EstadoPuzzle1 endVertex) {
		if(startVertex==null || endVertex==null)
			throw new IllegalArgumentException("El vértice inicial y final no pueden ser null");
		return startVertex.getNumDiferentes(endVertex);
	}


}
