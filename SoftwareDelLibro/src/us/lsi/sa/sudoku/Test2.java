package us.lsi.sa.sudoku;

import org.apache.commons.math3.genetics.MutationPolicy;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import us.lsi.ag.agchromosomes.ListIntegerChromosome;;

public class Test2 {

	

	public static void main(String[] args) {
		
		CuadroSudoku.tamSubCuadro = 3;		
		CuadroSudoku.iniDatos("sudoku.txt");
		CuadroSudoku c = CuadroSudoku.create();
		c.show();
		CuadroSudoku.asignaValoresUnicos();
		CuadroSudoku.sortPosicionesLibresPorPos();
		ProblemaSudokuAGListInteger p = new ProblemaSudokuAGListInteger();
		c = ProblemaSudokuAGListInteger.inicial;
		c.show();	
		Multiset<Integer> ms = HashMultiset.create();
		ms.addAll(c.getListDatos());
		System.out.println(ms);	
		CuadroSudoku c2 = CuadroSudoku.create();
		c2.show();
		System.out.println(CuadroSudoku.comienzoDeFilas+","+CuadroSudoku.numPosicionesLibres+","+CuadroSudoku.posicionesLibres.size());	
		for (int i = 0; i < CuadroSudoku.posicionesLibres.size(); i++) {
			System.out.println(i+","+Casilla.create(CuadroSudoku.posicionesLibres.get(i)));
		}
		
		MutationPolicy m = p.getMutationPolicy();
		ListIntegerChromosome.iniValues(p);
		for (int i = 0; i < 50; i++) {
			System.out.println(i);
			c = CuadroSudoku.create(((ListIntegerChromosome) m
					.mutate(new ListIntegerChromosome(c
							.getValoresEnPosicionesLibres()))).decode());
			c.show();
			ms = HashMultiset.create();
			ms.addAll(c.getListDatos());
			System.out.println(ms);		
		}
		c = CuadroSudoku.createCompletadoPorPosicionesLibres();
		c.show();

	}

}
