package us.lsi.sa.reinas;

import java.util.List;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.bt.reinas.Reina;
import us.lsi.common.ParInteger;
import us.lsi.sa.AlgoritmoSA;
import us.lsi.sa.ProblemaSA;

public class TestReinasSA {
	
	/**
	 * @param args Argumentos
	 */
	public static void main(String[] args) {
		ProblemaReinasSA.numeroDeReinas = 100;
		AlgoritmoSA.temperaturaInicial = 100;
		AlgoritmoSA.alfa = 0.98;
		AlgoritmoSA.numeroDeIteracionesPorIntento = 1000;
		AlgoritmoSA.numeroDeIteracionesALaMismaTemperatura = 100;
		AlgoritmoSA.numeroDeIntentos = 8;
		ProblemaSA<EstadoReinasSA,List<Reina>,ParInteger> p = ProblemaReinasSA.create();
		AlgoritmoSA<EstadoReinasSA,List<Reina>,ParInteger> ap = Algoritmos.createSA(p);
		ap.ejecuta();		
		for(List<Reina> s: ap.soluciones){
			System.out.println("Solucion =  "+s);
		}
	}	
}
