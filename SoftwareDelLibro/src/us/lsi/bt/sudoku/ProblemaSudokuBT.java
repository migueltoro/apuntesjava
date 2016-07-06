package us.lsi.bt.sudoku;

import us.lsi.bt.EstadoBT;
import us.lsi.bt.ProblemaBT;
import us.lsi.sa.sudoku.CuadroSudoku;

public class ProblemaSudokuBT implements ProblemaBT<CuadroSudoku, Integer>{


	@Override
	public us.lsi.bt.ProblemaBT.Tipo getTipo() {
		return Tipo.Otro;
	}

	@Override
	public EstadoBT<CuadroSudoku, Integer> getEstadoInicial() {
		return EstadoSudokuBT.create();
	}

	

}
