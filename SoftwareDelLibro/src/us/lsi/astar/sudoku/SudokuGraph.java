package us.lsi.astar.sudoku;

import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.EdgeFactory;

import us.lsi.astar.AStarGraph;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.UndirectedSimpleVirtualGraph;

public class SudokuGraph extends UndirectedSimpleVirtualGraph<CuadroSudokuVertex, SimpleEdge<CuadroSudokuVertex>> implements
    AStarGraph<CuadroSudokuVertex, SimpleEdge<CuadroSudokuVertex>> {

	public static SudokuGraph create() {
		EdgeFactory<CuadroSudokuVertex, SimpleEdge<CuadroSudokuVertex>> edgeFactory = SimpleEdge.create();
		return new SudokuGraph(edgeFactory);
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
			CuadroSudokuVertex endVertex, Predicate<CuadroSudokuVertex> goal,
			Set<CuadroSudokuVertex> goalSet) {
		return actual.getCuadro().getObjetivoMin();
	}

	@Override
	public double getEdgeWeight(SimpleEdge<CuadroSudokuVertex> e){
		return 1.;
	}

}
