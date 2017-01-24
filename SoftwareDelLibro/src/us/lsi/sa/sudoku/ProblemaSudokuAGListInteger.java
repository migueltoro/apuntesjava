package us.lsi.sa.sudoku;

import java.util.List;

import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.MutationPolicy;

import us.lsi.ag.ProblemaAGListInteger;
import us.lsi.ag.agchromosomes.ListIntegerChromosome;
import us.lsi.ag.agoperators.NoCrossoverPolicy;
import us.lsi.ag.agoperators.PermutationIntMutation;

public class ProblemaSudokuAGListInteger implements ProblemaAGListInteger<CuadroSudoku>{

	public static CuadroSudoku inicial = CuadroSudoku.createCompletadoPorPosicionesLibres();
	
	@Override
	public int getDimension() {
		return CuadroSudoku.numPosicionesLibres;
	}

	@Override
	public double fitnessFunction(ListIntegerChromosome cr) {
		List<Integer> d = cr.decode();
		CuadroSudoku c = CuadroSudoku.create(d);
		Double r = -1.*c.getObjetivoMin();
		return r;
	}

	@Override
	public List<Integer> getRandomList() {
		ListIntegerChromosome cr = new ListIntegerChromosome(inicial.getValoresEnPosicionesLibres());		
		return ((ListIntegerChromosome)mp.mutate(cr)).decode();
	}

	private static MutationPolicy mp = new PermutationIntMutation();
	private static CrossoverPolicy cp = new NoCrossoverPolicy();
	
	@Override
	public MutationPolicy getMutationPolicy() {		
		return mp;
	}
	
	
	@Override
	public CuadroSudoku getSolucion(ListIntegerChromosome c) {
		List<Integer> d = c.decode();
		CuadroSudoku cr = CuadroSudoku.create(d);
		return cr;
	}

	@Override
	public CrossoverPolicy getCrossoverPolicy() {
		return cp;
	}

}
