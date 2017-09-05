package us.lsi.bt.afinidad;


import java.util.function.Predicate;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.bt.AlgoritmoBT;
import us.lsi.bt.EstadoBT;
import us.lsi.pd.afinidad.ProblemaAfinidad;
import us.lsi.pd.afinidad.SolucionAfinidad;


public class TestAfinidadBT {
	
	public static void main(String[] args) {
		
		//creaProblema y lanza algoritmo
		AlgoritmoBT.calculaMetricas();
		AlgoritmoBT.conFiltro = true;
		ProblemaAfinidad.create("ficheros/afinidad_test2.txt");
		EstadoBT<SolucionAfinidad, Integer> p=  EstadoAfinidadBT.create();
		AlgoritmoBT<SolucionAfinidad, Integer> a= Algoritmos.createBT(p);
		a.ejecuta();
		
		//recuperasolución		
//		System.out.println(AlgoritmoBT.getMejorValor()+" ,"+a.solucion);
		Predicate<SolucionAfinidad> pp = x->x.getAfinidad().equals(a.getSolucion().getAfinidad());
		System.out.println(a.getSolucionesFiltradas(pp));
		System.out.println(AlgoritmoBT.metricas);
	}
}

