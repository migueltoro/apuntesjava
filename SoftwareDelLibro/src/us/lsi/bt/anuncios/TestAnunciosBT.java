package us.lsi.bt.anuncios;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.bt.AlgoritmoBT;
import us.lsi.pd.AlgoritmoPD;


public class TestAnunciosBT {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProblemaAnuncios.leeYOrdenaAnuncios(AlgoritmoPD.getRaiz()+"anuncios.txt");
		ProblemaAnuncios.tiempoTotal = 30;
		AlgoritmoBT.numeroDeSoluciones = 6;
		System.out.println(ProblemaAnuncios.todosLosAnunciosDisponibles);
		System.out.println(ProblemaAnuncios.restricciones);
		AlgoritmoBT.soloLaPrimeraSolucion = false;
		AlgoritmoBT<ListaDeAnunciosAEmitir, Integer> a = Algoritmos.createBT(ProblemaAnunciosBT.create());
		a.ejecuta();
		for(ListaDeAnunciosAEmitir s: a.soluciones){
			System.out.println(s);
		}
		System.out.println("Mejor ="+a.solucion);
	}
}
