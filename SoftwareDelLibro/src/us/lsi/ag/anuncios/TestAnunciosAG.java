package us.lsi.ag.anuncios;



import us.lsi.ag.AlgoritmoAG;
import us.lsi.ag.AlgoritmoAGMix;
import us.lsi.ag.ProblemaAGBag;
import us.lsi.algoritmos.Algoritmos;
import us.lsi.bt.anuncios.ListaDeAnunciosAEmitir;
import us.lsi.bt.anuncios.ProblemaAnuncios;

public class TestAnunciosAG {
	

	public static void main(String[] args){
		
		AlgoritmoAG.NUM_GENERATIONS = 400;
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 100;
		AlgoritmoAG.SOLUTIONS_NUMBER = 1;
		AlgoritmoAG.FITNESS = 623;
		AlgoritmoAG.stoppingConditionType = AlgoritmoAG.StoppingConditionType.GenerationCount;
		
		ProblemaAnuncios.tiempoTotal = 30;
		System.out.println(AlgoritmoAG.getRaiz()+"anuncios.txt");
		ProblemaAGBag<ListaDeAnunciosAEmitir> p = new ProblemaAnunciosAG(AlgoritmoAG.getRaiz()+"anuncios.txt");		
		AlgoritmoAGMix ap = Algoritmos.createAGMix(p);
		ap.ejecuta();
		
		
		System.out.println("================================");
		System.out.println(p.getSolucion(AlgoritmoAG.asCromosoma(ap.getBestFinal())));
		System.out.println("================================");		
	}	

}
