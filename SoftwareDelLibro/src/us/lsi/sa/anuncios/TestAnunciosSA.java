package us.lsi.sa.anuncios;


import org.apache.commons.math3.genetics.Chromosome;

import us.lsi.ag.AlgoritmoAG;
import us.lsi.ag.ProblemaAGIndex;
import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.ag.anuncios.ProblemaAnunciosAG;
import us.lsi.algoritmos.Algoritmos;
import us.lsi.bt.anuncios.ListaDeAnunciosAEmitir;
import us.lsi.bt.anuncios.ProblemaAnuncios;
import us.lsi.pd.AlgoritmoPD;
import us.lsi.sa.AlgoritmoSA;

public class TestAnunciosSA {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		ProblemaAnuncios.leeYOrdenaAnuncios(AlgoritmoPD.raiz+"anuncios.txt");
/*		System.out.println("------");
		System.out.println(ProblemaAnuncios.todosLosAnunciosDisponibles);
		System.out.println("------");
		System.out.println(ProblemaAnuncios.restricciones);
		System.out.println("------");
*/		ProblemaAnuncios.tiempoTotal = 30;
		AlgoritmoSA.temperaturaInicial = 10000;
		AlgoritmoSA.alfa = 0.98;
		AlgoritmoSA.numeroDeIteracionesPorIntento = 400;
		AlgoritmoSA.numeroDeIteracionesALaMismaTemperatura = 20;
		AlgoritmoSA.numeroDeIntentos = 4;
		ProblemaAGIndex<ListaDeAnunciosAEmitir> p = new ProblemaAnunciosAG(AlgoritmoAG.raiz+"anuncios.txt");		
		AlgoritmoSA ap = Algoritmos.createSA(ChromosomeType.IndexPermutationSubList,p);
		ap.ejecuta();		
		System.out.println("------");
		System.out.println(ProblemaAnuncios.todosLosAnunciosDisponibles);
		System.out.println("------");
		System.out.println(ProblemaAnuncios.restricciones);
		System.out.println("------");
		System.out.println("Soluciones ------");
		for(Chromosome s: ap.soluciones){
			System.out.println("Solucion =  "+p.getSolucion(ChromosomeFactory.asIndex(s)));
		}
		System.out.println("------");
		System.out.println("------");
		System.out.println(p.getSolucion(ChromosomeFactory.asIndex(ap.mejorSolucionEncontrada.asChromosome())));
		System.out.println(ProblemaAnuncios.tiempoTotal);
	}
}
