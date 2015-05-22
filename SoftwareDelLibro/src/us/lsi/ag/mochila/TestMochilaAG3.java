package us.lsi.ag.mochila;

import us.lsi.ag.AlgoritmoAG;
import us.lsi.ag.AlgoritmoAGMulti;
import us.lsi.ag.BagMultiChromosome;
import us.lsi.ag.ProblemaAGBag;
import us.lsi.ag.SolutionsNumber;
import us.lsi.algoritmos.AbstractAlgoritmo;
import us.lsi.algoritmos.Algoritmos;
import us.lsi.pd.mochila.ProblemaMochila;
import us.lsi.pd.mochila.SolucionMochila;

public class TestMochilaAG3 {

	public static void main(String[] args) {
		AlgoritmoAG.NUM_GENERATIONS = 6000;
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 50;
		AlgoritmoAG.SOLUTIONS_NUMBER = 1;
		AlgoritmoAG.FITNESS = 623;
		AlgoritmoAG.stoppingConditionType = AlgoritmoAG.StoppingConditionType.SolutionsNumber;
		
		ProblemaMochila.capacidadInicial = 78;
		ProblemaAGBag<SolucionMochila> p = new ProblemaMochilaAG3(AbstractAlgoritmo.getRaiz()+"objetosmochila.txt");
		AlgoritmoAGMulti ap = Algoritmos.createAGMulti(p);
		ap.ejecuta();
		
		System.out.println(ProblemaMochila.getObjetosDisponibles());
		System.out.println(BagMultiChromosome.multiplicidades);
		System.out.println(BagMultiChromosome.numeroDeBits);
		System.out.println(BagMultiChromosome.pow);
		System.out.println("================================");
		System.out.println(AlgoritmoAG.asCromosoma(ap.getBestFinal()).decode());
		System.out.println(p.getSolucion(AlgoritmoAG.asCromosoma(ap.getBestFinal())));
//		System.out.println(AlgoritmoAG.);
		System.out.println("================================");
		System.out.println(SolutionsNumber.numeroDeGeneraciones);
	}

}
