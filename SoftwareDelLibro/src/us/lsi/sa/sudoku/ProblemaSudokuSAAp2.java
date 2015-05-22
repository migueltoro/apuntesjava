package us.lsi.sa.sudoku;

import java.util.List;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;

import us.lsi.bt.sudoku.CuadroSudoku;
import us.lsi.bt.sudoku.ProblemaSudoku;
import us.lsi.common.ParInteger;
import us.lsi.sa.ProblemaSA;
import us.lsi.stream.Stream2;

public class ProblemaSudokuSAAp2 extends ProblemaSudoku implements ProblemaSA<EstadoSudokuSAAp2, CuadroSudoku, ParInteger> {

	public ProblemaSudokuSAAp2(String nf) {
		super(nf);
	}
	
	@Override
	public EstadoSudokuSAAp2 getEstadoInicial() {
		CuadroSudoku cuadro = CuadroSudoku.create();

		List<List<Integer>> valoresLibresEnSubCuadro = Lists.newArrayList();

		for (int sc = 0; sc < ProblemaSudoku.numeroDeFilas; sc++) {
			List<Integer> ls = Stream2.create(
					IntStream.rangeClosed(1, ProblemaSudoku.numeroDeFilas)
							.boxed()).toList();
			ls.removeAll(cuadro.getValoresOcupadosEnSubCuadro().get(sc));
			valoresLibresEnSubCuadro.add(ls);
		}
		for (int sc = 0; sc < ProblemaSudoku.numeroDeFilas; sc++) {
			int a = 0;
			for (Integer p : ProblemaSudoku.posicionesLibresEnSubCuadro.get(sc)) {
				cuadro.setInfo(p, valoresLibresEnSubCuadro.get(sc).get(a));
				a++;
			}
		}
		return EstadoSudokuSAAp2.create(cuadro);
	}

}
