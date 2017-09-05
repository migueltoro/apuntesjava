package us.lsi.ag.mochila;

import us.lsi.ag.AlgoritmoAG;
import us.lsi.ag.ProblemaAGIndex;
import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agchromosomes.IndexRangeChromosome;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.ag.agstopping.SolutionsNumber;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.algoritmos.Algoritmos;
import us.lsi.pd.mochila.ProblemaMochila;
import us.lsi.pd.mochila.SolucionMochila;

public class TestMochilaAGRange {

	public static void main(String[] args) {
		
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 50;
		
		StoppingConditionFactory.NUM_GENERATIONS = 6000;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.FITNESS_MIN = 623;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionFactory.StoppingConditionType.SolutionsNumber;
		
		ProblemaMochila.capacidadInicial = 78;
		ProblemaAGIndex<SolucionMochila> p = new ProblemaMochilaAGRange(AlgoritmoAG.raiz+"objetosmochila.txt");
		
		AlgoritmoAG ap = Algoritmos.createAG(ChromosomeType.IndexRange,p);
		ap.ejecuta();
		
		System.out.println(ProblemaMochila.getObjetosDisponibles());
		System.out.println(IndexRangeChromosome.numeroDeBits);
		System.out.println("================================");
		System.out.println(ChromosomeFactory.asIndex(ap.getBestFinal()).decode());
		System.out.println(p.getSolucion(ChromosomeFactory.asIndex(ap.getBestFinal())));
		System.out.println("================================");
		System.out.println(SolutionsNumber.numeroDeGeneraciones);
	}

}
