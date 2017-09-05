package us.lsi.bt.tareasyprocesadores;


import java.util.stream.Collectors;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.bt.AlgoritmoBT;
import us.lsi.pd.tareasprocesadores.Tarea;

public class Test2 {

	public static void main(String[] args) {
		EstadoTareasProcesadoresBT2 p = EstadoTareasProcesadoresBT2.create(AlgoritmoBT.raiz+"tareas.txt",3);
		System.out.println(Tarea.tareas);
		AlgoritmoBT.calculaMetricas();
		AlgoritmoBT.conFiltro = true;
		AlgoritmoBT<SolucionTareasProcesadores,Integer> a = Algoritmos.createBT(p);
		a.ejecuta();
				
		System.out.println(a.getSolucion().getObjetivo()+"\n"
				+a.getSolucion()
				.getAsignacion()
				.entrySet()
				.stream()
				.map(x->x.toString())
				.collect(Collectors.joining("\n")));
		System.out.println(AlgoritmoBT.metricas);
	}

}
