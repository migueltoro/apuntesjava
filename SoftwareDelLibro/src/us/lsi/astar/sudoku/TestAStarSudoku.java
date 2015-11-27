package us.lsi.astar.sudoku;


import java.util.function.Function;

import org.jgrapht.GraphPath;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.astar.AStarAlgorithm;
import us.lsi.astar.AStarGraph;
import us.lsi.graphs.SimpleEdge;
import us.lsi.sa.sudoku.CuadroSudoku;

public class TestAStarSudoku {

	

	public static void main(String[] args) {
		CuadroSudoku.tamSubCuadro = 3;		
		CuadroSudoku.iniDatos("sudoku.txt");
		CuadroSudoku.asignaValoresUnicos();
		AStarGraph<CuadroSudokuVertex,SimpleEdge<CuadroSudokuVertex>> graph = SudokuGraph.create();
		CuadroSudokuVertex initial = CuadroSudokuVertex.create();
		Function<CuadroSudokuVertex,Double> p = (CuadroSudokuVertex c)->(double)c.getCuadro().getObjetivoMin();
		AStarAlgorithm<CuadroSudokuVertex,SimpleEdge<CuadroSudokuVertex>> d = Algoritmos.createAStar(graph, initial, p);
		GraphPath<CuadroSudokuVertex,SimpleEdge<CuadroSudokuVertex>> path = d.getPath();
		if (path!=null) {
//			System.out.println(path.);
			CuadroSudoku.create(path.getEndVertex().getValoresEnCasillasOcupadas()).show();;
		}

	}

}
