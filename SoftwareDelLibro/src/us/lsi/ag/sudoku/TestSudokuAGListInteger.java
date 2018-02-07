package us.lsi.ag.sudoku;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agchromosomes.ChromosomeFactory.CrossoverType;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.algoritmos.Algoritmos;
import us.lsi.sa.sudoku.CuadroSudoku;
import us.lsi.sa.sudoku.ProblemaSudokuAGListInteger;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class TestSudokuAGListInteger {


	public static void main(String[] args) {
		AlgoritmoAG.ELITISM_RATE  = 0.2;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 10000;
		
		StoppingConditionFactory.NUM_GENERATIONS = 100;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.FITNESS_MIN = 230.;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.SolutionsNumber;
		
		ChromosomeFactory.crossoverType = CrossoverType.OnePoint;
		
		CuadroSudoku.tamSubCuadro = 3;
		CuadroSudoku.iniDatos("sudoku.txt");
		CuadroSudoku.asignaValoresUnicos();
		
		ProblemaSudokuAGListInteger p = new ProblemaSudokuAGListInteger();	
		CuadroSudoku c = ProblemaSudokuAGListInteger.inicial;
		c.show();
		Multiset<Integer> ms = HashMultiset.create();
		ms.addAll(c.getListDatos());
		System.out.println(ms);
		AlgoritmoAG a = Algoritmos.createAG(ChromosomeFactory.ChromosomeType.ListInteger ,p);		
		a.ejecuta();
		System.out.println("Mejor Solución =");
		c = CuadroSudoku.create(ChromosomeFactory.asListInteger(a.getBestFinal()).decode());
		c.show();
		ms = HashMultiset.create();
		ms.addAll(c.getListDatos());
		System.out.println(ms);

	}

}
