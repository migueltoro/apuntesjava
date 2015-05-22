package us.lsi.ag.reinas;

import java.util.List;









import org.apache.commons.math3.genetics.Chromosome;

import us.lsi.ag.AlgoritmoAG;
import us.lsi.ag.AlgoritmoAGRandomKey;
import us.lsi.ag.ProblemaAGBag;
import us.lsi.algoritmos.Algoritmos;
import us.lsi.bt.reinas.Reina;


public class TestReinasAG {

	

	public static void main(String[] args){
		AlgoritmoAG.NUM_GENERATIONS = 150000;
		AlgoritmoAG.ELITISM_RATE  = 0.20;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.8;
		AlgoritmoAG.POPULATION_SIZE = 400;
		AlgoritmoAG.SOLUTIONS_NUMBER = 1;
		AlgoritmoAG.FITNESS = 0.;
		AlgoritmoAG.stoppingConditionType = AlgoritmoAG.StoppingConditionType.SolutionsNumber;
		AlgoritmoAG.crossoverType = AlgoritmoAG.CrossoverType.OnePoint;
		ProblemaReinasAG.numeroDeReinas = 20;
		ProblemaAGBag<List<Reina>> p = ProblemaReinasAG.create();
		AlgoritmoAGRandomKey ap = Algoritmos.createAGRandomKey(p);
		ap.ejecuta();
		System.out.println("================================");
		
		System.out.println("================================");
		for (Chromosome c: AlgoritmoAG.bestChromosomes) {
			System.out.println(p.getSolucion(AlgoritmoAG.asCromosoma(c)));
		}
		System.out.println("================================");
		System.out.println(AlgoritmoAG.bestChromosomes.size());
	}	

}

