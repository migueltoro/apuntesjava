package us.lsi.ag.reinas;

import java.util.List;




import org.apache.commons.math3.genetics.Chromosome;

import us.lsi.ag.AlgoritmoAG;
import us.lsi.ag.ProblemaAGIndex;
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
		
		StoppingConditionFactory.NUM_GENERATIONS = 1500;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 10;
		StoppingConditionFactory.FITNESS_MIN = 0.;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.SolutionsNumber;
		
		ChromosomeFactory.crossoverType = CrossoverType.OnePoint;
		
		ProblemaReinasAG.numeroDeReinas = 8;
		ProblemaAGIndex<List<Reina>> p = ProblemaReinasAG.create();
		AlgoritmoAG ap = Algoritmos.createAG(ChromosomeType.PermutationIndex,p);
		ap.ejecuta();
		System.out.println("================================");
		
		System.out.println("================================");
		for (Chromosome c: AlgoritmoAG.bestChromosomes) {
			System.out.println(p.getSolucion(ChromosomeFactory.asIndex(c)));
		}
		System.out.println("================================");
		System.out.println(AlgoritmoAG.bestChromosomes.size());
	}	

}

