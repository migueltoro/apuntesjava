package us.lsi.bt.anuncios;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.bt.AlgoritmoBT;



public class TestAnunciosBT {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProblemaAnuncios.leeYOrdenaAnuncios(AlgoritmoBT.raiz+"anuncios.txt");
		ProblemaAnuncios.tiempoTotal = 30;
		AlgoritmoBT.numeroDeSoluciones = 6;
		AlgoritmoBT.conFiltro = true;
		System.out.println(ProblemaAnuncios.todosLosAnunciosDisponibles);
		System.out.println(ProblemaAnuncios.restricciones);
		EstadoAnunciosBT e = EstadoAnunciosBT.create();
		AlgoritmoBT<ListaDeAnunciosAEmitir, Integer> a = Algoritmos.createBT(e);
		a.ejecuta();
//		for(ListaDeAnunciosAEmitir s: a.getSoluciones()){
//			System.out.println(s);
//		}
		System.out.println("Mejor ="+a.getSolucion());
	}
}
