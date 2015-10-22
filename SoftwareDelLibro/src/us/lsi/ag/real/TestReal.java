package us.lsi.ag.real;

import us.lsi.ag.AlgoritmoAG;
import us.lsi.ag.ProblemaAGReal;
import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agchromosomes.RealChromosome;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.algoritmos.Algoritmos;

import java.util.List;

public class TestReal {

	public static void main(String[] args){
		
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 200;
		
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.GenerationCount;
		StoppingConditionFactory.NUM_GENERATIONS = 100;
		
		ChromosomeFactory.crossoverType = ChromosomeFactory.CrossoverType.OnePoint;
		
		ProblemaAGReal<List<Double>> p = new ProblemaReal();
		AlgoritmoAG ap = Algoritmos.createAG(ChromosomeFactory.ChromosomeType.Real,p);
		ap.ejecuta();
		
		RealChromosome cr = ChromosomeFactory.asReal(ap.getBestFinal());
		System.out.println("================================");
		System.out.println(p.getSolucion(cr)+","+(cr.fitness()));
		System.out.println("================================");
		
	}	
	
}
