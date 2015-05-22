package us.lsi.sa.sudoku;

import java.util.List;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;

import us.lsi.bt.sudoku.CuadroSudoku;
import us.lsi.bt.sudoku.ProblemaSudoku;
import us.lsi.sa.ProblemaSA;
import us.lsi.stream.Stream2;
import us.lsi.common.ParInteger;

public class ProblemaSudokuSAAp1 extends ProblemaSudoku implements ProblemaSA<EstadoSudokuSAAp1, CuadroSudoku, ParInteger> {

	
	
	public ProblemaSudokuSAAp1(String nf) {
		super(nf);
	}

	@Override
	public EstadoSudokuSAAp1 getEstadoInicial() {
		CuadroSudoku cuadro = CuadroSudoku.create();
		
		List<List<Integer>> valoresLibresEnX = Lists.newArrayList();

		for (int x = 0; x < ProblemaSudoku.numeroDeFilas; x++) {
			List<Integer> ls = Stream2.create(
					IntStream.rangeClosed(1, ProblemaSudoku.numeroDeFilas)
							.boxed()).toList();
			ls.removeAll(cuadro.getValoresOcupadosEnX().get(x));
			valoresLibresEnX.add(ls);
		}
		
		for(int x=0; x < ProblemaSudoku.numeroDeFilas; x++){
			int a = 0;
			for (Integer p: ProblemaSudoku.posicionesLibresEnX.get(x)) {
					cuadro.setInfo(p, valoresLibresEnX.get(x).get(a));
					a++;
				}
			}
		return EstadoSudokuSAAp1.create(cuadro);
	}

	
	
}
