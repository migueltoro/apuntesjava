package us.lsi.ag.mochila;


import us.lsi.ag.AlgoritmoAG;
import us.lsi.ag.AlgoritmoAGBinary;
import us.lsi.ag.BagBinaryChromosome;
import us.lsi.ag.Cromosoma;
import us.lsi.ag.ProblemaAGBag;
import us.lsi.ag.SolutionsNumber;
import us.lsi.algoritmos.AbstractAlgoritmo;
import us.lsi.algoritmos.Algoritmos;
import us.lsi.pd.mochila.ProblemaMochila;
import us.lsi.pd.mochila.SolucionMochila;

public class TestMochilaAG {


	public static void main(String[] args){
		AlgoritmoAG.NUM_GENERATIONS = 4000;
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 50;
		AlgoritmoAG.SOLUTIONS_NUMBER = 1;
		AlgoritmoAG.FITNESS = 623;
		AlgoritmoAG.stoppingConditionType = AlgoritmoAG.StoppingConditionType.SolutionsNumber;
		
		
		ProblemaMochila.capacidadInicial = 78;
		ProblemaAGBag<SolucionMochila> p = new ProblemaMochilaAG(AbstractAlgoritmo.getRaiz()+"objetosmochila.txt");
		AlgoritmoAGBinary ap = Algoritmos.createAGBinary(p);	
		ap.ejecuta();
		
		System.out.println(ProblemaMochila.getObjetosDisponibles());
		System.out.println(BagBinaryChromosome.normalSequence);
		System.out.println("================================");
		Cromosoma<Integer> cr = AlgoritmoAG.asCromosoma(ap.getBestFinal());
		System.out.println(p.getSolucion(cr));
		System.out.println("================================");	
		System.out.println(SolutionsNumber.numeroDeGeneraciones);
	}	

}
