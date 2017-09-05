package us.lsi.pd.tareasprocesadores;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.pd.AlgoritmoPD;


public class Test {

	
	
	public static void main(String[] args) {
		TareasProcesadoresPD p = TareasProcesadoresPD.create(AlgoritmoPD.raiz+"tareas.txt",3);
		System.out.println(Tarea.tareas);
		AlgoritmoPD.calculaMetricas();
		AlgoritmoPD.conFiltro = true;
		AlgoritmoPD<Map<Integer,List<Tarea>>,Integer> a = Algoritmos.createPD(p);
		a.ejecuta();
		System.out.println("Solucion Del Problema " +a.getSolucionParcial(p));		
		String s = a.getSolucion(p).entrySet()
				.stream()
				.map(x->x.toString())
				.collect(Collectors.joining("\n"));			
		System.out.println(s);
		a.showAllGraph("C:\\Users\\Boss\\Desktop\\Ficheros\\Procesadores.gv", "Grafo", p);
		System.out.println(AlgoritmoPD.metricas);
	}

}
