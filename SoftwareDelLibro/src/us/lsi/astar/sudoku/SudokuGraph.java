package us.lsi.astar.sudoku;

import java.util.Set;
import java.util.function.Function;

import org.jgrapht.EdgeFactory;

import us.lsi.astar.AStarGraph;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.UndirectedSimpleVirtualGraph;

public class SudokuGraph extends UndirectedSimpleVirtualGraph<CuadroSudokuVertex, SimpleEdge<CuadroSudokuVertex>> implements
    AStarGraph<CuadroSudokuVertex, SimpleEdge<CuadroSudokuVertex>> {

	public static SudokuGraph create() {
		return new SudokuGraph(SimpleEdge::create);
	}

	private SudokuGraph(EdgeFactory<CuadroSudokuVertex, SimpleEdge<CuadroSudokuVertex>> edgeFactory) {
		super(edgeFactory);
	}

	@Override
	public double getVertexWeight(CuadroSudokuVertex vertex) {
		return 0;
	}

	@Override
	public double getVertexWeight(CuadroSudokuVertex vertex,
			SimpleEdge<CuadroSudokuVertex> edgeIn,
			SimpleEdge<CuadroSudokuVertex> edgeOut) {
		return 0;
	}

	@Override
	public double getWeightToEnd(CuadroSudokuVertex actual,
			CuadroSudokuVertex endVertex, Function<CuadroSudokuVertex,Double> goalDistance,
			Set<CuadroSudokuVertex> goalSet) {
		return goalDistance.apply(actual);
	}

	@Override
	public double getEdgeWeight(SimpleEdge<CuadroSudokuVertex> e){
		return 1.;
	}

}
