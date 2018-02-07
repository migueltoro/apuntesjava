package us.lsi.ag.reinas;

import java.util.List;

import us.lsi.ag.IndexProblemAG;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.ag.agchromosomes.ChromosomeFactory.CrossoverType;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.algoritmos.Algoritmos;
import us.lsi.bt.reinas.Reina;


public class TestReinasAG {

	

	public static void main(String[] args){
		
		AlgoritmoAG.ELITISM_RATE  = 0.20;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.8;
		AlgoritmoAG.POPULATION_SIZE = 400;
		
		StoppingConditionFactory.NUM_GENERATIONS = 2000;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.FITNESS_MIN = 0.;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.SolutionsNumber;
		
		ChromosomeFactory.crossoverType = CrossoverType.OnePoint;
		
		ProblemaReinasAG.numeroDeReinas = 20;
		IndexProblemAG<List<Reina>> p = ProblemaReinasAG.create();
		AlgoritmoAG ap = Algoritmos.createAG(ChromosomeType.IndexPermutation,p);
		ap.ejecuta();
		System.out.println("================================");
		
		System.out.println("================================");
/*		Set<Chromosome> s = AlgoritmoAG.bestChromosomes.stream().collect(Collectors.toSet());
		for (Chromosome c: s) {
			System.out.println(ChromosomeFactory.asIndex(c).fitness()+","+p.getSolucion(ChromosomeFactory.asIndex(c)));
		}
		System.out.println("================================");
		System.out.println(s.size());
*/		System.out.println(p.getSolucion(ChromosomeFactory.asIndex(ap.getBestFinal()))+","+ChromosomeFactory.asIndex(ap.getBestFinal()).fitness());
	}	

}

