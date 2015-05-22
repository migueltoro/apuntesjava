package us.lsi.bt.sudoku;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import com.google.common.collect.Maps;

import us.lsi.bt.ProblemaBT;

public class ProblemaSudokuBT extends ProblemaSudoku implements ProblemaBT<CuadroSudoku, Integer> {
	
	
	private static Map<Integer, Integer> numOcupadosPorPosicion;
	public static Comparator<Integer> cmp = (Integer x, Integer y) -> 
			ProblemaSudokuBT.numOcupadosPorPosicion
			.get(x).compareTo(ProblemaSudokuBT.numOcupadosPorPosicion.get(y));

	public ProblemaSudokuBT(String nf) {
		super(nf);
		ProblemaSudokuBT.numOcupadosPorPosicion = ProblemaSudokuBT.calculaNumOcupados();
		Collections.sort(ProblemaSudoku.posicionesLibres,ProblemaSudokuBT.cmp.reversed());
	}
	
	@Override
	public EstadoSudokuBT getEstadoInicial() {
		return EstadoSudokuBT.create();
	}

	private static Map<Integer,Integer> calculaNumOcupados(){
		Map<Integer,Integer> map = Maps.newHashMap();
		CuadroSudoku cuadro = CuadroSudoku.create();
		for(Integer i=0; i < ProblemaSudoku.posicionesLibres.size();i++){
			map.put(ProblemaSudoku.posicionesLibres.get(i), cuadro.getValoresOcupadosEnPos(i).size());
		}
		return map;
	}
	
	@Override
	public Tipo getTipo() {
		return Tipo.Todas;
	}
}
