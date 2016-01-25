package us.lsi.sa.reinas;


import org.apache.commons.math3.genetics.Chromosome;

import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.ag.reinas.ProblemaReinasAG;
import us.lsi.algoritmos.Algoritmos;
import us.lsi.sa.AlgoritmoSA;

public class TestReinasSA {

	

	public static void main(String[] args) {
		ProblemaReinasAG.numeroDeReinas = 8;
		AlgoritmoSA.temperaturaInicial = 100;
		AlgoritmoSA.alfa = 0.98;
		AlgoritmoSA.numeroDeIteracionesPorIntento = 1000;
		AlgoritmoSA.numeroDeIteracionesALaMismaTemperatura = 100;
		AlgoritmoSA.numeroDeIntentos = 8;
		ProblemaReinasAG p = ProblemaReinasAG.create();
		AlgoritmoSA ap = Algoritmos.createSA(ChromosomeType.IndexPermutation,p);
		ap.ejecuta();		
		for(Chromosome s: ap.soluciones){
			System.out.println("Solucion =  "+p.getSolucion(ChromosomeFactory.asIndex(s)));
		}
	}

}
