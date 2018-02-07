package us.lsi.ag.anuncios;



import us.lsi.ag.IndexProblemAG;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.algoritmos.Algoritmos;
import us.lsi.bt.anuncios.ListaDeAnunciosAEmitir;
import us.lsi.bt.anuncios.ProblemaAnuncios;

public class TestAnunciosAG {
	

	public static void main(String[] args){
		
		
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 100;
		
		StoppingConditionFactory.NUM_GENERATIONS = 400;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.GenerationCount;
		
		ProblemaAnuncios.tiempoTotal = 30;
		System.out.println(AlgoritmoAG.raiz+"anuncios.txt");
		IndexProblemAG<ListaDeAnunciosAEmitir> p = new ProblemaAnunciosAG(AlgoritmoAG.raiz+"anuncios.txt");		
		AlgoritmoAG ap = Algoritmos.createAG(ChromosomeType.IndexPermutationSubList,p);
		ap.ejecuta();
		
		
		System.out.println("================================");
		System.out.println(p.getSolucion(ChromosomeFactory.asIndex(ap.getBestFinal())));
		System.out.println("================================");		
	}	

}
