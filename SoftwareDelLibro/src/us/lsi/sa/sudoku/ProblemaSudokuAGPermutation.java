package us.lsi.sa.sudoku;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import us.lsi.ag.ProblemaAGIndex;
import us.lsi.ag.agchromosomes.IndexChromosome;
import us.lsi.common.Lists2;


public class ProblemaSudokuAGPermutation implements ProblemaAGIndex<CuadroSudoku> {	
	

	@Override
	public Integer getObjectsNumber() {
		return CuadroSudoku.numPosicionesLibres;
	}

	@Override
	public Double fitnessFunction(IndexChromosome cr) {
		List<Integer> d = cr.decode();
		CuadroSudoku c = CuadroSudoku.create(d);
		Double r = 1.*c.getObjetivoMax();
		return r;
	}

	@Override
	public CuadroSudoku getSolucion(IndexChromosome cr) {
		List<Integer> d = cr.decode();
		CuadroSudoku c = CuadroSudoku.create(d);
		return c;
	}
	
	@Override
	public List<Integer> getNormalSequence() {
		 Multiset<Integer> ms = HashMultiset.create();
		 ms.addAll(CuadroSudoku.casillasOcupadas.values());
		 List<Integer> r = IntStream.rangeClosed(1,CuadroSudoku.numeroDeFilas)
				.boxed()
				.map(x->Lists2.nCopias(CuadroSudoku.numeroDeFilas-ms.count(x),x).stream())
				.flatMap(x->x)
				.collect(Collectors.toList());
		 return r;
	}

}
