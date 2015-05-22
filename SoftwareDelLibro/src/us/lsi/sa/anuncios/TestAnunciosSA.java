package us.lsi.sa.anuncios;


import us.lsi.algoritmos.Algoritmos;
import us.lsi.bt.anuncios.ListaDeAnunciosAEmitir;
import us.lsi.bt.anuncios.ProblemaAnuncios;
import us.lsi.pd.AlgoritmoPD;
import us.lsi.sa.AlgoritmoSA;

public class TestAnunciosSA {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		ProblemaAnuncios.leeYOrdenaAnuncios(AlgoritmoPD.getRaiz()+"anuncios.txt");
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
		AlgoritmoSA<EstadoAnunciosSA,ListaDeAnunciosAEmitir,AlternativaAnuncios> ap = Algoritmos.createSA(ProblemaAnunciosSA.create());
		ap.ejecuta();		
		System.out.println("------");
		System.out.println(ProblemaAnuncios.todosLosAnunciosDisponibles);
		System.out.println("------");
		System.out.println(ProblemaAnuncios.restricciones);
		System.out.println("------");
		System.out.println("Soluciones ------");
		for(ListaDeAnunciosAEmitir s: ap.soluciones){
			System.out.println("Solucion =  "+s);
		}
		System.out.println("------");
		System.out.println("------");
		System.out.println(ap.mejorSolucionEncontrada.getSolucion());
		System.out.println(ProblemaAnuncios.tiempoTotal);
	}
}
