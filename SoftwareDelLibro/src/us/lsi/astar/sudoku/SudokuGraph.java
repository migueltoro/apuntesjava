package us.lsi.astar.sudoku;

import us.lsi.astar.AStarGraph;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.UndirectedSimpleVirtualGraph;

public class SudokuGraph extends UndirectedSimpleVirtualGraph<CuadroSudokuVertex, SimpleEdge<CuadroSudokuVertex>> implements
    AStarGraph<CuadroSudokuVertex, SimpleEdge<CuadroSudokuVertex>> {

	public static SudokuGraph create(CuadroSudokuVertex... c) {
		return new SudokuGraph(c);
	}

	private SudokuGraph(CuadroSudokuVertex... c) {
		super(c);
	}

	@Override
	public double getWeightToEnd(CuadroSudokuVertex actual, CuadroSudokuVertex endVertex) {	
		return actual.getCuadro().getObjetivoMin();
	}

	@Override
	public double getEdgeWeight(SimpleEdge<CuadroSudokuVertex> e){
		return 1.;
	}

}
