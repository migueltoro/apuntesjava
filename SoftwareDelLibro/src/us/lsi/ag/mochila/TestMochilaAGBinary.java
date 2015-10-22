package us.lsi.ag.mochila;


import us.lsi.ag.AlgoritmoAG;
import us.lsi.ag.ProblemaAGIndex;
import us.lsi.ag.agchromosomes.BinaryIndexChromosome;
import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agchromosomes.IndexChromosome;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.ag.agstopping.SolutionsNumber;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.algoritmos.AbstractAlgoritmo;
import us.lsi.algoritmos.Algoritmos;
import us.lsi.pd.mochila.ProblemaMochila;
import us.lsi.pd.mochila.SolucionMochila;

public class TestMochilaAGBinary {


	public static void main(String[] args){
		
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 50;
		
		StoppingConditionFactory.NUM_GENERATIONS = 10000;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.FITNESS_MIN = 623;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionFactory.StoppingConditionType.SolutionsNumber;
		
		
		ProblemaMochila.capacidadInicial = 78;
		ProblemaAGIndex<SolucionMochila> p = new ProblemaMochilaAG(AbstractAlgoritmo.getRaiz()+"objetosmochila.txt");
		AlgoritmoAG ap = Algoritmos.createAG(ChromosomeType.BinaryIndex,p);	
		ap.ejecuta();
		
		System.out.println(ProblemaMochila.getObjetosDisponibles());
		System.out.println(BinaryIndexChromosome.normalSequence);
		System.out.println("================================");
		IndexChromosome cr = ChromosomeFactory.asIndex(ap.getBestFinal());
		System.out.println(p.getSolucion(cr));
		System.out.println("================================");	
		System.out.println(SolutionsNumber.numeroDeGeneraciones);
	}	

}
