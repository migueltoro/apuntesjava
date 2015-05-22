package us.lsi.ag.real;

import us.lsi.ag.AlgoritmoAG;
import us.lsi.ag.AlgoritmoAGReal;
import us.lsi.ag.Cromosoma;
import us.lsi.ag.ProblemaAGReal;
import us.lsi.algoritmos.Algoritmos;

import java.util.List;

public class TestReal {

	public static void main(String[] args){
		AlgoritmoAG.NUM_GENERATIONS = 100;
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 200;
		AlgoritmoAG.SOLUTIONS_NUMBER = 1;
		AlgoritmoAG.FITNESS = 623;
		AlgoritmoAG.stoppingConditionType = AlgoritmoAG.StoppingConditionType.GenerationCount;
		
		AlgoritmoAG.crossoverType = AlgoritmoAG.CrossoverType.OnePoint;
		
		ProblemaAGReal<List<Double>> p = new ProblemaReal();
		AlgoritmoAGReal ap = Algoritmos.createAGReal(p);
		ap.ejecuta();
		
		Cromosoma<Double> cr = AlgoritmoAG.asCromosoma(ap.getBestFinal());
		System.out.println("================================");
		System.out.println(p.getSolucion(cr)+","+cr.fitness());
		System.out.println("================================");
		
	}	
	
}
